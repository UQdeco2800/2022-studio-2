package com.deco2800.game.areas.terrain;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.deco2800.game.areas.terrain.TerrainComponent.TerrainOrientation;
import com.deco2800.game.components.CameraComponent;
import com.deco2800.game.utils.math.RandomUtils;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;

/** Factory for creating game terrains. */
public class TerrainFactory {
  private static final GridPoint2 MAP_SIZE = new GridPoint2(50, 50);
  private static final int TUFT_TILE_COUNT = 5;
  private static final int ROCK_TILE_COUNT = 5;

  private final OrthographicCamera camera;
  private final TerrainOrientation orientation;

  /**
   * Create a terrain factory with Orthogonal orientation
   *
   * @param cameraComponent Camera to render terrains to. Must be ortographic.
   */
  public TerrainFactory(CameraComponent cameraComponent) {
    this(cameraComponent, TerrainOrientation.ORTHOGONAL);
  }

  /**
   * Create a terrain factory
   *
   * @param cameraComponent Camera to render terrains to. Must be orthographic.
   * @param orientation orientation to render terrain at
   */
  public TerrainFactory(CameraComponent cameraComponent, TerrainOrientation orientation) {
    this.camera = (OrthographicCamera) cameraComponent.getCamera();
    this.orientation = orientation;
  }

  /**
   * Create a terrain of the given type, using the orientation of the factory. This can be extended
   * to add additional game terrains.
   *
   * @param terrainType Terrain to create
   * @return Terrain component which renders the terrain
   */
  public TerrainComponent createTerrain(TerrainType terrainType) {
    ResourceService resourceService = ServiceLocator.getResourceService();
    switch (terrainType) {
      case FOREST_DEMO:
        TextureRegion orthoGrass =
            new TextureRegion(resourceService.getAsset("images/grass_1.png", Texture.class));
        TextureRegion orthoTuft =
            new TextureRegion(resourceService.getAsset("images/grass_2.png", Texture.class));
        TextureRegion orthoRocks =
            new TextureRegion(resourceService.getAsset("images/grass_3.png", Texture.class));
        return createForestDemoTerrain(0.5f, orthoGrass, orthoTuft, orthoRocks);
      case FOREST_DEMO_ISO:
        TextureRegion isoGrass =
            new TextureRegion(resourceService.getAsset("images/iso_grass_1.png", Texture.class));
        TextureRegion isoTuft =
            new TextureRegion(resourceService.getAsset("images/iso_grass_2.png", Texture.class));
        TextureRegion isoRocks =
            new TextureRegion(resourceService.getAsset("images/iso_grass_3.png", Texture.class));
        return createForestDemoTerrain(1f, isoGrass, isoTuft, isoRocks);
      case FOREST_DEMO_HEX:
        TextureRegion hexGrass =
            new TextureRegion(resourceService.getAsset("images/hex_grass_1.png", Texture.class));
        TextureRegion hexTuft =
            new TextureRegion(resourceService.getAsset("images/hex_grass_2.png", Texture.class));
        TextureRegion hexRocks =
            new TextureRegion(resourceService.getAsset("images/hex_grass_3.png", Texture.class));
        return createForestDemoTerrain(1f, hexGrass, hexTuft, hexRocks);
      case LevelOne:
        TextureRegion orthoCobble =
                new TextureRegion(resourceService.getAsset("images/gold_cobble.png", Texture.class));
        TextureRegion orthoDrain =
                new TextureRegion(resourceService.getAsset("images/gold_drain.png", Texture.class));
        TextureRegion orthoRubble =
                new TextureRegion(resourceService.getAsset("images/grass_3.png", Texture.class));
        return createForestDemoTerrain(0.5f, orthoCobble, orthoDrain, orthoRubble);
      case LevelOneFlat:
        TextureRegion gold_cobble =
                new TextureRegion(resourceService.getAsset("images/level_1_tiledmap/32x32/gold_cobble.png", Texture.class));
        TextureRegion grass =
                new TextureRegion(resourceService.getAsset("images/level_1_tiledmap/32x32/grass" +
                        ".png", Texture.class));
        TextureRegion gold_drain =
                new TextureRegion(resourceService.getAsset("images/level_1_tiledmap/32x32/gold_drain.png",
                        Texture.class));
        TextureRegion water_tile =
                new TextureRegion(resourceService.getAsset("images/level_1_tiledmap/32x32/water_tile.png",
                        Texture.class));
        TextureRegion wall_tile =
                new TextureRegion(resourceService.getAsset("images/level_1_tiledmap/32x32/wall_tile.png",
                        Texture.class));
        TextureRegion tile_wet =
                new TextureRegion(resourceService.getAsset("images/level_1_tiledmap/32x32/tile_wet.png",
                        Texture.class));
        TextureRegion stairs =
                new TextureRegion(resourceService.getAsset("images/level_1_tiledmap/32x32/stairs.png",
                        Texture.class));
        TextureRegion tree =
                new TextureRegion(resourceService.getAsset("images/level_1_tiledmap/32x32/tree.png",
                        Texture.class));
        TextureRegion column =
                new TextureRegion(resourceService.getAsset("images/level_1_tiledmap/32x32/column.png",
                        Texture.class));
        return createLevelOne(1.2f, gold_cobble, grass, gold_drain, water_tile,
                wall_tile, tile_wet, stairs, tree, column);
      default:
        return null;
    }
  }

