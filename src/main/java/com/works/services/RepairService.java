package com.works.services;

import ch.qos.logback.core.encoder.Encoder;
import com.works.entities.Fault;
import com.works.entities.Role;
import com.works.entities.User;
import com.works.repositories.FaultRepository;
import com.works.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RepairService implements UserDetailsService {

    final UserRepository userRepository;
    final FaultRepository faultRepository;
    public RepairService(UserRepository userRepository, FaultRepository faultRepository) {
        this.userRepository = userRepository;
        this.faultRepository = faultRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        System.out.println( username );
        Optional<User> optionalUser = userRepository.findByEmailEqualsIgnoreCase( username );
        if ( optionalUser.isPresent() ) {
            User vtUser = optionalUser.get();
            System.out.println( vtUser );
            userDetails = new org.springframework.security.core.userdetails.User(
                    username,
                    vtUser.getPassword(),
                    vtUser.isEnabled(),
                    vtUser.isTokenExpired(),
                    true,
                    true,
                    getAuthorities(vtUser.getRoles())
            );
        }else {
            throw new UsernameNotFoundException("Böyle bir kullanıcı yok");
        }
        return userDetails;
    }
    private List<GrantedAuthority> getAuthorities (List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority( role.getName() ));
        }
        return authorities;
    }

    public ResponseEntity register(User user ) {
        Optional<User> oUser = userRepository.findByEmailEqualsIgnoreCase(user.getEmail());
        Map<String,Object> hm =new LinkedHashMap<>();

        if (oUser.isPresent() ) {
            // bu  kullanıcı daha önce kayıtlı
            //throw new UsernameNotFoundException("User Register Fail");
            hm.put("status", false);
            hm.put("message", "Bu kullanıcı daha önce kayıtlı!");
            hm.put("result", user  );
            return new ResponseEntity( hm, HttpStatus.BAD_REQUEST );
        }else {
            user.setPassword (encoder().encode(user.getPassword()) );
            hm.put("status", true);
            hm.put("result",  userRepository.save( user ) );
            return new ResponseEntity( hm, HttpStatus.OK );
        }
    }

    public ResponseEntity save(Fault fault) {
        Map<String, Object> hm = new LinkedHashMap<>();
        faultRepository.save(fault);
        hm.put("status", true);
        hm.put("result", fault);
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity list() {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("users", faultRepository.findAll());
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
