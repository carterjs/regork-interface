import java.util.Scanner;
import java.sql.*;

public class RegorkInterface {

    public static Scanner scanner;
    public static Connection conn;
    public static String role;

    public static void main(String[] args) {

        // Instantiate scanner
        scanner = new Scanner(System.in);

        // Greet
        System.out.println("\n\n\nWelcome to the Regork database interface!");
        System.out.println("Please provide valid credentials below.");

        boolean success = false;
        do {
            System.out.print("\nUsername: ");
            String username = RegorkInterface.scanner.next();
            System.out.print("Password: ");
            String password = RegorkInterface.scanner.next();
            try (
                Connection newConn=DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",username,password);
            ) {
                // Connection was succssful
                success = true;

                newConn.setAutoCommit(false);

                //Save static connction for connected action pages
                conn = newConn;
                
                // Enter role selector page
                RoleSelector rs = new RoleSelector();
                rs.activate();

            } catch (Exception e) {
                handleException(e);
                // Connection failed
                System.out.print("Login failed. Do you want to try again? (y/n): ");
                if(scanner.next().charAt(0) != 'y') {
                    break;
                }
            } finally {
                // Close connection
                try {
                    conn.close();
                } catch(Exception e) {
                    // Wasn't connected
                }
            }
        } while(!success);
    }

    /**
     * Handles all SQL errors throughout the application
     * 
     * @param e the exception thrown
     */
    public static void handleException(Exception e) {
        System.out.println("\nUh oh... " + e.getMessage());
    }
}