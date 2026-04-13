# 🚀 Quick Start - Deploy lên Railway trong 5 phút

## Bước 1: Chuẩn bị (1 phút)

```bash
# Clone hoặc đảm bảo code đã có trên GitHub
git add .
git commit -m "Prepare for Railway deployment"
git push origin main
```

## Bước 2: Tạo Project trên Railway (2 phút)

1. Truy cập: https://railway.app
2. Đăng nhập bằng GitHub
3. Click **"New Project"**
4. Chọn **"Provision PostgreSQL"** → Database được tạo
5. Click **"New"** → **"GitHub Repo"** → Chọn repo của bạn

## Bước 3: Link Database (1 phút)

1. Click vào **Application Service**
2. Tab **"Variables"** → **"New Variable"** → **"Add Reference"**
3. Chọn **PostgreSQL service**
4. Chọn tất cả variables: `DATABASE_URL`, `PGHOST`, `PGPORT`, `PGUSER`, `PGPASSWORD`, `PGDATABASE`
5. Click **"Add"**

## Bước 4: Generate Domain (30 giây)

1. Vẫn trong Application Service
2. Tab **"Settings"** → Scroll xuống **"Networking"**
3. Click **"Generate Domain"**
4. Copy URL: `https://your-app.up.railway.app`

## Bước 5: Đợi Deploy & Test (30 giây)

1. Tab **"Deployments"** → Xem build progress
2. Đợi status chuyển sang **"Success"** (khoảng 5-10 phút)
3. Truy cập URL vừa copy
4. Test: Đăng ký → Đăng nhập → Xem sản phẩm

## ✅ Xong!

Application của bạn đã live tại: `https://your-app.up.railway.app`

---

## 🔧 Nếu có lỗi

### Lỗi build
```bash
# Xem logs
railway logs

# Hoặc trong Railway dashboard: Deployments → View Logs
```

### Lỗi database connection
1. Verify variables đã được link: Tab "Variables"
2. Check database đang chạy: Click vào PostgreSQL service
3. Xem application logs: Tab "Deployments" → "View Logs"

### Application không start
1. Check JAVA_OPTS: Tab "Variables" → Add `JAVA_OPTS=-Xmx512m`
2. Verify Dockerfile: Đảm bảo file Dockerfile đúng format
3. Check pom.xml: Verify dependencies

---

## 📚 Chi tiết hơn

Xem file `RAILWAY_SETUP.md` để có hướng dẫn chi tiết đầy đủ.

## 🆘 Cần trợ giúp?

- Railway Docs: https://docs.railway.app
- Railway Discord: https://discord.gg/railway
- GitHub Issues: Tạo issue trong repo này
