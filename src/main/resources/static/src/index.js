document.addEventListener("DOMContentLoaded", function () {
  const odontologosMain = document.getElementById("odt-main");
  const turnosMain = document.getElementById("tur-main");
  const pacientesMain = document.getElementById("pacientes-main");

  odontologosMain.addEventListener("click", () => {
      window.location.href = "./odontologos/odontologos.html";
      console.log("Esta es la página de odontólogos");
  });

  turnosMain.addEventListener("click", () => {
      window.location.href = "./turnos/turnos.html";
      console.log("Esta es la página de turnos");
  });

  pacientesMain.addEventListener("click", () => {
      window.location.href = "./pacientes/pacientes.html";
      console.log("Esta es la página de pacientes");
  });
});