  private TerrainComponent createForestDemoTerrain(
      float tileWorldSize, TextureRegion grass, TextureRegion grassTuft, TextureRegion rocks) {
    GridPoint2 tilePixelSize = new GridPoint2(grass.getRegionWidth(), grass.getRegionHeight());
    TiledMap tiledMap = createForestDemoTiles(tilePixelSize, grass, grassTuft, rocks);
    TiledMapRenderer renderer = createRenderer(tiledMap, tileWorldSize / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation, tileWorldSize);
  }

  private TerrainComponent createLevelOne(
    float tileWorldSize, TextureRegion gold_cobble, TextureRegion grass, TextureRegion gold_drain
          , TextureRegion water_tile, TextureRegion wall_tile, TextureRegion tile_wet,
    TextureRegion stairs, TextureRegion tree, TextureRegion column
    ) {
    GridPoint2 tilePixelSize = new GridPoint2(gold_cobble.getRegionWidth(), gold_cobble.getRegionHeight());
    TiledMap tiledMap =   new TmxMapLoader().load("images/level_1_tiledmap/level_1.tmx");
    TiledMapRenderer renderer = createRenderer(tiledMap, tileWorldSize / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation, tileWorldSize);
  }

  private TiledMapRenderer createRenderer(TiledMap tiledMap, float tileScale) {
    switch (orientation) {
      case ORTHOGONAL:
        return new OrthogonalTiledMapRenderer(tiledMap, tileScale);
      case ISOMETRIC:
        return new IsometricTiledMapRenderer(tiledMap, tileScale);
      case HEXAGONAL:
        return new HexagonalTiledMapRenderer(tiledMap, tileScale);
      default:
        return null;
    }
  }

  private TiledMap createForestDemoTiles(
      GridPoint2 tileSize, TextureRegion grass, TextureRegion grassTuft, TextureRegion rocks) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(grass);
    TerrainTile grassTuftTile = new TerrainTile(grassTuft);
    TerrainTile rockTile = new TerrainTile(rocks);
    TiledMapTileLayer layer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y, tileSize.x, tileSize.y);

    // Create base grass
    fillTiles(layer, MAP_SIZE, grassTile);

    // Add some grass and rocks
    fillTilesAtRandom(layer, MAP_SIZE, grassTuftTile, TUFT_TILE_COUNT);
    fillTilesAtRandom(layer, MAP_SIZE, rockTile, ROCK_TILE_COUNT);

    tiledMap.getLayers().add(layer);
    return tiledMap;
  }

  private static void fillTilesAtRandom(
      TiledMapTileLayer layer, GridPoint2 mapSize, TerrainTile tile, int amount) {
    GridPoint2 min = new GridPoint2(0, 0);
    GridPoint2 max = new GridPoint2(mapSize.x - 1, mapSize.y - 1);

    for (int i = 0; i < amount; i++) {
      GridPoint2 tilePos = RandomUtils.random(min, max);
      Cell cell = layer.getCell(tilePos.x, tilePos.y);
      cell.setTile(tile);
    }
  }

  private static void fillTiles(TiledMapTileLayer layer, GridPoint2 mapSize, TerrainTile tile) {
    for (int x = 0; x < mapSize.x; x++) {
      for (int y = 0; y < mapSize.y; y++) {
        Cell cell = new Cell();
        cell.setTile(tile);
        layer.setCell(x, y, cell);
      }
    }
  }

  /**
   * This enum should contain the different terrains in your game, e.g. forest, cave, home, all with
   * the same oerientation. But for demonstration purposes, the base code has the same level in 3
   * different orientations.
   */
  public enum TerrainType {
    FOREST_DEMO,
    FOREST_DEMO_ISO,
    FOREST_DEMO_HEX,
    LevelOne,
    LevelOneFlat
  }
}
