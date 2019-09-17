import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Lists all suppliers
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class SupplierList extends ActionPage {

    /**
     * Creates supplier list ActionPage
     */
    public SupplierList() {
        super("Supplier List");
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new SupplierDetail()
        };
        super.activate();
    }

    @Override
    protected void run() {

        printTitle();

        // Get all suppliers
        try {
            // Create SQL statement
            Statement stmt = RegorkInterface.conn.createStatement();
            String q = "select id, name from supplier";
            ResultSet result = stmt.executeQuery(q);

            // Print results
            if(!result.next()) {
                // No results
                System.out.println("\nNo suppliers.");
            } else {
                System.out.printf("\n%-6s%-30s\n\n", "ID", "Name");
                do {
                    System.out.printf("%-6d%-30s\n", result.getInt("id"), result.getString("name"));
                } while(result.next());
            }

        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }
        System.out.print("\n");

        selectAction();
    }

}