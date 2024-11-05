package customer.demoapp.event.handler;

import cds.gen.customapi.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import customer.demoapp.services.JwtService;
import customer.demoapp.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ServiceName(Customapi_.CDS_NAME)
public class CustomEvenHandler implements EventHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @On
    public void onHello(HelloContext ctx) {
        ctx.setResult("Hello World!");
        ctx.setCompleted();
    }

    //http://localhost:8087/odata/v4/customapi/generateToken(username='admin',password='passadmin')
    @On
    public void onGenerateToken(GenerateTokenContext ctx) {
        String token = null;
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(ctx.getUsername(), ctx.getPassword()));
        if (authentication.isAuthenticated()) {
            token = jwtService.generateToken(ctx.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
        ctx.setResult(token);
        ctx.setCompleted();
    }

    @On
    public void onSignup(SignupContext ctx) {
        userInfoService.addUser(ctx.getUsername(),ctx.getPassword(),ctx.getEmail(),"USER");
        ctx.setCompleted();
    }

    @On
    public void onCurrentUser(CurrentUserContext ctx) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> map = new HashMap<>();
        map.put("user", authentication.getName());
        map.put("role", authentication.getAuthorities().toArray()[0].toString());

        String result = new ObjectMapper().writeValueAsString(map);
        ctx.setResult(result);
        ctx.setCompleted();
    }

}

