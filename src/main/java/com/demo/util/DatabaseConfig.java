package com.demo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Database configuration utility for Railway deployment
 * Reads database credentials from environment variables
 */
public class DatabaseConfig {
    
    /**
     * Get database properties from environment variables
     * Falls back to default values if env vars not set
     */
    public static Map<String, String> getDatabaseProperties() {
        Map<String, String> props = new HashMap<>();
        
        // Try to get DATABASE_URL first (Railway format)
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            // Railway provides DATABASE_URL in format:
            // postgresql://user:password@host:port/database
            props.put("jakarta.persistence.jdbc.url", 
                     convertToJdbcUrl(databaseUrl));
        } else {
            // Use individual environment variables
            String host = getEnv("PGHOST", "localhost");
            String port = getEnv("PGPORT", "5432");
            String database = getEnv("PGDATABASE", "electromart_db");
            String sslMode = getEnv("PGSSLMODE", "require");
            
            props.put("jakarta.persistence.jdbc.url", 
                     String.format("jdbc:postgresql://%s:%s/%s?sslmode=%s", 
                                   host, port, database, sslMode));
        }
        
        // Database credentials
        props.put("jakarta.persistence.jdbc.user", 
                 getEnv("PGUSER", "postgres"));
        props.put("jakarta.persistence.jdbc.password", 
                 getEnv("PGPASSWORD", ""));
        
        // Driver class
        props.put("jakarta.persistence.jdbc.driver", 
                 "org.postgresql.Driver");
        
        // Schema generation
        props.put("jakarta.persistence.schema-generation.database.action", 
                 "update");
        
        // Logging
        props.put("eclipselink.logging.level", 
                 getEnv("DB_LOG_LEVEL", "WARNING"));
        
        return props;
    }
    
    /**
     * Convert Railway DATABASE_URL to JDBC URL format
     */
    private static String convertToJdbcUrl(String databaseUrl) {
        if (databaseUrl.startsWith("postgresql://")) {
            return databaseUrl.replace("postgresql://", "jdbc:postgresql://") 
                             + "?sslmode=require";
        }
        return databaseUrl;
    }
    
    /**
     * Get environment variable with fallback default value
     */
    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }
    
    /**
     * Check if running in production (Railway)
     */
    public static boolean isProduction() {
        return System.getenv("RAILWAY_ENVIRONMENT") != null;
    }
    
    /**
     * Get database connection info for logging (without password)
     */
    public static String getConnectionInfo() {
        return String.format("Host: %s, Database: %s, User: %s", 
                           getEnv("PGHOST", "localhost"),
                           getEnv("PGDATABASE", "electromart_db"),
                           getEnv("PGUSER", "postgres"));
    }
}
