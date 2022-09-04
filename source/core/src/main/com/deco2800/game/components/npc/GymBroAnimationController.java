package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class GymBroAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("wanderStart", this::animateWander);
        entity.getEvents().addListener("chaseStart", this::animateChase);
        entity.getEvents().trigger("wanderStart");
    }

    void animateWander() {
        animator.startAnimation("walk_forward");
    }

    void animateChase() {
        animator.startAnimation("attack_left");
    }
}
