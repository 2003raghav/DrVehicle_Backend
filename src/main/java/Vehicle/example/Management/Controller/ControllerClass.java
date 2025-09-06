package Vehicle.example.Management.Controller;

import Vehicle.example.Management.List.UserList;
import Vehicle.example.Management.Service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        UserList user = userService.login(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserList> registerUser(
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("username") String username,
            @RequestParam("address") String address,
            @RequestParam("vehicletype") String vehicletype,
            @RequestParam("vehiclemodel") String vehiclemodel,
            @RequestParam("yearofmanufacture") int yearofmanufacture,
            @RequestParam("regno") String regno,
            @RequestParam("email") String email,
            @RequestParam("phone") long phone,
            @RequestParam("dateofbirth") String dateofbirth, // Expect dd-MM-yyyy
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        UserList user = new UserList();
        user.setPassword(password);
        user.setName(name);
        user.setUsername(username);
        user.setAddress(address);
        user.setVehicletype(vehicletype);
        user.setVehiclemodel(vehiclemodel);
        user.setYearofmanufacture(yearofmanufacture);
        user.setRegno(regno);
        user.setEmail(email);
        user.setPhone(phone);
        user.setImageName(imageFile != null ? imageFile.getOriginalFilename() : null);
        user.setImageType(imageFile != null ? imageFile.getContentType() : null);

        return ResponseEntity.ok(userService.saveUser(user));
    }


}
