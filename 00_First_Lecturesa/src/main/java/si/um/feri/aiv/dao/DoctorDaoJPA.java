package si.um.feri.aiv.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import si.um.feri.aiv.vao.Doctor;
import java.util.List;

@Stateless
public class DoctorDaoJPA implements DoctorDao {

    @PersistenceContext(unitName = "demo_pu")
    private EntityManager em;

    @Override
    public Doctor findById(Long id) {
        return em.find(Doctor.class, id);
    }

    @Override
    public List<Doctor> getAll() {
        return em.createQuery("SELECT d FROM Doctor d", Doctor.class).getResultList();
    }

    @Override
    public void add(Doctor doctor) {
        em.persist(doctor);
    }

    @Override
    public void update(Doctor doctor) {
        em.merge(doctor);
    }

    @Override
    public void delete(Long id) {
        Doctor d = em.find(Doctor.class, id);
        if (d != null) em.remove(d);
    }
}