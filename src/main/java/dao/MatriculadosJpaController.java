/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dto.Matriculados;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author DELL
 */
public class MatriculadosJpaController implements Serializable {

    public MatriculadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_ExamenRecuperacion_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Matriculados matriculados) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(matriculados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matriculados matriculados) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            matriculados = em.merge(matriculados);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = matriculados.getCodiMedi();
                if (findMatriculados(id) == null) {
                    throw new NonexistentEntityException("The matriculados with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matriculados matriculados;
            try {
                matriculados = em.getReference(Matriculados.class, id);
                matriculados.getCodiMedi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matriculados with id " + id + " no longer exists.", enfe);
            }
            em.remove(matriculados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Matriculados> findMatriculadosEntities() {
        return findMatriculadosEntities(true, -1, -1);
    }

    public List<Matriculados> findMatriculadosEntities(int maxResults, int firstResult) {
        return findMatriculadosEntities(false, maxResults, firstResult);
    }

    private List<Matriculados> findMatriculadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matriculados.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Matriculados findMatriculados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matriculados.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatriculadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matriculados> rt = cq.from(Matriculados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public Matriculados findByNdni(String ndni) {
    EntityManager em = getEntityManager();
    try {
        TypedQuery<Matriculados> q = em.createQuery(
            "SELECT m FROM Matriculados m WHERE m.ndniMedi = :ndni", Matriculados.class);
        q.setParameter("ndni", ndni);
        return q.getResultList().isEmpty() ? null : q.getSingleResult();
    } finally {
        em.close();
    }
}

}
