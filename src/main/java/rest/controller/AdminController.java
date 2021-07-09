package rest.controller;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rest.model.Role;
import rest.model.User;
import rest.service.RoleService;
import rest.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String page(@CurrentSecurityContext(expression = "authentication.principal") User principal, Model model){
        model.addAttribute("user", principal);
        model.addAttribute("targetPage", "admin");
        model.addAttribute("title", "Admin panel");
        model.addAttribute("allRoles", roleService.findAll());
        return "page";
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping("/saveUser")
    @ResponseBody
    public String saveUser(@RequestBody User user){
        userService.saveUser(user);
        return "ok";
    }

    @GetMapping("/get")
    @ResponseBody
    public User getUser(@RequestParam(name = "id") Long id){
        return userService.findById(id);
    }

    @GetMapping("/delete")
    @ResponseBody
    public String deleteUSer(@RequestParam(name = "id") Long id){
        userService.deleteById(id);
        return "ok 200";
    }
}
