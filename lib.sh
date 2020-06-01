# Bibliothèque pour l'exécution, la compilation 
# et la création d'une archive jar pour l'application java

# Couleur de la sortie
begin="\033["
rouge="$begin""31""m"
jaune="$begin""33""m"
vert="$begin""32""m"
end="\033[0m"

# Lance l'application depuis l'archive jar
run_jar() {
   echo -e "$vert""Lancement de l'application jar""$end"
   java -jar forbiddenIsland.jar $@ 
}

# Lance l'application depuis les fichiers bytecode
run_bin() {
    echo -e "$vert""Lancement de l'application bytecode""$end"
    java -classpath bin forbiddenIsland.Game $@
}

# Compile l'application
compile() {
    echo -e "$jaune""Compilation de l'application""$end"
    mkdir bin
    javac -sourcepath src src/forbiddenIsland/Game.java -d bin && echo -e "$vert""Compilation réussie""$end"
}

# Crée l'archive jar
make_jar() {
    cd bin
    echo -e "$jaune""Création du MANIFEST.MF""$end"
    echo -e "Main-Class: forbiddenIsland.Game\n" > MANIFEST.MF
    echo -e "$jaune""Création de l'archive forbiddenIsland.jar""$end"
    jar cvfm forbiddenIsland.jar MANIFEST.MF . && echo -e "$vert""Création de l'archive réussie""$end"
    echo -e "$jaune""Déplacement de l'archive jar""$end"
    mv forbiddenIsland.jar ..
    echo -e "$jaune""Suppression du répertoire bin""$end"
    rm -rf ../bin
}
