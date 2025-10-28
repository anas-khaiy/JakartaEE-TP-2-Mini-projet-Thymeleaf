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


