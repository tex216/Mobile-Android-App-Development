# Extra Requirements

**Author**: Team 109

## 1. Accuracy
Our app should have accurate returned results for the user. For instance, after the user adjusts the comparison settings and presses the `COMPARE JOB OFFERS` button, our product should rank jobs and return a job list correctly. The ranking is done by calculating the job score based on the provided formula using the integer weight assigned by the user.

## 2. Usability
Our app should have a good user experience. It should include a concise and clear UI design, a reliable return result, a good interaction with the user's input and error handling. For example, the user will be presented with four clear options on the Main Menu of our app: `ENTER/EDIT CURRENT JOB`; `ENTER JOB OFFERS`; `ADJUST COMPARISON SETTINGS`; `COMPARE JOB OFFERS`.

## 3. Reusability
Our app should be reusable for a user, the database of our product should well-store all the information for the user, and it should respond and return the same result whenever a user reuses the product in the future. For instance, our app employed SQLite database in the Room environment that should keep all the record from the user's inputs and will be available anytime in response to a user's action.

## 4. Maintainability
Our app should be maintainable for developers. It should be accessible for developers to maintain the application including the user interface, functions, and the database, etc. For example, developers should be allowed to maximize the app's useful life and correct any defects and errors of the application in the first time when they occur. We added detailed design documents to help future developers under the code.

## 5. Extensibility
Our app should be extensible for the addition of new functionality or through modification of existing functionality and without impairing existing system functions. For instance, currently, the user is only allowed to compare two jobs, but more should be allowed if necessary via modifying the corresponding functions. 
