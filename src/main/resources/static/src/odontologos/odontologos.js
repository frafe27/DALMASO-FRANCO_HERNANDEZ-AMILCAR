document.addEventListener("DOMContentLoaded", () => {
  const listOdontologos = () => {
    fetch("http://localhost:8080/odontologos/listar")
      .then((response) => response.json())
      .then((data) => {
        const odontologosList = document.getElementById("odontologos-list");
        odontologosList.innerHTML = data
          .map(
            (odontologo) => `
            <tr>
              <td class="py-2 text-center">${odontologo.nombre}</td>
              <td class="py-2 text-center">${odontologo.apellido}</td>
              <td class="py-2 text-center">${odontologo.numeroMatricula}</td>
              <td class="py-2 text-center"><button data-id="${odontologo.id}" class="text-red-500 dltOdt"><i class="fas fa-trash-alt"></i></button></td>
              <td class="py-2 text-center"><button data-id="${odontologo.id}" class="text-red-500 editOdt"><i class="fas fa-edit"></i></button></td>
            </tr>
          `
          )
          .join("");
        addEventListeners();
      })
      .catch((error) =>
        console.error("Hubo un problema con la petición Fetch:", error)
      );
  };

  const createOdt = document.getElementById("odtCreateBtn");
  createOdt.addEventListener("click", () => {
    window.location.href = "./odontologos.form.html";
  });

  const addEventListeners = () => {
    const deleteButtons = document.querySelectorAll(".dltOdt");
    const editButtons = document.querySelectorAll(".editOdt");

    deleteButtons.forEach((button) => {
      button.addEventListener("click", (event) => {
        const odontologoId = event.currentTarget.getAttribute("data-id");
        deleteOdt(odontologoId);
      });
    });

    editButtons.forEach((button) => {
      button.addEventListener("click", (event) => {
        const odontologoId = event.currentTarget.getAttribute("data-id");
        window.location.href = `./odontologos.form.html?id=${odontologoId}`;
      });
    });
  };

  const deleteOdt = (id) => {
    fetch(`http://localhost:8080/odontologos/eliminar?id=${id}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (response.ok) {
          listOdontologos();
        } else if (response.status === 400) {
          response.json().then((errorData) => {
            alert("Error al eliminar el odontólogo: " + errorData.mensaje);
          });
        } else {
          throw new Error(
            "Error al eliminar el odontólogo. Código de error: " +
              response.status
          );
        }
      })
      .catch((error) => {
        console.error(error); // Imprimir en la consola solo errores no controlados
      });
  };

  listOdontologos();
});
