package com.works.restcontrollers;

import com.works.entities.Fault;
import com.works.entities.User;
import com.works.services.RepairService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repair")
public class RepairRestController {

    final RepairService repairService;
    public RepairRestController(RepairService repairService) {
        this.repairService = repairService;
    }

    @PostMapping("/register")
    private ResponseEntity register(@RequestBody User user) {
        return repairService.register(user);
    }

    @PostMapping("/save")
    private ResponseEntity save(@RequestBody Fault fault) {
        return repairService.save(fault);
    }

    @GetMapping("/list")
    public ResponseEntity list() {
        return repairService.list();
    }
}
