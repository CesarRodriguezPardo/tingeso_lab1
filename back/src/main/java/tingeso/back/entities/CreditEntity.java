package tingeso.back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.List;

@Entity
@Table(name = "CreditEntity")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    // Attributes related to the characteristics of the credit
    private int requestedAmount;
    private int requestedTerm;
    private float interestRate;
    private int status;
    private int type;
    private String rejectedReason;
    // Attribute related to the userId that the user requested
    private String rut;
}
