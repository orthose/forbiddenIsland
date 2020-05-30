#!/bin/bash

# Importation de la lib
source lib.sh

# Vérifications préalables
if [ ! -d assets ]; then
    echo -e "$rouge""Erreur: Le dossier assets est manquant""$end"; exit 1
fi

# Exécution et compilation
if [ -f forbiddenIsland.jar ]; then run_jar $1
elif [ -d bin ]; then run_bin $1
else compile; run_bin $1
fi
