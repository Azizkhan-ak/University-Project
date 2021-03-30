
package RequestHandlersPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ResetPasswordHandler extends HttpServlet{
    private static boolean emailexistforresetpassword=false;
    
/////////SEARCH FOR EMAIL fORGOTPASSWORD.MAILTOUSER IN DATABASE AND UPDATE PASSWORD WITH REQ.GETPARAMETER("PASSWORD"); 
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "OFO","ofo");
            
////////////NOW FIRST RETRIVE ALL MAILS FROM DATABASE AND CHECK IF MAILTOUSER EXIST OR NOT?////////////

ArrayList emaillist=new ArrayList();

System.out.println("In Reset Password hanler...........");
Statement st=con.createStatement();
ResultSet resultset=null;
        resultset=st.executeQuery("select email from customers_signup_details");

while(resultset.next())
{
    emaillist.add(resultset.getString("email"));
}
for(int i=0;i<emaillist.size();i++)
{
    if(emaillist.get(i).equals(ForgotPasswordHandler.MailToUser))
    {
        emailexistforresetpassword=true;
        System.out.println("Email here");
        break;
    }
     
}
////////////////If this email exist reset user password and direct him to login page//////////
if(ResetPasswordHandler.emailexistforresetpassword)
{
   resultset=null;
   String passtobereset=req.getParameter("password");
   String encryptedpassword=EncryptionAndDecryption.Encrypt(new StringBuilder(passtobereset));
   
   PreparedStatement pst=con.prepareStatement("update customers_signup_details set accountpassword=? where email=?");
   pst.setString(1, encryptedpassword);
   pst.setString(2, ForgotPasswordHandler.MailToUser);
   pst.execute();
   res.setContentType("text/html");
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
"          <h1>Password Reset was successful.</h1>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
}  
else
{
    
    res.setContentType("text/html");
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
"          <h1>Failed! Account for this email does not exist in OFO.</h1>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
}
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ResetPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ResetPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ResetPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
}
