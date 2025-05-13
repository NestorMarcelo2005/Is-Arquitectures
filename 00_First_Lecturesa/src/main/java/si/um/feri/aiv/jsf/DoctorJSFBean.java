package si.um.feri.aiv.jsf;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import si.um.feri.aiv.dao.DoctorDao;
import si.um.feri.aiv.vao.Doctor;

import java.io.Serializable;
import java.util.List;

@Named("doctorJSFBean")
@SessionScoped
public class DoctorJSFBean implements Serializable {

    @EJB
    private DoctorDao doctorDao;

    private Doctor doctor = new Doctor();
    private Long selectedDoctorId;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Long getSelectedDoctorId() {
        return selectedDoctorId;
    }

    public void setSelectedDoctorId(Long selectedDoctorId) {
        this.selectedDoctorId = selectedDoctorId;
    }

    public List<Doctor> getAllDoctors() {
        return doctorDao.getAll();
    }

    public void saveDoctor() {
        if (doctor.getId() == null) {
            doctorDao.add(doctor);
        } else {
            doctorDao.update(doctor);
        }
        doctor = new Doctor();
    }

    public void prepareEdit(Doctor d) {
        this.doctor = d;
    }

    public void confirmDelete(Long id) {
        selectedDoctorId = id;
    }

    public void deleteConfirmedDoctor() {
        if (selectedDoctorId != null) {
            doctorDao.delete(selectedDoctorId);
            selectedDoctorId = null;
        }
    }

    public void cancelDelete() {
        selectedDoctorId = null;
    }
}