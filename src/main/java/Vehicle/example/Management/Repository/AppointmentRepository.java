package Vehicle.example.Management.Repository;

import Vehicle.example.Management.List.Appointment;
import Vehicle.example.Management.List.UserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser(UserList user);
    List<Appointment> findByProviderId(int providerId);
    List<Appointment> findByProviderOwnername(String ownerName);

}
