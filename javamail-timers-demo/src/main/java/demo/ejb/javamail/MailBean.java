package demo.ejb.javamail;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class MailBean {

	@Resource(name = "java:jboss/mail/Default")
	private Session session;

	public void sendMail(String mailToAddresses, String mailSubject, String mailText) throws AddressException, MessagingException {
		Message message = new MimeMessage(session);
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailToAddresses));
		message.setSubject(mailSubject);
		message.setText(mailText);
		Transport.send(message);
	}

}