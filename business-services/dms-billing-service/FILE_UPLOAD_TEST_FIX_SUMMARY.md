# File Upload Test Fix Summary

## Issues Fixed

### 1. Missing UploadSmokeTestDataProperties Class
**Problem:** `FileuploadTest.java` was importing `UploadSmokeTestDataProperties` which didn't exist in the billing service module.

**Solution:** Created new utility class at:
- `business-services/dms-billing-service/src/main/java/com/cyclonercm/utils/UploadSmokeTestDataProperties.java`

This class loads properties from `uploadsmoketestdata.properties` file (which already exists at `business-services/dms-billing-service/src/test/resources/uploadsmoketestdata.properties`).

### 2. Incorrect Import Statements
**Problem:** `FileuploadTest.java` was importing classes from wrong modules:
- `import com.cyclonercm.auth.pages.AuthenticationPage;` (from authentication-service)
- `import com.cyclonercm.files.base.SmokeBaseTest;` (from filemanagement-service)
- `import com.cyclonercm.files.pages.FileUploadPage;` (from filemanagement-service)

**Solution:** Updated imports to use local billing service classes:
- `import com.cyclonercm.pages.AuthenticationPage;` (local)
- `import com.cyclonercm.billing.base.SmokeBaseTest;` (local)
- `import com.cyclonercm.pages.FileUploadPage;` (local)

### 3. Missing Locator Constants
**Problem:** `LocatorConstants.java` in billing service was missing upload-related constants:
- `getStartedButton`
- `UploadSuccessToast`
- `UploadProgressToast`

**Solution:** Added the missing locators to `business-services/dms-billing-service/src/main/java/com/cyclonercm/utils/LocatorConstants.java`:
```java
//-------- File Upload Locators ---------
public static final By getStartedButton = By.xpath("//div[@class='clearfix system-color- topbar']//button[1]");
public static final By UploadSuccessToast = By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4");
public static final By UploadProgressToast = By.xpath("//p-toast[@position='top-right']//div[contains(@class,'ui-toast-message')]");
```

### 4. Module Dependencies
**Problem:** Billing service needed access to shared utilities from authentication-service module.

**Solution:** Previously added (in earlier fix):
- Added `authentication-service` as a dependency in `business-services/dms-billing-service/pom.xml`
- This provides access to `com.cyclonercm.utils` package classes like `WaitUtils`, `DriverFactory`, etc.

## Files Created/Modified

### Files Created:
1. `business-services/dms-billing-service/src/main/java/com/cyclonercm/utils/UploadSmokeTestDataProperties.java` - NEW

### Files Modified:
1. `business-services/dms-billing-service/src/test/java/com/cyclonercm/billing/tests/smoke/FileuploadTest.java` - Fixed imports
2. `business-services/dms-billing-service/src/main/java/com/cyclonercm/utils/LocatorConstants.java` - Added upload locators
3. `business-services/dms-billing-service/pom.xml` - Added authentication-service dependency (from previous fix)
4. `business-services/dms-billing-service/src/test/java/com/cyclonercm/billing/base/SmokeBaseTest.java` - Fixed imports (from previous fix)

## Verification Steps

### 1. IntelliJ IDEA - Reimport Maven Project
```
Right-click on business-services/dms-billing-service/pom.xml
→ Maven → Reload Project
```

Or click the Maven refresh button in the Maven tool window.

### 2. Invalidate Caches (if needed)
If IDE still shows errors after Maven reimport:
```
File → Invalidate Caches / Restart → Invalidate and Restart
```

### 3. Verify Properties File Exists
Ensure `uploadsmoketestdata.properties` exists at:
```
business-services/dms-billing-service/src/test/resources/uploadsmoketestdata.properties
```

The file should contain:
- validUserId
- validPassword
- systemLabel
- buildNumber
- uploadFilePathDMS1
- uploadMultipleFilePathDMS2
- uploadMultipleFilePathDMS3

