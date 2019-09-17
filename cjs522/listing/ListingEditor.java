import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Edits a specific listing
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class ListingEditor extends ActionPage {

    /**
     * The id of the target listing
     */
    int listingId;

    /**
     * Creates listing editor ActionPage
     * 
     * @param listingId
     */
    public ListingEditor(int listingId) {
        super("Edit listing: #" + listingId);

        this.listingId = listingId;
        message = "Select a field to edit: \n";
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ActionPage("Name"),
            new ActionPage("Price") 
        };
        super.activate();
    }

    @Override
    protected void run() {

        selectAction();

    }

    @Override
    protected void runAction(int index) {
        if (actions[index].title.equals("Name")) {
            editName();
        } else if(actions[index].title.equals("Price")) {
            editPrice();
        }
        super.selectAction();
    }

    /**
     * Edit the listing name
     */
    private void editName() {
        System.out.print("Name: ");
        if (RegorkInterface.scanner.hasNextLine()) {
            RegorkInterface.scanner.nextLine();
            String name = RegorkInterface.scanner.nextLine();
            if(name != null && name.length() > 0) {
                try {
                    String query = "update listing set name = ? where id = " + listingId;
                    PreparedStatement ps = RegorkInterface.conn.prepareStatement(query);
                    ps.setString(1, name);
                    ps.executeUpdate();
                    System.out.println("\nChanged name to \"" + name + ".\"");
                } catch (Exception e) {
                    RegorkInterface.handleException(e);
                }
            } else {
                System.out.println("\nKept name the same.");
            }
        }
    }

    /**
     * Edit the listing price
     */
    private void editPrice() {
        System.out.print("Price: $");
        if (RegorkInterface.scanner.hasNextLine()) {
            if(RegorkInterface.scanner.hasNextDouble()) {
                Double price = RegorkInterface.scanner.nextDouble();
                if(price > 0) {
                    try {
                        String query = "update listing set price = ? where id = " + listingId;
                        PreparedStatement ps = RegorkInterface.conn.prepareStatement(query);
                        ps.setDouble(1, price);
                        ps.executeUpdate();
                        System.out.printf("\nChanged price to $%.2f.", price);
                    } catch (Exception e) {
                        RegorkInterface.handleException(e);
                    }
                }
            } else {
                System.out.println("\nKept price the same.");
            }
        } else {
            editPrice();
        }
        
    }

}