package com.deco2800.game.components.gamearea;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.OpenCraftingComponent;
import com.deco2800.game.crafting.CraftingLogic;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.crafting.CraftingSystem;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.configs.CombatItemsConfig.MeleeConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.WeaponConfig;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.entities.factories.MaterialFactory;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.util.List;
import java.util.Map;

/**
 * Displays the name of the current game area.
 */
public class GameAreaDisplay extends UIComponent {
  private String gameAreaName = "";
  private Label title;

  private int numcrafted = 0;
  private ImageButton craftButton;
  private ImageButton catalogueButton;
  private ImageButton catOneButton;
  private ImageButton catTwoButton;
  private ImageButton inventoryButton;
  private ImageButton exitButton;
  private Texture buttonTexture;

  private TextureRegion buttonTextureRegion;
  private TextureRegionDrawable buttonDrawable;
  private Image craftMenu;
  private List<MeleeConfig> possibleBuilds;
  Entity currentWeapon;
  private Image catOneMenu;
  private Image catTwoMenu;
  private Image pauseMenu;
  private ImageButton material;
  private ImageButton firstToCraft;
  private ImageButton secondToCraft;
  private Texture materialTexture;
  private TextureRegion materialTextureRegion;
  private TextureRegionDrawable materialDrawable;

  private String weaponType = "";
  private Image weapon;
  private Group craftingGroup = new Group();
  private Group materialsGroup = new Group();
  private Materials[] boxes = new Materials[2];
  private Group pausingGroup = new Group();

  private int firstTime = 0;
  List<Entity> inventory;
  InventoryComponent inventoryComponent;
  private int index;
  private Image inventoryMenu;
  private Group inventoryGroup = new Group();
  private List<Entity> items;

  public GameAreaDisplay(String gameAreaName) {
    this.gameAreaName = gameAreaName;
    ServiceLocator.registerCraftArea(this);
    ServiceLocator.registerInventoryArea(this);
    ServiceLocator.registerPauseArea(this);
  }

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {
    title = new Label(this.gameAreaName, skin, "large");
    stage.addActor(title);
  }

  /**
   * Displays the inventory UI.
   *
   * INVENTORY_DISPLAY Self-made tag for the ease of searching
   */
  public void displayInventoryMenu() {
      inventoryMenu = new Image(new Texture(Gdx.files.internal
              ("images/Inventory/Inventory_Armor_V2.png")));
      //Note: the position of the asset is at the bottom left.
      inventoryMenu.setSize(768, 576 );
      inventoryMenu.setPosition(Gdx.graphics.getWidth() / 2 - inventoryMenu.getWidth(),
              Gdx.graphics.getHeight() / 2 - inventoryMenu.getHeight() / 2);
      inventoryGroup.addActor(inventoryMenu);
      stage.addActor(inventoryGroup);
      stage.draw();
  }

