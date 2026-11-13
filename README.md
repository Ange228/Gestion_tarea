# 🌸 Rosa Boutique — API REST con Spring Boot

Práctica de Calidad de Software. API REST de una tienda de cosméticos con un
**GET** y un **POST** sobre objetos `Producto` de 6 campos:
`id`, `nombre`, `marca`, `categoria`, `precio`, `stock`.

## Arquitectura por capas

```
Cliente (Postman / navegador)
        │  JSON sobre HTTP
        ▼
controller/   ProductoController   → recibe la petición y devuelve el código HTTP
        ▼
service/      ProductoService      → lógica de negocio
        ▼
repository/   ProductoRepository   → acceso a datos (Spring Data JPA)
        ▼
model/        Producto (@Entity)   → tabla "productos" en H2

dto/          ProductoRequest      → entrada del POST (con validaciones)
              ProductoResponse     → salida JSON
exception/    GlobalExceptionHandler → errores 400 / 404 con formato uniforme
config/       DatosIniciales       → 4 productos de ejemplo al arrancar
static/       index.html + css + js → interfaz rosada
```

Cada capa solo habla con la de abajo. Todo el código está comentado en español.

## Ejecutar localmente (Visual Studio Code / terminal)

```bash
mvn spring-boot:run
```

Abrir <http://localhost:8080> (interfaz) o usar Postman.

## Desplegar en GitHub Codespaces

1. Crea un repositorio en GitHub y sube esta carpeta (`tienda-rosa`) como raíz:
   ```bash
   git init
   git add .
   git commit -m "Tienda Rosa API"
   git branch -M main
   git remote add origin https://github.com/TU_USUARIO/tienda-rosa.git
   git push -u origin main
   ```
2. En GitHub: botón verde **Code → Codespaces → Create codespace on main**.
   El archivo `.devcontainer/devcontainer.json` instala Java 17 y Maven solo.
3. En la terminal del Codespace:
   ```bash
   mvn spring-boot:run
   ```
4. Abre la pestaña **PORTS**, busca el puerto **8080** y haz clic derecho →
   **Port Visibility → Public** (si no, Postman recibirá 401).
5. Copia la URL del puerto (`https://xxxx-8080.app.github.dev`) y úsala en Postman.

## Endpoints

| Método | URL                                  | Descripción                        | Respuesta |
|--------|--------------------------------------|------------------------------------|-----------|
| GET    | `/api/productos`                     | Lista todos                        | 200       |
| GET    | `/api/productos?categoria=Labiales`  | Filtra por categoría               | 200       |
| GET    | `/api/productos/{id}`                | Un producto                        | 200 / 404 |
| POST   | `/api/productos`                     | Crea un producto                   | 201 / 400 |
| PUT    | `/api/productos/{id}`                | Reemplaza los datos de un producto | 200 / 400 / 404 |
| DELETE | `/api/productos/{id}`                | Elimina un producto                | 204 / 404 |

Ejemplo de cuerpo para el POST (`Content-Type: application/json`):

```json
{
  "nombre": "Gloss Brillo Fresa",
  "marca": "Candy Glow",
  "categoria": "Labiales",
  "precio": 19.90,
  "stock": 50
}
```

## Postman

Importa `postman/TiendaRosa.postman_collection.json`. Cambia la variable
`baseUrl` por la URL de Codespaces. Incluye casos de éxito y de error (400, 404).

## Guion sugerido para la exposición

1. Mostrar la estructura de capas y explicar el flujo Controller → Service → Repository.
2. `GET /api/productos` → lista inicial de 4 productos.
3. `POST /api/productos` → 201 Created + cabecera `Location`.
4. `GET /api/productos` otra vez → aparece el producto nuevo.
5. `POST` con datos inválidos → 400 con el detalle de cada campo.
6. `GET /api/productos/999` → 404 con mensaje claro.

> Nota: la base H2 está en memoria; al reiniciar la app los datos vuelven a los 4 iniciales.
