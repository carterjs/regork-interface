/**
 * Menu for inventory actions
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class InventoryMenu extends ActionPage {

    /**
     * Creates inventory ActionPage
     */
    public InventoryMenu() {
        super("Inventory");

        exitKeyword = "Exit";
        message = "Choose an action: \n";
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new InventoryList(),
            new InventorySearch()
        };
        super.activate();
    }

}