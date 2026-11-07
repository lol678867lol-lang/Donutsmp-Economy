package de.donutmod.fabric;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.GsonBuilder;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Einstellungen: /settings (pro Spieler: Geld-Anzeige, Money Name Tags; global: kurze Zahlen) und /mod (Mod-Items an/aus). */
public final class Settings {
    private Settings() {}

    /** Kurze Zahlen (k/m/b/t/q/qi) statt voller Zahl, z.B. 3587 -> 3.5k. */
    static boolean shortNumbers = true;
    /** Items anderer Mods verkaufbar und in /ah und Orders. */
    static boolean modItems = true;
    /** Pro Spieler: [0] = Geld-Anzeige (Sidebar), [1] = Money Name Tags (Geld unter dem Namen). */
    private static final Map<UUID, boolean[]> PLAYERS = new HashMap<>();

    private static boolean[] of(UUID u) { return PLAYERS.computeIfAbsent(u, k -> new boolean[]{true, true}); }

    static boolean hud(UUID u) { return of(u)[0]; }
    static boolean tags(UUID u) { return of(u)[1]; }
    static void setHud(UUID u, boolean v) { of(u)[0] = v; save(); }
    static void setTags(UUID u, boolean v) { of(u)[1] = v; save(); }
    static void setShort(boolean v) { shortNumbers = v; save(); }
    static void setMods(boolean v) { modItems = v; save(); }

    static java.nio.file.Path file() { return DonutMod.worldDir().resolve("donutmod-settings.json"); }

    static void load() {
        PLAYERS.clear();
        shortNumbers = true;
        modItems = true;
        try {
            if (!Files.exists(file())) return;
            JsonObject o = JsonParser.parseString(Files.readString(file())).getAsJsonObject();
            if (o.has("shortNumbers")) shortNumbers = o.get("shortNumbers").getAsBoolean();
            if (o.has("modItems")) modItems = o.get("modItems").getAsBoolean();
            if (o.has("players"))
                for (var e : o.getAsJsonObject("players").entrySet()) {
                    JsonObject v = e.getValue().getAsJsonObject();
                    PLAYERS.put(UUID.fromString(e.getKey()), new boolean[]{
                            !v.has("hud") || v.get("hud").getAsBoolean(), !v.has("tags") || v.get("tags").getAsBoolean()});
                }
        } catch (Exception e) { System.err.println("[DonutMod] Einstellungen laden fehlgeschlagen: " + e); }
    }

    static void save() {
        try {
            JsonObject o = new JsonObject();
            o.addProperty("shortNumbers", shortNumbers);
            o.addProperty("modItems", modItems);
            JsonObject pl = new JsonObject();
            for (var e : PLAYERS.entrySet()) {
                JsonObject v = new JsonObject();
                v.addProperty("hud", e.getValue()[0]);
                v.addProperty("tags", e.getValue()[1]);
                pl.add(e.getKey().toString(), v);
            }
            o.add("players", pl);
            DonutMod.writeSafe(file(), new GsonBuilder().setPrettyPrinting().create().toJson(o));
        } catch (Exception e) { System.err.println("[DonutMod] Einstellungen speichern fehlgeschlagen: " + e); }
    }
}
