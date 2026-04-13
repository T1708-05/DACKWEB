#!/bin/bash

# Railway startup script for ElectroMart
# This script runs before starting Tomcat

echo "🚀 Starting ElectroMart on Railway..."

# Print environment info
echo "📊 Environment Information:"
echo "   Java Version: $(java -version 2>&1 | head -n 1)"
echo "   Timezone: ${TZ:-UTC}"
echo "   Railway Environment: ${RAILWAY_ENVIRONMENT:-not-set}"

# Check database connection
if [ -n "$DATABASE_URL" ]; then
    echo "✅ Database URL configured"
    echo "   Host: ${PGHOST}"
    echo "   Database: ${PGDATABASE}"
    echo "   User: ${PGUSER}"
else
    echo "⚠️  Warning: DATABASE_URL not set"
fi

# Set Java options if not already set
if [ -z "$JAVA_OPTS" ]; then
    export JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC"
    echo "📝 Set default JAVA_OPTS: $JAVA_OPTS"
fi

# Start Tomcat
echo "🚀 Starting Tomcat..."
exec catalina.sh run
