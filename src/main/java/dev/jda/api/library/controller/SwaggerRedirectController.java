package dev.jda.api.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SwaggerRedirectController {
    @RequestMapping(value = {"/", "/api-docs"})
    public RedirectView redirect(){
        return new RedirectView("/swagger-ui.html");
    }
}
