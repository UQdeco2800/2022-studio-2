package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class NPCAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("MaleShake", this::MaleShakeStart);
        entity.getEvents().trigger("MaleShake");
    }

    void MaleShakeStart() {
        animator.startAnimation("MaleShake");
    }

}

