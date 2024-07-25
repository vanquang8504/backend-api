package com.example.shopapp.services;

import com.example.shopapp.components.JwtTokenUtil;
import com.example.shopapp.dataNotFoundException.DataNotFoundException;
import com.example.shopapp.dataNotFoundException.PermissionDenyException;
import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new  DataIntegrityViolationException("Phone number already exit");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        if(role.getName().toUpperCase().equals(Role.ADMIN)){
            throw new PermissionDenyException("You cannot register a Admin account");
        }
        User newUer = User.builder()
                .fullname(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .password(userDTO.getPassword())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .isActive(true)
                .build();
        newUer.setRole(role);
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() ==0){
            String password = userDTO.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            newUer.setPassword(encodePassword);
        }

        return userRepository.save(newUer);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid phone number or password");
        }
        //return optionalUser.get(); // -> JWT
        User existingUser = optionalUser.get();
        //Check password
        if(existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() ==0){
            if(!passwordEncoder.matches(password , existingUser.getPassword())){
                throw new BadCredentialsException("Wrong number phone or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber,password,existingUser.getAuthorities()
        );
        //authenticate with Java spring
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public User findUserById(long idUser) throws DataNotFoundException {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id : "+idUser));
    }
}
