package com.deco2800.game.components.maingame;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.deco2800.game.components.Component;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


public class OpenKeyBinds extends Component {

    public class KeyBind {
        public String key;
        public String description;
        public String image;

        public KeyBind(String in_key, String in_desc, String in_image) {
            key = in_key;
            description = in_desc;
            image = in_image;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(OpenKeyBinds.class);
    private static JsonReader json;
    private static ArrayList<KeyBind> keys = new ArrayList<>();
    private static String[] keyTextures;
    //private static String[] backgroundTextures = {"images/KeyBinds/blank.png"};

    public OpenKeyBinds() {

        ResourceService resourceService = ServiceLocator.getResourceService();
        ArrayList<String> keyTexturesArray = new ArrayList<>();
        KeyBind dummy;
        String image, key, description;

        json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.getFileHandle("configs/keybinds.json", Files.FileType.Local));

        for (JsonValue component : base.get("Keys")) {

            description = component.getString("Description");
            if (!description.isEmpty()) { /* If description is empty, key is unbound, we don't want it */
                /* Get the JSON values we want */
                image = component.getString("Image");
                key = component.getString("Key");

                /* Create a keybinding entry */
                dummy = new KeyBind(key, description, image);
                keys.add(dummy);
                logger.info(String.format("%s key bound to action %s", component.getString("Key"),
                        component.getString("Description")));

                /* Add the key texture to be loaded */
                keyTexturesArray.add(image);
            }
        }

        /* Load our textures! */
        keyTextures = new String[keyTexturesArray.size()];
        keyTextures = keyTexturesArray.toArray(keyTextures);
        logger.debug("Loading keybinding key assets");
        resourceService.loadTextures(keyTextures);
        //resourceService.loadTextures(backgroundTextures);
    }

    /**
     * Unload the keybinding key image assets.
     */
    public void unloadKeyBindAssets() {
        logger.debug("Unloading keybinding key assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(keyTextures);
        //resourceService.unloadAssets(backgroundTextures);
    }
}
