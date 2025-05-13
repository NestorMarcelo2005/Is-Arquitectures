package si.um.feri.aiv.jms;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.Patient;

@Stateless
public class DoctorService {

    @PersistenceContext
    private EntityManager em;
    //verifies if the doctor have space to the new patient
    public boolean tryAssignPatient(Long patientId, Long doctorId) {
        Doctor doctor = em.find(Doctor.class, doctorId);
        Patient patient = em.find(Patient.class, patientId);

        if (doctor == null || patient == null) return false;
        if (doctor.getPatients().size() >= doctor.getMaxPatients()) return false;

        doctor.getPatients().add(patient);
        patient.setDoctor(doctor);

        em.merge(doctor);
        em.merge(patient);
        return true;
    }
    //search the doctor in the database
    public Doctor getDoctorById(Long id) {
        return em.find(Doctor.class, id);
    }
    //search the patient in the database
    public Patient getPatientById(Long id) {
        return em.find(Patient.class, id);
    }

}
