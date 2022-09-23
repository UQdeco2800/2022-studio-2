package com.deco2800.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.deathscreen.DeathScreenActions;
import com.deco2800.game.components.deathscreen.DeathScreenDisplay;
import com.deco2800.game.components.levelTransition.LevelTransitionDisplay;
import com.deco2800.game.components.npc.DialogueDisplay;
import com.deco2800.game.components.BackgroundSoundComponent;
import com.deco2800.game.components.mainmenu.MainMenuActions;
import com.deco2800.game.components.mainmenu.MainMenuDisplay;
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
 * The game screen containing the main menu.
 */
// TODO listen for the plug and cue the screen transition, waiting on team 5.
public class LevelTransitionScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LevelTransitionScreen.class);
    private final GdxGame game;
    private final Renderer renderer;
    // TODO Swap out deathscreen with loading texture when its complete.
    private static final String[] transitionTextures = {"images/DeathScreens/lvl_2.png"};
    private static final String backgroundMusic = "sounds/MenuSong-Overcast.mp3";
    private static final String[] transitionMusic = {backgroundMusic};

    /**
     * Level Transition constructor
     * @param game active state of the game
     */
    public LevelTransitionScreen(GdxGame game) {
        this.game = game;

        logger.debug("Initialising transition screen services");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        renderer = RenderFactory.createRenderer();

        loadAssets();
        createUI();
        playMusic();
    }

    private void playMusic() {
        Music music = ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class);
        music.setLooping(true);
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
    public void dispose() {
        logger.debug("Disposing transition screen");

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
        resourceService.loadTextures(transitionTextures);
        resourceService.loadMusic(transitionMusic);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(transitionTextures);
    }

    /**
     * Creates the level tranistion's ui including components for rendering ui elements to the screen and
     * capturing and handling ui input.
     */
    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new LevelTransitionDisplay())
                .addComponent(new InputDecorator(stage, 10));


        ServiceLocator.getEntityService().register(ui);
    }
}

