package com.minecolonies.core.client.render.gecko;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.api.util.Log;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExternalCitizenAssets
{
    private static final Gson GSON = new Gson();
    private static final String DEFAULT_MODEL = "minecolonies:geo/citizen/default_citizen.geo.json";
    private static final String DEFAULT_ANIMATION = "minecolonies:animations/citizen/default_citizen.animation.json";

    private static final Path PACK_ROOT = FMLPaths.GAMEDIR.get().resolve("resourcepacks").resolve("minecolonies_citizen_assets");
    private static final Path CONFIG_ROOT = FMLPaths.GAMEDIR.get().resolve("minecolonies").resolve("citizen_assets");
    private static final Path CONFIG = CONFIG_ROOT.resolve("citizen_rendering.json");

    private static ResourceLocation modelLocation = new ResourceLocation(DEFAULT_MODEL);
    private static ResourceLocation animationLocation = new ResourceLocation(DEFAULT_ANIMATION);
    private static String textureLocation = null;

    private static long nextReloadTick = 0L;

    private ExternalCitizenAssets() {}

    public static void initialize()
    {
        try
        {
            Files.createDirectories(CONFIG_ROOT);
            Files.createDirectories(PACK_ROOT.resolve("assets/minecolonies/geo/citizen"));
            Files.createDirectories(PACK_ROOT.resolve("assets/minecolonies/animations/citizen"));
            Files.createDirectories(PACK_ROOT.resolve("assets/minecolonies/textures/entity/citizen/custom"));

            Files.writeString(PACK_ROOT.resolve("pack.mcmeta"), packMeta(), StandardCharsets.UTF_8);
            Files.writeString(PACK_ROOT.resolve("README.txt"), packReadme(), StandardCharsets.UTF_8);

            if (Files.notExists(CONFIG))
            {
                Files.writeString(CONFIG, defaultConfig(), StandardCharsets.UTF_8);
            }
        }
        catch (final IOException e)
        {
            Log.getLogger().error("Failed to initialize external citizen assets folder", e);
        }
    }

    public static void reloadIfRequired()
    {
        final long now = System.currentTimeMillis();
        if (now < nextReloadTick)
        {
            return;
        }

        nextReloadTick = now + 5_000L;
        loadConfig();
    }

    private static void loadConfig()
    {
        modelLocation = new ResourceLocation(DEFAULT_MODEL);
        animationLocation = new ResourceLocation(DEFAULT_ANIMATION);
        textureLocation = null;

        if (Files.notExists(CONFIG))
        {
            return;
        }

        try
        {
            final JsonObject root = GSON.fromJson(Files.readString(CONFIG), JsonObject.class);
            if (root == null || !root.has("enabled") || !root.get("enabled").getAsBoolean())
            {
                return;
            }

            if (root.has("model"))
            {
                modelLocation = new ResourceLocation(root.get("model").getAsString());
            }
            if (root.has("animation"))
            {
                animationLocation = new ResourceLocation(root.get("animation").getAsString());
            }
            if (root.has("texture"))
            {
                textureLocation = root.get("texture").getAsString();
            }
        }
        catch (final Exception e)
        {
            Log.getLogger().error("Failed reading external citizen GeckoLib config", e);
        }
    }

    public static ResourceLocation getModelLocation()
    {
        return modelLocation;
    }

    public static ResourceLocation getAnimationLocation()
    {
        return animationLocation;
    }

    public static ResourceLocation getTextureLocation(final AbstractEntityCitizen citizen)
    {
        if (textureLocation == null || textureLocation.isBlank())
        {
            return citizen.getTexture();
        }

        return new ResourceLocation(textureLocation);
    }

    private static String defaultConfig()
    {
        return """
            {
              "enabled": false,
              "model": "minecolonies:geo/citizen/custom/citizen.geo.json",
              "texture": "minecolonies:textures/entity/citizen/custom/citizen.png",
              "animation": "minecolonies:animations/citizen/custom/citizen.animation.json"
            }
            """;
    }

    private static String packMeta()
    {
        return """
            {
              "pack": {
                "pack_format": 15,
                "description": "MineColonies external GeckoLib citizen assets"
              }
            }
            """;
    }

    private static String packReadme()
    {
        return """
            Put custom resources in this resourcepack and enable it in Minecraft:
            - assets/minecolonies/geo/citizen/custom/citizen.geo.json
            - assets/minecolonies/animations/citizen/custom/citizen.animation.json
            - assets/minecolonies/textures/entity/citizen/custom/citizen.png
            """;
    }
}
