package Vehicle.example.Management.Controller;


import Vehicle.example.Management.List.ProviderList;
import Vehicle.example.Management.List.UserList;
import Vehicle.example.Management.Service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/provider")
@CrossOrigin
public class ProviderController {

    @Autowired
    private ProviderService service;

    @GetMapping("/providerList")
    public ResponseEntity<List<ProviderList>> getList(){ return ResponseEntity.ok(service.getList());}

    @PostMapping("/register")
    public ResponseEntity<?> registerProvider(
            @RequestPart("provider") ProviderList provider,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        try {
            ProviderList savedProvider = service.registerProvider(provider, image);
            return new ResponseEntity<>(savedProvider, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to register provider: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginProvider(@RequestBody Map<String, String> loginData) {
        try {
            String ownername = loginData.get("ownername");
            String password = loginData.get("password");

            if(ownername == null || password == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ownername and password are required");
            }

            ProviderList provider = service.login(ownername, password);
            if (provider != null) {
                return ResponseEntity.ok(provider);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid ownername or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error: " + e.getMessage());
        }
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String ownername = request.get("ownername");
        String newPassword = request.get("newPassword");

        String result = service.resetPassword(ownername, newPassword);
        if (result.toLowerCase().contains("successful")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

}
