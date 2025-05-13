package si.um.feri.aiv.vao;

import java.io.Serializable;

import java.time.LocalDate;



public class Patient implements Serializable {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate dateOfBirth;
    private String details;
    private String doctor;
    private static long idCounter = 0;
    public Patient() {
        this.id = ++idCounter;
    }

    public Patient(Long id, String name, String surname, String email, LocalDate dateOfBirth, String details, String doctor) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
        this.doctor = doctor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }


}
