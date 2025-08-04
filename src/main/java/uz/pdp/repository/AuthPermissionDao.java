package uz.pdp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.entity.AuthPermission;

import java.util.List;

@Component
public class AuthPermissionDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthPermissionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<AuthPermission> findPermissionsByRoleId(Integer roleId){
        String sql = "select ap.* from authrole_authpermission arp inner join authpermission ap on ap.id=arp.permission_id where arp.role_id=?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AuthPermission.class), roleId);
    }

}
