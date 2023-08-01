package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    
    //private PasswordValidator passwordValidator;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider
                           ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
       // this.passwordValidator = passwordValidator;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
    
    
    @Override
    public String register(RegisterDto registerDto) {
    	
    	//Email validator
    	    String regex = "^(.+)@(.+)$";
    	    Pattern pattern = Pattern.compile(regex);
    		Matcher matcher = pattern.matcher(registerDto.getEmail());
    		if(matcher.matches()!=true) {
    			System.out.println(registerDto.getEmail() +" : "+ matcher.matches());
    			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Please provide valid email!.");
    		}
    	//password complexity check
    		 String PASSWORD_PATTERN =
    		            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    		     Pattern PassPattern = Pattern.compile(PASSWORD_PATTERN);
    		    Matcher passMatcher = PassPattern.matcher(registerDto.getPassword());	
    		if(passMatcher.matches()!=true) {
    			
    			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Password must contain at least one digit [0-9].\n"
    					+ "Password must contain at least one lowercase Latin character [a-z].\n"
    					+ "Password must contain at least one uppercase Latin character [A-Z].\n"
    					+ "Password must contain at least one special character like ! @ # & ( ).\n"
    					+ "Password must contain a length of at least 8 characters and a maximum of 20 characters.");
    		}
    		
        // add check for username exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        // add check for email exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!.";
    }

	@Override
	public String userLogin(LoginDto loginDto) {
		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		 SecurityContextHolder.getContext().setAuthentication(authentication);
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		 if (principal instanceof UserDetails) {
		 String username = ((UserDetails)principal).getUsername();
		
		  Long userId = userRepository.findUseridByEmail(username);
		  
		 } else {
			 String username = principal.toString();
			 System.out.println(username);
		 }
		
		 
		 return "User loggedin successfully!.";
	}
}
