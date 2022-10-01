package com.deco2800.game.components.npc;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.FloatArray;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.areas.ForestGameArea;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import static com.deco2800.game.areas.ForestGameArea.*;


/**
 * Displays a dialog box that allows the Player to interact with NPCs - Team 7 all-mid-npc
 */
public class DialogueDisplay extends UIComponent {

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

    private HashMap<Integer, String> dialogueMap = new HashMap<Integer, String>() {
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

    public static String[] textFemale = {
            "Nat:\n",
            "Oh good heavens, are you balding?\n",
            "That's quite horrific.",
            "But I guess it does not matter,",
            "For a plumber like you.",
            "Now go away and fix some toilets.",
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
            "1",
            "2",
            "I will give you something",
            "4",
            "5",
            "6"
    };

    public static String[] textChild = {
            "child\n",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7"
    };

    public static String[] textHumanGuard = {
            "George\n",
            "1",
            "2",
            "3",
            "4"
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
            "1",
            "2",
            "3",
            "4"
    };


    @Override
    public void create() {
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


        if (entity.getCenterPosition().dst(GridPointToVector(oneLegGirlPosition)) < 1.5) {
            countFemale++;
            textAreaFemale = new TextArea(textFemale[countFemale], skin);
            textAreaFemale.setWidth(480);
            dialogueContainerFemale.addActor(textAreaFemale);
            if (countFemale == textFemale.length - 1) {
                countFemale = 0;
                dialogueContainerFemale.remove();
            }
        } else if (entity.getCenterPosition().dst(GridPointToVector(GuardPosition)) < 1.5) {
            countGuard++;
            textAreaGuard = new TextArea(textGuard[countGuard], skin);
            textAreaGuard.setWidth(400);
            dialogueContainerGuard.addActor(textAreaGuard);

            if (countGuard == textGuard.length - 1) {
                countGuard = 0;
                dialogueContainerGuard.remove();
            }
        } else if (entity.getCenterPosition().dst(GridPointToVector(maleCitizenPosition)) < 1.5) {
            countMale++;
            textAreaMale = new TextArea(textMale[countMale], skin);
            textAreaMale.setWidth(400);
            dialogueContainerMale.addActor(textAreaMale);
            if (countMale == textMale.length - 1) {
                countMale = 0;
                dialogueContainerMale.remove();
            }
        } else if (entity.getCenterPosition().dst(GridPointToVector(childPosition)) < 1.5) {
            countChild++;
            textAreaChild = new TextArea(textChild[countChild], skin);
            textAreaChild.setWidth(400);
            dialogueContainerChild.addActor(textAreaChild);
            if (countChild == textChild.length - 1) {
                countChild = 0;
                dialogueContainerChild.remove();
            } else if (countChild >= 4) {
                TextButton childButton = new TextButton("yes", skin);
                dialogueContainerChild.addActor(childButton);

            }

        } else if (entity.getCenterPosition().dst(GridPointToVector(HumanGuardPosition)) < 1.5) {
            countHumanGuard++;
            textAreaHumanGuard = new TextArea(textHumanGuard[countHumanGuard], skin);
            textAreaHumanGuard.setWidth(400);
            dialogueContainerHumanGuard.addActor(textAreaHumanGuard);
            if (countHumanGuard == textHumanGuard.length - 1) {
                countHumanGuard = 0;
                dialogueContainerHumanGuard.remove();
            }

        } else if (entity.getCenterPosition().dst(GridPointToVector(friendlycreaturePosition)) < 1.5) {
            countFriendlyCreature++;
            textAreaFriendlyCreature = new TextArea(textFriendlyCreature[countFriendlyCreature], skin);
            textAreaFriendlyCreature.setWidth(400);
            dialogueContainerFriendlyCreature.addActor(textAreaFriendlyCreature);
            if (countFriendlyCreature == textFriendlyCreature.length - 1) {
                countFriendlyCreature = 0;
                dialogueContainerFriendlyCreature.remove();
            }

        } else if (entity.getCenterPosition().dst(GridPointToVector(PlumberFriendPosition)) < 1.5) {
            countPlumberFriend++;
            textAreaPlumberFriend = new TextArea(textPlumberFriend[countPlumberFriend], skin);
            textAreaPlumberFriend.setWidth(400);
            dialogueContainerPlumberFriend.addActor(textAreaPlumberFriend);
            if (countPlumberFriend == textPlumberFriend.length - 1) {
                countPlumberFriend = 0;
                dialogueContainerPlumberFriend.remove();
            }

        }
    }
    public void openDialogue() {

        if (state) {
            if (entity.getCenterPosition().dst(GridPointToVector(oneLegGirlPosition)) < 1.5) {
                stage.addActor(dialogueContainerFemale);
                state = false;
            } else if (entity.getCenterPosition().dst(GridPointToVector(GuardPosition)) < 1.5) {
                stage.addActor(dialogueContainerGuard);
                state = false;
            } else if (entity.getCenterPosition().dst(GridPointToVector(maleCitizenPosition)) < 1.5) {
                stage.addActor(dialogueContainerMale);
                state = false;
            } else if (entity.getCenterPosition().dst(GridPointToVector(childPosition)) < 1.5) {
                stage.addActor(dialogueContainerChild);
                state = false;
            } else if (entity.getCenterPosition().dst(GridPointToVector(HumanGuardPosition)) < 1.5) {
                stage.addActor(dialogueContainerHumanGuard);
                state = false;
            } else if (entity.getCenterPosition().dst(GridPointToVector(PlumberFriendPosition)) < 1.5) {
                stage.addActor(dialogueContainerPlumberFriend);
                state = false;
            } else if (entity.getCenterPosition().dst(GridPointToVector(friendlycreaturePosition)) < 1.5) {
                stage.addActor(dialogueContainerFriendlyCreature);
                state = false;
            }

        }
    }




    //hide dialogue function
    public void hideDialogue() {
        if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(oneLegGirlPosition)) < 1.5) {
            dialogueContainerFemale.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(GuardPosition)) < 1.5) {
            dialogueContainerGuard.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(maleCitizenPosition)) < 1.5) {
            dialogueContainerMale.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(childPosition)) < 1.5) {
            dialogueContainerChild.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(HumanGuardPosition)) < 1.5) {
            dialogueContainerHumanGuard.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(PlumberFriendPosition)) < 1.5) {
            dialogueContainerPlumberFriend.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(friendlycreaturePosition)) < 1.5) {
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
