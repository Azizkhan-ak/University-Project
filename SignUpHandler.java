
package RequestHandlersPackage;
import javax.crypto.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public class SignUpHandler extends HttpServlet{
      
        
       public static String VerificationCode;   
       private static HttpServletRequest req;
       private static HttpServletResponse res;
                
    public void init(ServletConfig conf)
    {
      
    }
    
    
       @Override
    public void doPost(HttpServletRequest req , HttpServletResponse res) throws IOException
    {

        System.out.println("In signuphandler");
        boolean accountexist=false;

     FormData formdata=new FormData();
     formdata.setFirstNameFromSignUpForm(req.getParameter("firstname"));
     formdata.setLastNameFromSignUpForm(req.getParameter("lastname"));
     formdata.setEmailFromSignUpForm(req.getParameter("email"));
     formdata.setPhoneNumberFromSignUpForm(req.getParameter("phonenumber"));
     formdata.setAddressFromSignUpForm(req.getParameter("address"));
     formdata.setPasswordFromSignUpForm(req.getParameter("password"));
     formdata.setConfirmPasswordFromSignUpForm(req.getParameter("confirmpassword"));
     VerificationCodeHandler.setFormData(formdata);
     ////////////////////////Checking weather account with this email already exists or not?   
                
        Connection con;
        Statement st;
        ResultSet rs;
        
        ArrayList<String> emailarray=new ArrayList<String>();  // Stores Emails Retrived from Database
      
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
             con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "OFO", "ofo");
             
             st=con.createStatement();
            
             rs=st.executeQuery("select email from customers_signup_details ");
            while(rs.next())
            {
               emailarray.add(rs.getString("email"));
            }
            
            

//   /////////////////////CHECKING IF THERE IS CUSTOMERS REGISTEERED IN DATABASE////////////   
//   /////////////////////IF ACCOUNT ALREADY EXISTS/////////////////////////////////////////
           if(!(emailarray.isEmpty()))
           {   
               for(int i=0;i<emailarray.size();i++)
               {
                  if(formdata.getEmailFromSignUpForm().equals(emailarray.get(i)))
                  {
                      accountexist=true;
                      break;
                  }
                  else
                  {
                      continue;
                  }
               }
               
           }
          
           
////////////////////////////////////BOOLEAN RESULT FOR ACCOUNT EXIST OR NOT/////////////////            
            if(accountexist)
            {
                System.out.println("account already exists");
                 res.sendRedirect("login.html");
            }
            else
            {
               System.out.println("Account does not exist");
               
// /////////////////////////////NOW WE HAVE TO DO EMAIL VERFICATION IN ORDER TO VERIFY EMAIL AND ADD THESE/////
// /////////////////////////////DETAILS IN DATABASE////////////////////////////////////////////////////////////
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
              return new PasswordAuthentication("orderfoodonlineofo@gmail.com","order-food-online-ofo-1");
          };
      };
     
      Session session=Session.getInstance(p, auth);
      Message msg=new MimeMessage(session);
                try {
                    msg.setFrom(new InternetAddress("orderfoodonlineofo@gmail.com"));
                    msg.setRecipient(Message.RecipientType.TO,new InternetAddress(formdata.getEmailFromSignUpForm()));
//         ////////////////////////////GENERATING RANDOM 6 DIGIT CODE FOR EMAIL VERIFICATION////////////////////
               Random random=new Random();
               int number=random.nextInt(999999);
               VerificationCode=String.format("%06d", number);
                    msg.setSentDate(new Date());
                    msg.setSubject("Verification Code OFO");
                    msg.setText("This code is sent to you for verification"
                            + " of your email from OrderFoodOnline"+"\n\n\n"+VerificationCode);
                    Transport.send(msg);
                    
////////////////////////NOW AFTER SENDING VERIFICATION CODE USER WILL BE PROMPTED TO ENTER VERIFICATION TO VERIFY EMAIL//////////////////
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

                    out.println("      <form action=\"VerificationCode\" method=\"Post\">\n" +
"        <input type=\"number\" name=\"verifycode\" placeholder=\"Verification code\">\n" +
"        <input type=\"submit\" value=\"Confirm\">\n" +
"      </form>");
               out.println("</div>");
               out.println("</div>");
               out.println("</body>");
               out.println("</html>");
               
               
                } catch (AddressException ex)
                {
                    Logger.getLogger(SignUpHandler.class.getName()).log(Level.SEVERE, null, ex);
                }catch(AuthenticationFailedException ex)
                {
                    
                }
                catch (MessagingException ex)
                {
                    Logger.getLogger(SignUpHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
      
      }
 
            
/////////////////////////////////////////////////////////////////////////////////////////////
//
//            
        } catch (ClassNotFoundException ex) {
           System.out.println(ex.getMessage());
        } catch (InstantiationException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        

    }
    public void destroy()
    {
      
    }

}
