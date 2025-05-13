package si.um.feri.aiv.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.aiv.dao.DoctorDao;
import si.um.feri.aiv.dao.PatientDao;
import si.um.feri.aiv.ejb.PatientRemote;
import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.Patient;
import java.util.List;

/**
 * REST resource for handling patient-related operations.
 * Base URI: /api/patients
 */
@Path("patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @EJB
    private PatientDao patientDao;

    @EJB
    private DoctorDao doctorDao;

    @EJB
    private PatientRemote patientRemote;

    @GET
    public List<Patient> getAllPatients() {
        return patientDao.getAll();
    }

    @GET
    @Path("/without-doctor")
    public List<Patient> getPatientsWithoutDoctor() {
        return patientRemote.findWithoutDoctor();
    }

    @GET
    @Path("/with-doctor")
    public List<Patient> getPatientsWithDoctor() {
        return patientRemote.findWithDoctor();
    }

    @POST
    public Response addPatient(Patient p) {
        // If a doctor is provided, validate and attach the entity
        if (p.getDoctor() != null && p.getDoctor().getId() != null) {
            Doctor assignedDoctor = doctorDao.findById(p.getDoctor().getId());
            if (assignedDoctor == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Doctor not found.").build();
            }

            // Check if doctor has room for more patients
            long count = patientDao.countPatientsByDoctorId(assignedDoctor.getId());
            if (count >= assignedDoctor.getMaxPatients()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Doctor has reached maximum number of patients.").build();
            }

            // Assign the doctor to the patient
            p.setDoctor(assignedDoctor);
        }

        // Save patient to the database
        patientDao.add(p);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/{patientId}/select-doctor/{doctorId}")
    public Response assignDoctor(
            @PathParam("patientId") long patientId,
            @PathParam("doctorId") long doctorId) {

        // Load patient and doctor from DB
        Patient patient = patientDao.findById(patientId);
        Doctor doctor = doctorDao.findById(doctorId);

        //verified if any exist
        if (patient == null || doctor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Patient or doctor not found.").build();
        }

        // Check if the doctor is full
        long count = patientDao.countPatientsByDoctorId(doctorId);
        if (count >= doctor.getMaxPatients()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Doctor has reached maximum number of patients.").build();
        }

        // Assign doctor to patient (but doesn't persist unless DAO handles it implicitly)
        patient.setDoctor(doctor);
        return Response.ok().build();
    }
}
