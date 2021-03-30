
package RequestHandlersPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginHandler extends HttpServlet {
    public static StringBuffer LoggedInUser;
    String decryptedpassword;
    StringBuilder sb;
     boolean accountexist=false;
    ArrayList<String> EmailFromDb=new ArrayList<String>();
    ArrayList<String> PasswordFromDb=new ArrayList<String>();
    
    public void init(ServletConfig conf)
    {
        
    }
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
    {
     
        String EmailFromLoginForm=req.getParameter("loginemail");
        String PasswordFromLoginForm=req.getParameter("loginpassword");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "OFO","ofo");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select email,accountpassword from customers_signup_details");
            while(rs.next())
            {
                EmailFromDb.add(rs.getString("email"));
                PasswordFromDb.add(rs.getString("accountpassword"));
            }
            
           for(int i=0;i<EmailFromDb.size();i++)
           {
               if(EmailFromDb.get(i).equals(EmailFromLoginForm))
               {
                   accountexist=true;
                   sb=null;
                    sb=new StringBuilder(PasswordFromDb.get(i));
                    decryptedpassword=null;
                    decryptedpassword=EncryptionAndDecryption.Decrypt(sb);
                   System.out.println(decryptedpassword+"   decrypted password");
                   if(decryptedpassword.equals(PasswordFromLoginForm))
                   {
 /////////////////////Setting boolean variable true if user is logged in/////////////////////////
                       UserLoggedInOrNot.setLogged(true);
///////SETTING STRING VARIABLE TO SHOW WHICH USER IS LOGGED IN//////////////////////////////////
                    LoginHandler.LoggedInUser=new StringBuffer(EmailFromLoginForm) ;
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
"          <h1>Logged In successful.</h1>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
                      
                       break;
                   }
                   else
                   {
 ////////////////Setting boolean variable false if user is not logged in /////////////////
                       UserLoggedInOrNot.setLogged(false);
                       System.out.println("Incorrect Password");
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
"          <h1>Logged In Failed.</h1>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");

                       break;
                   }
                   
               }
           }
           if(!(accountexist))
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
"          <h1>Account with this email does not exist in OFO.</h1>\n"
                       + "<a href='signup.html'>SignUp here</a>" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
           }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void destroy()
    {
        
    }
}
