document.addEventListener("DOMContentLoaded", () => {
  const listPacientes = () => {
    fetch("../info.json")
      .then((response) => response.json())
      .then((data) => {
        const pacientesList = document.getElementById("pacientes-list");

        const infoPac = data.pacienteEntradaDto;
        console.log(infoPac);
        pacientesList.innerHTML = infoPac
          .map(
            (paciente) => `
            <tr>
                <td class="py-2 text-center">${paciente.nombre}</td>
                <td class="py-2 text-center">${paciente.apellido}</td>
                <td class="py-2 text-center">${paciente.dni}</td>
                <td class="py-2 text-center">${paciente.fechaIngreso}</td>
                <td class="py-2 text-center">${paciente.domicilioEntradaDto.calle}, ${paciente.domicilioEntradaDto.localidad}, ${paciente.domicilioEntradaDto.provincia}</td>
                <td class="py-2 text-center"><button onclick="removePaciente()" class="text-red-500"><i class="fas fa-trash-alt"></i></button></td>
                <td class="py-2 text-center"><button onclick="removePaciente()" class="text-red-500"><i class="fas fa-edit"></i></button></td>
            </tr>
        `
          )
          .join("");
      })
      .catch((error) => {
        console.error("Hubo un problema con la petición Fetch:", error);
      });
  };

  const createPtc = document.getElementById("ptcCreateBtn");
  createPtc.addEventListener("click", () => {
    window.location.href = "./pacientes.form.html";
  });

  listPacientes();
});
