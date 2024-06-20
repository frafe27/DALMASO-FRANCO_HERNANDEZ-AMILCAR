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
                document.getElementById("calle").value = data.domicilioSalidaDto.calle || "";
                document.getElementById("numero").value = data.domicilioSalidaDto.numero || "";
                document.getElementById("localidad").value = data.domicilioSalidaDto.localidad || "";
                document.getElementById("provincia").value = data.domicilioSalidaDto.provincia || "";
                document.getElementById("domicilio-id").value = data.domicilioSalidaDto.id || "";
            })
            .catch((error) => {
                console.error("Error al cargar los datos del paciente:", error);
                showAlert("Error", "No se pudieron cargar los datos del paciente.");
            });
    }

    const saveButton = document.getElementById("savePacienteBtn");
    saveButton.addEventListener("click", (event) => {
        event.preventDefault();

        const fechaIngreso = formatDate(document.getElementById("fecha-ingreso").value);
        if (!fechaIngreso) {
            showAlert("Error", "Fecha de ingreso inválida. Formato esperado: YYYY-MM-DD");
            return;
        }
        const paciente = {
            nombre: document.getElementById("first-name").value,
            apellido: document.getElementById("last-name").value,
            dni: parseInt(document.getElementById("dni").value), // Asegurar que el DNI sea numérico si es necesario
            fechaIngreso: fechaIngreso,
            domicilioEntradaDto: {
                calle: document.getElementById("calle").value,
                numero: parseInt(document.getElementById("numero").value), // Asegurar que el número de domicilio sea numérico si es necesario
                localidad: document.getElementById("localidad").value,
                provincia: document.getElementById("provincia").value,
                id: parseInt(document.getElementById("domicilio-id").value) || null, // Incluir el ID del domicilio si existe
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
                        showAlert("Error", "Error al guardar el paciente: " + errorData.fechaIngreso);
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

    const formatDate = (dateString) => {
        const parts = dateString.split('-');
        if (parts.length === 3) {
            const year = parseInt(parts[0]);
            const month = parseInt(parts[1]);
            const day = parseInt(parts[2]);
            if (!isNaN(year) && !isNaN(month) && !isNaN(day)) {
                return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
            }
        }
        return null; // Fecha inválida
    };
});