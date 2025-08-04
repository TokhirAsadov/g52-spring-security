package uz.pdp.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class PrePostAuthorizationController {

    @GetMapping("/has_role_user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String hasRoleUser() {
        return "has_role_user page";
    }

    @GetMapping("/has_role_admin")
    @PreAuthorize("hasRole('ADMIN')") //  surov kelganda tekshiradi
//    @PostAuthorize("hasRole('ADMIN')") // surov junatilayotganda oxirida tekshiradi
//    @Secured("ROLE_ADMIN")
//    @RolesAllowed({"ADMIN","USER"})
    public String hasRoleAdmin() {
        return "has_role_admin page";
    }

    @GetMapping("/has_role_manager")
    @PreAuthorize("hasRole('MANAGER')")
    public String hasRoleManager() {
        return "has_role_admin page";
    }

    @GetMapping("/has_create_user")
    @PreAuthorize("hasAnyAuthority('CREATE_USER','CREATE_NEW_STAFF')")
    public String hasCreateUser() {
        return "has_create_user permission page";
    }

    @GetMapping("/has_block_user")
    @PreAuthorize("hasAuthority(T(uz.pdp.config.security.Permission).BLOCK_USER)")
    public String hasBlockUser() {
        return "has_block_user permission page";
    }

}
