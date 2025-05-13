package si.um.feri.aiv.dao;

import si.um.feri.aiv.vao.Doctor;
import java.util.List;

public interface DoctorDao {
    Doctor findById(Long id);
    List<Doctor> getAll();
    void add(Doctor doctor);
    void update(Doctor doctor);
    void delete(Long id);
}