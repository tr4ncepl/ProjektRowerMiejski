package rental;

import rental.MessageBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;



@Named("mailSender")
@Stateless
public class MailSenderBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String lastname;
    private String mail;
    private String contents;
    private String theme;
    private String date;
    private String car;
    private String hour;
    private String tel;

    private boolean rodo;






    @EJB
    private MessageBean messageBean;

    public String getMessageBean() {return messageBean.getContent();}


    public MailSenderBean() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }




public void reset()
{
    setName(null);
    setLastname(null);
    setTel(null);
    setContents(null);
    setMail(null);
    setRodo(false);
}




    public void sendEmail() {
        //final String username = "accouasn1234@gmail.com";
        //final String password = "Test1234%";


        if(name.isEmpty()
                ||lastname.isEmpty()
                ||mail.isEmpty()
                ||contents.isEmpty()
        )
        {
            messageBean.setContent("Wszystkie pola musza byc uzupelnione");
            return;
        }
        if(mail.contains("@"))
        {

        }
        else

        {
            messageBean.setContent("Niepoprawny adres email(brak @)");
            return;
        }

        if(rodo == true)
        {

        }
        else
        {
            messageBean.setContent("Musisz akceptować warunki RODO");
            return;
        }



        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.socketFactory.port", "587");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("accouasn1234@gmail.com"));


                message.setRecipient(Message.RecipientType.TO, new InternetAddress("accouasn1234@gmail.com"));
                message.setSubject(theme+"  "+name+" "+lastname);




            message.setContent(message, "text/html");
            message.setText("Dane "+name+  " "+lastname+"\n Adres email : "+mail+"\n Telefon Kontaktowy: "+tel+"\n\n Treść wiadomości : "+contents);



            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "accouasn1234", "Test1234%");

            transport.sendMessage(message, message.getAllRecipients());
            messageBean.setContent("Pomyślnie wysłano wiadomość \n\n");

        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }
    }


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean isRodo() {
        return rodo;
    }

    public void setRodo(boolean rodo) {
        this.rodo = rodo;
    }
}
