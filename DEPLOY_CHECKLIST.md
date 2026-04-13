# ✅ Railway Deployment Checklist

## Pre-Deployment

- [ ] Code đã được test kỹ trên local
- [ ] Database schema đã được verify
- [ ] Tất cả dependencies trong pom.xml đã đúng
- [ ] Không có hardcoded credentials trong code
- [ ] .gitignore đã loại trừ các file không cần thiết

## Railway Setup

### Database
- [ ] PostgreSQL database đã được tạo trên Railway
- [ ] Đã ghi nhớ thông tin database credentials
- [ ] Database có thể connect từ external (nếu cần)

### Application Service
- [ ] GitHub repository đã được link với Railway
- [ ] Dockerfile đã được verify
- [ ] railway.json đã được cấu hình đúng

### Environment Variables
- [ ] DATABASE_URL đã được set
- [ ] PGHOST, PGPORT, PGDATABASE đã được set
- [ ] PGUSER, PGPASSWORD đã được set
- [ ] JAVA_OPTS đã được cấu hình (nếu cần)
- [ ] TZ đã được set (Asia/Ho_Chi_Minh)

### Networking
- [ ] Public domain đã được generate
- [ ] Custom domain đã được cấu hình (nếu có)
- [ ] SSL certificate đã active

## Deployment

### Build Process
- [ ] Build logs không có error
- [ ] Maven dependencies download thành công
- [ ] WAR file được tạo thành công
- [ ] Docker image build thành công

### Application Start
- [ ] Tomcat start thành công
- [ ] Application deploy thành công
- [ ] Database connection thành công
- [ ] Không có error trong application logs

## Post-Deployment Testing

### Basic Functionality
- [ ] Trang chủ load được
- [ ] Static resources (CSS, JS, images) load được
- [ ] Navigation menu hoạt động

### User Features
- [ ] Đăng ký tài khoản mới
- [ ] Đăng nhập với tài khoản vừa tạo
- [ ] Đăng xuất
- [ ] Quên mật khẩu (nếu có email service)
- [ ] Cập nhật profile

### Product Features
- [ ] Xem danh sách sản phẩm
- [ ] Tìm kiếm sản phẩm
- [ ] Lọc sản phẩm theo category
- [ ] Xem chi tiết sản phẩm
- [ ] So sánh sản phẩm (nếu có)

### Shopping Features
- [ ] Thêm sản phẩm vào giỏ hàng
- [ ] Cập nhật số lượng trong giỏ
- [ ] Xóa sản phẩm khỏi giỏ
- [ ] Checkout process
- [ ] Tạo đơn hàng thành công
- [ ] Xem lịch sử đơn hàng

### Admin Features (nếu có)
- [ ] Đăng nhập admin
- [ ] Xem dashboard
- [ ] Quản lý sản phẩm (CRUD)
- [ ] Quản lý đơn hàng
- [ ] Xem báo cáo

### Performance
- [ ] Trang load trong < 3 giây
- [ ] Database queries không quá chậm
- [ ] Không có memory leak
- [ ] Application không crash sau vài phút

## Security

- [ ] Mật khẩu admin mặc định đã được thay đổi
- [ ] SQL injection đã được prevent
- [ ] XSS đã được prevent
- [ ] CSRF protection đã enable (nếu có)
- [ ] Sensitive data không bị expose trong logs
- [ ] Database connection sử dụng SSL

## Monitoring

- [ ] Railway logs đang hoạt động
- [ ] Error tracking đã được setup (nếu có)
- [ ] Uptime monitoring đã được setup (nếu cần)

## Documentation

- [ ] README.md đã được cập nhật với production URL
- [ ] API documentation đã được cập nhật (nếu có)
- [ ] Deployment guide đã được viết
- [ ] Troubleshooting guide đã được viết

## Backup & Recovery

- [ ] Database backup strategy đã được plan
- [ ] Recovery procedure đã được document
- [ ] Rollback plan đã được chuẩn bị

## Final Steps

- [ ] Thông báo cho team về deployment
- [ ] Cập nhật project documentation
- [ ] Ghi nhận deployment date và version
- [ ] Lưu trữ deployment logs

## Issues Found

Ghi chú các vấn đề phát hiện trong quá trình deploy:

1. 
2. 
3. 

## Notes

Ghi chú thêm:

- Deployment Date: _______________
- Deployed By: _______________
- Railway Project URL: _______________
- Application URL: _______________
- Database: _______________

---

**Status**: ⬜ Not Started | 🟡 In Progress | ✅ Completed | ❌ Failed

**Overall Status**: _____________
