# Earlier version (April 2026)

This is how the manga store looked in my first version, before I split it into folders and added the cart. It is only here to show how the project grew.

## What it has
- `login.fxml` and `LoginController`: the login window (the later version keeps the same idea).
- `manga.fxml` and `MangaController`: the catalog screen. The list of manga is written directly inside the controller (a 2D array of title, genre, price and image name) instead of being read from a json file.
- `GUI` and `Main`: start the app and open the login window.
- Everything lives in one package, `com.manga.store`.

## What it does not have yet
- No cart, no search or genre filter, no `manga.json`, no `Gson`.
- The cover images and the logo are not in the repo. The code looks for `naruto.png`, `chainsawman.png` and so on, and `login.fxml` and `manga.fxml` point to `logo_mangaworld_web.png`. Put your own images next to the fxml files, or remove the two `Image` lines.

## How to run
Same as the main README: JavaFX on the module path, then run `com.manga.store.Main`. It does not need Gson.

## Known issues
- The login is checked against values written in `LoginController`, like in the later version.
- The catalog data is hard-coded in `MangaController`.
