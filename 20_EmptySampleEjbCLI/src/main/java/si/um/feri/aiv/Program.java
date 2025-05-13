package si.um.feri.aiv;

import si.um.feri.aiv.ejb.PatientRemote;
import si.um.feri.aiv.vao.Patient;

import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Program {

    public static void main(String[] args) throws Exception {
        PatientRemote patient;
        Properties props=new Properties();
        props.put("java.naming.factory.initial","org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put("java.naming.provider.url","http-remoting://127.0.0.1:8080");
        props.put("jboss.naming.client.ejb.context","true");
        props.put("java.naming.factory.url.pkgs","org.jboss.ejb.client.naming");
        InitialContext ctx=new InitialContext(props);

        PatientRemote patientRemote = (PatientRemote) ctx.lookup("sampleProject/PatientRemoteBean!si.um.feri.aiv.ejb.PatientRemote");

        List<Patient> patients = patientRemote.findWithoutDoctor();

        for (Patient p : patients) {
            System.out.println("Name: " + p.getName());
            System.out.println("Surname: " + p.getSurname());
            System.out.println("-");
        }




    }

}