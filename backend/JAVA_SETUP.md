# Java Setup Instructions

## Issue
Spring Boot 3.2.0 requires **Java 17 or higher**, but your system currently has Java 8 installed. The Maven compiler plugin is trying to use the `--release` flag which is not supported in Java 8.

## Solution Options

### Option 1: Install Java 17 (Recommended)

#### Using SDKMAN (Recommended - you already have it):
```bash
sdk install java 17.0.9-tem
sdk use java 17.0.9-tem
```

Verify:
```bash
java -version
# Should show: openjdk version "17.x.x"
```

#### Using Homebrew (macOS):
```bash
brew install openjdk@17
```

Then set JAVA_HOME:
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

#### Manual Installation:
1. Download Java 17 from: https://adoptium.net/temurin/releases/
2. Install it
3. Set JAVA_HOME environment variable:
   ```bash
   export JAVA_HOME=/path/to/java17
   export PATH=$JAVA_HOME/bin:$PATH
   ```

**Important**: After installing Java 17, make sure Maven uses it:
```bash
# Check which Java Maven is using
mvn -version

# If it still shows Java 8, set JAVA_HOME before running Maven:
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
mvn clean compile
```

### Option 2: Use Java 8 with Spring Boot 2.7.x

If you prefer to stick with Java 8, follow these steps:

1. **Backup current pom.xml**:
   ```bash
   cp pom.xml pom.xml.backup
   ```

2. **Use the Java 8 compatible pom.xml**:
   ```bash
   cp pom-java8.xml pom.xml
   ```

3. **Replace all `jakarta.*` imports with `javax.*`**:
   ```bash
   # Run this in the backend directory
   find src -name "*.java" -exec sed -i '' 's/import jakarta\./import javax./g' {} \;
   ```

4. **Build**:
   ```bash
   mvn clean compile
   ```

## Quick Fix for Current Setup

If you want to use Spring Boot 3.2.0 with Java 17:

1. Install Java 17 using SDKMAN:
   ```bash
   sdk install java 17.0.9-tem
   sdk use java 17.0.9-tem
   ```

2. Verify Java version:
   ```bash
   java -version
   ```

3. Set JAVA_HOME and build:
   ```bash
   export JAVA_HOME=$(/usr/libexec/java_home -v 17)
   cd backend
   mvn clean compile
   ```

## Verify Installation

After installing Java 17, verify:
```bash
java -version
# Should show: openjdk version "17.x.x"

mvn -version
# Should show: Java version: 17.x.x
```

Then try building:
```bash
cd backend
mvn clean compile
```

## Current Status
- **Spring Boot Version**: 3.2.0
- **Required Java Version**: 17+
- **Current Java Version**: 8 (needs upgrade)
- **Error**: `invalid flag: --release` (Java 8 doesn't support this flag)
