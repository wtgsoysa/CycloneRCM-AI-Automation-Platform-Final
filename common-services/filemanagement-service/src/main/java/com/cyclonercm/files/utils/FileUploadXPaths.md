{
"metadata": {
"module": "File Upload Modal",
"description": "Reason For Your Upload - Complete XPath Locator Catalog",
"extractedDate": "2026-01-08",
"totalLocators": 67,
"model": "claude-sonnet-4-5-20250929",
"testCoverage": "55 test cases (Functional Positive, Negative, Alternative, UI)",
"navigationPath": "Dashboard > Get Started Button > File Upload Modal",
"systemUrl": "https://qa.cyclonercm.com/invoice"
},
"navigation": {
"getStartedButton": {
"label": "getStartedButton",
"xpath": "/html/body/ng-component/div/div/div[1]/div/div[2]/button[1]",
"alternative": "//button[contains(text(), 'Get Started')]",
"css": "button:contains('Get Started')",
"description": "Dashboard button that opens the File Upload Modal",
"usage": "Click to launch the 'Reason For Your Upload' modal"
}
},
"locators": {
"modal": {
"container": {
"label": "fileUploadModalContainer",
"xpath": "//div[@role='dialog' and contains(., 'Reason For Your Upload')]",
"css": "div[role='dialog']",
"alternative": "//p-dialog//div[contains(@class, 'dialog') or contains(@class, 'modal')]",
"byComponent": "/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload",
"description": "Main modal dialog container for File Upload",
"usage": "To verify modal is displayed and get modal root element"
},
"backdrop": {
"label": "modalBackdrop",
"xpath": "//div[contains(@class, 'modal-backdrop') or contains(@class, 'overlay') or contains(@class, 'p-dialog-mask')]",
"css": ".modal-backdrop, .overlay, .p-dialog-mask",
"description": "Modal background overlay/backdrop (PrimeNG style)",
"usage": "Verify modal overlay is visible or click to close"
},
"title": {
"label": "modalTitle",
"xpath": "//*[normalize-space(text())='Reason For Your Upload']",
"alternative": "//h1[contains(text(), 'Reason For Your Upload')] | //h2[contains(text(), 'Reason For Your Upload')] | //div[contains(@class, 'modal-header') or contains(@class, 'p-dialog-title')]//*[contains(text(), 'Reason')]",
"css": ".modal-title, .p-dialog-title",
"description": "Modal header title text 'Reason For Your Upload'",
"usage": "Verify modal title is displayed correctly"
},
"header": {
"label": "modalHeader",
"xpath": "//div[contains(@class, 'modal-header') or contains(@class, 'p-dialog-header')]",
"css": ".modal-header, .p-dialog-header",
"description": "Modal header section container",
"usage": "Locate header region for close button and title"
},
"body": {
"label": "modalBody",
"xpath": "//div[contains(@class, 'modal-body') or contains(@class, 'p-dialog-content')]",
"alternative": "//form//div[.//input[@type='checkbox']]",
"description": "Modal body content area with folder tree",
"usage": "Main content area containing folder selection tree"
},
"footer": {
"label": "modalFooter",
"xpath": "//div[contains(@class, 'modal-footer') or contains(@class, 'p-dialog-footer')]",
"alternative": "//div[.//button[contains(text(), 'Upload')]]",
"description": "Modal footer with action buttons",
"usage": "Footer region containing Upload and other action buttons"
},
"formContainer": {
"label": "fileUploadForm",
"xpath": "//form[.//input[@type='checkbox'] and .//button[contains(text(), 'Upload')]]",
"alternative": "/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form",
"description": "Main form container for file upload modal",
"usage": "Root form element containing all interactive elements"
}
},
"buttons": {
"upload": {
"label": "uploadButton",
"xpath": "//button[normalize-space(text())='Upload']",
"css": "button.upload, button[type='submit']",
"alternative": "//button[contains(text(), 'Upload') or contains(@class, 'upload') or @type='submit']",
"byAriaLabel": "//button[@aria-label='Upload' or @aria-label='Upload file' or @aria-label='Upload files']",
"description": "Primary upload submit button (typically green/primary color)",
"usage": "Primary action to upload file to selected folders"
},
"refresh": {
"label": "refreshButton",
"xpath": "//button[@aria-label='Refresh' or contains(@title, 'Refresh')]",
"css": "button[aria-label='Refresh']",
"alternative": "//button[contains(@class, 'refresh') or .//*[name()='svg' and contains(@class, 'refresh')]]",
"byIcon": "//button[.//*[contains(@class, 'fa-refresh') or contains(@class, 'fa-sync') or contains(@class, 'pi-refresh')]]",
"description": "Refresh button with circular arrow icon",
"usage": "Refresh the folder tree structure to reload folders"
},
"close": {
"label": "closeButton",
"xpath": "//button[@aria-label='Close' or normalize-space(text())='×']",
"css": "button.close, button[aria-label='Close']",
"alternative": "//button[contains(@class, 'close') or contains(@class, 'btn-close') or contains(@class, 'p-dialog-header-close') or text()='×' or text()='✕']",
"byIcon": "//button[.//*[contains(@class, 'fa-times') or contains(@class, 'fa-close') or contains(@class, 'pi-times')]]",
"description": "Close (X) button in modal header",
"usage": "Close modal without uploading, cancel operation"
},
"cancel": {
"label": "cancelButton",
"xpath": "//button[normalize-space(text())='Cancel']",
"alternative": "//button[contains(text(), 'Cancel') or contains(@class, 'cancel')]",
"description": "Cancel button (if present in footer)",
"usage": "Cancel operation and close modal"
}
},
"folderTree": {
"container": {
"label": "folderTreeContainer",
"xpath": "//div[@role='tree' or contains(@class, 'tree') or contains(@class, 'folder-list')]",
"alternative": "//ul[contains(@class, 'tree')] | //div[.//input[@type='checkbox'] and .//label]",
"css": "div[role='tree'], .tree, .folder-list",
"description": "Main folder tree container with all selectable folders",
"usage": "Root container for folder hierarchy"
},
"rootNode": {
"label": "folderTreeRoot",
"xpath": "//div[@role='tree']/*[1] | //ul[contains(@class, 'tree-root')]",
"description": "Root node of the folder tree",
"usage": "Top-level folder tree node"
},
"billing": {
"checkbox": {
"label": "billingCheckbox",
"xpath": "//label[normalize-space(text())='Billing']/preceding-sibling::input[@type='checkbox']",
"alternative": "//input[@type='checkbox' and (following-sibling::label[text()='Billing'] or ..//*[text()='Billing'])]",
"byParent": "//label[text()='Billing']/..//input[@type='checkbox']",
"byId": "//input[@id='billing' or @id='folder-billing' or @id='chk-billing']",
"byName": "//input[@name='billing' or @name='folder-billing']",
"description": "Checkbox for Billing folder selection",
"usage": "Select/deselect Billing folder for file upload"
},
"label": {
"label": "billingLabel",
"xpath": "//label[normalize-space(text())='Billing']",
"alternative": "//*[self::label or self::span][text()='Billing' and ancestor::*[contains(@class, 'folder') or contains(@class, 'tree')]]",
"css": "label:contains('Billing')",
"description": "Billing folder text label",
"usage": "Verify Billing folder label is displayed"
},
"icon": {
"label": "billingIcon",
"xpath": "//label[text()='Billing']/preceding-sibling::*[contains(@class, 'icon') or name()='svg' or name()='i']",
"alternative": "//label[text()='Billing']/..//*[contains(@class, 'folder-icon') or contains(@class, 'fa-folder')]",
"description": "Billing folder icon (typically folder icon)",
"usage": "Verify folder icon is displayed"
},
"expandArrow": {
"label": "billingExpandArrow",
"xpath": "//label[text()='Billing']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow') or contains(@class, 'toggle')]",
"alternative": "//label[text()='Billing']/../..//*[contains(@class, 'arrow') or contains(@class, 'expand') or contains(@class, 'chevron') or contains(@class, 'caret')]",
"byIcon": "//label[text()='Billing']/..//*[contains(@class, 'fa-chevron') or contains(@class, 'fa-caret') or contains(@class, 'pi-chevron')]",
"description": "Expand/collapse arrow for Billing folder (if has subfolders)",
"usage": "Click to expand/collapse Billing subfolder tree"
},
"container": {
"label": "billingFolderContainer",
"xpath": "//label[text()='Billing']/ancestor::*[contains(@class, 'folder-item') or contains(@class, 'tree-node')][1]",
"description": "Container div for entire Billing folder row",
"usage": "Get full folder item element"
}
},
"collections": {
"checkbox": {
"label": "collectionsCheckbox",
"xpath": "//label[normalize-space(text())='Collections']/preceding-sibling::input[@type='checkbox']",
"alternative": "//input[@type='checkbox' and (following-sibling::label[text()='Collections'] or ..//*[text()='Collections'])]",
"byParent": "//label[text()='Collections']/..//input[@type='checkbox']",
"byId": "//input[@id='collections' or @id='folder-collections' or @id='chk-collections']",
"description": "Checkbox for Collections folder",
"usage": "Select/deselect Collections folder"
},
"label": {
"label": "collectionsLabel",
"xpath": "//label[normalize-space(text())='Collections']",
"alternative": "//*[self::label or self::span][text()='Collections']",
"description": "Collections folder label text",
"usage": "Verify Collections folder is visible"
},
"icon": {
"label": "collectionsIcon",
"xpath": "//label[text()='Collections']/preceding-sibling::*[contains(@class, 'icon') or name()='svg' or name()='i']",
"description": "Collections folder icon",
"usage": "Verify folder icon presence"
},
"expandArrow": {
"label": "collectionsExpandArrow",
"xpath": "//label[text()='Collections']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow')]",
"alternative": "//label[text()='Collections']/..//*[contains(@class, 'chevron') or contains(@class, 'caret')]",
"description": "Expand/collapse arrow for Collections",
"usage": "Expand/collapse Collections subfolder tree"
},
"container": {
"label": "collectionsFolderContainer",
"xpath": "//label[text()='Collections']/ancestor::*[contains(@class, 'folder-item') or contains(@class, 'tree-node')][1]",
"description": "Collections folder row container",
"usage": "Get full Collections folder element"
}
},
"eorResponse": {
"checkbox": {
"label": "eorResponseCheckbox",
"xpath": "//label[normalize-space(text())='EOR Response']/preceding-sibling::input[@type='checkbox']",
"alternative": "//input[@type='checkbox' and (following-sibling::label[text()='EOR Response'] or ..//*[text()='EOR Response'])]",
"byParent": "//label[text()='EOR Response']/..//input[@type='checkbox']",
"byId": "//input[@id='eor-response' or @id='folder-eor-response' or @id='chk-eor']",
"description": "Checkbox for EOR Response folder",
"usage": "Select/deselect EOR Response folder"
},
"label": {
"label": "eorResponseLabel",
"xpath": "//label[normalize-space(text())='EOR Response']",
"alternative": "//*[contains(text(), 'EOR Response') and (self::label or self::span)]",
"css": "label:contains('EOR Response')",
"description": "EOR Response folder label",
"usage": "Verify EOR Response folder is displayed"
},
"icon": {
"label": "eorResponseIcon",
"xpath": "//label[text()='EOR Response']/preceding-sibling::*[contains(@class, 'icon') or name()='svg' or name()='i']",
"description": "EOR Response folder icon",
"usage": "Verify folder icon"
},
"expandArrow": {
"label": "eorResponseExpandArrow",
"xpath": "//label[text()='EOR Response']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow')]",
"alternative": "//label[text()='EOR Response']/..//*[contains(@class, 'chevron')]",
"description": "Expand/collapse arrow for EOR Response",
"usage": "Expand/collapse EOR Response tree"
},
"container": {
"label": "eorResponseFolderContainer",
"xpath": "//label[text()='EOR Response']/ancestor::*[contains(@class, 'folder-item') or contains(@class, 'tree-node')][1]",
"description": "EOR Response folder container element",
"usage": "Get full EOR Response folder element"
}
},
"petition": {
"checkbox": {
"label": "petitionCheckbox",
"xpath": "//label[normalize-space(text())='Petition']/preceding-sibling::input[@type='checkbox']",
"alternative": "//input[@type='checkbox' and (following-sibling::label[text()='Petition'] or ..//*[text()='Petition'])]",
"byParent": "//label[text()='Petition']/..//input[@type='checkbox']",
"byId": "//input[@id='petition' or @id='folder-petition' or @id='chk-petition']",
"description": "Checkbox for Petition folder",
"usage": "Select/deselect Petition folder"
},
"label": {
"label": "petitionLabel",
"xpath": "//label[normalize-space(text())='Petition']",
"alternative": "//*[text()='Petition' and (self::label or self::span)]",
"description": "Petition folder label text",
"usage": "Verify Petition folder presence"
},
"icon": {
"label": "petitionIcon",
"xpath": "//label[text()='Petition']/preceding-sibling::*[contains(@class, 'icon') or name()='svg' or name()='i']",
"description": "Petition folder icon",
"usage": "Verify folder icon display"
},
"expandArrow": {
"label": "petitionExpandArrow",
"xpath": "//label[text()='Petition']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow')]",
"alternative": "//label[text()='Petition']/..//*[contains(@class, 'chevron') or contains(@class, 'caret')]",
"description": "Expand/collapse arrow for Petition",
"usage": "Expand/collapse Petition folder tree"
},
"container": {
"label": "petitionFolderContainer",
"xpath": "//label[text()='Petition']/ancestor::*[contains(@class, 'folder-item') or contains(@class, 'tree-node')][1]",
"description": "Petition folder row container",
"usage": "Get complete Petition folder element"
}
},
"dynamic": {
"anyFolderCheckbox": {
"label": "anyFolderCheckboxByName",
"xpath": "//label[normalize-space(text())='{FOLDER_NAME}']/preceding-sibling::input[@type='checkbox']",
"alternative": "//input[@type='checkbox' and ..//*[normalize-space(text())='{FOLDER_NAME}']]",
"byParent": "//label[text()='{FOLDER_NAME}']/..//input[@type='checkbox']",
"description": "Parameterized XPath to find any folder checkbox by name. Replace {FOLDER_NAME} with actual folder name.",
"usage": "driver.find_element(By.XPATH, xpath.format(FOLDER_NAME='Billing'))"
},
"anyFolderLabel": {
"label": "anyFolderLabelByName",
"xpath": "//label[normalize-space(text())='{FOLDER_NAME}']",
"alternative": "//*[(self::label or self::span) and normalize-space(text())='{FOLDER_NAME}']",
"description": "Find any folder label by text content (parameterized)",
"usage": "Replace {FOLDER_NAME} with target folder name"
},
"anyExpandArrow": {
"label": "anyFolderExpandArrow",
"xpath": "//label[text()='{FOLDER_NAME}']/ancestor::div[1]//button[contains(@class, 'arrow') or contains(@class, 'toggle')]",
"alternative": "//label[text()='{FOLDER_NAME}']/..//*[contains(@class, 'chevron') or contains(@class, 'caret')]",
"description": "Find expand arrow for any folder (parameterized)",
"usage": "Dynamic folder expansion based on folder name"
},
"anyFolderIcon": {
"label": "anyFolderIcon",
"xpath": "//label[text()='{FOLDER_NAME}']/preceding-sibling::*[contains(@class, 'icon') or name()='svg']",
"description": "Find folder icon for any folder by name",
"usage": "Get icon element for verification"
},
"anyFolderContainer": {
"label": "anyFolderContainer",
"xpath": "//label[text()='{FOLDER_NAME}']/ancestor::*[contains(@class, 'folder-item') or contains(@class, 'tree-node')][1]",
"description": "Get container element for any folder by name",
"usage": "Access complete folder row element"
}
},
"collections": {
"allCheckboxes": {
"label": "allFolderCheckboxes",
"xpath": "//input[@type='checkbox' and ancestor::*[contains(@class, 'tree') or @role='tree']]",
"alternative": "//input[@type='checkbox']",
"description": "All folder checkboxes in the tree",
"usage": "Get list of all checkbox elements for iteration"
},
"allLabels": {
"label": "allFolderLabels",
"xpath": "//label[ancestor::*[contains(@class, 'tree') or @role='tree']]",
"alternative": "//label[following-sibling::input[@type='checkbox'] or preceding-sibling::input[@type='checkbox']]",
"description": "All folder labels in the tree",
"usage": "Get all folder names for validation"
},
"allIcons": {
"label": "allFolderIcons",
"xpath": "//*[(contains(@class, 'icon') or name()='svg') and ancestor::*[contains(@class, 'tree')]]",
"description": "All folder icons in the tree",
"usage": "Verify all folders have icons"
},
"allExpandArrows": {
"label": "allExpandArrows",
"xpath": "//button[contains(@class, 'arrow') or contains(@aria-label, 'expand')]",
"alternative": "//*[contains(@class, 'chevron') or contains(@class, 'caret') or contains(@class, 'toggle')]",
"description": "All expand/collapse arrows in tree",
"usage": "Find all expandable folder indicators"
}
}
},
"stateVerification": {
"uploadButtonEnabled": {
"label": "uploadButtonEnabled",
"xpath": "//button[contains(text(), 'Upload') and not(@disabled)]",
"alternative": "//button[contains(text(), 'Upload') and not(@disabled='true') and not(contains(@class, 'disabled'))]",
"description": "Upload button in enabled state (at least one folder selected)",
"usage": "Assert button is clickable when folders are selected"
},
"uploadButtonDisabled": {
"label": "uploadButtonDisabled",
"xpath": "//button[contains(text(), 'Upload') and @disabled]",
"alternative": "//button[contains(text(), 'Upload') and (@disabled='true' or contains(@class, 'disabled') or @aria-disabled='true')]",
"description": "Upload button in disabled state (no folders selected)",
"usage": "Assert button is disabled when no selection made"
},
"selectedCheckboxes": {
"label": "allSelectedCheckboxes",
"xpath": "//input[@type='checkbox' and (@checked='true' or @checked='checked')]",
"alternative": "//input[@type='checkbox'][@checked]",
"byProperty": "//input[@type='checkbox' and ancestor::*[contains(@class, 'checked')]]",
"description": "All currently selected/checked folder checkboxes",
"usage": "Get list of selected folders for validation"
},
"unselectedCheckboxes": {
"label": "allUnselectedCheckboxes",
"xpath": "//input[@type='checkbox' and not(@checked)]",
"alternative": "//input[@type='checkbox' and (not(@checked) or @checked='false')]",
"description": "All unselected/unchecked checkboxes",
"usage": "Verify unchecked state"
},
"modalVisible": {
"label": "modalVisibleState",
"xpath": "//div[@role='dialog' and not(contains(@style, 'display: none')) and not(contains(@class, 'hidden'))]",
"alternative": "//div[@role='dialog' and (contains(@style, 'display: block') or contains(@class, 'show') or contains(@class, 'visible'))]",
"description": "Modal in visible/displayed state",
"usage": "Assert modal is visible on screen"
},
"modalHidden": {
"label": "modalHiddenState",
"xpath": "//div[@role='dialog' and (contains(@style, 'display: none') or contains(@class, 'hidden') or contains(@class, 'ng-hide'))]",
"alternative": "//div[@role='dialog' and not(contains(@class, 'show'))]",
"description": "Modal in hidden/closed state",
"usage": "Assert modal is not displayed"
},
"selectedFolderCount": {
"label": "countSelectedFolders",
"xpath": "count(//input[@type='checkbox' and @checked='true'])",
"alternative": "count(//input[@type='checkbox'][@checked])",
"description": "XPath expression to count number of selected folders (use in assertions)",
"usage": "int count = (int) ((JavascriptExecutor)driver).executeScript('return document.evaluate(xpath, document, null, XPathResult.NUMBER_TYPE, null).numberValue;')"
},
"totalFolderCount": {
"label": "countTotalFolders",
"xpath": "count(//input[@type='checkbox'])",
"description": "Total number of folder checkboxes in tree",
"usage": "Get total folder count for validation"
},
"folderTreeExpanded": {
"label": "isFolderTreeExpanded",
"xpath": "//button[contains(@aria-label, 'expand') and contains(@aria-expanded, 'true')]",
"alternative": "//*[contains(@class, 'expanded') or contains(@class, 'open')]",
"description": "Check if folder tree has expanded nodes",
"usage": "Verify tree expansion state"
},
"folderTreeCollapsed": {
"label": "isFolderTreeCollapsed",
"xpath": "//button[contains(@aria-label, 'expand') and contains(@aria-expanded, 'false')]",
"alternative": "//*[contains(@class, 'collapsed') or contains(@class, 'closed')]",
"description": "Check if folder tree nodes are collapsed",
"usage": "Verify collapsed state"
},
"checkboxCheckedByFolder": {
"label": "isSpecificFolderChecked",
"xpath": "//label[text()='{FOLDER_NAME}']/preceding-sibling::input[@type='checkbox' and @checked]",
"description": "Check if specific folder is checked (parameterized)",
"usage": "Replace {FOLDER_NAME} to verify specific folder selection"
}
},
"validation": {
"folderLabels": {
"label": "expectedFolderLabels",
"expectedValues": ["Billing", "Collections", "EOR Response", "Petition"],
"xpath": "//label[ancestor::*[contains(@class, 'tree')]]/text()",
"description": "Expected folder label values for validation",
"usage": "Validate all expected folders are present"
},
"requiredElements": {
"label": "criticalElements",
"elements": [
"//div[@role='dialog']",
"//button[contains(text(), 'Upload')]",
"//button[@aria-label='Close']",
"//input[@type='checkbox']"
],
"description": "Critical elements that must be present",
"usage": "Validate modal structure completeness"
}
},
"fileSelection": {
"fileInput": {
"label": "fileInputField",
"xpath": "//input[@type='file']",
"alternative": "//input[@type='file' and ancestor::*[@role='dialog']]",
"byName": "//input[@name='file' or @name='upload' or @name='fileupload']",
"description": "File input field for selecting files (may be hidden)",
"usage": "Send file path to upload files"
},
"browseButton": {
"label": "browseFilesButton",
"xpath": "//button[contains(text(), 'Browse') or contains(text(), 'Choose')]",
"alternative": "//button[contains(@class, 'file-select') or contains(@class, 'browse')]",
"description": "Browse/Choose files button",
"usage": "Click to open file browser"
},
"dropZone": {
"label": "fileDropZone",
"xpath": "//div[contains(@class, 'drop-zone') or contains(@class, 'file-drop') or contains(text(), 'Drop')]",
"description": "Drag and drop zone for files",
"usage": "Drag files to this area for upload"
},
"selectedFileName": {
"label": "displayedFileName",
"xpath": "//*[contains(@class, 'file-name') or contains(@class, 'selected-file')]",
"alternative": "//span[contains(text(), '.pdf') or contains(text(), '.doc') or contains(text(), '.jpg')]",
"description": "Display of selected file name",
"usage": "Verify correct file is selected"
}
},
"loading": {
"spinner": {
"label": "loadingSpinner",
"xpath": "//*[contains(@class, 'spinner') or contains(@class, 'loading')]",
"alternative": "//*[contains(@class, 'fa-spinner') or contains(@class, 'fa-circle-o-notch')]",
"description": "Loading spinner during upload/refresh",
"usage": "Wait for spinner to disappear"
},
"progressBar": {
"label": "uploadProgressBar",
"xpath": "//div[contains(@class, 'progress-bar') or @role='progressbar']",
"description": "Upload progress bar",
"usage": "Monitor upload progress"
}
},
"messages": {
"successMessage": {
"label": "uploadSuccessMessage",
"xpath": "//*[contains(@class, 'success') and (contains(text(), 'success') or contains(text(), 'uploaded'))]",
"alternative": "//div[contains(@class, 'alert-success') or contains(@class, 'toast-success')]",
"description": "Success message after upload",
"usage": "Verify upload success confirmation"
},
"errorMessage": {
"label": "uploadErrorMessage",
"xpath": "//*[contains(@class, 'error') or contains(@class, 'alert-danger')]",
"alternative": "//div[contains(text(), 'error') or contains(text(), 'failed')]",
"description": "Error message on upload failure",
"usage": "Verify error handling and messages"
},
"validationMessage": {
"label": "validationErrorMessage",
"xpath": "//*[contains(@class, 'validation-error') or contains(@class, 'invalid-feedback')]",
"description": "Validation error messages",
"usage": "Verify field validation messages"
}
}
},
"testScenarios": {
"positive": [
"Select single folder (Billing) and verify Upload enabled",
"Select multiple folders and verify all are checked",
"Upload file to selected folders successfully",
"Verify modal displays correct title",
"Verify all folder options are visible"
],
"negative": [
"Verify Upload button is disabled with no folder selection",
"Attempt upload without file selection",
"Verify modal cannot be closed during active upload",
"Test with invalid file types",
"Test with file size exceeding limits"
],
"alternative": [
"Close modal using X button",
"Close modal using backdrop click",
"Refresh folder tree and verify structure",
"Expand/collapse folder tree nodes",
"Select all folders then deselect"
],
"ui": [
"Verify modal is centered on screen",
"Verify backdrop overlay is present",
"Verify all buttons are visible and enabled/disabled appropriately",
"Verify checkbox states display correctly",
"Verify modal responsive behavior"
]
},
"usageExamples": {
"java": {
"clickUploadButton": "driver.findElement(By.xpath(\"//button[normalize-space(text())='Upload']\")).click();",
"selectBillingFolder": "driver.findElement(By.xpath(\"//label[normalize-space(text())='Billing']/preceding-sibling::input[@type='checkbox']\")).click();",
"verifyModalVisible": "Assert.assertTrue(driver.findElement(By.xpath(\"//div[@role='dialog' and contains(., 'Reason For Your Upload')]\")).isDisplayed());",
"getSelectedFoldersCount": "List<WebElement> selected = driver.findElements(By.xpath(\"//input[@type='checkbox' and @checked='true']\"));\nint count = selected.size();",
"waitForModal": "WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));\nwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(\"//div[@role='dialog']\")));"
},
"python": {
"clickUploadButton": "driver.find_element(By.XPATH, \"//button[normalize-space(text())='Upload']\").click()",
"selectBillingFolder": "driver.find_element(By.XPATH, \"//label[normalize-space(text())='Billing']/preceding-sibling::input[@type='checkbox']\").click()",
"verifyModalVisible": "assert driver.find_element(By.XPATH, \"//div[@role='dialog']\").is_displayed()",
"dynamicFolderSelection": "folder_name = 'Collections'\nxpath = f\"//label[normalize-space(text())='{folder_name}']/preceding-sibling::input[@type='checkbox']\"\ndriver.find_element(By.XPATH, xpath).click()",
"waitForModal": "from selenium.webdriver.support.ui import WebDriverWait\nfrom selenium.webdriver.support import expected_conditions as EC\nWebDriverWait(driver, 10).until(EC.visibility_of_element_located((By.XPATH, \"//div[@role='dialog']\")))"
}
},
"bestPractices": {
"waitStrategies": [
"Always wait for modal to be visible before interacting: WebDriverWait(driver, 10).until(EC.visibility_of_element_located())",
"Wait for checkboxes to be clickable: EC.element_to_be_clickable()",
"Wait for Upload button to be enabled: EC.element_to_be_clickable() after folder selection",
"Use explicit waits instead of Thread.sleep()"
],
"locatorPriority": [
"1. Use @id or @name attributes when available (most stable)",
"2. Use @aria-label or @data-* attributes (semantic, stable)",
"3. Use text() content with normalize-space() for labels/buttons",
"4. Use contains(@class) with specific, meaningful class names",
"5. Avoid absolute XPaths (/html/body/...) except for documented reference points"
],
"errorHandling": [
"Always verify modal is visible before element interactions",
"Use try-catch blocks for StaleElementReferenceException",
"Verify element state (enabled/disabled) before click",
"Implement retry logic for dynamic content loading"
],
"maintainability": [
"Store all XPaths in a centralized constants file or Page Object",
"Use parameterized XPaths for dynamic folder selection",
"Create reusable methods for common actions (selectFolder, verifyModalOpen)",
"Document any absolute XPaths with clear comments"
]
},
"knownLimitations": [
"Absolute XPaths provided (Get Started button, Modal container) may break with DOM structure changes",
"Checkbox 'checked' attribute may be managed by JavaScript - use .is_selected() in Selenium instead of @checked",
"PrimeNG dialog components may use dynamic classes - prioritize @role and text-based locators",
"Folder tree may load asynchronously - always implement explicit waits"
],
"changelog": [
{
"date": "2026-01-08",
"version": "1.0.0",
"changes": "Initial comprehensive locator catalog generated with 67 unique locators",
"author": "Agentic Locator Discovery System"
}
]
}