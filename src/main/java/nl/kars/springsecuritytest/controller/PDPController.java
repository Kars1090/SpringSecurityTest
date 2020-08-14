package nl.kars.springsecuritytest.controller;

import nl.kars.springsecuritytest.UserDetailsStub;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/pdps")
public class PDPController {

    @GetMapping()
    public String test() {
        return "test";
    }

    @GetMapping("/authentication")
    public String testAuthentication(Authentication authentication) {
        UserDetailsStub userDetailsStub = (UserDetailsStub) authentication.getPrincipal();
        return userDetailsStub.getUsername();
    }


}
