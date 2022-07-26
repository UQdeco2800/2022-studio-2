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
        entity.getEvents().addListener("femaleShake", this::femaleShakeStart);
        entity.getEvents().trigger("femaleShake");
        entity.getEvents().addListener("childShake", this::childShakeStart);
        entity.getEvents().trigger("childShake");
        entity.getEvents().addListener("guardShake", this::guardShakeStart);
        entity.getEvents().trigger("guardShake");
        entity.getEvents().addListener("humanguardShake", this::humanguardShakeStart);
        entity.getEvents().trigger("humanguardShake");
        entity.getEvents().addListener("plumberfriendShake", this::plumberfriendShakeStart);
        entity.getEvents().trigger("plumberfriendShake");
        entity.getEvents().addListener("creatureShake", this::creatureShakeStart);
        entity.getEvents().trigger("creatureShake");
    }

    void MaleShakeStart() {
        animator.startAnimation("MaleShake");
    }
    void femaleShakeStart() {
        animator.startAnimation("femaleShake");
    }
    void childShakeStart() {
        animator.startAnimation("childShake");
    }
    void guardShakeStart() {
        animator.startAnimation("guardShake");
    }
    void humanguardShakeStart() {
        animator.startAnimation("humanguardShake");
    }
    void plumberfriendShakeStart() {
        animator.startAnimation("plumberfriendShake");
    }
    void creatureShakeStart() {
        animator.startAnimation("creatureShake");
    }

}

