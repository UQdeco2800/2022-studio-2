package com.deco2800.game.CombatItems;

import com.deco2800.game.crafting.Buildable;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Weapon extends Entity implements Buildable {
    protected int level; //level 1, 2, 3
    protected double damage;
    protected double coolDown;
    protected Aura auraToApply;

    Weapon(double damage, double coolDown, int level) {
        this.damage = damage;
        this.coolDown = coolDown;
        this.level = level;
    }

    //from buildable interface of team 09
    public void setCraftingRecipe() {
        HashMap<Materials,Integer> recipe;
        recipe = new HashMap<>();
        recipe.put(Materials.Wood, 2);
        recipe.put(Materials.Steel,1);
    }

    public double getDamage() {
        return this.damage;
    }

    public double getCoolDown() {
        return this.coolDown;
    }

    public int getLevel() {
        return this.level;
    }

    public abstract void auraEffect(Aura auraToApply);

    //the aura input is the aura to revert FROM
    public void revertAuraEffect(Aura auraToRevert) {
        this.auraEffect(auraToRevert.inverseEffect());
    }

}
