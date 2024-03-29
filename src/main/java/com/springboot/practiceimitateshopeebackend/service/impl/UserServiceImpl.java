package com.springboot.practiceimitateshopeebackend.service.impl;

import com.springboot.practiceimitateshopeebackend.entity.User;
import com.springboot.practiceimitateshopeebackend.model.LoginRequest;
import com.springboot.practiceimitateshopeebackend.model.LoginResponse;
import com.springboot.practiceimitateshopeebackend.model.UserModel;
import com.springboot.practiceimitateshopeebackend.repository.UserRepository;
import com.springboot.practiceimitateshopeebackend.security.JwtService;
import com.springboot.practiceimitateshopeebackend.service.UserService;
import com.springboot.practiceimitateshopeebackend.utils.StringUtils;
import com.springboot.practiceimitateshopeebackend.utils.mapper.UserMapper;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserModel register(UserModel userModel) {

        boolean isEmailExists = userRepository.existsByEmail(userModel.getEmail());

        if(isEmailExists){
            throw new EntityExistsException(StringUtils.ACCOUNT_EXISTS);
        }

        User user = mapper.mapUserModelToUserEntity(userModel);
        User saveUser = userRepository.save(user);
        return mapper.mapUserEntityToUserModel(saveUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            return LoginResponse.builder()
                    .responseMessage(StringUtils.LOGIN_SUCCESSFUL)
                    .jwtToken(jwtService.generateToken(authentication))
                    .build();
        }catch (AuthenticationException e){
            throw new BadCredentialsException(StringUtils.INVALID_CREDENTIALS);
        }

    }
}
