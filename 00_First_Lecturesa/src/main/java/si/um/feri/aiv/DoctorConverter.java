package si.um.feri.aiv.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import si.um.feri.aiv.dao.DoctorDao;
import si.um.feri.aiv.vao.Doctor;

@FacesConverter(value = "doctorConverter", managed = true)
public class DoctorConverter implements Converter<Doctor> {

    @EJB
    private DoctorDao doctorDao;

    @Override
    public Doctor getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) return null;
        return doctorDao.findById(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Doctor doctor) {
        return (doctor != null && doctor.getId() != null) ? String.valueOf(doctor.getId()) : "";
    }
}
