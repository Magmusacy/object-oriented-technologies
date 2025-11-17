package pl.edu.agh.school.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.PersistanceManager;

import static org.junit.jupiter.api.Assertions.*;

class SchoolModuleTest {

    @Test
    void testLoggerIsSingleton() {
        Injector injector = Guice.createInjector(new SchoolModule());

        Logger logger1 = injector.getInstance(Logger.class);
        Logger logger2 = injector.getInstance(Logger.class);

        assertNotNull(logger1);
        assertNotNull(logger2);
        assertSame(logger1, logger2, "Logger powinien być singletonem - obie instancje powinny być identyczne");
    }

    @Test
    void testLoggerIsInjectedIntoPersistenceManager() {
        Injector injector = Guice.createInjector(new SchoolModule());

        PersistanceManager manager = injector.getInstance(PersistanceManager.class);

        assertNotNull(manager);
    }

    @Test
    void testMultipleInjectionsUseSameLoggerInstance() {
        Injector injector = Guice.createInjector(new SchoolModule());

        Logger logger = injector.getInstance(Logger.class);
        PersistanceManager manager1 = injector.getInstance(PersistanceManager.class);
        PersistanceManager manager2 = injector.getInstance(PersistanceManager.class);

        assertNotNull(logger);
        assertNotNull(manager1);
        assertNotNull(manager2);
    }
}