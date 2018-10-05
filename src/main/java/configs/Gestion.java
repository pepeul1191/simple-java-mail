package configs;

import java.util.HashMap;
import java.util.Map;

import org.simplejavamail.MailException;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

public class Gestion {
    public void sendMail(){
        
        Mailer mailer = MailerBuilder
            .withSMTPServer("mail.softweb.pe", 25, "demo@softweb.pe", "demo123")
            .withSessionTimeout(10)
            .withTransportStrategy(TransportStrategy.SMTP_TLS)
            .clearEmailAddressCriteria() // turns off email validation
            .withDebugLogging(true)
            .buildMailer();
        System.out.println("1 +++++++++++++++++++++++++++++++++++++++++");
        Email email = EmailBuilder.startingBlank()
            .from("José Valdivia Caballero", "demo@softweb.pe")
            .to("C. Cane", "info@softweb.pe")
            .cc("C. Bo <pepe.valdivia.caballero@outlook.com>")
            .withSubject("hey")
            .withPlainText("Caracter Google o Outlook, no es SPAM! ;)")
            .buildEmail();
        System.out.println("2 +++++++++++++++++++++++++++++++++++++++++");
        try {
           mailer.sendMail(email);
        } catch (MailException e) {
            e.printStackTrace();
        }
        System.out.println("3 +++++++++++++++++++++++++++++++++++++++++");
    }

    public void mailConstructor(){
        // setting local email local variables
        Map<String, String> locals = new HashMap<String, String>();
        locals.put("nombre", "Pips Valdivia");
        // creating email
        Mail mail = new Mail("blank", "demo", "en");
        mail.setMailer("demo");
        mail.setEMail("demo", "C. Tevez", "info@softweb.pe", "pepe", "wellcome", "en", locals);
        //System.out.println(mail.getBody());
        // send
        mail.sendMail();
    }
}
