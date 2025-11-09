# 1. Description du projet

Contexte fonctionnel :
- Ce projet s'inscrit dans le domaine de la gestion des ressources humaines d’une entreprise ou d’une organisation. Il permet de suivre et d’administrer les demandes de congé des employés, en intégrant les informations sur les employés, les types de congé et le suivi statistique des demandes.

Objectif de l'application :
- Fournir un outil digital simple et efficace pour gérer, filtrer et visualiser les demandes de congé des employés, tout en offrant des statistiques pour le suivi et la planification.

Public cible / cas d'usage :
L’application s’adresse principalement aux responsables RH, managers et employés. Les cas d’usage incluent :
- Les employés peuvent soumettre leurs demandes de congé.
- Les managers peuvent valider, refuser ou annuler des demandes.
- Les RH peuvent suivre les congés par département, type de congé ou période et générer des statistiques.

Ce que l'application permet concrètement :
- Permet de gérer les demandes de congé des employés et de visualiser leurs statistiques de manière intuitive.

# 2. Architecture technique
## 2.1 Stack technologique
- Backend :
Spring Boot 3.5.7
Spring Data JPA / Hibernate pour la gestion des entités et des requêtes SQL.

- Frontend :
Thymeleaf pour le rendu côté serveur des pages HTML.
HTML / CSS / Bootstrap 5 pour le design et la mise en page réactive.
Chart.js pour les graphiques et visualisations statistiques.

- Base de données : MySQL (8.0).

- Build / Gestion du projet :
Maven pour la gestion des dépendances et le packaging de l’application.
Configuration Spring Boot via application.properties.


## 2.2 Structure du code
- `entity/` : DemandeConge - Employe - EmployeTypeCongePK - TypeConge > classes JPA.
- `repository/`:  DemandeCongeRepository - EmployeRepository - TypeCongeRepository > interfaces d'accès aux données.
- `service/` : -.
- `controller/` : ChartsController - DemandeCongeController - EmployeController - TypeCongeController > contrôleurs web MVC.
- `templates/` : vues Thymeleaf (html).
- `static/` : CSS, JS, images.


## 2.3 Diagramme d’architecture 
- Flux : navigateur → contrôleur Spring → service → repository → base de données → retour vue Thymeleaf.
<img width="2460" height="312" alt="image" src="https://github.com/user-attachments/assets/ff7f73b0-4553-478a-bb61-8083ba2e1e39" />

## 3. Fonctionnalités principales

- CRUD sur les entités principales
Employé : création, modification, suppression, consultation des informations (nom, département, date d’embauche).
Type de congé : gestion des types de congé avec quota annuel et libellé.
Demande de congé : ajout, modification, suppression, consultation des demandes avec tous les détails (date_debut, date_fin, type de congé, motif, statut, employé).
Recherche / filtrage

- Filtrage des demandes par :
Département de l’employé
Type de congé
Dates de début et fin
Statut de la demande (EN_ATTENTE, ACCEPTEE, REFUSEE, ANNULEE)

- Tableau de bord / statistiques
Jours de congé consommés par département : graphique en barres.
Répartition des demandes par statut : graphique circulaire (pie chart).
Demandes par type de congé : graphique doughnut.
Évolution des demandes par mois : graphique en ligne (line chart).

- Gestion des statuts
Modification automatique ou manuelle du statut des demandes (EN_ATTENTE, ACCEPTEE, REFUSEE, ANNULEE).
Les statistiques et les filtres tiennent compte du statut actuel.

# 4. Modèle de données
## 4.1 Entités 
### Employe :
- id : identifiant unique de l’employé
- nom : nom complet de l’employé
- departement : département d’affectation
- date_embauche : date d’entrée dans l’entreprise

### TypeConge
- id : identifiant unique du type de congé
- libelle : nom du type de congé (ex. : Congé annuel, Maladie, Maternité...)
- quota_annuel : nombre maximal de jours autorisés.

### DemandeConge
- id : identifiant unique de la demande
- date_debut : date de début du congé
- date_fin : date de fin du congé
- motif : raison du congé
- statut : état actuel de la demande (EN_ATTENTE, ACCEPTEE, REFUSEE, ANNULEE)


## 4.2 Relations
- les relations (`@OneToMany`, `@ManyToOne`, `@ManyToMany`) : 
- Employe / DemandeConge : @ManyToOne => Un employé peut faire plusieurs demandes de congé + Chaque demande de congé appartient à un seul employé.
- TypeConge / DemandeConge :  @ManyToOne => Un type de congé (ex. congé annuel) peut être utilisé dans plusieurs demandes + Chaque demande de congé est liée à un seul type de congé.
- Schéma ER : Employe (1) ────< (N) DemandeConge (N) >──── (1) TypeConge
- Modél relationnel
<img width="2202" height="822" alt="image" src="https://github.com/user-attachments/assets/5a66ce2d-935b-4b16-b8a4-aeb5f187f864" />

## 4.3 Configuration base de données
- URL de connexion.
  pring.datasource.url = jdbc:mysql://localhost:3306/ProjetThymeleaf1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
- Identifiants / mot de passe (pour les tests).
  spring.datasource.username = root
  spring.datasource.password =
- Stratégie de génération des tables (`spring.jpa.hibernate.ddl-auto`).
  spring.jpa.hibernate.ddl-auto = update

# 5. Lancer le projet
## 5.1 Prérequis
- Java version requise (21).
- Maven(Maven 3.9.11).

## 5.2 Installation
- Cloner le dépôt.
- Configurer `application.properties`.
- Lancer l'application (`mvn spring-boot:run` ou exécuter la classe main).

## 5.3 Accès
- URL d'accès à l'application http://localhost:8080/.
- URL du tableau de bord / statistiques (http://localhost:8080/charts).

# 6. Démonstration (Vidéo)
https://www.youtube.com/watch?v=I2vzgu6-Cqs


# 7. Auteurs / Encadrement
- Anas Khaiy
- Encadrant : Dr. Mohamed LACHGAR / module : Développement Web et multiplateforme / établissement : ENS Marrakech.

























