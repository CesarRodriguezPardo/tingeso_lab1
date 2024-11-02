package tingeso.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.back.entities.*;
import tingeso.back.repositories.CreditRepository;

@Service
public class CreditService {
    @Autowired
    CreditRepository creditRepository;

    public CreditEntity findByRut(String userId){
        return creditRepository.findByRut(userId);
    }

    public Boolean saveCredit(CreditEntity credit){
        creditRepository.save(credit);
        return true;
    }
}
