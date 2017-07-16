# Github API Project

An exercise project using Github API to display merge requests and accepting a merge request.

## Getting Started

It is a Maven Java console application. Main class is the entry point of the application.
Using the maven shaded plugin, the application is self contain.

At command line
 - mvn clean install
 - run the shaded jar from output
    For example: java -jar target/aoting-github-api-0.0.1-SNAPSHOT.jar
    
If running from Windows, please make sure it is run with Command Line (cmd.exe) as Java password input only works on native terminal.

This project demo the ability to show all/open/closed merge request to repository
Authorization to Github supports Basic HTTP Authorization for the time being. OAuth to be added later.

When running the application, it asks for Github account at the beginning. User can skip it and use my default repository and owner.

### Prerequisites

Java 8
Maven 3.3.9+

### Installing
Navigate to the root directory of this project
run at command line: mvn clean install

### Limitation
- Getting pull request support public repository on Github only as it does not need authorization.
- Merging a requeset asks for your credential and you must have write access to the branch being merged.

### Repository
https://github.com/aoting/github-api
