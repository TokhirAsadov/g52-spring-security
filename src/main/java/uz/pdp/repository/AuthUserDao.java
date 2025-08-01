package uz.pdp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.entity.AuthUser;

import java.util.Optional;

@Component
public class AuthUserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(AuthUser authUser) {
        String sql = "insert into authuser (username, password, role) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            var preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, authUser.getUsername());
            preparedStatement.setString(2, authUser.getPassword());
            preparedStatement.setString(3, authUser.getRole());
            return preparedStatement;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return key.intValue();
    }

    public Optional<AuthUser> findByUsername(String username) {
        String sql = "select * from authuser where username = ?";
        try {
            var mapper = BeanPropertyRowMapper.newInstance(AuthUser.class);
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, mapper, username));
        } catch (Exception e){
            return Optional.empty();
        }
    }

}
