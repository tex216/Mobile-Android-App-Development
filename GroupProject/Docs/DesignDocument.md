# Design Document

**Author**: Team 109

## 1 Design Considerations

### 1.1 Assumptions

1. We are assuming there will only be 1 user.
1. The minimum API level for the app is API 28: Android 9.0 (Pie).
1. There is a single system running the application so no communication or saving between devices is necessary.
1. The user will interact with the application in a reasonable fashion e.g. the user will add a reasonable number of job offers and not 1 million.
1. The user has all the details associated with a job and cannot enter partial information.
1. The user does not have special needs or requirements e.g. ADA.

### 1.2 Constraints

1. The application must be lightweight in performance so it can run on an Android phone.
1. There is no external data storage and all data will be saved locally.


### 1.3 System Environment

1. The application will be deployed to an Android phone that has a compatible API level.
1. The data will be stored on Android SQLite database.
1. The application will be written in Java.

## 2 Architectural Design

### 2.1 Component Diagram v1
This is still our intended end state. The alpha/beta version of the application currently does not reflect this but we will be referring to this to refactor our application in the Transition phase.

![component diagram](./images/component_diagram.png)

We will have multiple UI components for controlling the user's interactions with the application. These components will use the Activity API provided by Android.
\
The Persistence infrastructure component will provide the Persistence interface that allows our application to read and write from and to the database. The database will be the Android SQLite database.
\
Since both current job and job offers share the same attributes, the "Enter or Edit Current Job Details" component and the "Enter Job Offers" component will both inherit from a base "Enter Job" component. These components collectively will provide the JobDetails interface which is the required by the Jobs component to persist to the database.
\
When the user chooses to compare jobs, the "Display Ranked List of Jobs" UI component will require the RankedList, which the Rank Jobs component provides. The Rank Jobs component requires Jobs and ComparisonSettings in order to calculate the score for each job. These are provided by the Jobs component and "Comparison Settings" component respectively and they will read these information from the database.
\
The "Display Ranked List of Jobs" component will provide the JobOffersToCompare interface to the "Compare Jobs" component based on what the user has selected. The "Compare Jobs" component then requires the Jobs interface provided by the Jobs component to get the details of the selected jobs and provide these information via the SelectedJobs interface. The "Display Selected Jobs for Comparison" component then displays the SelectedJobs to the user. 

### 2.2 Deployment Diagram v1
![deployment diagram](./images/deployment_diagram.png)

Our app is written in Java. It uses Android SDK tools to compile classes, resources and package the code along with any required data. Android depends on Linux OS for essential operating services such as security management, process management, network stack etc. The Linux kernel plays the role of an abstraction layer between the hardware and the software stack.
\
When the app is activated by the user, it is firstly initialized by creating a new project in Android Studio 3.0+ and created a JobOfferComparison.apk file. The MainActivity.java class is where the app is built and prompted to run with the help of an emulator. For the front end of the application, AndroidManifest.xml is created to outline the layout of the application’s user interface and describes the main characteristics of the project to express each of its modules. After the deployment in the Android 3.0+ execution environment, the results are finally displayed to the user that imported from the Android SQLite datebase by retriving respective metadata requested by the user.

## 3 Low-Level Design

### 3.1 Class Diagram v1
This is still our intended end state. The alpha/beta version of the application currently does not reflect this but we will be referring to this to refactor our application in the Transition phase.

![class diagram](./images/class_diagram.png)

