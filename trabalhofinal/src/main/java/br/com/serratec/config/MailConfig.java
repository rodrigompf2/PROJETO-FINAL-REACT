package br.com.serratec.config;

import br.com.serratec.controller.PerfilController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {

    private final PerfilController perfilController;
	
	@Autowired
	private JavaMailSender javaMailSender;


    MailConfig(PerfilController perfilController) {
        this.perfilController = perfilController;
    }
	
	
	public void enviarEmail(String para, String assunto, String texto) {
		SimpleMailMessage message = new SimpleMailMessage();	
		message.setFrom("marcosmuniz815@gmail.com");
		message.setTo(para);
		message.setSubject(assunto);
		message.setText("Dados do usuario:\n" + texto + "\n\n\n Residência de Software");
		javaMailSender.send(message);
	}

}
