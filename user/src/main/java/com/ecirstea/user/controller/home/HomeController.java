package com.ecirstea.user.controller.home;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.Collections;
import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<String> healthcheck() {
        return ResponseEntity.ok().body("OK");
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Map<String, String> config() {
        return Collections.singletonMap("STATUS", "OK");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        System.out.println("swagger-ui.html");
        return "redirect:swagger-ui.html";
    }
}