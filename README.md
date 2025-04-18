# Github Users

> This Android application displays a list of GitHub users, their details and repositories.

## Getting Started

This project requires a GitHub Personal Access Token to work. Here's how to get started:

### Prerequisites

*   Android Studio (Meerkat)
*   GitHub Account

### Installation

1.  **Clone the project:** `git clone git@github.com:mknishad/github-users-android.git`
2.  **Open in Android Studio:** Open the cloned project in Android Studio.
3. **Gradle Sync:** Wait for Gradle to sync the project.
4. **Build**: Build the project.

### Configuration: `key.properties`

You need to create a `key.properties` file in the **root directory** of the project. This file stores your GitHub token.

1.  **Create `key.properties`:** Create a file named `key.properties` in the root folder of your project.
2.  **GitHub Token:**
    *   Go to your GitHub settings: [https://github.com/settings/tokens](https://github.com/settings/tokens)
    *   Generate a new token (classic).
    *   Give it a name and select the `repo` or `public_repo` scopes.
    *   Copy the token.
3.  **Add to `key.properties`:**
    *   Open `key.properties`.
    *   Add this line, replacing `<YOUR_GITHUB_TOKEN>`: API_KEY=<YOUR_GITHUB_ TOKEN>
4.  **Sync Project:** In Android Studio, sync your project with Gradle files.
5. **Run**: Run the application.

**Security:**

*   **Add `key.properties` to `.gitignore`** (do this to avoid accidentally committing the file).
*   **Keep your token secret!**

## Usage

[This Android application allows you to view GitHub users and their repositories. Here's a quick guide: The app finds GitHub users by username and opens to a list of users showing their avatar and username. Tapping a user allows you to see their details and repositories. To view a repository's web page if it's available, simply tap on it.]

## Built With

*   Kotlin
*   Android SDK
*   Retrofit
*   Hilt
*   ViewModel
*   Flow
*   Coroutine
*   Coil

## Contact

Monir Hossain - mknishad1@ymail.com