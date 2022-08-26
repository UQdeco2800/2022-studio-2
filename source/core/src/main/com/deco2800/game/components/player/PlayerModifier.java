package com.deco2800.game.components.player;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Self-sufficient player modifier handler for temporarily or permanently modifying player
 * statistics.
 * Takes in the
 */
public class PlayerModifier extends Component{

    static private class Modifier {
        public boolean used; // Flag to determine if the modifier has been used
        public boolean expired; // Flag to determine if the modifier has been used
        public long expiry; // Millisecond timestamp for when the modifier will expire
        public long lifetime;
        public float value; // The value difference after modification

        public String target; // The player stat we wish to modify

        public Modifier(String target, float value, int expiry) {
            this.used = false;
            this.expired = false;
            this.target = target;
            this.value = value;
            this.lifetime = expiry;
            this.expiry = (expiry == 0) ? 0 : System.currentTimeMillis() + expiry;
        }
    }

    // List of all components present in the parent entity
    private PlayerActions playerActions;
    private CombatStatsComponent combatStatsComponent;

    // Variables for modifier management
    private ArrayList<Modifier> modifiers;

    // List of all modifiable stats and their associated string for the modifier to work
    // "moveSpeed"
    private static float refSpeed;
    private static float modSpeed;
    // "damageReduction"
    private static float refDamageReduction;
    private static float modDamageReduction;

    /**
     * Initial define function for the modifier class.
     *
     * Creates the modifier array.
     * Initialises all modifiable variables to zero.
     */
    public PlayerModifier() {

        modifiers = new ArrayList<>();

        refSpeed = 0;
        modSpeed = 0;
        refDamageReduction = 0;
        modDamageReduction = 0;
    }

    /**
     * Creation function to gather all necessary components for PlayerModified component
     * to function.
     *
     * Gathers all parent components and necessary stat variables within them.
     */
    @Override
    public void create() {
        // Get the required components
        playerActions = entity.getComponent(PlayerActions.class);
        combatStatsComponent = entity.getComponent(CombatStatsComponent.class);

        refSpeed = playerActions.getMaxSpeed();
        modSpeed = playerActions.getMaxSpeed();

        refDamageReduction = combatStatsComponent.getDamageReduction();
        modDamageReduction = combatStatsComponent.getDamageReduction();
    }

