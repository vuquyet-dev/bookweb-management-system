package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.userdto.UserCreateDTO;
import com.example.bookweb_management.dto.userdto.UserResponseDTO;
import com.example.bookweb_management.dto.userdto.UserUpdateDTO;
import com.example.bookweb_management.entity.Permission;
import com.example.bookweb_management.entity.Role;
import com.example.bookweb_management.entity.User;
import com.example.bookweb_management.exception.user_exception.DuplicateIdentityNumberException;
import com.example.bookweb_management.exception.user_exception.DuplicateUsernameException;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.mapper.UserMapper;
import com.example.bookweb_management.repository.RoleRepository;
import com.example.bookweb_management.repository.UserRepository;
import com.example.bookweb_management.service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    //@PreAuthorize("hasRole('ADMIN')") // Authorize method-level
    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.info("In method get users");
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

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(createDTO.getRoleIds()));
        user.setRoles(roles);

        User saveUser = userRepository.save(user);
        return userMapper.toResponseDTO(saveUser);
    }

    @Override
    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found with username: " + id));
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setFullname(dto.getFullname());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setIdentityNumber(dto.getIdentityNumber());
        user.setAge(dto.getAge());
        user.setBirthday(dto.getBirthday());
        user.setAddress(dto.getAddress());

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(dto.getRoleIds()));
        user.setRoles(roles);

        User saveUser = userRepository.save(user);

        return userMapper.toResponseDTO(saveUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserResponseDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<User> users = userRepository.searchByFullnameOrIdentityNumber(keyword, pageable);

        return users.map(user -> {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setFullname(user.getFullname());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setIdentityNumber(user.getIdentityNumber());
            dto.setAge(user.getAge());
            dto.setBirthday(user.getBirthday());
            dto.setAddress(user.getAddress());
            return dto;
        });
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

    @Override
    public void usersExcelExport(HttpServletResponse httpServletResponse) throws IOException {
        List<User> users = userRepository.findAll();


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Users Info");


        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Username");
        row.createCell(2).setCellValue("Fullname");
        row.createCell(3).setCellValue("Phone number");
        row.createCell(4).setCellValue("Identity Number");
        row.createCell(5).setCellValue("Age");
        row.createCell(6).setCellValue("Birthday");
        row.createCell(7).setCellValue("Address");
        row.createCell(8).setCellValue("Role");

        int dataRowIndex = 1;

        for(User user : users)
        {
            XSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(user.getId());
            dataRow.createCell(1).setCellValue(user.getUsername());
            dataRow.createCell(2).setCellValue(user.getFullname());
            dataRow.createCell(3).setCellValue(user.getPhoneNumber());
            dataRow.createCell(4).setCellValue(user.getIdentityNumber());
            dataRow.createCell(5).setCellValue(user.getAge());
            dataRow.createCell(6).setCellValue(user.getBirthday());
            dataRow.createCell(7).setCellValue(user.getAddress());
            dataRowIndex++;
        }

        //độ rộng cột
        for(int i = 0; i < 9; i++)
        {

            sheet.autoSizeColumn(i);// kích thước cột bằng cell có data dài nhất
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
        }

        ServletOutputStream sos =httpServletResponse.getOutputStream();
        workbook.write(sos);
        workbook.close();
        sos.close();

    }
}
