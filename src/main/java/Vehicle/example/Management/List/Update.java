// Update.java
package Vehicle.example.Management.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "service_updates")
public class Update {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String step;
    private String note;
    private boolean completed;
    private String timestamp;
    private String technician;
}
