import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Displays all available information for an inventory item
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class InventoryDetail extends ActionPage {

    /**
     * Prepared SQL statement for selecting inventory by id
     */
    private PreparedStatement selectInventory;

    /**
     * The id of the selected inventory
     */
    private int id;

    /**
     * Creates inventory detail ActionPage
     */
    public InventoryDetail() {
        super("Select shipment");
    }
    
    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ProductDetail(),
            new ListingDetail(),
            new BatchDetail()
        };
        // Prepare statement - select by inventory id
        String query = "select inventory.id, product.id product_id, product.name product, listing.id listing_id, listing.name listing, batch, cost, listing.price price, inventory.quantity, delivery_date, included_recalled(batch.id) included_recalled, included_at_risk(batch.id) included_at_risk, at_risk, recalled from inventory, batch, product, listing where inventory.listing = listing and inventory.batch = batch.id and batch.product = product.id and inventory.id = ?";
        try {
            selectInventory = RegorkInterface.conn.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.activate();
    }

    @Override
    protected void run() {

        // Get user input
        System.out.print("\nType the ID of the shipment you wish to select: ");
        if (RegorkInterface.scanner.hasNextInt()) {

            // Store id
            id = RegorkInterface.scanner.nextInt();

            // Execute prepared statement
            try {

                selectInventory.setInt(1, id);
                ResultSet result = selectInventory.executeQuery();

                if (!result.next()) {
                    // No results
                    System.out.println("\nShipment not found");
                    return;
                } else {
                    // The id entered was valid
                    System.out.println("\nID: " + id);
                    System.out.println("Product: " + result.getString("product") + " (#" + result.getInt("product_id") + ")");
                    System.out.println("Listing: " + result.getString("listing") + " (#" + result.getInt("listing_id") + ")");
                    System.out.println("Batch: #" + result.getInt("batch"));
                    System.out.printf("Cost: $%.2f\n", result.getFloat("cost"));
                    System.out.printf("Price: $%.2f\n", result.getFloat("price"));
                    System.out.println("Quantity: " + result.getInt("quantity"));
                    System.out.printf("Delivery Date: %s\n\n", result.getTimestamp("delivery_date").toString());
                    if(result.getInt("recalled") == 1) {
                        System.out.print(" * * Recalled * *");
                    } else if(result.getInt("included_recalled") == 1) {
                        System.out.print(" * * Contains recalled item(s) * * ");
                    } else if(result.getInt("at_risk") == 1) {
                        System.out.print(" * * At risk for recall * *");
                    } else if(result.getInt("included_at_risk") == 1) {
                        System.out.print(" * * Contains at-risk item(s) * *");
                    }
                }
            } catch (Exception e) {
                RegorkInterface.handleException(e);
            }
        }

        selectAction();
    }
}