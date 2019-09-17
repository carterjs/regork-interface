import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Lists all batches included in specific batch
 */
public class BatchesIncluded extends ActionPage {

/**
 * Prepared SQL statement for getting batches included
 */
private PreparedStatement getIncluded;

/**
 * The id of the selected batch
 */
private int batchId;

/**
 * Creates select batches included ActionPage
 */
public BatchesIncluded(int batchId) {
    super("Included Batches: Batch #" + batchId);

    this.batchId = batchId;

}

@Override
public void activate() {
    if(RegorkInterface.role != null && RegorkInterface.role.equals("Product Supplier")) {
        this.actions = new ActionPage[] {
            new BatchDetail(),
            new ActionPage("Add Included Batch")
        };
    } else {
        this.actions = new ActionPage[] {
            new BatchDetail()
        };
    }
    String query = "with product_batch (id, name, at_risk, recalled) as ( select batch.id, product.name, batch.at_risk, batch.recalled from batch inner join product on batch.product = product.id ), rec_includes (id, name, at_risk, recalled) as ( select B.id, B.name, B.at_risk, B.recalled from product_batch A, includes, product_batch B where includes.batch = A.id and includes.other_batch = B.id and A.id = ? union all select B.id, B.name, B.at_risk, B.recalled from product_batch A, includes, product_batch B, rec_includes where includes.batch = A.id and includes.other_batch = B.id and A.id = rec_includes.id) select id, name, at_risk, recalled from rec_includes";
    try {
        getIncluded = RegorkInterface.conn.prepareStatement(query);
    } catch (Exception e) {
        RegorkInterface.handleException(e);
    }
    super.activate();
}

@Override
protected void run() {

        // Select product
        try {
            getIncluded.setInt(1,  batchId);
            ResultSet result = getIncluded.executeQuery();

            if (!result.next()) {
                System.out.println("\nNone found.");
            } else {
                System.out.printf("\n%-6s%-30s\n\n", "ID", "Name");
                do {
                    System.out.printf("%-6d%-30s", result.getInt("id"), result.getString("name"));
                    if(result.getInt("recalled") == 1) {
                        System.out.print(" * * Included in recall * *");
                    } else if(result.getInt("at_risk") == 1) {
                        System.out.print(" * * At risk for recall * *");
                    }
                    System.out.print("\n");
                } while(result.next());
            }
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }

        selectAction();
    }

    @Override
    protected void runAction(int index) {
        if(actions[index].title.equals("Add Included Batch")) {
            BatchesAddIncluded bai = new BatchesAddIncluded(batchId);
            bai.activate();
            run();
            return;
        }
        super.runAction(index);
    }
}