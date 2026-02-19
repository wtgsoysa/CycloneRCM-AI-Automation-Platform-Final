# Quick Start Guide: SMOKE_DEI_008A Test

## Test ID
**SMOKE_DEI_008A**: Interpreter Invoice Details Validation

## Purpose
Validates the Interpreter Invoice Details popup functionality, including:
- Opening the popup
- Verifying all fields are present
- Editing field values
- Saving changes
- Verifying data persistence

## Prerequisites
1. Valid user credentials configured
2. Chrome browser with compatible ChromeDriver
3. Test data with Interpreter Invoice Details available
4. Access to DMS Billing application

## Running the Test

### Option 1: Run Single Test
```bash
# Using Maven
mvn test -Dtest=HistoryEditInvoiceTest#SMOKE_DEI_008A

# Using TestNG XML (add to your suite)
<test name="Interpreter Invoice Details Test">
    <classes>
        <class name="com.cyclonercm.billing.tests.smoke.HistoryEditInvoiceTest">
            <methods>
                <include name="SMOKE_DEI_008A"/>
            </methods>
        </class>
    </classes>
</test>
```

### Option 2: Run Full Smoke Suite
```bash
mvn test -DsuiteXmlFile=test-suites/BillingSmokeSuite.xml
```

### Option 3: Run from IDE (IntelliJ/Eclipse)
1. Navigate to `HistoryEditInvoiceTest.java`
2. Find method `SMOKE_DEI_008A()`
3. Right-click → Run 'SMOKE_DEI_008A()'

## Test Execution Flow

```
1. Setup (from SMOKE_DEI_001)
   └─> Login to application
   └─> Navigate to Daily Billing
   └─> Click first invoice to open Edit screen

2. SMOKE_DEI_008A Execution
   └─> Click "Interpreter Invoice Details" link
   └─> Verify popup opens
   └─> Verify 7 mandatory fields:
       • Language
       • Exotic
       • Service Rate
       • Interp. Type
       • Dr. Name
       • Dr. Address
       • Terms
   └─> Capture original values
   └─> Click Edit button
   └─> Modify field values
   └─> Click Save button
   └─> Reopen popup to verify changes
   └─> Close popup

3. Teardown
   └─> Close browser
```

## Expected Results

### ✅ Success Criteria
- Popup opens with title "Interpreter Invoice Details"
- All 7 fields are present and accessible
- Field values can be read
- Edit button enables editing
- Field values can be modified
- Save button persists changes
- Changes are retained after closing and reopening popup

### ❌ Failure Scenarios
- Popup link not found or not clickable
- Popup doesn't open
- Any of the 7 fields missing
- Edit button doesn't work
- Save button doesn't persist changes
- Data lost after save

## Test Data

### Default Test Values
The test uses these sample values for editing:
```java
Language: "Spanish (Updated)"
Service Rate: "$150.00"
Dr. Name: "Dr. Updated Name"
Dr. Address: "123 Updated Medical Center"
Terms: "Updated payment terms"
```

You can modify these in the test method as needed.

## Debugging

### Enable Detailed Logging
The test includes console output with emojis for easy tracking:
- 🔗 Link clicking
- 🏷️ Popup verification
- 📋 Field verification
- 📖 Reading values
- ✏️ Editing fields
- 💾 Saving changes
- 🔍 Verifying persistence

### Common Issues

#### Issue: Link not found
**Symptom**: `Failed to click Interpreter Invoice Details link`
**Solution**: 
- Verify invoice has interpreter details
- Check XPath in `interpreterInvoiceDetailsLink` locator
- Ensure Edit screen is fully loaded before test runs

#### Issue: Popup doesn't open
**Symptom**: `Failed to get Interpreter Invoice Details popup label`
**Solution**:
- Increase wait time in `clickInterpreterInvoiceDetailsLink()`
- Verify popup dialog layer (div[2] vs div[3] vs div[4])
- Check for browser console errors

#### Issue: Fields not found
**Symptom**: `✗ [Field Name] field missing`
**Solution**:
- Inspect the popup HTML structure
- Update field locator XPath if needed
- Verify field labels match exactly

#### Issue: Changes not saved
**Symptom**: `AssertionError: Language should be 'Spanish (Updated)' but found: [old value]`
**Solution**:
- Verify Save button is clicked successfully
- Check for validation errors in popup
- Ensure popup is reopened after save before verification

## Reporting

### Console Output
Check console for detailed step-by-step execution with Unicode icons:
```
========================================
🧪 SMOKE_DEI_008A: Interpreter Invoice Details Validation
========================================
✓ Step completed successfully
```

### Allure Report
Test results are captured in Allure format:
1. Run tests
2. Generate report: `allure serve allure-results`
3. View detailed execution steps and screenshots

### ExtentReports
HTML report generated at: `test-output/extent-reports/`

## Performance

**Expected Execution Time**: 30-45 seconds
- Popup loading: ~3 seconds
- Field verification: ~5 seconds
- Data entry: ~5 seconds
- Save and verification: ~10 seconds
- Total with waits: ~30-45 seconds

## Integration Points

### Depends On
- **SMOKE_DEI_001**: Must run first to open Edit Invoice screen
- All preceding tests (001-008) must pass for context

### Affects
- **SMOKE_DEI_009** and later: Tests run after this one
- No data changes affect subsequent tests

## Maintenance

### When to Update
- UI changes to Interpreter Invoice Details popup
- Field additions or removals
- Label text changes
- Dialog structure changes (div layers)

### Files to Modify
1. `EditInvoicePage.java` - Update locators
2. `HistoryEditInvoiceTest.java` - Update test logic
3. This documentation

## Support

### Log Files
Check logs at:
- Console output during test run
- `test-output/logs/execution.log`
- Browser console (F12) for JavaScript errors

### Screenshots
Screenshots captured on failure at:
- `test-output/screenshots/`

### Contact
For issues or questions:
- Review `INTERPRETER_INVOICE_DETAILS_IMPLEMENTATION.md`
- Check test logs and screenshots
- Verify XPath locators in browser DevTools

---

**Last Updated**: February 19, 2026
**Test Status**: ✅ Ready for Execution
**Version**: 1.0
