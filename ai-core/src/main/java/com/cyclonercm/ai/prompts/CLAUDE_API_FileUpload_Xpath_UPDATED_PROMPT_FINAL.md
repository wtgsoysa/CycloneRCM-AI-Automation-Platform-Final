You are a senior Selenium WebDriver automation architect with hands-on experience testing Angular applications built using PrimeNG components (p-dialog, p-tree, p-treenode, p-checkbox).

This is a corrective task. Previous AI-generated XPath locators FAILED in execution because they were based on incorrect assumptions about HTML checkboxes, labels, and selection state.

Your job is to generate ONLY execution-safe, PrimeNG-accurate XPath locators that work in real Selenium runs.

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


--------------------------------
ABSOLUTE DOM TRUTHS (MANDATORY)
--------------------------------

The application uses PrimeNG.

PrimeNG CHECKBOX RULES (NON-NEGOTIABLE):

1. DO NOT generate XPaths that target:
    - <input type="checkbox">
    - <label> elements
2. <input> elements are hidden and NOT clickable.
3. The ONLY valid clickable checkbox element is:
    - <div class="p-checkbox-box">
4. Selection state is represented by CSS classes such as:
    - p-highlight
    - p-treenode-selected
      NOT by @checked or @selected attributes.
5. Folder rows exist inside:
    - p-tree → p-treenode → li
6. Any XPath violating these rules is INVALID.

--------------------------------
MODULE CONTEXT
--------------------------------

Module: File Upload Modal
Title: "Reason For Your Upload"
Framework: Selenium WebDriver
UI Stack: Angular + PrimeNG
Modal Root Reference:
/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div

--------------------------------
XPATH GENERATION RULES (STRICT)
--------------------------------

1. ALL checkbox locators MUST:
    - Resolve to div[contains(@class,'p-checkbox-box')]
    - Be anchored to a visible folder label via ancestor traversal
    - Be scoped inside p-tree / p-treenode

2. DO NOT:
    - Use input[@type='checkbox']
    - Use label clicks
    - Use @checked
    - Use jQuery-only CSS selectors
    - Use absolute XPaths as primary locators

3. State verification MUST use:
    - CSS class checks (p-highlight, p-treenode-selected)
    - NOT attributes

4. Use relative XPaths (//) only.

--------------------------------
COVERAGE REQUIREMENTS
--------------------------------

Generate locators for:

1. Modal
    - Container
    - Backdrop
    - Title

2. Buttons
    - Get Started
    - Upload
    - Refresh
    - Close

3. Folder Tree
    - Tree container
    - Billing checkbox (p-checkbox-box)
    - Collections checkbox (p-checkbox-box)
    - EOR Response checkbox (p-checkbox-box)
    - Petition checkbox (p-checkbox-box)

4. Dynamic Locators
    - Any folder checkbox by visible folder name
      (must resolve to p-checkbox-box)

5. State Verification
    - Selected folder (via p-highlight / p-treenode-selected)
    - Upload button enabled/disabled

--------------------------------
OUTPUT FORMAT
--------------------------------

Return ONLY valid JSON.
No markdown.
No explanations.

Each locator MUST include:
- label
- xpath
- description

Include alternatives ONLY if they follow all rules above.

Update totalLocators with the REAL count of valid locators.

--------------------------------
FINAL INSTRUCTION
--------------------------------

Generate ONLY Selenium-clickable, PrimeNG-safe XPath locators that would pass real execution.

If a locator cannot be made safe, DO NOT include it.

All generated locators please store -> src/main/java/com/cyclonercm/files/utils/FileUploadUpdatedXPaths.md
