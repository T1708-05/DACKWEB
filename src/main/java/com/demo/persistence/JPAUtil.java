package com.demo.persistence;

import com.demo.util.DatabaseConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Map;

public final class JPAUtil {
    private static final EntityManagerFactory EMF;

    static {
        try {
            // Kiểm tra nếu đang chạy trên Railway (có biến môi trường RAILWAY_ENVIRONMENT)
            String railwayEnv = System.getenv("RAILWAY_ENVIRONMENT");
            boolean isRailway = railwayEnv != null && !railwayEnv.isEmpty();
            
            if (isRailway || System.getenv("DATABASE_URL") != null) {
                // Chạy trên Railway - sử dụng config từ environment variables
                System.out.println("🚀 Initializing database connection for Railway...");
                Map<String, String> props = DatabaseConfig.getDatabaseProperties();
                
                // Log connection info (không log password)
                System.out.println("📊 Database Info: " + DatabaseConfig.getConnectionInfo());
                
                EMF = Persistence.createEntityManagerFactory("shopPU", props);
                System.out.println("✅ Connected to Railway PostgreSQL database successfully!");
            } else {
                // Chạy local - sử dụng config từ persistence.xml
                System.out.println("🏠 Initializing database connection for local development...");
                EMF = Persistence.createEntityManagerFactory("shopPU");
                System.out.println("✅ Connected to local database successfully!");
            }
        } catch (Exception e) {
            System.err.println("❌ CRITICAL ERROR: Failed to create EntityManagerFactory!");
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    private JPAUtil() { }

    /** Lấy EntityManager mới cho mỗi lần gọi */
    public static EntityManager em() {
        if (EMF == null || !EMF.isOpen()) {
            throw new IllegalStateException("EntityManagerFactory is not initialized or has been closed!");
        }
        return EMF.createEntityManager();
    }

    /** Nếu nơi khác cần dùng EMF trực tiếp */
    public static EntityManagerFactory getEmFactory() {
        return EMF;
    }

    /** Đóng EMF khi ứng dụng dừng (tùy chọn) */
    public static void close() {
        if (EMF != null && EMF.isOpen()) {
            System.out.println("🔒 Closing EntityManagerFactory...");
            EMF.close();
            System.out.println("✅ EntityManagerFactory closed successfully!");
        }
    }
}
