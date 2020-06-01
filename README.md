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

The music school of La Ville du Bois for the live
concert recordings !

Instructions to use bash launcher:
---------------------------------
WARNING: The bash launcher works only on a linux system
with specific requirements. Please don't move and don't
remove run.sh, makejar.sh and lib.sh.

0. Install OpenJDK

	sudo apt install default-jre

1. Set permissions of bash files

	chmod u+x run.sh makejar.sh

2. Execute the project
(Choose one way)

	i) ./run.sh
	ii) ./makejar.sh && ./run.sh
	
REMARK: You can use the -v option with
run.sh to get the console print.

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
	
REMARK: You can use the -v option
to get the console print.

Instructions to create and run a jar file:
-----------------------------------------
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

4. Create the manifest file

	cd bin
	echo -e "Main-Class: forbiddenIsland.Game\n" > MANIFEST.MF

5. Create the jar file

	jar cvfm forbiddenIsland.jar MANIFEST.MF .

6. Move the jar file in parent directory

	mv forbiddenIsland.jar ..

7. Execute the jar file

	java -jar forbiddenIsland.jar
	
REMARK: You can use the -v option
to get the console print.

WARNING: Notice that the jar file doesn't work if the assets
directory is not accessible from the current directory.

Instructions to run Junit tests:
-------------------------------
Junit tests have a graphic interface integrated with Eclipse IDE.
So you need to open the project with Eclipse IDE and run the files
in forbiddenIsland/src/test

Command-line options:
--------------------
-v --verbose: Enable console print
-ns --no-sound: Disable music player
