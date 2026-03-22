# BMNVButton - Jetpack Compose Button Library

A high-quality, reusable Jetpack Compose Button Library for Android, supporting multiple UI variants, custom styling, loading states, and a smooth Swipe-to-Action slider. Built with Material 3 compliance and native Dark Theme support.

## Features

- **Standard Button Variants**: Filled, Outlined, and Tonal.
- **Loading States**: Seamless transition to a loading indicator in all button types.
- **Customization**: Easily adjust colors, corner radius, icons, and positions.
- **Swipe-to-Action**: A "Swipe to Unlock" style slider button with interactive feedback.
- **Material 3 & Dark Theme**: Automatically adapts to your app's theme.

---
## 📱 Preview

<p align="center">
  <img src="https://raw.githubusercontent.com/Vashu78/BMNVButton/master/Screenshot_20260322_192343.png" width="300"/>
</p>

## 🎥 Demo

<p align="center">
  <video width="400" controls>
    <source src="https://raw.githubusercontent.com/Vashu78/BMNVButton/master/Screen_recording_20260322_192648.webm" type="video/webm">
  </video>
</p>

## Installation

### 1. Add the JitPack repository to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.Vashu78:BMNVButton:1.0.0")
}
```
*(Replace `Vashu78` with your actual GitHub username)*

---

## Usage

### 1. CustomButton

The `CustomButton` component is a unified button that supports multiple variants.

```kotlin
CustomButton(
    text = "Confirm Action",
    onClick = { /* handle click */ },
    variant = ButtonVariant.Filled,
    icon = Icons.Default.Check,
    isLoading = false
)
```

#### Variants:
- `ButtonVariant.Filled`: High-emphasis primary action.
- `ButtonVariant.Outlined`: Medium-emphasis with a customizable border.
- `ButtonVariant.Tonal`: Low-emphasis using secondary container colors.

### 2. SliderButton

A swipeable button perfect for critical actions like "Swipe to Pay" or "Slide to Unlock".

```kotlin
SliderButton(
    text = "Swipe to Confirm",
    onComplete = { /* trigger success action */ },
    handleIcon = Icons.Default.ArrowForward,
    handleColor = MaterialTheme.colorScheme.primary,
    isLoading = false
)
```

---

## Component API

### CustomButton Parameters

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `text` | `String` | - | The label displayed on the button. |
| `onClick` | `() -> Unit` | - | Lambda triggered when the button is clicked. |
| `variant` | `ButtonVariant` | `Filled` | `Filled`, `Outlined`, or `Tonal`. |
| `icon` | `ImageVector?` | `null` | Optional icon to display. |
| `iconPosition` | `IconPosition` | `Leading` | `Leading` or `Trailing`. |
| `backgroundColor`| `Color?` | `null` | Custom background color (overrides theme). |
| `contentColor` | `Color?` | `null` | Custom text/icon color. |
| `cornerRadius` | `Dp` | `8.dp` | Rounded corner radius. |
| `isLoading` | `Boolean` | `false` | Displays a loading spinner when true. |
| `enabled` | `Boolean` | `true` | Enables or disables interaction. |

### SliderButton Parameters

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `onComplete` | `() -> Unit` | - | Triggered when the slider reaches the end. |
| `text` | `String` | `"Swipe to unlock"`| Hint text inside the track. |
| `handleIcon` | `ImageVector?`| `null` | Icon displayed inside the sliding handle. |
| `handleColor` | `Color` | `primary` | Color of the sliding handle. |
| `backgroundColor`| `Color` | `surfaceVariant`| Color of the slider track. |
| `isLoading` | `Boolean` | `false` | Displays a centered spinner when true. |

---

## Preview Gallery

The library includes a `ComponentGalleryPreview` to visualize all components in both light and dark themes.

---

## License

```text
Copyright 2024 BMNV

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
