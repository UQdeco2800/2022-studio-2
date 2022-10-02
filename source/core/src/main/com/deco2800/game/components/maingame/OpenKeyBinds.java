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

    public static class KeyBind {
        public String key;
        public String description;
        public String imagelvl1;
        public String imagelvl2;

        public KeyBind(String in_key, String in_desc, String lvl_1, String lvl_2) {
            key = in_key;
            description = in_desc;
            imagelvl1 = lvl_1;
            imagelvl2 = lvl_2;
        }
    }

    // Variables for component functionality
    private static final Logger logger = LoggerFactory.getLogger(OpenKeyBinds.class);
    public static final int numKeysPerPage = 10;
    private static JsonReader json;
    private static ArrayList<KeyBind> keys = new ArrayList<>();
    private static String[] keyTextures;
    private static int numKeys;

    // Reading left to right of this LUT is equates to reading top to bottom, then left to right
    // This is the LUT for the pure keybinding textures
    public static final int[][] keyTexturePosLUT = {{372, 680}, {372, 595}, {372, 510}, {372, 425}, {372, 340},
            {912, 680}, {912, 595}, {912, 510}, {912, 425}, {912, 340}};

    // Label offset values
    public static final int keyLabelOffsetX = 100;
    public static final int keyLabelOffsetY = 25;


    public OpenKeyBinds() {

        ResourceService resourceService = ServiceLocator.getResourceService();
        ArrayList<String> keyTexturesArray = new ArrayList<>();
        KeyBind dummy;
        String image_lvl1, image_lvl2, key, description, path_lvl1, path_lvl2;
        numKeys = 0;

        json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.getFileHandle("configs/keybinds.json", Files.FileType.Local));

        path_lvl1 = base.getString("PATH_LVL1");
        path_lvl2 = base.getString("PATH_LVL2");

        for (JsonValue component : base.get("Keys")) {

            description = component.getString("Description");
            if (!description.isEmpty()) { /* If description is empty, key is unbound, we don't want it */
                /* Get the JSON values we want */
                key = component.getString("Key");
                image_lvl1 = path_lvl1 + key + ".png";
                image_lvl2 = path_lvl2 + key + ".png";

                /* Create a keybinding entry */
                numKeys++;
                dummy = new KeyBind(key, description, image_lvl1, image_lvl2);
                keys.add(dummy);
                logger.info(String.format("%s key bound to action %s", component.getString("Key"),
                        component.getString("Description")));

                /* Add the key texture to be loaded */
                keyTexturesArray.add(image_lvl1);
                keyTexturesArray.add(image_lvl2);
            }
        }

        /* Load our textures! */
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
    public static KeyBind[] getKeyBinds (int page) {

        KeyBind[] keyBinds = new KeyBind[numKeysPerPage];
        int index = numKeysPerPage * page;
        int loop = 0;

        while (index < numKeys && loop < numKeysPerPage) {
            keyBinds[loop] = keys.get(index);
            index++;
            loop++;
        }

        return keyBinds;
    }

    /**
     * Get the number of keys initialised.
     * @return int  Number of initialised keys
     */
    public static int getNumKeys () { return numKeys; }
}
