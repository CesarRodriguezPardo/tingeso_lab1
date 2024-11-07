package tingeso.back.services;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.entities.DocumentEntity;
import tingeso.back.entities.SavingAccountEntity;
import tingeso.back.entities.UserEntity;
import tingeso.back.repositories.CustomerWorksheetRepository;
import tingeso.back.repositories.DocumentRepository;
import tingeso.back.repositories.SavingAccountRepository;
import tingeso.back.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerWorksheetRepository worksheetRepository;

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @Autowired
    private DocumentRepository documentRepository;


    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

    public UserEntity findById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get(); // Si el usuario está presente, devolverlo
        } else {
            return null; // O manejar el caso de no encontrar el usuario, por ejemplo, devolviendo null o lanzando una excepción
        }
    }


    public UserEntity findByRut(String rut){
        return userRepository.findByRut(rut);
    }
    public UserEntity findByEmail(String email){
        return userRepository.findByPhone(email);
    }
    public UserEntity findByPhone(String phone){
        return userRepository.findByPhone(phone);
    }

    public String getIdByRut(String rut){
        UserEntity user = userRepository.findByRut(rut);
        if (user != null){
            return Long.toString(user.getId());
        }
        return null;
    }

    public Boolean saveApplication(UserEntity user){
        if ((userRepository.findByRut(user.getRut()) != null) ||
                (userRepository.findByEmail(user.getEmail()) != null) ||
                (userRepository.findByPhone(user.getPhone()) != null)) {
            return false;
        }
        CustomerWorksheetEntity worksheetEntity = new CustomerWorksheetEntity();
        worksheetEntity.setRut(user.getRut());

        SavingAccountEntity savingAccountEntity = new SavingAccountEntity();
        savingAccountEntity.setRut(user.getRut());

        user.setVerified(false);
        userRepository.save(user);

        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setUserId(Long.toString(user.getId()));

        worksheetRepository.save(worksheetEntity);
        savingAccountRepository.save(savingAccountEntity);
        documentRepository.save(documentEntity);
        return true;
    }

    public void deleteByRut(Long id){

        userRepository.deleteById(id);
        CustomerWorksheetEntity worksheetEntity = worksheetRepository.findById(id).get();
        worksheetRepository.deleteById(worksheetEntity.getId());
        SavingAccountEntity savingEntity = savingAccountRepository.findById(id).get();
        savingAccountRepository.deleteById(savingEntity.getId());
        DocumentEntity documentEntity = documentRepository.findById(id).get();
        documentRepository.deleteById(documentEntity.getId());
    }

    public UserEntity login(String email, String password){
        return userRepository.findByEmailAndPassword(email, password);
    }
    public List<UserEntity> findByVerified(boolean verified){
        return userRepository.findByVerified(verified);}


    // employee

    public void setSeniority(String rut, int seniority) {
        SavingAccountEntity savingAccount = savingAccountRepository.findByRut(rut);
        savingAccount.setSeniority(seniority);
    }

    public void setBalance(String rut, float balance) {
        SavingAccountEntity savingAccount = savingAccountRepository.findByRut(rut);
        savingAccount.setBalance(balance);
        savingAccountRepository.save(savingAccount);
    }

    public void setDicom(String rut, boolean dicom) {
        CustomerWorksheetEntity worksheetEntity = worksheetRepository.findByRut(rut);
        worksheetEntity.setDicom(dicom);
        worksheetRepository.save(worksheetEntity);
    }

    public void setTotalDebts(String rut, float totalDebts) {
        CustomerWorksheetEntity worksheetEntity = worksheetRepository.findByRut(rut);
        worksheetEntity.setTotalDebts(totalDebts);
        worksheetRepository.save(worksheetEntity);
    }

    public void setIndependentJob(String rut, boolean independentJob) {
        CustomerWorksheetEntity worksheetEntity = worksheetRepository.findByRut(rut);
        worksheetEntity.setIndependentJob(independentJob);
        worksheetRepository.save(worksheetEntity);
    }

    public void setJobSeniority(String rut, int jobSeniority) {
        CustomerWorksheetEntity worksheetEntity = worksheetRepository.findByRut(rut);
        worksheetEntity.setJobSeniority(jobSeniority);
        worksheetRepository.save(worksheetEntity);
    }

    public void setSalary(String rut, int salary) {
        CustomerWorksheetEntity worksheetEntity = worksheetRepository.findByRut(rut);
        worksheetEntity.setSalary(salary);
        worksheetRepository.save(worksheetEntity);
    }

    public void setIndepentEvaluate(String rut, Boolean evaluate) {
        CustomerWorksheetEntity worksheetEntity = worksheetRepository.findByRut(rut);
        worksheetEntity.setIndependentEvaluate(evaluate);
        worksheetRepository.save(worksheetEntity);
    }

    public void setLatePayment(String rut, Boolean latePayment) {
        CustomerWorksheetEntity worksheetEntity = worksheetRepository.findByRut(rut);
        worksheetEntity.setLatePayment(latePayment);
        worksheetRepository.save(worksheetEntity);
    }

    public void setRetiredCash(String rut, float retiredCash) {
        SavingAccountEntity savingAccount  = savingAccountRepository.findByRut(rut);
        savingAccount.setRetiredCash(retiredCash);
        savingAccountRepository.save(savingAccount);
    }

    public void setPeriodicDeposit(String rut, float periodicDeposit) {
        SavingAccountEntity savingAccount  = savingAccountRepository.findByRut(rut);
        savingAccount.setPeriodicDeposit(periodicDeposit);
        savingAccountRepository.save(savingAccount);
    }

    public void setRecentlyRetiredCash(String rut, float recentlyRetiredCash) {
        SavingAccountEntity savingAccount  = savingAccountRepository.findByRut(rut);
        savingAccount.setRecentlyRetiredCash(recentlyRetiredCash);
        savingAccountRepository.save(savingAccount);
    }

    public void verifyCostumer(String rut){
        UserEntity costumer = userRepository.findByRut(rut);
        costumer.setVerified(true);
        userRepository.save(costumer);
    }

}
