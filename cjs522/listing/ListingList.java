import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Lists all listings
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class ListingList extends ActionPage {

    /**
     * Creates listing list ActionPage
     */
    public ListingList() {
        super("Listing List");
    }

    @Override
    public void activate() {
        actions = new ActionPage[] { 
            new ListingDetail() 
        };
        super.activate();
    }

    @Override
    protected void run() {
        printTitle();

        // Get all listings
        try {
            // Create SQL statement
            Statement stmt = RegorkInterface.conn.createStatement();
            String q = "select id, name, price from listing";
            ResultSet result = stmt.executeQuery(q);
            if (!result.next()) {
                // No results
                System.out.println("\nNo listings.");
            } else {
                // Print listing table
                System.out.printf("\n%-6s%-30s%s\n\n", "ID", "Name", "Price");
                do {
                    System.out.printf("%-6d%-30s$%.2f\n", result.getInt("id"), result.getString("name"),
                            result.getFloat("price"));
                } while (result.next());
            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }
}