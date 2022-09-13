package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class PlumberFriendAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("plumberfriendShake", this::plumberfriendShakeStart);
        entity.getEvents().trigger("plumberfriendShake");

    }

    void plumberfriendShakeStart() {
        animator.startAnimation("plumberfriendShake");
    }

}
