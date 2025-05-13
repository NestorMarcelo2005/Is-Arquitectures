package si.um.feri.aiv.jms;

import java.io.Serializable;

public class DoctorSelectionMessage implements Serializable {

    private Long patientId;
    private Long doctorId;
    private String patientEmail;
    private String doctorEmail;

    public DoctorSelectionMessage() {}

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getPatientEmail() { return patientEmail; }
    public void setPatientEmail(String patientEmail) { this.patientEmail = patientEmail; }

    public String getDoctorEmail() { return doctorEmail; }
    public void setDoctorEmail(String doctorEmail) { this.doctorEmail = doctorEmail; }

}
