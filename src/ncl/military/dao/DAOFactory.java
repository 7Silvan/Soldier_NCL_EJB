package ncl.military.dao;

import ncl.military.dao.exceptions.DAOInitException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * User: Silvan
 * Date: 18.04.12
 * Time: 9:04
 */
public class DAOFactory {

    private static final Logger log = Logger.getLogger("model");

    public static DAO getDao(ServletContext context) throws DAOInitException {

        DAO module = null;

        try {
            String moduleClassName = context.getInitParameter("daoModule");
            if (moduleClassName == null)
                throw new IllegalStateException("Descriptor have no match for given param: daoModule");
            Class moduleClass = Class.forName(moduleClassName);
            Constructor moduleConstructor = moduleClass.getConstructor();
            module = (DAO) moduleConstructor.newInstance();
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
            throw new DAOInitException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            log.error("Could not load class with given name.", e);
            throw new DAOInitException("Could not load class with given name.", e);
        } catch (NoSuchMethodException e) {
            log.error("There is no such constructor in loaded class.", e);
            throw new DAOInitException("There is no such constructor in loaded class.", e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
            throw new DAOInitException(e.getMessage(), e);
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
            throw new DAOInitException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new DAOInitException(e.getMessage(), e);
        }
        return module;
    }
}
