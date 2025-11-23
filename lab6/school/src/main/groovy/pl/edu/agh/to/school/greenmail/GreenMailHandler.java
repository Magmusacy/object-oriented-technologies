package pl.edu.agh.to.school.greenmail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import jakarta.annotation.PreDestroy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Profile("dev")
public class GreenMailHandler {
    private final GreenMail greenMail;

    public GreenMailHandler(GreenMail greenMail) {
        this.greenMail = greenMail;
        greenMail.start();
    }

    @PreDestroy
    private void showAllGatheredEmails() throws MessagingException {
        for (MimeMessage message : greenMail.getReceivedMessages()) {
            String formattedMessage = "From: " + Arrays.toString(message.getFrom()) +
                    " | Subject: " + message.getSubject() +
                    " | Body: " + GreenMailUtil.getBody(message);
            System.out.println(formattedMessage);
        }
        greenMail.stop();
    }
}
