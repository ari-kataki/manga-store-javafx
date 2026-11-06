# Manga Store (JavaFX)

Small desktop manga shop made with JavaFX while learning Java GUI programming. There is a login screen, a catalog with search / genre filter / sorting, and a cart.

## Stack
- Java (written with JDK 25)
- JavaFX 26 (FXML for the screens)
- Gson to read the catalog from `manga.json`

## Structure
All code is under `src/main/com/manga/store`:

| Folder | What it has |
|---|---|
| `app` | `Main` and `GUI`, start the app and open the login screen |
| `auth` | `login.fxml` and `LoginController` |
| `manga` | `manga.fxml`, `MangaController` (catalog screen), `StoreItem` and `Manga` (model) |
| `cart` | `cart.fxml`, `CartController` and `Carrello` (the cart, static list) |
| `data` | `manga.json`, `FileManager` (reads the json) and `CatalogLoader` (loads it in a thread) |

## How to run
1. Download the JavaFX SDK and the Gson jar.
2. In IntelliJ mark `src` as the sources root, add the JavaFX `lib` folder and the Gson jar as libraries, and add the VM options
   `--module-path <path-to-javafx>/lib --add-modules javafx.controls,javafx.fxml`.
3. Run `main.com.manga.store.app.Main`.

Login: the credentials are hardcoded in the login code, look there for the demo user.

## Cover images
The manga cover images are not in this repo because they are not mine. See `src/main/com/manga/store/manga/photos/README.txt`. The app runs without them.

## What it shows about learning
- FXML + controllers, building the cards from code
- Inheritance and an abstract class (`StoreItem` / `Manga`)
- Reading JSON with Gson and loading it in a separate thread
- Italian and English names mixed together (`Carrello`, `aggiungi`, ...)

## Known issues
- The login is hardcoded in the source, it is only for the exercise, not real authentication.
- Prices are strings and the cart is a static list of `String[]`.
- The max quantity of 10 is only checked per click on the +/- buttons, so pressing "Add to Cart" several times can go over it.
- If a price in `manga.json` is not a number the app throws when sorting or totalling the cart.
- If the json cannot be read the error is only printed to the console and the catalog just shows "No manga found".
- The genre filter list is written in the controller, a new genre in the json would not be selectable.
- "Place order" only clears the cart, nothing is saved.
