package Vehicle.example.Management.Controller;

import Vehicle.example.Management.List.UserList;
import Vehicle.example.Management.Service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ControllerClass {

    @Autowired
    private ServiceClass userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserList>> getUsers() {
        return new ResponseEntity<>(userService.getList(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserList> getUserById(@PathVariable int id) {
        UserList user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserList loginRequest) {
        UserList user = userService.login(loginRequest.getName(), loginRequest.getPassword());

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
