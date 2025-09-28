package Vehicle.example.Management.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "provider_list")
@AllArgsConstructor
@NoArgsConstructor
public class ProviderList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String garagename;
    private String ownername;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGaragename() {
        return garagename;
    }

    public void setGaragename(String garagename) {
        this.garagename = garagename;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getGarageaddress() {
        return garageaddress;
    }

    public void setGarageaddress(String garageaddress) {
        this.garageaddress = garageaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(long phoneno) {
        this.phoneno = phoneno;
    }

    public String getSpecializations() {
        return specializations;
    }

    public void setSpecializations(String specializations) {
        this.specializations = specializations;
    }

    public String getAvailableservices() {
        return availableservices;
    }

    public void setAvailableservices(String availableservices) {
        this.availableservices = availableservices;
    }

    private String garageaddress;
    private String password;
    private String email;
    private long phoneno;
    private String specializations;
    private String availableservices;

    private String imageName;
    private String imageType;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

}
