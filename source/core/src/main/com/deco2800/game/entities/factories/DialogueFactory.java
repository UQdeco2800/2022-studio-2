package com.deco2800.game.entities.factories;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;


/**
* Factory to create non-playable dialogue box entities. Team 7 all-mid-npc
*/
public class DialogueFactory {

    /**
     * Create a dialogue box entity
     *
     * @return entity
     */
    public static Entity createDialogue() {
        Entity dialogue = new Entity()
                .addComponent((new TextureRenderComponent("images/NPC/Dialogue/dialogues2.png")));

        dialogue.getComponent(TextureRenderComponent.class).scaleEntity();
        dialogue.setScale(5,5);
        return dialogue;
    }

}
