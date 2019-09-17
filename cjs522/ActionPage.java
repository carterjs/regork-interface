/**
 * A menu page with an action Represents a state within the program
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class ActionPage {

    /**
     * The title of the page
     */
    public String title = "Page";

    /**
     * The sub-pages of this page
     */
    public ActionPage[] actions;

    /**
     * The title of the action to exit this page
     */
    protected String exitKeyword = "Back";

    /**
     * The message shown before action selection
     */
    protected String message = "Actions: \n";

    /**
     * Creates ActionPage
     * 
     * @param title the title of this page
     */
    public ActionPage(String title) {
        this.title = title;
        actions = new ActionPage[0];
    }

    /**
     * Activates the page
     */
    public void activate() {
        run();
    }

    /**
     * The action of the ActionPage
     */
    protected void run() {
        if (actions.length == 0) {
            return;
        }

        printTitle();
        selectAction();
    }

    /**
     * Prints the title with lines above and below it
     */
    protected void printTitle() {
        String divider = "";
        String buf = "";
        for (int i = 0; i < 50; i++) {
            divider += "-";
            if(i < (50 - title.length())/2) {
                buf += " ";
            }
        }
        System.out.print("\n\n\n\n\n\n\n\n\n\n");
        if(RegorkInterface.role != null) {
            System.out.println("Viewing as: " + RegorkInterface.role + "\n");
        }
        System.out.println(divider + "\n" + buf + title + "\n" + divider);
    }

    /**
     * Prints actions and prompts for selection
     */
    protected final void selectAction() {

        // Prints a list of all child ActionPages
        System.out.print("\n");
        System.out.println(message);
        for (int i = 0; i < actions.length; i++) {
            System.out.println("   [" + (i + 1) + "] " + actions[i].title);
        }
        // Print exit keyword
        System.out.println("\n   [0] " + exitKeyword);

        do {
            System.out.print("\nSelection: ");

            if (RegorkInterface.scanner.hasNextInt()) {
                int selection = RegorkInterface.scanner.nextInt();
                if (selection > 0 && selection <= actions.length) {
                    runAction(selection - 1);
                    break;
                } else if(selection == 0) {
                    return;
                } else {
                    System.out.println("Selection out of range.");
                }
            } else {
                System.out.println("Invalid selection. ");
                RegorkInterface.scanner.next();
            }
        } while (true);
    }

    /**
     * Called when an action is selected and before it is run
     * @param index the index of the selected action (Don't forget to subtract 1!)
     */
    protected void runAction(int index) {
        actions[index].activate();
        run();
    }

}