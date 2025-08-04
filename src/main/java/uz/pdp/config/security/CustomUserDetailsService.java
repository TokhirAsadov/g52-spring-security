package uz.pdp.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.entity.AuthPermission;
import uz.pdp.entity.AuthRole;
import uz.pdp.entity.AuthUser;
import uz.pdp.repository.AuthPermissionDao;
import uz.pdp.repository.AuthRoleDao;
import uz.pdp.repository.AuthUserDao;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserDao authUserDao;
    private final AuthRoleDao authRoleDao;
    private final AuthPermissionDao authPermissionDao;

    @Autowired
    public CustomUserDetailsService(AuthUserDao authUserDao, AuthRoleDao authRoleDao, AuthPermissionDao authPermissionDao) {
        this.authUserDao = authUserDao;
        this.authRoleDao = authRoleDao;
        this.authPermissionDao = authPermissionDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with username: " + username));

        List<AuthRole> userRoles = authRoleDao.findRolesByUserId(authUser.getId());
        for (AuthRole role : userRoles) {
            List<AuthPermission> permissionList = authPermissionDao.findPermissionsByRoleId(role.getId());
            role.setPermissions(permissionList);
        }
        authUser.setRoles(userRoles);
        return new CustomUserDetails(authUser);
    }

}
