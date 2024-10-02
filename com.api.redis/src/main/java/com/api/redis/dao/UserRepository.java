package com.api.redis.dao;

import com.api.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private static final String KEY="USER";

    //Save the User
    public User save(User user){
        redisTemplate.opsForHash().put(KEY,user.getUserId(),user);
        return user;
    }

    //Get User by Id
    public User get(String userId){
        System.out.println("Called from DB: get()");
        return (User)redisTemplate.opsForHash().get(KEY,userId);
    }

    //Find all the entries
    public Map<Object,Object> findAll(){
        return redisTemplate.opsForHash().entries(KEY);
    }

    //Delete an User
    public void delete(String userId){
        System.out.println("Called from DB: delete()");
        redisTemplate.opsForHash().delete(KEY,userId);
    }
}
