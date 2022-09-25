package com.deco2800.game.components.maingame;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.deco2800.game.components.Component;
import com.deco2800.game.files.FileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class OpenKeyBinds extends Component {

    private static final Logger logger = LoggerFactory.getLogger(OpenKeyBinds.class);

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

    JsonReader json;
    ArrayList<KeyBind> keys = new ArrayList<>();

    public OpenKeyBinds() {

        KeyBind dummy;
        json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.getFileHandle("configs/keybinds.json", Files.FileType.Local));

        for (JsonValue component : base.get("Keys")) {
            dummy =new KeyBind(component.getString("Key"),
                    component.getString("Description"),
                    component.getString("Image"));
            keys.add(dummy);
            logger.info(String.format("%s key bound to action %s", component.getString("Key"),
                    component.getString("Description")));
        }
    }


}
