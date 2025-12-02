# BIHealthScan

Une application Android intelligente d’aide à l’analyse biologique utilisant **IA embarquée**, **TensorFlow Lite**, et une interface Material Design moderne. BIHealthScan offre une analyse locale, rapide et sécurisée, optimisée pour les smartphones ARM.

---

## 🚀 Fonctionnalités principales

* Analyse d’images biomédicales via **modèles IA embarqués**.
* Optimisation ARM : **NEON SIMD**, **AArch64**, **quantized INT8**.
* Gestion des patients, historique, et préférences locales.
* Interface Material Design 3 moderne.
* Fonctionne totalement hors ligne.

---

## 📱 Installation (APK)

Téléchargez la dernière version stable de l’application :

**→ Lien APK (à insérer)**

Installez ensuite le fichier depuis votre smartphone Android.

---

## 🎥 Démonstration Vidéo

Une courte vidéo montrant l’analyse en temps réel et les principales fonctionnalités.

**→ Lien vidéo (YouTube ou MP4) à insérer**

---

## 🏗️ Architecture Technique

```
┌───────────────────────────────────────────────┐
│                BIHEALTHSCAN APP               │
├───────────────────────────────────────────────┤
│ Presentation Layer │ Business Logic │ Data Layer │
│ • Fragments/UI     │ • AI Engine    │ • Local DB │
│ • Material 3        │ • Health Logic │ • SharedPrefs │
│ • CameraX           │ • Processing   │ • Filesystem  │
└─────────────┬───────────────────────────┬────────────┘
              │                           │
       ┌──────▼─────────┐         ┌────────▼────────┐
       │ TensorFlow Lite │         │ ARM Optimized    │
       │ • INT8 Models   │         │ • NEON SIMD      │
       │ • On-Device AI  │         │ • AArch64 Native │
       │ • Fast Runtime  │         │ • Cortex Optim.  │
       └─────────────────┘         └───────────────────┘
```

---

## 🖼️ Screenshots

Les captures sont automatiquement générées depuis le dossier `/documentation/screenshots/`.

<div align="center">

<!-- Images auto-générées -->

<img src="documentation/screenshots/screenshot1.png" width="320"> <img src="documentation/screenshots/screenshot2.png" width="320"><br> <img src="documentation/screenshots/screenshot3.png" width="320"> <img src="documentation/screenshots/screenshot4.png" width="320"><br> <img src="documentation/screenshots/screenshot5.png" width="320"> <img src="documentation/screenshots/screenshot6.png" width="320">

</div>

---

## 🛠️ Technologies utilisées

* **Android (Java)**
* **TensorFlow Lite** (modèles quantifiés + runtime optimisé)
* **Material Design 3 (M3)**
* **CameraX** pour capture d’images
* **Room / SharedPreferences**
* **Optimisations ARM NEON**

---

## 📦 Pré-requis développeurs

* Android Studio Flamingo ou plus récent
* SDK Android 34+
* JDK 17
* Gradle 8+

---

## 🔧 Build & Execution

```bash
./gradlew assembleDebug
```

APK généré dans :

```
app/build/outputs/apk/debug/
```

---

## 🤝 Contributeurs & Communauté

Projet open-source initié par **BesmaInfo**.

Vous pouvez :

* proposer des idées,
* contribuer au code,
* envoyer des modèles IA améliorés,
* rejoindre l’équipe sur lablab.ai.

---

## 📄 Licence

MIT – libre utilisation et modification.

---

## 📢 Contact

Développement & IA : **BesmaInfo / BCB Learning**

GitHub : [https://github.com/BCBLearning](https://github.com/BCBLearning)
