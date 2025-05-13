package si.um.feri.aiv.jms;

import jakarta.ejb.Stateless;

@Stateless
public class EmailService {

    public void sendDoctorPatientConfirmation(String patientEmail, String doctorEmail) {
        System.out.println("📧 Email to Patient (" + patientEmail + "): You have been successfully assigned to Doctor (" + doctorEmail + ").");
        System.out.println("📧 Email to Doctor (" + doctorEmail + "): You have a new patient (" + patientEmail + ").");
    }

    public void sendPatientFailureNotification(String patientEmail, String doctorEmail) {
        System.out.println("❌ Email to Patient (" + patientEmail + "): Sorry, Doctor (" + doctorEmail + ") cannot accept new patients at this time.");
    }
}
