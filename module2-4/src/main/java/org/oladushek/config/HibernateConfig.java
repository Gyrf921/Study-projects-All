package org.oladushek.config;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.oladushek.entity.EventEntity;
import org.oladushek.entity.FileEntity;
import org.oladushek.entity.UserEntity;

public class HibernateConfig {

    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            configuration.addAnnotatedClass(UserEntity.class);
            configuration.addAnnotatedClass(EventEntity.class);
            configuration.addAnnotatedClass(FileEntity.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

}
