package com.deco2800.game.components.npc;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;

import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.factories.MaterialFactory;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.areas.ForestGameArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.HashMap;
import static com.deco2800.game.areas.ForestGameArea.*;
import static com.deco2800.game.areas.UndergroundGameArea.*;
import static com.deco2800.game.areas.UndergroundGameArea.GuardPosition;
import static com.deco2800.game.areas.UndergroundGameArea.friendlycreaturePosition;


/**
 * Displays a dialog box that allows the Player to interact with NPCs - Team 7 all-mid-npc
 */
public class DialogueDisplay extends UIComponent {

    InventoryComponent inventoryComponent;
    private static final Logger logger = LoggerFactory.getLogger(GameAreaDisplay.class);
    private static Image dialogueImagefemale;
    private static Image dialogueimageguard;
    private static Image dialogueimgchild;
    private static Image dialogueimagemale;
    private static Image dialogueimagehumanguard;
    private static Image dialogueimagefriendlycreature;
    private static Image dialogueimageplumberfriend;
    public int dialogueImageFemale = 0;
    public int dialogueImageChild = 1;
    public int dialogueImageGuard = 2;
    public int dialogueImageMale = 3;
    public int dialogueImageHumanGuard = 4;
    public int dialogueImageFriendlyCreature = 5;
    public int dialogueImagePlumberFriend = 6;
    private final HashMap<Integer, String> dialogueMap = new HashMap<Integer, String>() {
        {
            put(0, "images/NPC/Dialogue/dialoguesboxfemale2.png");
            put(1, "images/NPC/Dialogue/dialoguesboxchild2.png");
            put(2, "images/NPC/Dialogue/dialoguesboxguard2.png");
            put(3, "images/NPC/Dialogue/dialoguesboxmale2.png");
            put(4, "images/NPC/Dialogue/humanguarddialogue2.png");
            put(5, "images/NPC/Dialogue/friendlycreaturedialogue2.png");
            put(6, "images/NPC/Dialogue/plumberfriend2.png");
        }
    };
    static int countFemale = 0;
    static int countGuard = 0;
    static int countChild = 0;
    static int countMale = 0;
    static int countHumanGuard = 0;

    static int countHumanGuardAlready = 0;
    static int countPlumberFriendAlready = 0;
    static int countFriendlyCreature = 0;
    static int countFriendlyCreatureAlready = 0;
    static int countPlumberFriend = 0;
    public static Table dialogueContainerFemale;
    public static Table dialogueContainerGuard;
    public static Table dialogueContainerMale;
    public static Table dialogueContainerChild;
    public static Table dialogueContainerHumanGuard;
    public static Table dialogueContainerFriendlyCreature;
    public static Table dialogueContainerPlumberFriend;
    public static TextArea textAreaFemale;
    public static TextArea textAreaGuard;
    public static TextArea textAreaMale;
    public static TextArea textAreaChild;
    public static TextArea textAreaHumanGuard;
    public static TextArea textAreaHumanGuardAlready;
    public static TextArea textAreaPlumberFriendAlready;
    public static TextArea textAreaFriendlyCreature;
    public static TextArea textAreaFriendlyCreatureAlready;
    public static TextArea textAreaPlumberFriend;
    public static Boolean state = false;
    TextButton startButton = new TextButton("Start", skin);
    public int haveTalked = 0;
    public int haveTalkedPlumberFriend = 0;
    public int haveTalkedFriendlyCreature = 0;
    public static String[] textFemale = {
            "Nat:\n",
            "Oh good heavens, are you balding?\nThat's quite horrific.",
            "But I guess it does not matter,For a plumber like you.",
            "Now go away and fix some toilets.",
            ""
    };
    public static String[] textGuard = {
            "Guard: \n",
            "Have you seen anyone suspicious? ",
            "No? Okay then...",
            "Would you mind telling me your name?",
            "Hmm...okay",
            "A bit strange for a plumber to be",
            "roaming around right now..",
            ""

    };
    public static String[] textMale = {
            "Richard: \n",
            "What on Atlantis are you wearing?",
            "There’s a public restroom which probably needs fixing",
            "How about fixing that up instead of roaming around",
            ""
    };
    public static String[] textChild = {
            "Child: \n",
            "Hello…Rabbit says he has not seen you around before\n",
            "Your..hands..they are stained…brown\n",
            "I know someone who can help you \n",
            "You might want to speak to crocodile, they live in the drains of the city\n",
            ""
    };

