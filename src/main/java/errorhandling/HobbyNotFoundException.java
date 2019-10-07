package errorhandling;

public class HobbyNotFoundException extends Exception {
    public HobbyNotFoundException(String message) {
        super(message);
    }
}
