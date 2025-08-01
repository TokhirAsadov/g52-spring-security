package uz.pdp.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.dto.UserRegisterDTO;
import uz.pdp.entity.AuthUser;
import uz.pdp.repository.AuthUserDao;

@Controller
public class AuthController {

    private final AuthUserDao authUserDao;
    private final PasswordEncoder encoder;

    public AuthController(AuthUserDao authUserDao, PasswordEncoder encoder) {
        this.authUserDao = authUserDao;
        this.encoder = encoder;
    }

    @GetMapping("/auth/login")
    public ModelAndView loginPage(@RequestParam(name = "error", required = false) String error){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        modelAndView.addObject("errorMessage", error);
        return modelAndView;
    }

    @GetMapping("/auth/logout")
    public String logoutPage(){
        return "auth/logout";
    }

    @GetMapping("/auth/register")
    public String registerPage(){
        return "auth/register";
    }

    @PostMapping("/auth/register")
    public String registerPost(@ModelAttribute UserRegisterDTO dto){
        AuthUser authUser = AuthUser.builder()
                .username(dto.username())
                .password(encoder.encode(dto.password()))
                .role("USER")
                .build();
        Integer userId = authUserDao.save(authUser);
        System.out.println("userId: "+userId);
        return "redirect:/auth/login";
    }

}
