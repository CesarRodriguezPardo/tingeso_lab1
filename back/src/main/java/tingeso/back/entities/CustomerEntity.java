package tingeso.back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "CustomerEntity")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    // Attributes related to personal information
    private String firstName;
    private String middleName;
    private String firstSurname;
    private String secondSurname;
    private String rut;
    private String email;
    private String password;
    private String phone;
    private int age;

    // Attributes related to the verification
    private boolean verified;

    // Attributes related to personal PDF information
    // falta por subir lo que seria la planilla de trabajo y también
    // la planilla de ahorros en PDF
}
