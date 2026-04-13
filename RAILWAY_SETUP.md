# 🚀 Hướng dẫn Deploy ElectroMart lên Railway

## 📋 Tổng quan

Hướng dẫn này sẽ giúp bạn deploy ứng dụng ElectroMart (Java Servlet/JSP) lên Railway với PostgreSQL database.

## ✅ Yêu cầu

- Tài khoản GitHub
- Tài khoản Railway (đăng ký miễn phí tại https://railway.app)
- Code đã được push lên GitHub repository

## 🔧 Bước 1: Chuẩn bị Code

### 1.1. Cập nhật JPAUtil để sử dụng biến môi trường

Mở file `src/main/java/com/demo/persistence/JPAUtil.java` và cập nhật như sau:

```java
package com.demo.persistence;

import com.demo.util.DatabaseConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Map;

public class JPAUtil {
    private static EntityManagerFactory emf;

    static {
        try {
            // Kiểm tra nếu đang chạy trên Railway
            if (DatabaseConfig.isProduction()) {
                // Sử dụng config từ environment variables
                Map<String, String> props = DatabaseConfig.getDatabaseProperties();
                emf = Persistence.createEntityManagerFactory("shopPU", props);
                System.out.println("✅ Connected to Railway database: " + 
                                 DatabaseConfig.getConnectionInfo());
            } else {
                // Sử dụng config từ persistence.xml (local development)
                emf = Persistence.createEntityManagerFactory("shopPU");
                System.out.println("✅ Connected to local database");
            }
        } catch (Exception e) {
            System.err.println("❌ Error creating EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
```

### 1.2. Commit và Push code

```bash
git add .
git commit -m "Add Railway deployment configuration"
git push origin main
```

## 🗄️ Bước 2: Tạo PostgreSQL Database trên Railway

1. Đăng nhập vào Railway: https://railway.app
2. Click **"New Project"**
3. Chọn **"Provision PostgreSQL"**
4. Railway sẽ tự động tạo database và cung cấp các biến môi trường:
   - `DATABASE_URL`
   - `PGHOST`
   - `PGPORT`
   - `PGUSER`
   - `PGPASSWORD`
   - `PGDATABASE`

5. **Lưu ý**: Ghi nhớ thông tin database này (có thể xem trong tab "Variables")

## 🚢 Bước 3: Deploy Application

### 3.1. Thêm Service từ GitHub

1. Trong Railway project, click **"New"** → **"GitHub Repo"**
2. Chọn repository của bạn
3. Railway sẽ tự động detect Dockerfile và bắt đầu build

### 3.2. Cấu hình Environment Variables

Railway sẽ tự động link database variables, nhưng bạn có thể thêm các biến khác:

1. Click vào service vừa tạo
2. Chọn tab **"Variables"**
3. Thêm các biến sau (nếu cần):

```
JAVA_OPTS=-Xmx512m -Xms256m
TZ=Asia/Ho_Chi_Minh
DB_LOG_LEVEL=WARNING
```

### 3.3. Kết nối Database với Application

1. Click vào service application
2. Chọn tab **"Settings"**
3. Scroll xuống **"Service Variables"**
4. Click **"New Variable"** → **"Add Reference"**
5. Chọn PostgreSQL service
6. Chọn tất cả các biến: `DATABASE_URL`, `PGHOST`, `PGPORT`, `PGUSER`, `PGPASSWORD`, `PGDATABASE`

## 🌐 Bước 4: Cấu hình Domain

### 4.1. Generate Public URL

1. Click vào service application
2. Chọn tab **"Settings"**
3. Scroll xuống **"Networking"**
4. Click **"Generate Domain"**
5. Railway sẽ tạo URL dạng: `https://your-app.up.railway.app`

### 4.2. Custom Domain (Tùy chọn)

1. Trong **"Networking"** section
2. Click **"Custom Domain"**
3. Nhập domain của bạn
4. Cấu hình DNS theo hướng dẫn của Railway

## 📊 Bước 5: Kiểm tra Deployment

### 5.1. Xem Build Logs

1. Click vào service
2. Chọn tab **"Deployments"**
3. Click vào deployment mới nhất
4. Xem logs để đảm bảo build thành công

### 5.2. Xem Application Logs

1. Trong tab **"Deployments"**
2. Click **"View Logs"**
3. Kiểm tra xem có thông báo "✅ Connected to Railway database" không

### 5.3. Test Application

Truy cập URL được Railway cung cấp và test các chức năng:

- ✅ Trang chủ load được
- ✅ Đăng ký tài khoản mới
- ✅ Đăng nhập
- ✅ Xem danh sách sản phẩm
- ✅ Thêm vào giỏ hàng
- ✅ Checkout

## 🔍 Troubleshooting

### Lỗi: "Cannot connect to database"

**Giải pháp:**
1. Kiểm tra database service đang chạy
2. Verify các biến môi trường đã được link đúng
3. Xem logs để biết chi tiết lỗi:
```bash
railway logs
```

### Lỗi: "Build failed"

**Giải pháp:**
1. Kiểm tra Dockerfile syntax
2. Verify pom.xml dependencies
3. Xem build logs trong Railway dashboard

### Lỗi: "Application crashed"

**Giải pháp:**
1. Xem application logs
2. Kiểm tra JAVA_OPTS
3. Verify persistence.xml configuration
4. Check database connection string

### Database connection timeout

**Giải pháp:**
1. Thêm `?sslmode=require` vào connection string
2. Tăng connection timeout:
```xml
<property name="eclipselink.jdbc.timeout" value="30000"/>
```

## 💰 Chi phí

Railway cung cấp:
- **$5 credit miễn phí** mỗi tháng cho Hobby plan
- **PostgreSQL**: ~$5/tháng
- **Web Service**: ~$5/tháng

**Tổng**: ~$10/tháng (hoặc miễn phí nếu traffic thấp và nằm trong $5 credit)

## 🔄 Cập nhật Application

Để cập nhật application sau khi deploy:

1. Commit và push code mới lên GitHub:
```bash
git add .
git commit -m "Update feature"
git push origin main
```

2. Railway sẽ tự động detect changes và rebuild
3. Deployment mới sẽ tự động replace deployment cũ

## 📱 Railway CLI (Tùy chọn)

Cài đặt Railway CLI để quản lý từ terminal:

```bash
# Cài đặt
npm i -g @railway/cli

# Login
railway login

# Link project
railway link

# Xem logs
railway logs

# Deploy
railway up

# Mở dashboard
railway open
```

## 🔐 Bảo mật

### Khuyến nghị:

1. **Không commit** thông tin database vào Git
2. **Sử dụng** environment variables cho tất cả secrets
3. **Enable** SSL cho database connection
4. **Thay đổi** mật khẩu admin mặc định
5. **Cấu hình** CORS nếu cần

### Cập nhật mật khẩu database:

1. Trong Railway dashboard
2. Click vào PostgreSQL service
3. Tab "Settings" → "Danger Zone"
4. "Reset Password"

## 📚 Tài liệu tham khảo

- Railway Documentation: https://docs.railway.app
- Railway Discord: https://discord.gg/railway
- PostgreSQL on Railway: https://docs.railway.app/databases/postgresql
- Java on Railway: https://docs.railway.app/languages/java

## 🆘 Hỗ trợ

Nếu gặp vấn đề:

1. Kiểm tra Railway Status: https://status.railway.app
2. Xem Railway Docs: https://docs.railway.app
3. Hỏi trên Railway Discord: https://discord.gg/railway
4. Tạo issue trên GitHub repository

## ✅ Checklist Deploy

- [ ] Code đã được push lên GitHub
- [ ] PostgreSQL database đã được tạo trên Railway
- [ ] Application service đã được tạo và link với GitHub repo
- [ ] Environment variables đã được cấu hình
- [ ] Database variables đã được link với application
- [ ] Domain đã được generate
- [ ] Build thành công
- [ ] Application logs không có lỗi
- [ ] Test các chức năng chính
- [ ] Đã thay đổi mật khẩu admin mặc định

## 🎉 Hoàn thành!

Chúc mừng! Ứng dụng ElectroMart của bạn đã được deploy thành công lên Railway.

URL: `https://your-app.up.railway.app`

---

**Lưu ý**: Đây là hướng dẫn cho môn Lập Trình Web - Đồ án cuối kỳ 1/2025-2026
