package cc.project.busapp.controllers;

import cc.project.busapp.errors.DirectionNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleErrorController implements ErrorController {


    @RequestMapping("/error")
    public void handleError(){
        throw new DirectionNotFoundException("Url not Found");
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
