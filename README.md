
<img src="https://developer.android.com/images/jetpack/compose-logo.svg" width="400" height="200" />

# Jetpack Compose UI Testing

A project that demonstrates how to write and run UI tests for a Jetpack Compose-based Android application.

## Overview

This project uses the latest tools and libraries from Google to help you write robust and efficient UI tests for your Jetpack Compose app.

## Getting Started

### Prerequisites

- Android Studio (version X.X or higher)
- JDK 8

### Clone the Repository

\`\`\`bash
git clone origin https://github.com/Kaku0217/JetpackCompose.git
\`\`\`

### Open in Android Studio

- Open Android Studio
- Click on "Open an existing Android Studio project" and select the cloned repository.

### Running the Tests

- Right-click on the test package in the Project Explorer.
- Click on "Run Tests."

## Writing Tests

We use the \`compose-test\` library to write UI tests for Jetpack Compose. Here's an example test that clicks on a button and verifies the text:

\`\`\`kotlin
@Test
fun exampleTest() {
    composeTestRule.setContent {
        MyComposeApp()
    }
    composeTestRule.onNodeWithText("Click me!").performClick()
    composeTestRule.onNodeWithText("Clicked!").assertExists()
}
\`\`\`

## Contributing

If you'd like to contribute, please fork the repository and use a feature branch. Pull requests are warmly welcome.

## License

Distributed under the MIT License. See \`LICENSE.md\` for more information.

## Contact

- GitHub: [your-username](https://github.com/your-username)
- Your Email: [youremail@example.com](mailto:youremail@example.com)

## Acknowledgments

- Google's Android team for creating Jetpack Compose
- Everyone who has contributed to this project
