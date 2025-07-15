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

@WebServlet(name = "validaServlet", urlPatterns = "/valida")
public class ValidaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Recuperar datos del formulario
        String nombre = request.getParameter("nombre");
        String nacimiento = request.getParameter("nacimiento");
        String sueldo = request.getParameter("sueldo");

        StringBuilder messages = new StringBuilder();

        // Validar nombre
        if (nombre != null && !nombre.trim().isEmpty()) {
            messages.append("<p>Su nombre: <span style='color:green;'>")
                    .append(nombre)
                    .append("</span></p>");
        } else {
            messages.append("<p>Su nombre: <span style='color:red;'>No ingresado</span></p>");
        }

        // Validar fecha de nacimiento
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd"); // para parsear lo que llega del input
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // para mostrar al usuario


        parser.setLenient(false); // Validación estricta
        if (nacimiento != null && !nacimiento.trim().isEmpty()) {
            try {
                Date date = parser.parse(nacimiento);
                messages.append("<p>Su fecha de nacimiento: <span style='color:green;'>")
                        .append(formatter.format(date))
                        .append("</span></p>");
            } catch (Exception e) {
                messages.append("<p>Su fecha de nacimiento: <span style='color:red;'>Formato inválido (yyyy-MM-dd)</span></p>");

            }
        } else {
            messages.append("<p>Su fecha de nacimiento: <span style='color:red;'>No ingresada</span></p>");
        }

        // Validar sueldo
        if (sueldo != null && !sueldo.trim().isEmpty()) {
            try {
                double salario = Double.parseDouble(sueldo);
                if (salario >= 0) {
                    messages.append("<p>Su sueldo: <span style='color:green;'>")
                            .append(salario)
                            .append("</span></p>");
                } else {
                    messages.append("<p>Su sueldo: <span style='color:red;'>Debe ser un valor positivo</span></p>");
                }
            } catch (NumberFormatException e) {
                messages.append("<p>Su sueldo: <span style='color:red;'>Valor inválido</span></p>");
            }
        } else {
            messages.append("<p>Su sueldo: <span style='color:red;'>No ingresado</span></p>");
        }

        // Mostrar la respuesta HTML
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Resultado</title></head><body>");
            out.println("<h2>Resultado de Validación</h2>");
            out.println(messages.toString());
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Para que funcione igual con método POST
        doGet(request, response);
    }
}
