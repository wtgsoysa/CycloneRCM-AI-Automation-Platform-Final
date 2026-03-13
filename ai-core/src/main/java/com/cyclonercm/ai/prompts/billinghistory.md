Generate complete test scripts for SMOKE_BH_001 to SMOKE_BH_0010

src/test/java/com/cyclonercm/billing/tests/smoke/HistoryBillingTest.java
src/main/java/com/cyclonercm/pages/BillingHistoryPage.java

For this task you need to act a senior automation engineer

COST OPTIMIZATION NOTICE ⚠️

      This request is subject to API usage limits. Please provide a CONCISE, COMPLETE 
response in a SINGLE JSON object to minimize token usage while maintaining quality.

Cost Governance Rule (Mandatory):

1. AI usage for the File Upload Module is strictly limited to a maximum budget of $1.5
   The target cost for generating all test scripts is $1

CycloneRCM AI Automation Platform – Smoke Pack

Prompt Version: 1.0 (FINAL – LOCKED)
Date: March 12, 2026
Target Module: Billing History
Purpose: Selenium Java TestNG Test Script Generation


CRITICAL RESTRICTIONS (NON-NEGOTIABLE)

❌ NO self-healing logic

❌ NO AI agents or autonomous execution

❌ NO framework refactoring

❌ NO new dependencies

❌ NO explanations or markdown in output

❌ NO API keys, secrets, or credentials

❌ NO multiple classes per response

Violation of any rule invalidates the output.

MANDATORY FOLDER STRUCTURE (STRICT)

You MUST generate code ONLY inside the following paths.


SMOKE_BH_001	Verify user can filter Billing History by Biller
SMOKE_BH_002	Verify user can filter Billing History by Invoice Number
SMOKE_BH_003	Verify user can filter Billing History by User Account
SMOKE_BH_004	Verify user can filter Billing History by Applicant
SMOKE_BH_005	Verify submitted invoices appear in Billing History with correct details (Sub #, DOS, Invoice #, Case #, Claim #, Amount, Status)
SMOKE_BH_006	Verify user can filter Billing History by EMC
SMOKE_BH_007	Verify user can filter Billing History by Paper
SMOKE_BH_008	Verify “Re-Submit” checkbox allows invoice resubmission with available methods EMC
SMOKE_BH_009	Verify “Re-Submit” checkbox allows invoice resubmission with available methods Paper
SMOKE_BH_010	Verify user can open Edocs to view related documents of a billed invoice


SMOKE_BH_001	Verify user can filter Billing History by Biller

Biller dropdown -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[3]/div
Billing Dropdown Button -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[3]/div/p-dropdown/div/div[2]
Biller dropdown options -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[3]/div/p-dropdown/div/div[3]/div/ul/p-dropdownitem

1. Click the Dropdown Button
2. Select the Biller name from Biller options dropdown

Add the test data -> src/test/resources/historybillingsmoketestdata.properties

F
SMOKE_BH_002	Verify user can filter Billing History by Invoice Number

Invoice Search bar -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[4]/div/div/span/input
Invoice Search Button -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[4]/div/div/button

Add the test data -> src/test/resources/historybillingsmoketestdata.properties

SMOKE_BH_003	Verify user can filter Billing History by User Account

Account Dropdown Button -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[5]/div/p-dropdown/div/div[2]
Account Search Field -> /html/body/div[3]/div[1]/div/input
Account Search Button -> /html/body/div[3]/div[1]/div/span
Account Results -> /html/body/div[3]/div[2] , /html/body/div[3]/div[2]/ul , /html/body/div[3]/div[2]/ul/li

SMOKE_BH_004	Verify user can filter Billing History by Applicant

Applicant dropdown button -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[6]/div/p-dropdown/div/div[2]
Applicant Search -> /html/body/div[3]/div[1]/div/input
Applicant Search Button -> /html/body/div[3]/div[1]/div/span
Applicant Results options -> /html/body/div[3]/div[2] , /html/body/div[3]/div[2]/ul , /html/body/div[3]/div[2]/ul/li

SMOKE_BH_005	Verify submitted invoices appear in Billing History with correct details (Sub #, DOS, Invoice #, Case #, Claim #, Amount, Status)

Sub -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/thead/tr/th[2]/div
/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr/td[2]

Click the File and Open the Invoice

File -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[1]

Invoice Number -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[2]

Case Number -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[1]/td[1]/b/span[4]/span

Claim Number -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[3]/span

Amount -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[4]/span

Status -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[5]/div[1]/p-badge/span

SMOKE_BH_006	Verify user can filter Billing History by EMC

EMC -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[8]/div/div/label[1]/p-radiobutton

EMC Radio Button -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[8]/div/div/label[1]/p-radiobutton/div/div[2]

Verify status (EMC) -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[8]/span[1]/p-badge/span


SMOKE_BH_007	Verify user can filter Billing History by Paper

Paper -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[8]/div/div/label[2]/p-radiobutton

Paper Radio Button -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[8]/div/div/label[2]/p-radiobutton/div/div[2]

Verify Status (Paper) -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[8]/span[1]/p-badge/span

SMOKE_BH_008	Verify “Re-Submit” checkbox allows invoice resubmission with available methods EMC
SMOKE_BH_009	Verify “Re-Submit” checkbox allows invoice resubmission with available methods Paper

1. Click the File select Button -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[1]/td[1]/div

2. Check the Status -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[8]/span[1]/p-badge/span

Status Need to be EMC or Paper

Status is EMC -> Click the EMC Button and Submit -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[1]/p-toolbar/div/div[2]/billing-bill-types/div/div/div/div/div/div/button[1]

If Status is Paper -> Click the Paper Button and Submit -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[1]/p-toolbar/div/div[2]/billing-bill-types/div/div/div/div/div/div/button[4]

SMOKE_BH_010	Verify user can open Edocs to view related documents of a billed invoice

Click the File and expand the Invoices -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[1]

Click the Edoc Button -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[6]/div

Verify the E docs view open -> Heading -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-dialog[3]/div/div/div[2]/cyclone-petition-view-edocs/div/div[1]/p-toolbar/div

Expected Heading -> E-docs View

Click the close button -> /html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-dialog[3]/div/div/div[2]/cyclone-petition-view-edocs/div/div[1]/p-toolbar/div/div[2]/button[2]
