# web-crawler
### pre-requisites
1> JDK 8
2> Maven 3.x
3> IDE (Eclipse)

### Steps to import the project
1> Clone the project onto your local from github using following url - https://github.com/anuj-java/web-crawler.git 
2> Launch eclipse and choose menu option File -> Import -> Existing Maven Projects and browse to pom.xml of cloned project
3> If workspace is configured with Java 8 and internet connection is available then required dependencies would be downloaded and project should build successfully.

### Build / Run the solution
1> The specific Use case of web crawler can be executed by running class InternalSiteWebCrawlerTest (under src/test/java) as junit test. Once the test case has been executed, refresh the workspace and you should be able to view the generated site map. Check the name of generated site map on console on completion of test case.

Alternatively, do maven clean build and this will run all the test cases for the application. 

### Approach and thinking while designing the solution

1>	Classes were designed keeping in mind that things that change for same reason are grouped together, keeping Single Responsibility Principle in mind.
2>	Solution has been designed keeping in mind that classes are closed for modification and open for extension. Any new behaviour or functionality to be added in future
can be accommodated without changing already tested existing code. (Open Closed Principle)
3> Code to interface.
4> Keep it simple.    
5> Solution has been supplemented with detailed Java docs and comments to explain the solution.
6> Solution is designed specifically for the given use case.