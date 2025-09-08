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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userMapper.toResponseDTOs(userRepository.findAll());
    }


    @Override
    public UserResponseDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found with username: " + id));
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
        user.setPassword(encoder.encode(user.getPassword())); //encode password
        User saveUser = userRepository.save(user);
        return userMapper.toResponseDTO(saveUser);
    }

    @Override
    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found with username: " + id));
        Optional<User> existingUser = userRepository
                .findByUsernameOrIdentityNumber(dto.getUsername(), dto.getIdentityNumber());

        if (existingUser.isPresent()) {
            if (existingUser.get().getUsername().equals(dto.getUsername())) {
                throw new DuplicateUsernameException("Username '" + dto.getUsername() + "' already exists");
            }
            if (existingUser.get().getIdentityNumber().equals(dto.getIdentityNumber())) {
                throw new DuplicateIdentityNumberException("Identity number '" + dto.getIdentityNumber() + "' already exists");
            }
        }
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
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserResponseDTO> search(String keyword, int page, int size) {
        return null;
    }

    @Override
    public String verify(User user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated())
        {
            return jwtService.generateToken(user.getUsername());
        }
        return "fail";
    }
}
