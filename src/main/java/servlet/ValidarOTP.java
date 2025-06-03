/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import dto.Matriculados;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
@WebServlet("/ValidarOTP")
public class ValidarOTP extends HttpServlet {   

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String codigoStr = request.getParameter("codigo");

        // Verifica si el usuario está en sesión
        HttpSession session = request.getSession();
        Matriculados usuario = (Matriculados) session.getAttribute("usuario");

        if (usuario == null) {
            out.write("Sesión no válida. Por favor, vuelva a iniciar sesión.");
            return;
        }

        String secretKey = usuario.getSecretKey();
        if (secretKey == null || secretKey.isEmpty()) {
            out.write("No tienes un secretKey configurado.");
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            GoogleAuthenticator gAuth = new GoogleAuthenticator();

            boolean valido = gAuth.authorize(secretKey, codigo);

            if (valido) {
                out.write("Código válido. Autenticación completada.");
            } else {
                out.write("Código incorrecto. Intenta nuevamente.");
            }

        } catch (NumberFormatException e) {
            out.write("Formato de código inválido.");
        }
    }
}
