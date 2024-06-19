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
      .catch((error) => {
        console.error("Hubo un problema con la petición Fetch:", error);
      });
  };

  const createOdt = document.getElementById("odtCreateBtn");
  createOdt.addEventListener("click", () => {
    window.location.href = "./odontologos.form.html";
    console.log("Esta es la pagina del form para crear odontologos");
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
        editOdt(odontologoId);
      });
    });
  };

  const deleteOdt = (id) => {
    console.log(`Deleting odontologo with id: ${id}`);
    fetch(`http://localhost:8080/odontologos/eliminar/?id=${id}`, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Success:", data);
        listOdontologos();
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };

  const editOdt = (id) => {
    console.log(`Editing odontologo with id: ${id}`);
    window.location.href = `./odontologos.form.html`;

    if (id) {
      fetch(`http://localhost:8080/odontologos/listar/${id}`)
        .then((response) => response.json())
        .then((data) => {
          document.getElementById("odontologo-id").value = data.id;
          document.getElementById("first-name").value = data.nombre;
          document.getElementById("last-name").value = data.apellido;
          document.getElementById("matricula").value = data.numeroMatricula;
        })
        .catch((error) =>
          console.error("Error al cargar los datos del odontólogo:", error)
        );
    }

    const saveButton = document.getElementById("saveOdtBtn");
    saveButton.addEventListener("click", (event) => {
      event.preventDefault();

      const updatedOdontologo = {
        numeroMatricula: document.getElementById("matricula").value,
        nombre: document.getElementById("first-name").value,
        apellido: document.getElementById("last-name").value,
      };

      fetch(`http://localhost:8080/odontologos/actualizar/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedOdontologo),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log("Odontólogo actualizado:", data);
          window.location.href = "./odontologos.html"; // Redirigir a la lista de odontólogos
        })
        .catch((error) =>
          console.error("Error al actualizar el odontólogo:", error)
        );
    });
  };

  listOdontologos();
});
