package com.deco2800.game.components.player;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import com.deco2800.game.areas.ForestGameArea;
import org.slf4j.LoggerFactory;

public class OpenCraftingComponent extends Component {

    float craftingTableXCoord;
    float craftingTableYCoord;
    private static Logger logger;

    private static Boolean isOpen = false;

    public void create() {

        logger = LoggerFactory.getLogger(OpenCraftingComponent.class);

        this.craftingTableXCoord = (float)ForestGameArea.getCraftingTablePos().x;
        this.craftingTableYCoord = (float)ForestGameArea.getCraftingTablePos().y;


            entity.getEvents().addListener("can_open", this::openCrafting);

            entity.getEvents().addListener("can_close", this::closeCrafting);




    }

    private void openCrafting() {

        if (entity.getCenterPosition().dst(craftingTableXCoord, craftingTableYCoord) < 10 && isOpen == false) {
            System.out.println("3");
            ServiceLocator.getGameArea().spawnCraftingMenu();
            entity.getEvents().trigger("open_crafting");
            isOpen = true;
        }

    }

    private void closeCrafting() {
        if (isOpen == true) {
            ServiceLocator.getGameArea().disposeCraftingMenu();
            entity.getEvents().trigger("close_crafting");
            isOpen = false;
        }

        }

    }

