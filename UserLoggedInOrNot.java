
package RequestHandlersPackage;

public class UserLoggedInOrNot {
    private static boolean Logged=false;

    public static boolean GetLogged() {
        return Logged;
    }

    public static void setLogged(boolean Logged) {
        UserLoggedInOrNot.Logged = Logged;
    }
    
}
