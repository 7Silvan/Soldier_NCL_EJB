package ncl.military.dao.exceptions;

/**
 * @author gural
 * @version 1.0
 *          Date: 20.04.12
 *          Time: 18:55
 */
public class DAOInitException extends RuntimeException {
    public DAOInitException(String message) {
        super(message);
    }

    public DAOInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOInitException(Throwable cause) {
        super(cause);
    }
}
