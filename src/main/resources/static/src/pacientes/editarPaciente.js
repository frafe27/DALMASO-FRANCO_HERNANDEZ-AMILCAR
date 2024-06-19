document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const pacienteId = urlParams.get("id");

    if (pacienteId) {
        // Realizar la solicitud fetch para obtener los datos del paciente
        fetch(`http://localhost:8080/pacientes/${pacienteId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Error al obtener los datos del paciente");
                }
                return response.json();
            })
            .then((data) => {
                // Verificar si los datos se obtuvieron correctamente
                console.log("Datos del paciente obtenidos:", data);

                // Asignar los datos a los campos del formulario
                document.getElementById("paciente-id").value = data.id || "";
                document.getElementById("first-name").value = data.nombre || "";
                document.getElementById("last-name").value = data.apellido || "";
                document.getElementById("dni").value = data.dni || "";
                document.getElementById("fecha-ingreso").value = data.fechaIngreso || "";
                document.getElementById("calle").value = data.domicilioEntradaDto.calle || "";
                document.getElementById("numero").value = data.domicilioEntradaDto.numero || "";
                document.getElementById("localidad").value = data.domicilioEntradaDto.localidad || "";
                document.getElementById("provincia").value = data.domicilioEntradaDto.provincia || "";
            })
            .catch((error) => {
                console.error("Error al cargar los datos del paciente:", error);
                showAlert("Error", "No se pudieron cargar los datos del paciente.");
            });
    }

    const saveButton = document.getElementById("savePacienteBtn");
    saveButton.addEventListener("click", (event) => {
        event.preventDefault();

        const paciente = {
            nombre: document.getElementById("first-name").value,
            apellido: document.getElementById("last-name").value,
            dni: document.getElementById("dni").value,
            fechaIngreso: document.getElementById("fecha-ingreso").value,
            domicilioEntradaDto: {
                calle: document.getElementById("calle").value,
                numero: document.getElementById("numero").value,
                localidad: document.getElementById("localidad").value,
                provincia: document.getElementById("provincia").value,
            },
        };

        const method = pacienteId ? "PUT" : "POST";
        const url = pacienteId ? `http://localhost:8080/pacientes/actualizar/${pacienteId}` : "http://localhost:8080/pacientes/registrar";

        fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(paciente),
        })
            .then((response) => {
                if (response.ok) {
                    window.location.href = "./pacientes.html";
                } else if (response.status === 400) {
                    response.json().then((errorData) => {
                        showAlert("Error", "Error al guardar el paciente: " + errorData.mensaje);
                    });
                } else {
                    throw new Error("Error al guardar el paciente. Código de error: " + response.status);
                }
            })
            .catch((error) => {
                console.error("Error:", error);
                showAlert("Error", "No se pudo guardar el paciente.");
            });
    });

    function showAlert(title, message) {
        alert(`${title}: ${message}`);
    }
});