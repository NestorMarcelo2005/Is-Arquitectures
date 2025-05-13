package si.um.feri.aiv.vao;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "patient")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;

    private LocalDate date_of_birth;

    private String details;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonbTransient
    private Doctor doctor;

    public Patient() {}

    public Patient(Long id, String name, String surname, String email, LocalDate date_of_birth, String details, Doctor doctor) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.details = details;
        this.doctor = doctor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateOfBirth() { return date_of_birth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.date_of_birth = dateOfBirth; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
}
