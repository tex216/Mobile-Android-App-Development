# Test Plan

**Author**: Team 109

## 1 Testing Strategy

### 1.1 Overall strategy

*This section should provide details about your unit-, integration-, system-, and regression-testing strategies. In particular, it should discuss which activities you will perform as part of your testing process, and who will perform such activities.*

### 1.2 Test Selection

*Here you should discuss how you are going to select your test cases, that is, which black-box and/or white-box techniques you will use. If you plan to use different techniques at different testing levels (e.g., unit and system), you should clarify that.*

### 1.3 Adequacy Criterion

*Define how you are going to assess the quality of your test cases. Typically, this involves some form of functional or structural coverage. If you plan to use different techniques at different testing levels (e.g., unit and system), you should clarify that.*

### 1.4 Bug Tracking

*Describe how bugs and enhancement requests will be tracked.*

Bugs and enhancements will be created as a Git issue. Git issues will be prioritized by their impact. Prioritization will be issues with impacts to requirements (bugs or enhancements), bugs with existing features, and nice-to-have enhancements as lowest priority.

### 1.5 Technology

*Describe any testing technology you intend to use or build (e.g., JUnit, Selenium).*

* JUnit for unit tests.
* Android Studio emulator for integration and system tests.

## 2 Test Cases

*This section should be the core of this document. You should provide a table of test cases, one per row. For each test case, the table should provide its purpose, the steps necessary to perform the test, the expected result, the actual result (to be filled later), pass/fail information (to be filled later), and any additional information you think is relevant.*

### 2.1 Unit Tests

Id | Purpose | Steps | Expected Result | Actual Result | Pass/Fail Info
-- | ------- | ----- | --------------- | ------------- | --------------
1.1.1 | Throw error for incomplete addJob() | <ol><li>Execute addJob() with partially complete payload</li></ol> | Error thrown | - | -
1.1.2 | Throw error for invalid addJob() details pt. 1 | <ol><li>Execute addJob() with text in details that expect integer or float values </li></ol> | Error thrown | - | -
1.1.3 | Throw error for invalid addJob() details pt. 2 | <ol><li>Execute addJob() with negative values in details that expect integer or float values </li></ol> | Error thrown | - | -


### 2.2 Integration Tests

Id | Purpose | Steps | Expected Result | Actual Result | Pass/Fail Info
-- | ------- | ----- | --------------- | ------------- | --------------
2.1 | Save Job Offer to DB | <ol><li>Prepare addJob() payload with new job offer</li><li>Execute addJob() with Job Offer details</li></ol> | New job offer is inserted in DB with a valid and unique jobId | TBD | TBD
2.2 | Fetch Job Offer from DB | <ol><li>Prepare getAllJobOffers() payload</li><li>Execute getAllJobOffers()</li></ol> | All records in DB are returned | TBD | TBD
2.3 | Pass user inputs from GUI to backend | <ol><li>Open current job details screen</li><li>Enter current job details</li><li>Save current job details</li><li>Inspect if inputs are passed</li></ol> | User inputs from GUI are successfully passed to backend | TBD | TBD

### 2.3 System Tests

Id | Purpose | Steps | Expected Result | Actual Result | Pass/Fail Info
-- | ------- | ----- | --------------- | ------------- | --------------
3.0 |View Main Menu | <ol><li>Initialize app</li></ol> | User is presented with main menu where he/she can enter current job, add job offers, adjust comparison settings, or compare job offers | - | -
3.1.1 | Enter current job details and cancel without saving | <ol><li>Initialize app</li><li>Navigate to current job screen and see a list of empty details according to the requirements</li><li>Complete current job details form</li><li>Exit screen and return to main menu without saving</li><li>Navigate back to current job screen</li></ol> | Current job details remains unchanged from User actions | - | -
3.1.2 | Enter current job details and save | <ol><li>Initialize app</li><li>Navigate to current job screen</li><li>Complete current job details form</li><li>Save and exit screen</li><li>Navigate back to current job details screen</li></ol> | <ul><li>User cannot edit current job since no current job has been entered</li><li>DB should have a new record for current job</li><li>Current job details reflect user inputs</li></ul> | - | -
3.1.3 | Edit current job | <ol><li>Complete steps in 3.1.2</li><li>Navigate to current job offer screen</li><li>Update current job offer & save</li><li>Exit current job screen, navigate back to main menu, and return to current job screen</li></ol> | <ul><li>User cannot edit current job if not already entered</li><li>Current job details will reflect most recent changes made by User</li></ul> | - | -
3.1.4 | Disable adding another current job | <ol><li>Complete steps in 3.1.2</li><li>Navigate to current job offer screen</li></ol> | <ul><li>User can only edit current job and is unable to add another</li></ul> | - | -
3.2.1 | Enter job offer without saving | <ol><li>Initialize app</li><li>Navigate to job offer screen</li><li>Complete job offer details listed in the requirements</li><li>Exit screen without saving</li><li>Check the DB</li></ol> | DB should not have any new records due to User actions | - | -
3.2.2 | Enter job offer and save | <ol><li>Initialize app</li><li>Navigate to job offer screen</li><li>Complete job offer form, save, and exit</li></ol> | <ul><li>User cannot save unless all fields are completed</li><li>DB should have a new record for job offer</li></ul> | - | -
2.3.0 | Checked that if no weights are assigned, all factors are equal | <ol><li>Initialize app</li><li>Navigate to comparison settings screen</li></ol> | User should see all factors are equal (all weights set to 1) | - | -
2.3.1 | Adjust comparison settings | <ol><li>Initialize app</li><li>Navigate to comparison settings screen</li><li>Update weights with valid values and save</li><li>Return to main menu and navigate back to comparison settings screen</li></ol> | <ul><li>User cannot save settings if values are invalid (i.e., negative)</li><li>When returning to comparison settings screen, values reflect User changes</li></ul> | - | -
2.3.2 | Adjust comparison settings with jobs already entered | <ol><li>Initialize app</li><li>Enter valid current job (see 3.1.2)</li><li>Navigate to comparison settings screen</li><li>Update weights with valid values and save</li><li>Return to main menu and navigate back to comparison settings screen</li><li>Check job score in DB</li></ol> | <ul><li>User cannot save settings if values are invalid (i.e., negative)</li><li>When returning to comparison settings screen, values reflect User changes</li><li>Job scores should be recalculated using latest User setting inputs</li></ul> | - | -
2.4.0 | Check if compare job offers is disabled when appropriate pt. 1 | <ol><li>Initialize app</li><li>Enter and save a valid current job</li></ol> | Compare job offers should remain disabled throughout the entire procedure | - | -
2.4.1 | Check if compare job offers is disabled when appropriate pt. 2 | <ol><li>Initialize app</li><li>Enter and save a valid job offer</li></ol> | Compare job offers should remain disabled throughout the entire procedure | - | -
2.4.2 | See list of job offers | <ol><li>Initialize app</li><li>Enter and save a valid current job</li><li>Enter and save a valid job offer</li><li>Navigate to compare job offers screen</li></ol> | <ul><li>Compare job offer screen in main menu should be disabled until both current job and a job offer are saved</li<li>User should see a list of jobs when selecting compare job offer screen. This will is expected after both jobs are entered</li></li>Current job is clearly indicated</li><li>Jobs should be ranked by best to worst (i.e., by job score descending)</li></ul> | - | -
