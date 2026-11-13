// Ruta base de la API. Es relativa, así funciona igual en localhost y en Codespaces.
const API = "/api/productos";

const tabla = document.getElementById("tabla");
const formulario = document.getElementById("formulario");
const mensaje = document.getElementById("mensaje");

// Muestra un texto de éxito o de error bajo el formulario
function avisar(texto, esError = false) {
    mensaje.textContent = texto;
    mensaje.className = esError ? "error" : "";
}

// Escapa HTML para que un nombre como "<b>" no rompa la tabla (evita XSS)
function esc(valor) {
    const div = document.createElement("div");
    div.textContent = valor;
    return div.innerHTML;
}

// GET: pide la lista a la API y la dibuja en la tabla
async function cargarProductos() {
    const respuesta = await fetch(API);
    const productos = await respuesta.json();

    tabla.innerHTML = productos.map(p => `
        <tr>
            <td>${p.id}</td>
            <td>${esc(p.nombre)}</td>
            <td>${esc(p.marca)}</td>
            <td>${esc(p.categoria)}</td>
            <td>S/ ${Number(p.precio).toFixed(2)}</td>
            <td>${p.stock}</td>
        </tr>`).join("");
}

// POST: envía el formulario como JSON a la API
formulario.addEventListener("submit", async (evento) => {
    evento.preventDefault(); // evita que el navegador recargue la página

    const datos = Object.fromEntries(new FormData(formulario));
    datos.precio = Number(datos.precio);
    datos.stock = Number(datos.stock);

    const respuesta = await fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(datos)
    });

    if (respuesta.ok) {
        avisar("¡Producto guardado! 🌷");
        formulario.reset();
        cargarProductos();
    } else {
        const error = await respuesta.json();
        const detalle = error.detalles ? " " + Object.values(error.detalles).join(" · ") : "";
        avisar(error.mensaje + detalle, true);
    }
});

cargarProductos();
