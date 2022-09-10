package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class DialogueAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("childShake", this::childShakeStart);
        entity.getEvents().trigger("childShake");

    }

    void childShakeStart() {
        animator.startAnimation("childShake");
    }

}
