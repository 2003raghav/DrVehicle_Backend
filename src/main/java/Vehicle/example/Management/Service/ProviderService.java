package Vehicle.example.Management.Service;

import Vehicle.example.Management.List.ProviderList;
import Vehicle.example.Management.List.UserList;
import Vehicle.example.Management.Repository.ProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepo repo;

    public ProviderList registerProvider(ProviderList provider, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            provider.setImageName(image.getOriginalFilename());
            provider.setImageType(image.getContentType());
            // You can store image bytes in DB if you want:
            // provider.setImageData(image.getBytes());
        }
        return repo.save(provider);
    }

    public List<ProviderList> getList() {
        return repo.findAll();
    }

    public ProviderList login(String ownername, String password) {
        Optional<ProviderList> providerOpt = repo.findByOwnernameAndPassword(ownername, password);
        return providerOpt.orElse(null);
    }

    public String resetPassword(String ownername, String newPassword) {
        Optional<ProviderList> userOptional = repo.findByOwnername(ownername); // use instance

        if (userOptional.isPresent()) {
            ProviderList user = userOptional.get();
            user.setPassword(newPassword); // ⚠ plain text for now
            repo.save(user); // use instance
            return "Password reset successful!";
        } else {
            return "User not found with username: " + ownername;
        }
    }
}
