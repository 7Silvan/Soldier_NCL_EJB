package ncl.military.controller.handle;

import java.util.Map;

/**
 * @author gural
 * @version 1.0
 *          Date: 13.04.12
 *          Time: 16:59
 */
public interface Executable {
    Map<String, Object> execute(Map<String, Object> params);
}
