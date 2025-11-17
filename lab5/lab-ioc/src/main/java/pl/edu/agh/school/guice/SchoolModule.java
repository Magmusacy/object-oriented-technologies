package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.logger.IMessageSerializer;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.PersistanceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

import java.util.HashSet;

public class SchoolModule extends AbstractModule {
    @Provides
    public PersistanceManager providePersistanceManager(SerializablePersistenceManager manager) {
        return manager;
    }

    @Provides
    @Singleton
    public Logger provideLogger(@Named("log_file") String logFile) {
        HashSet<IMessageSerializer> serializers = new HashSet<>();
        serializers.add(new FileMessageSerializer(logFile));
        return new Logger(serializers);
    }

    @Override
    protected void configure() {
        bind(String.class)
                .annotatedWith(Names.named("class_storage"))
                .toInstance("guice-classes.dat");

        bind(String.class)
                .annotatedWith(Names.named("teachers_storage"))
                .toInstance("guice-teachers.dat");

        bind(String.class)
                .annotatedWith(Names.named("log_file"))
                .toInstance("persistence.log");
    }
}
