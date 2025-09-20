package Vehicle.example.Management.Controller;

import Vehicle.example.Management.List.UserList;
import Vehicle.example.Management.Service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ControllerClass {

    @Autowired
    private ServiceClass userService;

    // List all users
    @GetMapping("/users")
    public ResponseEntity<List<UserList>> getUsers() {
        return ResponseEntity.ok(userService.getList());
    }

    // Get user by username
    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<UserList> userOpt = userService.getUserByUsername(username);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }
    }


    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserList loginRequest) {
        UserList user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            // Return minimal info + username
            return ResponseEntity.ok(Map.of(
                    "username", user.getUsername(),
                    "name", user.getName()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }

    // Register
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
            @RequestParam("dateofbirth") String dateofbirth,
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

    // Forgot password
    @PutMapping("/users/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String newPassword = request.get("newPassword");

        String result = userService.resetPassword(username, newPassword);
        if (result.toLowerCase().contains("successful")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    // Serve profile image
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Path path = Paths.get("uploads/" + imageName); // adjust your uploads folder
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // adjust dynamically if needed
                .body(resource);
    }
}
