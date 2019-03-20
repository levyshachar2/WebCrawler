Web-Crawler
The web webcrawler.crawler must take as an input a configuration file
An example configuration file exist in the repository.
The webcrawler.crawler runs for the time configured in his configuration file, and only save results when finish running.


INSTRUCTIONS:
1)make sure maven is installed and configured correctly
2)unzip web webcrawler.crawler zip file
3)cd to unzipped folder
3)mvn clean compile assembly:single
4)cd target
5.0) make sure to configure inputfile.txt, you can look at the WebCrawler folder for the file
5)execute - java -jar WebCrawler-0.0.1-SNAPSHOT-jar-with-dependencies.jar "..\InputFile.txt"
