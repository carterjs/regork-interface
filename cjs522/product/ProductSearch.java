import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Searches products by id
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class ProductSearch extends ActionPage {

    /**
     * Prepared SQL statement for searching products by id
     */
    PreparedStatement searchProducts;

    /**
     * Creates product search ActionPage
     */
    public ProductSearch() {
        super("Product Search");
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ProductDetail(),
            new ActionPage("Search again")
        };
        // Prepare statement
        String query = "select product.id, product.name, supplier.name supplier from product, supplier where product.supplier = supplier.id and product.name like ?";
        try {
            searchProducts = RegorkInterface.conn.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.activate();
    }

    @Override
    protected void run() {

        printTitle();

        // Get search string
        System.out.print("\nSearch for a product by name: ");
        RegorkInterface.scanner.nextLine();
        String str = RegorkInterface.scanner.nextLine();

        // Execute search
        try {
            searchProducts.setString(1, "%" + str + "%");
            ResultSet result = searchProducts.executeQuery();

            // Print results
            if (!result.next()) {
                System.out.println("\nNo results.");
            } else {
                System.out.printf("\n%-6s%-30s%-30s\n\n", "ID", "Product", "Supplier");
                do {
                    System.out.printf("%-6d%-30s%-30s\n", result.getInt("id"), result.getString("name"),
                            result.getString("supplier"));
                } while (result.next());
            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }

}