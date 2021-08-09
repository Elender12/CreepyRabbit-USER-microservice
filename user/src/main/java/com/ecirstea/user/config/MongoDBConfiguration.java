package com.ecirstea.user.config;

import com.ecirstea.user.Application;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoDBConfiguration extends AbstractMongoClientConfiguration
{
    @Value("${spring.data.mongodb.host}")
    private String mongoDBHost;

    @Value("${spring.data.mongodb.port}")
    private String mongoDBPort;

    @Value("${spring.data.mongodb.user}")
    private String mongoDBUser;

    @Value("${spring.data.mongodb.pass}")
    private String mongoDBPass;

    @Value("${spring.data.mongodb.database}")
    private String mongoDBDatabase;

    @Override
    protected String getDatabaseName()
    {
        return mongoDBDatabase;
    }

    @Override
    public MongoClient mongoClient()
    {
        ConnectionString connectionString;
        if( Application.localInstance )
        {
            connectionString = new ConnectionString("mongodb://localhost:" + mongoDBPort + "/" + mongoDBDatabase + "?retryWrites=false");
        }
        else
        {
            connectionString = new ConnectionString("mongodb://" + mongoDBUser + ":" + mongoDBPass + "@" + mongoDBHost + ":" + mongoDBPort + "/" + mongoDBDatabase + "?retryWrites=false");
        }

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .applyConnectionString(connectionString).build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages()
    {
        return Collections.singleton("com.ecirstea.user.config");
    }

}