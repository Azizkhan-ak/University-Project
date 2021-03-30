
package RequestHandlersPackage;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerificationCodeForgotPasswordHandler extends HttpServlet{
    
   @Override
   public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
   {
       if(ForgotPasswordHandler.VerificationCodeForgotPassword.equals(req.getParameter("verifycode")))
       {
           res.sendRedirect("resetpassword.html");
           

       }
       else
       {
           
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
               out.println("<h1>Type correct verification code....</h1>");
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

       }
   }
}
