package Vehicle.example.Management.Repository;

import Vehicle.example.Management.List.UserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserList,Integer> {
    UserList findByusernameAndPassword(String username, String password);
}
