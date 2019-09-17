import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Lists all products
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class ProductList extends ActionPage {

    /**
     * Creates product list ActionPage
     */
    public ProductList() {
        super("Product List");
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

        try {
            Statement stmt = RegorkInterface.conn.createStatement();
            String q = "select product.id, product.name, supplier.name supplier from product, supplier where product.supplier = supplier.id";
            ResultSet result = stmt.executeQuery(q);
            if (!result.next()) System.out.println ("\nNo products.");
            else {
                System.out.printf("\n%-6s%-30s%-30s\n\n", "ID", "Product", "Supplier");
                do {
                    System.out.printf("%-6d%-30s%-30s\n", result.getInt("id"), result.getString("name"), result.getString("supplier"));
                } while (result.next());
            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }
}