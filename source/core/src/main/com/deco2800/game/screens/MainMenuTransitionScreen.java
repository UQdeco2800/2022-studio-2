package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;

import com.deco2800.game.components.levelTransition.TransitionInputComponent;
import com.deco2800.game.components.mainmenu.MainMenuActions;
import com.deco2800.game.components.mainmenu.MainMenuTransitionAnimation;
import com.deco2800.game.components.mainmenu.MainMenuTransitionComponent;
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

public class MainMenuTransitionScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MainMenuTransitionScreen.class);
    private final GdxGame game;
    private final Renderer renderer;
    private static final String animationPrefix = "images/Crafting-assets-sprint1/screens/main menu animation/atlantis sinks animation";
    public static final int frameCount = 85;
    public static String[] transitionTextures = new String[frameCount];

    public MainMenuTransitionScreen(GdxGame game) {
        this.game = game;
        logger.debug("Initialising main menu transition screen animation");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();

        loadFrames();
        createUI();
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
        renderer.dispose();
        unloadFrames();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();

        ServiceLocator.clear();
    }

    private void loadFrames() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();

        for (int i = 0; i < frameCount; i++) {
            transitionTextures[i] = animationPrefix + i + ".png";
        }
        resourceService.loadTextures(transitionTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadFrames(){
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(transitionTextures);
    }

    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        InputComponent inputComponent =
                ServiceLocator.getInputService().getInputFactory().createForTerminal();

        Entity ui = new Entity();
        ui.addComponent(new MainMenuTransitionAnimation())
                .addComponent(new InputDecorator(stage, 10))
                .addComponent(new MainMenuActions(game))
                .addComponent(new Terminal())
                .addComponent(inputComponent)
                .addComponent(new MainMenuTransitionComponent());

        ServiceLocator.getEntityService().register(ui);
    }
}
