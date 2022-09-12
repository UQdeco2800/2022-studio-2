package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class FriendlyCreatureAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("creatureShake", this::femaleShakeStart);
        entity.getEvents().trigger("creatureShake");

    }

    void femaleShakeStart() {
        animator.startAnimation("creatureShake");
    }

}
