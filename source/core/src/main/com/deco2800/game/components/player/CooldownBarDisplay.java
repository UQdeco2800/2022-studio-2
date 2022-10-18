package com.deco2800.game.components.player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.ui.UIComponent;

public class CooldownBarDisplay extends UIComponent {

    private Table cooldownBar;


    @Override
    public void create() {
        super.create();
        addActors();
    }

    @Override
    public void draw(SpriteBatch batch) {
        //draw is handled by the stage
        // update cooldowns here (kindof computationally expensive rip)
        // Maybe implement instead as a check to see if the cooldown image number
        // should be updated, then redraw the image
    }

    private void addActors() {
        cooldownBar = new Table();
        cooldownBar.bottom().right();
        cooldownBar.setFillParent(true);
        cooldownBar.padBottom(5f).padRight(25f);
        stage.addActor(cooldownBar);
    }


    public void addSkillIcon(Image skillIcon) {
        if (cooldownBar != null) {
            cooldownBar.add(skillIcon).size(96,96).pad(3f);
        }
    }

    public void clearSkillIcons() {
        cooldownBar.clear();
    }

    @Override
    public void dispose() {
        super.dispose();
        cooldownBar.clear();
    }
}