    public static String[] textHumanGuard = {
            "George: \n",
            "Oh good, you are here!",
            "My hands are a bit full right now",
            "Would you mind holding onto this for me?",
            "There was a special mission today... Wait...",
            "...Mission...I can't remember what it was",
            ""
    };

    public static String[] textHumanGuardAlready = {
            "Why are you still here?",
            "LEAVE",
            "ME",
            "ALONE",
            ""
    };

    public static String[] textPlumberFriendAlready = {
            "Why are you still here?",
            "LEAVE",
            "ME",
            "ALONE",
            ""
    };
    public static String[] textFriendlyCreature = {
            "FriendlyCreature: \n",
            "Hm? Why the hell did u come here?",
            "Didn't I say never to come here again??!!!",
            "Whatever... sing me a song and I will give you something good",
            "As expected... anyway, a promise is a promise. Here, the legendary toilet paper.",
            "Now, LEAVE ME ALONE!!!",
    };
    public static String[] textFriendlyCreatureAlready = {
            "Why are you still here?",
            "LEAVE",
            "ME",
            "ALONE",
            ""
    };
    public static String[] textPlumberFriend = {
            "PlumberFriend: \n",
            "Hey! I have not seen you in forever.",
            "Are you still going ahead with your plan?",
            "What do you mean 'what plan'?",
            "You were gonna sink Atlantis",
            "Me? Come with you?",
            "Hmm...maybe it's best I don't go...",
            "Instead I'll help by keeping you out of any suspicions",
            "Also I found some poop after fixing a toilet",
            "I don't have any use for it so you can have it.",
            ""
    };

