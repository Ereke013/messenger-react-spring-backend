package kz.csse.javaProject.models;

import kz.csse.javaProject.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 987654321L;


    private Long id;
    private String email;
    private String fullName;
    private String password;
    private String ava;
    private List<Roles> roles;
    private String jwtToken;

//    public JwtResponse(String jwtToken){
//        this.jwtToken = jwtToken;
//    }

    public String getJwtToken(){
        return this.jwtToken;
    }


}
