FlappyBird:

  This project is my attempt at recreating FlappyBird in Java  

Controls
Press '2' to hop
Press '3' to restart level 
Press '4' to return to main menu

FlappyBird.java -  
Main program logic controlling gameboard and interfacing with all other objects and controllers

ScoreHandler.java - 
Reads, Writes, and Sorts score data to scores.txt file
Creates arraylist of score objects 

Score.java - 
Score object consisting of a (str)name and (int)score

Bird.java - 
Bird object consisting of X/Y positioning, height, width, and a simple physics engine

Scores.txt - 
File consisting of all recorded scores
Must be in the format of '[Score],[Name]'

Known Bugs:
1)Physics engine does not allow bird to hop high enough fast enough to reach some gaps
-Physics engine could be modified or pipe gaps could be spawned within a certain range of one another to ensure reachability