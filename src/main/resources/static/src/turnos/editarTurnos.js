document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const turnoId = urlParams.get("id");

    if (turnoId) {
        // Si se está editando un turno existente, cargar los datos del turno
        fetch(`http://localhost:8080/turnos/${turnoId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Error al obtener los datos del turno");
                }
                return response.json();
            })
            .then((data) => {
                // Asignar los datos a los campos del formulario
                document.getElementById("paciente-id").value = data.pacienteSalidaDto.id || "";
                document.getElementById("paciente-nombre").value = data.pacienteSalidaDto.nombre || "";
                document.getElementById("paciente-apellido").value = data.pacienteSalidaDto.apellido || "";
                document.getElementById("paciente-dni").value = data.pacienteSalidaDto.dni || "";
                document.getElementById("paciente-fecha-ingreso").value = data.pacienteSalidaDto.fechaIngreso || "";
                document.getElementById("paciente-calle").value = data.pacienteSalidaDto.domicilioSalidaDto.calle || "";
                document.getElementById("paciente-numero").value = data.pacienteSalidaDto.domicilioSalidaDto.numero || "";
                document.getElementById("paciente-localidad").value = data.pacienteSalidaDto.domicilioSalidaDto.localidad || "";
                document.getElementById("paciente-provincia").value = data.pacienteSalidaDto.domicilioSalidaDto.provincia || "";
                document.getElementById("paciente-domicilio-id").value = data.pacienteSalidaDto.domicilioSalidaDto.id || "";

                document.getElementById("odontologo-id").value = data.odontologoSalidaDto.id || "";
                document.getElementById("odontologo-nombre").value = data.odontologoSalidaDto.nombre || "";
                document.getElementById("odontologo-apellido").value = data.odontologoSalidaDto.apellido || "";
                document.getElementById("odontologo-matricula").value = data.odontologoSalidaDto.numeroMatricula || ""; // Asegurarse de asignar correctamente

                document.getElementById("fecha-y-hora").value = data.fechaYHora || "";
            })
            .catch((error) => {
                console.error("Error al cargar los datos del turno:", error);
                showAlert("Error", "No se pudieron cargar los datos del turno.");
            });
    }
    
    const saveButton = document.getElementById("saveTurnoBtn");
    if (saveButton) {
        saveButton.addEventListener("click", (event) => {
            event.preventDefault();

            const fechaYHora = document.getElementById("fecha-y-hora").value;
            if (!fechaYHora) {
                showAlert("Error", "Fecha y hora inválidas.");
                return;
            }

            const turno = {
                pacienteSalidaDto: {
                    id: parseInt(document.getElementById("paciente-id").value),
                    nombre: document.getElementById("paciente-nombre").value,
                    apellido: document.getElementById("paciente-apellido").value,
                    dni: parseInt(document.getElementById("paciente-dni").value),
                    fechaIngreso: document.getElementById("paciente-fecha-ingreso").value,
                    domicilioSalidaDto: {
                        calle: document.getElementById("paciente-calle").value,
                        numero: parseInt(document.getElementById("paciente-numero").value),
                        localidad: document.getElementById("paciente-localidad").value,
                        provincia: document.getElementById("paciente-provincia").value,
                        id: parseInt(document.getElementById("paciente-domicilio-id").value) || null,
                    }
                },
                odontologoSalidaDto: {
                    id: parseInt(document.getElementById("odontologo-id").value),
                    nombre: document.getElementById("odontologo-nombre").value,
                    apellido: document.getElementById("odontologo-apellido").value,
                    numeroMatricula: parseInt(document.getElementById("odontologo-matricula").value),
                },
                fechaYHora: document.getElementById("fecha-y-hora").value,
            };

            const method = "PUT";
            const url = `http://localhost:8080/turnos/actualizar/${turnoId}`;

            fetch(url, {
                method: method,
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(turno),
            })
            .then((response) => {
                if (response.ok) {
                    window.location.href = "./turnos.html";
                } else if (response.status === 400) {
                    response.json().then((errorData) => {
                        showAlert("Error", "Error al guardar el turno: " + errorData.mensaje);
                    });
                } else {
                    throw new Error("Error al guardar el turno. Código de error: " + response.status);
                }
            })
            .catch((error) => {
                console.error("Error:", error);
                showAlert("Error", "No se pudo guardar el turno.");
            });
        });
    }

    function showAlert(title, message) {
        alert(`${title}: ${message}`);
    }
});