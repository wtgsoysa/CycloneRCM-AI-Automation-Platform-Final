# XPath Quick Reference Guide - File History Tests
## For SMOKE_FH_007 to SMOKE_FH_015

---

## Critical XPath Locators That May Need Adjustment

### 1. Dropdown Options Locator (SMOKE_FH_007)

**Current Implementation:**
```java
By optionLocator = By.xpath("//p-dropdownitem//li[contains(text(),'" + optionText + "')]");
```

**Alternative XPaths (if current fails):**
```xpath
// Option 1: Direct text match
//p-dropdownitem//li[.='<option_text>']

// Option 2: Span within li
//p-dropdownitem//li//span[text()='<option_text>']

// Option 3: Exact structure from your sample
/html/body/div[3]/div/ul/p-dropdownitem[1]/li  // For "All"
/html/body/div[3]/div/ul/p-dropdownitem[2]/li  // For "Success"
/html/body/div[3]/div/ul/p-dropdownitem[3]/li  // For "Fail"
/html/body/div[3]/div/ul/p-dropdownitem[4]/li  // For "Manually Corrected"
/html/body/div[3]/div/ul/p-dropdownitem[5]/li  // For "Duplicated"
/html/body/div[3]/div/ul/p-dropdownitem[6]/li  // For "Processing"
```

**Update Location:** `FileHistoryPage.java` line ~245

---

### 2. Dropdown Button (SMOKE_FH_007)

**Current Implementation:**
```java
By dropdownButton = By.xpath("(//cyclone-list-file-history//p-dropdown)[1]//div[contains(@class,'p-dropdown-trigger')]");
```

**Your Exact XPath:**
```xpath
/html/body/ng-component/div/div/div[2]/file-history-app/div/div[1]/cyclone-list-file-history/div/div/div/div/div[2]/div/div/div/div[1]/div/div[1]/div/div[2]/p-dropdown/div/div[2]
```

**Recommended (more stable):**
```xpath
//div[contains(@class,'p-dropdown-trigger') and ancestor::cyclone-list-file-history]
```

**Update Location:** `FileHistoryPage.java` line ~228

---

### 3. Success Count (SMOKE_FH_008)

**Current Implementation:**
```java
By successCountLocator = By.xpath(".//td/span[2]/div/div[7]/div[3]/span");
```

**Your Exact XPath:**
```xpath
/html/body/ng-component/div/div/div[2]/file-history-app/div/div[1]/cyclone-list-file-history/div/div/div/div/div[2]/div/div/form/p-table/div/div/table/tbody/tr[1]/td/span[2]/div/div[7]/div[3]
```

**Alternative (relative to row):**
```xpath
.//span[normalize-space()='Success Count']/parent::div/following-sibling::div[1]//span
```

**Update Location:** `FileHistoryPage.java` line ~500

---

### 4. Fail Count (SMOKE_FH_009)

**Current Implementation:**
```java
By failCountLocator = By.xpath(".//td/span[2]/div/div[7]/div[4]/span");
```

**Your Exact XPath:**
```xpath
/html/body/ng-component/div/div/div[2]/file-history-app/div/div[1]/cyclone-list-file-history/div/div/div/div/div[2]/div/div/form/p-table/div/div/table/tbody/tr[1]/td/span[2]/div/div[7]/div[4]
```

**Alternative (relative to row):**
```xpath
.//span[normalize-space()=' Fail Count']/parent::div/following-sibling::div[1]//span
```

**Update Location:** `FileHistoryPage.java` line ~512

---

### 5. Invoice Count (SMOKE_FH_006 & others)

**Current Implementation:**
```java
By invoiceCountLocator = By.xpath(".//td/span[2]/div/div[7]/div[2]");
```

**Your Exact XPath:**
```xpath
/html/body/ng-component/div/div/div[2]/file-history-app/div/div[1]/cyclone-list-file-history/div/div/div/div/div[2]/div/div/form/p-table/div/div/table/tbody/tr[1]/td/span[2]/div/div[7]/div[2]
```

**Update Location:** `FileHistoryPage.java` line ~524

---

### 6. Deleted Count (SMOKE_FH_006)

**Your Exact XPath:**
```xpath
/html/body/ng-component/div/div/div[2]/file-history-app/div/div[1]/cyclone-list-file-history/div/div/div/div/div[2]/div/div/form/p-table/div/div/table/tbody/tr[1]/td/span[2]/div/div[7]/div[5]
```

**Update Location:** `FileHistoryPage.java` line ~211

---

### 7. Amount (SMOKE_FH_006)

**Your Exact XPath:**
```xpath
/html/body/ng-component/div/div/div[2]/file-history-app/div/div[1]/cyclone-list-file-history/div/div/div/div/div[2]/div/div/form/p-table/div/div/table/tbody/tr[1]/td/span[2]/div/div[7]/div[6]
```

**Update Location:** `FileHistoryPage.java` line ~220

---

## How to Update XPaths

### Step 1: Identify the Failing Locator
Look at the error message. Example:
```
Option 'All' not found: no such element: Unable to locate element: 
{"method":"xpath","selector":"//p-dropdownitem//li[contains(.,'All')]"}
```

