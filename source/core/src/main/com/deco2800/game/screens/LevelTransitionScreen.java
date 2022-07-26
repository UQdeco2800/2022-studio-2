package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.leveltransition.LevelTransitionActions;
import com.deco2800.game.components.leveltransition.LevelTransitionDisplay;
import com.deco2800.game.components.leveltransition.TransitionInputComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.terminal.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The game screen containing the main menu.
 */
public class LevelTransitionScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LevelTransitionScreen.class);
    private final GdxGame game;
    private final Renderer renderer;
    private static final String BACKGROUND_MUSIC = "sounds/MenuSong-Overcast.mp3";
    private static final String[] transitionMusic = {BACKGROUND_MUSIC};
    private static final String TRANSITION_PREFIX = "images/loadingScreen/loadingscreenAnimation-";
    public static final int FRAME_COUNT = 55;
    private static String[] transitionTextures = new String[FRAME_COUNT];

    /**
     * Public facing function to allow other components to get the string of a transition texture.
     * @param frame The desired frame string to acquire
     * @return      The String of the desired frame
     */
    public static String getTransitionTexture(int frame) { return transitionTextures[frame]; }

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

    /**
     * Play music for the transition screen.
     */
    private void playMusic() {
        Music music = ServiceLocator.getResourceService().getAsset(BACKGROUND_MUSIC, Music.class);
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
        ServiceLocator.getResourceService().getAsset(BACKGROUND_MUSIC, Music.class).stop();

        ServiceLocator.clear();
    }

    /**
     * Load individual animation screens for the level transition.
     */
    public static void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();

        for (int i = 0; i < FRAME_COUNT; i++) {
            transitionTextures[i] = TRANSITION_PREFIX + (i + 1) + ".png";
        }

        resourceService.loadTextures(transitionTextures);
        resourceService.loadMusic(transitionMusic);
        ServiceLocator.getResourceService().loadAll();
    }

    /**
     * Unload all assets.
     */
    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(transitionTextures);
    }

    /**
     * Creates the level transition's ui including components for rendering ui elements to the screen and
     * capturing and handling ui input.
     */
    private void createUI() {
        logger.debug("Creating ui");
        logger.info("UI for transitionScreen created");
        Stage stage = ServiceLocator.getRenderService().getStage();
        InputComponent inputComponent =
                ServiceLocator.getInputService().getInputFactory().createForTerminal();

        Entity ui = new Entity();
        ui.addComponent(new LevelTransitionDisplay())
                .addComponent(new InputDecorator(stage, 10))
                .addComponent(new LevelTransitionActions(game))
                .addComponent(new Terminal())
                .addComponent(inputComponent)
                .addComponent(new TransitionInputComponent());

        ServiceLocator.getEntityService().register(ui);
    }
}

