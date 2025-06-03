/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.google.gson.Gson;
import dao.MatriculadosJpaController;
import dto.Matriculados;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import java.text.SimpleDateFormat;

/**
 *
 * @author DELL
 */
@WebServlet("/MatriculadoServlet")
public class MatriculadoServlet extends HttpServlet {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_ExamenRecuperacion_war_1.0-SNAPSHOTPU");
    private final MatriculadosJpaController ctrl = new MatriculadosJpaController(emf);
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("listar".equals(accion)) {
            List<Matriculados> lista = ctrl.findMatriculadosEntities();

            List<Map<String, Object>> resultado = new ArrayList<>();
            for (Matriculados m : lista) {
                Map<String, Object> item = new HashMap<>();
                item.put("codiMedi", m.getCodiMedi());
                item.put("ndniMedi", m.getNdniMedi());
                item.put("appaMedi", m.getAppaMedi());
                item.put("apmaMedi", m.getApmaMedi());
                item.put("nombMedi", m.getNombMedi());
                item.put("fechNaciMedi", m.getFechNaciMedi().toString());
                item.put("logiMedi", m.getLogiMedi());
                resultado.add(item);
            }

            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(resultado));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Matriculados dto = gson.fromJson(request.getReader(), Matriculados.class);
        response.setContentType("text/plain");

        try {
            if (dto.getCodiMedi() == 0) {
                // Registro nuevo - se requiere contraseña
                String clave = dto.getPassMedi();
                if (clave == null || clave.trim().isEmpty()) {
                    response.getWriter().write("La contraseña es obligatoria para nuevos registros.");
                    return;
                }

                Matriculados nuevo = new Matriculados();
                nuevo.setNdniMedi(dto.getNdniMedi());
                nuevo.setAppaMedi(dto.getAppaMedi());
                nuevo.setApmaMedi(dto.getApmaMedi());
                nuevo.setNombMedi(dto.getNombMedi());
                nuevo.setFechNaciMedi(dto.getFechNaciMedi());
                nuevo.setLogiMedi(dto.getLogiMedi());
                nuevo.setPassMedi(BCrypt.hashpw(clave, BCrypt.gensalt())); // ✅ solo una vez

                ctrl.create(nuevo);
                response.getWriter().write("Registrado correctamente.");
            } else {
                // Edición sin cambiar contraseña
                Matriculados existente = ctrl.findMatriculados(dto.getCodiMedi());
                if (existente != null) {
                    existente.setNdniMedi(dto.getNdniMedi());
                    existente.setAppaMedi(dto.getAppaMedi());
                    existente.setApmaMedi(dto.getApmaMedi());
                    existente.setNombMedi(dto.getNombMedi());
                    existente.setFechNaciMedi(dto.getFechNaciMedi());
                    existente.setLogiMedi(dto.getLogiMedi());

                    ctrl.edit(existente);
                    response.getWriter().write("Editado correctamente.");
                } else {
                    response.getWriter().write("No se encontró el registro.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error al guardar los datos.");
        }
    }
}
