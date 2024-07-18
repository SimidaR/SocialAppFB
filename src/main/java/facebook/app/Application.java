package facebook.app;

import facebook.app.exceptions.InvalidEmailFormatException;
import facebook.app.exceptions.MessageValidationException;
import facebook.app.exceptions.UserIOException;
import facebook.app.exceptions.UserNotFoundException;
import facebook.app.ui.IntroUI;

public class Application {
    public static void main(String[] args) throws UserNotFoundException, MessageValidationException, UserIOException, InvalidEmailFormatException {
        IntroUI introUI = new IntroUI();
        introUI.startUI();
    }
}
