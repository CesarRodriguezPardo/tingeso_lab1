package tingeso.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.entities.SavingAccountEntity;
import tingeso.back.services.SavingAccountService;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/savingAccount")

public class SavingAccountController {
    @Autowired
    private SavingAccountService savingAccountService;

    @GetMapping("/getAll")
    public ResponseEntity<List<SavingAccountEntity>> getAll(){
        return ResponseEntity.ok().body(savingAccountService.getAll());}

    @GetMapping("/findByRut/{rut}")
    public ResponseEntity<SavingAccountEntity> findByRut(String rut){
        return ResponseEntity.ok().body(savingAccountService.findByRut(rut));
    }
}