  /**
   * Display each item in the inventory in the inventory storage blocks.
   * Implemented by Team 2.
   */
  public void showItem() {
    float padding = 12.5f;
    InventoryComponent inventory =ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
    items = inventory.getInventory();
    for (int i = 0; i < items.size(); ++i) {
      Entity currentItem = items.get(i);
      Texture itemTexture = currentItem.getComponent(TextureRenderComponent.class).getTexture();
      TextureRegion itemTextureRegion = new TextureRegion(itemTexture);
      TextureRegionDrawable itemTextureDrawable = new TextureRegionDrawable(itemTextureRegion);
      ImageButton item = new ImageButton(itemTextureDrawable);
      item.setSize(53, 53);
      //187.5 and 360 are the magic numbers. DO NOT CHANGE!
      int row = i / 4;
      int column = i % 4;
      float horizontalPosition = (inventoryMenu.getX() + 187.5f) + column * (padding + 53);
      float verticalPosition = (inventoryMenu.getY() + 360) - row * (padding + 53);
      item.setPosition(horizontalPosition, verticalPosition);
      // Triggers an event when the button is pressed.
      String buttonText;
      if (items.get(i).checkEntityType(EntityTypes.WEAPON)
      || items.get(i).checkEntityType(EntityTypes.ARMOUR)) {
        buttonText = "Equip item";
      } else if (items.get(i).checkEntityType(EntityTypes.POTION)){
        buttonText = "Add to quick bar";
      } else {
        buttonText = "Add to crafting menu";
      }
      item.addListener(
              new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                  TextButton mainMenuBtn = new TextButton(buttonText, skin);
                  mainMenuBtn.setPosition(horizontalPosition, verticalPosition);
                  mainMenuBtn.addListener(
                          new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                              switch (buttonText) {
                                case "Equip item":
                                 inventory.equipItem(currentItem);
                                 break;
                                case "Add to quick bar":
                                  inventory.addQuickBarItems(currentItem);
                                  break;
                                case "Add to crafting menu":
                                  //Crafting team use this block to add items in crafting menu
                                  break;
                              }
                            }
                          }
                  );
                  inventoryGroup.addActor(mainMenuBtn);
                }
              });
      inventoryGroup.addActor(item);
    }
  }

  /**
   * Disposes the inventory display group.
   */
  public void disposeInventoryMenu() {
    inventoryGroup.remove();
  }


  /**
   * Code that opens an overlay crafting menu when the craft button is pressed. Creates assets based on users inventory
   * and creates button event handlers to test for user clicks.
   */
  public void openCraftingMenu() {
    if (firstTime == 0) {
      inventoryComponent = new InventoryComponent();
      inventoryComponent.addItem(MaterialFactory.createGold());
      inventoryComponent.addItem(MaterialFactory.createPlatinum());
      inventoryComponent.addItem(MaterialFactory.createSilver());
      inventoryComponent.addItem(MaterialFactory.createSteel());
      inventoryComponent.addItem(MaterialFactory.createWood());
      firstTime += 1;
    }
    craftMenu = new Image(new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/crafting table/crafting_inventory.png")));
    craftMenu.setSize(883.26f, 500);
    craftMenu.setPosition(Gdx.graphics.getWidth()/2 - craftMenu.getWidth()/2,
            Gdx.graphics.getHeight()/2 - craftMenu.getHeight()/2);
    craftingGroup.addActor(craftMenu);

    getInventory();

    buttonTexture = new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/widgets/craft_button.png"));
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
          ForestGameArea area = (ForestGameArea) ServiceLocator.getGameArea();
          inventoryComponent.addItem(currentWeapon);
          weapon.remove();
          weapon = null;
          clearBoxes(0);
        };
        getInventory();
      }
    });
    craftingGroup.addActor(craftButton);
    entity.getEvents().addListener("check", this::checkBuildables);
    buttonTexture = new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/widgets/catalogue_button.png"));
    buttonTextureRegion = new TextureRegion(buttonTexture);
    buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
    catalogueButton = new ImageButton(buttonDrawable);
    catalogueButton.setSize(146, 146);
    catalogueButton.setPosition(craftMenu.getX() + 300, craftMenu.getY() + 302);
    catalogueButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        displayCatOne();
      }
    });
    craftingGroup.addActor(catalogueButton);
    buttonTexture = new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/widgets/exit_button.png"));
    buttonTextureRegion = new TextureRegion(buttonTexture);
    buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
    exitButton = new ImageButton(buttonDrawable);
    exitButton.setSize(35, 35);
    exitButton.setPosition(craftMenu.getX() + 720, craftMenu.getY() + 365);
    exitButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        disposeCraftingMenu();
        EntityService.pauseAndResume();
        OpenCraftingComponent.setCraftingStatus();
      }
    });
    craftingGroup.addActor(exitButton);
    stage.addActor(craftingGroup);
    stage.draw();
  }

  private void checkBuildables() {

    if (boxes[0] != null && boxes[1] != null){
      for (MeleeConfig item: possibleBuilds){
        int numItems = 0;
        for (Map.Entry entry: item.materials.entrySet()){
          String entryString = entry.toString().split("=")[0];
          String upperCaseEntry = entryString.substring(0, 1).toUpperCase() + entryString.substring(1);
          System.out.print(upperCaseEntry);
          System.out.println(boxes[0].toString());
          if (boxes[0].toString().equals(upperCaseEntry) ||
                  boxes[1].toString().equals(upperCaseEntry)){
            numItems += 1;

          }
        }
        if (numItems == 2){
          displayWeapon(item);
          break;
        }
      }
    }
  }

  public void setPauseMenu() {
    pauseMenu = new Image(new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/screens/pauseScreen.png")));
    pauseMenu.setPosition(Gdx.graphics.getWidth()/2 - pauseMenu.getWidth()/2,
            Gdx.graphics.getHeight()/2 - pauseMenu.getHeight()/2);
    pausingGroup.addActor(pauseMenu);
    stage.addActor(pausingGroup);
    stage.draw();
  }
  //return the inventory for the user

  private void getInventory() {
    index = 0;
    this.possibleBuilds = CraftingLogic.getPossibleWeapons();
    inventory = inventoryComponent.getInventory();
    for (Entity item : inventory) {
      if (item.checkEntityType(EntityTypes.CRAFTABLE)) {
        materialTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
        materialTextureRegion = new TextureRegion(materialTexture);
        materialDrawable = new TextureRegionDrawable(materialTextureRegion);
        material = new ImageButton(materialDrawable);
        material.setSize(50, 50);

        material.setPosition(craftMenu.getX() + 172 + ((index%4) * 68),
                (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 208)));
        index++;
        material.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (boxes[0] == null) {
              clearMaterials();
              materialTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
              materialTextureRegion = new TextureRegion(materialTexture);
              materialDrawable = new TextureRegionDrawable(materialTextureRegion);
              firstToCraft = new ImageButton(materialDrawable);
              firstToCraft.setSize(50, 50);
              firstToCraft.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
              stage.addActor(firstToCraft);
              addToBoxes(checkType(item));
              inventoryComponent.removeItem(checkType(item));
              firstToCraft.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                  disposeFirstBox();
                  clearBoxes(1);
                  addToInventory(checkType(item));
                  getInventory();
                }
              });
              getInventory();
            } else if (boxes[1] == null) {
              clearMaterials();
              materialTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
              materialTextureRegion = new TextureRegion(materialTexture);
              materialDrawable = new TextureRegionDrawable(materialTextureRegion);
              secondToCraft = new ImageButton(materialDrawable);
              secondToCraft.setSize(50, 50);
              secondToCraft.setPosition(craftMenu.getX() + 548, craftMenu.getY() + 230);
              stage.addActor(secondToCraft);
              addToBoxes(checkType(item));
              inventoryComponent.removeItem(checkType(item));
              secondToCraft.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                  disposeSecondBox();
                  clearBoxes(2);
                  addToInventory(checkType(item));
                  getInventory();
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

  private EntityTypes checkType(Entity entity) {
    EntityTypes result = null;
    if (entity.checkEntityType(EntityTypes.GOLD)) {
      result = EntityTypes.GOLD;
    } else if (entity.checkEntityType(EntityTypes.IRON)) {
      result = EntityTypes.IRON;
    } else if (entity.checkEntityType(EntityTypes.STEEL)) {
      result = EntityTypes.STEEL;
    } else if (entity.checkEntityType(EntityTypes.WOOD)) {
      result = EntityTypes.WOOD;
    } else if (entity.checkEntityType(EntityTypes.PLASTIC)) {
      result = EntityTypes.PLASTIC;
    } else if (entity.checkEntityType(EntityTypes.RUBBER)) {
      result = EntityTypes.RUBBER;
    } else if (entity.checkEntityType(EntityTypes.PLATINUM)) {
      result = EntityTypes.PLATINUM;
    } else if (entity.checkEntityType(EntityTypes.SILVER)) {
      result = EntityTypes.SILVER;
    }
    return result;
  }

  private void addToInventory(EntityTypes type) {
    if (type == EntityTypes.GOLD) {
      inventoryComponent.addItem(MaterialFactory.createGold());
    } else if (type == EntityTypes.IRON) {
      inventoryComponent.addItem(MaterialFactory.createIron());
    } else if (type == EntityTypes.STEEL) {
      inventoryComponent.addItem(MaterialFactory.createSteel());
    } else if (type == EntityTypes.WOOD) {
      inventoryComponent.addItem(MaterialFactory.createWood());
    } else if (type == EntityTypes.PLASTIC) {
      inventoryComponent.addItem(MaterialFactory.createPlastic());
    } else if (type == EntityTypes.RUBBER) {
      inventoryComponent.addItem(MaterialFactory.createRubber());
    } else if (type == EntityTypes.PLATINUM) {
      inventoryComponent.addItem(MaterialFactory.createPlatinum());
    } else if (type == EntityTypes.SILVER) {
      inventoryComponent.addItem(MaterialFactory.createSilver());
    }
  }

  private void addToBoxes(EntityTypes type) {
    Materials materials = Materials.Gold;
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
    } else {
      materials = Materials.HerraDag;
    }
    if (this.boxes[0] == null)
      boxes[0] = materials;
    else if (this.boxes[1] == null)
      boxes[1] = materials;
    checkBuildables();
  }
  //0 Clears both boxes
  //1 Clears box 1
  //2 Clears box 2
  private void clearBoxes(int number){
    if (number == 0) {
      boxes[0] = null;
      boxes[1] = null;
    } else if (number == 1) {
      boxes[0] = null;
      if (weapon != null){
        weapon.remove();
        weapon = null;
      }
    } else if (number == 2) {
      boxes[1] = null;
      if (weapon != null){
        weapon.remove();
        weapon = null;
      }
    }

  }

  private void displayCatOne() {
    disposeMaterials();
    catOneMenu = new Image(new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/crafting table/crafting_catalogue_1.png")));
    catOneMenu.setSize(883.26f, 500);
    catOneMenu.setPosition(Gdx.graphics.getWidth() / 2 - catOneMenu.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - catOneMenu.getHeight() / 2);
    craftingGroup.addActor(catOneMenu);
    exitButton.setZIndex(catOneMenu.getZIndex() + 1);
    buttonTexture = new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/widgets/inventory_button.png"));
    buttonTextureRegion = new TextureRegion(buttonTexture);
    buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
    inventoryButton = new ImageButton(buttonDrawable);
    inventoryButton.setSize(146, 146);
    inventoryButton.setPosition(craftMenu.getX() + 150, craftMenu.getY() + 301);
    inventoryButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        disposeCatOne();
        getInventory();
      }
    });
    craftingGroup.addActor(inventoryButton);
    buttonTexture = new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/widgets/catalogue_2_button.png"));
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

  private void displayCatTwo() {
    disposeMaterials();
    catTwoMenu = new Image(new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/crafting table/crafting_catalogue_2.png")));
    catTwoMenu.setSize(883.26f, 500);
    catTwoMenu.setPosition(Gdx.graphics.getWidth()/2 - catTwoMenu.getWidth()/2,
            Gdx.graphics.getHeight()/2 - catTwoMenu.getHeight()/2);
    craftingGroup.addActor(catTwoMenu);
    exitButton.setZIndex(catTwoMenu.getZIndex()+1);
    buttonTexture = new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/widgets/inventory_button.png"));
    buttonTextureRegion = new TextureRegion(buttonTexture);
    buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
    inventoryButton = new ImageButton(buttonDrawable);
    inventoryButton.setSize(146, 146);
    inventoryButton.setPosition(craftMenu.getX() + 150, craftMenu.getY() + 301);
    inventoryButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        disposeCatTwo();
        getInventory();
      }
    });
    craftingGroup.addActor(inventoryButton);
    buttonTexture = new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/widgets/catalogue_1_button.png"));
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
      }
    });
    craftingGroup.addActor(catOneButton);
  }

  private void disposeCatOne() {
    catOneMenu.remove();
    inventoryButton.remove();
    catTwoButton.remove();
  }

  private void disposeCatTwo() {
    catTwoMenu.remove();
    inventoryButton.remove();
    catOneButton.remove();
  }

  private void clearMaterials() {
    materialsGroup.clear();
  }

  private void disposeMaterials() {
    materialsGroup.remove();
  }

  private void disposeFirstBox() {
    firstToCraft.remove();
  }

  private void disposeSecondBox() {
    secondToCraft.remove();
  }

  public void disposeCraftingMenu() {
    try {
      clearMaterials();
      clearBoxes(0);
      disposeFirstBox();
      disposeSecondBox();
    } catch (NullPointerException e) {}
    craftingGroup.remove();
  }

  public void disposePauseMenu() {
    pausingGroup.remove();
  }

  private void displayWeapon(MeleeConfig item) {
    Entity newItem = CraftingLogic.damageToWeapon(item);
    currentWeapon = newItem;
    String image = newItem.getComponent(TextureRenderComponent.class).getTexturePath();
    weapon = new Image(new Texture(Gdx.files.internal(image)));
    if (Math.floor(item.damage) == 25){
      weapon.setSize(60, 60);
      weaponType = "Trident";
      weapon.setPosition(craftMenu.getX() + 650, craftMenu.getY() + 220);
    } else {
      weapon.setSize(200, 200);
      weapon.setPosition(craftMenu.getX() + 600, craftMenu.getY() + 150);
    }
    numcrafted += 1;
    craftingGroup.addActor(weapon);
  }

  @Override
  public void draw(SpriteBatch batch)  {
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