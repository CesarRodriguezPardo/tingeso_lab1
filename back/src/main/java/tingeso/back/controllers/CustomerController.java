package tingeso.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tingeso.back.entities.CustomerEntity;
import tingeso.back.services.CustomerService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/customer")
@CrossOrigin

public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerEntity>> getAll(){
        return ResponseEntity.ok().body(customerService.getAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CustomerEntity> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(customerService.findById(id));
    }

    @GetMapping("/findByVerified/{verified}")
    public ResponseEntity<List<CustomerEntity>> findByVerified(@PathVariable boolean verified){
        return ResponseEntity.ok().body(customerService.findByVerified(verified));
    }

    @GetMapping("/findByRut/{rut}")
    public ResponseEntity<CustomerEntity> findByRut(@PathVariable String rut){
        return ResponseEntity.ok().body(customerService.findByRut(rut));
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<CustomerEntity> findByEmail(@PathVariable String email){
        return ResponseEntity.ok().body(customerService.findByEmail(email));
    }

    @GetMapping("/findByPhone/{phone}")
    public ResponseEntity<CustomerEntity> findByPhone(@PathVariable String phone){
        return ResponseEntity.ok().body(customerService.findByPhone(phone));
    }

    @PostMapping("/saveApply")
    public ResponseEntity<Boolean> saveApply(@RequestBody CustomerEntity customerEntity){
        return ResponseEntity.ok().body(customerService.saveApplication(customerEntity));
    }

    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password){
        CustomerEntity customer = customerService.login(email, password);
        if (customer != null){
            return ResponseEntity.ok().body(customer);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
        }
    }
}
