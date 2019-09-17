/**
 * The Regork manager role base menu
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class ManagerRole extends ActionPage {

    /**
     * Creates regork manager ActionPage
     */
    public ManagerRole() {
        super("Regork Manager");

        message = "Choose a category: \n";
    }

    @Override
    public void activate() {
        actions = new ActionPage[]{
            new SupplierMenu(),
            new ProductMenu(),
            new InventoryMenu(),
            new ListingMenu()
        };
        super.activate();
    }

}