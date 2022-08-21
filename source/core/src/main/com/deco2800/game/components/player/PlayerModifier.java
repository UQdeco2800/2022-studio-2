package com.deco2800.game.components.player;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerModifier extends Component{

    static private class Modifier {
        public boolean used; // Flag to determine if the modifier has been used
        public boolean expired; // Flag to determine if the modifier has been used
        public long expiry; // Millisecond timestamp for when the modifier will expire
        public float value; // The value difference after modification

        public String target; // The player stat we wish to modify

        public Modifier(String target, float value, int expiry) {
            this.used = false;
            this.expired = false;
            this.value = value;
            this.target = target;
            this.expiry = (expiry == 0) ? 0 : System.currentTimeMillis() + expiry;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(PlayerModifier.class);

    // List of all components present in the parent entity
    private PlayerActions playerActions;
    //private CombatStatsComponent combatStatsComponent;

    // Will need to implement a cleanup method for these - otherwise its mem leak city
    private ArrayList<Modifier> modifiers;

    // List of all modifiable stats and their associated string for the modifier to work
    // "movespeed"
    private static float refSpeed;
    private static float modSpeed;

    @Override
    public void create() {
        // Get the required components
        playerActions = entity.getComponent(PlayerActions.class);
        //combatStatsComponent = entity.getComponent(CombatStatsComponent.class);
        modifiers = new ArrayList<>();

        refSpeed = playerActions.getSpeedFloat();
        modSpeed = playerActions.getSpeedFloat();
        logger.info(String.format("I have been created with speed %f", refSpeed));
    }

    @Override
    public void update() {
        // Create an iterator for removal later
        Iterator<Modifier> iterator = modifiers.iterator();

        // Iterate through modifiers to apply/remove their affects
        for (Modifier mod : modifiers) {
            if (mod.used) { // Found modifier that's been used, do we need to get rid of it?
                if (mod.expiry <= System.currentTimeMillis()) {
                    applyModifier(mod, true);
                    mod.expired = true;
                }
            } else {
                if (mod.expiry == 0) { // This is a permanent change, enact it then remove
                    applyModifierPerm(mod);
                    mod.expired = true;
                } else {
                    applyModifier(mod, false);
                    mod.used = true;
                }
            }
        }

        // Iterate and remove any modifiers flagged as expired
        while(iterator.hasNext()) {
            Modifier mod = iterator.next();
            if (mod.expired) {
                iterator.remove();
            }
        }
    }

    private void applyModifier (Modifier mod, boolean remove) {

        float difference; // Used to return to original value if modifier is negative

        switch (mod.target) {
            case "movespeed" :
                difference = modSpeed;
                modSpeed = remove ? modSpeed - mod.value : modSpeed + mod.value;
                modSpeed = (modSpeed < 0) ? 0.3f : modSpeed; // Precaution for negative values
                difference -= modSpeed;
                mod.value = -1 * difference;
                playerActions.updateMaxSpeed(modSpeed);
                break;
            default :
                // Do nothing
        }
    }

    private void applyModifierPerm (Modifier mod) {
        switch (mod.target) {
            case "movespeed" :
                modSpeed += mod.value;
                refSpeed += mod.value;
                playerActions.updateMaxSpeed(modSpeed);
                break;
            case "gold":
                // Do nothing
            default :
                // Do nothing
        }
    }

    /**
     * Public function for creating player modifiers. Users will specify the target
     * statistic, the value of increase, a boolean for additive (false) or multiplicative (true)
     * modifiers, and the modifier length in milliseconds i.e. 1second = 1000ms
     * (0 if the modifier is intended to be permanent).
     * Modifiers are handled by the PlayerModifier update() override.
     *
     * @param target    Desired player statistic.
     * @param value     The value of the increase
     *
     */
    public void createModifier (String target, float value, boolean scaling, int expiry) {

        float valChange = 0f;

        switch (target) {
            case "movespeed":
                valChange = (scaling) ? refSpeed * value : value;
                break;
            default:
                // Do nothing
        }

        Modifier mod = new Modifier(target, valChange, expiry);
        modifiers.add(mod);
    }
}


