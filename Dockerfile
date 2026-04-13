FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM tomcat:10.1-jdk17

# Xóa webapps mặc định
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file
COPY --from=build /app/target/DACK_WEB_NHOM1-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Tạo script start để set port từ Railway
COPY <<'EOF' /usr/local/tomcat/bin/railway-start.sh
#!/bin/bash
set -e

# Railway cung cấp PORT env variable
# Tomcat mặc định dùng 8080, nhưng Railway có thể yêu cầu port khác
RAILWAY_PORT=${PORT:-8080}

echo "🚀 Starting Tomcat on port $RAILWAY_PORT"
echo "📊 Environment: ${RAILWAY_ENVIRONMENT:-local}"
echo "🗄️  Database: ${PGDATABASE:-not-set}"

# Cấu hình Tomcat để listen trên port Railway yêu cầu
sed -i "s/8080/$RAILWAY_PORT/g" /usr/local/tomcat/conf/server.xml

# Start Tomcat
exec catalina.sh run
EOF

RUN chmod +x /usr/local/tomcat/bin/railway-start.sh

EXPOSE 8080

CMD ["/usr/local/tomcat/bin/railway-start.sh"]
