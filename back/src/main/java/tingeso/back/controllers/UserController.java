package tingeso.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tingeso.back.entities.UserEntity;
import tingeso.back.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/user")
@CrossOrigin

public class UserController {
    @Autowired
    private UserService userService;

    // para customer

    @GetMapping("/getAll")
    public ResponseEntity<List<UserEntity>> getAll(){
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping("/findByVerified/{verified}")
    public ResponseEntity<List<UserEntity>> findByVerified(@PathVariable boolean verified){
        return ResponseEntity.ok().body(userService.findByVerified(verified));
    }

    @GetMapping("/findByRut/{rut}")
    public ResponseEntity<UserEntity> findByRut(@PathVariable String rut){
        return ResponseEntity.ok().body(userService.findByRut(rut));
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<UserEntity> findByEmail(@PathVariable String email){
        return ResponseEntity.ok().body(userService.findByEmail(email));
    }

    @GetMapping("/findByPhone/{phone}")
    public ResponseEntity<UserEntity> findByPhone(@PathVariable String phone){
        return ResponseEntity.ok().body(userService.findByPhone(phone));
    }

    @GetMapping("/getIdByRut/{rut}")
    public ResponseEntity<String> getIdByRut(@PathVariable String rut){
        return ResponseEntity.ok().body(userService.getIdByRut(rut));
    }

    @PostMapping("/saveApply")
    public ResponseEntity<Boolean> saveApply(@RequestBody UserEntity user){
        return ResponseEntity.ok().body(userService.saveApplication(user));
    }

    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password){
        UserEntity user = userService.login(email, password);
        if (user != null){
            return ResponseEntity.ok().body(user);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
        }
    }

    /// para employee

    @PostMapping("/setDicom/{rut}/{dicom}")
    public ResponseEntity<Void> setDicom(@PathVariable String rut, @PathVariable Boolean dicom){
        userService.setDicom(rut, dicom);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setTotalDebts/{rut}/{totalDebts}")
    public ResponseEntity<Void> setDicom(@PathVariable String rut, @PathVariable float totalDebts){
        userService.setTotalDebts(rut, totalDebts);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setIndependentJob/{rut}/{independentJob}")
    public ResponseEntity<Void> setIndependentJob(@PathVariable String rut, @PathVariable Boolean independentJob){
        userService.setIndependentJob(rut, independentJob);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setJobSeniority/{rut}/{jobSeniority}")
    public ResponseEntity<Void> setJobSeniority(@PathVariable String rut, @PathVariable int jobSeniority){
        userService.setJobSeniority(rut, jobSeniority);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setSalary/{rut}/{salary}")
    public ResponseEntity<Void> setSalary(@PathVariable String rut, @PathVariable int salary){
        userService.setSalary(rut, salary);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setIndependentEvaluate/{rut}/{evaluate}")
    public ResponseEntity<Void> setIndepentEvaluate(@PathVariable String rut,
                                                    @PathVariable Boolean evaluate){
        userService.setIndepentEvaluate(rut, evaluate);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setLatePayment/{rut}/{latePayment}")
    public ResponseEntity<Void> setLatePayment(@PathVariable String rut,
                                                    @PathVariable Boolean latePayment){
        userService.setLatePayment(rut, latePayment);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setSeniority/{rut}/{seniority}")
    public ResponseEntity<Void> setSeniority(@PathVariable String rut, @PathVariable int seniority){
        userService.setSeniority(rut, seniority);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setBalance/{rut}/{balance}")
    public ResponseEntity<Void> setBalance(@PathVariable String rut, @PathVariable int balance){
        userService.setBalance(rut, balance);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setRetiredCash/{rut}/{retiredCash}")
    public ResponseEntity<Void> setBalance(@PathVariable String rut,
                                           @PathVariable float retiredCash){
        userService.setRetiredCash(rut, retiredCash);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setPeriodicDeposit/{rut}/{periodicDeposit}")
    public ResponseEntity<Void> setPeriodicDeposit(@PathVariable String rut,
                                           @PathVariable float periodicDeposit){
        userService.setPeriodicDeposit(rut, periodicDeposit);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setRecentlyRetiredCash/{rut}/{recentlyRetired}")
    public ResponseEntity<Void> recentlyRetiredCash(@PathVariable String rut,
                                                    @PathVariable float recentlyRetired){
        userService.setRecentlyRetiredCash(rut, recentlyRetired);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verifyCostumer/{rut}")
    public ResponseEntity<Void> verifyCostumer(@PathVariable String rut){
        userService.verifyCostumer(rut);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteByRut(id);
        return ResponseEntity.noContent().build();
    }
}
