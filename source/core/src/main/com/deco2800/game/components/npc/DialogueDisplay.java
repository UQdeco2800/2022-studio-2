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
import static com.deco2800.game.areas.UndergroundGameArea.getFriendlycreaturePosition;



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
            put(0, "images/NPC/Dialogue/dialoguesboxfemale.png");
            put(1, "images/NPC/Dialogue/dialoguesboxchild.png");
            put(2, "images/NPC/Dialogue/dialoguesboxguard.png");
            put(3, "images/NPC/Dialogue/dialoguesboxmale.png");
            put(4, "images/NPC/Dialogue/HumanGuardDialogue.png");
            put(5, "images/NPC/Dialogue/FriendlyCreatureDialogue.png");
            put(6, "images/NPC/Dialogue/PlumberFriend.png");
        }
    };
    static int countFemale = 0;
    static int countGuard = 0;
    static int countChild = 0;
    static int countMale = 0;
    static int countHumanGuard = 0;
    static int countFriendlyCreature = 0;
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
    public static TextArea textAreaFriendlyCreature;
    public static TextArea textAreaPlumberFriend;
    public static Boolean state = false;
    public static TextButton childButton = new TextButton("yes", skin);
    public static String[] textFemale = {
            "Nat:\n",
            "Oh good heavens, are you balding?\n",
            "That's quite horrific.",
            "But I guess it does not matter,",
            "For a plumber like you.",
            "Now go away and fix some toilets.",
            ""
    };
    public static String[] textGuard = {
            "Guard\n",
            "Have you seen anyone suspicious? ",
            "No? Okay then...",
            "Would you mind telling me your name?",
            "Hmm...okay",
            "A bit strange for a plumber to be",
            "roaming around right now..",

    };
    public static String[] textMale = {
            "Richard: \n",
            "What on Atlantis are you wearing?",
            "There’s a public restroom which probably needs fixing",
            "How about fixing that up instead of roaming around",
            ""
    };
    public static String[] textChild = {
            "child\n",
            "Hello…Rabbit says he has not seen you around before\n",
            "Your..hands..they are stained…brown\n",
            "I know someone who can help you \n",
            "You might want to speak to crocodile, they live in the drains of the city\n",
            "5",
            "6",
            "7"
    };
    public static String[] textHumanGuard = {
            "George",
            "Oh good, you are here!",
            "My hands are a bit full right now",
            "Would you mind holding onto this for me?",
            "There was a special mission today… Wait…",
            "….Mission…I can’t remember what it was",
            ""
    };
    public static String[] textFriendlyCreature = {
            "FriendlyCreature\n",
            "1",
            "2",
            "3",
            "4"
    };
    public static String[] textPlumberFriend = {
            "PlumberFriend\n",
            "Hey! I have not seen you in forever.",
            "Are you still going ahead with your plan?",
            "What do you mean ‘what plan’?",
            "You were gonna sink Atlantis",
            "Me? Come with you?",
            "Hmm…maybe it’s best I don’t go..",
            "Instead I’ll help by keeping you out of any suspicions",
            "Also I found some poop after fixing a toilet",
            "I don’t have any use for it so you can have it.",
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
        dialogueimageguard = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageGuard), Texture.class));
        dialogueimagemale = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageMale), Texture.class));
        dialogueimgchild = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageChild), Texture.class));
        dialogueimagehumanguard = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageHumanGuard), Texture.class));
        dialogueimagefriendlycreature = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageFriendlyCreature), Texture.class));
        dialogueimageplumberfriend = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImagePlumberFriend), Texture.class));

        // initialize the text area for each NPCs
        textAreaFemale = new TextArea(textFemale[countFemale], skin);
        textAreaFemale.setWidth(480);
        textAreaFemale.setHeight(50);
        textAreaGuard = new TextArea(textGuard[countGuard], skin);
        textAreaGuard.setWidth(480);
        textAreaGuard.setHeight(50);
        textAreaMale = new TextArea(textMale[countMale], skin);
        textAreaMale.setWidth(480);
        textAreaMale.setHeight(50);
        textAreaChild = new TextArea(textChild[countChild], skin);
        textAreaChild.setWidth(480);
        textAreaChild.setHeight(50);
        textAreaHumanGuard = new TextArea(textHumanGuard[countHumanGuard], skin);
        textAreaHumanGuard.setWidth(480);
        textAreaHumanGuard.setHeight(50);
        textAreaFriendlyCreature = new TextArea(textFriendlyCreature[countFriendlyCreature], skin);
        textAreaFriendlyCreature.setWidth(480);
        textAreaFriendlyCreature.setHeight(50);
        textAreaPlumberFriend = new TextArea(textPlumberFriend[countPlumberFriend], skin);
        textAreaPlumberFriend.setWidth(480);
        textAreaPlumberFriend.setHeight(50);

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
            textAreaFemale = new TextArea(textFemale[countFemale], skin);
            textAreaFemale.setWidth(400);
            dialogueContainerFemale.addActor(textAreaFemale);
            if (countFemale == textFemale.length - 1) {
                countFemale = 0;
                dialogueContainerFemale.remove();
            }
        } else if ((GuardPosition) != null && entity.getCenterPosition().dst(GridPointToVector(GuardPosition)) < 1.5) {
            logger.info("new text loaded");
            countGuard++;
            textAreaGuard = new TextArea(textGuard[countGuard], skin);
            textAreaGuard.setWidth(400);
            dialogueContainerGuard.addActor(textAreaGuard);

            if (countGuard == textGuard.length - 1) {
                countGuard = 0;
                dialogueContainerGuard.remove();

            } else if (countGuard == 1) {
                logger.info("Guard1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/GuardLines/Guard Audio 1.wav"));
                music.play();
            } else if (countGuard == 2) {
                logger.info("Guard2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/GuardLines/Guard Audio 2.wav"));
                music.play();
            } else if (countGuard == 3) {
                logger.info("Guard3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/GuardLines/Guard Audio 3.wav"));
                music.play();
            } else if (countGuard == 4) {
                logger.info("Guard4 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/GuardLines/Guard Audio 4.wav"));
                music.play();
            } else if (countGuard == 5) {
                logger.info("Guard5 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Guard Audio 5.wav"));

                music.play();
            }
        } else if ((maleCitizenPosition) != null && entity.getCenterPosition().dst(GridPointToVector(maleCitizenPosition)) < 1.5) {
            logger.info("new text loaded");
            countMale++;
            textAreaMale = new TextArea(textMale[countMale], skin);
            textAreaMale.setWidth(400);
            dialogueContainerMale.addActor(textAreaMale);
            if (countMale == textMale.length - 1) {
                countMale = 0;
                dialogueContainerMale.remove();
            } else if (countMale == 1) {
                logger.info("Male1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Male Citizen Audio/Male Citizen 1.wav"));
                music.play();
            } else if (countMale == 2) {
                logger.info("Male2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Male Citizen Audio/Male Citizen 2.wav"));
                music.play();
            } else if (countMale == 3) {
                logger.info("Male3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Male Citizen Audio/Male Citizen 3.wav"));
                music.play();
            }
        } else if ((childPosition) != null && entity.getCenterPosition().dst(GridPointToVector(childPosition)) < 1.5) {
            logger.info("new text loaded");
            countChild++;
            textAreaChild = new TextArea(textChild[countChild], skin);
            textAreaChild.setWidth(480);
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
            } else if (countChild == 5) {
                dialogueContainerChild.removeActor(childButton);
                TextButton startButton = new TextButton("Start", skin);
                dialogueContainerChild.addActor(startButton);
                startButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        AudioRecorder recorder = Gdx.audio.newAudioRecorder(44100,true);
                        short[] audioBuffer = new short[44100 * 5];
                        recorder.read(audioBuffer, 0, audioBuffer.length);

                        AudioDevice audioDevice = Gdx.audio.newAudioDevice(44100, true);
                        audioDevice.writeSamples(audioBuffer, 0, audioBuffer.length);
                        System.out.println(Arrays.toString(audioBuffer));
                        recorder.dispose();
                        audioDevice.dispose();
                    };
                });


            } else if (countChild == 6) {
                dialogueContainerChild.addActor(childButton);
                childButton.addListener( new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
                        sound.play(1.0f);
                        inventoryComponent = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
                        inventoryComponent.addItem(MaterialFactory.createToiletPaper());
                    };
                });
            }

        } else if ((HumanGuardPosition) != null && entity.getCenterPosition().dst(GridPointToVector(HumanGuardPosition)) < 1.5) {
            logger.info("new text loaded");
            countHumanGuard++;
            textAreaHumanGuard = new TextArea(textHumanGuard[countHumanGuard], skin);
            textAreaHumanGuard.setWidth(400);
            dialogueContainerHumanGuard.addActor(textAreaHumanGuard);
            if (countHumanGuard == textHumanGuard.length - 1) {
                countHumanGuard = 0;
                dialogueContainerHumanGuard.remove();
            } else if (countHumanGuard == 1) {
                logger.info("HumanGuard1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Human Guard/Human Guard 1.wav"));
                music.play();
            } else if (countHumanGuard == 2) {
                logger.info("HumanGuard2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Human Guard/Human_Guard_2.wav"));
                music.play();
            } else if (countHumanGuard == 3) {

                inventoryComponent = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
                inventoryComponent.addItem(MaterialFactory.createRubber());
                inventoryComponent.addItem(MaterialFactory.createPlastic());

                logger.info("HumanGuard3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Human Guard/Human_Guard_3.wav"));
                music.play();
            } else if (countHumanGuard == 4) {
                logger.info("HumanGuard4 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Human Guard/Human_Guard_4.wav"));
                music.play();
            } else if (countHumanGuard == 5) {
                logger.info("HumanGuard5 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Human Guard/Human_Guard_5.wav"));
                music.play();
            }

        } else if ((friendlycreaturePosition) != null && entity.getCenterPosition().dst(GridPointToVector(friendlycreaturePosition)) < 1.5) {
            logger.info("new text loaded");
            countFriendlyCreature++;
            textAreaFriendlyCreature = new TextArea(textFriendlyCreature[countFriendlyCreature], skin);
            textAreaFriendlyCreature.setWidth(400);
            dialogueContainerFriendlyCreature.addActor(textAreaFriendlyCreature);
            if (countFriendlyCreature == textFriendlyCreature.length - 1) {
                countFriendlyCreature = 0;
                dialogueContainerFriendlyCreature.remove();
            } else if (countFriendlyCreature == 1) {
                inventoryComponent = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
                inventoryComponent.addItem(MaterialFactory.createToiletPaper());
            }

        } else if ((PlumberFriendPosition) != null && entity.getCenterPosition().dst(GridPointToVector(PlumberFriendPosition)) < 1.5) {
            logger.info("new text loaded");
            countPlumberFriend++;
            textAreaPlumberFriend = new TextArea(textPlumberFriend[countPlumberFriend], skin);
            textAreaPlumberFriend.setWidth(400);
            dialogueContainerPlumberFriend.addActor(textAreaPlumberFriend);
            if (countPlumberFriend == textPlumberFriend.length - 1) {
                countPlumberFriend = 0;
                dialogueContainerPlumberFriend.remove();
            } else if (countPlumberFriend == 1) {
                logger.info("PlumberFriend1 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 1.wav"));
                music.play();
            } else if (countPlumberFriend == 2) {
                logger.info("PlumberFriend2 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 2.wav"));
                music.play();
            } else if (countPlumberFriend == 3) {
                logger.info("PlumberFriend3 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 3.wav"));
                music.play();
            } else if (countPlumberFriend == 4) {
                logger.info("PlumberFriend4 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 4.wav"));
                music.play();
            } else if (countPlumberFriend == 5) {
                logger.info("PlumberFriend5 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 5.wav"));
                music.play();
            } else if (countPlumberFriend == 6) {
                logger.info("PlumberFriend6 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 6.wav"));
                music.play();
            } else if (countPlumberFriend == 7) {
                logger.info("PlumberFriend7 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 7.wav"));
                music.play();
            } else if (countPlumberFriend == 8) {
                logger.info("PlumberFriend8 sound displayed");
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 8.wav"));
                music.play();
            } else if (countPlumberFriend == 9) {
                logger.info("PlumberFriend9 sound displayed");
                inventoryComponent = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
                inventoryComponent.addItem(MaterialFactory.createPoop());
                Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Dialogue/Plumber Friend Audio/Plumber Friend 9.wav"));
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