## 4 User Interface Design v1
<section mockups>
    <table class="gui">
        <tr>
            <th>
                Requirements
            </th>
            <th>
                Mockups
            </th>
        </tr>
        <tr>
            <td class="description_width" width=350px>
                When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet)
            </td>
            <td class="image_width">
                <figure>
                    <img src="./images/main_menu_compare_enabled.png" alt="main_menu_compare_enabled" width=250px/>
                    <img src="./images/main_menu_compare_disabled.png" alt="main_menu_compare_disabled" width=250px/>
                </figure>
            </td>
        </tr>
        <tr>
            <td class="description_width" width=350px>
                When the user clicks on the ENTER/EDIT CURRENT JOB button, the user will be presented the Enter current job form.
            </td>
            <td class="image_width">
                <figure>
                    <img src="./images/enter_current_job_1.png" alt="enter_current_job_1" width=250px/>
                    <img src="./images/enter_current_job_2.png" alt="enter_current_job_2" width=250px/>
                </figure>
            </td>
        </tr>
            <tr>
            <td class="description_width" width=350px>
                If the user has previously entered a current job and is now editing it, the job details previously entered will be pre-populated.
            </td>
            <td class="image_width">
                <figure>
                    <img src="./images/edit_current_job_1.png" alt="edit_current_job_1" width=250px/>
                    <img src="./images/edit_current_job_2.png" alt="edit_current_job_2" width=250px/>
                </figure>
            </td>
        </tr>
        </tr>
            <tr>
            <td class="description_width" width=350px>
                When the user clicks on the ENTER JOB OFFERS button, the user will be presented the Enter job details form.
            </td>
            <td class="image_width">
                <figure>
                    <img src="./images/enter_job_offers_1.png" alt="enter_job_offers_1" width=250px/>
                    <img src="./images/enter_job_offers_2.png" alt="enter_job_offers_2" width=250px/>
                </figure>
            </td>
        </tr>
        </tr>
            <tr>
            <td class="description_width" width=350px>
                After the user enters the job offer details and clicks on the SAVE button, the user will be presented with the options to either ENTER ANOTHER OFFER, RETURN TO MAIN MENU or COMPARE WITH CURRENT JOB (if present). If the user clicks on the CANCEL button, the user will be taken back to the main menu.
            </td>
            <td class="image_width">
                <figure>
                    <img src="./images/after_entering_job_offers_compare_enabled.png" alt="after_entering_job_offers_compare_enabled" width=250px/>
                    <img src="./images/after_entering_job_offers_compare_disabled.png" alt="after_entering_job_offers_compare_disabled" width=250px/>
                </figure>
            </td>
        </tr>
        </tr>
            <tr>
            <td class="description_width" width=350px>
                When the user clicks on the ADJUST COMPARISON SETTINGS button, the user will be presented the Adjust comparison settings screen with the current weight pre-populated.
            </td>
            <td class="image_width">
                <figure>
                    <img src="./images/adjust_comparison_settings.png" alt="adjust_comparison_settings" width=250px/>
                </figure>
            </td>
        </tr>
        </tr>
            <tr>
            <td class="description_width" width=350px>
                When the user clicks on the COMPARE JOB OFFERS button, the user will be presented the Ranked list of jobs screen with the current job indicated and a list of jobs offers. The list is sorted in descending job score. The user can select two jobs to compare, selected jobs will have a checkmark next to it. The COMPARE button will only be clickable if the user has selected 2 jobs. The user will not be allowed to select more than 2 jobs.
            </td>
            <td class="image_width">
                <figure>
                    <img src="./images/ranked_list.png" alt="ranked_list" width=250px/>
                    <img src="./images/ranked_list_selected.png" alt="ranked_list_selected" width=250px/>
                </figure>
            </td>
        </tr>
        </tr>
            <tr>
            <td class="description_width" width=350px>
                After the user checks 2 jobs and click on the COMPARE button, the user will be presented with this screen. The table will display the job details for the selected jobs. The user will  have the option to MAKE ANOTHER COMPARISON or RETURN TO MAIN MENU.
            </td>
            <td class="image_width">
                <figure>
                    <img src="./images/compare_jobs_1.png" alt="compare_jobs_1" width=250px/>
                    <img src="./images/compare_jobs_2.png" alt="compare_jobs_2" width=250px/>
                </figure>
            </td>
        </tr>
        </tr>
            <tr>
            <td class="description_width" width=350px>
                User is required to provide all input fields correctly. An accompanying error mark will appear on the right hand side of the text field if it is invalid.
            </td>
            <td class="image_width">
                <figure>
                    <img src="./images/error_current_job.png" alt="error_current_job" width=250px/>
                    <img src="./images/error_enter_job_details.png" alt="error_enter_job_details" width=250px/>
                    <img src="./images/error_comparison_settings.png" alt="error_comparison_settings" width=250px/>
                    <img src="./images/error_all_weights_0.png" alt="error_all_weights_0" width=250px/>
                </figure>
            </td>
        </tr>
        </tr>
            <tr>
            <td class="description_width" width=350px>
                When user enters or edits current job or updates comparison settings weight, when we automatically navigate the user back to the main menu, we will also display a message briefly to notify the user the information has been successfully saved.
            </td>
            <td class="image_width">
                <figure>
                    <img src="./images/success_save_current_job.png" alt="success_save_current_job" width=250px/>
                    <img src="./images/success_save_comparison_settings.png" alt="success_save_comparison_settings" width=250px/>
                </figure>
            </td>
        </tr>
    </table>
</section>