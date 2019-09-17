import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Search inventory items
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class InventorySearch extends ActionPage {

    /**
     * Prepared SQL statement for searching inventory by listing name
     */
    PreparedStatement searchInventory;

    /**
     * Creates inventory search ActionPage
     */
    public InventorySearch() {
        super("Inventory Search");
    }

    @Override
    public void activate() {

        actions = new ActionPage[] {
            new InventoryDetail(),
            new ActionPage("Search again")
        };
        // Prepare statement
        String query = "select inventory.id, listing.name, inventory.quantity, delivery_date from inventory, batch, listing where inventory.batch = batch.id and inventory.listing = listing.id and listing.name like ?";
        try {
            searchInventory = RegorkInterface.conn.prepareStatement(query);
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        super.activate();
    }

    @Override
    protected void run() {

        printTitle();

        // Get search string
        System.out.print("\nSearch for inventory by listing name: ");
        RegorkInterface.scanner.nextLine();
        String str = RegorkInterface.scanner.nextLine();

        // Execute search
        try {
            searchInventory.setString(1, "%" + str + "%");
            ResultSet result = searchInventory.executeQuery();

            // Print results
            if (!result.next()) {
                System.out.println("\nNo results.");
            } else {
                System.out.printf("\n%-6s%-30s%-12s%s\n\n", "ID", "Listing", "Quantity", "Delivery Date");
                do {
                    System.out.printf("%-6d%-30s%-12d%s\n", result.getInt("id"), result.getString("name"),
                            result.getInt("quantity"), result.getTimestamp("delivery_date").toString());
                } while (result.next());
            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }

}