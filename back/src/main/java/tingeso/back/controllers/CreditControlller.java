package tingeso.back.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tingeso.back.entities.*;
import tingeso.back.services.CreditService;

@Controller
@RequestMapping("/api/v1/credit")
@CrossOrigin
public class CreditControlller {
    CreditService creditService;

    @GetMapping("/findByRut/{rut}")
    public ResponseEntity<CreditEntity> findByUserId(@PathVariable String rut){
        return ResponseEntity.ok().body(creditService.findByRut(rut));
    }
}
