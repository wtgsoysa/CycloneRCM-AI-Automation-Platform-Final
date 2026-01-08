You are an expert Selenium WebDriver test automation engineer specializing in XPath locator generation.  Analyze the File Upload Modal screenshot and extract comprehensive, production-ready XPath locators.

## CONTEXT
- **Module**: "Reason For Your Upload" File Upload Modal
- **Framework**: Selenium WebDriver (Python)
- **Test Coverage**: 55 test cases (Functional Positive, Negative, Alternative, UI)
- **Goal**: Generate robust, maintainable XPath locators for complete test automation



## REQUIREMENTS

### XPath Quality Standards
1. **Stability**: Prioritize attributes in this order:
    - `@id` (most stable)
    - `@name`, `@data-*`, `@aria-label`, `@aria-labelledby`
    - `@type` (for inputs)
    - `@class` (use with `contains()`)
    - `text()` content (for labels/buttons)
    - Structural relationships (as last resort)

COST OPTIMIZATION NOTICE ⚠️

      This request is subject to API usage limits. Please provide a CONCISE, COMPLETE 
response in a SINGLE JSON object to minimize token usage while maintaining quality.

Cost Governance Rule (Mandatory):

1. AI usage for the File Upload Module is strictly limited to a maximum budget of $1.
   The target cost for generating all X path is $0.8

System Navigation:

https://qa.cyclonercm.com/authentication/login?activationkey=60000006
Id : Thanuga
Password : 123

Naviagte Dahsboard Page ->  https://qa.cyclonercm.com/invoice
Click the File Upload Modal : " Get Started "" in Dashboard -> Click and Open the File Upload Modal.

Get Started Button -> /html/body/ng-component/div/div/div[1]/div/div[2]/button[1]

File Upload Modal -> /html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div


2. **Flexibility**:  Provide multiple strategies:
    - Primary XPath (most reliable)
    - Alternative XPath (fallback)
    - CSS Selector (where beneficial)

3. **Reusability**: Include parameterized XPaths for dynamic content

4. **Best Practices**:
    - Use relative paths (`//`) not absolute
    - Use `contains()` for partial matches
    - Use `normalize-space()` for text with whitespace
    - Avoid position-based selectors like `[1]`, `[2]` unless necessary
    - Provide ancestor/sibling relationships for checkboxes

### Coverage Requirements
Extract locators for:
- Modal structure (container, backdrop, title)
- All buttons (upload, refresh, close)
- Each folder (checkbox, label, icon, expand arrow)
- Dynamic selectors (any folder by name)
- State verification (enabled/disabled, selected/unselected)

## OUTPUT FORMAT

Return ONLY valid JSON in this exact structure (no markdown, no code blocks):
please store -> [GenerateXPaths.md](../../../../../../../../common-services/authentication-service/src/main/java/com/cyclonercm/auth/utils/GenerateXPaths.md)

Example :-

