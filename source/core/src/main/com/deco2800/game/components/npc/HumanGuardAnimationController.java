package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class HumanGuardAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("humanguardShake", this::humanguardShakeStart);
        entity.getEvents().trigger("humanguardShake");

    }

    void humanguardShakeStart() {
        animator.startAnimation("humanguardShake");
    }

}
