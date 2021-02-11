package com.example.RelationalDb.controller;

import com.example.RelationalDb.dto.Address;
import com.example.RelationalDb.dto.User;
import com.example.RelationalDb.interfaces.CRUDDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CRUDController {

    @Autowired
    @Qualifier("crudDaoAuto")
    private CRUDDao crudDao;

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody User user){
        Integer userId = this.crudDao.insertUser(user);
        return new ResponseEntity<Integer>(userId, HttpStatus.OK);
    }

    @PostMapping("/address")
    public ResponseEntity createAddress(@RequestBody Address address){
        Integer addressId = this.crudDao.insertAddress(address);
        return new ResponseEntity<Integer>(addressId, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity getUser(@PathVariable String username){
        User user = this.crudDao.getUserDetail(username);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/address/{username}")
    public ResponseEntity getAllAddress(@PathVariable String username){
        List<Address> addresses = this.crudDao.getAddress(username);
        return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK);
    }
}
