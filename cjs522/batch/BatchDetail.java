import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Lists all available information on specific product batch
 */
public class BatchDetail extends ActionPage {

    /**
     * Prepared SQL statement for selecting batch by id
     */
    private PreparedStatement selectBatch;

    /**
     * The id of the selected batch
     */
    private int id;

    /**
     * Whether or not the id returned a batch
     */
    private boolean valid;

    /**
     * Creates select product batch ActionPage
     */
    public BatchDetail() {
        super("Select Batch");
    }
    
    @Override
    public void activate() {
        if(RegorkInterface.role != null && !RegorkInterface.role.equals("Regork Employee")) {
            actions = new ActionPage[]{
                new ActionPage("Recalls"),
                new ActionPage("Included Batches")
            };
        } else {
            actions = new ActionPage[] {
                new ActionPage("Included Batches"),
                new ActionPage("Select again")
            };
        }
        String query = "select batch.id, product.id product_id, product.name product_name, supplier.id supplier_id, supplier.name supplier_name, quantity, unit, plant.address, plant.city, plant.state, plant.zip, manufacture_time, at_risk, recalled, included_at_risk(batch.id) included_at_risk, included_recalled(batch.id) included_recalled from batch, product, supplier, plant where batch.product = product.id and product.supplier = supplier.id and batch.plant = plant.id and batch.id = ?";
        try {
            selectBatch = RegorkInterface.conn.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(RegorkInterface.role != null && !RegorkInterface.role.equals("Regork Employee")) {
            actions = new ActionPage[]{
                new ActionPage("Recalls"),
                new ActionPage("Included Batches"),
                new ActionPage("Select Again")
            };
        }

        System.out.print("\nType the ID of the batch you wish to select: ");
        if (RegorkInterface.scanner.hasNextInt()) {
            id = RegorkInterface.scanner.nextInt();


        }
        super.activate();
    }

    @Override
    public void run() {

        // Select product
        try {
            selectBatch.setInt(1, id);
            ResultSet result = selectBatch.executeQuery();

            if (!result.next()) {
                System.out.println("\nBatch not found");
                return;
            } else {
                valid = true;
                System.out.println("\nID: " + id);
                System.out.println("Product: " + result.getString("product_name") + " (#" + result.getInt("product_id") + ")");
                System.out.println("Supplier: " + result.getString("supplier_name") + " (#" + result.getInt("supplier_id") + ")");
                System.out.println("Quantity: " + result.getInt("quantity") + " " + result.getString("unit") + "(s)");
                System.out.println("Plant Address:");
                System.out.println("   " + result.getString("address"));
                System.out.print("   " + result.getString("city") + ", ");
                System.out.print(result.getString("state"));
                System.out.println(" " + result.getInt("zip"));
                System.out.println("Manufacture Date: " + result.getTimestamp("manufacture_time").toString());
                if(result.getInt("recalled") == 1) {
                    System.out.println("\n * * Recalled * *");
                } else if(result.getInt("included_recalled") == 1) {
                    System.out.println("\n * * Contains recalled item(s) * * ");
                } else if(result.getInt("at_risk") == 1) {
                    System.out.println(" * * At risk for recall * *");
                } else if(result.getInt("included_at_risk") == 1) {
                    System.out.println(" * * Contains at-risk item(s) * *");
                }
            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }


    @Override
    public void runAction(int index) {
        if(actions[index].title.equals("Included Batches") && valid) {
            BatchesIncluded bi = new BatchesIncluded(id);
            bi.activate();
            run();
            return;
        } else if(actions[index].title.equals("Recalls") && valid) {
            BatchRecalls br = new BatchRecalls(id);
            br.activate();
            run();
            return;
        }
        super.runAction(index);
    }

}