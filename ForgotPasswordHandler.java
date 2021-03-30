
package RequestHandlersPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.mail.Message;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForgotPasswordHandler extends HttpServlet {
    public static String VerificationCodeForgotPassword;
    public static String MailToUser;
    public void doPost(HttpServletRequest req,HttpServletResponse res)
    {
        try {
            System.out.println("In forgot password handler");
           System.out.println(req.getParameter("mail"));
            
             MailToUser=req.getParameter("mail");
            
            Properties p=new Properties();
            p.put("mail.transport.protocol", "smtp");
            p.put("mail.store.protocol", "imaps");
            p.put("mail.smtp.host","smtp.gmail.com");
            p.put("mail.smtp.socketFactory.port","465");
            p.put("mail.smtp.socketFactory.port","587");
            p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            p.put("mail.smtp.auth","true");
            p.put("mail.smtp.starttls.enable","true");
            p.put("mail.smtp.ssl.trust","smtp.gmail.com");
            
            Authenticator auth= new javax.mail.Authenticator()
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication("orderfoodonlineofo@gmail.com","order-food-online-ofo-1");
                    
                }
            };
            Session session=Session.getInstance(p, auth);
            
            Message message=new MimeMessage(session);
            
            message.setFrom(new InternetAddress("orderfoodonlineofo@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(MailToUser));
            message.setSubject("Verification Code From OFO");
///////////////////////NOW GENERATING RANDOM 6 DIGIT NUMBER///////////////////////////////

 Random random=new Random();
               int number=random.nextInt(999999);
               VerificationCodeForgotPassword=String.format("%06d", number);
                    message.setSentDate(new Date());
                    message.setSubject("Verification Code OFO");
                    message.setText("This code is sent to you for verification"
                            + " of your email from OrderFoodOnline"+"\n\n\n"+VerificationCodeForgotPassword);
                    Transport.send(message);
     ////////////NOW WE RESPONSE VERIFICATION CODE PAGE TO CLIENT TO VERIFY CODE SEND////////////////
     
     res.setContentType("text/html");
               PrintWriter out=res.getWriter();
               out.println("<html>");
               out.println("<head>");
               out.print(" <meta charset='UTF-8'>");
               out.print(" <meta http-equiv='X-UA-Compatible' content='IE=edge'>");
               out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
               out.println("<title>Al-Jannat</title>");
               out.println("<link rel='shortcut icon' type='image/x-icon' href='../mainPageImages/icon.png' />");
               out.println("<style>");
               out.println(" * { box-sizing: border-box;}");
               out.println(" body { margin: 0;}");
               out.println("    .container {width: 100%;height: 100vh;display: flex;justify-content: center;align-items: center;background-image: linear-gradient(to bottom right, #5f4c4c67, whitesmoke, #5f4c4c67);}");
               out.println("    .content{display: flex;flex-direction: column;flex-wrap: nowrap;width: 23rem;height: 15rem;border: 2px solid #5f4c4c70;background-color: rgba(218, 218, 218, 0.719);}");
               out.println(".heading { font-size: 26px;text-align: start; margin: 1rem 0 0.5rem 1rem;}");
               out.println("    [type='number'] {padding: 0.5rem;width: 15rem;border: 2px solid #5f4c4c;font-size: 16px;border-radius: 5px 5px 5px 5px; margin: 1rem 0 0.5rem 1rem; }");
               out.println("[type='number']:hover,[type='number']:focus {outline: none;border: 3px solid #5f4c4c;}");
               out.println("    [type=\"submit\"] {padding: 0.4rem 1rem 0.4rem 1rem; border: 2px solid #5f4c4c;font-size: 16px; border-radius: 5px 5px 5px 5px;background-color: #887575;color: white;display: block;margin-left: 1rem;}");
               out.println("[type=\"submit\"]:hover {border: 3px solid #5f4c4c;background-color: #685a5a;}");
               out.println("</style>");
               
               
               
               
               out.println("</head>");
               out.println("<body>");

               out.println("<div class='container'>");
               out.println("<div class='content'>");
               out.println(" <div class='heading'>Enter verification code.</div>");

                    out.println("      <form action=\"VerificationCodeForgotPasswordHandler\" method=\"Post\">\n" +
"        <input type=\"number\" name=\"verifycode\" placeholder=\"Verification code\">\n" +
"        <input type=\"submit\" value=\"Confirm\">\n" +
"      </form>");
               out.println("</div>");
               out.println("</div>");
               out.println("</body>");
               out.println("</html>");

                    

            
            
            
            
        } catch (AddressException ex) {
            Logger.getLogger(ForgotPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ForgotPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ForgotPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
