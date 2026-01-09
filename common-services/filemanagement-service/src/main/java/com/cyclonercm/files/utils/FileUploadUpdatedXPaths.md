{
  "module": "File Upload Modal",
  "framework": "Selenium WebDriver + PrimeNG",
  "totalLocators": 28,
  "locators": {
    "modal": {
      "modalContainer": {
        "label": "File Upload Modal Container",
        "xpath": "//p-dialog//aeliusmd-file-upload",
        "description": "Main container for the file upload modal using PrimeNG p-dialog"
      },
      "modalDialog": {
        "label": "PrimeNG Dialog",
        "xpath": "//p-dialog[.//span[contains(text(), 'Reason For Your Upload')]]",
        "description": "PrimeNG dialog component containing the upload modal"
      },
      "modalBackdrop": {
        "label": "Modal Backdrop",
        "xpath": "//div[contains(@class, 'p-dialog-mask')]",
        "description": "Semi-transparent backdrop overlay behind the modal"
      },
      "modalTitle": {
        "label": "Modal Title",
        "xpath": "//p-dialog//div[contains(@class, 'p-dialog-header')]//span[contains(text(), 'Reason For Your Upload')]",
        "description": "Modal title text 'Reason For Your Upload'"
      },
      "modalFormContainer": {
        "label": "Modal Form Container",
        "xpath": "//aeliusmd-file-upload//form/div/div",
        "description": "Form container inside the file upload modal"
      }
    },
    "buttons": {
      "getStartedButton": {
        "label": "Get Started Button",
        "xpath": "//button[contains(., 'Get Started')]",
        "description": "Button to open the file upload modal from dashboard",
        "alternative": "//button[normalize-space()='Get Started']"
      },
      "uploadButton": {
        "label": "Upload Button",
        "xpath": "//p-dialog//button[contains(., 'Upload')]",
        "description": "Button to submit and upload selected files",
        "alternative": "//aeliusmd-file-upload//button[normalize-space()='Upload']"
      },
      "refreshButton": {
        "label": "Refresh Button",
        "xpath": "//p-dialog//button[contains(., 'Refresh') or contains(@class, 'refresh')]",
        "description": "Button to refresh the folder tree",
        "alternative": "//button[@title='Refresh' or contains(@aria-label, 'Refresh')]"
      },
      "closeButton": {
        "label": "Close Modal Button",
        "xpath": "//p-dialog//button[contains(@class, 'p-dialog-header-close')]",
        "description": "Close button (X) in the modal header",
        "alternative": "//p-dialog//button[@aria-label='Close']"
      },
      "cancelButton": {
        "label": "Cancel Button",
        "xpath": "//p-dialog//button[contains(., 'Cancel')]",
        "description": "Cancel button to close modal without uploading"
      }
    },
    "folderTree": {
      "treeContainer": {
        "label": "Folder Tree Container",
        "xpath": "//p-tree[contains(@class, 'p-tree')]",
        "description": "PrimeNG tree container holding all folder nodes"
      },
      "treeRoot": {
        "label": "Tree Root Node",
        "xpath": "//p-tree//ul[contains(@class, 'p-tree-container')]",
        "description": "Root ul element containing all tree nodes"
      },
      "billingCheckbox": {
        "label": "Billing Folder Checkbox",
        "xpath": "//p-tree//span[contains(text(), 'Billing')]/ancestor::p-treenode//div[contains(@class, 'p-checkbox-box')]",
        "description": "Clickable checkbox for Billing folder (PrimeNG p-checkbox-box)",
        "alternative": "//p-treenode[.//span[contains(text(), 'Billing')]]//div[contains(@class, 'p-checkbox-box')]"
      },
      "collectionsCheckbox": {
        "label": "Collections Folder Checkbox",
        "xpath": "//p-tree//span[contains(text(), 'Collections')]/ancestor::p-treenode//div[contains(@class, 'p-checkbox-box')]",
        "description": "Clickable checkbox for Collections folder (PrimeNG p-checkbox-box)",
        "alternative": "//p-treenode[.//span[contains(text(), 'Collections')]]//div[contains(@class, 'p-checkbox-box')]"
      },
      "eorResponseCheckbox": {
        "label": "EOR Response Folder Checkbox",
        "xpath": "//p-tree//span[contains(text(), 'EOR Response')]/ancestor::p-treenode//div[contains(@class, 'p-checkbox-box')]",
        "description": "Clickable checkbox for EOR Response folder (PrimeNG p-checkbox-box)",
        "alternative": "//p-treenode[.//span[contains(text(), 'EOR Response')]]//div[contains(@class, 'p-checkbox-box')]"
      },
      "petitionCheckbox": {
        "label": "Petition Folder Checkbox",
        "xpath": "//p-tree//span[contains(text(), 'Petition')]/ancestor::p-treenode//div[contains(@class, 'p-checkbox-box')]",
        "description": "Clickable checkbox for Petition folder (PrimeNG p-checkbox-box)",
        "alternative": "//p-treenode[.//span[contains(text(), 'Petition')]]//div[contains(@class, 'p-checkbox-box')]"
      },
      "billingFolderLabel": {
        "label": "Billing Folder Label",
        "xpath": "//p-tree//span[contains(text(), 'Billing')]",
        "description": "Text label for Billing folder"
      },
      "collectionsFolderLabel": {
        "label": "Collections Folder Label",
        "xpath": "//p-tree//span[contains(text(), 'Collections')]",
        "description": "Text label for Collections folder"
      },
      "eorResponseFolderLabel": {
        "label": "EOR Response Folder Label",
        "xpath": "//p-tree//span[contains(text(), 'EOR Response')]",
        "description": "Text label for EOR Response folder"
      },
      "petitionFolderLabel": {
        "label": "Petition Folder Label",
        "xpath": "//p-tree//span[contains(text(), 'Petition')]",
        "description": "Text label for Petition folder"
      }
    },
    "dynamicLocators": {
      "anyFolderCheckboxByName": {
        "label": "Any Folder Checkbox by Name (Parameterized)",
        "xpath": "//p-tree//span[contains(text(), '{folderName}')]/ancestor::p-treenode//div[contains(@class, 'p-checkbox-box')]",
        "description": "Dynamic locator to find any folder's checkbox by folder name. Replace {folderName} with actual folder name",
        "usage": "Replace {folderName} with the target folder name at runtime"
      },
      "anyFolderLabelByName": {
        "label": "Any Folder Label by Name (Parameterized)",
        "xpath": "//p-tree//span[contains(text(), '{folderName}')]",
        "description": "Dynamic locator to find any folder label by name. Replace {folderName} with actual folder name",
        "usage": "Replace {folderName} with the target folder name at runtime"
      },
      "anyFolderNodeByName": {
        "label": "Any Folder TreeNode by Name (Parameterized)",
        "xpath": "//p-treenode[.//span[contains(text(), '{folderName}')]]",
        "description": "Dynamic locator to find the entire p-treenode by folder name",
        "usage": "Replace {folderName} with the target folder name at runtime"
      }
    },
    "stateVerification": {
      "selectedFolderByHighlight": {
        "label": "Selected Folder (p-highlight)",
        "xpath": "//p-treenode[contains(@class, 'p-highlight')]",
        "description": "Find selected folder node by p-highlight CSS class"
      },
      "selectedFolderByTreeNodeSelected": {
        "label": "Selected Folder (p-treenode-selected)",
        "xpath": "//p-treenode[contains(@class, 'p-treenode-selected')]",
        "description": "Find selected folder node by p-treenode-selected CSS class"
      },
      "selectedCheckbox": {
        "label": "Selected Checkbox (p-highlight)",
        "xpath": "//div[contains(@class, 'p-checkbox-box') and contains(@class, 'p-highlight')]",
        "description": "Find checked checkbox by p-highlight CSS class on p-checkbox-box"
      },
      "uploadButtonEnabled": {
        "label": "Upload Button Enabled State",
        "xpath": "//p-dialog//button[contains(., 'Upload') and not(@disabled)]",
        "description": "Upload button in enabled state (not disabled)"
      },
      "uploadButtonDisabled": {
        "label": "Upload Button Disabled State",
        "xpath": "//p-dialog//button[contains(., 'Upload') and @disabled]",
        "description": "Upload button in disabled state"
      },
      "allSelectedFolders": {
        "label": "All Selected Folders",
        "xpath": "//p-tree//p-treenode[contains(@class, 'p-highlight') or contains(@class, 'p-treenode-selected')]",
        "description": "Find all selected folder nodes"
      },
      "checkboxCount": {
        "label": "Total Checkboxes Count",
        "xpath": "//p-tree//div[contains(@class, 'p-checkbox-box')]",
        "description": "Find all checkboxes in the tree for counting"
      },
      "selectedCheckboxCount": {
        "label": "Selected Checkboxes Count",
        "xpath": "//p-tree//div[contains(@class, 'p-checkbox-box') and contains(@class, 'p-highlight')]",
        "description": "Find all checked checkboxes for counting selections"
      }
    }
  },
  "notes": {
    "primeNgRules": [
      "All checkbox clicks MUST target div.p-checkbox-box elements",
      "NEVER use input[@type='checkbox'] - these are hidden and not clickable",
      "NEVER use label elements for clicking checkboxes",
      "Selection state is verified via CSS classes: p-highlight, p-treenode-selected",
      "Do NOT use @checked or @selected attributes for state verification",
      "All locators use relative XPath (//)",
      "All locators are scoped within p-tree/p-treenode for tree elements"
    ],
    "usageInstructions": [
      "For checkbox clicks: Use locators from folderTree section (e.g., billingCheckbox)",
      "For state verification: Use locators from stateVerification section",
      "For dynamic folder selection: Use anyFolderCheckboxByName and replace {folderName} at runtime",
      "For button actions: Use locators from buttons section",
      "Wait for modal to be visible before interacting with elements",
      "Verify checkbox state after click using p-highlight CSS class"
    ]
  }
}
