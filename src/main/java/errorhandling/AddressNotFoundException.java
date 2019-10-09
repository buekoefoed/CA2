package errorhandling;

public class AddressNotFoundException extends Exception {
    public AddressNotFoundException(String message) {
        super(message);
    }
}
