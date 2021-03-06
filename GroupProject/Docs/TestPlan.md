# Test Plan v3

**Author**: Team 109

v1 to v2: Updated job score test case
\
v2 to v3: Added additional tests and test results

## 1 Testing Strategy

### 1.1 Overall strategy

Testing will be completed at three levels: unit, integration, and system. Each level will focus on a specific area, the idea being that levels are orthogonal and come together to create a comprehensive test coverage.
* Unit focuses on methods and valid values
* Integration will be conducted to ensure modules interact appropriately
* System will test the UX and provide a second line to ensuring all requirements are met that were not captured by other levers

Testing procedures will follow the spirit of the test plan cases outlined below.

Primary testing duties will be given to the Quality Engineer, but the team will support where / if needed.

Regression testing will be conducted after every release to ensure no feature regression.

### 1.2 Test Selection

Because of the time cost with white-box, test cases will be primarily black-box throughout all levels of testing. Tests will leverage category-partition approach to bound the scope. See overall test strategy for more details.

### 1.3 Adequacy Criterion

The main goal is to ensure that all app requirements are covered. As such, test cases will true back to requirements additional second order requirements derived from our understanding of the domain.

### 1.4 Bug Tracking

Bugs and enhancements will be created as a Git issue. Git issues will be prioritized by their impact. Prioritization will be issues with impacts to requirements (bugs or enhancements), bugs with existing features, and nice-to-have enhancements as lowest priority.

### 1.5 Technology

* Android Studio emulator for integration and system tests.

## 2 Test Cases

### 1 Unit Tests

Id | Purpose | Steps | Expected Result | Actual Result | Pass/Fail Info
-- | ------- | ----- | --------------- | ------------- | --------------
1.1.1 | Throw error for incomplete addJob() | Execute addJob() with partially complete payload | Error thrown | Displays error mark next to incomplete fields | Pass
1.1.2 | Throw error for invalid addJob() details pt. 1 | Execute addJob() with text in details that expect integer or float values | Error thrown | Frontend input field does not allow user to type text | Pass
1.1.3 | Throw error for invalid addJob() details pt. 2 | Execute addJob() with negative values in details that expect integer or float values | Error thrown | Frontend input field does not allow user to input negative values | Pass
1.2.1 | Throw error for invalid updateComparisonSettingsWeight() | Execute updateComparisonSettingsWeight() with one or more negative values | Error thrown | Frontend input field does not allow user to input negative values | Pass
1.2.2 | Throw error for invalid updateComparisonSettingsWeight() | Execute updateComparisonSettingsWeight() with all 0s | Error thrown | Division by 0 error for job score calculation | Fail, raised issue, fixed bug
1.2.3 | Throw error for empty updateComparisonSettingsWeight() | Execute updateComparisonSettingsWeight() with one or more partially complete values | Error thrown | App crashes | Fail, raised issue, fixed bug
1.3.1 | Calculate correct jobScore() with default weights | <ul><li>Execute score() with these values: Index = 50, Telework Days = 1, Yearly Salary = 100,000, Yearly Bonus = 10,000, Retirement Benefits = 6%, and Leave Time = 26</li><li>All weights set to 1</li></ul> | Returned score is 46,400 | 46,400 | Pass
1.3.2 | Calculate correct jobScore() with default weights | <ul><li>Execute score() with these values: Index = 125, Telework Days = 1, Yearly Salary = 100,000, Yearly Bonus = 10,000, Retirement Benefits = 6%, and Leave Time = 26</li><li>All weights set to 1</li></ul> | Returned score is 18,560 | 18,560 | Pass
1.3.3 | Calculate correct jobScore() with specific weights | <ul><li>Execute score() with these values: Index = 50, Telework Days = 1, Yearly Salary = 100,000, Yearly Bonus = 10,000, Retirement Benefits = 6%, and Leave Time = 26</li><li>Yearly Salary Weight = 2, Yearly Bonus Weight = 3, Retirement Weight = 4, Leave Time Weight = 6, and Allowed Telework Weight = 1</li></ul> | Returned score is 38,000 | 38,000 | Pass
1.4 | Current job pre-populated correctly | Enter a current job. Go to enter current job interface. | Current job information pre-populated correctly | Remote work dropdown off by 1 | Fail, raised issue, fixed bug


