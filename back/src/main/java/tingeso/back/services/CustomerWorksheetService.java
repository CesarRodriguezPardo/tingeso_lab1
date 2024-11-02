package tingeso.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.repositories.CustomerWorksheetRepository;

import java.util.List;

@Service
public class CustomerWorksheetService {
    @Autowired
    private CustomerWorksheetRepository customerWorksheetRepository;

    public List<CustomerWorksheetEntity> getAll(){return customerWorksheetRepository.findAll();};
    public CustomerWorksheetEntity findByRut(String rut){return customerWorksheetRepository.findByRut(rut);};
}
