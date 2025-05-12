# Csv

## Description

Ce projet implémente un moteur de recherche de noms avec prétraitement phonétique et simple, et utilise un algorithme de **dédédoublonnage** et **comparaison Levenshtein**. Il inclut également la lecture de fichiers CSV, le prétraitement des noms et la recherche sur une base de données de noms.

### Fonctionnalités principales :
- Prétraitement phonétique et simple des noms.
- Déduplication de la base de données de noms.
- Recherche de noms avec des scores de similarité.
- Comparaison des noms en utilisant l'algorithme Levenshtein.

---

## Prérequis

Avant de commencer, assure-toi d'avoir installé les éléments suivants sur ton système :

- **JDK 8 ou supérieur** pour compiler et exécuter le code Java.
- Un outil de build comme **Maven** ou **Gradle** si tu préfères une gestion plus avancée des dépendances et des builds.

### Installation du JDK

1. Télécharge et installe le JDK depuis [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) ou utilise une version open-source comme [OpenJDK](https://openjdk.java.net/install/).

2. Vérifie l'installation avec la commande suivante :
   ```bash
   java -version

