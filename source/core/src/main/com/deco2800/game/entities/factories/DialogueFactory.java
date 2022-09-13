package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.npc.DialogueAnimationController;
import com.deco2800.game.components.npc.DialogueComponent;
import com.deco2800.game.components.npc.DialogueDisplay;
import com.deco2800.game.components.npc.DialogueKeybordInputComponent;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.events.listeners.EventListener;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;


/**
* Factory to create non-playable dialogue box entities. Team 7 all-mid-npc
 * All the dialogue created here should only have animation, but no interaction with player,
 * the interaction function should be found in components->NPC->DialogueComponent.
*/
public class DialogueFactory {

    /**
     * Create a dialogue box entity
     *
     * @return entity
     */
    public static Entity createDialogue() {

        AnimationRenderComponent animator =
                new AnimationRenderComponent(ServiceLocator.getResourceService().getAsset("images/NPC/dialogue_indicator/dialogue.atlas", TextureAtlas.class));
        animator.addAnimation("dialogueShake", 0.1f, Animation.PlayMode.LOOP);

        Entity dialogue = new Entity()
                .addComponent(animator)
                .addComponent(new DialogueAnimationController());


        dialogue.getComponent(AnimationRenderComponent.class).scaleEntity();
        dialogue.setScale(2,2);
        return dialogue;
    }

}
