package com.works.services;

import com.works.entities.Fault;
import com.works.repositories.FaultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {

    final FaultRepository faultRepository;
    public CustomerService(FaultRepository fRepo) {
        this.faultRepository = fRepo;
    }


    public ResponseEntity single(String fid ) {
        Map<String, Object> hm = new LinkedHashMap<>();
        try {
            Long id = Long.parseLong(fid);
            Optional<Fault> oFault = faultRepository.findById(id);
            if (oFault.isPresent() ) {
                // bu user var
                hm.put("status", true);
                hm.put("result", oFault.get() );
            }else {
                hm.put("status", false);
                hm.put("result", "Empty!" );
            }
        }catch (Exception ex) {
            hm.put("message", "id request : " + fid);
            hm.put("status", false);
            return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }
}
