package com.example.RelationalDb.dao;

import com.example.RelationalDb.dto.Address;
import com.example.RelationalDb.dto.User;
import com.example.RelationalDb.interfaces.CRUDDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDDaoImpl implements CRUDDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String insertUser;
    private String insertAddress;
    private String fetchUserDetails;
    private String fetchAddress;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    public void setInsertAddress(String insertAddress) {
        this.insertAddress = insertAddress;
    }

    public void setFetchUserDetails(String fetchUserDetails) {
        this.fetchUserDetails = fetchUserDetails;
    }

    public void setFetchAddress(String fetchAddress) {
        this.fetchAddress = fetchAddress;
    }

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
