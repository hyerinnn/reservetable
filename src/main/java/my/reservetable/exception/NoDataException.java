package my.reservetable.exception;

public class NoDataException extends RuntimeException {
    public NoDataException() {
    }

    public NoDataException(String message) {
        super(message);
    }
}
