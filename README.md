This is the forbidden island project of 2020 object programming course (L2 INFO 224 Paris-Saclay - Thibaut Balabonski).

The subject could be find at https://www.lri.fr/~blsk/POGL/IleInterdite.pdf

The design pattern used could be find at https://www.lri.fr/~blsk/POGL/Notes/Conway.html

Developers contact:
------------------
Maxime Vincent -> maxime.vincent1@u-psud.fr
Baptiste Maquet -> baptiste.maquet@u-psud.fr

Acknowledgements:
----------------
Maéva Vincent for beautiful players drawing !

Instructions to compile and execute:
-----------------------------------

0. Create a directory for the project

mkdir project
cd project

1. Clone the git repository
(Maybe you need to identify)

git clone https://gitlab.u-psud.fr/maxime.vincent1/forbiddenIsland.git

2. Create a bin directory

cd forbiddenIsland
mkdir bin

3. Compile the project
(The main class is Game.java)

javac -classpath src src/forbiddenIsland/Game.java -d bin

4. Execute the project

java -cp bin forbiddenIsland.Game

Instructions to run Junit tests:
-------------------------------

Junit tests have a graphic interface integrated with Eclipse IDE
So you need to open the project with Eclipse IDE and run the files
in forbiddenIsland/src/test