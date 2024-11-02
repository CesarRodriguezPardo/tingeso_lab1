package tingeso.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.back.entities.CustomerEntity;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.entities.SavingAccountEntity;
import tingeso.back.repositories.CustomerRepository;
import tingeso.back.repositories.CustomerWorksheetRepository;
import tingeso.back.repositories.SavingAccountRepository;

@Service
public class EmployeeService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @Autowired
    private CustomerWorksheetRepository worksheetRepository;

    public void setSeniority(String rut, int seniority) {
        SavingAccountEntity savingAccount = savingAccountRepository.findByRut(rut);
        savingAccount.setSeniority(seniority);
        savingAccountRepository.save(savingAccount);
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

    public void verifyCostumer(String rut){
        CustomerEntity costumer = customerRepository.findByRut(rut);
        costumer.setVerified(true);
        customerRepository.save(costumer);
    }
}
