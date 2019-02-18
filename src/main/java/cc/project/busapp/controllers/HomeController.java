package cc.project.busapp.controllers;


import cc.project.busapp.domain.Customer;
import cc.project.busapp.errors.DirectionNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
public class HomeController implements ErrorController {


    @Autowired
    public ObjectMapper mapper;


    Customer customer1 = Customer.builder()
            .userId(1l)
            .name("Jhon Doe")
            .userName("admin")
            .email("admin@admin.com")
            .password("1234")
            .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
            .build();

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ObjectNode hello(){

        ObjectNode node2 = mapper.createObjectNode();
        node2.put("ruta", "/user/1");
        node2.putPOJO("valor", customer1);
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("status", "OK");
        objectNode.put("ejemplo", node2);
        return objectNode;
    }


    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public ObjectNode status(){

        ObjectNode node2 = mapper.createObjectNode();
        node2.put("ruta", "/user/1");
        node2.putPOJO("valor", customer1);
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("status", "OK");
        objectNode.put("ejemplo", node2);
        return objectNode;
    }

    @RequestMapping(value = "/error")
    public void error() {
        throw new DirectionNotFoundException("Dirección no encontrada");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
