import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Create batches for a certain product
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class BatchCreator extends ActionPage {

    /**
     * The target product id
     */
    private int productId;

    /**
     * Creates create batch page for product with id productId
     * 
     * @param productId
     */
    public BatchCreator(int productId) {
        super("Create batch: Product #" + productId);

        this.productId = productId;
    }

    @Override
    public void run() {

        printTitle();

        // Print supplier's plants
        System.out.println("\nPlants: ");
        try {
            Statement stmt = RegorkInterface.conn.createStatement();
            String query = "select plant.id, address, city, state, zip from plant, product where product.supplier = plant.supplier and product.id = "
                    + productId;
            ResultSet result = stmt.executeQuery(query);
            int plantCount = 0;
            if (!result.next()) {
                System.out.println("\nNo plants.");
            } else {
                System.out.printf("\n%-6s%s\n\n", "ID", "Address");
                do {
                    System.out.printf("%-6d%s %s, %s %d\n", result.getInt("id"), result.getString("address"),
                            result.getString("city"), result.getString("state"), result.getInt("zip"));
                    plantCount++;
                } while (result.next());
            }

            // Insert batch
            String sql = "insert into batch (product, plant, quantity) values (" + productId + ", ?, ?)";
            PreparedStatement insertBatch = RegorkInterface.conn.prepareStatement(sql);

            if(plantCount > 0) {
                System.out.print("\nEnter plant ID: ");
                do {
                    if(RegorkInterface.scanner.hasNextInt()) {
                        int choice = RegorkInterface.scanner.nextInt();
                        if(choice > 0 && choice <= plantCount) {
                            insertBatch.setInt(1, choice);
                        }
                        break;
                    } else {
                        RegorkInterface.scanner.nextLine();
                    }
                } while(true);
            } else {
                System.out.println("This supplier doesn't have a plant to create the batch.");
                return;
            }

            System.out.print("Enter batch quantity: ");
            do {
                if(RegorkInterface.scanner.hasNextInt()) {
                    int choice = RegorkInterface.scanner.nextInt();
                    if(choice > 0) {
                        insertBatch.setInt(2, choice);
                    }
                    break;
                } else {
                    RegorkInterface.scanner.nextLine();
                }
            } while(true);

            // Run insertion
            insertBatch.executeUpdate();
            RegorkInterface.conn.commit();
            System.out.println("Batch created.");

        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

    }
}