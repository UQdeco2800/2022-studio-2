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
        String key;
        String description;
        String imagelvl1;
        String imagelvl2;

        public KeyBind(String inKey, String inDesc, String lvl1, String lvl2) {
            key = inKey;
            description = inDesc;
            imagelvl1 = lvl1;
            imagelvl2 = lvl2;
        }

        public String getKey() { return key; }

        public String getDescription() { return description; }

        public String getImagelvl1() { return imagelvl1; }

        public String getImagelvl2() { return imagelvl2; }
    }

    // Variables for component functionality
    private static final Logger logger = LoggerFactory.getLogger(OpenKeyBinds.class);
    public static final int KEYS_PER_PAGE = 10;
    // Reading left to right of this LUT is equates to reading top to bottom, then left to right
    // This is the LUT for the pure keybinding textures
    private static final int[][] KEY_TEXTURE_POS_LUT = {{372, 680}, {372, 595}, {372, 510}, {372, 425}, {372, 340},
            {912, 680}, {912, 595}, {912, 510}, {912, 425}, {912, 340}};
    // Label offset values
    public static final int KEY_OFFSET_X = 100;
    public static final int KEY_OFFSET_Y = 25;
    private ArrayList<KeyBind> keys;
    private String[] keyTextures;
    private int numKeys;

    public int getKeyPos(int index, int level) { return KEY_TEXTURE_POS_LUT[index][level]; }

    public OpenKeyBinds() {

        ResourceService resourceService = ServiceLocator.getResourceService();
        ArrayList<String> keyTexturesArray = new ArrayList<>();
        keys = new ArrayList<>();
        JsonReader json;
        KeyBind dummy;
        String imageLVL1;
        String imageLVL2;
        String key;
        String description;
        String pathLVL1;
        String pathLVL2;
        numKeys = 0;

        json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.getFileHandle("configs/keybinds.json", Files.FileType.Local));

        pathLVL1 = base.getString("PATH_LVL1");
        pathLVL2 = base.getString("PATH_LVL2");

        for (JsonValue component : base.get("Keys")) {

            description = component.getString("Description");
            if (!description.isEmpty()) { /* If description is empty, key is unbound, we don't want it */
                /* Get the JSON values we want */
                key = component.getString("Key");
                imageLVL1 = pathLVL1 + key + ".png";
                imageLVL2 = pathLVL2 + key + ".png";

                /* Create a keybinding entry */
                numKeys++;
                dummy = new KeyBind(key, description, imageLVL1, imageLVL2);
                keys.add(dummy);
                logger.debug("{} key bound to action {}", component.getString("Key"),
                        component.getString("Description"));

                /* Add the key texture to be loaded */
                keyTexturesArray.add(imageLVL1);
                keyTexturesArray.add(imageLVL2);
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
    public KeyBind[] getKeyBinds(int page) {

        KeyBind[] keyBinds = new KeyBind[KEYS_PER_PAGE];
        int index = KEYS_PER_PAGE * page;
        int loop = 0;

        while (index < numKeys && loop < KEYS_PER_PAGE) {
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
    public int getNumKeys () { return numKeys; }
}
