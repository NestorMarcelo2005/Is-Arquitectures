    package si.um.feri.aiv.vao;

    import jakarta.persistence.*;
    import java.io.Serializable;
    import java.util.List;

    @Entity
    @Table(name = "doctor")
    public class Doctor implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String surname;
        private String email;

        @Column(name = "max_patients")
        private int maxPatients;

        @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
        private List<Patient> patients;

        public Doctor() {}

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getSurname() { return surname; }
        public void setSurname(String surname) { this.surname = surname; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public int getMaxPatients() { return maxPatients; }
        public void setMaxPatients(int maxPatients) { this.maxPatients = maxPatients; }

        public List<Patient> getPatients() { return patients; }
        public void setPatients(List<Patient> patients) { this.patients = patients; }
        @Transient
        public int getCurrentPatientCount() {
            return patients != null ? patients.size() : 0;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Doctor other = (Doctor) obj;
            return id != null && id.equals(other.id);
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }

    }
