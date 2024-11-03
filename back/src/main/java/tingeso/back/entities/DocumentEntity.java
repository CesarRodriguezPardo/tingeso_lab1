package tingeso.back.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Lob
    private byte[] savingAccountFile; // cuenta de ahorros

    @Lob
    private byte[] worksheetFile; // planilla

    @Lob
    private byte[] incomeFile; // comprobante de ingresos

    @Lob
    private byte[] appraisalFile; // certificado de avalúo

    @Lob
    private byte[] creditHistoryFile; //historial crediticio

    @Lob
    private byte[] firstHomeFile; // escritura primera vivienda

    @Lob
    private byte[] businessPlanFile; // plan de negocios

    @Lob
    private byte[] remodelingBudgetFile; // presupuesto de remodelación

    @Lob
    private byte[] updatedAppraisalFile; // certificado avalúo actualizado

    @Lob
    private byte[] financialStatement; // estado financiero del negocio
}
