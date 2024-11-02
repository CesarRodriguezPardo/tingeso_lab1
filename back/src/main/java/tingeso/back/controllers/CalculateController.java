package tingeso.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tingeso.back.services.CalculateService;

@Controller
@RequestMapping("/api/v1/calculate")
@CrossOrigin

public class CalculateController {
    @Autowired
    CalculateService calculateService;

    @GetMapping("/calculatePayment/{p}/{amount}/{n}")
    public ResponseEntity<?> calculatePayment(@PathVariable float p,
                                                  @PathVariable float amount,
                                                  @PathVariable float n){
        return ResponseEntity.ok().body(calculateService.calculatePayment(p, amount, n));
    }

    @GetMapping("/calculateInsurance/{amount}/{n}")
    public ResponseEntity<?> calculateInsurance(@PathVariable float amount,
                                              @PathVariable float n){
        return ResponseEntity.ok().body(calculateService.calculateInsurance(amount, n));
    }

    @GetMapping("/calculateAdministrationCost/{amount}")
    public ResponseEntity<?> calculateAdministrationCost(@PathVariable float amount){
        return ResponseEntity.ok().body(calculateService.calculateAdministrationCost(amount));
    }

    @GetMapping("/calculateTotal/{m}/{n}/{insurance}/{administration}")
    public ResponseEntity<?> calculateTotal(@PathVariable float m,
                                            @PathVariable float n,
                                            @PathVariable float insurance,
                                            @PathVariable float administration){
        return ResponseEntity.ok().body(calculateService.calculateTotalCost(m,
                n,
                insurance,
                administration));
    }
}
