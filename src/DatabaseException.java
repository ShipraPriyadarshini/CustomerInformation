
@SuppressWarnings("serial")
public class DatabaseException extends Exception {
    DatabaseException() {}
    
    DatabaseException(Exception e) {
        super(e);
    }
}