{
"metadata": {
"module": "File Upload Modal",
"description": "Reason For Your Upload",
"extractedDate": "2026-01-08",
"totalLocators": 0,
"model": "claude-3-5-sonnet-20241022"
},
"locators": {
"modal": {
"container": {
"label": "fileUploadModalContainer",
"xpath": "//div[@role='dialog']",
"css": "div[role='dialog']",
"alternative": "//div[contains(@class, 'modal') and contains(., 'Reason For Your Upload')]",
"description": "Main modal dialog container",
"usage": "To verify modal is displayed and get modal element"
},
"backdrop": {
"label": "modalBackdrop",
"xpath": "//div[contains(@class, 'modal-backdrop') or contains(@class, 'overlay')]",
"css": ". modal-backdrop, .overlay",
"description":  "Modal background overlay/backdrop"
},
"title": {
"label":  "modalTitle",
"xpath": "//*[normalize-space(text())='Reason For Your Upload']",
"alternative": "//h1[contains(text(), 'Reason For Your Upload')] | //h2[contains(text(), 'Reason For Your Upload')] | //*[@class='modal-title' or contains(@class, 'modal-header')]/*[contains(text(), 'Reason')]",
"description": "Modal header title text"
}
},
"buttons":  {
"upload": {
"label": "uploadButton",
"xpath": "//button[normalize-space(text())='Upload']",
"css": "button. upload, button[type='submit']",
"alternative": "//button[contains(text(), 'Upload') or contains(@class, 'upload') or @type='submit']",
"byAriaLabel": "//button[@aria-label='Upload' or @aria-label='Upload file']",
"description": "Green upload submit button",
"usage": "Primary action to upload file to selected folders"
},
"refresh": {
"label": "refreshButton",
"xpath":  "//button[@aria-label='Refresh' or contains(@title, 'Refresh')]",
"css": "button[aria-label='Refresh']",
"alternative": "//button[contains(@class, 'refresh') or .//*[name()='svg' and contains(@class, 'refresh')]]",
"description": "Refresh button with circular icon",
"usage": "Refresh the folder tree structure"
},
"close": {
"label": "closeButton",
"xpath":  "//button[@aria-label='Close' or normalize-space(text())='×']",
"css": "button. close, button[aria-label='Close']",
"alternative":  "//button[contains(@class, 'close') or contains(@class, 'btn-close') or text()='×' or text()='✕']",
"description":  "Close (X) button in modal header",
"usage": "Close modal without uploading"
}
},
"folderTree": {
"container": {
"label": "folderTreeContainer",
"xpath": "//div[@role='tree' or contains(@class, 'tree') or contains(@class, 'folder-list')]",
"alternative": "//ul[contains(@class, 'tree')] | //div[.//input[@type='checkbox'] and . //label]",
"description": "Main folder tree container"
},
"billing": {
"checkbox": {
"label": "billingCheckbox",
"xpath": "//label[normalize-space(text())='Billing']/preceding-sibling::input[@type='checkbox']",
"alternative": "//input[@type='checkbox' and (following-sibling::label[text()='Billing'] or ..//*[text()='Billing'])]",
"byParent": "//label[text()='Billing']/..//input[@type='checkbox']",
"byId": "//input[@id='billing' or @id='folder-billing']",
"description": "Checkbox for Billing folder selection"
},
"label": {
"label": "billingLabel",
"xpath": "//label[normalize-space(text())='Billing']",
"alternative": "//*[self::label or self::span][text()='Billing' and ancestor::*[contains(@class, 'folder')]]",
"description": "Billing folder text label"
},
"icon": {
"label":  "billingIcon",
"xpath": "//label[text()='Billing']/preceding-sibling::*[contains(@class, 'icon') or name()='svg']",
"alternative": "//label[text()='Billing']/..//*[contains(@class, 'folder-icon')]",
"description": "Billing folder icon"
},
"expandArrow": {
"label": "billingExpandArrow",
"xpath": "//label[text()='Billing']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow')]",
"alternative": "//label[text()='Billing']/../..//*[contains(@class, 'arrow') or contains(@class, 'expand') or contains(@class, 'chevron')]",
"description":  "Expand/collapse arrow for Billing folder"
}
},
"collections": {
"checkbox":  {
"label": "collectionsCheckbox",
"xpath": "//label[normalize-space(text())='Collections']/preceding-sibling::input[@type='checkbox']",
"alternative":  "//input[@type='checkbox' and (following-sibling::label[text()='Collections'] or ..//*[text()='Collections'])]",
"byParent":  "//label[text()='Collections']/..//input[@type='checkbox']",
"description": "Checkbox for Collections folder"
},
"label":  {
"label": "collectionsLabel",
"xpath":  "//label[normalize-space(text())='Collections']",
"description": "Collections folder label"
},
"icon": {
"label": "collectionsIcon",
"xpath": "//label[text()='Collections']/preceding-sibling:: *[contains(@class, 'icon') or name()='svg']",
"description": "Collections folder icon"
},
"expandArrow":  {
"label": "collectionsExpandArrow",
"xpath": "//label[text()='Collections']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow')]",
"description": "Expand/collapse arrow for Collections"
}
},
"eorResponse": {
"checkbox":  {
"label": "eorResponseCheckbox",
"xpath": "//label[normalize-space(text())='EOR Response']/preceding-sibling::input[@type='checkbox']",
"alternative": "//input[@type='checkbox' and (following-sibling::label[text()='EOR Response'] or ..//*[text()='EOR Response'])]",
"byParent": "//label[text()='EOR Response']/..//input[@type='checkbox']",
"description": "Checkbox for EOR Response folder"
},
"label":  {
"label": "eorResponseLabel",
"xpath": "//label[normalize-space(text())='EOR Response']",
"alternative": "//*[contains(text(), 'EOR Response')]",
"description": "EOR Response folder label"
},
"icon": {
"label": "eorResponseIcon",
"xpath": "//label[text()='EOR Response']/preceding-sibling::*[contains(@class, 'icon') or name()='svg']",
"description": "EOR Response folder icon"
},
"expandArrow": {
"label": "eorResponseExpandArrow",
"xpath": "//label[text()='EOR Response']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow')]",
"description": "Expand/collapse arrow for EOR Response"
}
},
"petition": {
"checkbox": {
"label": "petitionCheckbox",
"xpath": "//label[normalize-space(text())='Petition']/preceding-sibling:: input[@type='checkbox']",
"alternative": "//input[@type='checkbox' and (following-sibling::label[text()='Petition'] or ..//*[text()='Petition'])]",
"byParent": "//label[text()='Petition']/..//input[@type='checkbox']",
"description": "Checkbox for Petition folder"
},
"label": {
"label": "petitionLabel",
"xpath": "//label[normalize-space(text())='Petition']",
"description": "Petition folder label"
},
"icon":  {
"label": "petitionIcon",
"xpath":  "//label[text()='Petition']/preceding-sibling:: *[contains(@class, 'icon') or name()='svg']",
"description": "Petition folder icon"
},
"expandArrow": {
"label": "petitionExpandArrow",
"xpath":  "//label[text()='Petition']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow')]",
"description": "Expand/collapse arrow for Petition"
}
},
"dynamic": {
"anyFolderCheckbox": {
"label":  "anyFolderCheckboxByName",
"xpath": "//label[normalize-space(text())='{FOLDER_NAME}']/preceding-sibling::input[@type='checkbox']",
"alternative": "//input[@type='checkbox' and ..//*[text()='{FOLDER_NAME}']]",
"description": "Parameterized XPath to find any folder checkbox by name.  Replace {FOLDER_NAME} with actual folder name.",
"usage": "xpath. format(FOLDER_NAME='Billing')"
},
"anyFolderLabel": {
"label": "anyFolderLabelByName",
"xpath": "//label[normalize-space(text())='{FOLDER_NAME}']",
"description": "Find any folder label by text content"
},
"anyExpandArrow": {
"label": "anyFolderExpandArrow",
"xpath": "//label[text()='{FOLDER_NAME}']/ancestor::div[1]//button[contains(@class, 'arrow')]",
"description": "Find expand arrow for any folder"
}
},
"collections": {
"allCheckboxes": {
"label":  "allFolderCheckboxes",
"xpath": "//input[@type='checkbox']",
"description": "All folder checkboxes in the tree"
},
"allLabels": {
"label":  "allFolderLabels",
"xpath": "//label[ancestor::*[contains(@class, 'tree') or @role='tree']]",
"description":  "All folder labels"
}
}
},
"stateVerification": {
"uploadButtonEnabled": {
"label": "uploadButtonEnabled",
"xpath": "//button[contains(text(), 'Upload') and not(@disabled)]",
"description":  "Upload button in enabled state"
},
"uploadButtonDisabled": {
"label": "uploadButtonDisabled",
"xpath": "//button[contains(text(), 'Upload') and @disabled]",
"alternative": "//button[contains(text(), 'Upload') and (contains(@class, 'disabled') or @aria-disabled='true')]",
"description": "Upload button in disabled state"
},
"selectedCheckboxes": {
"label": "allSelectedCheckboxes",
"xpath": "//input[@type='checkbox' and (@checked='true' or @checked='checked')]",
"alternative": "//input[@type='checkbox'][@checked]",
"description": "All currently selected folder checkboxes"
},
"unselectedCheckboxes": {
"label": "allUnselectedCheckboxes",
"xpath": "//input[@type='checkbox' and not(@checked)]",
"description": "All unselected checkboxes"
},
"modalVisible": {
"label": "modalVisibleState",
"xpath": "//div[@role='dialog' and not(contains(@style, 'display: none')) and not(contains(@class, 'hidden'))]",
"description":  "Modal in visible state"
},
"modalHidden": {
"label": "modalHiddenState",
"xpath": "//div[@role='dialog' and (contains(@style, 'display:  none') or contains(@class, 'hidden'))]",
"description": "Modal in hidden state"
},
"selectedFolderCount": {
"label":  "countSelectedFolders",
"xpath": "count(//input[@type='checkbox' and @checked='true'])",
"description": "XPath expression to count selected folders (use in assertions)"
}
}
}
}


## CRITICAL INSTRUCTIONS
1. Return ONLY the JSON object - no explanations, no markdown formatting, no code blocks
2. Ensure all JSON is valid (proper escaping, no trailing commas)
3. Fill in ALL XPath expressions based on common modal patterns and the screenshot
4. Every locator must have:  label, xpath, description
5. Include alternative, css, byAriaLabel where applicable
6. Make XPaths as robust as possible - they should work even if classes change
7. Update totalLocators count with actual number of unique locators

Generate the complete JSON now. 