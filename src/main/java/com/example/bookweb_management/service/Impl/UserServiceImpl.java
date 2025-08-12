package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.UserDTO;
import com.example.bookweb_management.entity.User;
import com.example.bookweb_management.exception.DuplicateIdentityNumberException;
import com.example.bookweb_management.exception.DuplicateUsernameException;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.mapper.UserMapper;
import com.example.bookweb_management.repository.UserRepository;
import com.example.bookweb_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.toDTOs(userRepository.findAll());
    }

    @Override
    public UserDTO getUser(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new ResourceNotFoundException("Not found with username: " + username));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        //Check username trùng
        if(userRepository.existsByUsername(dto.getUsername()))
        {
            throw new DuplicateUsernameException("Username '" + dto.getUsername() + "' already exists");
        }

        if(userRepository.existsByIdentityNumber(dto.getIdentityNumber()))
        {
            throw new DuplicateIdentityNumberException("Identity number '" + dto.getIdentityNumber() + "' already exists");
        }

        User user = userMapper.toEntity(dto);
        User saveUser = userRepository.save(user);
        return userMapper.toDTO(saveUser);
    }

    @Override
    public UserDTO update(String username, UserDTO dto) {
        User user = userRepository.findById(username).orElseThrow(() -> new ResourceNotFoundException("Not found with username: " + username));
        user.setPassword(dto.getPassword());
        user.setFullname(dto.getFullname());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setIdentityNumber(dto.getIdentityNumber());
        user.setAge(dto.getAge());
        user.setBirthday(dto.getBirthday());
        user.setAddress(dto.getAddress());
        User saveUser = userRepository.save(user);
        return userMapper.toDTO(saveUser);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    @Override
    public Page<UserDTO> search(String keyword, int page, int size) {
        return null;
    }
}
