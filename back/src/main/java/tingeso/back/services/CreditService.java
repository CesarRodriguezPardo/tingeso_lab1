package tingeso.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.back.entities.*;
import tingeso.back.repositories.CreditRepository;

import java.util.List;

@Service
public class CreditService {
    @Autowired
    CreditRepository creditRepository;

    @Autowired
    EvaluateCreditService evaluateCreditService;

    @Autowired
    CalculateService calculateService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerWorksheetService customerWorksheetService;

    @Autowired
    SavingAccountService savingAccountService;

    public List<CreditEntity> findByRut(String userId){
        return creditRepository.findByRut(userId);
    }
    public List<CreditEntity> findAll(){
        return creditRepository.findAll();
    }
    public CreditEntity findById(Long id){return (creditRepository.findById(id)).get();}

    public Boolean saveCredit(CreditEntity credit){
        String rut = credit.getRut();

        double monthlyPayment = calculateService.calculatePayment(credit.getRequestedAmount(),
                credit.getInterestRate(),
                credit.getRequestedTerm());

        if ( (evaluateCreditService.R1feeAndIncomeRatio(monthlyPayment, rut)) ||
                (evaluateCreditService.R2creditHistory(rut)) ||
                (evaluateCreditService.R3seniority(rut)) ||
                (evaluateCreditService.R4debtIncome(rut, monthlyPayment)) ||
                (evaluateCreditService.R6maxAge(credit.getRequestedTerm(), rut))){
            credit.setStatus(7);
            creditRepository.save(credit);
            return true;
        }

        savingAccountService.rateEvaluation(rut);
        SavingAccountEntity savingAccount = savingAccountService.findByRut(rut);

        if (savingAccount.getNegativePoints() < 2){
            credit.setStatus(7);
            creditRepository.save(credit);
            return true;
        }

        credit.setStatus(1);
        creditRepository.save(credit);
        return true;
    }

    public void setStatus(Long id, int status){
        CreditEntity credit = creditRepository.findById(id).get();
        credit.setStatus(status);
        creditRepository.save(credit);
    }

    public void setType(Long id, int status){
        CreditEntity credit = creditRepository.findById(id).get();
        credit.setType(status);
        creditRepository.save(credit);
    }
}
