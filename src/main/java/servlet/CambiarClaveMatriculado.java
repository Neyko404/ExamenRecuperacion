/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.MatriculadosJpaController;
import dto.Matriculados;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author DELL
 */
@WebServlet("/CambiarClaveMatriculado")
public class CambiarClaveMatriculado extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Matriculados usuario = (Matriculados) session.getAttribute("usuario");

        response.setContentType("text/plain");

        if (usuario == null) {
            response.getWriter().write("Debe iniciar sesión primero.");
            return;
        }

        String claveActual = request.getParameter("actual");
        String nuevaClave = request.getParameter("nueva");

        if (!BCrypt.checkpw(claveActual, usuario.getPassMedi())) {
            response.getWriter().write("La clave actual es incorrecta.");
            return;
        }

        String nuevaHash = BCrypt.hashpw(nuevaClave, BCrypt.gensalt());

        // Actualizar en BD
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_ExamenRecuperacion_war_1.0-SNAPSHOTPU");
        MatriculadosJpaController ctrl = new MatriculadosJpaController(emf);

        try {
            usuario.setPassMedi(nuevaHash);
            ctrl.edit(usuario);
            response.getWriter().write("La clave fue cambiada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error al cambiar la clave.");
        }
    }
}
