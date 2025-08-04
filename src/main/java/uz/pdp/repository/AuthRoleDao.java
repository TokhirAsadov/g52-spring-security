package uz.pdp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.entity.AuthRole;
import java.util.List;

@Component
public class AuthRoleDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthRoleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<AuthRole> findRolesByUserId(Integer userId){
        String sql = "select ar.* from authuser_authrole aur inner join authrole ar on ar.id=aur.role_id where aur.user_id=?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AuthRole.class), userId);
    }

}
