package Vehicle.example.Management.Service;

import Vehicle.example.Management.List.UserList;
import Vehicle.example.Management.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceClass {

    @Autowired
    private UserRepo userRepository;

    public List<UserList> getList() {
        return userRepository.findAll();
    }

    public UserList getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserList login(String username, String password) {
        return userRepository.findByusernameAndPassword(username, password);
    }

    public UserList saveUser(UserList user) {
        return userRepository.save(user);
    }

    public String resetPassword(String username, String newPassword) {
        Optional<UserList> userOptional = userRepository.findByUsername(username); // use instance

        if (userOptional.isPresent()) {
            UserList user = userOptional.get();
            user.setPassword(newPassword); // ⚠ plain text for now
            userRepository.save(user); // use instance
            return "Password reset successful!";
        } else {
            return "User not found with username: " + username;
        }

    }
    public Optional<UserList> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
