package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.userdto.UserCreateDTO;
import com.example.bookweb_management.dto.userdto.UserResponseDTO;
import com.example.bookweb_management.dto.userdto.UserUpdateDTO;
import com.example.bookweb_management.entity.User;
import com.example.bookweb_management.exception.user_exception.DuplicateIdentityNumberException;
import com.example.bookweb_management.exception.user_exception.DuplicateUsernameException;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.mapper.UserMapper;
import com.example.bookweb_management.repository.UserRepository;
import com.example.bookweb_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userMapper.toResponseDTOs(userRepository.findAll());
    }


    @Override
    public UserResponseDTO getUser(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new ResourceNotFoundException("Not found with username: " + username));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO createUser(UserCreateDTO createDTO) {

        Optional<User> existingUser = userRepository
                .findByUsernameOrIdentityNumber(createDTO.getUsername(), createDTO.getIdentityNumber());

        if (existingUser.isPresent()) {
            if (existingUser.get().getUsername().equals(createDTO.getUsername())) {
                throw new DuplicateUsernameException("Username '" + createDTO.getUsername() + "' already exists");
            }
            if (existingUser.get().getIdentityNumber().equals(createDTO.getIdentityNumber())) {
                throw new DuplicateIdentityNumberException("Identity number '" + createDTO.getIdentityNumber() + "' already exists");
            }
        }

        if(userRepository.existsByIdentityNumber(createDTO.getIdentityNumber()))
        {
            throw new DuplicateIdentityNumberException("Identity number '" + createDTO.getIdentityNumber() + "' already exists");
        }

        User user = userMapper.toEntity(createDTO);
        User saveUser = userRepository.save(user);
        return userMapper.toResponseDTO(saveUser);
    }

    @Override
    public UserResponseDTO update(String username, UserUpdateDTO dto) {
        User user = userRepository.findById(username).orElseThrow(() -> new ResourceNotFoundException("Not found with username: " + username));
        user.setPassword(dto.getPassword());
        user.setFullname(dto.getFullname());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setIdentityNumber(dto.getIdentityNumber());
        user.setAge(dto.getAge());
        user.setBirthday(dto.getBirthday());
        user.setAddress(dto.getAddress());
        User saveUser = userRepository.save(user);
        return userMapper.toResponseDTO(saveUser);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    @Override
    public Page<UserResponseDTO> search(String keyword, int page, int size) {
        return null;
    }
}
