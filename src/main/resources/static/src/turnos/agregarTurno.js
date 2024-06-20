// Función para obtener datos completos del paciente por ID
function obtenerDatosPaciente(id) {
    const url = `http://localhost:8080/pacientes/${id}`;
    return fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos del paciente con ID ${id}`);
            }
            return response.json();
        })
        .then(data => {
            return data; // Devuelve los datos del paciente
        })
        .catch(error => {
            console.error('Error:', error);
            throw error;
        });
}

// Función para obtener datos completos del odontólogo por ID
function obtenerDatosOdontologo(id) {
    const url = `http://localhost:8080/odontologos/listar/${id}`;
    return fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos del odontólogo con ID ${id}`);
            }
            return response.json();
        })
        .then(data => {
            return data; // Devuelve los datos del odontólogo
        })
        .catch(error => {
            console.error('Error:', error);
            throw error; // Propaga el error para manejarlo fuera de esta función
        });
}

// Función para llenar el dropdown de pacientes
function llenarDropdownPacientes() {
    const pacienteSelect = document.getElementById('paciente-select');
    fetch('http://localhost:8080/pacientes/listar')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener la lista de pacientes.');
            }
            return response.json();
        })
        .then(data => {
            pacienteSelect.innerHTML = '<option value="">Selecciona un paciente...</option>';
            data.forEach(paciente => {
                const option = document.createElement('option');
                option.value = paciente.id;
                option.textContent = `${paciente.nombre} ${paciente.apellido}`;
                pacienteSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('Error', 'No se pudo cargar la lista de pacientes.');
        });
}

// Función para llenar el dropdown de odontólogos
function llenarDropdownOdontologos() {
    const odontologoSelect = document.getElementById('odontologo-select');
    fetch('http://localhost:8080/odontologos/listar')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener la lista de odontólogos.');
            }
            return response.json();
        })
        .then(data => {
            odontologoSelect.innerHTML = '<option value="">Selecciona un odontólogo...</option>';
            data.forEach(odontologo => {
                const option = document.createElement('option');
                option.value = odontologo.id;
                option.textContent = `${odontologo.nombre} ${odontologo.apellido}`;
                odontologoSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('Error', 'No se pudo cargar la lista de odontólogos.');
        });
}

// Función para obtener el ID del paciente seleccionado en el dropdown
function obtenerIdPacienteSeleccionado() {
    const pacienteSelect = document.getElementById('paciente-select');
    return pacienteSelect.value;
}

// Función para obtener el ID del odontólogo seleccionado en el dropdown
function obtenerIdOdontologoSeleccionado() {
    const odontologoSelect = document.getElementById('odontologo-select');
    return odontologoSelect.value;
}

// Función para obtener la fecha y hora ingresada por el usuario
function obtenerFechaYHora() {
    const fechaYHoraInput = document.getElementById('fecha-y-hora');
    return fechaYHoraInput.value;
}

// Función para registrar el turno
function registrarTurno() {
    const pacienteId = obtenerIdPacienteSeleccionado();
    const odontologoId = obtenerIdOdontologoSeleccionado();
    const fechaYHora = obtenerFechaYHora();

    if (!pacienteId || !odontologoId || !fechaYHora) {
        showAlert('Error', 'Por favor selecciona paciente, odontólogo y fecha y hora.');
        return;
    }

    // Obtener datos completos del paciente y del odontólogo por sus respectivos IDs
    Promise.all([
            obtenerDatosPaciente(pacienteId),
            obtenerDatosOdontologo(odontologoId)
        ])
        .then(result => {
            const paciente = result[0];
            const odontologo = result[1];

            // Construir el objeto de datos para enviar al endpoint de registro
            const data = {
                pacienteSalidaDto: {
                    id: paciente.id,
                    nombre: paciente.nombre,
                    apellido: paciente.apellido,
                    dni: paciente.dni,
                    fechaIngreso: paciente.fechaIngreso,
                    domicilioSalidaDto: paciente.domicilioSalidaDto
                },
                odontologoSalidaDto: {
                    id: odontologo.id,
                    numeroMatricula: odontologo.numeroMatricula,
                    nombre: odontologo.nombre,
                    apellido: odontologo.apellido
                },
                fechaYHora: fechaYHora,
            };

            // Realizar la solicitud POST al endpoint de registro de turnos
            const url = 'http://localhost:8080/turnos/registrar';
            fetch(url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                })
                .then(response => {
                    if (response.ok) {
                        showAlert('Éxito', 'El turno se registró correctamente.');
                        window.location.href = './turnos.html';
                    } else if(response.status === 400) {
                        response.json().then((errorData) => {
                            showAlert("Error", "Error al guardar el turno: " + errorData.fechaYHora);
                        });
                    }
                    else {
                        throw new Error('Error al registrar el turno.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showAlert('Error', 'No se pudo registrar el turno.');
                });
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('Error', 'No se pudieron obtener los datos completos de paciente u odontólogo.');
        });
}

// Función para mostrar alertas
function showAlert(title, message) {
    alert(`${title}: ${message}`);
}

// Cargar las opciones de pacientes y odontólogos al cargar la página
document.addEventListener('DOMContentLoaded', () => {
    llenarDropdownPacientes();
    llenarDropdownOdontologos();

    const guardarButton = document.getElementById('saveTurnoBtn');
    guardarButton.addEventListener('click', registrarTurno);
});