package com.openclassrooms.mddapi.back.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {
    @RequestMapping("/documentation")
    public String getRedirectHtml() {
        return "redirect:swagger-ui.html";
    }

    @RequestMapping("/documentation-api")
    public String getRedirectJsonUrl() {
        return "redirect:v3/api-docs";
    }

}