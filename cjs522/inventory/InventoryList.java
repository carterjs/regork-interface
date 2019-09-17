import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * Lists all inventory items (shipments)
 * 
 * @author Carter Schmalzle
 */
public class InventoryList extends ActionPage {

    /**
     * Creates inventory list ActionPage
     */
    public InventoryList() {
        super("Inventory List");
    }

    @Override
    public void activate() {

        actions = new ActionPage[] {
            new InventoryDetail()
        };

        super.activate();
    }

    @Override
    protected void run() {
        printTitle();

        // Get all inventory items
        try {
            // Create SQL statement
            Statement stmt = RegorkInterface.conn.createStatement();
            String q = "select inventory.id, listing.name, included_at_risk(batch.id) included_at_risk, included_recalled(batch.id) included_recalled, inventory.quantity, delivery_date, at_risk, recalled from inventory, batch, listing where inventory.batch = batch.id and inventory.listing = listing.id";
            ResultSet result = stmt.executeQuery(q);
            if (!result.next()) {
                // No results
                System.out.println("\nNo inventory.");
            } else {
                System.out.printf("\n%-6s%-30s%-12s%s\n\n", "ID", "Listing", "Quantity", "Delivery Date");
                do {
                    System.out.printf("%-6d%-30s%-12d%s", result.getInt("id"), result.getString("name"),
                            result.getInt("quantity"), result.getTimestamp("delivery_date").toString());
                    if(result.getInt("recalled") == 1) {
                        System.out.print(" * * Recalled * *");
                    } else if(result.getInt("included_recalled") == 1) {
                        System.out.print(" * * Contains recalled item(s) * * ");
                    } else if(result.getInt("at_risk") == 1) {
                        System.out.print(" * * At risk for recall * *");
                    } else if(result.getInt("included_at_risk") == 1) {
                        System.out.print(" * * Contains at-risk item(s) * *");
                    }
                    System.out.println("");
                } while (result.next());
            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }
}