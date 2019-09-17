import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Lists all products made by a specific supplier
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class SupplierProductList extends ActionPage {

    /**
     * The targer supplier id
     */
    int supplierId;

    /**
     * Creates supplier product list ActionPage
     * Ï
     * @param supplierId
     */
    public SupplierProductList(int supplierId) {
        super("Product List: Supplier #" + supplierId);

        this.supplierId = supplierId;
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ProductDetail()
        };
        super.activate();
    }

    @Override
    protected void run() {

        printTitle();

        // Get products of target supplier
        try {
            // Create SQL statement
            Statement stmt = RegorkInterface.conn.createStatement();
            String q = "select id, name from product where product.supplier = " + supplierId;
            ResultSet result = stmt.executeQuery(q);
            if (!result.next()) {
                // No results
                System.out.println ("No products.");
            } else {
                System.out.printf("\n%-6s%-30s\n\n", "ID", "Name");
                do {
                    System.out.printf("%-6d%-30s\n", result.getInt("id"), result.getString("name"));
                } while (result.next());
            }
        } catch(Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }
}