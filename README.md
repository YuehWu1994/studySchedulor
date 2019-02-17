# studySchedulor


## Description

   This is the project for HackUCI (UC, Irvine) 2019. We initiated a final exam scheduling website for UCI computer science student throught Java Database Connectivity (JDBC) and jQuery. To manipulate this interface, users would insert related information related to final exam (ex: Course name, Professor name and Final exam date) and available time to study. Then, our system would extract data from MySQL and propose feasible study schedule based on factors such as level of difficult, rating of professor and expected grade of users.



## Directory
#### hackUCI/
- Main project file. It contains java servlet code (*/src*), javascript and html frontend (*/Webcontent*), and MySQL command (*/sql*). This directory is runnable at Eclipse by importing existing Maven project.

#### python_scrapping/
- Scrape json and html information from *Rate My Professors*. The code is inspired and modified by [this link](https://github.com/Rodantny/Rate-My-Professor-Scraper-and-Search).

#### playground/
- Some testing code


## Future Work

   This is the basic version and we hope to extend based on following directions.
   #### Scalability
   - Curretly, we only extract information related to UCI CS department from *Rate My Professors*. We hope this system could also become applicable for other departments or schools.

   #### Reactive
   - At *Schedule your Study* page, we use form to let user insert available time, which is not quite intuitive and reactive. We hope to implement a **reactive calendar** such as [react-big-calendar](https://github.com/intljusticemission/react-big-calendar) that allows the users to drag and drop timeslot. Secondly, our system would only output console text to illustrate our proposed studying schedule due to time limitation on hackathon. We hope to use more decorative interface to demonstrate.

   #### Flexibility
   - Our studying scheduling system currently only allow allocating 20 time slots for 10 different days, and some other boundary conditions and customized functionalities are not implemented, which make our system not quite flexible.