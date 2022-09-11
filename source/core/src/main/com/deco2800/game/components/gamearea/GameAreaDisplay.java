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
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.entities.factories.MaterialFactory;
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
  private ImageButton gold;
  private ImageButton iron;
  private ImageButton steel;
  private ImageButton wood;
  private ImageButton plastic;
  private ImageButton rubber;
  private ImageButton platinum;
  private ImageButton silver;
  private Texture materialTexture;
  private TextureRegion materialTextureRegion;
  private TextureRegionDrawable materialDrawable;
  private Image weapon;
  private Group craftingGroup = new Group();
  private Group materialsGroup = new Group();
  private Materials[] boxes = new Materials[2];
  private Group pausingGroup = new Group();
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
    inventoryComponent = new InventoryComponent();
    inventoryComponent.addItem(MaterialFactory.createGold());
    inventoryComponent.addItem(MaterialFactory.createPlatinum());
    inventoryComponent.addItem(MaterialFactory.createSilver());
    inventoryComponent.addItem(MaterialFactory.createSteel());
    inventoryComponent.addItem(MaterialFactory.createWood());
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
      inventoryMenu.setSize(640, 480 );
      inventoryMenu.setPosition(Gdx.graphics.getWidth() / 2 - inventoryMenu.getWidth(),
              Gdx.graphics.getHeight() / 2);
      inventoryGroup.addActor(inventoryMenu);
      stage.addActor(inventoryGroup);
      stage.draw();
  }

  public void showItem() {
    float padding = 20;
    items = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class).getInventory();
    for (int i = 0; i < items.size(); ++i) {
      Texture itemTexture = items.get(i).getComponent(TextureRenderComponent.class).getTexture();
      TextureRegion itemTextureRegion = new TextureRegion(itemTexture);
      TextureRegionDrawable itemTextureDrawable = new TextureRegionDrawable(itemTextureRegion);
      ImageButton item = new ImageButton(itemTextureDrawable);
      item.setSize(60, 60);
      if (i < 4){
            item.setPosition(inventoryMenu.getX() + 20 + (100 * (i+1)) + (10 * i), inventoryMenu.getY() + 450);
          } else if (4 < i || i <= 8) {
            item.setPosition(inventoryMenu.getX() + 20 + (100 * (i+1)) + (10 * i), inventoryMenu.getY() + 340);
          } else if (8 < i || i <= 12) {
            item.setPosition(inventoryMenu.getX() + 20 + (100 * (i+1)) + (10 * i), inventoryMenu.getY() + 220);
          } else if (12 < i || i <= 16) {
            item.setPosition(inventoryMenu.getX() + 20 + (100 * (i+1)) + (10 * i), inventoryMenu.getY() + 100);
          }
      inventoryGroup.addActor(item);
//      switch (items.get(i)) {
//        case Wood:
//          woodTexture = new Texture(Gdx.files.internal
//                  ("images/Crafting-assets-sprint1/materials/wood.png"));
//          woodTextureRegion = new TextureRegion(woodTexture);
//          woodDrawable = new TextureRegionDrawable(woodTextureRegion);
//          wood = new ImageButton(woodDrawable);
//          wood.setSize(50, 50);
//          if (i < 4){
//            wood.setPosition(inventoryMenu.getX() + 20 + (100 * (i+1)) + (10 * i), inventoryMenu.getY() + 450);
//          } else if (4 < i || i <= 8) {
//            wood.setPosition(inventoryMenu.getX() + 20 + (100 * (i+1)) + (10 * i), inventoryMenu.getY() + 340);
//          } else if (8 < i || i <= 12) {
//            wood.setPosition(inventoryMenu.getX() + 20 + (100 * (i+1)) + (10 * i), inventoryMenu.getY() + 220);
//          } else if (12 < i || i <= 16) {
//            wood.setPosition(inventoryMenu.getX() + 20 + (100 * (i+1)) + (10 * i), inventoryMenu.getY() + 100);
//          }
//          inventoryGroup.addActor(wood);
//          break;
//      }
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
    inventoryComponent.addItem(MaterialFactory.createGold());
    inventoryComponent.addItem(MaterialFactory.createPlatinum());
    inventoryComponent.addItem(MaterialFactory.createSilver());
    inventoryComponent.addItem(MaterialFactory.createSteel());
    inventoryComponent.addItem(MaterialFactory.createWood());
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
          weapon.setPosition(craftMenu.getX() + 100 +
                  (75 * (numcrafted-1)), craftMenu.getTop() - 475);
          ForestGameArea area = (ForestGameArea) ServiceLocator.getGameArea();
                  area.getPlayer().getComponent(InventoryComponent.class).addItem(currentWeapon);
        };
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

  private void getInventory() {

    this.possibleBuilds = CraftingLogic.getPossibleWeapons();
    index = 0;
    inventory = inventoryComponent.getInventory();
    for (Entity item : inventory) {
      if (item.checkEntityType(EntityTypes.GOLD)) {
        gold = initMaterials(Materials.Gold);
        gold.setSize(50, 50);
        gold.setPosition(craftMenu.getX() + 172 + ((index%4) * 68),
                (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 208)));
        index++;
        materialsGroup.addActor(gold);
        gold.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (boxes[0] == null) {
              clearMaterials();
              gold = initMaterials(Materials.Gold);
              gold.setSize(50, 50);
              gold.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
              addToBoxes(Materials.Gold);
              materialsGroup.addActor(gold);
              inventoryComponent.removeItem(EntityTypes.GOLD);
              getInventory();
            } else if (boxes[1] == null) {
              clearMaterials();
              gold = initMaterials(Materials.Gold);
              gold.setSize(50, 50);
              gold.setPosition(craftMenu.getX() + 501, craftMenu.getY() + 230);
              addToBoxes(Materials.Gold);
              materialsGroup.addActor(gold);
              inventoryComponent.removeItem(EntityTypes.GOLD);
              getInventory();

            }
          }

        });
      } else if (item.checkEntityType(EntityTypes.IRON)) {
        iron = initMaterials(Materials.Iron);
        iron.setSize(50, 50);
        iron.setPosition(craftMenu.getX() + 172 + ((index%4) * 68),
                (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 208)));
        index++;
        materialsGroup.addActor(iron);
        iron.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (boxes[0] == null) {
              clearMaterials();
              iron = initMaterials(Materials.Iron);
              iron.setSize(50, 50);
              iron.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
              addToBoxes(Materials.Iron);
              materialsGroup.addActor(iron);
              inventoryComponent.removeItem(EntityTypes.IRON);
              getInventory();
            } else if (boxes[1] == null) {
              clearMaterials();
              iron = initMaterials(Materials.Iron);
              iron.setSize(50, 50);
              iron.setPosition(craftMenu.getX() + 501, craftMenu.getY() + 230);
              addToBoxes(Materials.Iron);
              materialsGroup.addActor(iron);
              inventoryComponent.removeItem(EntityTypes.IRON);
              getInventory();
            }
          }
        });
      } else if (item.checkEntityType(EntityTypes.STEEL)) {
        steel = initMaterials(Materials.Steel);
        steel.setSize(50, 50);
        steel.setPosition(craftMenu.getX() + 172 + ((index%4) * 68),
                (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 208)));
        index++;
        materialsGroup.addActor(steel);
        steel.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (boxes[0] == null) {

              clearMaterials();
              steel = initMaterials(Materials.Steel);
              steel.setSize(50, 50);
              steel.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
              addToBoxes(Materials.Steel);
              materialsGroup.addActor(steel);
              inventoryComponent.removeItem(EntityTypes.STEEL);
              getInventory();
            } else if (boxes[1] == null) {

              clearMaterials();
              steel = initMaterials(Materials.Steel);
              steel.setSize(50, 50);
              steel.setPosition(craftMenu.getX() + 501, craftMenu.getY() + 230);
              addToBoxes(Materials.Steel);
              materialsGroup.addActor(steel);
              inventoryComponent.removeItem(EntityTypes.STEEL);
              getInventory();
            }
          }
        });
      } else if (item.checkEntityType(EntityTypes.WOOD)) {
        wood = initMaterials(Materials.Wood);
        wood.setSize(50, 50);
        wood.setPosition(craftMenu.getX() + 172 + ((index%4) * 68),
                (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 208)));
        index++;
        materialsGroup.addActor(wood);
        wood.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (boxes[0] == null) {

              clearMaterials();
              wood = initMaterials(Materials.Wood);
              wood.setSize(50, 50);
              wood.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
              addToBoxes(Materials.Wood);
              materialsGroup.addActor(wood);
              inventoryComponent.removeItem(EntityTypes.WOOD);
              getInventory();
            } else if (boxes[1] == null) {

              clearMaterials();
              wood = initMaterials(Materials.Wood);
              wood.setSize(50, 50);
              wood.setPosition(craftMenu.getX() + 501, craftMenu.getY() + 230);
              addToBoxes(Materials.Wood);
              materialsGroup.addActor(wood);
              inventoryComponent.removeItem(EntityTypes.WOOD);
              getInventory();
            }
          }
        });
      } else if (item.checkEntityType(EntityTypes.PLASTIC)) {
        plastic = initMaterials(Materials.Plastic);
        plastic.setSize(50, 50);
        plastic.setPosition(craftMenu.getX() + 172 + ((index%4) * 68),
                (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 208)));
        index++;
        materialsGroup.addActor(plastic);
        plastic.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (boxes[0] == null) {
              clearMaterials();
              plastic = initMaterials(Materials.Plastic);
              plastic.setSize(50, 50);
              plastic.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
              addToBoxes(Materials.Plastic);
              materialsGroup.addActor(plastic);
              inventoryComponent.removeItem(EntityTypes.PLASTIC);
              getInventory();
            } else if (boxes[1] == null) {
              clearMaterials();
              plastic = initMaterials(Materials.Plastic);
              plastic.setSize(50, 50);
              plastic.setPosition(craftMenu.getX() + 501, craftMenu.getY() + 230);
              addToBoxes(Materials.Plastic);
              materialsGroup.addActor(plastic);
              inventoryComponent.removeItem(EntityTypes.PLASTIC);
              getInventory();
            }
          }
        });
      } else if (item.checkEntityType(EntityTypes.RUBBER)) {
        rubber = initMaterials(Materials.Rubber);
        rubber.setSize(50, 50);
        rubber.setPosition(craftMenu.getX() + 172 + ((index%4) * 68),
                (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 208)));
        index++;
        materialsGroup.addActor(rubber);
        rubber.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (boxes[0] == null) {
              clearMaterials();
              rubber = initMaterials(Materials.Rubber);
              rubber.setSize(50, 50);
              rubber.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
              addToBoxes(Materials.Rubber);
              materialsGroup.addActor(rubber);
              inventoryComponent.removeItem(EntityTypes.RUBBER);
              getInventory();
            } else if (boxes[1] == null) {
              clearMaterials();
              rubber = initMaterials(Materials.Rubber);
              rubber.setSize(50, 50);
              rubber.setPosition(craftMenu.getX() + 501, craftMenu.getY() + 230);
              addToBoxes(Materials.Rubber);
              materialsGroup.addActor(rubber);
              inventoryComponent.removeItem(EntityTypes.RUBBER);
              getInventory();
            }
          }
        });
      } else if (item.checkEntityType(EntityTypes.PLATINUM)) {
        platinum = initMaterials(Materials.Platinum);
        platinum.setSize(50, 50);
        platinum.setPosition(craftMenu.getX() + 172 + ((index%4) * 68),
                (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 208)));
        index++;
        materialsGroup.addActor(platinum);
        platinum.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (boxes[0] == null) {

              clearMaterials();
              platinum = initMaterials(Materials.Platinum);
              platinum.setSize(50, 50);
              platinum.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
              addToBoxes(Materials.Platinum);
              materialsGroup.addActor(platinum);
              inventoryComponent.removeItem(EntityTypes.PLATINUM);
              getInventory();
            } else if (boxes[1] == null) {

              clearMaterials();
              platinum = initMaterials(Materials.Platinum);
              platinum.setSize(50, 50);
              platinum.setPosition(craftMenu.getX() + 501, craftMenu.getY() + 230);
              addToBoxes(Materials.Platinum);
              materialsGroup.addActor(platinum);
              inventoryComponent.removeItem(EntityTypes.PLATINUM);
              getInventory();
            }
          }
        });
      } else if (item.checkEntityType(EntityTypes.SILVER)) {
        silver = initMaterials(Materials.Silver);
        silver.setSize(50, 50);
        silver.setPosition(craftMenu.getX() + 172 + ((index%4) * 68),
                (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 208)));
        index++;
        materialsGroup.addActor(silver);
        silver.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (boxes[0] == null) {

              clearMaterials();
              silver = initMaterials(Materials.Silver);
              silver.setSize(50, 50);
              silver.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
              addToBoxes(Materials.Silver);
              materialsGroup.addActor(silver);
              inventoryComponent.removeItem(EntityTypes.SILVER);
              getInventory();
            } else if (boxes[1] == null) {

              clearMaterials();
              silver = initMaterials(Materials.Silver);
              silver.setSize(50, 50);
              silver.setPosition(craftMenu.getX() + 501, craftMenu.getY() + 230);
              addToBoxes(Materials.Silver);
              materialsGroup.addActor(silver);
              inventoryComponent.removeItem(EntityTypes.SILVER);
              getInventory();
            }
          }
        });
      }
    }
    stage.addActor(materialsGroup);
  }

  private ImageButton initMaterials(Materials material) {
    materialTexture = new Texture(material.getImage());
    materialTextureRegion = new TextureRegion(materialTexture);
    materialDrawable = new TextureRegionDrawable(materialTextureRegion);
    return new ImageButton(materialDrawable);
  }

  private void addToBoxes(Materials materials) {
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
    } else if (number == 2) {
      boxes[1] = null;
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

  public void disposeCraftingMenu() {
    materialsGroup.clear();
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
    weapon.setSize(200, 200);
    weapon.setPosition(craftMenu.getX() + 600, craftMenu.getY() + 150);
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