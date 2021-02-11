package com.example.RelationalDb.interfaces;

import com.example.RelationalDb.dto.Address;
import com.example.RelationalDb.dto.User;

import java.util.List;

public interface CRUDDao {

    Integer insertUser(User user);

    Integer insertAddress(Address address);

    User getUserDetail(String username);

    List<Address> getAddress(String username);
}
