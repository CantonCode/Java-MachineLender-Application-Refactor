package Main.Authentication.Logic;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class OTPHelper {
    public static void sendEmail(String receipt,String otp) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myAccountEmail = "cantoncodes@gmail.com";
        String password = "Coding123";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });

        Message message = prepareMessage(session,myAccountEmail,receipt,otp);

        Transport.send(message);
    }

    public static String generateDigit(){
        Random rand = new Random();


        return String.format("%04d", rand.nextInt(10000));
    }

    private static Message prepareMessage(Session session,String myAccountEmail, String receipt,String otp){

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(receipt));
            message.setSubject("Machine Lender OTP");
            message.setText("Please enter this number into the application: Machine Lender \n" + otp);
            return message;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
