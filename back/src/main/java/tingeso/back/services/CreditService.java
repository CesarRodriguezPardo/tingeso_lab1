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

        // Evaluating credit conditions
        if ( (evaluateCreditService.R1feeAndIncomeRatio(monthlyPayment, rut)) ) {
            credit.setRejectedReason("Rejected due to fee to income ratio");
            credit.setStatus(7);  // Rejected
            creditRepository.save(credit);
            return true;
        }

        if ( (evaluateCreditService.R2creditHistory(rut)) ) {
            credit.setRejectedReason("Rejected due to negative credit history");
            credit.setStatus(7);  // Rejected
            creditRepository.save(credit);
            return true;
        }

        if ( (evaluateCreditService.R3seniority(rut)) ) {
            credit.setRejectedReason("Rejected due to lack of employment seniority");
            credit.setStatus(7);  // Rejected
            creditRepository.save(credit);
            return true;
        }

        if ( (evaluateCreditService.R4debtIncome(rut, monthlyPayment)) ) {
            credit.setRejectedReason("Rejected due to debt to income ratio");
            credit.setStatus(7);  // Rejected
            creditRepository.save(credit);
            return true;
        }

        if ( (evaluateCreditService.R6maxAge(credit.getRequestedTerm(), rut)) ) {
            credit.setRejectedReason("Rejected due to maximum age for the requested term");
            credit.setStatus(7);  // Rejected
            creditRepository.save(credit);
            return true;
        }

        // If the savings account has low negative points
        savingAccountService.rateEvaluation(rut);
        SavingAccountEntity savingAccount = savingAccountService.findByRut(rut);

        if (savingAccount.getNegativePoints() < 2){
            credit.setRejectedReason("Rejected due to savings account evaluation");
            credit.setStatus(7);  // Rejected
            creditRepository.save(credit);
            return true;
        }

        // If not rejected, set status as pending
        credit.setStatus(1);  // Pending
        credit.setRejectedReason("Waiting evaluation.");
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

    public String getRejectedReason(Long id){
        CreditEntity credit = creditRepository.findById(id).get();
        return credit.getRejectedReason();
    }
}
