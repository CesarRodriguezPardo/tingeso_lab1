package tingeso.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.back.entities.CustomerEntity;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.repositories.CustomerRepository;
import tingeso.back.repositories.CustomerWorksheetRepository;

@Service
public class EvaluateCreditService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerWorksheetRepository customerWorksheetRepository;


    public boolean R1feeAndIncomeRatio(float m, String rut){
        CustomerWorksheetEntity worksheetEntity = customerWorksheetRepository.findByRut(rut);

        float ratio = (m / worksheetEntity.getSalary()) * 100;
        return ratio > 35;
    }

    public boolean R2creditHistory(String rut,
                                   boolean latePayment){
        CustomerWorksheetEntity worksheetEntity = customerWorksheetRepository.findByRut(rut);


        return (worksheetEntity.getDicom()) || (latePayment);
    }

    public boolean R3seniority(String rut, boolean independentEvaluate){
        CustomerWorksheetEntity worksheetEntity = customerWorksheetRepository.findByRut(rut);
        if (worksheetEntity.getIndependentJob()){
            return independentEvaluate;
        }else{
            return (worksheetEntity.getJobSeniority() < 1);
        }
    }

    public boolean R4debtIncome(String rut){
        CustomerWorksheetEntity worksheetEntity = customerWorksheetRepository.findByRut(rut);
        return (worksheetEntity.getTotalDebts() > (worksheetEntity.getSalary() / 2));
    }

        /*/
    R5 es el monto maximo de financiamiento...
     */

    public boolean R6maxAge(int n, String rut){
        CustomerEntity customer = customerRepository.findByRut(rut);

        return (customer.getAge() + n) > 70;
    }

}
