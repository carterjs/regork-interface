public class SupplierMenu extends ActionPage {

    public SupplierMenu() {
        super("Suppliers");
        
        exitKeyword = "Exit";
        message = "Choose an action: \n";
    }

    @Override
    public void activate() {
        actions = new ActionPage[]{
            new SupplierList(),
            new SupplierSearch()
        };
        super.activate();
    }
}