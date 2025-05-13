package si.um.feri.aiv.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import si.um.feri.aiv.ejb.PatientRemote;
import si.um.feri.aiv.vao.Patient;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/patientsWithoutDoctor")
public class PatientWithoutDoctorServlet extends HttpServlet {

    @EJB
    private PatientRemote patientRemote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Patient> patients = patientRemote.findWithoutDoctor();

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>Patients Without Doctor</title>");
        out.println("<link rel='stylesheet' type='text/css' href='" + req.getContextPath() + "/CSS/style.css'/>");
        out.println("</head><body>");

        out.println("<h3>Patients Without a Doctor</h3>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Name</th><th>Surname</th><th>Date of Birth</th><th>Email</th></tr>");

        for (Patient p : patients) {
            out.println("<tr>");
            out.println("<td>" + p.getId() + "</td>");
            out.println("<td>" + p.getName() + "</td>");
            out.println("<td>" + p.getSurname() + "</td>");
            out.println("<td>" + p.getDateOfBirth() + "</td>");
            out.println("<td>" + p.getEmail() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<br/><a href='index.html' class='command-button'>Back to Home</a>");
        out.println("</body></html>");

    }
}