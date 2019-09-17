import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Displays all available information for a listing
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class ListingDetail extends ActionPage {

    /**
     * Prepared SQL statement for selecting listing by id
     */
    private PreparedStatement selectListing;

    /**
     * The id of the selected listing
     */
    private int id;

    /**
     * Whether or not the id returned a listing
     */
    private boolean valid;

    /**
     * Creates inventory detail ActionPage
     */
    public ListingDetail() {
        super("Select Listing");
    }
    
    @Override
    public void activate() {
         // Permissions
         if(RegorkInterface.role != null && RegorkInterface.role.equals("Regork Manager")) {
            this.actions = new ActionPage[] {
                new ActionPage("Edit")
            };
        }
         // Prepare statement - select by listing id
         String query = "select name, price from listing where id = ?";
         try {
             selectListing = RegorkInterface.conn.prepareStatement(query);
         } catch (Exception e) {
             e.printStackTrace();
         }
         // Get user input
        System.out.print("\nType the ID of the listing you wish to select: ");
        if (RegorkInterface.scanner.hasNextInt()) {

            // Store id
            id = RegorkInterface.scanner.nextInt();

        }
        super.activate();

    }

    @Override
    protected void run() {

        // Execute prepared statement
        try {

            selectListing.setInt(1, id);
            ResultSet result = selectListing.executeQuery();

            if (!result.next()) {
                // No results
                System.out.println("\nListing not found");
                return;
            } else {
                // The id entered was valid
                valid = true;
                System.out.println("\nID: " + id);
                System.out.println("Name: " + result.getString("name"));
                System.out.printf("Price: $%.2f\n", result.getFloat("price"));

            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }

    @Override
    protected void runAction(int index) {
        if(actions[index].title.equals("Edit") && valid) {
            ListingEditor le = new ListingEditor(id);
            le.activate();
            System.out.println("\nUpdated data: ");
            run();
        } else if(actions[index].title.equals("View")) {
            run();
        }
    }
}