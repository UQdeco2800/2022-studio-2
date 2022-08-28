package com.deco2800.game.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class PotionStatsComponent extends Component {

    private static final Logger logger = LoggerFactory.getLogger(PotionStatsComponent.class);

    private float speed;
    private float maxSpeed;

    public void create() {
        entity.getEvents().addListener("increaseSpeed",this::incSpeed);

    }

    public PotionStatsComponent(float speed) {
        setSpeed(speed);
    }

    public void incSpeed(float speed) {
        setSpeed(this.speed + speed);
    }

    public void setSpeed(float speed) {
        if (speed >= 0 && speed <= maxSpeed) {
            this.speed = speed;
        } else if (speed > maxSpeed) {
            this.speed = maxSpeed;
        } else {
            this.speed = 0;
        }
        if (entity != null) {
            entity.getEvents().trigger("updateSpeed", this.speed);
        }
    }



}
