package com.User_service.Service;

import com.User_service.Dao.UserRepository;
import com.User_service.Exception.ResourceNotFoundException;
import com.User_service.Model.User;
import com.User_service.Model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Create User
    public User createUser(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
        return userRepository.save(user);
    }

    // Get All Users with Pagination (returns only list, not metadata)
    public List<User> getAllUsers(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return page.getContent();
    }

    // Get User by ID
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
    }

    // Update User
    public User updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(userDTO.getName());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setPassword(userDTO.getPassword());
            existingUser.setRole(userDTO.getRole());
            return userRepository.save(existingUser);
        } else {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
    }

    // Delete User
    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
    }
}

