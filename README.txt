Web-Crawler
The web crawler must take as an input a configuration file
you can look at the one in the package for example
The crawler runs for the time configured in his configuration file, and only save results when finish running.


INSTRUCTIONS:
1)make sure maven is installed and configured correctly
2)unzip web crawler zip file
3)cd to unzipped folder
3)mvn clean compile assembly:single
4)wait until build finish
5)cd target
6.0) make sure to configure inputfile.txt, you can look at the WebCrawler folder for the file
6)execute - java -jar WebCrawler-0.0.1-SNAPSHOT-jar-with-dependencies.jar "D:\WorkspaceJava\WebCrawler\InputFile.txt"