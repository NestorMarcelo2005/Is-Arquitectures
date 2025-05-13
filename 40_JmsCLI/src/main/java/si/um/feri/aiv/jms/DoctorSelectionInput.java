package si.um.feri.aiv.jms;

import java.util.Scanner;

public class DoctorSelectionInput {

    public static DoctorSelectionMessage readFromConsole() {
        Scanner scanner = new Scanner(System.in);
        DoctorSelectionMessage msg = new DoctorSelectionMessage();

        System.out.print("Enter Patient ID: ");
        msg.setPatientId(scanner.nextLong());
        scanner.nextLine(); // consume newline

        System.out.print("Enter Doctor ID: ");
        msg.setDoctorId(scanner.nextLong());
        scanner.nextLine(); // consume newline again

        return msg;
    }



}
