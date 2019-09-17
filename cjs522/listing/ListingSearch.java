import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Search listings by name
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class ListingSearch extends ActionPage {

    /**
     * Prepared SQL statement for searching listings by id
     */
    private PreparedStatement searchListings;

    /**
     * Creates listing search ActionPage
     */
    public ListingSearch() {
        super("Listing Search");

        // Prepare statement
        String query = "select id, name, price from listing where name like ?";
        try {
            searchListings = RegorkInterface.conn.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ListingDetail(),
            new ActionPage("Search again")
        };
        super.activate();
    }

    @Override
    protected void run() {
        printTitle();

        // Get search string
        System.out.print("\nSearch for a listing by name: ");
        RegorkInterface.scanner.nextLine();
        String str = RegorkInterface.scanner.nextLine();

        // Execute search
        try {
            searchListings.setString(1, "%" + str + "%");
            ResultSet result = searchListings.executeQuery();

            // Print results
            if (!result.next()) {
                System.out.println("\nNo results.");
            } else {
                System.out.printf("\n%-6s%-30s%s\n\n", "ID", "Product", "Price");
                do {
                    System.out.printf("%-6d%-30s%.2f\n", result.getInt("id"), result.getString("name"),
                            result.getFloat("price"));
                } while (result.next());
            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }

}