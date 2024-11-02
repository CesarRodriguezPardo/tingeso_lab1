package tingeso.back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CustomerWorksheetEntity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerWorksheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    String rut;

    Boolean dicom;
    float totalDebts;

    private Boolean independentJob;
    private int jobSeniority;
    private float salary;
}
