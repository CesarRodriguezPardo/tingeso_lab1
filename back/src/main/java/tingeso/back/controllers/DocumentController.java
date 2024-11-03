package tingeso.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tingeso.back.entities.DocumentEntity;
import tingeso.back.services.DocumentService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/document")
@CrossOrigin("*")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/findByUserId/{userId}")
    ResponseEntity<DocumentEntity> findByUserId(@PathVariable String userId){
        return ResponseEntity.ok().body(documentService.findByUserId(userId));
    }

    @GetMapping("/getAll")
    ResponseEntity<List<DocumentEntity>> getAll(){
        return ResponseEntity.ok().body(documentService.getAll());
    }

    @GetMapping("/isSavingUploaded/{userId}")
    public ResponseEntity<Boolean> isSavingUploaded(@PathVariable String userId) {
        Boolean result = documentService.uploadedSaving(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/isWorksheetUploaded/{userId}")
    public ResponseEntity<Boolean> isWorksheetUploaded(@PathVariable String userId) {
        Boolean result = documentService.uploadedWorksheet(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/isIncomeUploaded/{userId}")
    public ResponseEntity<Boolean> isIncomeUploaded(@PathVariable String userId) {
        Boolean result = documentService.uploadedIncome(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/isAppraisalUploaded/{userId}")
    public ResponseEntity<Boolean> isAppraisalUploaded(@PathVariable String userId) {
        Boolean result = documentService.uploadedAppraisal(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/isCreditHistoryUploaded/{userId}")
    public ResponseEntity<Boolean> isCreditHistoryUploaded(@PathVariable String userId) {
        Boolean result = documentService.uploadedCreditHistory(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/isFirstHomeUploaded/{userId}")
    public ResponseEntity<Boolean> isFirstHomeUploaded(@PathVariable String userId) {
        Boolean result = documentService.uploadedFirstHome(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/isBusinessUploaded/{userId}")
    public ResponseEntity<Boolean> isBusinessUploaded(@PathVariable String userId) {
        Boolean result = documentService.uploadedBusiness(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/isRemodelingUploaded/{userId}")
    public ResponseEntity<Boolean> isRemodelingUploaded(@PathVariable String userId) {
        Boolean result = documentService.uploadedRemodeling(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/isUpdatedAppraisalUploaded/{userId}")
    public ResponseEntity<Boolean> isUpdatedAppraisalUploaded(@PathVariable String userId) {
        Boolean result = documentService.uploadedUpdatedAppraisal(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/isFinancialUploaded/{userId}")
    public ResponseEntity<Boolean> isFinancialUploaded(@PathVariable String userId) {
        Boolean result = documentService.uploadedFinancial(userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/uploadCustomerDocs/{userId}")
    ResponseEntity<Boolean> uploadCustomerDocs(
            @PathVariable String userId,
            @RequestParam MultipartFile savingAccountFile,
            @RequestParam MultipartFile worksheetFile) {
        return ResponseEntity.ok().body(documentService.uploadCustomerDocs(userId,
                savingAccountFile,
                worksheetFile));
    }

    @PostMapping("/uploadFirstCreditDocs/{userId}")
    ResponseEntity<Boolean> uploadFirstCreditDocs(
            @PathVariable String userId,
            @RequestParam MultipartFile incomeFile,
            @RequestParam MultipartFile appraisalFile,
            @RequestParam MultipartFile creditHistoryFile) {
        return ResponseEntity.ok().body(documentService.uploadFirstCreditDocs(userId,
                incomeFile,
                appraisalFile,
                creditHistoryFile));
    }

    @PostMapping("/uploadSecondCreditDocs/{userId}")
    ResponseEntity<Boolean> uploadSecondCreditDocs(
            @PathVariable String userId,
            @RequestParam MultipartFile incomeFile,
            @RequestParam MultipartFile appraisalFile,
            @RequestParam MultipartFile creditHistoryFile,
            @RequestParam MultipartFile firstHomeFile) {
        return ResponseEntity.ok().body(documentService.uploadSecondCreditDocs(userId,
                incomeFile,
                appraisalFile,
                creditHistoryFile,
                firstHomeFile));
    }

    @PostMapping("/uploadCommercialDocs/{userId}")
    ResponseEntity<Boolean> uploadCommercialDocs(
            @PathVariable String userId,
            @RequestParam MultipartFile financialStatement,
            @RequestParam MultipartFile incomeFile,
            @RequestParam MultipartFile appraisalFile,
            @RequestParam MultipartFile businessPlanFile) {
        return ResponseEntity.ok().body(documentService.uploadCommercialDocs(userId,
                financialStatement,
                incomeFile,
                appraisalFile,
                businessPlanFile));
    }

    @PostMapping("/uploadRemodelingDocs/{userId}")
    ResponseEntity<Boolean> uploadRemodelingDocs(
            @PathVariable String userId,
            @RequestParam MultipartFile incomeFile,
            @RequestParam MultipartFile remodelingBudgetFile,
            @RequestParam MultipartFile updatedAppraisalFile) {
        return ResponseEntity.ok().body(documentService.uploadRemodelingDocs(userId,
                incomeFile,
                remodelingBudgetFile,
                updatedAppraisalFile));
    }

    @GetMapping("/download/{userId}/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String userId, @PathVariable String fileName) {
        DocumentEntity document = documentService.findByUserId(userId);

        if (document == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] fileData;
        String downloadFileName;

        switch (fileName) {
            case "savingAccountFile":
                fileData = document.getSavingAccountFile();
                downloadFileName = "savingAccountFile.pdf";
                break;
            case "worksheetFile":
                fileData = document.getWorksheetFile();
                downloadFileName = "worksheetFile.pdf";
                break;
            case "incomeFile":
                fileData = document.getIncomeFile();
                downloadFileName = "incomeFile.pdf";
                break;
            case "appraisalFile":
                fileData = document.getAppraisalFile();
                downloadFileName = "appraisalFile.pdf";
                break;
            case "creditHistoryFile":
                fileData = document.getCreditHistoryFile();
                downloadFileName = "creditHistoryFile.pdf";
                break;
            case "firstHomeFile":
                fileData = document.getFirstHomeFile();
                downloadFileName = "firstHomeFile.pdf";
                break;
            case "businessPlanFile":
                fileData = document.getBusinessPlanFile();
                downloadFileName = "businessPlanFile.pdf";
                break;
            case "remodelingBudgetFile":
                fileData = document.getRemodelingBudgetFile();
                downloadFileName = "remodelingBudgetFile.pdf";
                break;
            case "updatedAppraisalFile":
                fileData = document.getUpdatedAppraisalFile();
                downloadFileName = "updatedAppraisalFile.pdf";
                break;
            case "financialStatement":
                fileData = document.getFinancialStatement();
                downloadFileName = "financialStatement.pdf";
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (fileData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(downloadFileName).build());

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }
}
