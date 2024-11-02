package tingeso.back.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rut;

    @Lob
    private byte[] savingAccountFile;

    @Lob
    private byte[] worksheetFile;

    @Lob
    private byte[] incomeFile;

    @Lob
    private byte[] appraisalFile;

    @Lob
    private byte[] creditHistoryFile;

    @Lob
    private byte[] firstHomeFile;

    @Lob
    private byte[] businessPlanFile;

    @Lob
    private byte[] remodelingBudgetFile;

    @Lob
    private byte[] updatedAppraisalFile;
}
