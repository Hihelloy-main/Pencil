package org.bukkit.craftbukkit.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.SharedConstants;
import org.bukkit.Bukkit;

public final class Versioning {
    private static final String BUKKIT_VERSION;
    private static final String API_VERSION;

    static {
        String bukkitVersion = "Unknown-Version";
        String apiVersion = null;
        try (final InputStream stream = Bukkit.class.getClassLoader().getResourceAsStream("apiVersioning.json")) {
            if (stream == null) {
                throw new IOException("apiVersioning.json not found in classpath");
            }

            final JsonObject jsonObject = new Gson()
                .fromJson(new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)), JsonObject.class);

            if (jsonObject == null) {
                throw new IOException("apiVersioning.json is not a valid JSON file");
            }

            final JsonElement versionEl = jsonObject.get("version");
            final JsonElement apiEl = jsonObject.get("currentApiVersion");

            if (versionEl != null && !versionEl.isJsonNull()) {
                bukkitVersion = versionEl.getAsString();
            } else {
                throw new IOException("Missing 'version' in apiVersioning.json");
            }

            if (apiEl != null && !apiEl.isJsonNull()) {
                apiVersion = apiEl.getAsString();
            }
        } catch (final IOException ex) {
            Logger.getLogger(Versioning.class.getName()).log(Level.SEVERE, "Could not get Bukkit version!", ex);
        }
        BUKKIT_VERSION = bukkitVersion;
        if (apiVersion == null) {
            apiVersion = SharedConstants.getCurrentVersion().id();
        }
        API_VERSION = apiVersion;
    }

    public static String getBukkitVersion() {
        return BUKKIT_VERSION;
    }

    public static String getCurrentApiVersion() {
        return API_VERSION;
    }
}
