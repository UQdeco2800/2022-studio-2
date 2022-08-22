package com.deco2800.game.components.player;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import org.slf4j.Logger;
import com.deco2800.game.areas.ForestGameArea;
import org.slf4j.LoggerFactory;

public class OpenCraftingComponent extends Component {

    float craftingTableXCoord;
    float craftingTableYCoord;
    private static Logger logger;

    public void create() {

        logger = LoggerFactory.getLogger(OpenCraftingComponent.class);

        this.craftingTableXCoord = (float)ForestGameArea.getCraftingTablePos().x;
        this.craftingTableYCoord = (float)ForestGameArea.getCraftingTablePos().y;
        entity.getEvents().addListener("can_open", this::openCrafting);
        System.out.println("4");

    }

    private void openCrafting() {

        if (entity.getCenterPosition().dst(craftingTableXCoord, craftingTableYCoord) < 10) {
            System.out.println("3");
            entity.getEvents().trigger("open_crafting");
        }

    }
}
