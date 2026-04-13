# 🔍 Debug "Application failed to respond" trên Railway

## ❌ Lỗi bạn đang gặp

```
Application failed to respond
This error appears to be caused by the application.
```

**Nghĩa là**: Deploy thành công, domain đã có, NHƯNG app bên trong đang crash/treo/không chạy đúng.

## 🎯 Nguyên nhân với Java/Tomcat

### 1. ❌ Không listen đúng PORT
Railway yêu cầu app phải listen trên `$PORT` environment variable (không phải hard-code 8080).

**✅ ĐÃ SỬA**: Dockerfile mới đã tự động set port từ Railway.

### 2. ❌ Database connection failed
App không kết nối được PostgreSQL → crash khi start.

**✅ ĐÃ SỬA**: JPAUtil.java đã được cập nhật để đọc database config từ Railway.

### 3. ❌ App crash khi khởi động
Lỗi trong code, thiếu dependency, hoặc config sai.

**🔍 CẦN KIỂM TRA**: Xem logs để biết chi tiết.

## 📋 Các bước debug

### Bước 1: Xem Deploy Logs

1. Vào Railway dashboard
2. Click vào **service của bạn**
3. Tab **"Deployments"**
4. Click vào **deployment mới nhất**
5. Xem **"Build Logs"** và **"Deploy Logs"**

### Bước 2: Tìm các dòng quan trọng

Tìm các dòng này trong logs:

#### ✅ Dấu hiệu THÀNH CÔNG:
```
🚀 Starting Tomcat on port 8080
✅ Connected to Railway PostgreSQL database successfully!
INFO: Server startup in [xxxx] milliseconds
```

#### ❌ Dấu hiệu LỖI:

**Lỗi database:**
```
❌ CRITICAL ERROR: Failed to create EntityManagerFactory!
Connection refused
Could not connect to database
FATAL: password authentication failed
```

**Lỗi port:**
```
Port already in use
Address already in use
java.net.BindException
```

**Lỗi build:**
```
BUILD FAILED
compilation failure
package does not exist
```

**Lỗi Tomcat:**
```
SEVERE: Context initialization failed
Failed to start component
Application startup failed
```

### Bước 3: Kiểm tra Environment Variables

1. Trong Railway service
2. Tab **"Variables"**
3. Đảm bảo có các biến sau:

```
✅ DATABASE_URL=postgresql://...
✅ PGHOST=...
✅ PGPORT=5432
✅ PGDATABASE=...
✅ PGUSER=...
✅ PGPASSWORD=...
```

**Nếu KHÔNG CÓ** → Cần link database với service:
1. Tab "Variables" → "New Variable" → "Add Reference"
2. Chọn PostgreSQL service
3. Chọn tất cả variables

### Bước 4: Kiểm tra Database Service

1. Click vào **PostgreSQL service**
2. Tab **"Metrics"**
3. Đảm bảo database đang **running** (không phải stopped/crashed)

### Bước 5: Test Database Connection

Trong Railway dashboard:

1. Click PostgreSQL service
2. Tab **"Data"** hoặc **"Connect"**
3. Copy connection string
4. Test bằng tool như DBeaver hoặc psql

## 🔧 Các giải pháp thường gặp

### Giải pháp 1: Redeploy sau khi sửa code

```bash
# Commit code mới
git add .
git commit -m "Fix Railway deployment issues"
git push origin main

# Railway sẽ tự động rebuild
```

### Giải pháp 2: Restart Service

1. Trong Railway service
2. Tab "Settings"
3. Scroll xuống "Danger Zone"
4. Click "Restart"

### Giải pháp 3: Rebuild từ đầu

1. Tab "Deployments"
2. Click vào deployment cũ
3. Click "Redeploy"

### Giải pháp 4: Kiểm tra Healthcheck

Railway tự động check health bằng HTTP request. Đảm bảo:
- App response HTTP 200 OK tại root path `/`
- Response trong vòng 30 giây

### Giải pháp 5: Tăng Memory (nếu cần)

1. Tab "Settings"
2. Scroll xuống "Resources"
3. Tăng Memory limit lên 1GB hoặc 2GB

## 📊 Xem Logs Real-time

### Cách 1: Railway Dashboard
1. Tab "Deployments"
2. Click "View Logs"
3. Logs sẽ stream real-time

### Cách 2: Railway CLI
```bash
# Cài đặt CLI
npm i -g @railway/cli

# Login
railway login

# Link project
railway link

# Xem logs
railway logs

# Xem logs real-time
railway logs --follow
```

## 🐛 Debug từng bước

### 1. Kiểm tra Build có thành công không?

Trong Build Logs, tìm:
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX s
```

Nếu **BUILD FAILED** → Sửa lỗi compile trước.

### 2. Kiểm tra WAR file có được tạo không?

Trong Build Logs, tìm:
```
Building war: /app/target/DACK_WEB_NHOM1-1.0-SNAPSHOT.war
```

Nếu không có → Kiểm tra pom.xml.

### 3. Kiểm tra Tomcat có start không?

Trong Deploy Logs, tìm:
```
🚀 Starting Tomcat on port 8080
INFO: Starting ProtocolHandler ["http-nio-8080"]
```

Nếu không có → Tomcat không start được.

### 4. Kiểm tra Database connection

Trong Deploy Logs, tìm:
```
✅ Connected to Railway PostgreSQL database successfully!
```

Nếu thấy lỗi connection → Kiểm tra database variables.

### 5. Kiểm tra App có deploy không?

Trong Deploy Logs, tìm:
```
INFO: Deployment of web application archive [ROOT.war] has finished
INFO: Server startup in [xxxx] milliseconds
```

Nếu có → App đã start thành công!

## 🎯 Checklist cuối cùng

Trước khi hỏi support, đảm bảo:

- [ ] Code đã được push lên GitHub
- [ ] Railway đã rebuild với code mới
- [ ] Database service đang running
- [ ] Database variables đã được link với app service
- [ ] Build logs không có error
- [ ] Deploy logs không có error
- [ ] Đã đợi ít nhất 2-3 phút sau khi deploy
- [ ] Đã thử restart service
- [ ] Đã thử redeploy

## 📸 Gửi logs để được hỗ trợ

Nếu vẫn lỗi, gửi các thông tin sau:

1. **Build Logs** (50 dòng cuối)
2. **Deploy Logs** (100 dòng cuối)
3. **Screenshot** tab Variables
4. **Screenshot** lỗi trên browser

Copy logs bằng cách:
```bash
railway logs > logs.txt
```

Hoặc screenshot trong Railway dashboard.

## 🆘 Liên hệ hỗ trợ

- Railway Discord: https://discord.gg/railway
- Railway Docs: https://docs.railway.app/troubleshoot/fixing-common-errors
- GitHub Issues: Tạo issue trong repo này

---

## 🔄 Sau khi sửa xong

1. Commit và push code:
```bash
git add .
git commit -m "Fix Railway deployment"
git push origin main
```

2. Đợi Railway rebuild (5-10 phút)

3. Kiểm tra logs xem có thông báo thành công:
```
✅ Connected to Railway PostgreSQL database successfully!
INFO: Server startup in [xxxx] milliseconds
```

4. Truy cập domain và test app

**Chúc bạn debug thành công! 🚀**
