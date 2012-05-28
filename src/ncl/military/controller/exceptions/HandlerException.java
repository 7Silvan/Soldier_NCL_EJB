package ncl.military.controller.exceptions;

/**
 * User: Silvan
 * Date: 10.05.12
 * Time: 10:16
 */
public class HandlerException extends RuntimeException {
    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlerException(Throwable cause) {
        super(cause);
    }
}
