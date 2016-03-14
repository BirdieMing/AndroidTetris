package TetrisGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Preferences;

/**
 * Created by panda on 7/30/2015.
 */
public class TetrisGame extends Game {

    private ImageProvider imageProvider;
    public Preferences prefs;

    @Override
    public void create() {
        imageProvider = new ImageProvider();
        gotoGameScreen();
    }

    public ImageProvider getImageProvider() {
        return this.imageProvider;
    }

    public void gotoStartScreen() {
        //setScreen(new StartScreen(this));
    }

    public void gotoGameScreen() {
        setScreen(new GameScreen(this));
    }

/*    public static int getHighScore() {
        Preferences prefs = Gdx.app.getPreferences("highScore");

        if (!prefs.contains("highScore"))
            prefs.putInteger("highScore", 0);

        return prefs.getInteger("highScore");
    }

    public static void setHighScore(int value) {
        Preferences prefs = Gdx.app.getPreferences("highScore");

        prefs.putInteger("highScore", value);
        prefs.flush();
    }*/
}
