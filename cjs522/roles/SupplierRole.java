/**
 * The product supplier role base menu
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class SupplierRole extends ActionPage {

    /**
     * Creates product supplier ActionPage
     */
    public SupplierRole() {
        super("Product Supplier");
    }

    @Override
    protected void run() {
        printTitle();

        // Launch supplier menu
        SupplierChooser sc = new SupplierChooser();
        sc.activate();
    }
}