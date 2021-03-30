
package RequestHandlersPackage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

public class ContactUsHandler extends HttpServlet{
   
    String subject;
    String message;
    String email;
    String password;
    @Override
    public void init(ServletConfig conf)
    {
        
    }
    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException
    {
        
      this.subject=req.getParameter("subject");
      this.message =req.getParameter("message");
      this.email=req.getParameter("email");
      this.password=req.getParameter("password");
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
      
      
      
      Authenticator auth=new Authenticator()
              {
                  @Override
                  protected PasswordAuthentication getPasswordAuthentication()
              {
                  return new PasswordAuthentication(email,password);
              }
              };
       
        Session session=Session.getInstance(p,auth);
       
        Message msg=new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(this.email));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("orderfoodonlineofo@gmail.com"));
            msg.setSubject(this.subject);
            msg.setText(this.message);
            Transport.send(msg);
            
           PrintWriter out=res.getWriter();
           out.println("<!DOCTYPE html>\n" +
"<html lang=\"en\">\n" +
"  <head>\n" +
"    <meta charset=\"UTF-8\" />\n" +
"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
"    <title>Al-Jannat</title>\n" +
"    <link\n" +
"      rel=\"shortcut icon\"\n" +
"      type=\"image/x-icon\"\n" +
"      href=\"mainPageImages/icon.png\"\n" +
"    />\n" +
"    <style>\n" +
"      * {\n" +
"        box-sizing: border-box;\n" +
"      }\n" +
"      body {\n" +
"        margin: 0;\n" +
"      }\n" +
"      .container {\n" +
"        width: 100%;\n" +
"        height: 100vh;\n" +
"        display: flex;\n" +
"        flex-direction: column;\n" +
"        align-items: center;\n" +
"        justify-content: center;\n" +
"        background-image: linear-gradient(\n" +
"          to bottom right,\n" +
"          #5f4c4c67,\n" +
"          whitesmoke,\n" +
"          #5f4c4c67\n" +
"        );\n" +
"      }\n" +
"      .content {\n" +
"        width: 20rem;\n" +
"        height: 60%;\n" +
"      }\n" +
"      .img {\n" +
"        width: 100%;\n" +
"        height: 80%;\n" +
"        background-image: url(\"mainPageImages/signupsuccessfull.png\");\n" +
"        background-repeat: no-repeat;\n" +
"        background-position: left 60% top 0%;\n" +
"      }\n" +
"      .message {\n" +
"        width: 100%;\n" +
"        text-align: center;\n" +
"      }\n" +
"      @media (max-width: 21rem) {\n" +
"        .content {\n" +
"          width: 13rem;\n" +
"          height: 35%;\n" +
"        }\n" +
"        .img{\n" +
"          background-size: 100%;\n" +
"        }\n" +
"      }\n" +
"    </style>\n" +
"  </head>\n" +
"  <body>\n" +
"    <div class=\"container\">\n" +
"      <div class=\"content\">\n" +
"        <div class=\"img\">\n" +
"        </div>\n" +
"        <div class=\"message\">\n" +
"          <h1>Email sent.OFO will respond back soon</h1>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
            
            
        } catch (AddressException ex) {
            Logger.getLogger(ContactUsHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ContactUsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
    {
     this.subject=req.getParameter("subject");
      this.message =req.getParameter("message");
      this.email=req.getParameter("email");
      this.password=req.getParameter("password");
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
      
      
      
      Authenticator auth=new Authenticator()
              {
                  @Override
                  protected PasswordAuthentication getPasswordAuthentication()
              {
                  return new PasswordAuthentication(email,password);
              }
              };
       
        Session session=Session.getInstance(p,auth);
       
        Message msg=new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(this.email));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("orderfoodonlineofo@gmail.com"));
            msg.setSubject(this.subject);
            msg.setText(this.message);
            Transport.send(msg);
             
           PrintWriter out=res.getWriter();
           out.println("<!DOCTYPE html>\n" +
"<html lang=\"en\">\n" +
"  <head>\n" +
"    <meta charset=\"UTF-8\" />\n" +
"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
"    <title>Al-Jannat</title>\n" +
"    <link\n" +
"      rel=\"shortcut icon\"\n" +
"      type=\"image/x-icon\"\n" +
"      href=\"mainPageImages/icon.png\"\n" +
"    />\n" +
"    <style>\n" +
"      * {\n" +
"        box-sizing: border-box;\n" +
"      }\n" +
"      body {\n" +
"        margin: 0;\n" +
"      }\n" +
"      .container {\n" +
"        width: 100%;\n" +
"        height: 100vh;\n" +
"        display: flex;\n" +
"        flex-direction: column;\n" +
"        align-items: center;\n" +
"        justify-content: center;\n" +
"        background-image: linear-gradient(\n" +
"          to bottom right,\n" +
"          #5f4c4c67,\n" +
"          whitesmoke,\n" +
"          #5f4c4c67\n" +
"        );\n" +
"      }\n" +
"      .content {\n" +
"        width: 20rem;\n" +
"        height: 60%;\n" +
"      }\n" +
"      .img {\n" +
"        width: 100%;\n" +
"        height: 80%;\n" +
"        background-image: url(\"mainPageImages/signupsuccessfull.png\");\n" +
"        background-repeat: no-repeat;\n" +
"        background-position: left 60% top 0%;\n" +
"      }\n" +
"      .message {\n" +
"        width: 100%;\n" +
"        text-align: center;\n" +
"      }\n" +
"      @media (max-width: 21rem) {\n" +
"        .content {\n" +
"          width: 13rem;\n" +
"          height: 35%;\n" +
"        }\n" +
"        .img{\n" +
"          background-size: 100%;\n" +
"        }\n" +
"      }\n" +
"    </style>\n" +
"  </head>\n" +
"  <body>\n" +
"    <div class=\"container\">\n" +
"      <div class=\"content\">\n" +
"        <div class=\"img\">\n" +
"        </div>\n" +
"        <div class=\"message\">\n" +
"          <h1>Email sent.OFO will respond back soon</h1>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
            
        } catch (AddressException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException ex) {
            PrintWriter out=res.getWriter();
            out.println("<!DOCTYPE html>\n" +
"<html lang=\"en\">\n" +
"  <head>\n" +
"    <meta charset=\"UTF-8\" />\n" +
"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
"    <title>Al-Jannat</title>\n" +
"    <link\n" +
"      rel=\"shortcut icon\"\n" +
"      type=\"image/x-icon\"\n" +
"      href=\"mainPageImages/icon.png\"\n" +
"    />\n" +
"    <style>\n" +
"      * {\n" +
"        box-sizing: border-box;\n" +
"      }\n" +
"      body {\n" +
"        margin: 0;\n" +
"      }\n" +
"      .container {\n" +
"        width: 100%;\n" +
"        height: 100vh;\n" +
"        display: flex;\n" +
"        flex-direction: column;\n" +
"        align-items: center;\n" +
"        justify-content: center;\n" +
"        background-image: linear-gradient(\n" +
"          to bottom right,\n" +
"          #5f4c4c67,\n" +
"          whitesmoke,\n" +
"          #5f4c4c67\n" +
"        );\n" +
"      }\n" +
"      .content {\n" +
"        width: 20rem;\n" +
"        height: 60%;\n" +
"      }\n" +
"      .img {\n" +
"        width: 100%;\n" +
"        height: 80%;\n" +
"        background-image: url(\"mainPageImages/signupnotsuccessful.png\");\n" +
"        background-repeat: no-repeat;\n" +
"        background-position: left 60% top 0%;\n" +
"        background-size: 100%;\n" +
"      }\n" +
"      .message {\n" +
"        width: 100%;\n" +
"        text-align: center;\n" +
"      }\n" +
"      @media (max-width: 21rem) {\n" +
"        .content {\n" +
"          width: 13rem;\n" +
"          height: 35%;\n" +
"        }\n" +
"        .img{\n" +
"          background-size: 100%;\n" +
"        }\n" +
"      }\n" +
"    </style>\n" +
"  </head>\n" +
"  <body>\n" +
"    <div class=\"container\">\n" +
"      <div class=\"content\">\n" +
"        <div class=\"img\">\n" +
"        </div>\n" +
"        <div class=\"message\">\n" +
"          <h1>Username or Password incorrect...</h1>\n"
                    + "<a href='contactus.html'>Try Again</a>" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(ContactUsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    @Override
    public void destroy()
    {
        
    }
   
    
}
