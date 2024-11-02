package tingeso.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "FirstHomeCreditEntity")

public class FirstHomeCreditEntity extends CreditEntity{
    private int type = 1;
    private int maximumTerm = 30;
    private float minInterest = (float) 3.5;
    private float maxInterest = (float) 5.0;
    private int maximumFinancing = 80;
    private String documentaryRequirements = ("1, 2, 3");
}
