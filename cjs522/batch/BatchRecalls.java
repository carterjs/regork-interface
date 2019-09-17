import java.sql.Statement;

/**
 * Sets the recall state of a specific batch
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class BatchRecalls extends ActionPage {

    private int batchId;

    public BatchRecalls(int batchId) {
        super("Batch Recalls: #" + batchId);

        this.batchId = batchId;
        message = "Set recall status: \n";
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ActionPage("Clear"),
            new ActionPage("At Risk"),
            new ActionPage("Recalled")
        };

        super.activate();
    }

    @Override
    protected void run() {
        selectAction();
    }

    @Override
    protected void runAction(int index) {
        if(actions[index].title.equals("Clear")) {
            setClear();
        } else if(actions[index].title.equals("At Risk")) {
            setAtRisk();
        } else if(actions[index].title.equals("Recalled")) {
            setRecalled();
        }
        super.runAction(index);
    }

    private void setClear() {
        try {
            Statement stmt = RegorkInterface.conn.createStatement();
            String query = "update batch set recalled = null, at_risk = null where id = " + batchId;
            stmt.executeUpdate(query);
            RegorkInterface.conn.commit();
            System.out.println("Set recall status to clear.");
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }
    }

    private void setAtRisk() {
        System.out.println("Entering");
        try {
            Statement stmt = RegorkInterface.conn.createStatement();
            String query = "update batch set recalled = null, at_risk = 1 where id = " + batchId;
            stmt.executeUpdate(query);
            RegorkInterface.conn.commit();
            System.out.println("Set recall status to at risk.");
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }
        System.out.println("Leaving");
    }

    private void setRecalled() {
        try {
            Statement stmt = RegorkInterface.conn.createStatement();
            String query = "update batch set recalled = 1, at_risk = null where id = " + batchId;
            stmt.executeUpdate(query);
            RegorkInterface.conn.commit();
            System.out.println("Recalled batch.");
        } catch (Exception e) {
            RegorkInterface.handleException(e);
        }
    }
}