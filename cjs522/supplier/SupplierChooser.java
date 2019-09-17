/**
 * Prompts user to choose their supplier by id or namee
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class SupplierChooser extends ActionPage {
    SupplierChooser() {
        super("Choose Supplier");

        message = "Select a method to find your company: \n";
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ActionPage("Find by ID"),
            new ActionPage("Find by Name")
        };
        super.activate();
    }

    @Override
    public void run() {
        selectAction();
    }

    @Override
    protected void runAction(int index) {
        if (actions[index].title.equals("Find by ID")) {
            SupplierDetail sd = new SupplierDetail();
            sd.activate();
        } else if (actions[index].title.equals("Find by Name")) {
            SupplierSearch ss = new SupplierSearch();
            ss.activate();
        }
    }
}