package com.deco2800.game.components.maingame;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.ceil;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class OpenKeyBindsTest {

    OpenKeyBinds openKeyBinds;
    JsonReader json;
    JsonValue base;

    @BeforeEach
    void init () {
        // Create our service locator
        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        ServiceLocator.registerResourceService(resourceService);

        // Create our keybinds
        openKeyBinds = new OpenKeyBinds();

        // Create our json viewer
        json = new JsonReader();
        base = json.parse(Gdx.files.getFileHandle("configs/keybinds.json", Files.FileType.Local));
    }

    @Test
    void shouldHaveCorrectKeyCount() {

        String description;
        int count = 0;

        for (JsonValue component : base.get("Keys")) {
            description = component.getString("Description");
            if (!description.isEmpty()) {
                count++;
            }
        }

        assertEquals(count, openKeyBinds.getNumKeys());
    }

    @Test
    void shouldHaveCorrectKeys() {

        ArrayList<OpenKeyBinds.KeyBind> refKeys = new ArrayList<>();
        OpenKeyBinds.KeyBind dummy;
        String path_lvl1, path_lvl2, description, keyName, image_lvl1, image_lvl2;
        int refIndex = 0;
        int pages = ceil((float)openKeyBinds.getNumKeys() / (float)OpenKeyBinds.KEYS_PER_PAGE);
        int realLength;

        /* The below code is almost identical to the real class building code. It
           has been reused to test if produced keys should be created as expected.
           Must do dynamically as the JSON can change.
         */
        path_lvl1 = base.getString("PATH_LVL1");
        path_lvl2 = base.getString("PATH_LVL2");

        for (JsonValue component : base.get("Keys")) {

            description = component.getString("Description");
            if (!description.isEmpty()) { /* If description is empty, key is unbound, we don't want it */
                /* Get the JSON values we want */
                keyName = component.getString("Key");
                image_lvl1 = path_lvl1 + keyName + ".png";
                image_lvl2 = path_lvl2 + keyName + ".png";

                /* Create a keybinding entry */
                dummy = new OpenKeyBinds.KeyBind(keyName, description, image_lvl1, image_lvl2);
                refKeys.add(dummy);
            }
        }

        for (int i = 0; i < pages; i++) {
            OpenKeyBinds.KeyBind[] keys = openKeyBinds.getKeyBinds(i);

            /* Cannot use keys.length in the following for loop as there is a possibility
            the returned key array could include NULL terms. I.e. always returns an array of size
            10 but there might only be 16 keys -> Array of 10 then array of  next loop
             */
            realLength = 0;
            while (realLength < keys.length && keys[realLength] != null) {
                realLength++;
            }

            for (int j = 0; j < realLength; j++) {
                assertEquals(refKeys.get(refIndex).getKey(), keys[j].getKey());
                assertEquals(refKeys.get(refIndex).getDescription(), keys[j].getDescription());
                assertEquals(refKeys.get(refIndex).getImagelvl1(), keys[j].getImagelvl1());
                assertEquals(refKeys.get(refIndex).getImagelvl2(), keys[j].getImagelvl2());
                refIndex++;
            }
        }
    }

    @Test
    void shouldHaveLoadedKeyTextures() {

        ArrayList<String> keyTexturesArray = new ArrayList<>();
        String path_lvl1, path_lvl2, image_lvl1, image_lvl2, description, keyName;

        path_lvl1 = base.getString("PATH_LVL1");
        path_lvl2 = base.getString("PATH_LVL2");

        for (JsonValue component : base.get("Keys")) {

            description = component.getString("Description");
            if (!description.isEmpty()) { /* If description is empty, key is unbound, we don't want it */
                /* Get the JSON values we want */
                keyName = component.getString("Key");
                image_lvl1 = path_lvl1 + keyName + ".png";
                image_lvl2 = path_lvl2 + keyName + ".png";

                /* Add the key texture to be loaded */
                keyTexturesArray.add(image_lvl1);
                keyTexturesArray.add(image_lvl2);
            }
        }

        for (int i = 0; i < openKeyBinds.getNumKeys(); i++) {
            assertTrue(ServiceLocator.getResourceService().containsAsset(keyTexturesArray.get(i), Texture.class));
        }
    }

    @Test
    void shouldHaveDisposedKeyTextures() {

        ArrayList<String> keyTexturesArray = new ArrayList<>();
        String path_lvl1, path_lvl2, image_lvl1, image_lvl2, description, keyName;

        path_lvl1 = base.getString("PATH_LVL1");
        path_lvl2 = base.getString("PATH_LVL2");

        for (JsonValue component : base.get("Keys")) {

            description = component.getString("Description");
            if (!description.isEmpty()) { /* If description is empty, key is unbound, we don't want it */
                /* Get the JSON values we want */
                keyName = component.getString("Key");
                image_lvl1 = path_lvl1 + keyName + ".png";
                image_lvl2 = path_lvl2 + keyName + ".png";

                /* Add the key texture to be loaded */
                keyTexturesArray.add(image_lvl1);
                keyTexturesArray.add(image_lvl2);
            }
        }

        // Dispose of all assets
        openKeyBinds.unloadKeyBindAssets();

        for (int i = 0; i < openKeyBinds.getNumKeys(); i++) {
            assertFalse(ServiceLocator.getResourceService().containsAsset(keyTexturesArray.get(i), Texture.class));
        }
    }
}
