# WANAGO

WANAGO is a mobile application developed as a group project for the ArchitectCoders course. The application connects with the Ticketmaster API to provide a list of events that users can save as favorites.

## Features

- Fetch and display events from the Ticketmaster API.
- Save favorite events for easy access.
- Detailed view of each event.

## Architecture

The application follows the MVVM (Model-View-ViewModel) architectural pattern and Clean Architecture principles. This ensures separation of concerns, making the codebase easier to navigate and maintain.

## Technologies

- **Hilt**: Used for dependency injection, Hilt simplifies the implementation of DI and improves the efficiency of the app by reusing instances of expensive classes.
- **Coroutines**: Used for managing background tasks, providing a simple and efficient way to manage concurrency in the application.
- **Retrofit**: A type-safe HTTP client used for network requests.
- **Room**: A persistence library providing an abstraction layer over SQLite, used for data storage.
- **Ticketmaster API**: The source of the events displayed in the app.

## Testing

The application includes a comprehensive suite of tests:

- **Unit Tests**: Used to test individual components in isolation.
- **Integration Tests**: Used to test the interaction between different components of the application.
- **UI Tests**: Used to test the user interface and the user interaction with the application.

## API Key Configuration

To compile and run the project, you'll need to add your Ticketmaster API key to the `local.properties` file. Follow these steps:

1. Open `local.properties` in the root directory.
2. Add your API key in the following format: `ticketMasterApiKey=[api_key]`.
3. Save the file and rebuild the project.
