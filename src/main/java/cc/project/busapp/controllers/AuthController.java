package cc.project.busapp.controllers;

import cc.project.busapp.security.JwtTokenProvider;
import cc.project.busapp.services.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    CustomerServiceImpl customerService;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest data) {

        try {
            String username = data.getUserName();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));

            List<String> roles = this.customerService.getCustomerByName(username).getRoles();
            String token = jwtTokenProvider.createToken(username, roles);

            Map<Object, Object> model = new HashMap<>();
            model.put("role", roles);
            model.put("username", username);
            model.put("token", token);
            return ok(model);

        } catch (AuthenticationException e) {
          //  throw new BadCredentialsException("Usuario no valido "+ data.getUserName()+ "password"+ data.getPassword());
            throw new BadCredentialsException("Usuario/password no valido");

        }
    }

}
