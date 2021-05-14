package kz.csse.javaProject.rest;

import kz.csse.javaProject.entities.Users;
import kz.csse.javaProject.jwt.JwtTokenGenerator;
import kz.csse.javaProject.models.JwtRequest;
import kz.csse.javaProject.models.JwtResponse;
import kz.csse.javaProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class JwtAuthController {

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/auth")
    public ResponseEntity<?> auth(@RequestBody JwtRequest request) throws Exception{
       authenticate(request.getEmail(),request.getPassword());
       final UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
       final Users user= userService.getUserByEmail(request.getEmail());
       final String token = jwtTokenGenerator.generateToken(userDetails);
        System.out.println("Whatapp");
//       return  ResponseEntity.ok(new JwtResponse(token));

        return new ResponseEntity<>(new JwtResponse(user.getId(), user.getEmail(), user.getFullName(), user.getPassword(), user.getAva(), user.getRoles(),token), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> toRegister(@RequestBody Users user){
        Users newUser = new Users();
        System.out.println("USER NAME: " + user.getFullName());
        newUser.setFullName(user.getFullName());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setAva(user.getAva());
        if (userService.createuser(newUser)!= null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.ok(user);
    }



    public void authenticate(String email, String password) throws Exception{

        try{

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }

    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users) authentication.getPrincipal();
            return user;
        }
        return null;
    }




}
