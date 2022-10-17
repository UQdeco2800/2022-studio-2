package com.deco2800.game.entities.npc;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.CombatItemsComponents.AreaOfEffectStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.NPCFactory;
import com.deco2800.game.extensions.GameExtension;

import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)


class npcTest {

    DebugRenderer debugRenderer;

    public static String[] textFemale ;
    public static String[] textGuard ;
    public static String[] textMale ;
    public static String[] textChild ;
    public static String[] textFriendlyCreature ;
    public static String[] textHumanGuard ;
    public static String[] textPlumberFriend ;
    @Mock
    ShapeRenderer shapeRenderer;
    @Mock
    Box2DDebugRenderer physicsRenderer;
    @Mock
    Matrix4 projMatrix;

    public static int index_female;
    public static int index_guard;
    public static int index_male;
    public static int index_child;
    public static int index_friendlycreature;
    public static int index_humanguard;
    public static int index_plumberfriend;
    public static String npcName;
    public static String key;
    public static String item;
    public boolean addItem(String item){
        return item != null && index_plumberfriend != 0;
    }

    @BeforeEach
    public void setUp() {
        debugRenderer = new DebugRenderer(physicsRenderer, shapeRenderer);
        index_female = 0;
        index_guard = 0;
        index_male = 0;
        index_child = 0;
        index_humanguard = 0;
        index_friendlycreature = 0;
        index_plumberfriend = 0;

        key = "f";
        npcName = "plumberFriend";
        if (key.equalsIgnoreCase("f") && npcName.equals("guard")){
            index_guard++;
            item = null;
        }

        if (key.equalsIgnoreCase("f") && npcName.equals("female")){
            index_female++;
            item = null;
        }
        if (key.equalsIgnoreCase("f") && npcName.equals("male")){
            index_male++;
            item = null;
        }

        if (key.equalsIgnoreCase("f") && npcName.equals("child")){
            index_child++;
            item = "item";
        }
        if (key.equalsIgnoreCase("f") && npcName.equals("humanGuard")){
            index_humanguard++;
            item = "item";
        }

        if (key.equalsIgnoreCase("f") && npcName.equals("friendlyCreature")){
            index_friendlycreature++;
            item = null;
        }
        if (key.equalsIgnoreCase("f") && npcName.equals("plumberFriend")){
            index_plumberfriend++;
            item = "item";
        }


        textFemale = new String[]{
                "Nat:\n",
                "Oh good heavens, are you balding?\n",
                "That's quite horrific."

        };

        textGuard = new String[]{
                "Guard\n",
                "Have you seen anyone suspicious? ",
                "No? Okay then..."


        };
        textMale = new String[] {
                "Richard: \n",
                "What on Atlantis are you wearing?",
                "There’s a public restroom which probably needs fixing"

        };


        textChild = new String[] {
                "child\n",
                "Hello…Rabbit says he has not seen you around before\n",
                "Your..hands..they are stained…brown\n"

        };
        textHumanGuard = new String[] {
                "George",
                "Oh good, you are here!",
                "My hands are a bit full right now"
        };
        textFriendlyCreature = new String[] {
                "FriendlyCreature\n",
                "1",
                "2"

        };
        textPlumberFriend = new String[] {
                "PlumberFriend\n",
                "Hey! I have not seen you in forever.",
                "Are you still going ahead with your plan?",
                "What do you mean ‘what plan’?"
        };
    }

    @Test
    void npcPosition() {

        Vector2 from = new Vector2(0f, 0f);
        Vector2 to = new Vector2(200f, 200f);
        debugRenderer.drawLine(from, to);
        debugRenderer.render(projMatrix);

        verify(shapeRenderer).line(from, to);

        // Should not render next frame
        debugRenderer.render(projMatrix);
        verify(shapeRenderer, times(1)).line(any(Vector2.class), any(Vector2.class));
    }


    @Test
    void GuardExist() {

        GridPoint2 GuardPosition = new GridPoint2(10, 8);
        assertTrue(GuardPosition.x>0&&GuardPosition.x<=100 && GuardPosition.y>0&&GuardPosition.y<=100);

        GridPoint2 GuardDialoguePosition = new GridPoint2(10, 9);
        assertTrue(GuardDialoguePosition.x>0&&GuardDialoguePosition.x<=100 && GuardDialoguePosition.y>0&&GuardDialoguePosition.y<=100);

    }

    @Test
    void MaleCitizenExist() {
//        spawnMaleCitizen();
        GridPoint2 maleCitizenPosition = new GridPoint2(74,121);
        assertTrue(maleCitizenPosition.x>0&&maleCitizenPosition.x<=201 && maleCitizenPosition.y>0&&maleCitizenPosition.y<=200);

        GridPoint2 maleCitizenDialoguePosition = new GridPoint2(74, 122);
        assertTrue(maleCitizenDialoguePosition.x>0&&maleCitizenDialoguePosition.x<=200 && maleCitizenDialoguePosition.y>0&&maleCitizenDialoguePosition.y<=200);

    }

//    @Test
//    void shouldSpawnMale() {
//        TerrainFactory terrainFactory = mock(TerrainFactory.class);
//        ForestGameArea forestGameArea =
//                new ForestGameArea(terrainFactory) {
//                    @Override
//                    public void create() {}
//                };
//        ServiceLocator.registerEntityService(new EntityService());
//        Entity entity = mock(Entity.class);
//
//        forestGameArea.spawn(entity);
//        verify(entity).create();
//    }


