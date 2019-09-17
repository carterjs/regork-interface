import java.sql.Statement;

/**
 * Adds included batches
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class BatchesAddIncluded extends ActionPage {

    /**
     * The id of the specific batch
     */
    int batchId;

    /**
     * Creates add included batch ActionPage
     * 
     * @param batchId
     */
    public BatchesAddIncluded(int batchId) {
        super("Add Included Batch: #" + batchId);

        this.batchId = batchId;
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ActionPage("Add Another")
        };
        super.activate();
    }

    @Override
    protected void run() {

        System.out.print("Enter ID of batch to add: ");
        do {
            if (RegorkInterface.scanner.hasNextInt()) {
                int choice = RegorkInterface.scanner.nextInt();
                if (choice > 0) {
                    try {
                        Statement stmt = RegorkInterface.conn.createStatement();
                        String query = "insert into includes (batch, other_batch) values (" + batchId + ", " + choice
                                + ")";
                        stmt.executeUpdate(query);
                        RegorkInterface.conn.commit();
                        break;
                    } catch (Exception e) {
                        System.out.print("Not a valid ID");
                    }
                }
            }
        } while (true);

        selectAction();
    }
}