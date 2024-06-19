document.addEventListener("DOMContentLoaded", () => {
  const listTurnos = () => {
    fetch("../info.json")
      .then((response) => response.json())
      .then((data) => {
        const tunosList = document.getElementById("turnos-list");

        const infoTur = data.turnos;
        console.log(infoTur);
        tunosList.innerHTML = infoTur
          .map(
            (turno) => `
            <tr>
                <td class="py-2 text-center">Paciente ${turno.pacienteEntradaDto.nombre}</td>
                <td class="py-2 text-center">Odontologo ${turno.odontologoEntradaDto.nombre}</td>
                <td class="py-2 text-center">${turno.fechaYHora}</td>
                <td class="py-2 text-center"><button onclick="removeOdontologo()" class="text-red-500"><i class="fas fa-trash-alt"></i></button></td>
                <td class="py-2 text-center"><button onclick="removeOdontologo()" class="text-red-500"><i class="fas fa-edit"></i></button></td>
            </tr>
        `
          )
          .join("");
      })
      .catch((error) => {
        console.error("Hubo un problema con la petición Fetch:", error);
      });
  };

  const createTur = document.getElementById("turCreateBtn");
  createTur.addEventListener("click", () => {
    window.location.href = "./turnos.form.html";
  });

  listTurnos();
});
