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


    public int dialogueImageFemale = 0;
    public int dialogueImageChild = 1;
    public int dialogueImageGuard = 2;
    public int dialogueImageMale = 3;

    private HashMap<Integer, String> dialogueMap = new HashMap<Integer, String>() {
        {
            put(0, "images/NPC/Dialogue/dialoguesboxfemale.png");
            put(1, "images/NPC/Dialogue/dialoguesboxchild.png");
            put(2, "images/NPC/Dialogue/dialoguesboxguard.png");
            put(3, "images/NPC/Dialogue/dialoguesboxmale.png");
        }
    };
    static int countFemale = 0;
    static int countGuard = 0;
    static int countChild = 0;
    static int countMale = 0;


    public static Table dialogueContainerFemale;
    public static Table dialogueContainerGuard;
    public static Table dialogueContainerMale;
    public static Table dialogueContainerChild;

    public static TextArea textAreaFemale;
    public static TextArea textAreaGuard;
    public static TextArea textAreaMale;
    public static TextArea textAreaChild;

    public static Boolean state = false;

    public static String[] textFemale = {
            "Alice:\n",
            "Are you still going ahead with your plan? \n",
            "Me? Come with you? Hmm…maybe it’s best I don’t go since I’m a bit out of shape.\n",
            "Instead I’ll help by keeping you out of any suspicions \n",
    };

    public static String[] textGuard = {
            "Guard\n",
            "1",
            "2",
            "3",
            "4",
            "5"
    };

    public static String[] textMale = {
            "male\n",
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

        dialogueImagefemale = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageFemale), Texture.class));
        dialogueimageguard = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageGuard), Texture.class));
        dialogueimagemale = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageMale), Texture.class));
        dialogueimgchild = new Image(ServiceLocator.getResourceService()
                .getAsset(dialogueMap.get(dialogueImageChild), Texture.class));

        textAreaFemale = new TextArea(textFemale[countFemale], skin);
        textAreaFemale.setWidth(400);
        textAreaFemale.setHeight(50);
        textAreaGuard = new TextArea(textGuard[countGuard], skin);
        textAreaGuard.setWidth(400);
        textAreaGuard.setHeight(50);
        textAreaMale = new TextArea(textMale[countMale], skin);
        textAreaMale.setWidth(400);
        textAreaMale.setHeight(50);
        textAreaChild = new TextArea(textChild[countChild], skin);
        textAreaChild.setWidth(400);
        textAreaChild.setHeight(50);


        dialogueContainerFemale.addActor(dialogueImagefemale);
        dialogueContainerFemale.addActor(textAreaFemale);
        dialogueContainerGuard.addActor(dialogueimageguard);
        dialogueContainerGuard.addActor(textAreaGuard);
        dialogueContainerMale.addActor(dialogueimagemale);
        dialogueContainerMale.addActor(textAreaMale);
        dialogueContainerChild.addActor(dialogueimgchild);
        dialogueContainerChild.addActor(textAreaChild);

        super.create();
        entity.getEvents().addListener("openDialogue", this::openDialogue);
        entity.getEvents().addListener("nextText", this::nextText);
        entity.getEvents().addListener("hideDialogue", this::hideDialogue);
    }

    public void nextText() {


        if (entity.getCenterPosition().dst(GridPointToVector(oneLegGirlPosition)) < 1.5) {
            countFemale++;
            textAreaFemale = new TextArea(textFemale[countFemale], skin);
            textAreaFemale.setWidth(400);
            dialogueContainerFemale.addActor(textAreaFemale);
            if (countFemale >= 3) {
                countFemale = 0;
            }
        } else if (entity.getCenterPosition().dst(GridPointToVector(GuardPosition)) < 1.5) {
            countGuard++;
            textAreaGuard = new TextArea(textGuard[countGuard], skin);
            textAreaGuard.setWidth(400);
            dialogueContainerGuard.addActor(textAreaGuard);

            if (countGuard >= 4) {
                countGuard = 0;
            }
        } else if (entity.getCenterPosition().dst(GridPointToVector(maleCitizenPosition)) < 1.5) {
            countMale++;
            textAreaMale = new TextArea(textMale[countMale], skin);
            textAreaMale.setWidth(400);
            dialogueContainerMale.addActor(textAreaMale);
            if (countMale >= textMale.length - 1) {
                countMale = 0;
            }
        } else if (entity.getCenterPosition().dst(GridPointToVector(childPosition)) < 1.5) {
            countChild++;
            textAreaChild = new TextArea(textChild[countChild], skin);
            textAreaChild.setWidth(400);
            dialogueContainerChild.addActor(textAreaChild);
            if (countChild >= textChild.length - 1) {
                countChild = 0;
                dialogueContainerChild.remove();
            } else if (countChild >= 4) {
                TextButton childButton = new TextButton("yes", skin);
                dialogueContainerChild.add(childButton);

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
            }

        }


//        } else if (state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(HumanGuardPosition)) < 2) {
//            stage.addActor(dialogueImage);
//            state = false;
//        } else if (state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(PlumberFriendPosition)) < 2) {
//            stage.addActor(dialogueImage);
//            state = false;
//        } else if (state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(friendlycreaturePosition)) < 2) {
//            stage.addActor(dialogueImage);
//            state = false;
//        }

    }




    //hide dialogue function
    public void hideDialogue() {
        if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(oneLegGirlPosition)) < 2) {
            dialogueContainerFemale.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(GuardPosition)) < 2) {
            dialogueContainerGuard.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(maleCitizenPosition)) < 2) {
            dialogueContainerMale.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(childPosition)) < 2) {
            dialogueContainerChild.remove();
            state = true;
        }
//        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(oneLegGirlPosition)) > 2) {
//            dialogueImage.remove();
//            state = true;
//        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(GuardPosition)) > 2) {
//            dialogueImage.remove();
//            state = true;
//        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(maleCitizenPosition)) > 2) {
//            dialogueImage.remove();
//            state = true;
//        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(childPosition)) > 2) {
//            dialogueImage.remove();
//            state = true;
//        }
//        else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(HumanGuardPosition)) > 2) {
//            dialogueImage.remove();
//            state = true;
//        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(PlumberFriendPosition)) > 2) {
//            dialogueImage.remove();
//            state = true;
//        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(friendlycreaturePosition)) > 2) {
//            dialogueImage.remove();
//            state = true;
//        }

    }


    @Override
    public void draw(SpriteBatch batch) {

    }



    @Override
    public void dispose() {

        super.dispose();
    }
}
