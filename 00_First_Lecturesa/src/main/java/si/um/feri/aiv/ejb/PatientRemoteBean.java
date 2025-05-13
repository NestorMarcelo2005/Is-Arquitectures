package si.um.feri.aiv.ejb;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import si.um.feri.aiv.dao.PatientDao;
import si.um.feri.aiv.vao.Patient;

import java.util.List;

@Stateless
public class PatientRemoteBean implements PatientRemote {

    @EJB
    private PatientDao dao;

    @Override
    public List<Patient> findWithoutDoctor() {
        return dao.getAll().stream()
                .filter(p -> p.getDoctor() == null)
                .toList();
    }

    @Override
    public List<Patient> findWithDoctor() {
        return dao.getAll().stream()
                .filter(p -> p.getDoctor() != null)
                .toList();
    }
}
