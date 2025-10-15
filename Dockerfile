FROM tomcat:10.1-jdk17

# Copy WAR file to Tomcat webapps directory
COPY target/DACK_WEB_NHOM1-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Set environment variables
ENV CATALINA_OPTS="-Dfile.encoding=UTF-8 -Duser.timezone=Asia/Ho_Chi_Minh"

# Start Tomcat
CMD ["catalina.sh", "run"]
