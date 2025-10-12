package Vehicle.example.Management.Service;

import Vehicle.example.Management.List.Appointment;
import Vehicle.example.Management.List.Billing;
import Vehicle.example.Management.List.ProviderList;
import Vehicle.example.Management.Repository.AppointmentRepository;
import Vehicle.example.Management.Repository.BillingRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    @Autowired
    private BillingRepo billingRepo;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public List<Billing> getBillingByUser(Long userId) {
        try {
            System.out.println("Fetching billing records for user ID: " + userId);
            List<Billing> billings = billingRepo.findByUserId(userId);
            System.out.println("Found " + billings.size() + " billing records for user " + userId);

            // Initialize lazy collections
            billings.forEach(b -> {
                if (b.getServices() != null) {
                    b.getServices().size(); // Force initialization
                }
            });
            return billings;
        } catch (Exception e) {
            System.out.println("Error fetching billing for user " + userId + ": " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public List<Billing> getBillingByProvider(String providerName) {
        List<Billing> billings = billingRepo.findByProviderName(providerName);

        // Initialize lazy collections
        billings.forEach(b -> {
            if (b.getServices() != null) {
                b.getServices().size(); // Force initialization
            }
        });

        return billings;
    }

    public Billing saveBilling(Billing billing) {
        return billingRepo.save(billing);
    }

    @Transactional
    public Billing updatePaymentStatus(Long id, String paymentStatus) {
        Optional<Billing> optionalBilling = billingRepo.findById(id);

        if (optionalBilling.isPresent()) {
            Billing billing = optionalBilling.get();
            billing.setPaymentStatus(paymentStatus);

            if ("paid".equals(paymentStatus)) {
                billing.setPaymentDate(LocalDateTime.now());
            }

            return billingRepo.save(billing);
        } else {
            throw new RuntimeException("Billing record not found with id: " + id);
        }
    }

    @Transactional
    public Billing updatePayment(Long id, String paymentStatus, String paymentMethod) {
        Optional<Billing> optionalBilling = billingRepo.findById(id);

        if (optionalBilling.isPresent()) {
            Billing billing = optionalBilling.get();
            billing.setPaymentStatus(paymentStatus);
            billing.setPaymentMethod(paymentMethod);

            if ("paid".equals(paymentStatus)) {
                billing.setPaymentDate(LocalDateTime.now());

                // Also update the associated appointment status if needed
                updateAppointmentStatusIfNeeded(billing.getAppointmentId());
            }

            return billingRepo.save(billing);
        } else {
            throw new RuntimeException("Billing record not found with id: " + id);
        }
    }

    // Helper method to update appointment status when payment is completed
    private void updateAppointmentStatusIfNeeded(Long appointmentId) {
        if (appointmentId != null) {
            Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
            if (appointmentOpt.isPresent()) {
                Appointment appointment = appointmentOpt.get();
                System.out.println("Appointment " + appointmentId + " associated with paid billing");
            }
        }
    }

    public List<Billing> getBillingByAppointment(Long appointmentId) {
        List<Billing> billings = billingRepo.findByAppointmentId(appointmentId);

        // If no billing record exists, create a default one
        if (billings.isEmpty()) {
            Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

            if (appointment.isPresent() && "completed".equals(appointment.get().getStatus())) {
                Billing defaultBilling = new Billing();
                defaultBilling.setAppointmentId(appointmentId);
                defaultBilling.setPaymentStatus("pending");
                defaultBilling.setTotalAmount(0.0);

                // Set other necessary fields
                Appointment apt = appointment.get();
                defaultBilling.setVehicleName(apt.getVehicleName());
                defaultBilling.setVehicleNumber(apt.getVehicleNumber());

                // Get user ID from the User object
                if (apt.getUser() != null) {
                    defaultBilling.setUserId(apt.getUser().getId());
                }

                // Get provider information from the ProviderList object
                if (apt.getProvider() != null) {
                    ProviderList provider = apt.getProvider();
                    defaultBilling.setProviderId((long) provider.getId());

                    // Use garagename as provider name (or ownername if preferred)
                    if (provider.getGaragename() != null && !provider.getGaragename().isEmpty()) {
                        defaultBilling.setProviderName(provider.getGaragename());
                    } else if (provider.getOwnername() != null && !provider.getOwnername().isEmpty()) {
                        defaultBilling.setProviderName(provider.getOwnername());
                    } else {
                        defaultBilling.setProviderName("Service Provider");
                    }
                } else {
                    defaultBilling.setProviderName("Service Provider");
                }

                // Set date and time from appointment
                if (apt.getDate() != null) {
                    defaultBilling.setDate(apt.getDate().toString());
                }
                if (apt.getTime() != null) {
                    defaultBilling.setTime(apt.getTime().toString());
                }

                billings = List.of(billingRepo.save(defaultBilling));
            }
        }

        return billings;
    }
}