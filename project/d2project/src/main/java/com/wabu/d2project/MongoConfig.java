package com.wabu.d2project;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration{
	@Value("${spring.data.mongodb.username")
	private String username;
	@Value("${spring.data.mongodb.password")
	private String password;
	@Value("${spring.data.mongodb.database")
	private String database;

	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate((MongoClient)mongoClient(),database);
	}
	@Override
	public MongoClient mongoClient() {
		// TODO Auto-generated method stub
		MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
		@Deprecated
		return new MongoClient(new ServerAddress("localhost", 27017), credential);
	}

	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return database;
	}
}
