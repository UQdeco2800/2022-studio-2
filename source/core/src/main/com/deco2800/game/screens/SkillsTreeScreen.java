package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.terminal.Terminal;
import com.deco2800.game.ui.terminal.TerminalDisplay;
import com.deco2800.game.components.gamearea.PerformanceDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The game screen containing the skills tree.
 */
public class SkillsTreeScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SkillsTreeScreen.class);
    private final GdxGame game;
    private final Renderer renderer;
    private final PhysicsEngine physicsEngine;

    private static final String[] dashImg = {"images/Skills/dash.png"};
    private static final String[] blockImg = {"images/Skills/block.png"};
    private static final String[] dodgeImg = {"images/Skills/dodge.png"};
    private static final String[] invulnerabilityImg = {"images/Skills/invulnerability.png"};

    public SkillsTreeScreen(GdxGame game) {
        this.game = game;

        logger.debug("Initialising skills tree screen services");
        ServiceLocator.registerTimeSource(new GameTime());

        PhysicsService physicsService = new PhysicsService();
        ServiceLocator.registerPhysicsService(physicsService);
        physicsEngine = physicsService.getPhysics();

        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());

        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        renderer = RenderFactory.createRenderer();
        renderer.getDebug().renderPhysicsWorld(physicsEngine.getWorld());

        loadAssets();
        createUI();

    }

    @Override
    public void render(float delta) {
        physicsEngine.update();
        ServiceLocator.getEntityService().update();
        renderer.render();
    }

    @Override
    public void dispose() {
        logger.debug("Disposing main game screen");

        renderer.dispose();

        ServiceLocator.getEntityService().dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getResourceService().dispose();

        ServiceLocator.clear();
    }

    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        InputComponent inputComponent =
                ServiceLocator.getInputService().getInputFactory().createForTerminal();

        Entity ui = new Entity();
        ui.addComponent(new InputDecorator(stage, 10))
                .addComponent(new PerformanceDisplay())
                .addComponent(new com.deco2800.game.SkillsTree.SkillsTreeActions(this.game))
                .addComponent(new com.deco2800.game.SkillsTree.SkillsTreeDisplay())
                .addComponent(new Terminal())
                .addComponent(inputComponent)
                .addComponent(new TerminalDisplay());

        ServiceLocator.getEntityService().register(ui);
    }

    private void loadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(dashImg);
        resourceService.loadTextures(blockImg);
        resourceService.loadTextures(dodgeImg);
        resourceService.loadTextures(invulnerabilityImg);
        ServiceLocator.getResourceService().loadAll();
    }

}
