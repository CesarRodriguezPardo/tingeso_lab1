package tingeso.back.services;

import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    // M es la cuota mensual que se va a pagar
    // P es el monto del prestamo
    // r es la tasa de interes mensual
    // n es el numero total de pagos // plazo / 12 / 100

    public double calculatePayment(double p, double interest, double term){
        double result;
        double r = interest / 100;
        double n = term * 12;

        double base =  Math.pow((1 + r), n);

        result = p * ((r * base) / (base - 1));

        return result;
    }

    public double calculateInsurance(double creditAmount, double n){
        double fireInsurance =  20.000;
        double desgravamentInsurance = creditAmount *  0.0003;
        double desgravamentInsuranceMonthly = desgravamentInsurance * (n*12);
        return fireInsurance + desgravamentInsuranceMonthly;
    }

    public double calculateAdministrationCost(double creditAmount){
        return creditAmount *  0.01;
    }

    public double calculateTotalCost(double M,
                                     double n,
                                     double insurance,
                                     double administrationCost){
        double monthlyFee = M + insurance;

        double toMonthlyFee = n * 12;

        return (monthlyFee * toMonthlyFee) + administrationCost;
    }
}
