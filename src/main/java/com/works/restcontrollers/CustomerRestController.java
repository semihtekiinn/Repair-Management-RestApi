package com.works.restcontrollers;

import com.works.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    final CustomerService customerService;
    public CustomerRestController(CustomerService cService) {
        this.customerService = cService;
    }

    @GetMapping("/single")
    public ResponseEntity single(@RequestParam String id ) {
        return customerService.single(id);
    }

}
