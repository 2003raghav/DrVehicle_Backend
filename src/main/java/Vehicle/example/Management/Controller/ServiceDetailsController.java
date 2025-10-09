package Vehicle.example.Management.Controller;

import Vehicle.example.Management.List.ServiceDetails;
import Vehicle.example.Management.Service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin
public class ServiceDetailsController {

    @Autowired
    private ServiceLayer serviceDetailsService;

    // Get all services for a user
    @GetMapping("/{username}")
    public ResponseEntity<List<ServiceDetails>> getServicesByUsername(@PathVariable String username) {
        List<ServiceDetails> services = serviceDetailsService.getServicesByUsername(username);
        if (services.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(services);
        }
        return ResponseEntity.ok(services);
    }
}

