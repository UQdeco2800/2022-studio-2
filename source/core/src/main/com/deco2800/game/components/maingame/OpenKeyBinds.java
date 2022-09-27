package com.deco2800.game.components.maingame;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.PlayerModifier;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;


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
    private static final int numKeysPerPage = 8;
    private static JsonReader json;
    private static ArrayList<KeyBind> keys = new ArrayList<>();
    private static String[] keyTextures;
    private static int numKeys;

    // Reading left to right of this LUT is equates to reading top to bottom, then left to right
    // This is the LUT for the pure keybinding textures
//    public final int[][] keyTexturePosLUT = {{408, 306}, {408, 420}, {408, 534}, {408, 648},
//            {948, 306}, {948, 420}, {948, 534}, {948, 648}};

    // This is the LUT for the FULLSCREEN keybind textures
    public static final int[][] keyTexturePosLUT = {{408 - 918, 306 - 120}, {408 - 918, 420- 120}, {408 - 918, 534- 120}, {408 - 918, 648- 120},
            {948- 378, 306- 120}, {948- 378, 420- 120}, {948- 378, 534- 120}, {948- 378, 648- 120}};

    public OpenKeyBinds() {

        ResourceService resourceService = ServiceLocator.getResourceService();
        ArrayList<String> keyTexturesArray = new ArrayList<>();
        KeyBind dummy;
        String image, key, description;
        numKeys = 0;

        json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.getFileHandle("configs/keybinds.json", Files.FileType.Local));

        for (JsonValue component : base.get("Keys")) {

            description = component.getString("Description");
            if (!description.isEmpty()) { /* If description is empty, key is unbound, we don't want it */
                /* Get the JSON values we want */
                image = component.getString("Image");
                key = component.getString("Key");

                /* Create a keybinding entry */
                numKeys++;
                dummy = new KeyBind(key, description, image);
                keys.add(dummy);
                logger.info(String.format("%s key bound to action %s", component.getString("Key"),
                        component.getString("Description")));

                /* Add the key texture to be loaded */
                keyTexturesArray.add(image);
            }
        }

        /* Load our textures! */
        System.out.println(keyTexturesArray);
        keyTextures = new String[keyTexturesArray.size()];
        keyTextures = keyTexturesArray.toArray(keyTextures);
        logger.debug("Loading keybinding key assets");
        resourceService.loadTextures(keyTextures);
    }

    /**
     * Unload the keybinding key image assets.
     */
    public void unloadKeyBindAssets() {
        logger.debug("Unloading keybinding key assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(keyTextures);
    }

    /**
     * Gets the KeyBinds associated with the keybinding pause menu page number.
     * Returns up to a maximum of 8 KeyBind types.
     * @param page          Page number of the keybinding menu
     * @return KeyBind[]    Array of keybindings to display
     */
    public KeyBind[] getKeyBinds (int page) {

        KeyBind[] keyBinds = new KeyBind[8];
        int index = numKeysPerPage * page;
        int loop = 0;

        while (index < numKeys && loop < 8) {
            keyBinds[loop] = keys.get(index);
            index++;
            loop++;
        }

        return keyBinds;
    }
}
