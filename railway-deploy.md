# Hướng dẫn Deploy lên Railway

## Bước 1: Chuẩn bị Database PostgreSQL

1. Đăng nhập vào Railway: https://railway.app
2. Tạo New Project
3. Chọn "Provision PostgreSQL"
4. Railway sẽ tự động tạo database và cung cấp các biến môi trường:
   - `DATABASE_URL`
   - `PGHOST`
   - `PGPORT`
   - `PGUSER`
   - `PGPASSWORD`
   - `PGDATABASE`

## Bước 2: Cấu hình Database Connection

Cập nhật file `src/main/resources/META-INF/persistence.xml` để sử dụng biến môi trường:

```xml
<property name="jakarta.persistence.jdbc.url" 
          value="${DATABASE_URL}"/>
<property name="jakarta.persistence.jdbc.user" 
          value="${PGUSER}"/>
<property name="jakarta.persistence.jdbc.password" 
          value="${PGPASSWORD}"/>
```

Hoặc sử dụng cấu hình động trong code Java để đọc từ `System.getenv()`.

## Bước 3: Deploy Application

### Cách 1: Deploy từ GitHub (Khuyến nghị)

1. Push code lên GitHub repository
2. Trong Railway project, chọn "New Service"
3. Chọn "GitHub Repo"
4. Chọn repository của bạn
5. Railway sẽ tự động detect Dockerfile và build

### Cách 2: Deploy từ CLI

```bash
# Cài đặt Railway CLI
npm i -g @railway/cli

# Login
railway login

# Link project
railway link

# Deploy
railway up
```

## Bước 4: Cấu hình Environment Variables

Trong Railway dashboard, thêm các biến môi trường cần thiết:

```
JAVA_OPTS=-Xmx512m -Xms256m
TZ=Asia/Ho_Chi_Minh
```

## Bước 5: Cấu hình Domain

1. Trong Railway service settings
2. Chọn "Settings" > "Networking"
3. Click "Generate Domain" để có public URL
4. Hoặc thêm custom domain của bạn

## Lưu ý quan trọng

1. **Database Connection**: Đảm bảo persistence.xml đọc đúng biến môi trường từ Railway
2. **Port**: Railway tự động expose port 8080, không cần thay đổi
3. **Build Time**: Lần build đầu tiên có thể mất 5-10 phút
4. **Logs**: Xem logs trong Railway dashboard để debug
5. **Health Check**: Railway tự động check health qua HTTP requests

## Kiểm tra Deploy

Sau khi deploy thành công:

1. Truy cập URL được Railway cung cấp
2. Kiểm tra kết nối database
3. Test các chức năng chính:
   - Đăng ký/Đăng nhập
   - Xem sản phẩm
   - Thêm vào giỏ hàng
   - Checkout

## Troubleshooting

### Lỗi kết nối database
- Kiểm tra biến môi trường DATABASE_URL
- Verify PostgreSQL service đang chạy
- Check logs để xem chi tiết lỗi

### Lỗi build
- Kiểm tra Dockerfile syntax
- Verify pom.xml dependencies
- Check Java version compatibility

### Application không start
- Xem logs trong Railway dashboard
- Kiểm tra JAVA_OPTS
- Verify Tomcat configuration

## Chi phí

- Railway cung cấp $5 credit miễn phí mỗi tháng
- PostgreSQL database: ~$5/tháng
- Web service: ~$5/tháng
- Tổng: ~$10/tháng (có thể dùng free tier nếu traffic thấp)

## Liên hệ hỗ trợ

Nếu gặp vấn đề, tham khảo:
- Railway Docs: https://docs.railway.app
- Railway Discord: https://discord.gg/railway
