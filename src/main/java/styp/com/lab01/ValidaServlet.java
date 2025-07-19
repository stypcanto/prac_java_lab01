package styp.com.lab01;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// Anotación que indica que esta clase es un Servlet accesible desde la URL /valida
@WebServlet(name = "validaServlet", urlPatterns = "/valida")
public class ValidaServlet extends HttpServlet {

    // Método que se ejecuta cuando se recibe una solicitud GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Recuperar los datos enviados por el formulario con método GET (nombre de los campos HTML)
        String nombre = request.getParameter("nombre");
        String nacimiento = request.getParameter("nacimiento");
        String sueldo = request.getParameter("sueldo");

        // Usamos un StringBuilder para construir los mensajes HTML que mostraremos al usuario
        StringBuilder messages = new StringBuilder();

        // ---------------- VALIDACIÓN DEL NOMBRE ----------------
        if (nombre != null && !nombre.trim().isEmpty()) {
            // Si el nombre no está vacío ni en blanco, lo mostramos en verde
            messages.append("<p>Su nombre: <span style='color:green;'>")
                    .append(nombre)
                    .append("</span></p>");
        } else {
            // Si no se ingresó nombre, se muestra en rojo
            messages.append("<p>Su nombre: <span style='color:red;'>No ingresado</span></p>");
        }

        // ---------------- VALIDACIÓN DE FECHA DE NACIMIENTO ----------------

        // Formato de entrada esperado: yyyy-MM-dd (como lo usa el input type="date")
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

        // Formato de salida para mostrar al usuario: dd/MM/yyyy
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        // Activar validación estricta de la fecha (no se permite 32/01/2024 por ejemplo)
        parser.setLenient(false);

        if (nacimiento != null && !nacimiento.trim().isEmpty()) {
            try {
                // Intentamos parsear la fecha que vino como string
                Date date = parser.parse(nacimiento);

                // Si es válida, la mostramos en formato bonito y en verde
                messages.append("<p>Su fecha de nacimiento: <span style='color:green;'>")
                        .append(formatter.format(date))
                        .append("</span></p>");
            } catch (Exception e) {
                // Si falla el parseo, mostramos mensaje de error
                messages.append("<p>Su fecha de nacimiento: <span style='color:red;'>Formato inválido (yyyy-MM-dd)</span></p>");
            }
        } else {
            // Si no se ingresó nada en la fecha
            messages.append("<p>Su fecha de nacimiento: <span style='color:red;'>No ingresada</span></p>");
        }

        // ---------------- VALIDACIÓN DEL SUELDO ----------------
        if (sueldo != null && !sueldo.trim().isEmpty()) {
            try {
                // Intentamos convertir el sueldo a número decimal
                double salario = Double.parseDouble(sueldo);

                if (salario >= 0) {
                    // Si es positivo, lo mostramos en verde
                    messages.append("<p>Su sueldo: <span style='color:green;'>")
                            .append(salario)
                            .append("</span></p>");
                } else {
                    // Si es negativo, mostramos advertencia en rojo
                    messages.append("<p>Su sueldo: <span style='color:red;'>Debe ser un valor positivo</span></p>");
                }
            } catch (NumberFormatException e) {
                // Si no se puede convertir a número, error
                messages.append("<p>Su sueldo: <span style='color:red;'>Valor inválido</span></p>");
            }
        } else {
            // Si no se ingresó sueldo
            messages.append("<p>Su sueldo: <span style='color:red;'>No ingresado</span></p>");
        }

        // ---------------- MOSTRAR RESULTADO EN HTML ----------------
        // Indicamos que el contenido de la respuesta será HTML con codificación UTF-8
        response.setContentType("text/html; charset=UTF-8");

        // Usamos PrintWriter para escribir la respuesta HTML al navegador
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Resultado</title></head><body>");
            out.println("<h2>Resultado de Validación</h2>");
            out.println(messages.toString()); // Mostramos los mensajes generados
            out.println("</body></html>");
        }
    }

    // Método que se ejecuta si se recibe la solicitud por método POST
    // Aquí simplemente redirigimos el POST al método doGet para reutilizar la lógica
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