    @Override
    public void create() {
        //initialize the dialogue container for each NPCs
        dialogueContainerFemale = new Table();
        dialogueContainerFemale.setFillParent(true);
        dialogueContainerGuard = new Table();
        dialogueContainerGuard.setFillParent(true);
        dialogueContainerMale = new Table();
        dialogueContainerMale.setFillParent(true);
        dialogueContainerChild = new Table();
        dialogueContainerChild.setFillParent(true);
        dialogueContainerHumanGuard = new Table();
        dialogueContainerHumanGuard.setFillParent(true);
        dialogueContainerFriendlyCreature = new Table();
        dialogueContainerFriendlyCreature.setFillParent(true);
        dialogueContainerPlumberFriend = new Table();
        dialogueContainerPlumberFriend.setFillParent(true);

        // initialize the dialogue image for each NPCs
        dialogueImagefemale = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageFemale), Texture.class));
        dialogueImagefemale.setPosition((Gdx.graphics.getWidth()/2) - (dialogueImagefemale.getWidth()/2), 0);
        dialogueimageguard = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageGuard), Texture.class));
        dialogueimageguard.setPosition((Gdx.graphics.getWidth()/2) - (dialogueimageguard.getWidth()/2),0);
        dialogueimagemale = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageMale), Texture.class));
        dialogueimagemale.setPosition((Gdx.graphics.getWidth()/2) - (dialogueimagemale.getWidth()/2),0);
        dialogueimgchild = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageChild), Texture.class));
        dialogueimgchild.setPosition((Gdx.graphics.getWidth()/2) - (dialogueimgchild.getWidth()/2),0);
        dialogueimagehumanguard = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageHumanGuard), Texture.class));
        dialogueimagehumanguard.setPosition((Gdx.graphics.getWidth()/2) - (dialogueimagehumanguard.getWidth()/2),0);
        dialogueimagefriendlycreature = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageFriendlyCreature), Texture.class));
        dialogueimagefriendlycreature.setPosition((Gdx.graphics.getWidth()/2) - (dialogueimagefriendlycreature.getWidth()/2),0);
        dialogueimageplumberfriend = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImagePlumberFriend), Texture.class));
        //dialogueimageplumberfriend.setPosition(220,0);
        dialogueimageplumberfriend.setPosition((Gdx.graphics.getWidth()/2) - (dialogueimageplumberfriend.getWidth()/2),0);

        // initialize the text area for each NPCs
        textAreaFemale = new TextArea(textFemale[countFemale], skin, "female");
        textAreaFemale.setWidth(700);
        textAreaFemale.setHeight(70);
        textAreaFemale.setPosition((Gdx.graphics.getWidth()/2) - (textAreaFemale.getWidth()/2),29);
        textAreaGuard = new TextArea(textGuard[countGuard], skin, "guard");
        textAreaGuard.setWidth(700);
        textAreaGuard.setHeight(70);
        textAreaGuard.setPosition((Gdx.graphics.getWidth()/2) - (textAreaGuard.getWidth()/2),29);
        textAreaMale = new TextArea(textMale[countMale], skin, "male");
        textAreaMale.setWidth(700);
        textAreaMale.setHeight(70);
        textAreaMale.setPosition((Gdx.graphics.getWidth()/2) - (textAreaMale.getWidth()/2),29);
        textAreaChild = new TextArea(textChild[countChild], skin, "child");
        textAreaChild.setWidth(700);
        textAreaChild.setHeight(70);
        textAreaChild.setPosition((Gdx.graphics.getWidth()/2) - (textAreaChild.getWidth()/2),29);
        textAreaHumanGuard = new TextArea(textHumanGuard[countHumanGuard], skin, "humanguard");
        textAreaHumanGuard.setWidth(700);
        textAreaHumanGuard.setHeight(70);
        textAreaHumanGuard.setPosition((Gdx.graphics.getWidth()/2) - (textAreaHumanGuard.getWidth()/2),29);
        textAreaHumanGuardAlready = new TextArea(textHumanGuardAlready[countHumanGuardAlready], skin, "humanguard");
        textAreaHumanGuardAlready.setWidth(700);
        textAreaHumanGuardAlready.setHeight(70);
        textAreaHumanGuardAlready.setPosition((Gdx.graphics.getWidth()/2) - (textAreaHumanGuardAlready.getWidth()/2),29);
        textAreaFriendlyCreature = new TextArea(textFriendlyCreature[countFriendlyCreature], skin, "friendlycreature");
        textAreaFriendlyCreature.setWidth(700);
        textAreaFriendlyCreature.setHeight(88);
        textAreaFriendlyCreature.setPosition((Gdx.graphics.getWidth()/2) - (textAreaFriendlyCreature.getWidth()/2),29);
        textAreaFriendlyCreatureAlready = new TextArea(textFriendlyCreatureAlready[countFriendlyCreatureAlready], skin, "friendlycreature");
        textAreaFriendlyCreatureAlready.setWidth(700);
        textAreaFriendlyCreatureAlready.setHeight(88);
        textAreaFriendlyCreatureAlready.setPosition((Gdx.graphics.getWidth()/2) - (textAreaFriendlyCreatureAlready.getWidth()/2),29);
        textAreaPlumberFriend = new TextArea(textPlumberFriend[countPlumberFriend], skin, "plumberfriend");
        textAreaPlumberFriend.setWidth(700);
        textAreaPlumberFriend.setHeight(63);
        textAreaPlumberFriend.setPosition((Gdx.graphics.getWidth()/2) - (textAreaPlumberFriend.getWidth()/2),29);
        textAreaPlumberFriendAlready = new TextArea(textPlumberFriendAlready[countPlumberFriendAlready], skin, "plumberfriend");
        textAreaPlumberFriendAlready.setWidth(700);
        textAreaPlumberFriendAlready.setHeight(63);
        //textAreaPlumberFriendAlready.setPosition(240,29);
        textAreaPlumberFriendAlready.setPosition((Gdx.graphics.getWidth()/2) - (textAreaPlumberFriend.getWidth()/2), 29);

        // add dialogue image and dialogue text area to the container
        dialogueContainerFemale.addActor(dialogueImagefemale);
        dialogueContainerFemale.addActor(textAreaFemale);
        dialogueContainerGuard.addActor(dialogueimageguard);
        dialogueContainerGuard.addActor(textAreaGuard);
        dialogueContainerMale.addActor(dialogueimagemale);
        dialogueContainerMale.addActor(textAreaMale);
        dialogueContainerChild.addActor(dialogueimgchild);
        dialogueContainerChild.addActor(textAreaChild);
        dialogueContainerHumanGuard.addActor(dialogueimagehumanguard);
        dialogueContainerHumanGuard.addActor(textAreaHumanGuard);
        dialogueContainerFriendlyCreature.addActor(dialogueimagefriendlycreature);
        dialogueContainerFriendlyCreature.addActor(textAreaFriendlyCreature);
        dialogueContainerPlumberFriend.addActor(dialogueimageplumberfriend);
        dialogueContainerPlumberFriend.addActor(textAreaPlumberFriend);

        super.create();
        entity.getEvents().addListener("openDialogue", this::openDialogue);
        entity.getEvents().addListener("nextText", this::nextText);
        entity.getEvents().addListener("hideDialogue", this::hideDialogue);
    }

    public void nextText() {
        if ((oneLegGirlPosition) != null && entity.getCenterPosition().dst(GridPointToVector(oneLegGirlPosition)) < 1.5) {
            logger.info("new text loaded");
            countFemale++;
            textAreaFemale = new TextArea(textFemale[countFemale], skin, "female");
            textAreaFemale.setWidth(700);
            textAreaFemale.setHeight(50);
            textAreaFemale.setPosition((Gdx.graphics.getWidth()/2) - (textAreaFemale.getWidth()/2),29);
            dialogueContainerFemale.addActor(textAreaFemale);
            if (countFemale == textFemale.length - 1) {
                countFemale = 0;
                dialogueContainerFemale.remove();
            } else if (countFemale == 1) {
                logger.info("female1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/FemaleLines/FemaleLine32 (3).wav"));
                music.play();
            } else if (countFemale == 2) {
                logger.info("female2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/FemaleLines/FemaleLine33.wav"));
                music.play();
            } else if (countFemale == 3) {
                logger.info("female3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/FemaleLines/FemaleLine34.wav"));
                music.play();
            }
        } else if ((GuardPosition) != null && entity.getCenterPosition().dst(GridPointToVector(GuardPosition)) < 1.5) {
            logger.info("new text loaded");
            countGuard++;
            textAreaGuard = new TextArea(textGuard[countGuard], skin, "guard");
            textAreaGuard.setWidth(700);
            textAreaGuard.setHeight(50);
            textAreaGuard.setPosition((Gdx.graphics.getWidth()/2) - (textAreaGuard.getWidth()/2),29);
            dialogueContainerGuard.addActor(textAreaGuard);

            if (countGuard == textGuard.length - 1) {
                countGuard = 0;
                dialogueContainerGuard.remove();

            } else if (countGuard == 1) {
                logger.info("Guard1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/GuardLines/Guard Audio 1.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countGuard == 2) {
                logger.info("Guard2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/GuardLines/Guard Audio 2.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countGuard == 3) {
                logger.info("Guard3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/GuardLines/Guard Audio 3.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countGuard == 4) {
                logger.info("Guard4 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/GuardLines/Guard Audio 4.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countGuard == 5) {
                logger.info("Guard5 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Guard Audio 5.wav"));
                music.setVolume(0.3f);
                music.play();
            }
        } else if ((maleCitizenPosition) != null && entity.getCenterPosition().dst(GridPointToVector(maleCitizenPosition)) < 1.5) {
            logger.info("new text loaded");
            countMale++;
            textAreaMale = new TextArea(textMale[countMale], skin, "male");
            textAreaMale.setWidth(700);
            textAreaMale.setHeight(50);
            textAreaMale.setPosition((Gdx.graphics.getWidth()/2) - (textAreaMale.getWidth()/2),29);
            dialogueContainerMale.addActor(textAreaMale);
            if (countMale == textMale.length - 1) {
                countMale = 0;
                dialogueContainerMale.remove();
            } else if (countMale == 1) {
                logger.info("Male1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Male Citizen Audio/Male Citizen 1.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countMale == 2) {
                logger.info("Male2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Male Citizen Audio/Male Citizen 2.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countMale == 3) {
                logger.info("Male3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Male Citizen Audio/Male Citizen 3.wav"));
                music.setVolume(0.3f);
                music.play();
            }
        } else if ((childPosition) != null && entity.getCenterPosition().dst(GridPointToVector(childPosition)) < 1.5) {
            logger.info("new text loaded");
            countChild++;
            textAreaChild = new TextArea(textChild[countChild], skin, "child");
            textAreaChild.setWidth(700);
            textAreaChild.setHeight(50);
            textAreaChild.setPosition((Gdx.graphics.getWidth()/2) - (textAreaChild.getWidth()/2),29);
            dialogueContainerChild.addActor(textAreaChild);
            if (countChild == textChild.length - 1) {
                countChild = 0;
                dialogueContainerChild.remove();
            } else if (countChild == 1) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Dialogue/ChildLines/ChildLine10 (2).wav"));
                sound.play(1.0f);
            } else if (countChild == 2) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Dialogue/ChildLines/ChildLine11.wav"));
                sound.play(1.0f);
            } else if (countChild == 3) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Dialogue/ChildLines/ChildLine12.wav"));
                sound.play(1.0f);
            } else if (countChild == 4) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Dialogue/ChildLines/ChildLine13.wav"));
                sound.play(1.0f);
            }


        } else if ((HumanGuardPosition) != null && entity.getCenterPosition().dst(GridPointToVector(HumanGuardPosition)) < 1.5 && haveTalked==0) {
            logger.info("new text loaded");
            countHumanGuard++;
            textAreaHumanGuard = new TextArea(textHumanGuard[countHumanGuard], skin, "humanguard");
            textAreaHumanGuard.setWidth(700);
            textAreaHumanGuard.setHeight(50);
            textAreaHumanGuard.setPosition((Gdx.graphics.getWidth()/2) - (textAreaHumanGuard.getWidth()/2),29);
            dialogueContainerHumanGuard.addActor(textAreaHumanGuard);

            if (countHumanGuard == textHumanGuard.length - 1) {
                countHumanGuard = 0;
                dialogueContainerHumanGuard.remove();
                haveTalked = 1;
            } else if (countHumanGuard == 1) {
                logger.info("HumanGuard1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Human Guard/Human Guard 1.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countHumanGuard == 2) {
                logger.info("HumanGuard2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Human Guard/Human_Guard_2.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countHumanGuard == 3) {
                logger.info("HumanGuard3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Human Guard/Human_Guard_3.wav"));
                music.setVolume(0.3f);
                music.play();

                inventoryComponent = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
                inventoryComponent.addItem(MaterialFactory.createRubber());
                inventoryComponent.addItem(MaterialFactory.createPlastic());


            } else if (countHumanGuard == 4) {
                logger.info("HumanGuard4 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Human Guard/Human_Guard_4.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countHumanGuard == 5) {
                logger.info("HumanGuard5 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Human Guard/Human_Guard_5.wav"));
                music.setVolume(0.3f);
                music.play();
            }

        } else if ((HumanGuardPosition) != null && entity.getCenterPosition().dst(GridPointToVector(HumanGuardPosition)) < 1.5 && haveTalked == 1) {
            logger.info("new text loaded");
            countHumanGuardAlready++;
            textAreaHumanGuardAlready = new TextArea(textHumanGuardAlready[countHumanGuardAlready], skin, "humanguard");
            textAreaHumanGuardAlready.setWidth(700);
            textAreaHumanGuardAlready.setHeight(50);
            textAreaHumanGuardAlready.setPosition((Gdx.graphics.getWidth()/2) - (textAreaHumanGuardAlready.getWidth()/2),29);
            dialogueContainerHumanGuard.addActor(textAreaHumanGuardAlready);

            if (countHumanGuardAlready == textHumanGuardAlready.length - 1) {
                countHumanGuardAlready = 0;
                dialogueContainerHumanGuard.remove();
            } else if (countHumanGuardAlready == 1) {
                logger.info("HumanGuard1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/LEAVE.wav"));
                music.play();
            } else if (countHumanGuardAlready == 2) {
                logger.info("HumanGuard2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/ME.wav"));
                music.play();
            } else if (countHumanGuardAlready == 3) {
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/ALONE.wav"));
                music.play();
            } else if (countHumanGuard == 4) {
                logger.info("HumanGuard4 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/ALONE.wav"));
                music.play();
            }
        } else if ((friendlycreaturePosition) != null && entity.getCenterPosition().dst(GridPointToVector(friendlycreaturePosition)) < 1.5  && haveTalkedFriendlyCreature == 0) {
            logger.info("new text loaded");
            countFriendlyCreature++;
            textAreaFriendlyCreature = new TextArea(textFriendlyCreature[countFriendlyCreature], skin, "friendlycreature");
            textAreaFriendlyCreature.setWidth(700);
            textAreaFriendlyCreature.setHeight(68);
            textAreaFriendlyCreature.setPosition((Gdx.graphics.getWidth()/2) - (textAreaFriendlyCreature.getWidth()/2),29);
            dialogueContainerFriendlyCreature.addActor(textAreaFriendlyCreature);
            if (countFriendlyCreature == textFriendlyCreature.length - 1) {
                countFriendlyCreature = 0;
                dialogueContainerFriendlyCreature.remove();
                haveTalkedFriendlyCreature = 1;
            } else if (countFriendlyCreature == 1) {
                logger.info("FriendlyCreature1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/FriendlyCreature/Line_1.wav"));
                music.play();
            } else if (countFriendlyCreature == 2) {
                logger.info("FriendlyCreature2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/FriendlyCreature/Line_2.wav"));
                music.play();

            } else if (countFriendlyCreature == 3) {
                logger.info("FriendlyCreature3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/FriendlyCreature/Line_3.wav"));
                music.play();
                dialogueContainerFriendlyCreature.addActor(startButton);
                startButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        AudioRecorder recorder = Gdx.audio.newAudioRecorder(44100,true);
                        short[] audioBuffer = new short[44100 * 15];
                        recorder.read(audioBuffer, 0, audioBuffer.length);

                        AudioDevice audioDevice = Gdx.audio.newAudioDevice(44100, true);
                        audioDevice.writeSamples(audioBuffer, 0, audioBuffer.length);
                        System.out.println(Arrays.toString(audioBuffer));
                        recorder.dispose();
                        audioDevice.dispose();
                    };
                });
            } else if (countFriendlyCreature == 4) {
                dialogueContainerFriendlyCreature.removeActor(startButton);
                logger.info("FriendlyCreature4 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/FriendlyCreature/Line_4.wav"));
                music.play();
                inventoryComponent = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
                inventoryComponent.addItem(MaterialFactory.createToiletPaper());

            } else if(countFriendlyCreature == 5) {
                logger.info("FriendlyCreature5 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/FriendlyCreature/Line_5.wav"));
                music.play();
            }

        } else if ((friendlycreaturePosition) != null && entity.getCenterPosition().dst(GridPointToVector(friendlycreaturePosition)) < 1.5 && haveTalkedFriendlyCreature == 1) {
            logger.info("new text loaded");

            countFriendlyCreatureAlready++;
            textAreaFriendlyCreatureAlready = new TextArea(textFriendlyCreatureAlready[countFriendlyCreatureAlready], skin, "friendlycreature");
            textAreaFriendlyCreatureAlready.setWidth(700);
            textAreaFriendlyCreatureAlready.setHeight(68);
            textAreaFriendlyCreatureAlready.setPosition((Gdx.graphics.getWidth() / 2) - (textAreaFriendlyCreatureAlready.getWidth() / 2), 29);
            dialogueContainerFriendlyCreature.addActor(textAreaFriendlyCreatureAlready);

        } if (countFriendlyCreatureAlready == textFriendlyCreatureAlready.length - 1) {
                countFriendlyCreatureAlready = 0;
                dialogueContainerFriendlyCreature.remove();
            } else if (countFriendlyCreatureAlready == 1) {
                logger.info("FriendlyCreature1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/LEAVE.wav"));
                music.play();
            } else if (countFriendlyCreatureAlready == 2) {
                logger.info("FriendlyCreature2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/ME.wav"));
                music.play();
            } else if (countFriendlyCreatureAlready== 3) {
                logger.info("FriendlyCreature3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/ALONE.wav"));
                music.play();
            } else if (countFriendlyCreature == 4) {
                logger.info("FriendlyCreature4 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/ALONE.wav"));
                music.play();

        } else if ((PlumberFriendPosition) != null && entity.getCenterPosition().dst(GridPointToVector(PlumberFriendPosition)) < 1.5 && haveTalkedPlumberFriend == 0) {

            logger.info("new text loaded");
            countPlumberFriend++;
            textAreaPlumberFriend = new TextArea(textPlumberFriend[countPlumberFriend], skin, "plumberfriend");
            textAreaPlumberFriend.setWidth(700);
            textAreaPlumberFriend.setHeight(43);
            textAreaPlumberFriend.setPosition((Gdx.graphics.getWidth()/2) - (textAreaPlumberFriend.getWidth()/2),29);
            dialogueContainerPlumberFriend.addActor(textAreaPlumberFriend);
            if (countPlumberFriend == textPlumberFriend.length - 1) {
                haveTalkedPlumberFriend = 1;
                countPlumberFriend = 0;
                dialogueContainerPlumberFriend.remove();
                haveTalkedPlumberFriend = 1;
            } else if (countPlumberFriend == 1) {
                logger.info("PlumberFriend1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 1.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countPlumberFriend == 2) {
                logger.info("PlumberFriend2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 2.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countPlumberFriend == 3) {
                logger.info("PlumberFriend3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 3.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countPlumberFriend == 4) {
                logger.info("PlumberFriend4 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 4.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countPlumberFriend == 5) {
                logger.info("PlumberFriend5 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 5.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countPlumberFriend == 6) {
                logger.info("PlumberFriend6 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 6.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countPlumberFriend == 7) {
                logger.info("PlumberFriend7 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 7.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countPlumberFriend == 8) {
                logger.info("PlumberFriend8 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 8.wav"));
                music.setVolume(0.3f);
                music.play();
            } else if (countPlumberFriend == 9) {
                logger.info("PlumberFriend9 sound displayed");
                inventoryComponent = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
                inventoryComponent.addItem(MaterialFactory.createPoop());
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 9.wav"));
                music.setVolume(0.3f);
                music.play();

            }
        } else if ((PlumberFriendPosition) != null && entity.getCenterPosition().dst(GridPointToVector(PlumberFriendPosition)) < 1.5 && haveTalkedPlumberFriend == 1) {
            logger.info("new text loaded");
            countPlumberFriendAlready++;
            textAreaPlumberFriendAlready = new TextArea(textPlumberFriendAlready[countPlumberFriendAlready], skin, "plumberfriend");
            textAreaPlumberFriendAlready.setWidth(700);
            textAreaPlumberFriendAlready.setHeight(43);
            textAreaPlumberFriendAlready.setPosition((Gdx.graphics.getWidth()/2) - (textAreaPlumberFriendAlready.getWidth()/2),29);
            dialogueContainerPlumberFriend.addActor(textAreaPlumberFriendAlready);

            if (countPlumberFriendAlready == textPlumberFriendAlready.length - 1) {
                countPlumberFriendAlready = 0;
                dialogueContainerPlumberFriend.remove();
            } else if (countPlumberFriendAlready == 1) {
                logger.info("HumanGuard1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/LEAVE.wav"));
                music.play();
            } else if (countPlumberFriendAlready == 2) {
                logger.info("PlumberFriend2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/ME.wav"));
                music.play();
            } else if (countPlumberFriendAlready == 3) {
                logger.info("PlumberFriend3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/ALONE.wav"));
                music.play();
            } else if (countPlumberFriendAlready == 4) {
                logger.info("PlumberFriend4 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/ALONE.wav"));
                music.play();

            }
        }
    }
    public void openDialogue() {

        if (state) {
            if ((oneLegGirlPosition) != null && entity.getCenterPosition().dst(GridPointToVector(oneLegGirlPosition)) < 1.5) {
                logger.info("dialogue closed manually");
                stage.addActor(dialogueContainerFemale);
                state = false;
            } else if ((GuardPosition) != null && entity.getCenterPosition().dst(GridPointToVector(GuardPosition)) < 1.5) {
                logger.info("dialogue closed manually");
                stage.addActor(dialogueContainerGuard);
                state = false;
            } else if ((maleCitizenPosition) != null && entity.getCenterPosition().dst(GridPointToVector(maleCitizenPosition)) < 1.5) {
                logger.info("dialogue closed manually");
                stage.addActor(dialogueContainerMale);
                state = false;
            } else if ((childPosition) != null && entity.getCenterPosition().dst(GridPointToVector(childPosition)) < 1.5) {
                logger.info("dialogue closed manually");
                stage.addActor(dialogueContainerChild);
                state = false;
            } else if ((HumanGuardPosition) != null && entity.getCenterPosition().dst(GridPointToVector(HumanGuardPosition)) < 1.5) {
                logger.info("dialogue closed manually");
                stage.addActor(dialogueContainerHumanGuard);
                state = false;
            } else if ((PlumberFriendPosition) != null && entity.getCenterPosition().dst(GridPointToVector(PlumberFriendPosition)) < 1.5) {
                logger.info("dialogue closed manually");
                System.out.println(haveTalkedPlumberFriend);
                stage.addActor(dialogueContainerPlumberFriend);
                state = false;
            } else if ((friendlycreaturePosition) != null && entity.getCenterPosition().dst(GridPointToVector(friendlycreaturePosition)) < 1.5) {
                logger.info("dialogue closed manually");
                stage.addActor(dialogueContainerFriendlyCreature);
                state = false;
            }

        }
    }
    //hide dialogue function
    public void hideDialogue() {
        if (!state && (oneLegGirlPosition) != null && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(oneLegGirlPosition)) < 1.5) {
            dialogueContainerFemale.remove();
            state = true;
        } else if (!state && (GuardPosition) != null && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(GuardPosition)) < 1.5) {
            dialogueContainerGuard.remove();
            state = true;
        } else if (!state && (maleCitizenPosition) != null && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(maleCitizenPosition)) < 1.5) {
            dialogueContainerMale.remove();
            state = true;
        } else if (!state && (childPosition) != null && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(childPosition)) < 1.5) {
            dialogueContainerChild.remove();
            state = true;
        } else if (!state && (HumanGuardPosition) != null && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(HumanGuardPosition)) < 1.5) {
            dialogueContainerHumanGuard.remove();
            state = true;
        } else if (!state && (PlumberFriendPosition) != null && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(PlumberFriendPosition)) < 1.5) {
            dialogueContainerPlumberFriend.remove();
            state = true;
        } else if (!state && (friendlycreaturePosition) != null && entity.getCenterPosition().dst(GridPointToVector(friendlycreaturePosition)) < 1.5) {
            dialogueContainerFriendlyCreature.remove();
            state = true;
        }

    }
    @Override
    public void draw(SpriteBatch batch) {

    }
    @Override
    public void dispose() {

        super.dispose();
    }
}
