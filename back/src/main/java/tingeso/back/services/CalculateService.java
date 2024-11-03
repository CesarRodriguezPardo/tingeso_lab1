package tingeso.back.services;

import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    // M es la cuota mensual que se va a pagar
    // P es el monto del prestamo
    // r es la tasa de interes mensual
    // n es el numero total de pagos // plazo / 12 / 100

    public float calculatePayment(float p, float interest, float term){
        float result;
        float r = (interest / 100) * 100;
        float n = term * 12;

        float base = (float) Math.pow((1 + r), n);

        result = p * ((r * base) / (base - 1));

        return result;
    }

    public float calculateInsurance(float creditAmount, float n){
        float fireInsurance = (float) 20.000;
        float desgravamentInsurance = creditAmount * (float) 0.0003;
        float desgravamentInsuranceMonthly = desgravamentInsurance * (n*12);
        return fireInsurance + desgravamentInsuranceMonthly;
    }

    public float calculateAdministrationCost(float creditAmount){
        return creditAmount * (float) 0.01;
    }

    public float calculateTotalCost(float M,
                                    float n,
                                    float insurance,
                                    float administrationCost){
        float monthlyFee = M + insurance;

        float toMonthlyFee = n * 12;

        return (monthlyFee * toMonthlyFee) + administrationCost;
    }
}
