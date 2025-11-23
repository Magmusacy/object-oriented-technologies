package pl.edu.agh.to.school.greenmail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class GreenMailConfig {
    @Bean
    public GreenMail createGreenMail() {
        ServerSetup smtp = new ServerSetup(1025, "localhost", ServerSetup.PROTOCOL_SMTP);
        return new GreenMail(smtp);
    }
}
