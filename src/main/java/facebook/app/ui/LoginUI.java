package facebook.app.ui;

import facebook.app.controller.UserController;
import facebook.app.exceptions.UserIOException;
import java.util.Scanner;

public class LoginUI {
    private final UserController userController = new UserController();
    private final Scanner scanner = new Scanner(System.in);

    public boolean startLogin() throws UserIOException {
        System.out.println("Login");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userController.login(email, password)) {
            return true;
        } else {
            System.out.println("Login failed.");
            return false;
        }
    }
}
