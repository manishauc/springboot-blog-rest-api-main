package com.springboot.blog.controller;

import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //START ::  Build Token Creation REST API
    @PostMapping(value = {"/token", "/accessToken"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
    	
    	
    	 //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
         //String encodedPassword = passwordEncoder.encode("manishauc");      
         //System.out.println(encodedPassword);
    	
    	
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }
    //END ::  Build Token Creation REST API

    
    // START :: Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    // END :: Build Register  REST API
    
    
    // START :: Build Login  REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> userLogin(@RequestBody LoginDto loginDto){
    	
    	String response = authService.userLogin(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // END :: Build Login  REST API

}