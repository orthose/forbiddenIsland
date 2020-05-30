#!/bin/bash

# Importation de la lib
source lib.sh

# Vérifications préalables
if [ ! -d assets ]; then
    echo -e "$rouge""Erreur: Le dossier assets est manquant""$end"; exit 1
fi

# Création de l'archive jar
if [ -d bin ]; then make_jar
else
    compile; make_jar
fi
