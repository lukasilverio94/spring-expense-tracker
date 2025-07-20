package com.dev.expensetracker.constants;

public class DockerImageConstants {

    // PostgreSQL Docker image
    public static final String POSTGRES_IMAGE = "postgres:17-alpine";

    // kafka docker image (implement in the future for messaging system)
    public static final String KAFKA_IMAGE = "confluentic/cp-kafka:7.6.1*";

    // private constructor to prevent instantiation
    private DockerImageConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
