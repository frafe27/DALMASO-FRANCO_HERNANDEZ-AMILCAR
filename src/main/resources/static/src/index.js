document.addEventListener("DOMContentLoaded", function () {
  const odontologosMain = document.getElementById("odt-main");
  const turnosMain = document.getElementById("tur-main");

  odontologosMain.addEventListener("click", () => {
    window.location.href = "./odontologos/odontologos.html";
    console.log("Esta es la pagina de odontologos");
  });

  turnosMain.addEventListener("click", () => {
    console.log("Esta es la pagina de turnos");
    window.location.href = "./turnos/turnos.html";
  });
});
