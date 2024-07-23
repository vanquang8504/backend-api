package com.example.shopapp.services;

import com.example.shopapp.dataNotFoundException.DataNotFoundException;
import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new  DataIntegrityViolationException("Phone number already exit");
        }
        User newUer = User.builder()
                .fullname(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .password(userDTO.getPassword())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new DataNotFoundException("Role not found"));
        newUer.setRole(role);
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() ==0){
            String password = userDTO.getPassword();
        }

        return userRepository.save(newUer);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return null;
    }

    @Override
    public User findUserById(long idUser) throws DataNotFoundException {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id : "+idUser));
    }
}
