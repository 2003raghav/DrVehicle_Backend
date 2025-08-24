package Vehicle.example.Management.Service;

import Vehicle.example.Management.List.UserList;
import Vehicle.example.Management.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public UserList login(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }
}
