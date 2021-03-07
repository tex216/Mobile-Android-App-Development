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

Purpose | Steps | Expected Result | Actual Result | Pass/Fail Info
------- | ----- | --------------- | ------------- | --------------
Placeholder | <ol><li>Task 1</li><li>Task 2</li></ol> | - | - | -

### 2.2 Integration Tests

Purpose | Steps | Expected Result | Actual Result | Pass/Fail Info
------- | ----- | --------------- | ------------- | --------------
Save Job Offer to DB | <ul><li>Prepare addJob() payload with new job offer</li><li>Execute addJob() with Job Offer details</li></ul> | New job offer is inserted in DB with a valid and unique jobId | TBD | TBD
Fetch Job Offer from DB | <ul><li>Prepare getAllJobOffers() payload</li><li>Execute getAllJobOffers()</li></ul> | All records in DB are returned | TBD | TBD
Pass user inputs from GUI to backend | <ul><li>Open current job details screen</li><li>Enter current job details</li><li>Save current job details</li><li>Inspect if inputs are passed</li></ul> | User inputs from GUI are successfully passed to backend | - | -

### 2.3 System Tests

Purpose | Steps | Expected Result | Actual Result | Pass/Fail Info
------- | ----- | --------------- | ------------- | --------------
Placeholder | <ul><li>Task 1</li><li>Task 2</li></ul> | - | - | -
