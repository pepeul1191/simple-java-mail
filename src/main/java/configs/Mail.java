package configs;

import org.simplejavamail.MailException;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Mail {
  private Mailer mailer;
  private Email mail;
  private Config config;
  private String body;

  public Mail() {
    this.config = ConfigFactory.parseResources("application.conf");
  }

  public Mailer getMailer() {
    return this.mailer;
  }

  public void setMailer(String userName) {
    String smtp = this.config.getString("mailer.smtp");
    String user = this.config.getString("mailer.users." + userName + ".user");
    String pass = this.config.getString("mailer.users." + userName + ".pass");
    this.mailer = MailerBuilder
      .withSMTPServer(smtp, 25, user, pass)
      .withSessionTimeout(10)
      .withTransportStrategy(TransportStrategy.SMTP_TLS)
      .clearEmailAddressCriteria() // turns off email validation
      .withDebugLogging(true)
      .buildMailer();
  }

  public Email getMail() {
    return this.mail;
  }

  public void setMail(Email mail) {
    Email email = EmailBuilder.startingBlank()
      .from("José Valdivia Caballero", "demo@softweb.pe")
      .to("C. Cane", "info@softweb.pe")
      .cc("C. Bo <pepe.valdivia.caballero@outlook.com>")
      .withSubject("hey")
      .withPlainText("Caracter Google o Outlook, no es SPAM! ;)")
      .buildEmail();
  }
}
