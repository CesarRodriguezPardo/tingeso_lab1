package tingeso.back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UserEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String middleName;
    private String firstSurname;
    private String secondSurname;
    private String rut;
    private String email;
    private String password;
    private String phone;
    private int age;

    private Boolean verified;
    private int role; // 1 for customer, 2 for employee
}
