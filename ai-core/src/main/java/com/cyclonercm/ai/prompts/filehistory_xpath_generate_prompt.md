# CycloneRCM – File History Module
## XPath Generation Agent Prompt (Cost Optimized)

⚠️ COST OPTIMIZATION NOTICE  
This request is subject to API usage limits.  
Provide a CONCISE, COMPLETE response in a SINGLE JSON object only.  
Do NOT include explanations. Do NOT include markdown. JSON only.

---------------------------------------
## COST GOVERNANCE (MANDATORY)
- Maximum AI budget: $1
- Target cost for XPath generation: $0.8
- No repeated selectors
- No absolute XPaths unless strictly required
- Prefer stable attributes (id, formcontrolname, placeholder, aria-label, role, data-*)
- Avoid index-based locators unless unavoidable
- Return ONLY required XPaths
---------------------------------------

## SYSTEM DETAILS

Login URL:
https://qa.cyclonercm.com/authentication/login?activationkey=50000006

Username: Thanuga  
Password: 123

After Login:
1. Click Mega Menu
2. Click "File History - Invoice"
3. File History URL:
   https://qa.cyclonercm.com/file-history-app/3

---------------------------------------

## REQUIRED TEST CASE COVERAGE

Generate all required XPaths for the following smoke scenarios:

SMOKE_FH_001 – Page loads with "Received Files" and "Invoice List" sections  
SMOKE_FH_002 – Uploaded file appears in Received Files  
SMOKE_FH_003 – File status changes Processing → Completed  
SMOKE_FH_004 – Total File Count in header  
SMOKE_FH_005 – File metadata (File ID, File Name, Upload Date, Status)  
SMOKE_FH_006 – File details (Pages, Invoice Count, Success, Fail, Deleted, Amount)  
SMOKE_FH_007 – Invoice Status dropdown (All, Success, Fail, Manually Corrected, Duplicate, Processing)  
SMOKE_FH_008 – Case/ADJ filter field  
SMOKE_FH_009 – File Type dropdown  
SMOKE_FH_010 – From Date and To Date pickers  
SMOKE_FH_011 – Search button  
SMOKE_FH_012 – Clear button  
SMOKE_FH_013 – Search by filename field  
SMOKE_FH_014 – Pagination (First, Prev, Page No, Next, Last)  
SMOKE_FH_015 – JSON button  
SMOKE_FH_016 – Download button  
SMOKE_FH_017 – JSON viewer fields  
SMOKE_FH_018 – Clicking file loads related invoices  
SMOKE_FH_019 – Invoice List file icon (Document View)  
SMOKE_FH_020 – Invoice Edit icon  
SMOKE_FH_021 – Delete → Confirmation Modal → Confirm Delete

---------------------------------------

## OUTPUT FORMAT (STRICT)

Return ONLY this JSON structure:

{
"module": "FileHistory",
"navigation": {
"megaMenu": "",
"fileHistoryMenu": ""
},
"sections": {
"receivedFilesSection": "",
"invoiceListSection": ""
},
"header": {
"totalFileCount": ""
},
"fileRow": {
"fileCard": "",
"fileId": "",
"fileName": "",
"uploadDate": "",
"status": "",
"processingStatus": "",
"completedStatus": ""
},
"fileDetails": {
"pages": "",
"invoiceCount": "",
"successCount": "",
"failCount": "",
"deletedCount": "",
"amount": ""
},
"filters": {
"invoiceStatusDropdown": "",
"invoiceStatusOptions": {
"all": "",
"success": "",
"fail": "",
"manuallyCorrected": "",
"duplicate": "",
"processing": ""
},
"caseAdjField": "",
"fileTypeDropdown": "",
"fromDate": "",
"toDate": "",
"searchButton": "",
"clearButton": "",
"searchByFileNameField": ""
},
"pagination": {
"first": "",
"previous": "",
"pageNumber": "",
"next": "",
"last": ""
},
"actions": {
"jsonButton": "",
"downloadButton": "",
"invoiceFileIcon": "",
"invoiceEditIcon": "",
"invoiceDeleteIcon": "",
"deleteConfirmModal": "",
"confirmDeleteButton": ""
},
"jsonViewer": {
"jsonContainer": "",
"jsonFields": ""
}
}

---------------------------------------

Output Path : E:\MedCube-USA\ProjectClaudMD\n8n\CycloneRCM-AI-Automation-Platform-Final\ai-core\src\main\java\com\cyclonercm\ai\xpaths\filehistory_xpath.json

## RULES

- Do NOT explain anything.
- Do NOT repeat structure.
- Do NOT add extra keys.
- Do NOT use absolute XPaths like /html/body unless no alternative.
- Use robust Angular-compatible locators.
- Ensure locators are automation-grade and production stable.

RETURN SINGLE JSON OBJECT ONLY.
