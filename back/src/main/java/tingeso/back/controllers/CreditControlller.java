package tingeso.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tingeso.back.entities.*;
import tingeso.back.services.CreditService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/credit")
@CrossOrigin
public class CreditControlller {
    @Autowired
    CreditService creditService;

    @GetMapping("/findByRut/{rut}")
    public ResponseEntity<List<CreditEntity>> findByUserId(@PathVariable String rut){
        return ResponseEntity.ok().body(creditService.findByRut(rut));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CreditEntity>> findAll(){
        return ResponseEntity.ok().body(creditService.findAll());
    }

    @PostMapping("/saveCredit")
    public ResponseEntity<Boolean> saveCredit(@RequestBody CreditEntity credit){
        return ResponseEntity.ok().body(creditService.saveCredit(credit));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CreditEntity> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(creditService.findById(id));
    }

    @PostMapping("/setStatus/{id}/{status}")
    ResponseEntity<Void> setStatus(@PathVariable Long id, @PathVariable int status){
        creditService.setStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setType/{id}/{type}")
    ResponseEntity<Void> setType(@PathVariable Long id, @PathVariable int type){
        creditService.setType(id, type);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getRejectedReason/{id}")
    ResponseEntity<String> getRejectedReason(@PathVariable Long id){
        return ResponseEntity.ok().body(creditService.getRejectedReason(id));
    }
}
