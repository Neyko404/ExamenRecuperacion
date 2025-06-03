/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.MatriculadosJpaController;
import dto.Matriculados;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
@WebServlet("/LoginMatriculados")
public class LoginMatriculados extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ndni = request.getParameter("ndni");
        String clave = request.getParameter("clave");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_ExamenRecuperacion_war_1.0-SNAPSHOTPU");
        MatriculadosJpaController ctrl = new MatriculadosJpaController(emf);
        Matriculados usuario = ctrl.findByNdni(ndni);

        response.setContentType("text/plain");

        if (usuario != null && BCrypt.checkpw(clave, usuario.getPassMedi())) {
            request.getSession().setAttribute("usuario", usuario);
            response.getWriter().write("Login exitoso. Bienvenido " + usuario.getNombMedi());
        } else {
            response.getWriter().write("Credenciales incorrectas");
        }
    }
}
