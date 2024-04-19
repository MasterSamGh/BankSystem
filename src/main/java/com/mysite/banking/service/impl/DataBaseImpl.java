package com.mysite.banking.service.impl;

import com.mysite.banking.model.*;
import com.mysite.banking.service.DataBase;
import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import java.sql.*;

public class DataBaseImpl implements DataBase {
    private static final String JDBC_URL = "jdbc:h2:./data/BankSystemDB";
    private static final String USERNAME = "sam82";
    private static final String PASSWORD = "8203";
    private SessionFactory sessionFactory;
    private static DataBaseImpl INSTANCE;
    public static DataBaseImpl getInstance(){
        if (INSTANCE == null)
            INSTANCE = new DataBaseImpl();
        return INSTANCE;
    }
    private DataBaseImpl(){
        String jdbcUrl = System.getProperty("CUSTOM_JDBC_URL");
        if (jdbcUrl == null || jdbcUrl.isEmpty())
            jdbcUrl = JDBC_URL;

        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .applySetting(Environment.DRIVER,"org.h2.Driver")
                .applySetting(Environment.URL,jdbcUrl)
                .applySetting(Environment.USER,USERNAME)
                .applySetting(Environment.PASS,PASSWORD)
                .applySetting(Environment.DIALECT,"org.hibernate.dialect.H2Dialect")
                .applySetting(Environment.SHOW_SQL,"true")
                .applySetting("hibernate.hbm2ddl.auto","update")
                .build();

        try {
            Server.createTcpServer("-tcpAllowOthers").start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Server.createWebServer("-web","-webAllowOthers").start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        MetadataSources sources = new MetadataSources(standardServiceRegistry);
        sources.addAnnotatedClass(RealClient.class);
        sources.addAnnotatedClass(LegalClient.class);
        sources.addAnnotatedClass(Account.class);
        sources.addAnnotatedClass(Amount.class);
        sources.addAnnotatedClass(ATM.class);

        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    @Override
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
