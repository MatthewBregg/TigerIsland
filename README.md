# TigerIsland

## Team Google Drive
https://drive.google.com/drive/folders/0B2V_ssWNgrvHQ1BDUzUtNTUxZlU


To run the server:

Clone the repo


Download this:

https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.16.1.jar


Add it to your project structure:

http://stackoverflow.com/questions/1051640/correct-way-to-add-external-jars-lib-jar-to-an-intellij-idea-project

Set the scope as compile.

There are two things that are may be run to test your game. One of them is to create the scoreboard. It is optional. The other is the server itelf (which is required.) You can opt to just run the server. These are the steps:


1) Optional:Creating the scorboard. Right click on ScoreBoarMain.java and run it. This will create tigersssss.db in your working directory. The server writes to this database. Open up ScoreBoard.html in your working directory to view the scores. If you are re-running things, delete tigersssss.db and ScoreBoard.html before running ScoreBoarddMain.


2) Required: Run the server

To do this, create a run configuration for the RunTournamentServer.java. You can create it like this from the run/debug config menu:

![alt tag](https://github.com/MatthewBregg/TigerIsland/blob/master/ServerRunConfiguration.png)

___
## Server Run Configuration Parameters:

*Format:*

**challenges** || **tournaments** || **password path** || **time** **delay** || **port** || **seed for deck**

*Example:*

3 heygang passwords.txt 20 6969 123456789

*Note*
***Run it from the run menu.***
___



















