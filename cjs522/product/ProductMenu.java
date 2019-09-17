/**
 * Menu for product actions
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class ProductMenu extends ActionPage {

    /**
     * Creates products ActionPage
     */
    public ProductMenu() {
        super("Products");

        exitKeyword = "Exit";
        message = "Choose an action: \n";
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ProductList(),
            new ProductSearch() 
        };
        super.activate();
    }

}