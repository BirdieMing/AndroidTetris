package TetrisGame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


import java.util.ArrayList;

//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

public class GameScreen implements Screen, InputProcessor, GestureDetector.GestureListener {

    private TetrisGame game;
    //private TextureRegion region;
    private ImageProvider imageProvider;
    private SpriteBatch batch;
    private final TetrisModel model;
    private Drawer drawer;
    private long nextTickTime;
    private boolean isUpdating;
    private int intervalInSeconds;
    private ArrayList<MyTextButton> textButtons;
    private MyTextButton scoreBtn;
    private MyTextButton pauseBtn;

    //private MediaPlayer player;
    private Music mp3Sound;
    Skin skin;
    TextButton buttonExit;
    Stage stage;
    Table table;
    int exitButtonFont = 150;
    int exitButtonX = Gdx.graphics.getWidth() - 300;
    int exitButtonY = Gdx.graphics.getHeight() - 220;
    int scoreButtonX = 200;
    int scoreButtonY = Gdx.graphics.getHeight() - 220;
    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();

    ShapeRenderer sr;
    OrthographicCamera camera;
    TextButton buttonNewGame;

    public GameScreen(TetrisGame game) {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        this.game = game;
        this.imageProvider = game.getImageProvider();
        //this.region = imageProvider.getBackground();
        this.model = new TetrisModel();
        drawer = new Drawer();
        GestureDetector gd = new GestureDetector(this);
        //Gdx.input.setInputProcessor(gd);



        intervalInSeconds = 1;
        nextTickTime = com.badlogic.gdx.utils.TimeUtils.millis() + 7000;
        isUpdating = false;

        textButtons = new ArrayList<MyTextButton>();

        scoreBtn = new MyTextButton("ScoreButton", "Score: ", 80, scoreButtonX, scoreButtonY);
        textButtons.add(scoreBtn);

        mp3Sound = Gdx.audio.newMusic(Gdx.files.internal("data/tetris.mp3"));
        mp3Sound.setLooping(true);

        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gd);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/ARCADE.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter playButtonParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        playButtonParameter.size = exitButtonFont;
        BitmapFont playButtonFont = generator.generateFont(playButtonParameter); // font size 12 pixel

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/ui-green.atlas"));
        skin = new Skin(atlas);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_04");
        textButtonStyle.over = skin.getDrawable("button_04");
        textButtonStyle.down = skin.getDrawable("button_04");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = playButtonFont;

        buttonExit = new TextButton("Exit", textButtonStyle);
        buttonExit.setPosition(exitButtonX, exitButtonY);;
        buttonExit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            };
        });
        buttonExit.pad(30, 20, 0, 10);
        table.add(buttonExit);

        buttonNewGame = new TextButton("New Game", textButtonStyle);
        buttonNewGame.setPosition(screenWidth / 2 - 300, screenHeight / 2);
        buttonNewGame.pad(30, 20, 0, 10);
        buttonNewGame.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                model.reset();
            };
        });
        table.add(buttonNewGame);



        camera = new OrthographicCamera(1280, 720);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public void show() {

    }

    private void UpdateModel() {
        long now = com.badlogic.gdx.utils.TimeUtils.millis();

        if (this.model.isGameOver)
            return;

        //Model store deleted rows
        //Seconds passing counted
        //After 5 move on
        if (nextTickTime != 0 && nextTickTime <= now) {
            if (!isUpdating) {
                isUpdating = true;
                this.model.Tick();
                nextTickTime = now + (intervalInSeconds * 1000);
                isUpdating = false;

                scoreBtn.SetText("Score: " + String.valueOf(this.model.score));
            }
        }
    }

    @Override
    public void render(float delta) {

        UpdateModel();

        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawer.DrawBackground();
        drawer.DrawGameGrid(model.grid, model.getColorMap());
        drawer.DrawPiece(model.currentPiece);

        batch.begin();

        for (int i = 0; i < textButtons.size(); i++) {
            textButtons.get(i).Draw(batch);
        }

        batch.end();

        if (this.model.isGameOver) {
            drawer.DrawPauseScreen();
            batch.begin();
            buttonNewGame.draw(batch, 1);
            batch.end();
        }

        batch.begin();
        buttonExit.draw(batch, 1);
        batch.end();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        this.model.RotatePiece();
        int yPos = Gdx.graphics.getHeight() - Math.round(y);
        int xPos = Math.round(x);
        //Gdx.app.debug("tap", xPos + " " + yPos);
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        Gdx.app.debug("fling", velocityX + " " + velocityY);
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 10)
                this.model.MovePieceRight();
            else
                this.model.MovePieceLeft();
        } else {
            if (velocityY > 200) {
                this.model.MovePieceToBottom();
            } else if (velocityY > 10) {
                this.model.MovePieceDown();
            }
        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
