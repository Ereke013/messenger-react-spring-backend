package kz.csse.javaProject.models;


import kz.csse.javaProject.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String email;
    private String fullName;
    private String ava;
    private String password;
    private List<Roles> roles;


//    private  String jwtToken;

//    public JwtResponse(String jwtToken){
//        this.jwtToken = jwtToken;
//    }
//
//    public String getJwtToken(){
//        return this.jwtToken;
//    }

}
