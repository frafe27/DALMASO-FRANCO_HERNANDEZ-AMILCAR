document.addEventListener("DOMContentLoaded", () => {
    const listPacientes = () => {
        fetch("http://localhost:8080/pacientes/listar")
            .then((response) => response.json())
            .then((data) => {
                const pacientesList = document.getElementById("pacientes-list");

                if (!Array.isArray(data)) {
                    throw new Error('Los datos recibidos no son un array');
                }

                pacientesList.innerHTML = data
                    .map((paciente) => `
                        <tr>
                            <td>${paciente.nombre}</td>
                            <td>${paciente.apellido}</td>
                            <td>${paciente.dni}</td>
                            <td>${paciente.fechaIngreso}</td>
                            <td>${formatDomicilio(paciente.domicilioSalidaDto)}</td>
                            <td>
                                <button data-id="${paciente.id}" class="text-red-500 dltPaciente"><i class="fas fa-trash-alt"></i></button>
                                <button data-id="${paciente.id}" class="text-red-500 editPaciente"><i class="fas fa-edit"></i></button>
                            </td>
                        </tr>
                    `).join("");

                addEventListeners();
            })
            .catch((error) => console.error("Hubo un problema con la petición Fetch:", error));
    };

    const addEventListeners = () => {
        const deleteButtons = document.querySelectorAll(".dltPaciente");
        const editButtons = document.querySelectorAll(".editPaciente");

        deleteButtons.forEach((button) => {
            button.addEventListener("click", (event) => {
                const pacienteId = event.currentTarget.getAttribute("data-id");
                deletePaciente(pacienteId);
            });
        });

        editButtons.forEach((button) => {
            button.addEventListener("click", (event) => {
                const pacienteId = event.currentTarget.getAttribute("data-id");
                window.location.href = `./pacientes.form.html?id=${pacienteId}`; // Cambio aquí
            });
        });

        const pacienteCreateBtn = document.getElementById("ptcCreateBtn");
        pacienteCreateBtn.addEventListener("click", () => {
            window.location.href = "./pacientes.form.html"; // Cambio aquí
        });
    };

    const deletePaciente = (id) => {
        fetch(`http://localhost:8080/pacientes/eliminar?id=${id}`, {
            method: "DELETE",
        })
        .then((response) => {
            if (response.ok) {
                listPacientes();
            } else {
                console.error("Error al eliminar el paciente");
            }
        })
        .catch((error) => console.error("Error:", error));
    };

    const formatDomicilio = (domicilio) => {
        if (!domicilio) {
            return '';
        }
        return `${domicilio.calle || ''} ${domicilio.numero || ''}, ${domicilio.localidad || ''}, ${domicilio.provincia || ''}`;
    };

    listPacientes();
});