### Step 2: Open Browser DevTools
1. Navigate to File History page
2. Click dropdown to open options
3. Inspect the option element
4. Copy the exact XPath

### Step 3: Update FileHistoryPage.java
Replace the dynamic XPath with your exact one:

**Before:**
```java
By optionLocator = By.xpath("//p-dropdownitem//li[contains(text(),'" + optionText + "')]");
```

**After (using your exact structure):**
```java
private By getDropdownOptionXPath(String optionText) {
    switch(optionText) {
        case "All": return By.xpath("/html/body/div[3]/div/ul/p-dropdownitem[1]/li");
        case "Success": return By.xpath("/html/body/div[3]/div/ul/p-dropdownitem[2]/li");
        case "Fail": return By.xpath("/html/body/div[3]/div/ul/p-dropdownitem[3]/li");
        case "Manually Corrected": return By.xpath("/html/body/div[3]/div/ul/p-dropdownitem[4]/li");
        case "Duplicate": return By.xpath("/html/body/div[3]/div/ul/p-dropdownitem[5]/li");
        case "Processing": return By.xpath("/html/body/div[3]/div/ul/p-dropdownitem[6]/li");
        default: return By.xpath("//p-dropdownitem//li[contains(text(),'" + optionText + "')]");
    }
}
```

Then update `isDropdownOptionDisplayed()`:
```java
public boolean isDropdownOptionDisplayed(String optionText) {
    try {
        Thread.sleep(1500);
        return driver.findElement(getDropdownOptionXPath(optionText)).isDisplayed();
    } catch (Exception e) {
        System.out.println("Option '" + optionText + "' not found: " + e.getMessage());
        return false;
    }
}
```

---

## Status Badge XPaths

### File Status (LEFT Panel)
**Current:**
```xpath
.//p-badge/span
```

**Your Sample:**
```xpath
/html/body/ng-component/div/div/div[2]/file-history-app/div/div[1]/cyclone-list-file-history/div/div/div/div/div[2]/div/div/form/p-table/div/div/table/tbody/tr[1]/td/span[2]/div/div[5]/span[3]/p-badge/span
```

### Invoice Status (RIGHT Panel)
**Current:**
```xpath
.//td[last()]//p-badge//span
```

**Remains stable - no change needed**

---

## Search Elements

### Search Input Field
**Current:**
```xpath
//input[@placeholder='Search by file name invoice #']
```
**Status:** ✅ Should work fine

### Search Button
**Current:**
```xpath
//button[@ptooltip='Search']
```
**Status:** ✅ Should work fine

---

## Testing XPaths in Browser Console

### Quick Test Method:
```javascript
// Open Browser Console (F12)
// Paste this:
$x("//p-dropdownitem//li[contains(text(),'All')]")

// Should return the element
// If empty array [], XPath is wrong
```

### Find Correct XPath:
```javascript
// 1. Right-click element → Inspect
// 2. Right-click in Elements tab → Copy → Copy XPath
// 3. Test in console:
$x("YOUR_COPIED_XPATH")
```

---

## Common XPath Issues & Solutions

### Issue 1: Dynamic IDs
**Problem:** `id="pr_id_60-table"` changes on each load  
**Solution:** Use class-based or structure-based XPath

### Issue 2: Shadow DOM
**Problem:** Elements inside shadow-root  
**Solution:** Use `/deep/` or `>>>` selectors (rarely needed in Angular)

### Issue 3: Lazy Loading
**Problem:** Elements load after page ready  
**Solution:** Increase wait time, use retry logic

### Issue 4: Dropdown Panel Outside DOM
**Problem:** Dropdown renders in `<body>` not in component  
**Solution:** Use absolute XPath from `<body>` level

---

## Recommended XPath Strategy

### For Stable Elements (Headers, Labels)
✅ Use relative XPath with class/attribute
```xpath
//span[contains(text(),'Received Files')]
```

### For Dynamic Elements (Dropdowns, Modals)
✅ Use index-based absolute XPath
```xpath
/html/body/div[3]/div/ul/p-dropdownitem[1]/li
```

### For Row Data
✅ Use relative XPath within row context
```xpath
.//td/span[2]/div/div[7]/div[3]/span
```

---

## Update Checklist

When test fails, update in this order:

- [ ] 1. Dropdown button XPath (line ~228)
- [ ] 2. Dropdown options XPath (line ~245)
- [ ] 3. Success Count XPath (line ~500)
- [ ] 4. Fail Count XPath (line ~512)
- [ ] 5. Invoice Count XPath (line ~524)
- [ ] 6. Status badge XPath (if needed)
- [ ] 7. Recompile: `mvn clean test-compile`
- [ ] 8. Re-run: `mvn test -Dtest=FileHistoryTest#SMOKE_FH_007`

---

## Contact for XPath Issues

If XPath updates don't resolve the issue:
1. Capture screenshot of element in DevTools
2. Copy full HTML structure
3. Provide browser version & OS
4. Share exact error message

---

*End of XPath Quick Reference Guide*
