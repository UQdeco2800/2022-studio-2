package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class PlayerAnimationController extends Component {

    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("regularAnimation", this::animateRegular);
        entity.getEvents().addListener("teleportAnimation", this::animateTeleport);
        entity.getEvents().trigger("regularAnimation");
    }

    void animateRegular() {
        animator.startAnimation("walk");
    }

    void animateTeleport() {
        animator.startAnimation("teleport");
    }
}

