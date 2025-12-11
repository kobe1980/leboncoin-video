
---

### `Claude.md`

```markdown
# Claude.md

Tu es Claude Code, assistant spécialisé Android/Kotlin.  
Ta mission est d’implémenter l’application décrite dans `plan.md`.

## Règles générales

1. **Toujours respecter `plan.md`**  
   - Avant toute modification, relis les sections pertinentes de `plan.md`.
   - Si une info n’est pas précisée, fais un choix raisonnable et simple, cohérent avec le reste du projet.

2. **Stack et architecture**
   - Kotlin + Jetpack Compose.
   - Architecture MVVM avec ViewModels & StateFlow.
   - Navigation Compose pour les écrans.
   - Retrofit + OkHttp pour l’API.
   - Coil pour les images.
   - ExoPlayer (Media3) pour la vidéo.
   - DI : utilise un seul framework (Hilt OU Koin), choisi au début et appliqué partout.

3. **Qualité du code**
   - Utilise la convention officielle Kotlin (nommage, organisation, immutabilité lorsque possible).
   - Factorise les composants UI réutilisables (`ui/components`).
   - Ajoute des `@Preview` pour les principaux Composables.
   - Évite la complexité inutile : pas de sur-architecture.

4. **Gestion d’état et erreurs**
   - Les ViewModels exposent un `StateFlow<UiState>` ou `MutableState` équivalent.
   - Gère explicitement :
     - `isLoading`
     - `error` (String? ou sealed class simple)
   - Ne laisse jamais l’UI dépendre directement de `Result` ou d’exceptions.

5. **APIs et données**
   - Implémente les interfaces d’API telles que décrites dans `plan.md`.
   - Fournis au besoin un `FakeListingRepository` qui renvoie des données locales hardcodées pour permettre le développement même sans backend.

6. **Vidéo & médias**
   - Emballe ExoPlayer dans un composant Compose dédié.
   - Gère correctement le cycle de vie : créer le player avec `remember` et le libérer dans `DisposableEffect`.
   - Ne multiplie pas les instances de players inutilement.

7. **UI & design**
   - Suis les maquettes fournies :
     - Couleurs dominantes : fond clair, orange pour les actions principales.
     - Coins arrondis (cartes, boutons).
     - Emphase sur prix & titres.
   - Garde le design responsive mais optimisé pour un écran mobile classique.

8. **Organisation des fichiers**
   - Respecte la structure de packages décrite dans `plan.md`.
   - Nomme les fichiers de façon claire :
     - `HomeScreen.kt`, `HomeViewModel.kt`
     - `ListingDetailScreen.kt`, `ListingDetailViewModel.kt`
     - `ListingRepositoryImpl.kt`, etc.

9. **Tests & maintenance**
   - Ajoute au moins quelques tests unitaires sur les ViewModels en mockant le repository.
   - Documente les points importants dans des commentaires concis.

10. **Interaction**
    - Si le plan est ambigu sur un point bloquant, commente clairement le choix que tu fais dans le code (ex. `// Hypothèse : ...`).

Ton objectif : produire une base de code propre, lisible et facilement extensible, qui compile et affiche les écrans principaux décrits dans `plan.md` avec de fausses données si nécessaire.
