package tingeso.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.back.entities.DocumentEntity;
import tingeso.back.repositories.DocumentRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public DocumentEntity findByUserId(String userId){
        return documentRepository.findByUserId(userId);
    }

    public List<DocumentEntity> getAll(){
        return documentRepository.findAll();
    }

    public Boolean uploadedSaving(String userId){
        DocumentEntity documentEntity = findByUserId(userId);
        return documentEntity.getSavingAccountFile() != null;
    }

    public Boolean uploadedWorksheet(String userId){
        DocumentEntity documentEntity = findByUserId(userId);
        return documentEntity.getWorksheetFile() != null;
    }

    public Boolean uploadedIncome(String userId){
        DocumentEntity documentEntity = findByUserId(userId);
        return documentEntity.getIncomeFile() != null;
    }

    public Boolean uploadedAppraisal(String userId){
        DocumentEntity documentEntity = findByUserId(userId);
        return documentEntity.getAppraisalFile() != null;
    }

    public Boolean uploadedCreditHistory(String userId){
        DocumentEntity documentEntity = findByUserId(userId);
        return documentEntity.getCreditHistoryFile() != null;
    }

    public Boolean uploadedFirstHome(String userId){
        DocumentEntity documentEntity = findByUserId(userId);
        return documentEntity.getFirstHomeFile() != null;
    }

    public Boolean uploadedBusiness(String userId){
        DocumentEntity documentEntity = findByUserId(userId);
        return documentEntity.getBusinessPlanFile() != null;
    }

    public Boolean uploadedRemodeling(String userId){
        DocumentEntity documentEntity = findByUserId(userId);
        return documentEntity.getRemodelingBudgetFile() != null;
    }

    public Boolean uploadedUpdatedAppraisal(String userId){
        DocumentEntity documentEntity = findByUserId(userId);
        return documentEntity.getAppraisalFile() != null;
    }

    public Boolean uploadedFinancial(String userId){
        DocumentEntity documentEntity = findByUserId(userId);
        return documentEntity.getFinancialStatement() != null;
    }

    public Boolean uploadDoc(String userId, MultipartFile doc) {
        try {
            DocumentEntity document = documentRepository.findByUserId(userId);
            if (document == null) {
                return false;
            }

            if (doc != null && !doc.isEmpty()) {
                document.setSavingAccountFile(doc.getBytes());
            }
            documentRepository.save(document);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Boolean uploadCustomerDocs(String userId, MultipartFile savingAccountFile, MultipartFile worksheetFile) {
        try {
            DocumentEntity document = documentRepository.findByUserId(userId);
            if (document == null) {
                return false;
            }

            if (savingAccountFile != null && !savingAccountFile.isEmpty()) {
                document.setSavingAccountFile(savingAccountFile.getBytes());
            }

            if (worksheetFile != null && !worksheetFile.isEmpty()) {
                document.setWorksheetFile(worksheetFile.getBytes());
            }

            documentRepository.save(document);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Boolean uploadFirstCreditDocs(String userId,
                                         MultipartFile incomeFile,
                                         MultipartFile appraisalFile,
                                         MultipartFile creditHistoryFile) {
        try {
            DocumentEntity document = documentRepository.findByUserId(userId);
            if (document == null) {
                return false;
            }

            if (incomeFile != null && !incomeFile.isEmpty()) {
                document.setIncomeFile(incomeFile.getBytes());
            }

            if (appraisalFile != null && !appraisalFile.isEmpty()) {
                document.setAppraisalFile(appraisalFile.getBytes());
            }

            if (creditHistoryFile != null && !creditHistoryFile.isEmpty()) {
                document.setCreditHistoryFile(creditHistoryFile.getBytes());
            }

            documentRepository.save(document);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Boolean uploadSecondCreditDocs(String userId,
                                          MultipartFile incomeFile,
                                          MultipartFile appraisalFile,
                                          MultipartFile creditHistoryFile,
                                          MultipartFile firstHomeFile) {
        try {
            DocumentEntity document = documentRepository.findByUserId(userId);
            if (document == null) {
                return false;
            }

            if (incomeFile != null && !incomeFile.isEmpty()) {
                document.setIncomeFile(incomeFile.getBytes());
            }

            if (appraisalFile != null && !appraisalFile.isEmpty()) {
                document.setAppraisalFile(appraisalFile.getBytes());
            }

            if (creditHistoryFile != null && !creditHistoryFile.isEmpty()) {
                document.setCreditHistoryFile(creditHistoryFile.getBytes());
            }

            if (firstHomeFile != null && !firstHomeFile.isEmpty()) {
                document.setFirstHomeFile(firstHomeFile.getBytes());
            }

            documentRepository.save(document);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Boolean uploadCommercialDocs(String userId,
                                        MultipartFile financialStatement,
                                        MultipartFile incomeFile,
                                        MultipartFile appraisalFile,
                                        MultipartFile businessPlanFile) {
        try {
            DocumentEntity document = documentRepository.findByUserId(userId);
            if (document == null) {
                return false;
            }

            if (financialStatement != null && !financialStatement.isEmpty()) {
                document.setFinancialStatement(financialStatement.getBytes());
            }

            if (incomeFile != null && !incomeFile.isEmpty()) {
                document.setIncomeFile(incomeFile.getBytes());
            }

            if (appraisalFile != null && !appraisalFile.isEmpty()) {
                document.setAppraisalFile(appraisalFile.getBytes());
            }

            if (businessPlanFile != null && !businessPlanFile.isEmpty()) {
                document.setBusinessPlanFile(businessPlanFile.getBytes());
            }

            documentRepository.save(document);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Boolean uploadRemodelingDocs(String userId,
                                        MultipartFile incomeFile,
                                        MultipartFile remodelingBudgetFile,
                                        MultipartFile updatedAppraisalFile) {
        try {
            DocumentEntity document = documentRepository.findByUserId(userId);
            if (document == null) {
                return false;
            }

            if (incomeFile != null && !incomeFile.isEmpty()) {
                document.setIncomeFile(incomeFile.getBytes());
            }

            if (remodelingBudgetFile != null && !remodelingBudgetFile.isEmpty()) {
                document.setRemodelingBudgetFile(remodelingBudgetFile.getBytes());
            }

            if (updatedAppraisalFile != null && !updatedAppraisalFile.isEmpty()) {
                document.setUpdatedAppraisalFile(updatedAppraisalFile.getBytes());
            }

            documentRepository.save(document);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
