package Vehicle.example.Management.Repository;

import Vehicle.example.Management.List.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillingRepo extends JpaRepository<Billing, Long> {
        List<Billing> findByAppointmentId(Long appointmentId);
        List<Billing> findByUserId(Long userId);
        List<Billing> findByProviderName(String providerName);

}
