package com.deco2800.game.components.gamearea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.SkillsTree.SkillsTreeDisplay;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.areas.UndergroundGameArea;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.maingame.OpenKeyBinds;
import com.deco2800.game.components.maingame.PauseMenuActions;
import com.deco2800.game.components.player.*;
import com.deco2800.game.crafting.CraftingLogic;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.configs.CombatItemsConfig.WeaponConfig;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.math.MathUtils.E;
import static com.badlogic.gdx.math.MathUtils.ceil;

/**
 * Class that allows for overlaying the current map with displays and animation. Used for crafting, inventory, NPCs and
 * puase menu
 */
public class GameAreaDisplay extends UIComponent {

    private String gameAreaName = "";
    private Label title;

    private static final Logger logger = LoggerFactory.getLogger(GameAreaDisplay.class);


    /* The image button the user clicks to craft something */
    private ImageButton craftButton;
    /* The image button the user clicks to open the first catalogue */
    private ImageButton catalogueButton;
    /* The image button the user clicks to open the first category */
    private ImageButton catOneButton;
    /* The image button the user clicks to open the second category */
    private ImageButton catTwoButton;
    private ImageButton inventoryButton;
    private ImageButton exitButton;
    private Texture buttonTexture;
    private TextureRegion buttonTextureRegion;
    private TextureRegionDrawable buttonDrawable;
    /* The image of the crafting menu used to show it on the map */
    private Image craftMenu;
    /* List of all the possible weapons the user can build */
    private List<WeaponConfig> possibleBuilds;
    /* The current weapon being used */
    Entity currentWeapon;
    /* Image for the first category of crafting menu */
    private Image catOneMenu;
    /* Image for the second category of crafting menu */
    private Image catTwoMenu;
    private ImageButton firstToCraft;
    private ImageButton secondToCraft;
    private ImageButton resume;
    private ImageButton exit;
    private ImageButton controls;
    private ImageButton howToCraft;
    private ImageButton overviewBtn;
    private ImageButton findItemsBtn;
    private ImageButton weaponBuffsBtn;
    private ImageButton skillTreeInfoBtn;
    private ImageButton levelUpBtn;
    private ImageButton accessInventoryBtn;
    private ImageButton howToWinBtn;
    private ImageButton playerGuideBtn;
    private Texture materialTexture;
    private TextureRegion materialTextureRegion;
    private TextureRegionDrawable materialDrawable;
    private Image matAmount;
    private Image playerGuideMenu;
    private Image popUp;
    private Image firstMatArrow;
    private Image secondMatArrow;
    private Image craftArrow;
    private Image firstMatText;
    private Image secondMatText;
    private Image craftText;
    private Image weapon;
    private Group craftingGroup = new Group();
    private Group materialsGroup = new Group();
    private Group firstTutorial = new Group();
    private Group secondTutorial = new Group();
    private Group thirdTutorial = new Group();
    private Materials[] boxes = new Materials[2];
    private Group pausingGroup = new Group();
    private Group playerGuidGroup = new Group();
    private Group keyBindGroup = new Group();
    private int keyBindPage = 0;
    private int keyBindMod = 0;
    private int firstTime = 0;
    List<Entity> inventoryList;
    InventoryComponent inventoryComponent;
    private Image inventoryMenu;
    private Group inventoryGroup = new Group();
    private Group itemButtonGroup = new Group();
    private Group dropdownGroup = new Group();
    private Image minimapImage;
    private Group minimapGroup = new Group();

    private Boolean currentScreenCrafting = false;
    private int gameLevel;

    @Override
    public void create() {
        super.create();
        ServiceLocator.registerCraftArea(this);
        addActors();
    }

    public GameAreaDisplay(String gameAreaName) {
        this.gameAreaName = gameAreaName;
        logger.info("The current map is {}", this.gameAreaName);
        gameLevel = (this.gameAreaName.equals("Underground"))? 2 : 1;
        ServiceLocator.registerInventoryArea(this);
        ServiceLocator.registerPauseArea(this);
        ServiceLocator.registerPlayerGuideArea(this);
        ServiceLocator.registerKeyBindArea(this);
    }

    public String getGameAreaName() {
        return gameAreaName;
    }

    private void addActors() {
        title = new Label(this.gameAreaName, skin, "large");
        stage.addActor(title);
    }

    public void displayMinimap() {
        GameArea gameArea = ServiceLocator.getGameArea();
        logger.info(String.format("Displaying minimap, area is %s", gameArea.getClass().getSimpleName()));
        if (gameArea.getClass().getSimpleName().equals(ForestGameArea.class.getSimpleName())) {
            minimapImage = new Image(new Texture(Gdx.files.internal
                    ("images/level_1_tiledmap/minimap1.png")));
        } else if (gameArea.getClass().getSimpleName().equals(UndergroundGameArea.class.getSimpleName())) {
            minimapImage = new Image(new Texture(Gdx.files.internal
                    ("images/level_2_tiledmap/minimap2.png")));
        } else {
            logger.info("Game area invalid for minimap");
            return;
        }

        //Note: the position of the asset is at the bottom left.
        minimapImage.setSize(800, 977);
        minimapImage.setPosition(Math.round((double)Gdx.graphics.getWidth() / 2 - minimapImage.getWidth() / 2),
                Math.round((double)Gdx.graphics.getHeight() / 2 - minimapImage.getHeight() / 2));
        minimapGroup.addActor(minimapImage);
        stage.addActor(minimapGroup);
        stage.draw();
    }

    /**
     * Disposes of the minimap when it is open and M is pressed.
     */
    public void disposeMinimap() {
        minimapGroup.remove();
        logger.info("Removing minimap");
    }

    public void updateInventoryDisplay() {
        itemButtonGroup.clear();
        dropdownGroup.clear();
        displayItems();
        displayEquipables();
    }

    /**
     * Load the all assets required for displaying inventory
     */
    public void initialiseInventoryDisplay() {
        inventoryMenu = new Image(new Texture(Gdx.files.internal
                ("images/Inventory/Inventory_v3.png")));
        //Note: the position of the asset is at the bottom left.
        inventoryMenu.setSize(1024, 768);
        inventoryMenu.setPosition((float)Gdx.graphics.getWidth() / 2 - inventoryMenu.getWidth() / 2,
                (float)Gdx.graphics.getHeight() / 2 - inventoryMenu.getHeight() / 2);
        inventoryGroup.addActor(inventoryMenu);
        inventoryGroup.addActor(itemButtonGroup);
        inventoryGroup.addActor(dropdownGroup);
    }

