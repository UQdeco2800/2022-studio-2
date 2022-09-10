package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class DialogueAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("femaleShake", this::femaleShakeStart);
        entity.getEvents().trigger("femaleShake");

    }

    void femaleShakeStart() {
        animator.startAnimation("femaleShake");
    }

}
