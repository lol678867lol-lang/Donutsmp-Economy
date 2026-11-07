DonutMod - dein Projekt (Mojang-Mappings, 1.21.11) mit den neuen Funktionen

NEU
- Geld-Anzeige rechts in der Mitte: Donutsmp (oben) / Geld / Economy (unten)
- Zahlen ueberall als k/m/b/t/q/qi (3587 -> 3.5k), in /settings ein- und ausschaltbar
- /settings: Kurze Zahlen, Geld-Anzeige, Money Name Tags (Geld unter dem Namen)
- /mod on|off und /opsettings (nur OP): Items anderer Mods verkaufbar / in /ah und Orders
- Preise aus Rezepten (Zutaten + 10 %, nie billiger als das Ausgangsmaterial), auch fuer Mod-Items
  -> beim ersten Start entsteht config/donutmod-prices-v8.json (die alte v7-Datei wird nicht mehr benutzt)
- Easter Egg: Schild "WerIstDas88" bei X=8888, Z=8888 (Overworld)

GEAENDERT: DonutMod.java, Gui.java, OrderBuilder.java, PriceFile.java
NEU:       Settings.java, RecipeGraph.java, RecipeLoader.java, Easter.java
Alles andere ist unveraendert aus deiner ZIP (inkl. build.gradle, fabric.mod.json, donutmod.accesswidener).

HOCHLADEN
1. ZIP entpacken, alle Dateien in dein GitHub-Repository hochladen (vorhandene ersetzen).
2. .github/workflows/build.yml: Upload-Pfad ist jetzt build/libs/*.jar (nimmt jede Jar).
3. Der Ordner "nur-falls-fehlt" enthaelt gradle.properties und settings.gradle - nur noetig, wenn sie in deinem Repository fehlen
   (diese Dateien dann ins Hauptverzeichnis legen, nicht im Ordner lassen).