### 2 Integration Tests

Id | Purpose | Steps | Expected Result | Actual Result | Pass/Fail Info
-- | ------- | ----- | --------------- | ------------- | --------------
2.1 | Save Current Job to DB | <ol><li>Prepare system.addJob() payload with isCurrentJob set to true</li><li>Execute addJob()</li><li>Inspect the JOB_DETAILS table</li></ol> | New job offer is inserted in DB with a valid & unique jobId and isCurrentJob is set to 1 | New job offer is inserted in DB with a valid and unique jobId | Pass
2.2 | Save Job Offer to DB | <ol><li>Prepare system.addJob() payload with isCurrentJob set to false</li><li>Execute addJob() with Job Offer details</li><li>Inspect the JOB_DETAILS table</li></ol> | New job offer is inserted in DB with a valid & unique jobId | New job offer is inserted in DB with a valid and unique jobId | Pass
2.3 | Fetch Job Offer from DB | <ol><li>Follow procedure in tests 2.1 and 2.2 to enter job details</li><li>Prepare system.getAllJobs() payload</li><li>Execute getAllJobOffers()</li></ol> | All records in DB are returned to object | All records in DB are returned | Pass
2.4 | Initialize default comparison weights in DB | Execute system.initialize() | <ul><li>DB is initialized with COMPARISON_SETTINGS_WEIGHT table</li><li>All weight values are set to 1</li></ul> | Table is initialized with all weight values set to 1 | Pass
2.5 | Insert comparison weights in DB | <ol><li>Prepare system.updateComparisonWeights() hashmap payload. All values can be set to 2 for this test</li><li>Execute updateComparisonWeights() hashmap</li><li>Inspect the COMPARISON_SETTINGS_WEIGHT table</li></ol> | The 5 comparison weight values are all set to 2 | All weight values are set to 2 | Pass
2.6 | Pass user inputs from current job screen to backend objects | <ol><li>Open current job details screen</li><li>Enter current job details</li><li>Save current job details</li><li>Inspect if inputs are passed</li></ol> | User inputs from GUI are successfully passed to backend objects | User inputs from GUI are successfully passed to backend | Pass
2.7 | Move between screens | <ol><li>Open home page. Navigate to Enter Current Job screen</li><li>Cancel and return to Main Menu</li><li>Navigate to Enter Job Offer Screen</li><li>Cancel and return to Main Menu</li><li>Navigate to Adjust Comparison Settings screen</li><li>Cancel and return to Main Menu</li></ol> | User should be able to execute each step and move between screens without crashing the app. Final screen will be Main Menu | Able to move between each enumerated screen in the procedure without crashing the app | Pass

### 3 System Tests

