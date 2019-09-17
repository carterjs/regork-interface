import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Lists all available information on specific product
 */
public class ProductDetail extends ActionPage {

    /**
     * Prepared SQL statement for selecting product by id
     */
    private PreparedStatement selectProduct;

    /**
     * The id of the selected product
     */
    private int id;

    /**
     * Whether or not the id returned a product
     */
    private boolean valid;

    /**
     * Creates select product ActionPage
     */
    public ProductDetail() {
        super("Select product");

        String query = "select product.name, supplier.name supplier, unit_price, unit from product, supplier where product.supplier = supplier.id and product.id = ?";
        try {
            selectProduct = RegorkInterface.conn.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ActionPage("Batches"),
            new ActionPage("Select again")
        };
        super.activate();
    }

    @Override
    protected void run() {
        System.out.print("\nType the ID of the product you wish to select: ");
        if (RegorkInterface.scanner.hasNextInt()) {
            id = RegorkInterface.scanner.nextInt();

            // Select product
            try {
                selectProduct.setInt(1, id);
                ResultSet result = selectProduct.executeQuery();

                if (!result.next()) {
                    System.out.println("\nProduct not found");
                } else {
                    valid = true;
                    System.out.println("\nID: " + id);
                    System.out.println("Name: " + result.getString("name"));
                    System.out.println("Supplier: " + result.getString("supplier"));
                    System.out.printf("Price: $%.2f / %s\n", result.getFloat("unit_price"), result.getString("unit"));
                }
            } catch (Exception e) {
                RegorkInterface.handleException(e);
            }
        }

        selectAction();
    }

    @Override
    protected void runAction(int index) {

        if (actions[index].title.equals("Batches") && valid) {
            BatchList bl = new BatchList(id);
            bl.activate();
        } else {
            super.runAction(index);
            return;
        }

        selectAction();
    }
}