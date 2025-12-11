# plan.md

## 1. Contexte & objectifs

Application Android Kotlin, version simplifiée de leboncoin :

- Liste d’annonces avec images et vidéos (stockées sur un serveur distant).
- Section de vidéos en format **short** (type Instagram Reels/TikTok) sur l’écran d’accueil.
- Écran de détail d’annonce qui permet aussi de lire la vidéo associée.
- Les **assets UI** (icônes, couleurs, fonts, placeholders…) sont locaux dans l’app.
- Pas de login dans cette V1.

L’objectif de ce plan est de fournir à Claude Code toutes les informations nécessaires pour :

1. Générer le squelette du projet Android.
2. Implémenter les écrans, la navigation et la consommation des médias distants.
3. Garder le code propre, modulable et simple à étendre.

---

## 2. Stack technique

- **Langage** : Kotlin.
- **UI** : Jetpack Compose.
- **Navigation** : Navigation Compose.
- **Lecture vidéo** : ExoPlayer (Media3) intégré à Compose.
- **Chargement images** : Coil pour Compose.
- **HTTP** : Retrofit + OkHttp + kotlinx.serialization (ou Moshi, au choix mais être cohérent).
- **Gestion d’état** : ViewModel + StateFlow (architecture MVVM).
- **DI simplifiée** : Koin ou Hilt (au choix, mais préciser et rester cohérent dans tout le projet).
- **Build** :
  - Gradle Kotlin DSL.
  - minSdk ~ 24, targetSdk version récente.

---

## 3. Architecture & structure de modules

Monolithique pour la V1 mais architecture claire :

- Module unique `app` avec séparation par packages :

  - `data`
    - `remote` (API, DTOs, Retrofit service)
    - `repository`
  - `domain`
    - `model`
    - `repository` (interfaces)
    - `usecase` (si nécessaire, optionnel)
  - `ui`
    - `navigation`
    - `home`
    - `listingdetail`
    - `video`
    - `components`
    - `theme`
  - `core`
    - utilitaires (formatage prix, date, etc.)

Principe :  
UI → ViewModel → Repository → Remote API.

---

## 4. API & modèles de données

### 4.1. Hypothèse d’API

Les données viennent d’un serveur HTTP (qu’on pourra simuler avec un mock ou un JSON hebergé).  
Pour permettre à Claude d’implémenter l’app sans backend réel, définir une API REST simple :

- `GET /listings`  
  - Retourne :
    - Annonces mises en avant pour la section *Découvertes en vidéo* (avec vidéos).
    - Annonces recommandées (grille).
- `GET /listings/{id}`  
  - Retourne le détail complet d’une annonce.

Les réponses contiendront des URLs absolues vers les images et vidéos (ex: `https://media.example.com/images/123.jpg`).

### 4.2. Modèles domaine

#### `Listing`

```kotlin
data class Listing(
    val id: String,
    val title: String,
    val price: Int,
    val currency: String, // "EUR"
    val thumbnailUrl: String,
    val media: List<Media>,  // images & vidéos
    val location: String,    // "Paris 11e"
    val timeAgo: String,     // "2h", "Hier"
    val isNew: Boolean,
    val isGoodDeal: Boolean,
    val category: ListingCategory,
    val seller: Seller,
    val description: String,
    val attributes: List<ListingAttribute> // état, taille, marque, livraison dispo…
)
