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

    private static class Modifier {
        boolean used; // Flag to determine if the modifier has been used
        boolean expired; // Flag to determine if the modifier has been used
        boolean scalar; // Determine if modifier was originally a scalar function
        long expiry; // Millisecond timestamp for when the modifier will expire
        long lifetime;
        float value; // The value difference after modification
        float ogValue; // The value before modification

        String target; // The player stat we wish to modify

        public Modifier(String target, float ogValue, float value, boolean scalar, int expiry) {
            this.used = false;
            this.expired = false;
            this.scalar = scalar;
            this.target = target;
            this.value = value;
            this.ogValue = ogValue;
            this.lifetime = expiry;
            this.expiry = (expiry == 0) ? 0 : System.currentTimeMillis() + expiry;
        }
    }

    public static final String MOVESPEED = "moveSpeed";
    public static final String DMGREDUCTION = "damageReduction";
    public static final String MANAREGEN = "manaRegen";
    public static final String MANAMAX = "manaMax";
    public static final String STAMINAREGEN = "staminaRegen";
    public static final String STAMINAMAX = "staminaMax";

    // List of all components present in the parent entity
    private static PlayerActions playerActions;
    private static CombatStatsComponent combatStatsComponent;

    // Variables for modifier management
    private static ArrayList<Modifier> modifiers;

    // List of all modifiable stats and their associated string for the modifier to work
    // MOVESPEED
    private static float refSpeed;
    private static float modSpeed;
    // DMGREDUCTION
    private static float refDamageReduction;
    private static float modDamageReduction;
    // MANAREGEN
    private static float refManaRegen;
    private static float modManaRegen;
    // MANAMAX
    private static float refManaMax;
    private static float modManaMax;
    // STAMINAREGEN
    private static float refStaminaRegen;
    private static float modStaminaRegen;
    // STAMINAMAX
    private static float refStaminaMax;
    private static float modStaminaMax;



    /**
     * Initial define function for the modifier class.
     * Creates the modifier array.
     * Initialises all modifiable variables to zero.
     * Used purely for Junit functionality.
     */
    public PlayerModifier() {

        modifiers = new ArrayList<>();

        // Temporarily exists for jUnit testing
        refSpeed = 0;
        modSpeed = 0;
        refDamageReduction = 0;
        modDamageReduction = 0;
        refStaminaMax = 0;
        modStaminaMax = 0;
        refStaminaRegen = 0;
        modStaminaRegen = 0;
        refManaMax = 0;
        modManaMax = 0;
        refManaRegen = 0;
        modManaRegen = 0;
    }

    /**
     * Creation function to gather all necessary components for PlayerModified component
     * to function.
     * Gathers all parent components and necessary stat variables within them.
     * This function cannot yet be covered by Junit tests.
     */
    @Override
    public void create() {
        // Create our soon to exist modifiers
        modifiers = new ArrayList<>();

        // Get the required components
        playerActions = entity.getComponent(PlayerActions.class);
        combatStatsComponent = entity.getComponent(CombatStatsComponent.class);

        refSpeed = playerActions.getMaxSpeed();
        modSpeed = playerActions.getMaxSpeed();

        refDamageReduction = combatStatsComponent.getDamageReduction();
        modDamageReduction = combatStatsComponent.getDamageReduction();

        refManaRegen = combatStatsComponent.getManaRegenerationRate();
        modManaRegen = combatStatsComponent.getManaRegenerationRate();

        refManaMax = combatStatsComponent.getMaxMana();
        modManaMax = combatStatsComponent.getMaxMana();

        refStaminaRegen = combatStatsComponent.getStaminaRegenerationRate();
        modStaminaRegen = combatStatsComponent.getStaminaRegenerationRate();

        refStaminaMax = combatStatsComponent.getMaxStamina();
        modStaminaMax = combatStatsComponent.getMaxStamina();
    }

    /**
     * Triggers on frame update.
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
    private static void modifierHandler (Modifier mod, boolean remove) {

        float difference; // Used to return to original value if modifier is negative

        switch (mod.target) {
            case MOVESPEED -> {
                difference = modSpeed;
                modSpeed = remove ? modSpeed - mod.value : modSpeed + mod.value;
                modSpeed = (modSpeed <= 0) ? 0.1f : modSpeed; // Precaution for negative values
                difference -= modSpeed;
                mod.value = -1 * difference;
                playerActions.updateMaxSpeed(modSpeed);
            }
            case DMGREDUCTION -> {
                modDamageReduction = remove ? modDamageReduction - mod.value : modDamageReduction + mod.value;
                combatStatsComponent.setDamageReduction(modDamageReduction);
            }
            case MANAREGEN -> {
                modManaRegen = remove ? modManaRegen - mod.value : modManaRegen + mod.value;
                combatStatsComponent.setManaRegenerationRate((int) modManaRegen);
            }
            case MANAMAX -> {
                modManaMax = remove ? modManaMax - mod.value : modManaMax + mod.value;
                combatStatsComponent.setMaxMana((int) modManaMax);
            }
            case STAMINAREGEN -> {
                modStaminaRegen = remove ? modStaminaRegen - mod.value : modStaminaRegen + mod.value;
                combatStatsComponent.setStaminaRegenerationRate((int) modStaminaRegen);
            }
            case STAMINAMAX -> {
                modStaminaMax = remove ? modStaminaMax - mod.value : modStaminaMax + mod.value;
                combatStatsComponent.setMaxStamina((int) modStaminaMax);
            }
            default -> {
                // Do nothing
            }
        }
    }

    /**
     * Permanently apply a modification to the player entity. Increase the base and
     * modified player statistics.
     *
     * @param mod   Target statistic
     */
    private static void applyModifierPerm (Modifier mod) {

        switch (mod.target) {
            case MOVESPEED -> {
                modSpeed = (-1* mod.value >= modSpeed) ? 0.1f : modSpeed + mod.value;
                refSpeed = (-1* mod.value >= refSpeed) ? 0.1f : refSpeed + mod.value;
            }
            case DMGREDUCTION -> {
                modDamageReduction += mod.value;
                refDamageReduction += mod.value;
                combatStatsComponent.setDamageReduction(modDamageReduction);
            }
            case MANAREGEN -> {
                modManaRegen += mod.value;
                refManaRegen += mod.value;
                combatStatsComponent.setManaRegenerationRate((int) modManaRegen);
            }
            case MANAMAX -> {
                modManaMax += mod.value;
                refManaMax += mod.value;
                combatStatsComponent.setMaxMana((int) modManaMax);
            }
            case STAMINAREGEN -> {
                modStaminaRegen += mod.value;
                refStaminaRegen += mod.value;
                combatStatsComponent.setStaminaRegenerationRate((int) modStaminaRegen);
            }
            case STAMINAMAX -> {
                modStaminaMax += mod.value;
                refStaminaMax += mod.value;
                combatStatsComponent.setMaxStamina((int) modStaminaMax);
            }
            default -> {
                // Do nothing
            }
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

        float valChange;

        switch (target) {
            case MOVESPEED:
                valChange = (scaling) ? refSpeed * value : value;
                break;
            case DMGREDUCTION:
                valChange = (scaling) ? refDamageReduction * value : value;
                break;
            case MANAREGEN :
                valChange = (scaling) ? (int)(refManaRegen * value) : (int)value;
                break;
            case MANAMAX :
                valChange = (scaling) ? (int)(refManaMax * value) : (int)value;
                break;
            case STAMINAREGEN :
                valChange = (scaling) ? (int)(refStaminaRegen * value) : (int)value;
                break;
            case STAMINAMAX :
                valChange = (scaling) ? (int)(refStaminaMax * value) : (int)value;
                break;
            default:
                return false;
        }

        Modifier mod = new Modifier(target, value, valChange, scaling, expiry);
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
     * @return          True if modifier exists, false otherwise
     */
    public boolean checkModifier (String target, float value, boolean scaling, int expiry) {

        Iterator<Modifier> iterator = modifiers.iterator();

        while(iterator.hasNext()) {
            Modifier mod = iterator.next();
            if (mod.target.equals(target) && mod.ogValue == value && mod.lifetime == expiry && mod.scalar == scaling) {
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

        return switch (target) {
            case MOVESPEED -> modSpeed;
            case DMGREDUCTION -> modDamageReduction;
            case MANAREGEN -> modManaRegen;
            case MANAMAX -> modManaMax;
            case STAMINAREGEN -> modStaminaRegen;
            case STAMINAMAX -> modStaminaMax;
            default -> -1;
        };
    }

    /**
     * Public function to return current modified value of a desired target.
     *
     * @param target    Desired player statistic.
     * @return Float value of the desired modified target statistic, else -1 on fail.
     */
    public float getReference (String target) {

        return switch (target) {
            case MOVESPEED -> refSpeed;
            case DMGREDUCTION -> refDamageReduction;
            case MANAREGEN -> refManaRegen;
            case MANAMAX -> refManaMax;
            case STAMINAREGEN -> refStaminaRegen;
            case STAMINAMAX -> refStaminaMax;
            default -> -1;
        };
    }

    /**
     * Temporary test functions for JUnit Testing until "mocking"
     * is covered in the course.
     */

    public static void jUnitAddPlayerActions (PlayerActions actions) {
        playerActions = actions;
        refSpeed = playerActions.getMaxSpeed();
        modSpeed = playerActions.getMaxSpeed();
    }

    public static void jUnitAddCombatStats (CombatStatsComponent combat) {
        combatStatsComponent = combat;
        refDamageReduction = combatStatsComponent.getDamageReduction();
        modDamageReduction = combatStatsComponent.getDamageReduction();

        refManaRegen = combatStatsComponent.getManaRegenerationRate();
        modManaRegen = combatStatsComponent.getManaRegenerationRate();

        refManaMax = combatStatsComponent.getMaxMana();
        modManaMax = combatStatsComponent.getMaxMana();

        refStaminaRegen = combatStatsComponent.getStaminaRegenerationRate();
        modStaminaRegen = combatStatsComponent.getStaminaRegenerationRate();

        refStaminaMax = combatStatsComponent.getMaxStamina();
        modStaminaMax = combatStatsComponent.getMaxStamina();
    }
}


