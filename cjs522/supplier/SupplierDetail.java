import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Lists all available information on a specific supplier
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class SupplierDetail extends ActionPage {

    /**
     * A prepared SQL statement for selecting a supplier by id
     */
    PreparedStatement selectSupplier;

    /**
     * The id of the selected supplier
     */
    int id;

    /**
     * Whether or not the id returned a supplier
     */
    boolean valid;

    /**
     * Creates select supplier ActionPage
     */
    public SupplierDetail() {
        super("Select Supplier");
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ActionPage("Plants"),
            new ActionPage("Products"),
            new ActionPage("Select again")
        };

        // Prepare statement
        String query = "select id, name from supplier where id = ?";
        try {
            selectSupplier = RegorkInterface.conn.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.activate();
    }

    @Override
    protected void run() {
        
        // Prompt for supplier id
        System.out.print("\nType the ID of the supplier you wish to select: ");
        if (RegorkInterface.scanner.hasNextInt()) {
            id = RegorkInterface.scanner.nextInt();

            // Execute prepared statement
            try {
                selectSupplier.setInt(1, id);
                ResultSet result = selectSupplier.executeQuery();

                if (!result.next()) {
                    // No results
                    System.out.println("\nSupplier not found");
                    return;
                } else {
                    valid = true;
                    System.out.println("\nID: " + id);
                    System.out.println("Name: " + result.getString("name"));
                }
            } catch (Exception e) {
                RegorkInterface.handleException(e);
            }
        }

        selectAction();
    }

    @Override
    protected void runAction(int index) {
        if (actions[index].title.equals("Products") && valid) {
            SupplierProductList spl = new SupplierProductList(id);
            spl.activate();
        } else if (actions[index].title.equals("Plants")) {
            SupplierPlantList spl = new SupplierPlantList(id);
            spl.activate();
        } else {
            super.runAction(index);
            return;
        }
        selectAction();
    }
}