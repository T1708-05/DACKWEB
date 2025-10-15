FROM tomcat:10.1-jdk17

# Copy WAR file to Tomcat webapps directory
COPY target/DACK_WEB_NHOM1-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Set environment variables
ENV VNP_RETURN_URL="https://web-electromartshop.fly.dev/payment-return"
ENV VNP_TMN_CODE="K1SH6864"
ENV VNP_HASH_SECRET="1J64G1DKLNTS3B30FSYO6XSPMW6QBE7E"

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
