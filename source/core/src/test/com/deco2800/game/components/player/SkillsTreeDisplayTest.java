package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deco2800.game.SkillsTree.SkillsTreeDisplay;
import com.deco2800.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SkillsTreeDisplayTest {

    SkillsTreeDisplay skillsTreeDisplay;
    CooldownBarDisplay cooldownBar;

    @BeforeEach
    void init() {
        skillsTreeDisplay = Mockito.mock(SkillsTreeDisplay.class);
        cooldownBar = Mockito.mock(CooldownBarDisplay.class);
        Entity entity = Mockito.mock(Entity.class);
        when(entity.getComponent(CooldownBarDisplay.class)).thenReturn(cooldownBar);

        skillsTreeDisplay.setEntity(entity);
    }

    @Test
    void testCreate() {
        //skillsTreeDisplay.create();
        //verify(cooldownBar).addSkillIcon();
        //verify(cooldownBar).addSkillIcon(new Image(new Texture("images/Skills/blankSkillIcon.png")));

    }

}
