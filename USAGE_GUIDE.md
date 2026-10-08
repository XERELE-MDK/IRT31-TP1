# MyCoolApp - Usage Guide

## Overview
A Spring Boot application with REST endpoint, Spring Security authentication, and JDK 25 support.

---

## 🔧 Project Changes Summary

### 1. Java JDK Version: 25 (Reverted per user request)
- **File**: `pom.xml`
- **Change**: Java version set to 25.0.4.1
- **System JDK**: `/home/xerele-mdk/.jdks/ms-25.0.4.1`
- **Verification**: 
  ```
  java -version  → openjdk version "25.0.4.1" 2026-08-18 LTS
  ```

### 2. FunRestController.java - Fixed Structure
- **File**: `src/main/java/com/luv2code/mycoolapp/FunRestController.java`
- **Problem**: Had invalid `void main()` method, syntax errors
- **Solution**: Rewrote with proper Java class structure, removed invalid method
- **Result**: Project compiles and runs successfully

### 3. application.properties - Security Credentials
- **File**: `src/main/resources/application.properties`
- **Added**: 
  ```
  spring.security.user.name=user
  spring.security.user.password=password
  spring.security.user.role=USER
  ```
- **Purpose**: Configures default Basic Auth credentials for Spring Security 7
- **Note**: Spring Security 7 has no default credentials - must be configured

---

## 🚀 How to Start the Application

### Option 1: Using Maven Wrapper (recommended)
```bash
# Navigate to project directory
cd /home/xerele-mdk/Documents/SPRING\ BOOT/mycoolapp

# Ensure JDK 25 is in PATH
export PATH=/home/xerele-mdk/.jdks/ms-25.0.4.1/bin:$PATH

# Start the application
./mvnw spring-boot:run
```

### Option 2: Using Direct Java Command
```bash
# Navigate to project directory
cd /home/xerele-mdk/Documents/SPRING\ BOOT/mycoolapp

# Build and run the JAR
export PATH=/home/xerele-mdk/.jdks/ms-25.0.4.1/bin:$PATH
java -jar target/mycoolapp-0.0.1-SNAPSHOT.jar
```

### Expected Startup Output:
```
:: Spring Boot ::                (v4.0.8)

2026-10-XX-XX.XX:XX:XX.XXXZ  INFO ##### --- [mycoolapp] [           main] c.l.mycoolapp.MycoolappApplication       : Starting MycoolappApplication v0.0.1-SNAPSHOT using Java 25.0.4.1 with PID #####
2026-10-XX-XX.XX.XXXZ  INFO ##### --- [mycoolapp] [           main] o.s.boot.tomcat.TomcatWebServer          : Tomcat initialized with port 8080 (http)
2026-10-XX-XX.XX.XXXZ  INFO ##### --- [mycoolapp] [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 1 endpoint beneath base path '/actuator'
2026-10-XX-XX.XX.XXXZ  INFO ##### --- [mycoolapp] [           main] o.s.boot.tomcat.TomcatWebServer          : Tomcat started on port 8080 (http) with context path '/'
2026-10-XX-XX.XX.XXXZ  INFO ##### --- [mycoolapp] [           main] c.l.mycoolapp.MycoolappApplication       : Started MycoolappApplication in 12.XXX seconds
```

---

## 🔑 Login Credentials

### Default Credentials (configured in application.properties):
- **Username:** `user`
- **Password:** `password`
- **Role:** `USER`

### How to Test Without Credentials (will fail):
```bash
curl http://localhost:8080/
# Returns: 401 Unauthorized with Basic Auth prompt
```

### How to Login with curl (SUCCESS):

#### Method 1: Basic Authentication (recommended)
```bash
curl -u user:password http://localhost:8080/
# Output: "Hello World!" (HTTP 200)
```

#### Method 2: Explicit Authorization Header
```bash
curl -H "Authorization: Basic dXNlcjpwYXNzd29yZA==" http://localhost:8080/
# Output: "Hello World!" (HTTP 200)
# Note: "dXNlcjpwYXNzd29yZA==" is base64 encoding of "user:password"
```

#### Method 3: Using -u flag with password only (prompts for username):
```bash
curl -u :password http://localhost:8080/
# Will prompt for username interactively
```

---

## 🌐 Testing the Web Interface

### 1. Open in Browser:
1. Go to: `http://localhost:8080/`
2. You'll see a **Basic Authentication prompt**
3. Enter: `user` / `password`
4. You'll see: **"Hello World!"**

### 2. Using curl (Command Line):

#### Without credentials (expect 401):
```bash
curl -v http://localhost:8080/
# Shows: HTTP 401, WWW-Authenticate: Basic realm="Realm"
```

#### With credentials (expect 200):
```bash
curl -v -u user:password http://localhost:8080/
# Shows: HTTP 200, Content-Type: text/plain, body: "Hello World!"
```

#### Just the response body:
```bash
curl -s -u user:password http://localhost:8080/
# Output: Hello World!
```

---

## 📋 Complete Startup & Test Procedure

### Full Workflow:

```bash
# 1. Kill any existing processes
pkill -f "mycoolapp" 2>/dev/null
pkill -f "java.*mycoolapp" 2>/dev/null
sleep 2

# 2. Set JDK 25 path
export PATH=/home/xerele-mdk/.jdks/ms-25.0.4.1/bin:$PATH

# 3. Start the application
cd /home/xerele-mdk/Documents/SPRING\ BOOT/mycoolapp
./mvnw spring-boot:run &
sleep 20

# 4. Test without credentials (should get 401)
echo "=== Test without auth ==="
curl -s http://localhost:8080/
echo ""

# 5. Test with credentials (should get 200)
echo "=== Test with auth ==="
curl -s -u user:password http://localhost:8080/
echo ""

# 6. Verify with verbose output
echo "=== Verbose output ==="
curl -v -u user:password http://localhost:8080/
```

---

## 🛠️ Troubleshooting

### Common Issues:

| Problem | Solution |
|---------|----------|
| `Connection refused` on port 8080 | Ensure application is started, kill existing processes |
| `401 Unauthorized` without credentials | This is expected - provide user/password |
| Wrong password error | Use exact credentials: `user` / `password` |
| Compiler errors about Java version | Ensure JDK 25 is in PATH, not JDK 21 |
| `unsupported major.minor version` | Rebuild project after changing pom.xml |

### To Restart After Changes:
```bash
# Kill existing
pkill -f "mycoolapp" 2>/dev/null

# Clean and rebuild
cd /home/xerele-mdk/Documents/SPRING\ BOOT/mycoolapp
export PATH=/home/xerele-mdk/.jdks/ms-25.0.4.1/bin:$PATH
./mvnw clean package -DskipTests

# Run again
./mvnw spring-boot:run
```

---

## 📁 Related Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven configuration, Java version 25 |
| `FunRestController.java` | REST controller with `/` endpoint |
| `application.properties` | Spring Security default credentials |
| `CHANGES_LOG.md` | Detailed change history |
| `USAGE_GUIDE.md` | This document (current) |

---