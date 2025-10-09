package Vehicle.example.Management.Service;

import Vehicle.example.Management.List.ServiceDetails;
import Vehicle.example.Management.Repository.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLayer {

    @Autowired
    private ServiceRepo serviceRepo;

    public List<ServiceDetails> getServicesByUsername(String username) {
        return serviceRepo.findByUsername(username);
    }

    public ServiceDetails saveService(ServiceDetails service) {
        return serviceRepo.save(service);
    }
    // Fetch single service by ID
    public ServiceDetails getServiceById(Long id) {
        return serviceRepo.findById(id).orElse(null);
    }



}
