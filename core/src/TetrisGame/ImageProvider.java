package TetrisGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ImageProvider {

    private ShapeRenderer sr;

    private int SCREEN_WIDTH = 800;

    private int SCREEN_HEIGHT = 480;

    private TextureAtlas atlas;

    private TextureAtlas textAtlas;

    private Texture backgroundSpring;

    private Texture backgroundSummer;

    private Texture backgroundAutumn;

    private Texture backgroundWinter;

    private Texture sign;

    public ImageProvider() {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
    }

    public void load() {
        atlas = new TextureAtlas(Gdx.files.internal("game.atlas"));
        /*if (locale.equals("de")) {
            textAtlas  = new TextureAtlas(Gdx.files.internal("text_images_de.atlas"));
        }
        else {
            textAtlas  = new TextureAtlas(Gdx.files.internal("text_images.atlas"));
        }*/

        backgroundAutumn = new Texture(Gdx.files.internal("autumn.png"));
        backgroundWinter = new Texture(Gdx.files.internal("winter.png"));
        backgroundSpring = new Texture(Gdx.files.internal("spring.png"));
        backgroundSummer = new Texture(Gdx.files.internal("summer.png"));

        sign = new Texture(Gdx.files.internal("sign.png"));
    }

    public void dispose() {
        atlas.dispose();
        textAtlas.dispose();
        backgroundSpring.dispose();
        backgroundSummer.dispose();
        backgroundAutumn.dispose();
        backgroundWinter.dispose();
        sign.dispose();
    }

    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public TextureRegion getButton() {
        return new TextureRegion(new Texture(Gdx.files.internal("data/StartGame.png")));
    }

    public TextureRegion getBackground() {
        return new TextureRegion(new Texture(Gdx.files.internal("data/Background.jpg")));
    }

    public Sprite getBackgroundSprite() {
        return new Sprite(new Texture(Gdx.files.internal("data/Background.jpg")));
    }
}
