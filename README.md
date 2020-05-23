# Projet de RT0805 

Répertoire Git du projet de RT0805 

## Auteurs

Ce projet à été créé par :
- DE SOUSA Luciano / luciano.de-sousa@etudiant.univ-reims.fr
- HOQUIGNY Danyel / danyel.hocquigny@etudiant.univ-reims.fr

## Description du projet

Ceci est une application de tracking sportif, chaque utilisateur dispose d'un terminal qui lui permet, lorsque il commence une activté, de sélectionner ce qu'il veut faire et envoyer au serveur son parcours. 
L'utilsateur pourra ensuite allez voir ses performances sur le site web.
Un système d'administration est aussi présent, attention aux petits malins.

## Prérequis

Vous devez avoir installé les paquets suivants :
- openjdk-11-jdk
- maven

Et devez disposer de :
- Un ordinateur
- Un navigateur graphique
- Un terminal pour lancer les programmes
- Le port 8080 disponible

Si jamais il n'était pas possible de libérer le port 8080, rendez vous dans RT0805/pom.xml et cherchez les lignes
 
<httpConnector>
    <port>8080</port>
</httpConnector>

en bas du fichier, changez alors le port pour un qui sera disponible.

## Les composants de l'application

En téléchargeant le projet, vous trouverez deux dossiers :

**Dossier RT0805**

Ce dossier contient la partie Web du projet, c'est à dire le Visualisateur et le Serveur

**Dossier RT0805_terminal**

Ce dossier contient quant à lui le programme client de l'utilisateur à partir duquel il pourra effectuer son activité.


Un autre dossier est important dans le projet 

**Dossier RT0805/donnees**

Ce dossier contient trois JSON, c'est grâce à eux que va fonctionner la partie Web
Attention de ne toucher qu'avec les yeux.



Le tout fonctionne sous:

- Jakarta EE 8
- Jetty


## Lancement de l'application

### Partie Web
Pour lancer la partie web, placez vous d'abord dans son dossier (RT0805) et avant le premier lancement, faites :
> mvn compile package

Ensuite pour lancer faire tourner le tout sous Jetty, faites :
> mvn jetty:run

### Terminal
Pour lancer le terminal, placez vous d'abord dans le dossier du terminal (RT0805_terminal) et avant le premier lancement, faites :
> mvn compile package

Ensuite pour lancer le serveur, faites :
> mvn exec:java


## Comment l'utiliser ?

Vous pouvez vous rendre sur le site web en vous rendant sur localhost:8080 (ou le port que vous avez choisi), mais cela n'est pas utile tant que vous n'avez pas rentré d'activité, puisque c'est le but principal du site.

Pour se faire, lancez le terminal et suivez les instructions, il vous demandera en premier lieu de vous inscrire, puisque vous n'avez pas de compte, vous pourrez ensuite simuler une activité 

Pour voir votre activité, rendez vous sur le site Web, connectez vous avec le compte précédemment créé, et laissez vous guider par le site pour voir votre merveilleuse activité !
