package com.deco2800.game.components;

import com.badlogic.gdx.audio.Music;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;

/**
 * Reusable sound component to create background music
 * Loads, plays and unloads music automatically.
 * Note: Maps files to the Music class and not the Sound class.
 */
public class BackgroundSoundComponent extends Component {

    private String backgroundMusic;
    private final float volume;
    private final boolean looping;

    /**
     * @param backgroundMusic set the background music
     * @param volume set the volume
     * @param looping set the looping to true or false
     *
     * <p>Example:
     * <pre>
     * {@code
     *   addComponent(new BackgroundSoundComponent("sounds/MenuSong-Overcast.mp3", 0.5f, true));
     * }</pre>
     */
    public BackgroundSoundComponent(String backgroundMusic, float volume, boolean looping) {
        this.backgroundMusic = backgroundMusic;
        this.volume = volume;
        this.looping = looping;
        loadSound();
    }

    /**
     * Load the background music
     */
    private void loadSound() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadMusic(new String[]{backgroundMusic});
        resourceService.loadAll();
    }

    @Override
    public void create() {
        super.create();
        playSound();

        entity.getEvents().addListener("changeSong", (String newMusic) -> {
            stopSound();
            unloadSound();
            backgroundMusic = newMusic;
            loadSound();
            playSound();
        });
    }

    /**
     * Play the background music
     */
    public void playSound() {
        Music music = ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class);
        music.setLooping(looping);
        music.setVolume(volume);
        music.play();
    }

    @Override
    public void dispose() {
        stopSound();

        unloadSound();
        super.dispose();
    }

    /**
     * Stop playing the music
     */
    public void stopSound() {
        ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).stop();
    }

    /**
     * Dispose background music
     */
    private void unloadSound() {
        ServiceLocator.getResourceService().unloadAssets(new String[]{backgroundMusic});
    }

}
