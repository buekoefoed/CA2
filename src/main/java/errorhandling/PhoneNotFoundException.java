package errorhandling;

public class PhoneNotFoundException extends Exception {
    public PhoneNotFoundException(String message) {
        super(message);
    }
}
