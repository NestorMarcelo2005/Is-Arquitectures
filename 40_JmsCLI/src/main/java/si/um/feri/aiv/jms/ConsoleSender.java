package si.um.feri.aiv.jms;

public class ConsoleSender {
    public static void main(String[] args) {
        try {
            DoctorSelectionMessage msg = DoctorSelectionInput.readFromConsole();
            new DoctorSelectionSender().send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
