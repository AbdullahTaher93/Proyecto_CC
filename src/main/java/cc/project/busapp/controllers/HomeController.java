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


    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ObjectNode hello(){

        ObjectNode node = mapper.createObjectNode();
        node.put("status", "OK");
        return node;
    }


    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public ObjectNode status(){

        ObjectNode node = mapper.createObjectNode();
        node.put("status", "OK");
        return node;
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
