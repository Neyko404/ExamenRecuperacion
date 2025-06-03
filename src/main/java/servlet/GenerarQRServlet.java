/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
@WebServlet("/GenerarQR")
public class GenerarQRServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario"); // ej: logiMedi

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();

        // Este secret lo debes guardar en BD relacionado al usuario
        String secret = key.getKey();

        // Simula guardado (reemplaza con tu lógica de persistencia real)
        request.getSession().setAttribute("secret_" + usuario, secret);

        // URI para Google Authenticator
        String otpauthURL = "otpauth://totp/TuApp:" + usuario + "?secret=" + secret + "&issuer=TuApp";

        response.setContentType("text/plain");
        response.getWriter().write(otpauthURL);
    }
}
