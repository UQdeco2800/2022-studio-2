package com.deco2800.game.SkillsTree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.components.player.*;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;

public class SkillsTreeDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(com.deco2800.game.SkillsTree.SkillsTreeDisplay.class);
    private static final float Z_INDEX = 2f;
    private static final int SKILL_ICON_BUTTON_SIZE = 50;
    private Table skillTreeBackground;
    private Table exitTable;
    private Table equipTable;
    private Image skillTreeImage;
    private Image skillbarImage;
    private boolean skillTreeOpen = false;
    private ArrayList<ImageButton> skillTreeIcons = new ArrayList<>();
    private Image activeTooltip;
    private boolean toolTipDisplaying;

    private PlayerSkillComponent.SkillTypes skill1Type = PlayerSkillComponent.SkillTypes.NONE;
    private PlayerSkillComponent.SkillTypes skill2Type = PlayerSkillComponent.SkillTypes.NONE;
    private PlayerSkillComponent.SkillTypes skill3Type = PlayerSkillComponent.SkillTypes.NONE;


    @Override
    public void create() {
        entity.getEvents().addListener("toggleSkillTree", this::toggleSkillTreeDisplay);
        super.create();
        CooldownBarDisplay cooldownBar = entity.getComponent(CooldownBarDisplay.class);
        cooldownBar.addSkillIcon(new Image(new Texture("images/Skills/dash.png")));
        for (int i = 0 ; i < 3 ; ++i) {
            cooldownBar.addSkillIcon(new Image(new Texture("images/Skills/blankSkillIcon.png")));
        }
    }

    /**
     * Toggles the skill tree display
     */
    public void toggleSkillTreeDisplay() {
        ServiceLocator.getEntityService().toggleTimeStop();
        if(!skillTreeOpen) {
            addActors();
        } else {
            dispose();
        }
        skillTreeOpen = !skillTreeOpen;
    }

    /**
     * Adds all UI actors to the skill tree UI based on skill states
     */
    private void addActors() {
        skillTreeBackground = new Table();
        exitTable = new Table();
        equipTable = new Table();
        equipTable.bottom().left();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture("images/Skill_tree/skill tree background.png")));
        skillTreeBackground.setBackground(textureRegionDrawableBg);
        exitTable.top().right();
        exitTable.setFillParent(true);
        equipTable.setFillParent(true);
        skillTreeBackground.setFillParent(true);
        skillTreeImage = new Image(new Texture(Gdx.files.internal("images/Skill_tree/skill_tree_2.png")));
        skillTreeImage.setSize(850f, 850f);
        skillTreeImage.setPosition(250,0);
        skillbarImage = new Image(new Texture("images/Skills/skillbar.png"));
        skillbarImage.setSize(350,160);
        skillbarImage.setPosition((float) 280, -45);


        TextureRegionDrawable exitTextureUp = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/Skills/skillExitButton.png", Texture.class));
        TextureRegionDrawable exitTextureDown = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/Skills/skillExitButtonDown.png", Texture.class));
        ImageButton exitButton = new ImageButton(exitTextureUp);
        ImageButton.ImageButtonStyle style = exitButton.getStyle();
        style.imageUp = exitTextureUp;
        style.imageDown = exitTextureDown;
        exitButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        entity.getEvents().trigger("toggleSkillTree");
                    }
                });

        exitTable.add(exitButton).padTop(10f).padRight(10f);

        refreshEquippedSkillsUI();

        stage.addActor(skillTreeBackground);
        stage.addActor(exitTable);
        stage.addActor(skillTreeImage);
        stage.addActor(skillbarImage);
        stage.addActor(equipTable);
        addAllSkillsToTree();
    }

    /**
     * Adds all skill icons to the tree for existing skills.
     */
    private void addAllSkillsToTree() {
        int playerSkillPoints = ServiceLocator.getGameArea().getPlayer()
                .getComponent(PlayerActions.class).getSkillComponent().getSkillPoints();

        boolean row1Lock = true;
        boolean row2Lock = true;
        boolean row3Lock = true;
        boolean row4Lock = true;
        if (playerSkillPoints >= 0) {
            row1Lock = false;
        }
        if (playerSkillPoints >= 1) {
            row2Lock = false;
        }
        if (playerSkillPoints >= 3) {
            row3Lock = false;
        }
        if (playerSkillPoints >= 6) {
            row4Lock = false;
        }

        addSkillToTree(1, "dash", PlayerSkillComponent.SkillTypes.NONE, false);
        // Row 1
        addSkillToTree(2, "dodge", PlayerSkillComponent.SkillTypes.DODGE, row1Lock);
        addSkillToTree(3, "teleport", PlayerSkillComponent.SkillTypes.TELEPORT, row1Lock);
        addSkillToTree(4, "aoe", PlayerSkillComponent.SkillTypes.AOE, row1Lock);
        addSkillToTree(5, "root", PlayerSkillComponent.SkillTypes.ROOT, row1Lock);
        // Row 2
        addSkillToTree(6, "block", PlayerSkillComponent.SkillTypes.BLOCK, row2Lock);
        addSkillToTree(7, "invulnerability", PlayerSkillComponent.SkillTypes.INVULNERABILITY, row2Lock);
        // Row 3
        addSkillToTree(8, "wrenchProjectile", PlayerSkillComponent.SkillTypes.PROJECTILE, row3Lock);
        addSkillToTree(9, "charge", PlayerSkillComponent.SkillTypes.CHARGE, row3Lock);
        addSkillToTree(10, "bleed", PlayerSkillComponent.SkillTypes.BLEED, row3Lock);
        // Row 4
        addSkillToTree(11, "ultimate", PlayerSkillComponent.SkillTypes.ULTIMATE, row4Lock);
        addSkillToTree(12, "fireballUltimate", PlayerSkillComponent.SkillTypes.FIREBALLULTIMATE, row4Lock);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public float getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void dispose() {
        if(skillTreeBackground != null) {
            skillTreeBackground.clear();
            skillTreeBackground.remove();
        }

        if(exitTable != null) {
            exitTable.clear();
            exitTable.remove();
        }

        if (equipTable != null) {
            equipTable.clear();
            equipTable.remove();
        }

        if (skillTreeImage != null) {
            skillTreeImage.clear();
            skillTreeImage.remove();
        }

        if (skillbarImage != null) {
            skillbarImage.clear();
            skillbarImage.remove();
        }

        if (!skillTreeIcons.isEmpty()) {
            for (ImageButton button : skillTreeIcons) {
                button.clear();
                button.remove();
            }
        }
        skillTreeIcons.clear();
        super.dispose();
    }

    /**
     * Refreshes the equip bar to visually represent the equipped skills
     */
    private void refreshEquippedSkillsUI() {
        equipTable.clear();
        entity.getComponent(CooldownBarDisplay.class).clearSkillIcons();
        addEquipText();
        addEquippedSkill(PlayerSkillComponent.SkillTypes.DASH, 0);
        addEquippedSkill(skill1Type, 1);
        addEquippedSkill(skill2Type,2);
        addEquippedSkill(skill3Type, 3);
        addClearButton();
    }

    /**
     * Adds an image button to clear the equipped skills
     */
    private void addClearButton() {
        TextureRegionDrawable texture = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/Skills/clearSkillsButton.png", Texture.class));
        TextureRegionDrawable texture2 = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/Skills/clearSkillsButton_down.png", Texture.class));
        ImageButton button = new ImageButton(texture);
        ImageButton.ImageButtonStyle style = button.getStyle();
        style.imageDown = texture2;
        style.imageUp = texture;
        equipTable.add(button).pad(10f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                clearEquippedSkills();
            }
        });
    }

    /**
     * Adds a UI image for equipped skill UI (bottom left of equip bar)
     */
    private void addEquipText() {
        Image equipText = new Image(new Texture(Gdx.files.internal("images/Skills/EquippedSkillsText.png")));
        equipText.setSize(210f, 64f);
        equipTable.add(equipText).padLeft(20f).padBottom(5f);
    }

    /**
     * Clears the equipped skills. Function should be called from clear button event.
     */
    private void clearEquippedSkills() {
        skill1Type = PlayerSkillComponent.SkillTypes.NONE;
        skill2Type = PlayerSkillComponent.SkillTypes.NONE;
        skill3Type = PlayerSkillComponent.SkillTypes.NONE;
        ServiceLocator.getGameArea().getPlayer().getComponent(Countdown.class).clearCountdownListeners();
        refreshEquippedSkillsUI();
        ServiceLocator.getGameArea().getPlayer().getComponent(PlayerActions.class)
                .getSkillComponent().resetSkills(ServiceLocator.getGameArea().getPlayer());

    }

    /**
     * Adds an equipped skill to the skill tree based on the skill type
     * @param skillType the skill type from PlayerSkillComponent.SkillTypes
     */
    private void addEquippedSkill(PlayerSkillComponent.SkillTypes skillType, int skillNum) {
        switch (skillType) {
            case NONE -> addEquipImage("blankSkillIcon");
            case DASH -> {
                addEquipImage("dash");
                addCountdownTrigger("dashCountdown", skillNum, 500);
            }
            case TELEPORT -> {
                addEquipImage("teleport");
                addCountdownTrigger("teleportCountdown", skillNum, 10000);
            }
            case BLOCK -> {
                addEquipImage("block");
                addCountdownTrigger("blockCountdown", skillNum, 3000);
            }
            case DODGE -> {
                addEquipImage("dodge");
                addCountdownTrigger("dodgeCountdown", skillNum, 3000);
            }
            case FIREBALLULTIMATE -> {
                addEquipImage("fireballUltimate");
                addCountdownTrigger("fireballCountdown", skillNum, 20000);
            }
            case ULTIMATE -> {
                addEquipImage("ultimate");
                addCountdownTrigger("ultimateCountdown", skillNum, 20000);
            }
            case ROOT -> {
                addEquipImage("root");
                addCountdownTrigger("rootCountdown", skillNum, 5000);
            }
            case AOE -> {
                addEquipImage("aoe");
                addCountdownTrigger("aoeCountdown", skillNum, 5000);
            }
            case PROJECTILE -> {
                addEquipImage("wrenchProjectile");
                addCountdownTrigger("wrenchCountdown", skillNum, 5000);
            }
            case BLEED -> {
                addEquipImage("bleed");
                addCountdownTrigger("bleedCountdown", skillNum, 5000);
            }
            case CHARGE -> {
                addEquipImage("charge");
                addCountdownTrigger("chargeCountdown", skillNum, 10000);
            }
            case INVULNERABILITY -> {
                addEquipImage("invulnerability");
                addCountdownTrigger("invulnerabilityCountdown", skillNum, 10000);
            }
        }
    }

    /**
     * Adds equip image based on the image name. Assumes image is in images/Skills/ folder as a png.
     * @param imageName The name of the png image filename in images/Skills/
     */
    private void addEquipImage(String imageName) {
        String imagePath = "images/Skills/" + imageName + ".png";
        Image skillIcon = new Image(new Texture(imagePath));
        equipTable.add(skillIcon).pad(5f);
        CooldownBarDisplay cooldownBar = entity.getComponent(CooldownBarDisplay.class);
        cooldownBar.addSkillIcon(new Image(new Texture(imagePath)));
    }

    /**
     * Adds a countdown trigger to the cooldown display.
     * @param listenerName The name of the countdown controller listener
     * @param skillNum the equipped skill number, 1 based and up to 3 equipped skills
     * @param cooldownLength the length of the cooldown for the countdown visual display
     */
    private void addCountdownTrigger(String listenerName, int skillNum, long cooldownLength) {
        Countdown countdownController = ServiceLocator.getGameArea().getPlayer().getComponent(Countdown.class);
        countdownController.setCountdownTrigger(skillNum, listenerName, cooldownLength);
    }

    /**
     * Adds the skill tree buttons to the skill tree boxes in the image skill tree
     * @param skillNumber the number of the skill, from top to bottom left to right.
     *                    Top skill is row 1, second skill is row 1 far left in tree,
     *                    3rd skill is row 1, one to the right. etc.
     * @param baseImageFileName The name of the skill icon file, has a '_disabled' trailing
     *                          file name for disabled version of icon when disabled is true
     * @param skillType the skill type from PlayerSkillComponent.SkillTypes
     */
    private void addSkillToTree(int skillNumber, String baseImageFileName, PlayerSkillComponent.SkillTypes skillType, boolean disabled) {
        String imageFileName;
        if (disabled) { // Based on player skill points
            imageFileName = baseImageFileName + "_disabled";
        } else {
            imageFileName = baseImageFileName;
        }
        switch (skillNumber) {
            case 1 -> addSkillTreeButton(808, 642, imageFileName, skillType, disabled);
            case 2 -> addSkillTreeButton(698, 532, imageFileName, skillType, disabled);
            case 3 -> addSkillTreeButton(774, 532, imageFileName, skillType, disabled);
            case 4 -> addSkillTreeButton(920, 532, imageFileName, skillType, disabled);
            case 5 -> addSkillTreeButton(844, 532, imageFileName, skillType, disabled);
            case 6 -> addSkillTreeButton(736, 436, imageFileName, skillType, disabled);
            case 7 -> addSkillTreeButton(882, 436, imageFileName, skillType, disabled);
            case 8 -> addSkillTreeButton(736, 344, imageFileName, skillType, disabled);
            case 9 -> addSkillTreeButton(920, 344, imageFileName, skillType, disabled);
            case 10 -> addSkillTreeButton(844, 344, imageFileName, skillType, disabled);
            case 11 -> addSkillTreeButton(781, 210, imageFileName, skillType, disabled);
            case 12 -> addSkillTreeButton(850, 210, imageFileName, skillType, disabled);
        }
    }

    /**
     * Adds a skill tree image button, based on its position coordinates and the image file name,
     * as well as the skill type.
     * @param xCoord The x position of the skill tree icon button
     * @param yCoord The y position of the skill tree icon button
     * @param imageFilePath the name of the png file of the skill icon in images/Skills/
     * @param skillType the skill type from PlayerSkillComponent.SkillTypes
     */
    private void addSkillTreeButton(int xCoord, int yCoord, String imageFilePath, PlayerSkillComponent.SkillTypes skillType, boolean disabled) {
        TextureRegionDrawable texture = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/Skills/" + imageFilePath + ".png", Texture.class));
        ImageButton button = new ImageButton(texture);
        ImageButton.ImageButtonStyle style = button.getStyle();
        style.imageUp = texture;
        button.getImage().setFillParent(true);

        button.setPosition(xCoord, yCoord);
        button.setSize(SKILL_ICON_BUTTON_SIZE, SKILL_ICON_BUTTON_SIZE);

        if (!disabled) {
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    equipSkill(skillType);
                    removeActiveTooltip();
                }
            });
            button.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    addTooltipImage(imageFilePath, xCoord, yCoord);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    removeActiveTooltip();
                }
            });
        }

        stage.addActor(button);
        skillTreeIcons.add(button);
    }

    /**
     * Adds tooltip image to skill tree icon.
     * @param imageFilePath the name of the skill tree icon
     * @param xCoord the x coordinates of the skill icon, taken directly from its placement on the skill tree
     * @param yCoord the y coordinates of the skill icon, taken directly from its placement on the skill tree
     */
    private void addTooltipImage(String imageFilePath, float xCoord, float yCoord) {
        if (!this.toolTipDisplaying) {
            this.toolTipDisplaying = true;
            this.activeTooltip = new Image(new TextureRegionDrawable(ServiceLocator.getResourceService()
                    .getAsset("images/Skill_tree/tooltips/" + imageFilePath + "Tooltip.png", Texture.class)));
            this.activeTooltip.setPosition(xCoord - SKILL_ICON_BUTTON_SIZE/2f , yCoord + SKILL_ICON_BUTTON_SIZE);
            this.activeTooltip.setSize(SKILL_ICON_BUTTON_SIZE * 3, SKILL_ICON_BUTTON_SIZE * 2);
            stage.addActor(this.activeTooltip);
        }

    }

    /**
     * Removes the active tooltip for a skill icon
     */
    private void removeActiveTooltip() {
        if (this.activeTooltip != null) {
            this.activeTooltip.remove();
            this.activeTooltip = null;
            this.toolTipDisplaying = false;
        }
    }

    /**
     * Equips a skill of type from types in PlayerSkillComponent.SkillTypes
     * @param skillType, skill of types PlayerSkillComponent.SkillTypes
     */
    private void equipSkill(PlayerSkillComponent.SkillTypes skillType) {
        if (skillType == PlayerSkillComponent.SkillTypes.NONE || isDuplicateSkillEquip(skillType)) {
            return; // Can't equip no skill as a valid skill
        }
        Entity playerEntity = ServiceLocator.getGameArea().getPlayer();
        PlayerActions playerActions = playerEntity.getComponent(PlayerActions.class);
        PlayerSkillComponent skillComponent = playerActions.getSkillComponent();

        if (skill1Type == PlayerSkillComponent.SkillTypes.NONE) {
            skill1Type = skillType;
            skillComponent.setSkill(1, skillType, playerEntity, playerActions);
            refreshEquippedSkillsUI();
        } else if (skill2Type == PlayerSkillComponent.SkillTypes.NONE) {
            skill2Type = skillType;
            skillComponent.setSkill(2, skillType, playerEntity, playerActions);
            refreshEquippedSkillsUI();
        } else if (skill3Type == PlayerSkillComponent.SkillTypes.NONE) {
            skill3Type = skillType;
            skillComponent.setSkill(3, skillType, playerEntity, playerActions);
            refreshEquippedSkillsUI();
        } // Else too many skills trying to be equipped and do nothing
    }

    /**
     * Checks for a duplicate skill equip
     * @param skillType, the type of skill trying to be equipped, of type
     *                   from PlayerSkillComponent.SkillTypes
     * @return true if the skill is currently equpped
     *          false otherwise
     */
    private boolean isDuplicateSkillEquip(PlayerSkillComponent.SkillTypes skillType) {
        if (skillType == skill1Type || skillType == skill2Type || skillType == skill3Type) {
            return true;
        } else {
            return false;
        }
    }


}
