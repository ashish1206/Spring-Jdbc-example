package com.example.RelationalDb.dao;

import com.example.RelationalDb.dto.Address;
import com.example.RelationalDb.dto.User;
import com.example.RelationalDb.interfaces.CRUDDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("crudDaoAuto")
public class CRUDDaoImplAuto implements CRUDDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String insertUser = "insert into users (username, date_of_birth) values(:username, :dateOfBirth)";
    private String insertAddress = "insert into address (door, street, landmark, city, user_id) values(:door, :street, :landmark, :city, :userId)";
    private String fetchUserDetails = "select * from users where username = :username";
    private String fetchAddress = "select * from address where user_id = (select user_id from users where username = :username)";

    @Override
    public Integer insertUser(User user) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("dateOfBirth", user.getDateOfBirth());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.namedParameterJdbcTemplate.update(insertUser, params, keyHolder);
        Integer id = (Integer)keyHolder.getKeyList().get(0).get("user_id");
        return id;
    }

    @Override
    public Integer insertAddress(Address address) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("door", address.getDoor())
                .addValue("street", address.getStreet())
                .addValue("landmark", address.getLandmark())
                .addValue("city", address.getCity())
                .addValue("userId", address.getUserId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.namedParameterJdbcTemplate.update(insertAddress, params, keyHolder);
        Integer id = (Integer)keyHolder.getKeyList().get(0).get("user_id");
        return id;
    }

    @Override
    public User getUserDetail(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        User user = this.namedParameterJdbcTemplate.query(fetchUserDetails, params, new ResultSetExtractor<User>() {

            @Override
            public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                User user = new User();
                while(resultSet.next()){
                    user.setUsername(resultSet.getString("username"));
                    user.setDateOfBirth(resultSet.getDate("date_of_birth"));
                }
                return user;
            }
        });
        return user;
    }

    @Override
    public List<Address> getAddress(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        List<Address> addresses = this.namedParameterJdbcTemplate.query(fetchAddress, params, new RowMapper<Address>() {
            @Override
            public Address mapRow(ResultSet resultSet, int i) throws SQLException {
                Address address = new Address();
                address.setDoor(resultSet.getString("door"));
                address.setStreet(resultSet.getString("street"));
                address.setLandmark(resultSet.getString("landmark"));
                address.setCity(resultSet.getString("city"));
                address.setUserId(resultSet.getInt("user_id"));
                return address;
            }
        });
        return addresses;
    }
}
