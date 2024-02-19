README for AppyJourney

Overview:

This project implements a journey tracker app in Android using Jetpack Compose. It displays a list of stops, tracks progress, and allows users to switch between kilometers and miles.

Key Features:

    Stop List: Displays a list of stops with distances, highlighting the current stop.
    Progress Bar: Shows overall progress on the journey.
    Distance Information: Displays total distance, distance covered, and distance left.
    Unit Switching: Allows users to toggle between kilometers and miles.
    Column vs. LazyColumn: Demonstrates the difference in performance and behavior between Column and LazyColumn for displaying lists.
    Reset Functionality: Resets the progress to the starting point.

Code Structure:

    MainActivity: Entry point of the app, sets up Jetpack Compose UI.
    TrackerApp: Main composable, handles state and UI structure.
    TrackerTopBar: Top app bar with toggles and reset button.
    StopsColumn/StopsLazyColumn: Displays the list of stops using either Column or LazyColumn.
    StopItem: Composable for each stop item, highlighting the current stop.
    Distance: Composable displaying distance information.

Running the App:

    Open the project in Android Studio.
    Connect an Android device or start an emulator.
    Run the app.
