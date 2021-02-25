1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).

>To realize this requirement, at the beginning, I added 'Name' and 'Email' to the User class to track the current user’s information. The user's login information is validated by the 'verifyLogin() method'. Once the user login into the system, basic job information of the user, string 'Current job title', string 'Current job company', and int 'Job offers number' will be shown in the main GUI, which also can check if these information is missing or not. 
>The user can achieve the above-mentioned four functions in the main menu GUI using corresponding methods (buttons). (1) enter or edit current job details ('editCurrentJob()'), (2) enter job offers ('editJobOffers()'), (3) adjust the comparison settings ('adjustCompareSettings()'), or (4) compare job offers (disabled if no job offers were entered yet) ('compareJobOffers()').

2. When choosing to *enter current job details*, a user will: (a) Be shown a user interface to enter (if it is the first time) or edit all of the details of their current job. (b) Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

>Since the current job and job offers include the same required information and could be exchanged, I created a job entity to store the job information. The jobs entity will store all the job information, consisting of: string 'Title', string 'Company', string 'Location', int 'Cost of living in the location', money 'Yearly salary', money 'Yearly bonus', int 'Allowed weekly telework days',  percentage 'Retirement benefits', int 'Leave time'.
>To achieve this requirement, I also added a *Current Job* interface (GUI) which is inherent from the user class and is part of the *Jobs* entity. The user can enter or edit current job details through the *Current Job* interface using the 'editCurrentJob()' method and save the data to the job entity using the 'save()' method.  The user also can exit and return to the main menu GUI using 'exit()' method. If the user did not click the save button in the GUI and exit, the edited current job information will not be saved. The user also can cancel any current changes using the 'cancel()' method.  

3. When choosing to *enter job offers*, a user will: (a) Be shown a user interface to enter all of the details of the offer, which are the same ones listed above for the current job. (b) Be able to either save the job offer details or cancel. (c) Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

> To achieve this requirement, I created another interface *Job Offers* (GUI), it is also inherent from the user class and is part of the *Jobs* entity. In the *Job Offers* interface, the user can edit or enter job offer using the 'editJobOffer()' method, and save the information to the *Jobs* entity using the 'save()' method. The user is able to cancel editing by using the 'cancel()' method. The GUI also allows users to add another job offer through the 'addJobOffer()' method and 'exit()' to the main GUI.

4. When *adjusting the comparison settings*, the user can assign integer weights. If no weights are assigned, all factors are considered equal.

> To realize this requirement, I added a *Weights Settings* class in which int 'Weight of yearly salary', int 'Weight of yearly bonus', int 'Weight of allowed weekly telework days', int 'Weight of retirement benefits', and int 'Weight of leave time' can be set using the 'editWeights()' method. If there is no additional setting, the default weights for all factors are equal. The user also can save the weights settings by using the 'save()' method and return to the main GUI via the 'exit()' method.  

5. When choosing to *compare job offers*, a user will: (a) Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.  (b) Select two jobs to compare and trigger the comparison. (c) Be shown a table comparing the two jobs, displaying details, for each job. (d) Be offered to perform another comparison or go back to the main menu.

>To compare job offers, I added a *Compare Jobs* class which associate with *Current Job* and *Job Offers*. A list of job offers, including the current job, will be shown in the GUI by using 'showComparasionList()' method. In the class,  I added string 'Title', string 'Company', int 'Ranking', which will be displayed in the list. If the user want to further compare two jobs by selecting them in the list, details of those two jobs will be displayed in a table. This function can be achieved by using the 'compareTwoJobs()' method. The user also can return to the *Compare Jobs* GUI by using the 'return()' function.

7. When ranking jobs, a job’s score is computed as the weighted sum of:
AYS + AYB + (RBP * AYS) + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8)
>To realize this requirement, I added a ‘rankingCalculation()’ method in the Compare Jobs class. The detailed computation is not represented in my design, as it will be handled entirely within the ‘rankingCalculation()’ method. When calculating the jobs' scores, the weights information will be used from the *Weights Settings* class.   
8. The user interface must be intuitive and responsive.
> This is not represented in my design, since it will be implemented during GUI design and implementation. 
9. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).
> Assumed 

