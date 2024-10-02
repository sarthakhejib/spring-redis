package com.api.redis.controller;


import com.api.redis.dao.UserRepository;
import com.api.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@EnableCaching
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;


    @PostMapping
    public User createUser(@RequestBody User user){
        user.setUserId(UUID.randomUUID().toString());
        return repository.save(user);
    }

    @GetMapping("/{userId}")
    @Cacheable(key = "#userId", value = "USER") // This is used for Get mappings only
    public User getUser(@PathVariable("userId") String userId){
        return repository.get(userId);
    }

    @GetMapping()
    public Map<Object, Object> getAll(){
        return repository.findAll();
    }

    @DeleteMapping("{userId}")
    @CacheEvict(key = "#userId", value = "USER") // This is used for Delete Mapping only (Refresh the data in the cache)
    public void deleteUser(@PathVariable("userId") String userId){
         repository.delete(userId);
    }

}
