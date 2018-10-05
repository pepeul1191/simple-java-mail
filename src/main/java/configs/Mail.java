package configs;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.simplejavamail.MailException;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import java.util.HashMap;
import java.util.Map;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Mail {
  private Mailer mailer;
  private Email email;
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

  public void setBodyBlank(String partialFile, String language, Map<String, String> localsPartial){
    Config partialConfig = ConfigFactory.parseResources("templates/partial_" + partialFile + ".conf");

    StrSubstitutor sub = new StrSubstitutor(localsPartial, "%(", ")");
    String partialYield = sub.replace(partialConfig.getString("yield." + language));

    Map<String, String> localsLayout = new HashMap<String, String>();
    localsLayout.put("yield", partialYield);

    Config templateConfig = ConfigFactory.parseResources("templates/layout_blank.conf");
    sub = new StrSubstitutor(localsLayout, "%(", ")");
    String body = sub.replace(templateConfig.getString("layout"));

    System.out.println("1 ++++++++++++++++++++++++++++++");
    System.out.println(body);
    System.out.println("2 ++++++++++++++++++++++++++++++");
  }

  public Email getEMail() {
    return this.email;
  }

  public void setEMail(String userName, String destinationName, String destiationEmail, String ccName, String subjectName, String language) {
    String demo = this.config.getString("email.from." + userName + ".name");
    String mail = this.config.getString("email.from." + userName + ".mail");
    String cc = this.config.getString("email.cc." + ccName);
    String subject = this.config.getString("email.subject." + subjectName + "." + language);
    this.email = EmailBuilder.startingBlank()
      .from(demo, mail)
      .to(destinationName, destiationEmail)
      .cc(cc)
      .withSubject(subject)
      .withHTMLText(this.body)
      .buildEmail();
  }
}
