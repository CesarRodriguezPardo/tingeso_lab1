package tingeso.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.services.CustomerWorksheetService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/worksheet")
@CrossOrigin

public class CustomerWorksheetController {
    @Autowired
    private CustomerWorksheetService customerWorksheetService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerWorksheetEntity>> getAll(){
        return ResponseEntity.ok().body(customerWorksheetService.getAll());
    }

    @GetMapping("/findByRut/{rut}")
    public ResponseEntity<CustomerWorksheetEntity> findByRut(String rut){
        return ResponseEntity.ok().body(customerWorksheetService.findByRut(rut));
    }
}
