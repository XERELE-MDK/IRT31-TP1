# MyCoolApp - Changes Log

## Overview
This document summarizes all the changes made to the project to get it running properly. **Important: JDK version was reverted to 25 as requested by the user.**

---

## 1. Java JDK Version - REVERTED to 25 (User Request)

**Original Issue:** The project's `pom.xml` was configured with Java 25 (`<java.version>25</java.version>`), but initially changed to 21 to match the default system JDK.

**User Request:** "I HAVE JDK 25( AT /HOME/xerele-mdk/.jdks) REVERT THE CHANGE"

**Reaction:** The JDK version was reverted back to 25 to accommodate the user's environment.

**Current Status:** ✅ Project compiles and runs successfully with **Java 25.0.4.1** at `/home/xerele-mdk/.jdks/ms-25.0.4.1`

**Build Command Used:**
```bash
export PATH=/home/xerele-mdk/.jdks/ms-25.0.4.1/bin:$PATH
./mvnw spring-boot:run  # or java -jar target/mycoolapp-0.0.1-SNAPSHOT.jar
```

**Verification:** 
```
:: Spring Boot ::                (v4.0.8)

2026-10-07T01:30:47.471Z  INFO 555142 --- [mycoolapp] [           main] c.l.mycoolapp.MycoolappApplication       : Starting MycoolappApplication v0.0.1-SNAPSHOT using Java 25.0.4.1 with PID 555142
```

---

## 2. FunRestController.java Fix

**Problem:** The controller file had structural issues:
- An extra `void main()` method that was invalid
- Missing proper method structure
- Syntax errors preventing compilation

**Error encountered:**
```
unnamed classes are a preview feature and are disabled by default
class, interface, enum, or record expected
```

**Solution:** Rewrote the file with proper Java structure:

```java
package com.luv2code.mycoolapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {

    @GetMapping("/")
    public String sayHello() {

        return "Hello World!";
    }
}
```

**Changes made to `FunRestController.java`:**
- Removed invalid `void main()` method
- Added proper package declaration
- Fixed method annotation placement
- Ensured correct class structure

---

## 3. Spring Security Default Credentials

**Problem:** Spring Security 7 (included via `spring-boot-starter-security`) has **no default credentials**. Attempting to access `http://localhost:8080/` returns 401 Unauthorized with Basic Auth prompt.

**Solution:** Added explicit user configuration to `application.properties`:

```properties
spring.security.user.name=user
spring.security.user.password=password
spring.security.user.role=USER
```

**Login credentials to test the web interface:**
- **Username:** `user`
- **Password:** `password`

**How to test:**
1. Go to `http://localhost:8080/`
2. You'll see a Basic Authentication prompt
3. Enter: `user` / `password`
4. You'll see "Hello World!"

**Note about Spring Security defaults:**
- Spring Boot 3.x / Security 6+: No default credentials - must configure
- Spring Boot 2.x: Sometimes has randomly generated user password logged at startup
- This project uses Spring Security 7, so explicit configuration is required

---

## 4. Application Startup (Java 25)

The application now runs successfully with:
- **Java 25.0.4.1** (as requested by user)
- Spring Boot 4.0.8
- Spring Security active with configured credentials
- REST endpoint `/` returning "Hello World!" after authentication

**Startup confirmation:**
```
Tomcat started on port 8080 (http) with context path '/'
Started MycoolappApplication in 12.266 seconds
```

---

## Summary of Changes

| File | Change | Reason |
|------|--------|--------|
| `pom.xml` | Java version 25 | **Reverted to 25** as user requested - project now runs on JDK 25 |
| `FunRestController.java` | Removed invalid `void main()`, fixed structure | Fix compilation errors |
| `application.properties` | Added `spring.security.user.*` | Enable login authentication |

---

## Testing the Web Interface

**1. Start the application with JDK 25:**
```bash
export PATH=/home/xerele-mdk/.jdks/ms-25.0.4.1/bin:$PATH
cd /home/xerele-mdk/Documents/SPRING\ BOOT/mycoolapp
./mvnw spring-boot:run
# or
java -jar target/mycoolapp-0.0.1-SNAPSHOT.jar
```

**2. Test the login:**
```bash
curl -v -u user:password http://localhost:8080/
# Returns: "Hello World!" with HTTP 200
```

**3. Without credentials (will get 401):**
```bash
curl http://localhost:8080/
# Returns: 401 Unauthorized with Basic Auth prompt
```

---