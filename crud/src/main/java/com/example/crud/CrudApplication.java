package com.example.crud;

import com.example.crud.constants.RoleEnum;
import com.example.crud.entity.Role;
import com.example.crud.entity.User;
import com.example.crud.repository.RoleRepository;
import com.example.crud.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CrudApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		Role roleUser = roleRepository.findByName(RoleEnum.ROLE_ADMIN.name());
		if(roleUser == null){
			Role role = new Role();
			role.setName(RoleEnum.ROLE_USER.name());
			roleRepository.save(role);
		}
		Role roleAdmin = roleRepository.findByName(RoleEnum.ROLE_ADMIN.name());
		if(roleAdmin == null){
			Role adminRole = new Role();
			adminRole.setName(RoleEnum.ROLE_ADMIN.name());
			roleAdmin = roleRepository.save(adminRole);
		}

		User checkAdmin = userRepository.findByEmail("admin@test.com");
		if(checkAdmin == null){
			User user = new User();
			user.setEmail("admin@test.com");
			user.setName("Admin Admin");
			user.setPassword(passwordEncoder.encode("password"));
			user.setRoleList(List.of(roleAdmin));

			userRepository.save(user);
		}
	}
}
