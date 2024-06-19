document.addEventListener("DOMContentLoaded", () => {
    // Obtener el ID del odontólogo desde la URL
    const urlParams = new URLSearchParams(window.location.search);
    const odontologoId = urlParams.get("id");
  
    if (odontologoId) {
      // Realizar la solicitud fetch para obtener los datos del odontólogo
      fetch(`http://localhost:8080/odontologos/listar/${odontologoId}`)
        .then((response) => {
          if (!response.ok) {
            throw new Error("Error al obtener los datos del odontólogo");
          }
          return response.json();
        })
        .then((data) => {
          // Verificar si los datos se obtuvieron correctamente
          console.log("Datos del odontólogo obtenidos:", data);
  
          // Asignar los datos a los campos del formulario
          document.getElementById("odontologo-id").value = data.id || "";
          document.getElementById("first-name").value = data.nombre || "";
          document.getElementById("last-name").value = data.apellido || "";
          document.getElementById("matricula").value = data.numeroMatricula || "";
        })
        .catch((error) => console.error("Error al cargar los datos del odontólogo:", error));
    }
  
    const saveButton = document.getElementById("saveOdtBtn");
    saveButton.addEventListener("click", (event) => {
      event.preventDefault();
  
      const odontologo = {
        nombre: document.getElementById("first-name").value,
        apellido: document.getElementById("last-name").value,
        numeroMatricula: document.getElementById("matricula").value,
      };
  
      const method = odontologoId ? "PUT" : "POST";
      const url = odontologoId ? `http://localhost:8080/odontologos/actualizar/${odontologoId}` : "http://localhost:8080/odontologos/registrar";
  
      fetch(url, {
        method: method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(odontologo),
      })
        .then((response) => {
          if (response.ok) {
            window.location.href = "./odontologos.html";
          } else {
            console.error("Error al guardar el odontólogo");
          }
        })
        .catch((error) => console.error("Error:", error));
    });
  });
  