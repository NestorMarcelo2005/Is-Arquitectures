package si.um.feri.aiv.ejb;

import jakarta.ejb.Remote;
import si.um.feri.aiv.vao.Patient;
import java.util.List;

@Remote
public interface PatientRemote {
    List<Patient> findWithoutDoctor();
    List<Patient> findWithDoctor();
}