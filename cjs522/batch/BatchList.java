import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Prints list of products with specified id
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class BatchList extends ActionPage {

    /**
     * The target product
     */
    int productId;

    /**
     * Creates batch list with product id
     * 
     * @param productId the target product
     */
    public BatchList(int productId) {

        super("Batch List: Product #" + productId);

        this.productId = productId;

    }

    @Override
    public void activate() {
        // Take away add batch for non-suppliers
        if (RegorkInterface.role != null && RegorkInterface.role.equals("Product Supplier")) {
            actions = new ActionPage[] { new BatchDetail(), new ActionPage("Create Batch") };
        } else {
            actions = new ActionPage[] {
                new BatchDetail()
            };
        }
        super.activate();
    }

    @Override
    protected void run() {

        // Get batches with product id productId
        try {
            Statement stmt = RegorkInterface.conn.createStatement();
            String q = "select batch.id, product.name product, supplier.name supplier, quantity, at_risk, recalled, included_recalled(batch.id) included_recalled, included_at_risk(batch.id) included_at_risk from batch, product, supplier where batch.product = product.id and product.supplier = supplier.id and product.id = "
                    + productId;
            ResultSet result = stmt.executeQuery(q);
            if (!result.next()) {
                System.out.println("No batches.");
            } else {
                System.out.printf("\n%-6s%-30s%-30s%-12s\n\n", "ID", "Product", "Supplier", "Quantity");
                do {
                    System.out.printf("%-6d%-30s%-30s%-12d", result.getInt("id"), result.getString("product"),
                            result.getString("supplier"), result.getInt("quantity"));
                    if(result.getInt("recalled") == 1) {
                        System.out.print(" * * Recalled * *");
                    } else if(result.getInt("included_recalled") == 1) {
                        System.out.print(" * * Contains recalled item(s) * * ");
                    } else if(result.getInt("at_risk") == 1) {
                        System.out.print(" * * At risk for recall * *");
                    } else if(result.getInt("included_at_risk") == 1) {
                        System.out.print(" * * Contains at-risk item(s) * *");
                    }
                    System.out.print("\n");
                } while (result.next());
            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }

    @Override
    protected void runAction(int index) {
        if (actions[index].title.equals("Create Batch")) {
            BatchCreator bc = new BatchCreator(productId);
            bc.activate();
        } else {
            super.runAction(index);
        }
    }
}