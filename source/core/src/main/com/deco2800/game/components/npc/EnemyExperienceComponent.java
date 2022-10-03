package com.deco2800.game.components.npc;

import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerSkillComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.services.ServiceLocator;

public class EnemyExperienceComponent {

    private int experiencePointsOnDeath = 0;

    public EnemyExperienceComponent(int experiencePointsOnDeath) {
        this.experiencePointsOnDeath = experiencePointsOnDeath;
    }

    public void triggerExperienceGain() {
        Entity player = ServiceLocator.getGameArea().getPlayer();
        PlayerSkillComponent skillComponent = player.getComponent(PlayerActions.class).getSkillComponent();
        skillComponent.addSkillPoints(experiencePointsOnDeath);
    }
}
