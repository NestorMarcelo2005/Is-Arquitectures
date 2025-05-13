package si.um.feri.vao;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class RestClientJEEDemo {

    static final URI PATIENT_URI = URI.create("http://localhost:8080/sampleProject/api/patients");

    HttpClient client = HttpClient.newHttpClient();
    Jsonb jsonb = JsonbBuilder.create();

    void getAllPatients() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(PATIENT_URI)
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n== All Patients ==");

        if (response.statusCode() == 200) {
            try {
                Patient[] patients = jsonb.fromJson(response.body(), Patient[].class);

                Jsonb prettyJsonb = JsonbBuilder.newBuilder()
                        .withConfig(new JsonbConfig().withFormatting(true))
                        .build();

                String formattedJson = prettyJsonb.toJson(patients);
                System.out.println(formattedJson);

            } catch (Exception e) {
                System.out.println("Failed to parse JSON response:");
                System.out.println(response.body());
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to fetch patients. Status: " + response.statusCode());
            System.out.println("Body: " + response.body());
        }
    }

    void addPatient(Patient p) throws Exception {
        String json = jsonb.toJson(p);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(PATIENT_URI)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\n== Add Patient Response ==");
        System.out.println("Status: " + response.statusCode());
        System.out.println("Body: " + response.body());
    }

    public static void main(String[] args) throws Exception {
        RestClientJEEDemo client = new RestClientJEEDemo();

        Patient newPatient = new Patient();
        newPatient.setName("Martin");
        newPatient.setSurname("Krpan");
        newPatient.setEmail("martin@krpan.si");
        newPatient.setDateOfBirth(LocalDate.of(1990, 5, 10));
        newPatient.setDetails("Allergic to bees");

        client.addPatient(newPatient);
        client.getAllPatients();
    }
}