Id | Purpose | Steps | Expected Result | Actual Result | Pass/Fail Info
-- | ------- | ----- | --------------- | ------------- | --------------
3.0 |View Main Menu | <ol><li>Initialize app</li></ol> | User is presented with main menu where he/she can (1) enter current job, (2) add job offers, (3) adjust comparison settings, or (4) compare job offers | User is presented with the main menu and allowed to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet) | Pass
3.1.1 | Enter current job details and cancel without saving | <ol><li>Initialize app</li><li>Navigate to current job screen and see a list of empty details according to the requirements</li><li>Complete current job details form</li><li>Exit screen and return to main menu without saving</li><li>Navigate back to current job screen</li></ol> | Current job details remain unchanged from User actions | Current job details remains unchanged from User actions | Pass
3.1.2 | Enter current job details and save | <ol><li>Initialize app</li><li>Navigate to current job screen</li><li>Complete current job details form</li><li>Save and exit screen</li><li>Navigate back to current job details screen</li></ol> | <ul><li>User cannot edit current job if no current job has been entered</li><li>DB should have a new record for current job</li><li>Current job details reflect user inputs</li></ul> | <ul><li>There is no job info pre-populated in the Current Job screen when entering current job for the first time.</li><li> After entering all of the current job details, and clicking save button, DB has a new record for current job</li><li>Current job details reflect user inputs</li></ul>| Pass
3.1.3 | Edit current job | <ol><li>Complete steps in 3.1.2</li><li>Navigate to current job offer screen</li><li>Update current job offer & save</li><li>Exit current job screen, navigate back to main menu, and return to current job screen</li></ol> | <ul><li>User cannot edit current job if not already entered</li><li>Current job details will reflect most recent changes made by User</li></ul> | <ul><li>User can see the previous saved current job details </li><li> User is able to edit the details </li><li>After saving, current job details in DB can reflect most recent changes made by User</li></ul> | Pass
3.1.4 | Disable adding another current job | <ol><li>Complete steps in 3.1.2</li><li>Navigate to current job offer screen</li></ol> | User can only edit current job and is unable to add another | User can only modify current job and is unable to add another | Pass
3.2.1 | Enter job offer without saving | <ol><li>Initialize app</li><li>Navigate to job offer screen</li><li>Complete job offer details listed in the requirements</li><li>Exit screen without saving</li><li>Check the DB</li></ol> | DB should not have any new records due to User actions | DB do not have any new records due to User actions | Pass
3.2.2 | Enter job offer and save | <ol><li>Initialize app</li><li>Navigate to job offer screen</li><li>Complete job offer form, save, and exit</li></ol> | <ul><li>User cannot save unless all fields are completed</li><li>DB should have a new record for job offer</li></ul> | <ul><li>User cannot save unless all fields are completed</li><li>DB have a new record for job offer</li></ul> | Pass
3.3.0 | Checked that if no weights are assigned, all factors are equal | <ol><li>Initialize app</li><li>Navigate to comparison settings screen</li></ol> | User should see all factors are equal (all weights set to 1) | All factors are equal (all weights set to 1) | Pass
3.3.1 | Adjust comparison settings | <ol><li>Initialize app</li><li>Navigate to comparison settings screen</li><li>Update weights with valid values and save</li><li>Return to main menu and navigate back to comparison settings screen</li></ol> | <ul><li>User cannot save settings if values are invalid (i.e., negative)</li><li>When returning to comparison settings screen, values reflect User changes</li></ul> | <ul><li>User is not allowed to input invalid values (i.e., negative)</li><li>Values reflect User changes when returning to comparison settings screen</li></ul> | Pass
3.3.2 | Adjust comparison settings with jobs already entered | <ol><li>Initialize app</li><li>Enter valid current job (see 3.1.2)</li><li>Navigate to comparison settings screen</li><li>Update weights with valid values and save</li><li>Return to main menu and navigate back to comparison settings screen</li><li>Check job score in DB</li></ol> | <ul><li>User cannot save settings if values are invalid (i.e., negative)</li><li>When returning to comparison settings screen, values reflect User changes</li><li>Job scores should be recalculated</li></ul> | <ul><li>User is not allowed to input invalid values (i.e., negative)</li><li>Values reflect User changes when returning to comparison settings screen</li><li>Job scores have been recalaulated</li></ul> | Pass
3.4.0 | Check if compare job offers is disabled when appropriate pt. 1 | <ol><li>Initialize app</li><li>Enter and save a valid current job</li></ol> | Compare job offers should remain disabled throughout the entire procedure | Compare job offers remain disabled throughout the entire procedure | Pass
3.4.1 | Check if compare job offers is disabled when appropriate pt. 2 | <ol><li>Initialize app</li><li>Enter and save a valid job offer</li></ol> | Compare job offers should remain disabled throughout the entire procedure | Compare job offers remain disabled throughout the entire procedure | Pass
3.4.2 | See list of job offers | <ol><li>Initialize app</li><li>Enter and save a valid current job</li><li>Enter and save a valid job offer</li><li>Navigate to compare job offers screen</li></ol> | <ul><li>Compare job offer screen in main menu should be disabled until both current job and a job offer are saved</li><li>User should see a list of jobs when selecting compare job offer screen. This will is expected after both jobs are entered</li></li>Current job is clearly indicated</li><li>Jobs should be ranked by best to worst (i.e., by job score descending)</li></ul> |<ul><li> Compare job offer button in main menu is disabled until there are two job details.</li><li>User can see a list of jobs in ranked list of jobs screen. </li></li> Current job is clearly indicated</li><li> Jobs are ranked by best to worst (i.e., by job score descending)</li></ul> | Pass
