package si.um.feri.aiv.dao;

import jakarta.ejb.Local;
import si.um.feri.aiv.vao.Patient;
import java.util.List;

@Local
public interface PatientDao {
    List<Patient> getAll();
    void add(Patient p);
    void removeById(long id);
    Patient findById(long id);
    long countPatientsByDoctorId(long doctorId);
}