package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.deathscreen.DeathScreenActions;
import com.deco2800.game.components.deathscreen.DeathScreenDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The game screen containing the death screen.
 */
public class DeathScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(DeathScreen.class);
    private final GdxGame game;
    private final int level;
    private final Renderer renderer;
    private static final String[] deathTextures = {"images/DeathScreens/lvl_1.png", "images/DeathScreens/lvl_2.png",
            "images/WinScreen/atlantis_sinks_no_island.png", "images/WinScreen/Win.png",
            "images/WinScreen/atlantis_sinks_no_island_win.png",
            "images/DeathScreens/lvl_2_w_buttons.png", "images/DeathScreens/lvl_1_w_buttons.png"
    };
    private static final String[] deathBtnTextures = {"images/DeathScreens/widgets/main_menu_lvl_1.png",
                                                    "images/DeathScreens/widgets/main_menu_lvl_2.png",
                                                    "images/DeathScreens/widgets/play_again_lvl_1.png",
                                                    "images/DeathScreens/widgets/play_again_lvl_2.png",
                                                    "images/WinScreen/winbtn.png"};
    private static final String backgroundMusic = "sounds/MenuSong-Overcast.mp3";
    private static final String buzzDeathMusic = "sounds/buzz_death.mp3";
    private static final String flushWinSound = "sounds/flush_win.mp3";
    private static final String[] deathMusic = {backgroundMusic, buzzDeathMusic, flushWinSound};


    /**
     * DeathScreen constructor
     * @param game
     * @param level
     */
    public DeathScreen(GdxGame game, int level) {
        this.game = game;
        this.level = level;
        logger.debug("Initialising death screen screen services");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        renderer = RenderFactory.createRenderer();

        loadAssets();
        createUI(level);
        playMusic();
    }

    private void playMusic() {
        // Unsure why the load is needed here (a second time too), but otherwise it doesn't play
        ServiceLocator.getResourceService().loadMusic(deathMusic);
        Music music = ServiceLocator.getResourceService().getAsset(buzzDeathMusic, Music.class);
        if (level == 1 || level == 2) {
            music = ServiceLocator.getResourceService().getAsset(buzzDeathMusic, Music.class);
            music.setLooping(true);
        } else {
            music = ServiceLocator.getResourceService().getAsset(flushWinSound, Music.class);
            music.setLooping(false);
            music.setVolume(0.2f);
            Music bgMusic = ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class);
            bgMusic.setVolume(0.3f);
            bgMusic.play();
        }
        music.setVolume(0.3f);
        music.play();
    }

    @Override
    public void render(float delta) {
        ServiceLocator.getEntityService().update();
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
        logger.trace("Resized renderer: ({} x {})", width, height);
    }

    @Override
    public void pause() {
        logger.info("Game paused");
    }

    @Override
    public void resume() {
        logger.info("Game resumed");
    }

    @Override
    public void dispose() {
        logger.debug("Disposing death screen");

        renderer.dispose();
        unloadAssets();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();
        ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).stop();

        ServiceLocator.clear();
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(deathTextures);
        resourceService.loadTextures(deathBtnTextures);
        resourceService.loadMusic(deathMusic);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(deathTextures);
        resourceService.unloadAssets(deathBtnTextures);
        resourceService.unloadAssets(deathMusic);
    }

    /**
     * Creates the death screens's ui including components for rendering ui elements to the screen and
     * capturing and handling ui input.
     */
    private void createUI(int level) {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new DeathScreenDisplay(level))
                .addComponent(new InputDecorator(stage, 10))
                .addComponent(new DeathScreenActions(game));

        ServiceLocator.getEntityService().register(ui);
    }
}
