Zhengning Han
CS 6300 Assignment 5

1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet ).
<p>
<b> (1) The System class, which is the entry point to the system and will be a singleton initiated on app start, has operation addJob which allows the user to enter current job details. This operation will also allow user to update current job if one already exists. The currentJob attribute keeps track the current job details the user has entered and can determine if a current job already exists. The user can then update the job details such as title, company via the corresponding set methods, e.g. setTitle, setCompany in the Job class. 
(2) The addJob operation also allows the user to enter new job offers. These job offers are added to the jobOffers attribute, which is a list of job offers the user has saved.
(3) The updateComparisonSettingsWeight operation saves any adjustments to comparison settings to the comparisonSettings attribute in the System class.
(4) The Job class has an attribute jobId, which will be auto incremented and unique to each job offer, including the current job. As the user makes selection, the jobId will be used to retrieve the corresponding job via the getSelectedJobs operation. The System class also has an attribute jobOffers, which can be used to determine if there are any job offers to compare. </b>
</p>
<br>

2. When choosing to enter current job details, a user will:
\
a.	Be shown a user interface to enter (if it is the first time) or edit all of the details of their current job, which consist of:
\
i.	Title
\
ii.	Company
\
iii.	Location (entered as city and state)
\
iv.	Cost of living in the location (expressed as an index)
\
v.	Possibility to work remotely (expressed as the number of days a week one could work remotely, between 1 and 5)
\
vi.	Yearly salary
\
vii.	Yearly bonus
\
viii.	Retirement benefits (as percentage matched)
\
ix.	Leave time (vacation days and holiday and/or sick leave, as a single overall number of days)
\
b.	Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.
<p>
<b>The Job class attributes correspond to each of the job details listed. For location, since it consists of both city and state, the Location class is created to encapsulate these information. When the user saves the job, the System class calls the addJob operation and creates a new instance of the Job object. This instance is then assigned to the currentJob attribute in the System class. If the user cancels, the currentJob attribute will remain unassigned.</b>
</p>
<br>

3.	When choosing to enter job offers, a user will:
\
a.	Be shown a user interface to enter all of the details of the offer, which are the same ones listed above for the current job.
\
b.	Be able to either save the job offer details or cancel.
\
c.	Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer with the current job details (if present).
<p>
<b>Since the information for a job offer and the current job is the same, the same addJob operation will be used to save the job offers. These job offers are added to the jobOffers attribute in the Job class. The jobOffers attribute is a list whic holds all of the job offers the user has entered. If the user cancels, a job instance will not be created and will not be added to the jobOffers list. Each job will have a unique jobId attribute so it can be easily retrieved based on the jobs the user has selected to compare.</b>
</p>
<br>

4.	When adjusting the comparison settings, the user can assign integer weights to:
\
a.	Possibility to work remotely
\
b.	Yearly salary
\
c.	Yearly bonus
\
d.	Retirement benefits
\
e.	Leave time
\
If no weights are assigned, all factors are considered equal.
<p>
<b>The enumeration ComparisonSettingType lists these 5 comparison settings that the user can set. The comparisonSettings attribute keeps track of the weight assigned to each by the user. This attribute is part of the System class and will be instantied on app startup. Each comparison setting type will be assigned a default value of 1. As the user adjusts the weights, the updateComparisonSettingsWeight operation will be used to update the corresponding comparison setting based on the type passed into this method. The type will be based on which setting the user chooses to update in the GUI.</b>
</p>
<br>

5.	When choosing to compare job offers, a user will:
\
a.	Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.
\
b.	Select two jobs to compare and trigger the comparison.
\
c.	Be shown a table comparing the two jobs, displaying, for each job:
\
i.	Title
\
ii.	Company
\
iii.	Location
\
iv.	Yearly salary adjusted for cost of living
\
v.	Yearly bonus adjusted for cost of living
\
vi.	Retirement benefits (as percentage matched)
\
vii.	Leave time
\
d.	Be offered to perform another comparison or go back to the main menu.
<p>
<b>The getAllJobOffers will return the jobs in the jobOffers attribute as well as the current job from the currentJob attribute in the System class. The System class will call into the setScore operation in the Job class. Each job will compute and set its own score attribute based on the weights in the comparisonSettings attribute. The getScore operation can then be used to retrieve the score for each job and the getAllJobOffers operation will then order the jobs based on each's score.</b>
</p>
<br>

6.	When ranking jobs, a job’s score is computed as the weighted sum of:
\
AYS + AYB + (RBP * AYS) + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8)
\
where:
\
AYS = yearly salary adjusted for cost of living
\
AYB = yearly bonus adjusted for cost of living
\
RBP = retirement benefits percentage
\
LT = leave time
\
RWT = remote work days per week
\
The rationale for the RWT subformula is:
\
a.	value of an employee hour = (AYS / 260) / 8
\
b.	commute hours per year (assuming a 1-hour/day commute) =
1 * (260 - 52 * RWT)
\
c.	therefore commute-time cost = (260 - 52 * RWT) * (AYS / 260) / 8
\
For example, if the weights are 2 for the yearly salary, 2 for the retirement benefits, and 1 for all other factors, the score would be computed as:
\
2/7 * AYS + 1/7 * AYB + 2/7 * (RBP * AYS) + 1/7 * (LT * AYS / 260) - 1/7 * ((260 - 52 * RWT) * (AYS / 260) / 8)
<p>
<b>The operation setScore will compute the score for each job based on the comparisonSettings input. The comparisonSettings input comes from the comparisonSettings attribute in the System class and the weights assigned to each comparison setting can be retrieved by iterating through the input.</b>
</p>
<br>

7.	The user interface must be intuitive and responsive.
<p>
<b>This is a GUI-specific requirement.</b>
</p>
<br>

8.	For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).
<p>
<b>The System class represents this single system and will be instantiated on app start. It will be a singleton and serves as the entry point to the backend from the frontend. This class ties everything together as it keeps track of the job information and comparison settings weight the user has inputted. It will also instantiate other classes as needed and call into the corresponding operations.</b>
</p>
<br>