import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Searches suppliers by name
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class SupplierSearch extends ActionPage {

    /**
     * A prepared SQL statement for finding suppliers by name
     */
    private PreparedStatement searchSuppliers;

    /**
     * Creates supplier search ActionPage
     */
    public SupplierSearch() {
        super("Supplier Search");
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new SupplierDetail(),
            new ActionPage("Search again")
        };

        // Prepare statement
        try {
            String query = "select id, name from supplier where name like ?";
            searchSuppliers = RegorkInterface.conn.prepareStatement(query);
        } catch(Exception e) {
            RegorkInterface.handleException(e);
        }

        super.activate();
    }

    @Override
    protected void run() {

        // Prompt for supplier name
        if(RegorkInterface.role != null && RegorkInterface.role.equals("Product Supplier")) {
            System.out.print("\nSearch for your company's name: ");
        } else {
            printTitle();
            System.out.print("\nSearch for a supplier by name: ");
        }
        RegorkInterface.scanner.nextLine();
        String str = RegorkInterface.scanner.nextLine();
        
        // Execute search
        try {
            searchSuppliers.setString(1, "%" + str + "%");
            ResultSet result = searchSuppliers.executeQuery();

            // Print results
            if(!result.next()) {
                System.out.println("\nNo suppliers.");
            } else {
                System.out.printf("\n%-6s%-30s\n\n", "ID", "Name");
                do {
                    System.out.printf("%-6d%-30s\n", result.getInt("id"), result.getString("name"));
                } while(result.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            RegorkInterface.handleException(e);
        }

        selectAction();
    }

    @Override
    protected void runAction(int index) {
        if(actions[index].title.equals("Select Supplier")) {
            actions[index].activate();
            return;
        }
        super.runAction(index);
    }

}