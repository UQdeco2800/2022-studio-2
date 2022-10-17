package com.deco2800.game.entities.factories;

import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.combatitemscomponents.PhysicalWeaponStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(GameExtension.class)
public class PotionFactoryTest {

    @Test
    void createSpeedPotion() {
        //Entity entity = MaterialFactory.creatTestMaterial("iron");
        // assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        // assertTrue(entity.checkEntityType(EntityTypes.IRON));
        Entity entity = PotionFactory.createSpeedPotion();
        assertTrue(entity.checkEntityType(EntityTypes.POTION));
    }
}
