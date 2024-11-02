package tingeso.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tingeso.back.services.CustomerService;
import tingeso.back.services.EmployeeService;

@Controller
@RequestMapping("/api/v1/employee")
@CrossOrigin

public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;


    @PostMapping("/setDicom/{rut}/{dicom}")
    public ResponseEntity<Void> setDicom(@PathVariable String rut, @PathVariable boolean dicom){
        employeeService.setDicom(rut, dicom);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setTotalDebts/{rut}/{totalDebts}")
    public ResponseEntity<Void> setDicom(@PathVariable String rut, @PathVariable float totalDebts){
        employeeService.setTotalDebts(rut, totalDebts);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setDicom/{rut}/{independentJob}")
    public ResponseEntity<Void> setIndependentJob(@PathVariable String rut, @PathVariable boolean independentJob){
        employeeService.setIndependentJob(rut, independentJob);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setJobSeniority/{rut}/{jobSeniority}")
    public ResponseEntity<Void> setJobSeniority(@PathVariable String rut, @PathVariable int jobSeniority){
        employeeService.setJobSeniority(rut, jobSeniority);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setSalary/{rut}/{salary}")
    public ResponseEntity<Void> setSalary(@PathVariable String rut, @PathVariable int salary){
        employeeService.setSalary(rut, salary);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setSeniority/{rut}/{seniority}")
    public ResponseEntity<Void> setSeniority(@PathVariable String rut, @PathVariable int seniority){
        employeeService.setSeniority(rut, seniority);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/setBalance/{rut}/{balance}")
    public ResponseEntity<Void> setBalance(@PathVariable String rut, @PathVariable int balance){
        employeeService.setBalance(rut, balance);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verifyCostumer/{rut}")
    public ResponseEntity<Void> verifyCostumer(@PathVariable String rut){
        employeeService.verifyCostumer(rut);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        customerService.deleteByRut(id);
        return ResponseEntity.noContent().build();
    }
}
