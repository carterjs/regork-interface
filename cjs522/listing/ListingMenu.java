/**
 * Menu for listing actions
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class ListingMenu extends ActionPage {

    /**
     * Creates listings ActionPage
     */
    public ListingMenu() {
        super("Listings");

        exitKeyword = "Exit";
        message = "Choose an action: \n";
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ListingList(),
            new ListingSearch()
        };
        super.activate();
    }
}