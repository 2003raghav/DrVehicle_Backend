package Vehicle.example.Management.Controller;

import Vehicle.example.Management.List.UserList;
import Vehicle.example.Management.Service.ServiceClass;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ControllerClass {

    @Autowired
    private ServiceClass service;

    @RequestMapping("/Hello")
    public String greet(){
        return "Hello";
    }

    @RequestMapping("/Lists")
    public ResponseEntity<List<UserList>> getlist(){
        return new ResponseEntity<>(service.getlist() , HttpStatus.OK);
    }

    @RequestMapping("/User/{id}")
    public ResponseEntity<UserList>getlistbyid(@PathVariable int id)
    {
        UserList lists=service.getlistbyid(id);
        if(lists!=null)
            return new ResponseEntity<>(lists,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
