package ncl.military.dao.exceptions;

/**
 * @author gural
 * @version 1.0
 *          Date: 20.04.12
 *          Time: 18:55
 */
public class DataAccessException extends RuntimeException {
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
