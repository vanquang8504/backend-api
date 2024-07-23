package com.example.shopapp.services;

import com.example.shopapp.dataNotFoundException.DataNotFoundException;
import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;

    String login(String phoneNumber, String password);

    User findUserById(long idUser) throws DataNotFoundException;
}
