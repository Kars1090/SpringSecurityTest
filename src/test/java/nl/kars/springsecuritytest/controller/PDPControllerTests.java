package nl.kars.springsecuritytest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kars.springsecuritytest.UserDetailsStub;
import nl.kars.springsecuritytest.configuration.SecurityConfiguration;
import nl.kars.springsecuritytest.filter.JwtRequestFilter;
import nl.kars.springsecuritytest.service.MyUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfiguration.class)
@RunWith(SpringRunner.class)
@WebMvcTest(PDPController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PDPControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean(name = "userDetailsService")
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @PostConstruct
    public void setup() {
        given(userDetailsService.loadUserByUsername(anyString()))
                .willReturn(new UserDetailsStub());
    }

    @Test
    @WithUserDetails(value = "username", userDetailsServiceBeanName = "userDetailsService")
    public void testAuthentication() throws Exception {
        mvc.perform(get("/pdps/authentication").secure(true)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRoot() throws Exception {
        mvc.perform(get("/pdps").secure(true)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
