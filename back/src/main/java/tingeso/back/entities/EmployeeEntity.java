package tingeso.back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EmployeeEntity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    // Attributes related to personal information
    private String firstName;
    private String middleName;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private String phone;
}
