
package RequestHandlersPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OrderItemHandler extends HttpServlet{
    
    @Override
    public void init(ServletConfig conf)
    {
        
    }
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
    {
        PreparedStatement pst;
        Connection con;
        String ItemType=null;
        String ItemNameFromForm=req.getParameter("itemname");
        String ItemPriceFromForm=req.getParameter("itemprice").substring(3).trim();
        
 //////////////////CREATING PROPERTIES OBJECT TO STORE PROPERTIES OF EMAIL/////////////////
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
          }
      };
      Session session=Session.getInstance(p,auth);
      MimeMessage msg=new MimeMessage(session);  
 ///////FIRST OF ALL WE CHECK WETHER USER IS LOGGED OR NOT,IF YES WE UPDATE DATABASE AND SEND CONFIRMATION 
 ////// OF ORDER EMAIL TO USER, IF NOT WE REDIRECT USER TO LOGIN PAGE TO LOGIN HIM FIRST//////////////////
       
        if(UserLoggedInOrNot.GetLogged())
        {
            
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                
                
                
                
                con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","OFO","ofo");
                
                
                
                System.out.println("User is logged in");
                
                
                
                
                if((ItemNameFromForm.equalsIgnoreCase("Pepsi")) ||(ItemNameFromForm.equalsIgnoreCase("Coke"))||(ItemNameFromForm.equalsIgnoreCase("Juice")||(ItemNameFromForm.equalsIgnoreCase("Pakola"))||(ItemNameFromForm.equalsIgnoreCase("Fanta"))||(ItemNameFromForm.equalsIgnoreCase("Dew"))))
                {
                    ItemType="Beverages";
                }
                else if((ItemNameFromForm.equalsIgnoreCase("Cajun Pasta")) ||(ItemNameFromForm.equalsIgnoreCase("Chiken Karahi"))||(ItemNameFromForm.equalsIgnoreCase("Kung Pao")||(ItemNameFromForm.equalsIgnoreCase("Mirchi Kebabs"))||(ItemNameFromForm.equalsIgnoreCase("Biryani"))||(ItemNameFromForm.equalsIgnoreCase("Alo Sabzi"))))
                {
                    ItemType="SpicyMeal";
                }
                else if((ItemNameFromForm.equalsIgnoreCase("Pizza")) ||(ItemNameFromForm.equalsIgnoreCase("Club Sanwich"))||(ItemNameFromForm.equalsIgnoreCase("Chiken Burger")||(ItemNameFromForm.equalsIgnoreCase("Chiken Nuggets"))||(ItemNameFromForm.equalsIgnoreCase("Fires"))||(ItemNameFromForm.equalsIgnoreCase("Tikka Breast"))))
                {
                    ItemType="FastFood";
                }
                else
                {
                    ItemType="Sweets";
                }
                
                if(ItemType.equalsIgnoreCase("Beverages"))
                {
                    pst=con.prepareStatement("select beveragetypeleft from BEVERAGES where beveragename=?");
                    pst.setString(1,ItemNameFromForm);
                    ResultSet result=pst.executeQuery();
                    result.next();
                    if(result.getInt("BeverageTypeLeft")>0)
                    {
                        System.out.println("Order processed......");
                        System.out.println(LoginHandler.LoggedInUser);
                        
///////////NOW WE UPDATE DATA IN DATABASE AND SEND EMAIL TO USER/////////////////////

pst=con.prepareStatement("update beverages set beveragetypeleft=? where beveragename=?");
pst.setInt(1, result.getInt("BeverageTypeLeft")-1);
pst.setString(2, ItemNameFromForm);
pst.execute();

msg.setFrom(new InternetAddress("orderfoodonlineofo@gmail.com"));
msg.setSubject("Confimation Email");
String str=new String(LoginHandler.LoggedInUser);
msg.setRecipient(Message.RecipientType.TO, new InternetAddress(str));
msg.setText("Hellow MR/MISS "+LoginHandler.LoggedInUser+" ! \n Your order : Name "+ItemNameFromForm+" Price "+req.getParameter("itemprice")+" placed on "+new Date()+" is being processed. In next 10 minutes you will receive confirmation call from OFO to confirm your order."+"\nIf you did not place this order reply us at orderfoodonlineofo@gmail.com to cancel your order"+"\n Thanks");
Transport.send(msg);

System.out.println("Mesage sned");
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
        "          <h1>Your order is being processed.Confirmation email has been sent to you</h1>\n" +
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
                        System.out.println("Not in Stock");
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
"          <h1>Requested Item out of stock. Please try again later.</h1>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
                        
    msg.setSubject("Item out of stock");
    msg.setFrom(new InternetAddress("orderfoodonlineofo@gmail.com"));
    msg.setRecipient(Message.RecipientType.TO,new InternetAddress("orderfoodonlineofo@gmail.com"));
    msg.setText("Item "+ItemNameFromForm+" requested by customer "+LoginHandler.LoggedInUser+" is out of stock");
    Transport.send(msg);
                    }
                    
                    
                    
                    UserLoggedInOrNot.setLogged(false);
                }
                else if(ItemType.equalsIgnoreCase("SpicyMeal"))
                {
                    pst=con.prepareStatement("select spicymealtypeleft from SPICYMEAL where spicymealname=?");
                    pst.setString(1,ItemNameFromForm);
                    ResultSet result=pst.executeQuery();
                    result.next();
                    if(result.getInt("spicymealTypeLeft")>0)
                    {
                        System.out.println("Order processed......");
                        System.out.println(LoginHandler.LoggedInUser);
                        
///////////NOW WE UPDATE DATA IN DATABASE AND SEND EMAIL TO USER/////////////////////

pst=con.prepareStatement("update spicymeal set spicymealtypeleft=? where spicymealname=?");
pst.setInt(1, result.getInt("spicymealTypeLeft")-1);
pst.setString(2, ItemNameFromForm);
pst.execute();

msg.setFrom(new InternetAddress("orderfoodonlineofo@gmail.com"));
msg.setSubject("Confimation Email");
String str=new String(LoginHandler.LoggedInUser);
msg.setRecipient(Message.RecipientType.TO, new InternetAddress(str));
msg.setText("Hellow MR/MISS "+LoginHandler.LoggedInUser+" ! \n Your order : Name "+ItemNameFromForm+" Price "+req.getParameter("itemprice")+" placed on "+new Date()+" is being processed. In next 10 minutes you will receive confirmation call from OFO to confirm your order."+"\nIf you did not place this order reply us at orderfoodonlineofo@gmail.com to cancel your order"+"\n Thanks");
Transport.send(msg);

System.out.println("Mesage sned");
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
        "          <h1>Your order is being processed.Confirmation email has been sent to you</h1>\n" +
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
                        System.out.println("Not in Stock");
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
"          <h1>Requested Item out of stock. Please try again later.</h1>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
                        
    msg.setSubject("Item out of stock");
    msg.setFrom(new InternetAddress("orderfoodonlineofo@gmail.com"));
    msg.setRecipient(Message.RecipientType.TO,new InternetAddress("orderfoodonlineofo@gmail.com"));
    msg.setText("Item "+ItemNameFromForm+" requested by customer "+LoginHandler.LoggedInUser+" is out of stock");
    Transport.send(msg);
                    }
                    
                    
                    
                    UserLoggedInOrNot.setLogged(false);
                }
                
                else if(ItemType.equalsIgnoreCase("FastFood"))
                {
                 pst=con.prepareStatement("select fastfoodtypeleft from fastfood where fastfoodname=?");
                    pst.setString(1,ItemNameFromForm);
                    ResultSet result=pst.executeQuery();
                    result.next();
                    if(result.getInt("fastfoodTypeLeft")>0)
                    {
                        System.out.println("Order processed......");
                        System.out.println(LoginHandler.LoggedInUser);
                        
///////////NOW WE UPDATE DATA IN DATABASE AND SEND EMAIL TO USER/////////////////////

pst=con.prepareStatement("update fastfood set fastfoodtypeleft=? where fastfoodname=?");
pst.setInt(1, result.getInt("fastfoodTypeLeft")-1);
pst.setString(2, ItemNameFromForm);
pst.execute();

msg.setFrom(new InternetAddress("orderfoodonlineofo@gmail.com"));
msg.setSubject("Confimation Email");
String str=new String(LoginHandler.LoggedInUser);
msg.setRecipient(Message.RecipientType.TO, new InternetAddress(str));
msg.setText("Hellow MR/MISS "+LoginHandler.LoggedInUser+" ! \n Your order : Name "+ItemNameFromForm+" Price "+req.getParameter("itemprice")+" placed on "+new Date()+" is being processed. In next 10 minutes you will receive confirmation call from OFO to confirm your order."+"\nIf you did not place this order reply us at orderfoodonlineofo@gmail.com to cancel your order"+"\n Thanks");
Transport.send(msg);

System.out.println("Mesage sned");
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
        "          <h1>Your order is being processed.Confirmation email has been sent to you</h1>\n" +
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
                        System.out.println("Not in Stock");
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
"          <h1>Requested Item out of stock. Please try again later.</h1>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
                        
    msg.setSubject("Item out of stock");
    msg.setFrom(new InternetAddress("orderfoodonlineofo@gmail.com"));
    msg.setRecipient(Message.RecipientType.TO,new InternetAddress("orderfoodonlineofo@gmail.com"));
    msg.setText("Item "+ItemNameFromForm+" requested by customer "+LoginHandler.LoggedInUser+" is out of stock");
    Transport.send(msg);
                    }
                    
                    
                    
                    UserLoggedInOrNot.setLogged(false);   
                }
                else     //////////////IF ITEM TYPE IS WEETS//////////////
                {
                    pst=con.prepareStatement("select sweetstypeleft from SWEETS where sweetsname=?");
                    pst.setString(1,ItemNameFromForm);
                    ResultSet result=pst.executeQuery();
                    result.next();
                    if(result.getInt("SWEETSTypeLeft")>0)
                    {
                        System.out.println("Order processed......");
                        System.out.println(LoginHandler.LoggedInUser);
                        
///////////NOW WE UPDATE DATA IN DATABASE AND SEND EMAIL TO USER/////////////////////

pst=con.prepareStatement("update SWEETS set SWEETStypeleft=? where sweetsname=?");
pst.setInt(1, result.getInt("sweetsTypeLeft")-1);
pst.setString(2, ItemNameFromForm);
pst.execute();

msg.setFrom(new InternetAddress("orderfoodonlineofo@gmail.com"));
msg.setSubject("Confimation Email");
String str=new String(LoginHandler.LoggedInUser);
msg.setRecipient(Message.RecipientType.TO, new InternetAddress(str));
msg.setText("Hellow MR/MISS "+LoginHandler.LoggedInUser+" ! \n Your order : Name "+ItemNameFromForm+" Price "+req.getParameter("itemprice")+" placed on "+new Date()+" is being processed. In next 10 minutes you will receive confirmation call from OFO to confirm your order."+"\nIf you did not place this order reply us at orderfoodonlineofo@gmail.com to cancel your order"+"\n Thanks");
Transport.send(msg);

System.out.println("Mesage sned");
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
        "          <h1>Your order is being processed.Confirmation email has been sent to you</h1>\n" +
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
                        System.out.println("Not in Stock");
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
"          <h1>Requested Item out of stock. Please try again later.</h1>\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
                        
    msg.setSubject("Item out of stock");
    msg.setFrom(new InternetAddress("orderfoodonlineofo@gmail.com"));
    msg.setRecipient(Message.RecipientType.TO,new InternetAddress("orderfoodonlineofo@gmail.com"));
    msg.setText("Item "+ItemNameFromForm+" requested by customer "+LoginHandler.LoggedInUser+" is out of stock");
    Transport.send(msg);
                    }
                    
                    
                    
                    UserLoggedInOrNot.setLogged(false);
                }     
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OrderItemHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(OrderItemHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AddressException ex) {
                Logger.getLogger(OrderItemHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(OrderItemHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            





        }
        else
        {
            System.out.println("User is Not logged in.");
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
"          <h1>In order to process your request you must login first. "
                  + "</h1>\n"
                  + "<a href ='login.html'>Go and login first</a>" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>\n" +
"\n" +
"");
 
        }
        
        
        
    }
}
