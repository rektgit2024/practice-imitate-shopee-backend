package com.springboot.practiceimitateshopeebackend.utils.mapper;

import com.springboot.practiceimitateshopeebackend.entity.User;
import com.springboot.practiceimitateshopeebackend.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper = new ModelMapper();

    public UserModel mapUserEntityToUserModel(User user){

        UserModel userModel = mapper.map(user, UserModel.class);
        userModel.setPassword(passwordEncoder.encode(user.getPassword()));
        return userModel;
    }

    public User mapUserModelToUserEntity(UserModel userModel){

        User user = mapper.map(userModel, User.class);
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return user;
    }
}