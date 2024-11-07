package tingeso.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.entities.UserEntity;
import tingeso.back.repositories.CustomerWorksheetRepository;
import tingeso.back.repositories.UserRepository;

@Service
public class EvaluateCreditService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerWorksheetRepository customerWorksheetRepository;

    public boolean R1feeAndIncomeRatio(double m, String rut){
        CustomerWorksheetEntity worksheetEntity = customerWorksheetRepository.findByRut(rut);

        double ratio = (m / worksheetEntity.getSalary()) * 100;
        return ratio > 35;
    }

    public boolean R2creditHistory(String rut){
        CustomerWorksheetEntity worksheetEntity = customerWorksheetRepository.findByRut(rut);

        return worksheetEntity.getDicom() || worksheetEntity.getLatePayment();
    }

    public boolean R3seniority(String rut){
        CustomerWorksheetEntity worksheetEntity = customerWorksheetRepository.findByRut(rut);
        if (worksheetEntity.getIndependentJob()){
            return worksheetEntity.getIndependentEvaluate();
        }else{
            return (worksheetEntity.getJobSeniority() < 1);
        }
    }

    public boolean R4debtIncome(String rut, double newPayment){
        CustomerWorksheetEntity worksheetEntity = customerWorksheetRepository.findByRut(rut);
        return ((worksheetEntity.getTotalDebts() + newPayment) > (worksheetEntity.getSalary() / 2));
    }

    public boolean R6maxAge(int n, String rut){
        UserEntity user = userRepository.findByRut(rut);
        return (user.getAge() + n) > 70;
    }

}
