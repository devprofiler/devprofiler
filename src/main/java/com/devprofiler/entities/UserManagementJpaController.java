
package com.devprofiler.entities;

import com.devprofiler.entities.UserManagement;
import com.devprofiler.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author PRanjan3
 */
public class UserManagementJpaController implements Serializable {

    public UserManagementJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserManagement userManagement) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(userManagement);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserManagement userManagement) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            userManagement = em.merge(userManagement);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = userManagement.getId();
                if (findUserManagement(id) == null) {
                    throw new NonexistentEntityException("The userManagement with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserManagement userManagement;
            try {
                userManagement = em.getReference(UserManagement.class, id);
                userManagement.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userManagement with id " + id + " no longer exists.", enfe);
            }
            em.remove(userManagement);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserManagement> findUserManagementEntities() {
        return findUserManagementEntities(true, -1, -1);
    }

    public List<UserManagement> findUserManagementEntities(int maxResults, int firstResult) {
        return findUserManagementEntities(false, maxResults, firstResult);
    }

    private List<UserManagement> findUserManagementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserManagement.class));
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

    public UserManagement findUserManagement(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserManagement.class, id);
        } finally {
            em.close();
        }
    }
    
     public UserManagement findUserManagementUidPwd(String username,String password) {
        EntityManager em = getEntityManager();
        try {
            Query q =  em.createQuery("SELECT u from UserManagement u where u.username=:username and u.password =:password",UserManagement.class)
                    .setParameter("username", username)
                    .setParameter("password", password);
            UserManagement singleResult = (UserManagement) q.getResultList().get(0);
            return singleResult;
        }catch(Exception ex ) {
            ex.printStackTrace();
            return null;
        }finally {
            em.close();
        }
    }

    public int getUserManagementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserManagement> rt = cq.from(UserManagement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public UserManagement findUserManagementUserName(String username) {
       EntityManager em = getEntityManager();
        try {
            Query q =  em.createQuery("SELECT u from UserManagement u where u.username=:username",UserManagement.class)
                    .setParameter("username", username);
                    
            UserManagement singleResult = (UserManagement) q.getSingleResult();
            return singleResult;
        }catch(Exception ex ) {
            ex.printStackTrace();
            
        }finally {
            em.close();
        }
        
        return null;
    }
    
}
