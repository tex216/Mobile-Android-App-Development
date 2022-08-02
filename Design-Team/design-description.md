# Design Document

CS 6300 
Team 109 Deliverable 1

## Requirements

1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).
   ``Not represented in the diagram as this will be handled by the GUI prompt. We have a function doJobsExist() which can be used to disable/enable the compare job offers feature.``

1. When choosing to enter current job details, a user will:
   1. Be shown a user interface to enter (if it is the first time) or edit all of the details of their current job, which consist of: ``Not represented as this will be handled by the GUI prompt. Entering will be handled by the addJob() method where the isCurrentJob will be set to True. Editing will be handled by the updateJob() method which will call into the corresponding set methods e.g. setCompany() to update the job details.``
      1. Title ``An attribute in 'Job'.``
      1. Company ``An attribute in 'Job'.``
      1. Location (entered as city and state) ``An attribute in 'Job'.``
      1. Cost of living in the location (expressed as an index) ``An attribute in 'Job'.``
      1. Yearly salary ``An attribute in 'Job'.``
      1. Yearly bonus ``An attribute in 'Job'.``
      1. Allowed weekly telework days ``An attribute in 'Job'.``
      1. Retirement benefits (as percentage matched) ``An attribute in 'Job'.``
      1. Leave time (vacation days and holiday and/or sick leave, as a single overall number of days) ``An attribute in 'Job'.``
   1. Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu. ``Saving the details is done by the 'addJob()' method which creates an instance of the 'Job' class with the provided attributes and isCurrentJob set to True. Save and cancel & exit without saving will be handled by the GUI.``

1. When choosing to enter job offers, a user will:
   1. Be shown a user interface to enter all of the details of the offer, which are the same ones listed above for the current job. ``Attributes in 'Job' with isCurrentJob flagged to False.``
   1. Be able to either save the job offer details or cancel. ``Saving the details is done by the 'addJob()' method which creates an instance of the 'Job' class with the provided attributes and isCurrentJob attribute set to False. Save and cancel & exit without saving will be handled by the GUI.``
   1. Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer with the current job details (if present). ``Not represented as this is handled by the GUI prompt.``

1. When adjusting the comparison settings, the user can assign integer weights to:
   1. Yearly salary ``An attribute of the 'ComparisonSettings' class.``
   1. Yearly bonus ``An attribute of the 'ComparisonSettings' class.``
   1. Allowed weekly telework days ``An attribute of the 'ComparisonSettings' class.``
   1. Retirement benefits ``An attribute of the 'ComparisonSettings' class.``
   1. Leave time ``An attribute of the 'ComparisonSettings' class.``

   If no weights are assigned, all factors are considered equal. ``Represented by having all weights set equal to 1 as the default value.``

1. When choosing to compare job offers, a user will:
   1. Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated. ``The getAllJobOffers() method in the System class will call into the getAllJobOffers() method in the CompareJobOffers class which will get all objects of 'Job' with the requested attributes. The getAllJobOffers() method in CompareJobOffers class will also rank the list by 'score' descending. The actual display & styling will be handled by the GUI.``
   1. Select two jobs to compare and trigger the comparison. ``The compareJobOffers() method in the System class will call into the getSelectedJobOffers() method in the CompareJobOffers class which will return two 'Job' objects with requested attributes. The selection of jobs is not represented and handled by the GUI prompt.``
   1. Be shown a table comparing the two jobs, displaying, for each job: ``Returned & handled by the GUI after the compareJobOffers() is executed.``
      1. Title ``This information is in the Job class.``
      1. Company ``This information is in the Job class.``
      1. Location ``This information is in the Job class.``
      1. Yearly salary adjusted for cost of living ``This information is in the Job class.``
      1. Yearly bonus adjusted for cost of living ``This information is in the Job class.``
      1. Allowed weekly telework days ``This information is in the Job class.``
      1. Retirement benefits (as percentage matched) ``This information is in the Job class.``
      1. Leave time ``This information is in the Job class.``
   1. Be offered to perform another comparison or go back to the main menu. ``Not represented as this is handled by the GUI.``

1. When ranking jobs, a job’s score is computed as the **weighted** sum of: ``This is represented by the derived attribute 'score' (see in Assumptions & Rationale). Ranking will happen within the getAllJobOffers() method in the CompareJobOffers class which sorts by 'score' descending. The math logic is not represented as this will be implemented by the method.``

   AYS + AYB + (RBP * AYS) + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8)

   where:

   AYS = yearly salary adjusted for cost of living
   AYB = yearly bonus adjusted for cost of living
   RBP = retirement benefits percentage
   LT = leave time
   RWT = telework days per week

   The rationale for the CT subformula is:
   1. value of an employee hour = (AYS / 260) / 8
   1. commute hours per year (assuming a 1-hour/day commute) = 1 * (260 - 52 * RWT)
   1. therefore **travel-time cost** = (260 - 52 * RWT) * (AYS / 260) / 8

   For example, if the weights are 2 for the yearly salary, 2 for the retirement benefits, and 1 for all other factors, the score would be computed as:

   2/7 * AYS + 1/7 * AYB + 2/7 * (RBP * AYS) + 1/7 * (LT * AYS / 260) - 1/7 * ((260 - 52 * RWT) * (AYS / 260) / 8)


1. The user interface must be intuitive and responsive.
   ``This is not represented in my diagram and will be handled in the implementation.``

1. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary). ``Represented by a border that encapsulates all the classes.``

## Assumptions & Rationale

* This app currently does not support an account or user abstraction.
* 'score' attribute is calculated when a new instance of 'Job' is initialized and re-calculated after updateComparisonSettingsWeight() method is executed. Executing will lead to recalculateJobScore() method calling all Job objects to setScore().