### 4. Run Tests from IntelliJ
Create/Update TestNG Run Configuration:
- **Test kind:** Class or Suite
- **Class:** `com.cyclonercm.billing.tests.smoke.FileuploadTest`
- **Module:** `dms-billing-service` (IMPORTANT!)
- **VM options:** (optional) `-ea`
- **Working directory:** `$MODULE_DIR$`

### 5. Run Tests from Command Line (PowerShell)
From repository root:
```powershell
# Compile the module
mvn -pl business-services/dms-billing-service -am clean compile

# Run specific test class
mvn -pl business-services/dms-billing-service test -Dtest=FileuploadTest

# Or run all smoke tests in billing module
mvn -pl business-services/dms-billing-service test
```

## Architecture Notes

### Module Structure
```
business-services/dms-billing-service/
├── src/main/java/com/cyclonercm/
│   ├── pages/
│   │   ├── AuthenticationPage.java
│   │   └── FileUploadPage.java
│   └── utils/
│       ├── UploadSmokeTestDataProperties.java  ← NEW
│       ├── SmokeTestDataProperties.java
│       ├── LocatorConstants.java
│       ├── WaitUtils.java
│       ├── DriverFactory.java
│       ├── ExtentManager.java
│       └── ... (other utils)
└── src/test/
    ├── java/com/cyclonercm/billing/
    │   ├── base/
    │   │   ├── SmokeBaseTest.java
    │   │   └── BaseTest.java
    │   └── tests/smoke/
    │       ├── AuthenticationTest.java
    │       └── FileuploadTest.java
    └── resources/
        ├── uploadsmoketestdata.properties
        ├── billingsmoketestdata.properties
        └── BillingSmokeSuite.xml
```

### Dependency Flow
```
dms-billing-service (module)
    ↓ depends on
authentication-service (module)
    ↓ provides
com.cyclonercm.utils package classes
    (DriverFactory, WaitUtils, ConfigReader, etc.)
```

### Why We Have Local Utils Package
The billing service has its own `com.cyclonercm.utils` package that:
1. Contains billing-specific utilities and properties loaders
2. Also includes some duplicated classes from authentication-service for module independence
3. The dependency on authentication-service provides additional shared utilities

This is intentional to allow the billing module to be:
- Self-contained for its specific test data
- Share common framework utilities via dependency
- Independent deployment if needed

## Test Data Properties Files

The billing service now has two separate smoke test properties files:

1. **billingsmoketestdata.properties** - Used by `AuthenticationTest.java`
   - Loaded via `SmokeTestDataProperties.java`
   - Contains authentication test data

2. **uploadsmoketestdata.properties** - Used by `FileuploadTest.java`
   - Loaded via `UploadSmokeTestDataProperties.java`
   - Contains file upload test data
   - Contains file paths for upload tests

## Common Errors and Solutions

### Error: "Cannot resolve symbol 'UploadSmokeTestDataProperties'"
**Solution:** Maven reimport is needed. The new class exists but IDE hasn't indexed it yet.

### Error: "Cannot find class in classpath: com.cyclonercm.billing.tests.smoke.FileuploadTest"
**Solution:** 
1. Ensure Maven module is properly imported
2. Set TestNG run configuration Module to `dms-billing-service`
3. Run `mvn clean compile` from module directory

### Error: "Failed to load uploadsmoketestdata.properties"
**Solution:** Verify the properties file exists in `src/test/resources/` and is included in the build

### Error: File upload path not found
**Solution:** Ensure test data PDF files exist in the paths specified in `uploadsmoketestdata.properties`. The paths are relative to the project root.

## Next Steps

1. **Reimport Maven project in IntelliJ**
2. **Run the FileuploadTest class** to verify all fixes work
3. **Check console output** for any file path or runtime errors
4. **Add more test data files** if needed in `src/test/resources/testdata/`
5. **Update properties file** with actual test data file paths

## Contact

For issues or questions about this fix, contact the QA Automation Team.

---
**Last Updated:** February 16, 2026
**Fixed By:** AI Assistant