    /**
     * Triggers on frame update.
     *
     * Searches through all modifiers present in ArrayList, and handles based on
     * current system time and expiry time.
     */
    @Override
    public void update() {
        // Create an iterator for removal later, we cant remove when iterating
        Iterator<Modifier> iterator = modifiers.iterator();

        // Iterate through modifiers to apply/remove their affects
        for (Modifier mod : modifiers) {
            if (mod.used) { // Found modifier that's been used, do we need to get rid of it?
                if (mod.expiry <= System.currentTimeMillis()) {
                    modifierHandler(mod, true); // Modifier time expired, remove
                    mod.expired = true;
                }
            } else {
                if (mod.expiry == 0) { // This is a permanent change, enact it then remove
                    applyModifierPerm(mod);
                    mod.expired = true;
                } else {
                    modifierHandler(mod, false);
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

    /**
     * Internal function to apply and remove temporary modifications. Sets the modifier value equal
     * to the increase/decrease in the reference statistic such that when this function is called for
     * modifier removal, the value is properly restored.
     *
     * @param mod       Target statistic
     * @param remove    Boolean to determine if a modifier is being removed
     */
    private void modifierHandler (Modifier mod, boolean remove) {

        float difference; // Used to return to original value if modifier is negative

        switch (mod.target) {
            case "moveSpeed" :
                difference = modSpeed;
                modSpeed = remove ? modSpeed - mod.value : modSpeed + mod.value;
                modSpeed = (modSpeed < 0) ? 0.1f : modSpeed; // Precaution for negative values
                difference -= modSpeed;
                mod.value = -1 * difference;
                playerActions.updateMaxSpeed(modSpeed);
                break;
            case "damageReduction" :
                difference = modDamageReduction;
                modDamageReduction = remove ? modDamageReduction - mod.value : modDamageReduction + mod.value;
                modDamageReduction = (modDamageReduction < 0) ? 0.1f : modDamageReduction; // Precaution for negative values
                difference -= modDamageReduction;
                mod.value = -1 * difference;
                combatStatsComponent.setDamageReduction(modDamageReduction);
                break;
            default :
                // Do nothing
        }
    }

    /**
     * Permanently apply a modification to the player entity. Increase the base and
     * modified player statistics.
     *
     * @param mod   Target statistic
     */
    private void applyModifierPerm (Modifier mod) {

        switch (mod.target) {
            case "moveSpeed" :
                modSpeed += mod.value;
                refSpeed += mod.value;
                playerActions.updateMaxSpeed(modSpeed);
                break;
            case "damageReduction":
                modDamageReduction += mod.value;
                refDamageReduction += mod.value;
                combatStatsComponent.setDamageReduction(modDamageReduction);
                break;
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
     * @param scaling   Boolean flag to indicate if the increase value is multiplicative or additive
     * @param expiry    Expiry time (milliseconds) of modifier, 0 if permanent
     * @return          True on success, false otherwise
     */
    public boolean createModifier (String target, float value, boolean scaling, int expiry) {

        float valChange = 0f;

        switch (target) {
            case "moveSpeed":
                valChange = (scaling) ? refSpeed * value : value;
                break;
            case "damageReduction":
                valChange = (scaling) ? refDamageReduction * value : value;
                break;
            default:
                return false;
                // Do nothing
        }

        Modifier mod = new Modifier(target, valChange, expiry);
        modifiers.add(mod);
        return true;
    }

    /**
     * Public function to check if there is an already existing modifier with matching parameters
     *
     * @param target    Desired player statistic.
     * @param value     The value of the increase
     * @param scaling   Boolean flag to indicate if the increase value is multiplicative or additive
     * @param expiry    Expiry time (milliseconds) of modifier, 0 if permanent
     */
    public boolean checkModifier (String target, float value, boolean scaling, int expiry) {

        float valChange = 0f;
        Iterator<Modifier> iterator = modifiers.iterator();

        switch (target) {
            case "moveSpeed":
                valChange = (scaling) ? refSpeed * value : value;
                break;
            case "damageReduction":
                valChange = (scaling) ? refDamageReduction * value : value;
                break;
            default:
                // Do nothing
        }

        while(iterator.hasNext()) {
            Modifier mod = iterator.next();
            if (mod.target.equals(target) && mod.value == valChange && mod.lifetime == expiry) {
                return true;
            }
        }

        return false;
    }

    /**
     * Public function to return current reference value of a desired target.
     *
     * @param target    Desired player statistic.
     * @return Float value of the desired reference target statistic, else -1 on fail.
     */
    public float getModified (String target) {

        switch (target) {
            case "moveSpeed":
                return modSpeed;
            case "damageReduction":
                return modDamageReduction;
            default:
                return -1;
        }
    }

    /**
     * Public function to return current modified value of a desired target.
     *
     * @param target    Desired player statistic.
     * @return Float value of the desired modified target statistic, else -1 on fail.
     */
    public float getReference (String target) {

        switch (target) {
            case "moveSpeed":
                return refSpeed;
            case "damageReduction":
                return refDamageReduction;
            default:
                return -1;
        }
    }

    /**
     * Temporary test functions for JUnit Testing until "mocking"
     * is covered in the course.
     */

    public void jUnitAddPlayerActions (PlayerActions actions) {
        playerActions = actions;
        refSpeed = playerActions.getMaxSpeed();
        modSpeed = playerActions.getMaxSpeed();
    }

    public void jUnitAddCombatStats (CombatStatsComponent combat) {
        combatStatsComponent = combat;
        refDamageReduction = combatStatsComponent.getDamageReduction();
        modDamageReduction = combatStatsComponent.getDamageReduction();
    }
}


