// ServiceDetails.java
package Vehicle.example.Management.List;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "service_details")
public class ServiceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // map the service to user
    private String vehicleModel;
    private String licensePlate;
    private String serviceType;
    private String description;
    private String status;
    private String estimatedCompletion;
    private String costEstimate;
    private String technician;
    private String priority;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private List<Update> updates;
}