    /**
     * Displays the inventory UI.
     *
     */
    public void displayInventoryMenu() {
        initialiseInventoryDisplay();
        stage.addActor(inventoryGroup);
        stage.draw();
    }

    /**
     * Create a button based on the input operation
     * @param operation button action
     * @return button
     */
    private Button createInventoryButton (String operation, float x, float y) {
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal(String.format("images/Inventory/button/%s_up.png", operation)))));
        style.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal(String.format("images/Inventory/button/%s_down.png", operation)))));

        Button button = new Button(style);
        button.setPosition(x, y);
        button.setSize(96,36);
        return button;
    }

    private void addEquipListener(Button button, Entity item) {
        InventoryComponent inventory = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (item.checkEntityType(EntityTypes.POTION)) {
                            inventory.addQuickBarItems(item);
                            QuickBarDisplay.updatePotionTable();
                        } else {
                            inventory.equipItem(item);
                        }
                        updateInventoryDisplay();
                        dropdownGroup.clear();
                    }
                }
        );
        dropdownGroup.addActor(button);
    }
    private void addDropListener(Button button, Entity item) {
        InventoryComponent inventory = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (inventory.removeItem(item)) updateInventoryDisplay();
                        dropdownGroup.clear();
                    }
                }
        );
        dropdownGroup.addActor(button);
    }

    private void addEquipableListner(Button button, String operation, int itemSlot){
        InventoryComponent inventory = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                switch (operation) {
                    case "unequip":
                        if (inventory.unequipItem(itemSlot)) updateInventoryDisplay();
                        break;
                    case "drop":
                        if (inventory.removeEquipable(itemSlot)) updateInventoryDisplay();
                        break;
                }
                dropdownGroup.clear();
            }
        });
        dropdownGroup.addActor(button);
    }
    /**
     * Create an image button based on entity's default texture
     * @param item the image button's texture source
     * @param size the size of the button
     * @return button
     */
    private ImageButton createImageButton (Entity item, float size, float x, float y) {
        Texture itemTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
        ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(itemTexture)));
        button.setSize(size, size);
        button.setPosition(x, y);
        itemButtonGroup.addActor(button);
        return button;
    }

    /**
     * Displays the items that the player has equipped.
     */
    public void displayEquipables() {
        InventoryComponent inventory = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
        final float horizontalPosition = (inventoryMenu.getX() + 696);
        for (Entity item : inventory.getEquipables()) {
            if (item != null) {
                int itemSlot = item.checkEntityType(EntityTypes.WEAPON)? 0: 1;
                final float verticalPosition = inventoryMenu.getY() + 416 - 192f * itemSlot;
                ImageButton equippedItem = createImageButton(item, 128, horizontalPosition, verticalPosition);
                equippedItem.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        dropdownGroup.clear();
                        addEquipableListner(
                                createInventoryButton("drop",horizontalPosition + 63, verticalPosition),
                                "drop",
                                itemSlot);
                        addEquipableListner(
                                createInventoryButton("unequip", horizontalPosition + 63, verticalPosition - 40),
                                "unequip",
                                itemSlot);
                    }
                });
            }
        }
    }

    /**
     * Displays each item in the inventory in the inventory storage blocks.
     * Implemented by Team 2.
     */
    public void displayItems() {
        float padding = 32f;
        InventoryComponent inventory = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
        List<Entity> items = inventory.getInventory();
        for (int i = 0; i < items.size(); ++i) {
            Entity currentItem = items.get(i);
            float horizontalPosition = (inventoryMenu.getX() + 192) + (i % 4) * (padding + 64);
            float verticalPosition = (inventoryMenu.getY() + 496) - (float)(i / 4) * (padding + 64);
            ImageButton item = createImageButton(currentItem, 64, horizontalPosition, verticalPosition);
            item.addListener(
                    new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent changeEvent, Actor actor) {
                            dropdownGroup.clear();
                            addDropListener(
                                    createInventoryButton("drop", horizontalPosition + 48, verticalPosition),
                                    currentItem);
                            if (!currentItem.checkEntityType(EntityTypes.CRAFTABLE)
                                    || currentItem.checkEntityType(EntityTypes.WEAPON)){
                                addEquipListener(
                                        createInventoryButton("equip", horizontalPosition + 48, verticalPosition - 42),
                                        currentItem);
                            }
                        }
                    });
        }
    }


    /**
     * Disposes the inventory display group.
     */
    public void disposeInventoryMenu() {
        inventoryGroup.clear();
        inventoryGroup.remove();
    }

    /**
     * Opens an overlay crafting menu when 'Q' is pressed. Creates assets based on users' inventory
     * and creates button event handlers to test for user clicks.
     */
    public void openCraftingMenu() {
        logger.info("Opening Crafting Menu");
        inventoryComponent = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
        craftMenu = new Image(new Texture(Gdx.files.internal(String.format("images/Crafting-assets-sprint1/" +
                "crafting table/crafting_inventory_lvl%d.png", gameLevel))));
        craftMenu.setSize(883.26f, 500);
        craftMenu.setPosition((float) (Gdx.graphics.getWidth() / (double) 2 - craftMenu.getWidth() / 2),
                (float) (Gdx.graphics.getHeight() / (double) 2 - craftMenu.getHeight() / 2));
        craftingGroup.addActor(craftMenu);
        if (firstTime == 0) {
            initTutorial();
            if (getGameAreaName().equals("Underground")) {
                inventoryComponent.addItem(MaterialFactory.createWood());
                inventoryComponent.addItem(MaterialFactory.createToiletPaper());
                inventoryComponent.addItem(MaterialFactory.createGold());
                inventoryComponent.addItem(MaterialFactory.createIron());
            }
            firstTime = 1;
        }
        if (firstTime == 1 && getGameAreaName().equals("Forest")) {
            if (!inventoryComponent.hasItem(MaterialFactory.createWood(), inventoryComponent.getInventory())) {
                inventoryComponent.addItem(MaterialFactory.createWood());
            }
            if (!inventoryComponent.hasItem(MaterialFactory.createRubber(), inventoryComponent.getInventory())) {
                inventoryComponent.addItem(MaterialFactory.createRubber());
            }
        }
        getInventory();
        currentScreenCrafting = true;
        buttonTexture = new Texture(Gdx.files.internal
                (String.format("images/Crafting-assets-sprint1/widgets/craft_button_lvl%d.png", gameLevel)));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        craftButton = new ImageButton(buttonDrawable);
        craftButton.setSize(146, 146);
        craftButton.setPosition(craftMenu.getX() + 527, craftMenu.getY() + 110.5f);
        craftButton.addListener(new ChangeListener() {
            // Method to add item to players inventory
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (weapon != null) {
                    disposeFirstBox();
                    disposeSecondBox();
                    logger.info("Weapon added to inventory");
                    ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class).addItem(currentWeapon);
                    weapon.remove();
                    weapon = null;
                    clearBoxes(0);
                    displayPopUp();
                }
                clearMaterials();
                getInventory();
            }
        });
        craftingGroup.addActor(craftButton);
        entity.getEvents().addListener("check", this::checkBuildables);
        buttonTexture = new Texture(Gdx.files.internal
                (String.format("images/Crafting-assets-sprint1/widgets/catalogue_button_lvl%d.png", gameLevel)));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        catalogueButton = new ImageButton(buttonDrawable);
        catalogueButton.setSize(146, 146);
        catalogueButton.setPosition(craftMenu.getX() + 300, craftMenu.getY() + 302);
        catalogueButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Sound catalogueSound = ServiceLocator.getResourceService().getAsset("sounds/ItemClick.wav", Sound.class);
                catalogueSound.play();
                logger.info("Catalogue button pressed");
                currentScreenCrafting = false;
                displayCatOne();
            }
        });
        craftingGroup.addActor(catalogueButton);
        buttonTexture = new Texture(Gdx.files.internal
                (String.format("images/Crafting-assets-sprint1/widgets/exit_button_lvl1.png", gameLevel)));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        exitButton = new ImageButton(buttonDrawable);
        exitButton.setSize(35, 35);
        exitButton.setPosition(craftMenu.getX() + 720, craftMenu.getY() + 365);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.info("Exit Crafting menu");
                Sound exitCraftMenu = ServiceLocator.getResourceService().getAsset("sounds/Scroll.wav", Sound.class);
                exitCraftMenu.play();
                disposeCraftingMenu();
                KeyboardPlayerInputComponent.clearMenuOpening();
                EntityService.pauseAndResume();
                OpenCraftingComponent.setCraftingStatus();
                if (firstTime < 4) {
                    firstTime = 1;
                }
            }
        });
        craftingGroup.addActor(exitButton);
        stage.addActor(craftingGroup);
        stage.draw();
    }

    /**
     * Loads all the required elements for the overlay when the pause button is pressed by the user.
     */
    public void setPauseMenu() {
        logger.info("Opening Pause Menu");
        Image pauseMenu;
        if (getGameAreaName().equals("Underground")) {
            pauseMenu = new Image(new Texture(Gdx.files.internal
                    ("images/PauseMenu/lvl2PauseScreen.png")));
        } else {
            pauseMenu = new Image(new Texture(Gdx.files.internal
                    ("images/PauseMenu/newPauseScreen.png")));
        }
        pauseMenu.setSize(1920, 1080);
        pauseMenu.setPosition((float) (Gdx.graphics.getWidth()/ (double) 2 - pauseMenu.getWidth()/2),
                (float) (Gdx.graphics.getHeight()/ (double) 2 - pauseMenu.getHeight()/2));
        pausingGroup.addActor(pauseMenu);
        stage.addActor(pausingGroup);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        resume = new ImageButton(buttonDrawable);
        resume.setSize(386f, 122.4f);
        resume.setPosition(pauseMenu.getX() + 760f, pauseMenu.getY() + 576);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.debug("Pause menu resume button clicked");
                KeyboardPlayerInputComponent.incrementPauseCounter();
                OpenPauseComponent.closePauseMenu();
            }
        });
        pausingGroup.addActor(resume);
        stage.addActor(pausingGroup);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        exit = new ImageButton(buttonDrawable);
        exit.setSize(386, 122.4f);
        exit.setPosition(pauseMenu.getX() + 765f, pauseMenu.getY() + 288);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.debug("Pause menu exit button clicked");
                KeyboardPlayerInputComponent.incrementPauseCounter();
                KeyboardPlayerInputComponent.clearMenuOpening();
                PauseMenuActions.setQuitGameStatus();
            }
        });
        pausingGroup.addActor(exit);

        // Debug button to open keybind menu - hey Rey this is for you!
        // thanks!:) -Rey
        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        controls = new ImageButton(buttonDrawable);
        controls.setSize(386, 122.4f);
        controls.setPosition(pauseMenu.getX() + 762f, pauseMenu.getY() + 382);
        controls.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("Key binding button things");
                        OpenPauseComponent.openKeyBindings();
                    }
                });
        pausingGroup.addActor(controls);

        // Player guide menu added.
        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        playerGuideBtn = new ImageButton(buttonDrawable);
        playerGuideBtn.setSize(386, 122.4f);
        playerGuideBtn.setPosition(pauseMenu.getX() + 762f, pauseMenu.getY() + 482);
        playerGuideBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("Player guide button clicked");
                        if (getGameAreaName().equals("Underground")) {
                            OpenPauseComponent.openPlayerGuideLevel2(1);
                        } else {
                            OpenPauseComponent.openPlayerGuide(1);
                        }

                    }
                });
        pausingGroup.addActor(playerGuideBtn);
        stage.addActor(pausingGroup);

        stage.draw();
    }

    /**
     * Loads the elements required to provide tutorials for the player when first using the crafting system.
     * @param filePath the asset to be loaded and showed when the tutorial is active.
     */
    public void setPlayerGuideMenu(String filePath) {
        logger.info("Opening Player guide menu");
        playerGuideMenu = new Image(new Texture(filePath));

        playerGuideMenu.setSize(1920, 1080);
        playerGuideMenu.setPosition((float) ((float)Gdx.graphics.getWidth()/ (double)2 - playerGuideMenu.getWidth()/2),
                (float) ((float)Gdx.graphics.getHeight()/(double) 2 - playerGuideMenu.getHeight()/2));
        playerGuidGroup.addActor(playerGuideMenu);

//        testing = new Image(new Texture("images/Crafting-assets-sprint1/widgets/catalogue_button_lvl1.png"));
//        testing.setSize(385, 97.4f);
//        testing.setPosition(playerGuideMenu.getX() + 382f, playerGuideMenu.getY() + 675);
        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        overviewBtn = new ImageButton(buttonDrawable);
        overviewBtn.setSize(385, 97.4f);
        overviewBtn.setPosition(playerGuideMenu.getX() + 382f, playerGuideMenu.getY() + 745);
        overviewBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("HowToCraft clicked");
                        if (getGameAreaName().equals("Underground")) {
                            OpenPauseComponent.openPlayerGuideLevel2(1);
                        } else {
                            OpenPauseComponent.openPlayerGuide(1);
                        }
                    }
                });
        playerGuidGroup.addActor(overviewBtn);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        howToCraft = new ImageButton(buttonDrawable);
        howToCraft.setSize(385, 97.4f);
        howToCraft.setPosition(playerGuideMenu.getX() + 382f, playerGuideMenu.getY() + 675);
        howToCraft.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("HowToCraft clicked");
                        if (getGameAreaName().equals("Underground")) {
                            OpenPauseComponent.openPlayerGuideLevel2(2);
                        } else {
                            OpenPauseComponent.openPlayerGuide(2);
                        }
                    }
                });
        playerGuidGroup.addActor(howToCraft);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        findItemsBtn = new ImageButton(buttonDrawable);
        findItemsBtn.setSize(385, 97.4f);
        findItemsBtn.setPosition(playerGuideMenu.getX() + 382f, playerGuideMenu.getY() + 605);
        findItemsBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("find item section clicked");
                        if (getGameAreaName().equals("Underground")) {
                            OpenPauseComponent.openPlayerGuideLevel2(3);
                        } else {
                            OpenPauseComponent.openPlayerGuide(3);
                        }
                    }
                });
        playerGuidGroup.addActor(findItemsBtn);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        weaponBuffsBtn = new ImageButton(buttonDrawable);
        weaponBuffsBtn.setSize(385, 97.4f);
        weaponBuffsBtn.setPosition(playerGuideMenu.getX() + 382f, playerGuideMenu.getY() + 545);
        weaponBuffsBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("weapon buff section clicked");
                        if (getGameAreaName().equals("Underground")) {
                            OpenPauseComponent.openPlayerGuideLevel2(4);
                        } else {
                            OpenPauseComponent.openPlayerGuide(4);
                        }
                    }
                });
        playerGuidGroup.addActor(weaponBuffsBtn);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        skillTreeInfoBtn = new ImageButton(buttonDrawable);
        skillTreeInfoBtn.setSize(385, 97.4f);
        skillTreeInfoBtn.setPosition(playerGuideMenu.getX() + 382f, playerGuideMenu.getY() + 475);
        skillTreeInfoBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("skill tree info section clicked");
                        if (getGameAreaName().equals("Underground")) {
                            OpenPauseComponent.openPlayerGuideLevel2(5);
                        } else {
                            OpenPauseComponent.openPlayerGuide(5);
                        }
                    }
                });
        playerGuidGroup.addActor(skillTreeInfoBtn);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        levelUpBtn = new ImageButton(buttonDrawable);
        levelUpBtn.setSize(385, 97.4f);
        levelUpBtn.setPosition(playerGuideMenu.getX() + 382f, playerGuideMenu.getY() + 409);
        levelUpBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("level up section clicked");
                        if (getGameAreaName().equals("Underground")) {
                            OpenPauseComponent.openPlayerGuideLevel2(6);
                        } else {
                            OpenPauseComponent.openPlayerGuide(6);
                        }
                    }
                });
        playerGuidGroup.addActor(levelUpBtn);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        accessInventoryBtn = new ImageButton(buttonDrawable);
        accessInventoryBtn.setSize(385, 97.4f);
        accessInventoryBtn.setPosition(playerGuideMenu.getX() + 382f, playerGuideMenu.getY() + 343);
        accessInventoryBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("level up section clicked");
                        if (getGameAreaName().equals("Underground")) {
                            OpenPauseComponent.openPlayerGuideLevel2(7);
                        } else {
                            OpenPauseComponent.openPlayerGuide(7);
                        }
                    }
                });
        playerGuidGroup.addActor(accessInventoryBtn);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        howToWinBtn = new ImageButton(buttonDrawable);
        howToWinBtn.setSize(385, 97.4f);
        howToWinBtn.setPosition(playerGuideMenu.getX() + 382f, playerGuideMenu.getY() + 275);
        howToWinBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("level up section clicked");
                        if (getGameAreaName().equals("Underground")) {
                            OpenPauseComponent.openPlayerGuideLevel2(8);
                        } else {
                            OpenPauseComponent.openPlayerGuide(8);
                        }
                    }
                });
        playerGuidGroup.addActor(howToWinBtn);
        stage.addActor(playerGuidGroup);

        stage.draw();
    }

    /**
     * Removes the elements of the player guide menu from the map.
     */
    public void disposePlayerGuideMenu() {
        playerGuidGroup.remove();
    }

    /**
     * Creates the keybinding menu.
     * Adds the background images, key images, key texts, and next button to navigate the menu.
     * Utilises modulo technique to ensure page changing simply loops.
     */
    public void setKeyBindMenu() {
        Image keyBindMenu;
        if (getGameAreaName().equals("Underground")) {
            keyBindMenu = new Image(new Texture("images/keybind/level_2/ControlPage.png"));
        } else {
            keyBindMenu = new Image(new Texture("images/keybind/level_1/ControlPage.png"));
        }
        keyBindMenu.setSize(1920, 1080);
        keyBindMenu.setPosition((float) ((float)Gdx.graphics.getWidth()/ (double)2 - keyBindMenu.getWidth()/2),
                (float) ((float)Gdx.graphics.getHeight()/(double) 2 - keyBindMenu.getHeight()/2));
        keyBindGroup.addActor(keyBindMenu);

        for (Actor actor : createKeyBindings()) {
            if (actor != null) {
                keyBindGroup.addActor(actor);
            }
        }

        // Invisible next button
        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        ImageButton keyBindNextBtn = new ImageButton(buttonDrawable);
        keyBindNextBtn.setPosition(1325, 360);
        keyBindNextBtn.setSize(200, 65);
        keyBindNextBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("Moving to next keybinding page");
                        keyBindPage++;
                        keyBindMod = ceil((float)OpenKeyBinds.getNumKeys() / (float)OpenKeyBinds.numKeysPerPage);
                        keyBindPage = keyBindPage % keyBindMod;
                        disposeKeyBindMenu();
                        OpenPauseComponent.openKeyBindings();
                    }
                });
        keyBindGroup.addActor(keyBindNextBtn);

        stage.addActor(keyBindGroup);
        stage.draw();
    }

    /**
     * Dispose the keybinding menu group
     */
    public void disposeKeyBindMenu() { keyBindGroup.clear(); }


    /**
     * Creates the appropriate image and label entries for key labelling
     * as actors then returns them.
     * @return Actor[]  Key images and label actors
     */
    public Actor[] createKeyBindings() {
        OpenKeyBinds.KeyBind[] keyBinds = OpenKeyBinds.getKeyBinds(keyBindPage);
        OpenKeyBinds.KeyBind keyBind;
        Actor[] keys = new Actor[OpenKeyBinds.numKeysPerPage * 2]; // x2, one for label, one for image
        Image keyTexture;
        Label keyText;
        int keyIndex = 0;
        int pos = 0;

        while (keyIndex < keyBinds.length && keyBinds[keyIndex] != null) {
            // Create our key image
            keyBind = keyBinds[keyIndex];
            // Select image depending on level
            if (getGameAreaName().equals("Underground")) {
                keyTexture = new Image(new Texture(keyBind.imagelvl2));
            } else {
                keyTexture = new Image(new Texture(keyBind.imagelvl1));
            }
            keyTexture.setSize(128, 72);
            keyTexture.setPosition(OpenKeyBinds.keyTexturePosLUT[keyIndex][0],
                    OpenKeyBinds.keyTexturePosLUT[keyIndex][1]);
            keys[pos++] = keyTexture;

            // Create our label
            keyText = new Label(keyBind.description, skin);
            keyText.setPosition((OpenKeyBinds.keyTexturePosLUT[keyIndex][0] + OpenKeyBinds.keyLabelOffsetX),
                    OpenKeyBinds.keyTexturePosLUT[keyIndex][1] + OpenKeyBinds.keyLabelOffsetY);
            keys[pos++] = keyText;

            keyIndex++;
        }

        return keys;
    }

    /**
     * Checks if a weapon is possibly buildable with the two materials the user has chosen to craft with
     */
    private void checkBuildables() {
        if (boxes[0] != null && boxes[1] != null) {
            for (WeaponConfig item : possibleBuilds) {
                int numItems = 0;
                for (Map.Entry entry : item.materials.entrySet()) {
                    String entryString = entry.toString().split("=")[0];
                    String upperCaseEntry = entryString.substring(0, 1).toUpperCase() + entryString.substring(1);
                    if (boxes[0].toString().equals(upperCaseEntry) ||
                            boxes[1].toString().equals(upperCaseEntry)) {

                        numItems += 1;
                    }
                }
                if (numItems == 2) {
                    if (firstTime == 3 && getGameAreaName().equals("Forest")) {
                        stage.addActor(thirdTutorial);
                    }
                    displayWeapon(item);
                    break;
                }
            }
        }
    }

    /**
     * If an item is craftable, display it in the crafting inventory and set up event handlers to add it to the right
     * side of the menu to be crafted when clicked on. Method scans the inventory, checks which items are craftable
     * and then assigns those a specific position on the map
     */
    private void getInventory() {
        currentScreenCrafting = true;
        int index = 0;
        this.possibleBuilds = CraftingLogic.getPossibleWeapons();
        inventoryList = inventoryComponent.getInventory();
        for (Entity item : inventoryList) {
            if (item.checkEntityType(EntityTypes.CRAFTABLE)) {
                materialTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
                if (item.checkEntityType((EntityTypes.WEAPON))){
                    materialTexture = new Texture("images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Hera.png");
                }
                materialTextureRegion = new TextureRegion(materialTexture);
                materialDrawable = new TextureRegionDrawable(materialTextureRegion);
                if (item.checkEntityType((EntityTypes.WEAPON))){
                    materialDrawable.setMinSize(35, 35);
                }
                ImageButton material = new ImageButton(materialDrawable);
                if (!(item.checkEntityType((EntityTypes.WEAPON)))) {
                    material.setSize(50, 50);
                    material.setPosition(craftMenu.getX() + 172 + ((index % 4) * 68),
                            (float) (craftMenu.getTop() - ((Math.floor(index / (double) 4) * 62) + 208)));
                } else {
                    material.setPosition(craftMenu.getX() + 180 + ((index % 4) * 68),
                            (float) (craftMenu.getTop() - ((Math.floor(index / (double) 4) * 62) + 200)));
                }
                displayAmount(inventoryComponent.getItemQuantity(item), index);
                index++;
                material.addListener(new ClickListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        super.enter(event, x, y, pointer, fromActor);
                        material.setSize(50, 65);
                    }
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        super.exit(event, x, y, pointer, toActor);
                        material.setSize(50, 50);
                    }
                });
                material.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Sound selectMat = ServiceLocator.getResourceService().getAsset("sounds/ItemClick.wav", Sound.class);
                        selectMat.play();
                        if (boxes[0] == null) {
                            clearMaterials();
                            materialTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
                            logger.info(String.format(" item: %s added to box 1", item.getEntityTypes().get(0)));
                            if (item.checkEntityType((EntityTypes.WEAPON))){
                                materialTexture = new Texture("images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Hera.png");
                            }
                            materialTextureRegion = new TextureRegion(materialTexture);
                            materialDrawable = new TextureRegionDrawable(materialTextureRegion);
                            if (item.checkEntityType((EntityTypes.WEAPON))){
                                materialDrawable.setMinSize(35, 35);
                            }
                            firstToCraft = new ImageButton(materialDrawable);
                            if (!(item.checkEntityType((EntityTypes.WEAPON)))) {
                                firstToCraft.setSize(50, 50);
                                firstToCraft.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
                            } else {
                                firstToCraft.setSize(50, 50);
                                firstToCraft.setPosition(craftMenu.getX() + 480, craftMenu.getY() + 225);
                            }
                            stage.addActor(firstToCraft);
                            addToBoxes(checkType(item));
                            inventoryComponent.removeItem(checkType(item));
                            firstToCraft.addListener(new ChangeListener() {
                                @Override
                                public void changed(ChangeEvent event, Actor actor) {
                                    if (Boolean.TRUE.equals(currentScreenCrafting)){
                                        clearMaterials();
                                        disposeFirstBox();
                                        clearBoxes(1);
                                        addToInventory(checkType(item));
                                        getInventory();
                                    }
                                }
                            });
                            getInventory();
                            if (firstTime == 1 && getGameAreaName().equals("Forest")) {
                                disposeFirstTutorial();
                                stage.addActor(secondTutorial);
                                firstTime = 2;
                            }
                        } else if (boxes[1] == null) {
                            clearMaterials();
                            materialTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
                            if (item.checkEntityType((EntityTypes.WEAPON))){
                                materialTexture = new Texture("images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Hera.png");
                            }
                            if (firstTime == 2) {
                                disposeSecondTutorial();
                                firstTime = 3;
                            }
                            logger.info(String.format(" item: %s added to box 2", item.getEntityTypes().get(0)));
                            materialTextureRegion = new TextureRegion(materialTexture);
                            materialDrawable = new TextureRegionDrawable(materialTextureRegion);
                            if (item.checkEntityType((EntityTypes.WEAPON))){
                                materialDrawable.setMinSize(35, 35);
                            }
                            secondToCraft = new ImageButton(materialDrawable);
                            if (!(item.checkEntityType((EntityTypes.WEAPON)))) {
                                secondToCraft.setSize(50, 50);
                                secondToCraft.setPosition(craftMenu.getX() + 548, craftMenu.getY() + 230);
                            } else {
                                secondToCraft.setSize(50, 50);
                                secondToCraft.setPosition(craftMenu.getX() + 545, craftMenu.getY() + 225);
                            }
                            stage.addActor(secondToCraft);
                            addToBoxes(checkType(item));
                            inventoryComponent.removeItem(checkType(item));
                            secondToCraft.addListener(new ChangeListener() {
                                @Override
                                public void changed(ChangeEvent event, Actor actor) {
                                    if (Boolean.TRUE.equals(currentScreenCrafting)) {
                                        clearMaterials();
                                        disposeSecondBox();
                                        clearBoxes(2);
                                        addToInventory(checkType(item));
                                        getInventory();
                                    }
                                }
                            });
                            getInventory();
                        }
                    }
                });
                materialsGroup.addActor(material);
                stage.addActor(materialsGroup);
            }
        }
    }

    /**
     * Display an icon next to each material showing the quantity levels and add animations to them
     * @param amount the number of the item present in the users' inventory
     * @param index position of the material on the crafting menu.
     */
    private void displayAmount(int amount, int index) {
        matAmount = new Image(new Texture(Gdx.files.internal
                (String.format("images/Crafting-assets-sprint1/popups/number%d_popup.png", amount))));
        matAmount.setSize(18, 18);
        matAmount.setPosition(craftMenu.getX() + 212 + ((index % 4) * 68),
                (float) (craftMenu.getTop() - ((Math.floor(index / (double) 4) * 62) + 168)));
        Action upDown = Actions.forever(Actions.sequence(Actions.moveTo(matAmount.getX(), matAmount.getY()+4.5f,
                0.5f), Actions.moveTo(matAmount.getX(), matAmount.getY()-4.5f, 0.5f)));
        matAmount.addAction(upDown);
        materialsGroup.addActor(matAmount);
    }

    /**
     * Displays a "Weapon crafted" pop-up whenever the user successfully crafts a weapon and add animations to it
     */
    private void displayPopUp() {
        if (firstTime == 3) {
            disposeThirdTutorial();
            firstTime = 4;
        }
        Sound weaponCrafted = ServiceLocator.getResourceService().getAsset("sounds/new_Weapon_Crafted.wav", Sound.class);
        weaponCrafted.play();
        popUp = new Image
                (new Texture(Gdx.files.internal("images/Crafting-assets-sprint1/popups/crafting_indicator.png")));
        popUp.setHeight(5);
        popUp.setPosition(Gdx.graphics.getWidth()/2-71.25f, Gdx.graphics.getHeight()/2-23.75f);
        Action popUpAction = Actions.sequence(Actions.sizeTo(142.5f, 47.5f, 0.5f),
                Actions.delay(0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        popUp.remove();
                    }
                }));
        popUp.addAction(popUpAction);
        stage.addActor(popUp);
    }

    /**
     * Initialises the crafting tutorial assets for players opening the crafting table for the first time
     */
    private void initTutorial() {
        Action firstArrowAction = Actions.forever(Actions.sequence(Actions.moveBy(5, 5, 0.5f),
                Actions.moveBy(-5, -5, 0.5f)));
        firstMatArrow = new Image
                (new Texture(Gdx.files.internal("images/Crafting-assets-sprint1/popups/arrow-top-right.png")));
        firstMatArrow.setPosition(craftMenu.getX() + 152, craftMenu.getTop() - 228);
        firstMatArrow.addAction(firstArrowAction);
        Action secondArrowAction = Actions.forever(Actions.sequence(Actions.moveBy(5, 5, 0.5f),
                Actions.moveBy(-5, -5, 0.5f)));
        secondMatArrow = new Image
                (new Texture(Gdx.files.internal("images/Crafting-assets-sprint1/popups/arrow-top-right.png")));
        secondMatArrow.setPosition(craftMenu.getX() + 152, craftMenu.getTop() - 228);
        secondMatArrow.addAction(secondArrowAction);
        Action craftArrowAction = Actions.forever(Actions.sequence(Actions.moveBy(5, -5, 0.5f),
                Actions.moveBy(-5, 5, 0.5f)));
        craftArrow = new Image
                (new Texture(Gdx.files.internal("images/Crafting-assets-sprint1/popups/arrow-top-left.png")));
        craftArrow.setPosition(craftMenu.getX() + 650, craftMenu.getY() + 145);
        craftArrow.addAction(craftArrowAction);
        firstMatText = new Image
                (new Texture(Gdx.files.internal("images/Crafting-assets-sprint1/popups/first-mat-prompt.png")));
        firstMatText.setPosition(craftMenu.getX(), craftMenu.getY());
        secondMatText = new Image
                (new Texture(Gdx.files.internal("images/Crafting-assets-sprint1/popups/second-mat-prompt.png")));
        secondMatText.setPosition(craftMenu.getX(), craftMenu.getY());
        craftText = new Image
                (new Texture(Gdx.files.internal("images/Crafting-assets-sprint1/popups/craft-prompt.png")));
        craftText.setPosition(craftMenu.getX(), craftMenu.getY());
        firstTutorial.addActor(firstMatArrow);
        firstTutorial.addActor(firstMatText);
        secondTutorial.addActor(secondMatArrow);
        secondTutorial.addActor(secondMatText);
        thirdTutorial.addActor(craftArrow);
        thirdTutorial.addActor(craftText);
    }

    /**
     * Checks what type of material is being passed and return the type
     * @param entity the entity to be checked
     * @return, null if the entity is not craftable, the type of the entity if it is.
     */
    private EntityTypes checkType(Entity entity) {
        List<EntityTypes> entityTypes = entity.getEntityTypes();
        for (EntityTypes type : entityTypes) {
            if (!type.equals(EntityTypes.CRAFTABLE)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Takes an entity type and creates the material corresponding to that entity type. It then adds the given element
     * to the user inventory.
     * @param type the entity type to be created
     */
    private void addToInventory(EntityTypes type) {
        switch(type) {
            case GOLD:
                inventoryComponent.addItem(MaterialFactory.createGold());
                break;
            case IRON:
                inventoryComponent.addItem(MaterialFactory.createIron());
                break;
            case STEEL:
                inventoryComponent.addItem(MaterialFactory.createSteel());
                break;
            case WOOD:
                inventoryComponent.addItem(MaterialFactory.createWood());
                break;
            case PLASTIC:
                inventoryComponent.addItem(MaterialFactory.createPlastic());
                break;
            case RUBBER:
                inventoryComponent.addItem(MaterialFactory.createRubber());
                break;
            case PLATINUM:
                inventoryComponent.addItem(MaterialFactory.createPlatinum());
                break;
            case SILVER:
                inventoryComponent.addItem(MaterialFactory.createSilver());
                break;
            case POOP:
                inventoryComponent.addItem(MaterialFactory.createPoop());
                break;
            case TOILETPAPER:
                inventoryComponent.addItem(MaterialFactory.createToiletPaper());
                break;
            default:
                inventoryComponent.addItem(WeaponFactory.createHera());
        }
    }

    /**
     * Converts an entityType class to a Material which is stored in a box to indicate it's being
     * used for crafting
     * @param type the entity the user clicked on
     */
    private void addToBoxes(EntityTypes type) {
        Materials materials;
        if (type == EntityTypes.GOLD) {
            materials = Materials.Gold;
        } else if (type == EntityTypes.IRON) {
            materials = Materials.Iron;
        } else if (type == EntityTypes.STEEL) {
            materials = Materials.Steel;
        } else if (type == EntityTypes.WOOD) {
            materials = Materials.Wood;
        } else if (type == EntityTypes.PLASTIC) {
            materials = Materials.Plastic;
        } else if (type == EntityTypes.RUBBER) {
            materials = Materials.Rubber;
        } else if (type == EntityTypes.PLATINUM) {
            materials = Materials.Platinum;
        } else if (type == EntityTypes.SILVER) {
            materials = Materials.Silver;
        } else if (type == EntityTypes.POOP) {
            materials = Materials.Poop;
        }  else if (type == EntityTypes.TOILETPAPER) {
            materials = Materials.ToiletPaper;
        }
        else {
            materials = Materials.HerraDag;
        }
        if (this.boxes[0] == null)
            boxes[0] = materials;
        else if (this.boxes[1] == null)
            boxes[1] = materials;
        checkBuildables();
    }

    /**
     * Clears the boxes used to indicate what materials were selected for crafting
     * 0 clears both boxes
     * 1 clears the first box
     * 2 clears the first box
     * @param number the number that indicates what box is being disposed
     */
    private void clearBoxes(int number) {
        if (number == 0) {
            boxes[0] = null;
            boxes[1] = null;
            if (weapon != null) {
                weapon.remove();
                weapon = null;
            }
        } else if (number == 1) {
            boxes[0] = null;
            if (weapon != null) {
                weapon.remove();
                weapon = null;
            }
        } else if (number == 2) {
            boxes[1] = null;
            if (weapon != null) {
                weapon.remove();
                weapon = null;
            }
        }

    }

    /**
     * Displays the first page of the catalogue menu and adds event handlers for buttons
     */
    public void displayCatOne() {
        Sound catOneSound = ServiceLocator.getResourceService().getAsset("sounds/ItemClick.wav", Sound.class);
        catOneSound.play();
        disposeMaterials();
        disposeFirstTutorial();
        disposeSecondTutorial();
        catOneMenu = new Image(new Texture(Gdx.files.internal(String.format("images/Crafting-assets-sprint1/" +
                "crafting table/crafting_catalogue_1_lvl%d.png", gameLevel))));
        catOneMenu.setSize(883.26f, 500);
        catOneMenu.setPosition((float) (Gdx.graphics.getWidth() / (double) 2 - catOneMenu.getWidth() / 2),
                (float) (Gdx.graphics.getHeight() / (double) 2 - catOneMenu.getHeight() / 2));
        craftingGroup.addActor(catOneMenu);
        exitButton.setZIndex(catOneMenu.getZIndex() + 1);
        buttonTexture = new Texture(Gdx.files.internal
                (String.format("images/Crafting-assets-sprint1/widgets/inventory_button_lvl%d.png", gameLevel)));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        inventoryButton = new ImageButton(buttonDrawable);
        inventoryButton.setSize(146, 146);
        inventoryButton.setPosition(craftMenu.getX() + 150, craftMenu.getY() + 301);
        inventoryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Sound inventorySound = ServiceLocator.getResourceService().getAsset("sounds/ItemClick.wav", Sound.class);
                inventorySound.play();
                disposeCatOne();
                clearMaterials();
                getInventory();
                if (getGameAreaName().equals("Forest")) {
                    if (firstTime == 1) {
                        stage.addActor(firstTutorial);
                    } else if (firstTime == 2) {
                        stage.addActor(secondTutorial);
                    }
                }
            }
        });
        craftingGroup.addActor(inventoryButton);
        buttonTexture = new Texture(Gdx.files.internal
                (String.format("images/Crafting-assets-sprint1/widgets/catalogue_page2_lvl%d.png", gameLevel)));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        catTwoButton = new ImageButton(buttonDrawable);
        catTwoButton.setSize(60, 60);
        catTwoButton.setPosition(craftMenu.getX() + 110, craftMenu.getY() + 159);
        catTwoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disposeCatOne();
                displayCatTwo();
            }
        });
        craftingGroup.addActor(catTwoButton);
    }

    /**
     * Displays the second page of the catalogue menu and adds event handlers for buttons.
     */
    private void displayCatTwo() {
        Sound catTwoSound = ServiceLocator.getResourceService().getAsset("sounds/ItemClick.wav", Sound.class);
        catTwoSound.play();
        disposeMaterials();
        disposeFirstTutorial();
        disposeSecondTutorial();
        catTwoMenu = new Image(new Texture(Gdx.files.internal(String.format("images/Crafting-assets-sprint1/" +
                "crafting table/crafting_catalogue_2_lvl%d.png", gameLevel))));
        catTwoMenu.setSize(883.26f, 500);
        catTwoMenu.setPosition((float) (Gdx.graphics.getWidth() / (double) 2 - catTwoMenu.getWidth() / 2),
                (float) (Gdx.graphics.getHeight() / (double) 2 - catTwoMenu.getHeight() / 2));
        craftingGroup.addActor(catTwoMenu);
        exitButton.setZIndex(catTwoMenu.getZIndex() + 1);
        buttonTexture = new Texture(Gdx.files.internal
                (String.format("images/Crafting-assets-sprint1/widgets/inventory_button_lvl%d.png", gameLevel)));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        inventoryButton = new ImageButton(buttonDrawable);
        inventoryButton.setSize(146, 146);
        inventoryButton.setPosition(craftMenu.getX() + 150, craftMenu.getY() + 301);
        inventoryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Sound inventorySound = ServiceLocator.getResourceService().getAsset("sounds/ItemClick.wav", Sound.class);
                inventorySound.play();
                disposeCatTwo();
                clearMaterials();
                getInventory();
                if (getGameAreaName().equals("Forest")) {
                    if (firstTime == 1) {
                        stage.addActor(firstTutorial);
                    } else if (firstTime == 2) {
                        stage.addActor(secondTutorial);
                    }
                }
            }
        });
        craftingGroup.addActor(inventoryButton);
        buttonTexture = new Texture(Gdx.files.internal
                (String.format("images/Crafting-assets-sprint1/widgets/catalogue_page1_lvl%d.png", gameLevel)));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        catOneButton = new ImageButton(buttonDrawable);
        catOneButton.setSize(60, 60);
        catOneButton.setPosition(craftMenu.getX() + 110, craftMenu.getY() + 225);
        catOneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disposeCatTwo();
                displayCatOne();
                currentScreenCrafting = false;
            }
        });
        craftingGroup.addActor(catOneButton);
    }

    /**
     * Disposes the elements of the first category from the screen
     */
    private void disposeCatOne() {
        catOneMenu.remove();
        inventoryButton.remove();
        catTwoButton.remove();
    }

    /**
     * Disposes the elements of the second category from the screen
     */
    private void disposeCatTwo() {
        catTwoMenu.remove();
        inventoryButton.remove();
        catOneButton.remove();
    }

    /**
     * Disposes the third first from the screen
     */
    private void disposeFirstTutorial() {
        firstTutorial.remove();
    }

    /**
     * Disposes the second tutorial from the screen
     */
    private void disposeSecondTutorial() {
        secondTutorial.remove();
    }

    /**
     * Disposes the third tutorial from the screen
     */
    private void disposeThirdTutorial() {
        thirdTutorial.remove();
    }

    /**
     * Clears the materials from the boxes used for crafting
     */
    private void clearMaterials() {
        materialsGroup.clear();
    }

    /**
     * Disposes the materials shown in the crafting menu
     */
    private void disposeMaterials() {
        materialsGroup.remove();
    }

    /**
     * Disposes first box used for crafting
     */
    private void disposeFirstBox() {
        firstToCraft.remove();
    }

    /**
     * Disposes second box used for crafting
     */
    private void disposeSecondBox() {
        secondToCraft.remove();
    }

    /**
     * Disposes the crafting menu by deleting all the elements from the screen and also resets the boxes to make
     * it like new when opened again.
     */
    public void disposeCraftingMenu() {
        try {
            clearMaterials();
            clearBoxes(0);
            disposeFirstBox();
            disposeSecondBox();
        } catch (NullPointerException ignored) {
            logger.info("NPE Caught");
        }
        disposeFirstTutorial();
        disposeSecondTutorial();
        disposeThirdTutorial();
        craftingGroup.remove();
    }

    /**
     * Method that disposes the pause menu by removing all the elements present on the screen
     */
    public void disposePauseMenu() {
        pausingGroup.remove();
    }

    /**
     * This method takes a weapon and draws the item in the location of the crafting menu. There are
     * different scalings and positions for each weapons as the original weapons are different sizes.
     * @param item the weapon to be drawn on the crafting menu.
     */
    private void displayWeapon(WeaponConfig item) {
        Entity newItem = CraftingLogic.damageToWeapon(item);
        currentWeapon = newItem;
        String image = newItem.getComponent(TextureRenderComponent.class).getTexturePath();
        weapon = new Image(new Texture(Gdx.files.internal(image)));

        if (Math.floor(item.damage) == 35) {
            weapon.setSize(60, 60);
            //trident
            weapon.setPosition(craftMenu.getX() + 650, craftMenu.getY() + 220);
        } else if (Math.floor(item.damage) == 30) {
            //sword
            weapon.setSize(60, 60);
            weapon.setPosition(craftMenu.getX() + 675, craftMenu.getY() + 235);
        } else if (Math.floor(item.damage) == 15) {
            //pipe
            weapon.setSize(100, 100);
            weapon.setPosition(craftMenu.getX() + 640, craftMenu.getY() + 210);
        } else if (Math.floor(item.damage) == 10) {
            //plunger
            weapon.setSize(110, 110);
            weapon.setPosition(craftMenu.getX() + 640, craftMenu.getY() + 200);
        } else if (Math.floor(item.damage) == 40) {
            //herathena
            weapon.setSize(100, 100);
            weapon.setPosition(craftMenu.getX() + 640, craftMenu.getY() + 200);
        } else if (Math.floor(item.damage) == 20 || Math.floor(item.damage) == 70) {
            //bows
            weapon.setSize(50, 50);
            weapon.setPosition(craftMenu.getX() + 665, craftMenu.getY() + 230);
        } else {
            weapon.setSize(200, 200);
            weapon.setPosition(craftMenu.getX() + 600, craftMenu.getY() + 150);
        }
        craftingGroup.addActor(weapon);
    }

    /**
     * Toggles the skill tree display
     */
    public void toggleSkillTree() {
        SkillsTreeDisplay skillsTree = ServiceLocator.getGameArea().getPlayer().getComponent(SkillsTreeDisplay.class);
        if (skillsTree != null) {
            skillsTree.toggleSkillTreeDisplay();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {

        int screenHeight = Gdx.graphics.getHeight();
        float offsetX = 10f;
        float offsetY = 30f;

        title.setPosition(offsetX, screenHeight - offsetY);
    }

    @Override
    public void dispose() {
        super.dispose();
        title.remove();
    }
}