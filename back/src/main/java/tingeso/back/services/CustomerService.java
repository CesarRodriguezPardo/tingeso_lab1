package tingeso.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.back.entities.CustomerEntity;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.entities.SavingAccountEntity;
import tingeso.back.repositories.CustomerRepository;
import tingeso.back.repositories.CustomerWorksheetRepository;
import tingeso.back.repositories.SavingAccountRepository;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerWorksheetRepository worksheetRepository;

    @Autowired
    private SavingAccountRepository savingAccount;

    public List<CustomerEntity> getAll(){
        return customerRepository.findAll();
    }
    public CustomerEntity findById(Long id){return customerRepository.findById(id).get();}
    public CustomerEntity findByRut(String rut){
        return customerRepository.findByRut(rut);
    }
    public CustomerEntity findByEmail(String email){
        return customerRepository.findByPhone(email);
    }
    public CustomerEntity findByPhone(String phone){
        return customerRepository.findByPhone(phone);
    }
    public Boolean saveApplication(CustomerEntity customerEntity){
        if ((customerRepository.findByRut(customerEntity.getRut()) != null) ||
                (customerRepository.findByEmail(customerEntity.getEmail()) != null) ||
                (customerRepository.findByPhone(customerEntity.getPhone()) != null)) {
            return false;
        }
        CustomerWorksheetEntity worksheetEntity = new CustomerWorksheetEntity();
        worksheetEntity.setRut(customerEntity.getRut());

        SavingAccountEntity savingAccountEntity = new SavingAccountEntity();
        savingAccountEntity.setRut(customerEntity.getRut());

        customerEntity.setVerified(false);
        customerRepository.save(customerEntity);
        worksheetRepository.save(worksheetEntity);
        savingAccount.save(savingAccountEntity);
        return true;
    }

    public void deleteByRut(Long id){
        customerRepository.deleteById(id);
    }
    public CustomerEntity login(String email, String password){
        return customerRepository.findByEmailAndPassword(email, password);
    }
    public List<CustomerEntity> findByVerified(boolean verified){
        return customerRepository.findByVerified(verified);}
}
