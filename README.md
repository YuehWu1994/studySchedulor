# studySchedulor


## Description

   This is our project for HackUCI (UC Irvine) 2019. We initiated a final exam scheduling website for UCI computer science students through Java Database Connectivity (JDBC) and jQuery. To manipulate this interface, users would insert information relevant to their final exams (e.g. course name, professor, and final exam date) and when they are available to spend time studying. Then, our system would extract data from MySQL and propose a feasible study schedule based on factors such as level of difficulty of the class, professor rating, and desired grade.



## Directory
#### hackUCI/
- Main project file. It contains the java servlet code (*/src*), javascript and html frontend (*/Webcontent*), and MySQL command (*/sql*). This directory can be run in Eclipse by importing the existing Maven project.

#### python_scrapping/
- Scrape json and html information from *Rate My Professors*. The code is inspired and modified by [this link](https://github.com/Rodantny/Rate-My-Professor-Scraper-and-Search).

#### playground/
- Some testing code.


## Future Work

   This is the currently only the most basic version and we hope to make improvements in the following directions:
   #### Scalability
   - Currently, we only extract information related to the UCI CS department from *RateMyProfessor*. We hope this system could also become applicable for other departments the entire university, or even extend to use in multiple universities. 

   #### Reactive
   - At *Schedule your Study* page, we use form to let user insert available time, which is not quite intuitive and reactive. We hope to implement a **reactive calendar** such as [react-big-calendar](https://github.com/intljusticemission/react-big-calendar) that allows the users to drag and drop timeslot. Secondly, our system would only output console text to illustrate our proposed studying schedule due to time limitation on hackathon. We hope to use more decorative interface to demonstrate.

   #### Flexibility
   - Our studying scheduling system currently only allows for allocating a maximum of 20 time slots for 10 different days. Other planned boundary conditions and customized functionalities are not yet implemented, which make our system not as flexible as we wanted.