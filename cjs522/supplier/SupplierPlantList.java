import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Lists the plants belonging to a specific supplier
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class SupplierPlantList extends ActionPage {

    /**
     * The target supplier id
     */
    int supplierId;

    /**
     * Creates supplier plant list ActionPage
     * 
     * @param supplierId the target supplier id
     */
    public SupplierPlantList(int supplierId) {
        super("Plant List: Supplier #" + supplierId);

        this.supplierId = supplierId;
    }

    @Override
    protected void run() {

        System.out.println("Plants operated by supplier #" + supplierId);
        // Get all plants belonging to target supplier
        try {
            // Create SQL statement
            Statement stmt = RegorkInterface.conn.createStatement();
            String q = "select address, city, state, zip from plant where plant.supplier = " + supplierId;
            ResultSet result = stmt.executeQuery(q);
            if (!result.next()) {
                // No results
                System.out.println("\nNo plants.");
            } else {
                do {
                    System.out.println("\n" + result.getString("address"));
                    System.out.print(result.getString("city") + ", ");
                    System.out.print(result.getString("state") + " ");
                    System.out.print(result.getInt("zip") + "\n\n");
                } while (result.next());
            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

    }
}