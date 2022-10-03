package com.deco2800.game.SkillsTree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.components.player.PlayerSkillComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class SkillsTreeDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(com.deco2800.game.SkillsTree.SkillsTreeDisplay.class);
    private static final float Z_INDEX = 2f;
    private static final int SKILL_ICON_BUTTON_SIZE = 50;
    private Table skillTreeBackground;
    private Table exitTable;
    private Table equipTable;
    private Image skillTreeImage;
    private boolean skillTreeOpen = false;
    private ArrayList<ImageButton> skillTreeIcons = new ArrayList<>();

    private PlayerSkillComponent.SkillTypes skill1Type = PlayerSkillComponent.SkillTypes.NONE;
    private PlayerSkillComponent.SkillTypes skill2Type = PlayerSkillComponent.SkillTypes.NONE;
    private PlayerSkillComponent.SkillTypes skill3Type = PlayerSkillComponent.SkillTypes.NONE;

    @Override
    public void create() {
        entity.getEvents().addListener("toggleSkillTree", this::toggleSkillTreeDisplay);
        super.create();
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
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.TEAL);
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        skillTreeBackground.setBackground(textureRegionDrawableBg);
        exitTable.top().right();
        exitTable.setFillParent(true);
        equipTable.setFillParent(true);
        skillTreeBackground.setFillParent(true);
        skillTreeImage = new Image(new Texture(Gdx.files.internal("images/Skill_tree/skill_tree_2.png")));
        skillTreeImage.setSize(850f, 850f);
        skillTreeImage.setPosition(250,0);



        TextButton mainMenuBtn = new TextButton("Exit", skin);

        /**
         * Currently clicking exit button will start a new game,
         * but I think someone was fixing this in GdxGame and I
         * have no idea about how to fix this  XD
         */
        mainMenuBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        entity.getEvents().trigger("toggleSkillTree");
                    }
                });

        exitTable.add(mainMenuBtn).padTop(10f).padRight(10f);

        refreshEquippedSkillsUI();

        stage.addActor(skillTreeBackground);
        stage.addActor(exitTable);
        stage.addActor(skillTreeImage);
        stage.addActor(equipTable);


        addSkillToTree(1, "dash", PlayerSkillComponent.SkillTypes.NONE);
        // Row 1
        addSkillToTree(2, "dodge", PlayerSkillComponent.SkillTypes.DODGE);
        addSkillToTree(3, "teleport", PlayerSkillComponent.SkillTypes.TELEPORT);
        addSkillToTree(4, "invulnerability", PlayerSkillComponent.SkillTypes.NONE);
        addSkillToTree(5, "invulnerability", PlayerSkillComponent.SkillTypes.NONE);
        // Row 2
        addSkillToTree(6, "block", PlayerSkillComponent.SkillTypes.BLOCK);
        addSkillToTree(7, "invulnerability", PlayerSkillComponent.SkillTypes.NONE);
        // Row 3
        addSkillToTree(8, "invulnerability", PlayerSkillComponent.SkillTypes.NONE);
        addSkillToTree(9, "invulnerability", PlayerSkillComponent.SkillTypes.NONE);
        addSkillToTree(10, "invulnerability", PlayerSkillComponent.SkillTypes.NONE);
        // Row 4
        addSkillToTree(11, "invulnerability", PlayerSkillComponent.SkillTypes.NONE);
        addSkillToTree(12, "fireballUltimate", PlayerSkillComponent.SkillTypes.FIREBALLULTIMATE);
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
        skillTreeBackground.clear();
        skillTreeBackground.remove();
        exitTable.clear();
        exitTable.remove();
        equipTable.clear();
        equipTable.remove();
        skillTreeImage.clear();
        skillTreeImage.remove();
        for (ImageButton button: skillTreeIcons) {
            button.clear();
            button.remove();
        }
        skillTreeIcons.clear();
        super.dispose();
    }

    /**
     * Refreshes the equip bar to visually represent the equipped skills
     */
    private void refreshEquippedSkillsUI() {
        equipTable.clear();
        addEquipText();
        addEquippedSkill(PlayerSkillComponent.SkillTypes.DASH);
        addEquippedSkill(skill1Type);
        addEquippedSkill(skill2Type);
        addEquippedSkill(skill3Type);
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
        refreshEquippedSkillsUI();
    }

    /**
     * Adds an equipped skill to the skill tree based on the skill type
     * @param skillType the skill type from PlayerSkillComponent.SkillTypes
     */
    private void addEquippedSkill(PlayerSkillComponent.SkillTypes skillType) {
        switch (skillType) {
            case NONE -> addEquipImage("blankSkillIcon");
            case DASH -> addEquipImage("dash");
            case TELEPORT -> addEquipImage("teleport");
            case BLOCK -> addEquipImage("block");
            case DODGE -> addEquipImage("dodge");
            case FIREBALLULTIMATE -> addEquipImage("fireballUltimate");
        }
    }

    /**
     * Adds equip image based on the image name. Assumes image is in images/Skills/ folder as a png.
     * @param imageName The name of the png image filename in images/Skills/
     */
    private void addEquipImage(String imageName) {
        Image skillIcon = new Image(new Texture(Gdx.files.internal(
                                "images/Skills/" + imageName + ".png")));
        equipTable.add(skillIcon).pad(10f);
    }

    /**
     * Adds the skill tree buttons to the skill tree boxes in the image skill tree
     * @param skillNumber the number of the skill, from top to bottom left to right.
     *                    Top skill is row 1, second skill is row 1 far left in tree,
     *                    3rd skill is row 1, one to the right. etc.
     * @param imageFileName The name of the skill icon file
     * @param skillType the skill type from PlayerSkillComponent.SkillTypes
     */
    private void addSkillToTree(int skillNumber, String imageFileName, PlayerSkillComponent.SkillTypes skillType) {
        switch (skillNumber) {
            case 1 -> addSkillTreeButton(808, 642, imageFileName, skillType);
            case 2 -> addSkillTreeButton(698, 532, imageFileName, skillType);
            case 3 -> addSkillTreeButton(774, 532, imageFileName, skillType);
            case 4 -> addSkillTreeButton(920, 532, imageFileName, skillType);
            case 5 -> addSkillTreeButton(844, 532, imageFileName, skillType);
            case 6 -> addSkillTreeButton(736, 436, imageFileName, skillType);
            case 7 -> addSkillTreeButton(882, 436, imageFileName, skillType);
            case 8 -> addSkillTreeButton(736, 344, imageFileName, skillType);
            case 9 -> addSkillTreeButton(920, 344, imageFileName, skillType);
            case 10 -> addSkillTreeButton(844, 344, imageFileName, skillType);
            case 11 -> addSkillTreeButton(781, 210, imageFileName, skillType);
            case 12 -> addSkillTreeButton(850, 210, imageFileName, skillType);
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
    private void addSkillTreeButton(int xCoord, int yCoord, String imageFilePath, PlayerSkillComponent.SkillTypes skillType) {
        TextureRegionDrawable texture = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/Skills/" + imageFilePath + ".png", Texture.class));
        TextureRegionDrawable texture2 = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/Skills/block.png", Texture.class));
        ImageButton button = new ImageButton(texture);
        ImageButton.ImageButtonStyle style = button.getStyle();
        style.imageDown = texture2;
        style.imageUp = texture;
        button.getImage().setFillParent(true);

        button.setPosition(xCoord, yCoord);
        button.setSize(SKILL_ICON_BUTTON_SIZE, SKILL_ICON_BUTTON_SIZE);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                equipSkill(skillType);
            }
        });
        stage.addActor(button);
        skillTreeIcons.add(button);
    }

    /**
     * Equips a skill of type from types in PlayerSkillComponent.SkillTypes
     * @param skillType, skill of types PlayerSkillComponent.SkillTypes
     */
    private void equipSkill(PlayerSkillComponent.SkillTypes skillType) {
        if (skillType == PlayerSkillComponent.SkillTypes.NONE || isDuplicateSkillEquip(skillType)) {
            return; // Can't equip no skill as a valid skill
        }

        if (skill1Type == PlayerSkillComponent.SkillTypes.NONE) {
            skill1Type = skillType;
            refreshEquippedSkillsUI();
        } else if (skill2Type == PlayerSkillComponent.SkillTypes.NONE) {
            skill2Type = skillType;
            refreshEquippedSkillsUI();
        } else if (skill3Type == PlayerSkillComponent.SkillTypes.NONE) {
            skill3Type = skillType;
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
