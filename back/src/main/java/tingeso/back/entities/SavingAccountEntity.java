package tingeso.back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "savingAccountEntity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SavingAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    String rut;
    int seniority; // in years
    float balance;

    float retiredCash;
    float periodicDeposit;
    float recentlyRetiredCash;


    int negativePoints = 0;
    int typeCapacity;
}
