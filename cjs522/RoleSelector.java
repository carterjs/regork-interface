
/**
 * Selects user role
 * 
 * @author Carter Schmalzle (cjs522)
 */
public class RoleSelector extends ActionPage {

    /**
     * Creates select role ActionPage
     */
    public RoleSelector() {
        super("Select Role");

        message = "Select your role: \n";
        exitKeyword = "Exit";
    }

    @Override
    public void activate() {
        actions = new ActionPage[] {
            new ManagerRole(),
            new EmployeeRole(),
            new SupplierRole()
        };
        super.activate();
    }

    @Override
    protected void runAction(int index) {

        // Store role globally
        RegorkInterface.role = actions[index].title;

        // Enter role menu
        actions[index].activate();

        // Ask to exit application before returning to role list
        System.out.print("\nExit application? (y/n): ");
        if(RegorkInterface.scanner.next().charAt(0) == 'n') {
            run();
        }
    }

}