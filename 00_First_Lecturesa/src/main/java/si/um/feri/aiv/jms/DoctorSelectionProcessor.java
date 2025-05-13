package si.um.feri.aiv.jms;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;
import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.Patient;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/DoctorSelectionQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class DoctorSelectionProcessor implements MessageListener {

    @EJB
    private DoctorService doctorService;

    @EJB
    private EmailService emailService;

    @Override
    public void onMessage(Message message) {
        try {
            // Ensure the received message is an ObjectMessage
            if (!(message instanceof ObjectMessage)) {
                System.err.println("⚠️ Received message is not of type ObjectMessage.");
                return;
            }

            // Extract the object from the JMS message
            ObjectMessage objMsg = (ObjectMessage) message;
            Object obj = objMsg.getObject();

            // Ensure the object is of the expected type: DoctorSelectionMessage
            if (!(obj instanceof DoctorSelectionMessage)) {
                System.err.println("⚠️ Received object is not of type DoctorSelectionMessage.");
                return;
            }

            // Cast the object to DoctorSelectionMessage to access the payload
            DoctorSelectionMessage selection = (DoctorSelectionMessage) obj;

            // Log basic information about the request
            System.out.println("📥 Processing doctor selection: patientId=" + selection.getPatientId()
                    + ", doctorId=" + selection.getDoctorId()
                    + ", patientEmail=" + selection.getPatientEmail()
                    + ", doctorEmail=" + selection.getDoctorEmail());

            // Check for missing IDs, which are required to proceed
            if (selection.getPatientId() == null || selection.getDoctorId() == null) {
                System.err.println("❌ Missing patient or doctor ID.");
                return;
            }

            // Attempt to assign the patient to the doctor (returns true if successful)
            boolean accepted = doctorService.tryAssignPatient(selection.getPatientId(), selection.getDoctorId());

            // Retrieve full entities to access their email addresses
            Doctor doctor = doctorService.getDoctorById(selection.getDoctorId());
            Patient patient = doctorService.getPatientById(selection.getPatientId());

            // If the assignment succeeded, send confirmation emails to both parties
            if (accepted) {
                System.out.println("✅ Assignment successful.");
                emailService.sendDoctorPatientConfirmation(patient.getEmail(), doctor.getEmail());
            }
            // Otherwise, notify the patient that the assignment failed
            else {
                System.out.println("❌ Assignment failed. Doctor may not exist or is full.");
                emailService.sendPatientFailureNotification(patient.getEmail(), doctor.getEmail());
            }

        } catch (JMSException e) {
            // Handle JMS-related exceptions
            System.err.println("❗ JMSException while processing message: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            System.err.println("❗ Unexpected exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
