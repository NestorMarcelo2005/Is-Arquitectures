package si.um.feri.aiv.jsf;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.*;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import si.um.feri.aiv.dao.DoctorDao;
import si.um.feri.aiv.jms.DoctorSelectionMessage;
import si.um.feri.aiv.jms.DoctorSelectionSender;
import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.dao.PatientDao;
import si.um.feri.aiv.ejb.PatientRemote;
import si.um.feri.aiv.vao.Patient;
import java.io.Serializable;
import java.util.List;

@Named("patientJSFBean")
@SessionScoped
public class PatientJSFBean implements Serializable {

    @EJB
    private DoctorSelectionSender sender;

    private Long doctorId;
    @EJB
    private PatientDao dao;
    @EJB
    private PatientRemote patientRemote;
    private Patient patient;
    private Long selectedPatientId;
    private boolean editMode;
    @EJB
    private DoctorDao doctorDao;

    public PatientJSFBean(){
        patient = new Patient();
    }

    public void savePatient() {
        if (patient.getDoctor() != null) {
            Doctor assignedDoctor = doctorDao.findById(patient.getDoctor().getId());

            if (assignedDoctor != null) {
                long currentPatientCount = dao.countPatientsByDoctorId(assignedDoctor.getId());

                if (currentPatientCount >= assignedDoctor.getMaxPatients()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "The selected doctor has already reached the maximum number of patients (" +
                                            assignedDoctor.getMaxPatients() + "). Please select a different doctor.", null));
                    return;
                }

                patient.setDoctor(assignedDoctor);
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Selected doctor does not exist.", null));
                return;
            }
        }
        dao.add(patient);
        patient = new Patient();
        editMode = false;

    }

    public void prepareEdit(long id) {
        this.patient = dao.findById(id);
        this.editMode = true;
    }

    public void confirmDelete(long id) {
        selectedPatientId = id;
    }

    public void deleteConfirmedPatient() {
        if (selectedPatientId != null) {
            dao.removeById(selectedPatientId);
            selectedPatientId = null;
        }
    }

    public List<Patient> getPatients() {
        List<Patient> list = dao.getAll();
        System.out.println("Found patients: " + list.size());
        return list;
    }

    public void cancelDelete() {
        selectedPatientId = null;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public Long getSelectedPatientId() {
        return selectedPatientId;
    }

    public void setSelectedPatientId(Long selectedPatientId) {
        this.selectedPatientId = selectedPatientId;
    }

    public List<Patient> getAllPatients() {
        return dao.getAll();
    }

    public List<Patient> getPatientsWithDoctor() {
        return patientRemote.findWithDoctor();

    }

    public List<Patient> getPatientsWithoutDoctor() {
        return patientRemote.findWithoutDoctor();
    }

    public List<Doctor> getAllDoctors() {
        return doctorDao.getAll();
    }

}