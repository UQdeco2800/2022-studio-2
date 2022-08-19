package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.physics.components.PhysicsComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

// I have to define this here else it gets upset?
import static com.deco2800.game.components.player.interaction.ADDITIVE;
import static com.deco2800.game.components.player.interaction.MULTIPLICATIVE;
// Is defining an enum like this okay?
enum interaction {
    ADDITIVE,
    MULTIPLICATIVE
}

public class PlayerModifier extends Component{

    private class Modifier {
        public long expiry; // Millisecond timestamp for when the modifier will expire
        public float difference = 0; // The value difference after modification
        public interaction scaling = ADDITIVE; // How the modifier interacts with the value

        public Modifier(float value, interaction scaling) {
            this.difference = value;
            this.scaling = scaling;
            this.expiry = System.currentTimeMillis();
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(PlayerModifier.class);

    // List of all components present in the parent entity
    private PlayerActions playerActions;
    private CombatStatsComponent combatStatsComponent;

    // Will need to implement a cleanup method for these - otherwise its mem leak city
    private Modifier[] modifiers;

    // We cant implement deep copies nicely
    // Need to have a list of variables that we can modify (both the base value and the
    private static Vector2 maxSpeed_base;
    private static Vector2 maxSpeed_mod;

    public PlayerModifier() {
        maxSpeed_base = playerActions.getMaxSpeed();
        maxSpeed_mod = playerActions.getMaxSpeed();
    }

    @Override
    public void create() {
        playerActions = entity.getComponent(PlayerActions.class);
        combatStatsComponent = entity.getComponent(CombatStatsComponent.class);
    }

    public void createModifier (String target, float value, interaction scaling, boolean temporary) {

        if (temporary) {
            // Create our temporary modifier
        } else {
            // Modify both our reference and our current
        }
    }
}


