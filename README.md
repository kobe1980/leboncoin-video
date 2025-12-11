# Leboncoin Video

Une application Android moderne inspirée de leboncoin avec support vidéo intégré, développée en Kotlin avec Jetpack Compose.

## 📱 Aperçu du projet

**Leboncoin Video** est une application mobile Android qui permet aux utilisateurs de consulter des annonces de vente avec un focus particulier sur le contenu vidéo. L'application propose :

- **Découvertes en vidéo** : Section dédiée aux annonces avec vidéos au format vertical (type Instagram Reels/TikTok)
- **Recommandations** : Grille d'annonces classiques avec images
- **Détail d'annonce** : Vue complète avec galerie média interactive et lecture vidéo intégrée

## 🏗️ Architecture technique

### Stack technologique
- **Langage** : Kotlin 100%
- **UI** : Jetpack Compose
- **Navigation** : Navigation Compose
- **Vidéo** : ExoPlayer (Media3)
- **Images** : Coil
- **HTTP** : Retrofit + OkHttp + kotlinx.serialization
- **Architecture** : MVVM avec StateFlow
- **Injection de dépendances** : Hilt
- **Build** : Gradle Kotlin DSL

### Architecture des modules
```
app/
├── data/
│   ├── remote/          # API, DTOs, Retrofit
│   └── repository/      # Implémentations repository
├── domain/
│   ├── model/          # Modèles métier
│   └── repository/     # Interfaces repository
├── ui/
│   ├── navigation/     # Navigation Compose
│   ├── home/          # Écran d'accueil
│   ├── listingdetail/ # Détail d'annonce
│   ├── video/         # Composants vidéo
│   ├── components/    # Composants réutilisables
│   └── theme/         # Thème UI
└── core/
    └── di/            # Injection de dépendances
```

## 🎯 Fonctionnalités implémentées

### ✅ Architecture de base
- [x] Configuration Gradle complète
- [x] Structure de packages selon Clean Architecture
- [x] Injection de dépendances avec Hilt
- [x] Thème UI aux couleurs leboncoin (orange + coins arrondis)

### ✅ Modèles de données
- [x] `Listing` : Modèle d'annonce complet
- [x] `Media` : Gestion images/vidéos avec enum `MediaType`
- [x] `Seller`, `ListingCategory`, `ListingAttribute`
- [x] DTOs et mappeurs pour l'API

### ✅ Couche données
- [x] Interface `ListingRepository`
- [x] `FakeListingRepository` avec données de test réalistes
- [x] Configuration Retrofit prête pour API réelle
- [x] Gestion d'erreurs avec `Result<T>`

### ✅ Interface utilisateur

#### Navigation
- [x] Bottom navigation bar à 4 onglets
- [x] Navigation Compose avec gestion état
- [x] Masquage bottom bar sur détail

#### Écran d'accueil (HomeScreen)
- [x] Section "Découvertes en vidéo" (scroll horizontal)
- [x] Section "Recommandations" (grille 2 colonnes)
- [x] États de chargement et erreur
- [x] Pull-to-refresh via retry

#### Écran de détail (ListingDetailScreen)
- [x] Galerie média interactive
- [x] Lecture vidéo avec ExoPlayer
- [x] Informations vendeur et annonce
- [x] Section caractéristiques
- [x] Design responsive

### ✅ Composants vidéo
- [x] `VideoPlayer` : ExoPlayer intégré à Compose
- [x] `VideoShortCard` : Format vertical pour découvertes
- [x] Gestion cycle de vie automatique
- [x] Mode répétition pour vidéos courtes

### ✅ ViewModels et état
- [x] `HomeViewModel` avec StateFlow
- [x] `ListingDetailViewModel` avec SavedStateHandle
- [x] Gestion états : loading, success, error
- [x] Isolation UI des exceptions

### ✅ Composants UI
- [x] `ListingCard` avec badges et indicateurs
- [x] `LoadingState` et `ErrorState` réutilisables
- [x] Previews Compose pour développement

## 🚧 À implémenter (V2)

### Écrans secondaires
- [ ] **SearchScreen** : Recherche d'annonces avec filtres
- [ ] **MessagesScreen** : Messagerie entre utilisateurs
- [ ] **ProfileScreen** : Profil utilisateur et paramètres

### Fonctionnalités avancées
- [ ] **Authentification** : Login/register utilisateur
- [ ] **Favoris** : Sauvegarde d'annonces
- [ ] **Notifications** : Messages et nouvelles annonces
- [ ] **Géolocalisation** : Annonces par proximité
- [ ] **Upload** : Création d'annonces avec vidéos

### Optimisations
- [ ] **Cache** : Mise en cache images/vidéos
- [ ] **Pagination** : Chargement par page
- [ ] **Recherche** : Moteur de recherche avancé
- [ ] **Analytics** : Tracking utilisation

### API réelle
- [ ] Remplacement de `FakeListingRepository`
- [ ] Authentification JWT
- [ ] Upload de médias
- [ ] WebSocket pour temps réel

## 🎨 Design et UX

L'application suit les principes de Material Design 3 avec une personnalisation aux couleurs leboncoin :

- **Couleur primaire** : Orange leboncoin (#EA6100)
- **Formes** : Coins arrondis sur cartes et boutons
- **Typographie** : System fonts avec hiérarchie claire
- **Navigation** : Bottom bar intuitive
- **Vidéos** : Interface type TikTok/Reels pour engagement

## 🛠️ Installation et développement

### Prérequis
- Android Studio Arctic Fox ou plus récent
- JDK 11+
- Android SDK API 24+

### Configuration
1. Cloner le repository
2. Ouvrir dans Android Studio
3. Sync Gradle dependencies
4. Lancer sur émulateur ou device

### Structure des données
L'application utilise actuellement `FakeListingRepository` qui génère :
- 3 annonces avec vidéos pour la section découvertes
- 6 annonces classiques pour les recommandations
- Images via Picsum Photos (placeholders)
- Vidéo de test : Big Buck Bunny (libre de droits)

## 📊 État du projet

**Version actuelle** : 1.0-alpha
**Statut** : ✅ MVP fonctionnel avec données de test
**Prochaine étape** : Intégration API backend

### Métriques de code
- **Architecture** : Clean Architecture + MVVM
- **Couverture** : Tests unitaires ViewModels à ajouter
- **Performance** : Optimisé pour scroll fluide + lecture vidéo
- **Sécurité** : Prêt pour authentification JWT

## 🤝 Contribution

Le projet suit les conventions Kotlin officielles :
- Code en anglais (sauf UI strings)
- Immutabilité privilégiée
- Separation of concerns stricte
- Previews Compose obligatoires

## 📝 Notes de développement

### Choix techniques justifiés
- **Hilt vs Koin** : Hilt choisi pour intégration AndroidX
- **StateFlow vs LiveData** : StateFlow pour Compose-first
- **FakeRepository** : Permet développement sans backend
- **ExoPlayer** : Standard Android pour vidéo performante

### Hypothèses prises
- Orientation portrait uniquement
- Pas d'autoplay vidéos (économie batterie)
- Cache images automatique via Coil
- Répétition vidéos courtes en boucle

---

*Développé avec ❤️ en Kotlin + Jetpack Compose*