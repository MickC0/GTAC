# GTAC (Gère Ton Association Caritative)

GTAC est une application de gestion d'association caritative qui offre des fonctionnalités de gestion des bénévoles et de suivi des missions qu'ils réalisent.
Cette application vise à simplifier et à améliorer le processus de gestion des associations caritatives en offrant une plateforme centralisée pour gérer les ressources humaines et les activités de bénévolat.

## Fonctionnalités principales

- **Gestion des Bénévoles:** GTAC permet aux organisations caritatives de suivre les informations clés sur leurs bénévoles, notamment leurs coordonnées, leurs compétences, et leur disponibilité.

- **Gestion des Missions:** Les responsables d'association peuvent créer et gérer des missions spécifiques, en spécifiant les détails de la mission, les compétences requises, les dates et les emplacements.

- **Affectation des Bénévoles:** Les bénévoles peuvent être assignés à des missions en fonction de leurs compétences et de leur disponibilité.

## Installation

### Préalables à l'installation :
- Java 21 doit être installé sur votre machine.
- Postgresql doit être installé sur votre machine.

Pour installer GTAC sur votre système, suivez ces étapes :

1. Clonez ce dépôt sur votre ordinateur :

   ```
   git clone https://github.com/MickC0/GTAC.git
   ```
   
2. Configurez votre base de données. Installez PostgreSQL et lancer les scripts de création de la base et d'injection des données nécessaires au fonctionnement.
   ```
   create_db_gtac.sql
   create_user_gtac.sql
   insert_roles_gtac.sql
   ```
   Les scripts se trouvent dans le dossier "sql" à la racine du projet.


3. Lancez l'application en utilisant la commande depuis le terminal à la racine du projet:

   ```
   ./mvnw spring-boot:run
   ```
   L'application se lance sur l'adresse :
   ```
   http://localhost:8084/
   ```

## Configuration

Avant de démarrer GTAC, assurez-vous de configurer les variables d'environnement nécessaires. Créez un fichier `.env` à la racine du projet et définissez les valeurs appropriées pour les variables suivantes :

- `DATABASE_USERNAME`: nom d'utilisateur de connexion à votre base de données.
- `DATABASE_PASSWORD`: mot de passe de connexion à votre base de données.
- `DATABASE_URL`: URL de connexion à votre base de données.


## Contribuer

Nous accueillons les contributions de la communauté ! Si vous souhaitez contribuer à GTAC, veuillez suivre ces étapes :

1. Fork ce dépôt.
2. Créez une branche pour votre fonctionnalité ou votre correctif.
3. Faites vos modifications.
4. Soumettez une PR (Pull Request) avec une description détaillée de vos changements.

## Auteurs

- [MickC0](https://github.com/MickC0)
