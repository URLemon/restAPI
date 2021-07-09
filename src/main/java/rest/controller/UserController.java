package rest.controller;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.model.User;

import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public String page(@CurrentSecurityContext(expression = "authentication.principal") User principal,Model model){
        model.addAttribute("user", principal);
        model.addAttribute("targetPage", "user");
        model.addAttribute("title", "User page");
        return "page";
    }

    @GetMapping("/give")
    @ResponseBody
    public User getUserInfo(@CurrentSecurityContext(expression = "authentication.principal") User principal){
        return principal;
    }
}
