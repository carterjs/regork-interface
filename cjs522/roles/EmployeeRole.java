/**
 * The regork employee role base menu
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class EmployeeRole extends ActionPage {

    /**
     * Creates employee role ActionPage
     */
    public EmployeeRole() {
        super("Regork Employee");

        message = "Choose a category: \n";
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new SupplierMenu(),
            new ProductMenu(),
            new InventoryMenu(),
            new ListingMenu()
        };
        super.activate();
    }

}