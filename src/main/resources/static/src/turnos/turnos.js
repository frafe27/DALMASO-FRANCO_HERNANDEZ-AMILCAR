document.addEventListener("DOMContentLoaded", () => {
  const listTurnos = () => {
      // Obtener la lista de turnos
      fetch("http://localhost:8080/turnos/listar")
          .then((response) => response.json())
          .then((data) => {
              const turnosList = document.getElementById("turnos-list");
              turnosList.innerHTML = data
                  .map((turno) => `
                      <tr>
                          <td>
                              <strong>Paciente:</strong><br>
                              Nombre: ${turno.pacienteSalidaDto.nombre}<br>
                              Apellido: ${turno.pacienteSalidaDto.apellido}<br>
                              DNI: ${turno.pacienteSalidaDto.dni}<br>
                              Fecha de Ingreso: ${turno.pacienteSalidaDto.fechaIngreso}<br>
                              Domicilio: ${formatDomicilio(turno.pacienteSalidaDto.domicilioSalidaDto)}
                          </td>
                          <td>
                              <strong>Odontólogo:</strong><br>
                              Nombre: ${turno.odontologoSalidaDto.nombre}<br>
                              Apellido: ${turno.odontologoSalidaDto.apellido}<br>
                              Número de Matrícula: ${turno.odontologoSalidaDto.numeroMatricula}
                          </td>
                          <td>${new Date(turno.fechaYHora).toLocaleString()}</td>
                          <td>
                              <button data-id="${turno.id}" class="text-red-500 dltTurno"><i class="fas fa-trash-alt"></i></button>
                              <button data-id="${turno.id}" class="text-red-500 editTurno"><i class="fas fa-edit"></i></button>
                          </td>
                      </tr>
                  `).join("");
              addEventListeners();
          })
          .catch((error) => console.error("Hubo un problema con la petición Fetch:", error));
  };

  const addEventListeners = () => {
      // Event listeners para los botones de eliminar y editar turno
      const deleteButtons = document.querySelectorAll(".dltTurno");
      const editButtons = document.querySelectorAll(".editTurno");

      deleteButtons.forEach((button) => {
          button.addEventListener("click", (event) => {
              const turnoId = event.currentTarget.getAttribute("data-id");
              deleteTurno(turnoId);
          });
      });

      editButtons.forEach((button) => {
          button.addEventListener("click", (event) => {
              const turnoId = event.currentTarget.getAttribute("data-id");
              window.location.href = `./turnos.form.html?id=${turnoId}`;
          });
      });

      // Event listener para el botón de añadir turno
      const turnoCreateBtn = document.getElementById("tcrCreateBtn");
      turnoCreateBtn.addEventListener("click", () => {
          window.location.href = "./agregarTurno.html";
      });
  };

  const deleteTurno = (id) => {
      // Función para eliminar un turno
      fetch(`http://localhost:8080/turnos/eliminar/${id}`, {
          method: "DELETE",
      })
      .then((response) => {
          if (response.ok) {
              listTurnos();
          } else if (response.status === 400) {
              response.json().then((errorData) => {
                  alert("Error al eliminar el turno: " + errorData.mensaje);
              });
          } else {
              throw new Error("Error al eliminar el turno. Código de error: " + response.status);
          }
      })
      .catch((error) => console.error("Error:", error));
  };

  const formatDomicilio = (domicilio) => {
      // Función para formatear el domicilio
      if (!domicilio) {
          return '';
      }
      return `${domicilio.calle || ''} ${domicilio.numero || ''}, ${domicilio.localidad || ''}, ${domicilio.provincia || ''}`;
  };

  listTurnos();
});