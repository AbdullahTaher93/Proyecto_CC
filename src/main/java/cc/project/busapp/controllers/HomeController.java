package cc.project.busapp.controllers;


import cc.project.busapp.domain.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {


    @Autowired
    public ObjectMapper mapper;

    Customer customer1 = new Customer(1l, "Jhon Doe", "jhonDoe", "jhonDoe@mail.com");

    @GetMapping("/")
    public ObjectNode hello(){

        ObjectNode node2 = mapper.createObjectNode();
        node2.put("ruta", "/user/1");
        node2.putPOJO("valor", customer1);
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("status", "OK");
        objectNode.put("ejemplo", node2);
        return objectNode;
    }
}
