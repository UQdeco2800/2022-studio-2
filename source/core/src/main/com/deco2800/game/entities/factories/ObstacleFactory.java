package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.deco2800.game.components.MenuComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * Factory to create obstacle entities.
 *
 * <p>Each obstacle entity type should have a creation method that returns a corresponding entity.
 */
public class ObstacleFactory {

  /**
   * Creates a tree entity.
   * @return entity
   */
  public static Entity createTree() {
    Entity tree =
        new Entity()
            .addComponent(new TextureRenderComponent("images/Map_assets/sprint_1/tree-1_1.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    tree.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    tree.getComponent(TextureRenderComponent.class).scaleEntity();
    tree.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(tree, 0.5f, 0.2f);
    return tree;
  }

  /**
   * Creates a small tree entity. - Team 5 1map4all @LYB
   * @return small tree entity
   */
  public static Entity createSmallTree() {
    Entity smallTree =
            new Entity()
                    .addComponent(new TextureRenderComponent("images/Map_assets/sprint_1/tree-2_2" +
                            ".png"))
                    .addComponent(new PhysicsComponent())
                    .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    smallTree.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    smallTree.getComponent(TextureRenderComponent.class).scaleEntity();
    smallTree.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(smallTree, 0.5f, 0.2f);
    return smallTree;
  }


  public static Entity createCraftingMenu() {
    Entity craftingTable =
            new Entity()
                    .addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1" +
                            "/crafting table/craftingUI-2-Sprint1.png"))
                    .addComponent(new PhysicsComponent())
                    .addComponent(new ColliderComponent().setLayer(PhysicsLayer.NONE))
                    .addComponent(new MenuComponent());

    craftingTable.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    craftingTable.getComponent(TextureRenderComponent.class).scaleEntity();
    craftingTable.scaleHeight(10f);

    return craftingTable;
  }

  public static Entity createCraftingTable() {
    Entity craftingTable =
            new Entity()
                    .addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1" +
                            "/crafting table/craftingTable.png"))
                    .addComponent(new PhysicsComponent())
                    .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    craftingTable.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    craftingTable.getComponent(TextureRenderComponent.class).scaleEntity();
    craftingTable.scaleHeight(1.5f);
    PhysicsUtils.setScaledCollider(craftingTable, 0.8f, 0.7f);
    return craftingTable;
  }

  /**
   * Creates an invisible physics wall.
   * @param width Wall width in world units
   * @param height Wall height in world units
   * @return Wall entity of given width and height
   */
  public static Entity createWall(float width, float height) {
    Entity wall = new Entity()
        .addComponent(new PhysicsComponent().setBodyType(BodyType.StaticBody))
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));
    wall.setScale(width, height);
    return wall;
  }

  /**
   * Creates a column entity on the map. - Team 5 1map4all @LYB
   * @return Column entity.
   */
  public static Entity createColumn() {
    Entity column =
            new Entity()
                    .addComponent(new TextureRenderComponent("images/Map_assets/sprint_1/column.png"))
                    .addComponent(new PhysicsComponent())
                    .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    column.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    column.getComponent(TextureRenderComponent.class).scaleEntity();
    column.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(column, 0.9f, 0.9f);
    return column;
  }

  private ObstacleFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