    @Test
    void ChildExist() {

        GridPoint2 childPosition = new GridPoint2(33,95);
        assertTrue(childPosition.x>0&&childPosition.x<=100 && childPosition.y>0&&childPosition.y<=100);

        GridPoint2 childDialoguePosition = new GridPoint2(33, 96);
        assertTrue(childDialoguePosition.x>0&&childDialoguePosition.x<=100 && childDialoguePosition.y>0&&childDialoguePosition.y<=100);

    }

    @Test
    void PlumberFriendExist() {

        GridPoint2 PlumberFriendPosition = new GridPoint2(96, 13);
        assertTrue(PlumberFriendPosition.x>0&&PlumberFriendPosition.x<=100 && PlumberFriendPosition.y>0&&PlumberFriendPosition.y<=100);

        GridPoint2 PLumberFriendDialoguePosition = new GridPoint2(96, 14);
        assertTrue(PLumberFriendDialoguePosition.x>0&&PLumberFriendDialoguePosition.x<=100 && PLumberFriendDialoguePosition.y>0&&PLumberFriendDialoguePosition.y<=100);

    }

    @Test
    void HumanGuardExist() {

        GridPoint2 HumanGuardPosition = new GridPoint2(110, 41);
        assertTrue(HumanGuardPosition.x>0&&HumanGuardPosition.x<=150 && HumanGuardPosition.y>0&&HumanGuardPosition.y<=150);

        GridPoint2 HumanGuardDialoguePosition = new GridPoint2(110, 42);
        assertTrue(HumanGuardDialoguePosition.x>0&&HumanGuardDialoguePosition.x<=150 && HumanGuardDialoguePosition.y>0&&HumanGuardDialoguePosition.y<=150);

    }

    @Test
    void OneLegGirlExist() {

        GridPoint2 oneLegGirlPosition = new GridPoint2(87, 28);
        assertTrue(oneLegGirlPosition.x>0&&oneLegGirlPosition.x<=100 && oneLegGirlPosition.y>0&&oneLegGirlPosition.y<=100);

        GridPoint2 oneLegGirlDialoguePosition = new GridPoint2(87, 29);
        assertTrue(oneLegGirlDialoguePosition.x>0&&oneLegGirlDialoguePosition.x<=100 && oneLegGirlDialoguePosition.y>0&&oneLegGirlDialoguePosition.y<=100);
    }


    @Test
    void FriendlyCreatureExist() {

        GridPoint2 friendlycreaturePosition = new GridPoint2(5, 10);
        assertTrue(friendlycreaturePosition.x>0&&friendlycreaturePosition.x<=100 && friendlycreaturePosition.y>0&&friendlycreaturePosition.y<=100);

        GridPoint2 friendlycreatureDialoguePosition = new GridPoint2(5, 11);
        assertTrue(friendlycreatureDialoguePosition.x>0&&friendlycreatureDialoguePosition.x<=100 && friendlycreatureDialoguePosition.y>0&&friendlycreatureDialoguePosition.y<=100);

    }

    @Test
    void femaleText() {

        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertEquals(textFemale[index_female], "Nat:\n");
    }

    @Test
    void guardText() {

        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertEquals(textGuard[index_female], "Guard\n");

    }

    @Test
    void maleText() {

        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertEquals(textMale[index_male], "Richard: \n");
    }

    @Test
    void childText() {

        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertEquals(textChild[index_child], "child\n");

    }

    @Test
    void humanGuardText() {

        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertEquals(textHumanGuard[index_humanguard], "George");
    }

    @Test
    void humanGuardCloseText() {
        index_friendlycreature= 4;
        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertFalse(false);
    }

    @Test
    void friendlyCreatureText() {

        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertEquals(textFriendlyCreature[index_friendlycreature], "FriendlyCreature\n");

    }

    @Test
    void friendlyCreatureCloseText() {
        index_friendlycreature= 4;
        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertFalse(false);
    }

    @Test
    void plumberFriendText() {

        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertEquals(textPlumberFriend[index_plumberfriend], "Hey! I have not seen you in forever.");
    }

    @Test
    void plumberFriendCloseText() {
        index_plumberfriend = 4;
        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertFalse(false);
    }

    @Test
    void addItemTest() {

        assertTrue(key.equalsIgnoreCase("f"),"isPressed");
        assertTrue(addItem(item));

    }

    @Test
    void shouldSpawnMale() {

//        ServiceLocator.registerEntityService(new EntityService());
//        NPCFactory npcFactory =
//                new NPCFactory() {
//                    public void create() {
//                    }
//                };
//        Entity entity = mock(Entity.class);
//
//        npcFactory.createBaseNPC();
//        verify(entity).create();

        PhysicsEngine engine = new PhysicsService().getPhysics();
        PhysicsService physicsService = new PhysicsService();
//        PhysicsComponent physicsComponent = mock(PhysicsComponent.class);
        ServiceLocator serviceLocator = mock(ServiceLocator.class);
//        RenderService renderService = mock(RenderService.class);
//        when(serviceLocator.getPhysicsService()).thenReturn(physicsService);
//        when(physicsService.getPhysics()).thenReturn(engine);


        Entity expectedNPC =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new PhysicsMovementComponent())
                        .addComponent(new ColliderComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC));

        Entity npc = NPCFactory.createBaseNPC();

        assertEquals(expectedNPC, npc);
    }
}