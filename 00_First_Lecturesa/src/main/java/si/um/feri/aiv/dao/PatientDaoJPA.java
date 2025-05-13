package si.um.feri.aiv.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import si.um.feri.aiv.vao.Patient;

import java.util.List;

@Stateless
public class PatientDaoJPA implements PatientDao {

    @PersistenceContext(unitName = "demo_pu")
    EntityManager em;

    @Override
    public void add(Patient p) {
        em.merge(p);
    }

    @Override
    public void removeById(long id) {
        Patient p = em.find(Patient.class, id);
        if (p != null) em.remove(p);
    }

    @Override
    public Patient findById(long id) {
        return em.find(Patient.class, id);
    }

    @Override
    public long countPatientsByDoctorId(long doctorId) {
        return em.createQuery(
                        "SELECT COUNT(p) FROM Patient p WHERE p.doctor.id = :docId", Long.class)
                .setParameter("docId", doctorId)
                .getSingleResult();
    }


    @Override
    public List<Patient> getAll() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }


}