package com.deco2800.game.components.combatitemscomponents;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimatedImage extends Image
{
    protected Animation<TextureRegion> animation = null;
    private float stateTime = 0;

    public AnimatedImage(Animation<TextureRegion> animation) {
        super(animation.getKeyFrame(0));
        this.animation = animation;
    }

    @Override
    public void act(float delta)
    {
        stateTime = stateTime + delta;
        ((TextureRegionDrawable)getDrawable()).setRegion(animation.getKeyFrame(stateTime, true));
        super.act(delta);
    }
}