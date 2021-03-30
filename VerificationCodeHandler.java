
package RequestHandlersPackage;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class VerificationCodeHandler extends HttpServlet{
    
    static FormData formdata;
    public  static FormData getFormData()
    {
        return VerificationCodeHandler.formdata;
    }
    public static void setFormData(FormData fd)
    {
        formdata=fd;
    }
    
    public void init(ServletConfig conf)
    {
        
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        String encryptedpassword;
        try {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(VerificationCodeHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(VerificationCodeHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(VerificationCodeHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","OFO","ofo");
            
          
            String VerificationCodeEntered=req.getParameter("verifycode");
            if(VerificationCodeEntered.equals(SignUpHandler.VerificationCode))
            {
                System.out.println("Seccess");
/////////////////////NOW WE FORWARD ALL DATA TO DATABASE TO STORE IT. /////////////////////////
////////////////////FIRST LET US ENCRYPT PASSWORD//////////////////////////////////////////////

StringBuilder sb=new StringBuilder(formdata.getPasswordFromSignUpForm());
encryptedpassword=EncryptionAndDecryption.Encrypt(sb);
    PreparedStatement pst=con.prepareStatement("insert into customers_signup_details(firstname,lastname,email,phonenumber,address,accountpassword)values(?,?,?,?,?,?)");
    pst.setString(1,formdata.getFirstNameFromSignUpForm());
    pst.setString(2,formdata.getLastNameFromSignUpForm());
    pst.setString(3,formdata.getEmailFromSignUpForm());
    pst.setBigDecimal(4,BigDecimal.valueOf(Long.valueOf(formdata.getPhoneNumberFromSignUpForm())));
    pst.setString(5,formdata.getAddressFromSignUpForm());
    pst.setString(6,encryptedpassword);
    pst.executeUpdate();
    con.close();
    

res.sendRedirect("showaftersignupifsuccessfull.html");
            }
            else
            {
                System.out.println("Verification code did not match.....");
                res.sendRedirect("showaftersignupifnotsuccessful.html");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VerificationCodeHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 

        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(VerificationCodeHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void destroy()
    {
        
    }
}
