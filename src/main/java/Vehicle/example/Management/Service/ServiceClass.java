package Vehicle.example.Management.Service;

import Vehicle.example.Management.List.UserList;
import Vehicle.example.Management.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceClass {

    @Autowired
    private UserRepo repo;

    public List<UserList> getlist() {
        return repo.findAll();
    }

    public UserList getlistbyid(int id) {
        return repo.findById(id).orElse(new UserList());
    }
}
