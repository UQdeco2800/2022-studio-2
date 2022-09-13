package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class GuardAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("guardShake", this::guardShakeStart);
        entity.getEvents().trigger("guardShake");

    }

    void guardShakeStart() {
        animator.startAnimation("guardShake");
    }

}
