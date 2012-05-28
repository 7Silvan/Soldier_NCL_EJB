package ncl.military.dao.tools;

/**
 * @author gural
 * @version 1.0
 *          Date: 13.04.12
 *          Time: 16:41
 */
public enum FilterType {

    MATCH {
        public String toString() {
            return " = ";
        }
    }, // match suppose to work as equal
    LIKE {
        public String toString() {
            return " like ";
        }
    }, // like suppose to work as like
    BIGGER {
        public String toString() {
            return " > ";
        }
    },// bigger suppose to work as ">"
    LESSER {
        public String toString() {
            return " < ";
        }
    } // bigger suppose to work as "<"

}
