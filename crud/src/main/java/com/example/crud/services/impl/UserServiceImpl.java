package com.example.crud.services.impl;

import com.example.crud.constants.RoleEnum;
import com.example.crud.config.SecurityConfig;
import com.example.crud.dto.EditUserDto;
import com.example.crud.dto.UserDto;
import com.example.crud.entity.Role;
import com.example.crud.entity.User;
import com.example.crud.exceptions.ResourceNotFoundException;
import com.example.crud.repository.RoleRepository;
import com.example.crud.repository.UserRepository;
import com.example.crud.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository
    ,PasswordEncoder passwordEncoder, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public User dtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto,User.class);
    }

    public UserDto userToDto(User user){
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {

        try{
            Role roleUser = roleRepository.findByName(RoleEnum.ROLE_USER.name());
            Role roleAdmin = roleRepository.findByName(RoleEnum.ROLE_USER.name());

            if(roleUser == null){
                roleUser = roleRepository.save(new Role(RoleEnum.ROLE_USER.name()));
            }
            if(roleAdmin == null){
                roleAdmin = roleRepository.save(new Role(RoleEnum.ROLE_ADMIN.name()));
            }

            log.info("user creating");

            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRoleList(List.of(roleUser));
            userRepository.save(user);
            log.info("user created");
            return this.userToDto(user);
        }catch (Exception ex){
            log.info("{} error while creating user", ex.getMessage());
            return null;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public EditUserDto editUser(Long id, EditUserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            throw new SecurityException("Not authenticated");
        }

        boolean hasAdminRole = authentication.getAuthorities()
                .stream().anyMatch(role -> role.getAuthority().equals(RoleEnum.ROLE_ADMIN.name()));

        log.info("haAdminRole: {}",hasAdminRole);

        if(hasAdminRole){
            User user = userRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("user","id", id.intValue()));
            user.setName(userDto.getName());
            userRepository.save(user);
            return this.modelMapper.map(user,EditUserDto.class);
        }
        else {
            throw new RuntimeException("You do not have permission to edit this user");
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("USER","ID", id.intValue()));
        return this.userToDto(user);
    }

    @Override
    public EditUserDto getEditUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("USER","ID", id.intValue()));
        return this.modelMapper.map(user,EditUserDto.class);
    }

    @Override
    public boolean deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("USER","ID", id.intValue()));
        userRepository.delete(user);
        return true;
    }
}
