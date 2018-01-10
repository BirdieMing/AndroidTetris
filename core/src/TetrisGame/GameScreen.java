package TetrisGame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
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
    private long intervalInSeconds;
    private ArrayList<MyTextButton> textButtons;
    private MyTextButton textScore;
    private MyTextButton textScoreNum;
    private MyTextButton textNextPiece;
    private MyTextButton textLevel;
    private MyTextButton textLevelNum;

    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();

    //private MediaPlayer player;
    private Music mp3Sound;
    Skin skin;
    TextButton buttonExit;
    Stage stage;
    Table table;
    int exitButtonFont = 150;
    int buttonExitX = Gdx.graphics.getWidth() - 300;
    int buttonExitY = Gdx.graphics.getHeight() - 220;
    int textNextPieceX = 300;
    int textNextPieceY = this.screenHeight - 50;
    int textScoreX = 60;
    int textScoreY = this.screenHeight - 50;
    int textScoreNumX = 100;
    int textScoreNumY = this.screenHeight - 130;
    int textLevelX = 60;
    int textLevelY = this.screenHeight - 200;
    int textLevelNumX = 100;
    int textLevelNumY = this.screenHeight - 270;

    ShapeRenderer sr;
    TextButton buttonNewGame;

    public GameScreen(TetrisGame game) {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        this.game = game;
        //this.imageProvider = game.getImageProvider();
        this.model = new TetrisModel();
        drawer = new Drawer();
        GestureDetector gd = new GestureDetector(this);
        //Gdx.input.setInputProcessor(gd);

        intervalInSeconds = 1;
        nextTickTime = com.badlogic.gdx.utils.TimeUtils.millis() + 7000;
        isUpdating = false;

        textButtons = new ArrayList<MyTextButton>();

        textScore = new MyTextButton("Score", "Score", 60, textScoreX, textScoreY);
        textButtons.add(textScore);

        textScoreNum = new MyTextButton("Score", "0", 100, textScoreNumX, textScoreNumY);
        textButtons.add(textScoreNum);

        textLevel = new MyTextButton("Level", "Level", 60, textLevelX, textLevelY);
        textButtons.add(textLevel);

        textLevelNum = new MyTextButton("Level", "1", 100, textLevelNumX, textLevelNumY);
        textButtons.add(textLevelNum);

        textNextPiece = new MyTextButton("TextNextPiece", "Next Piece", 80, textNextPieceX, textNextPieceY);
        textButtons.add(textNextPiece);

        mp3Sound = Gdx.audio.newMusic(Gdx.files.internal("data/tetris.mp3"));
        mp3Sound.setLooping(true);
        //mp3Sound.play();

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
        buttonExit.setPosition(buttonExitX, buttonExitY);;
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
                if (model.isGameOver)
                    model.reset();
            };
        });
        table.add(buttonNewGame);
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
                double intervalInDouble = (double) (1.0 - ((this.model.level - 1) * 0.1));
                long intervalInLong = (long) (intervalInDouble * 1000);
                nextTickTime = now + intervalInLong;
                isUpdating = false;

                textScoreNum.SetText(String.valueOf(this.model.score));
                textLevelNum.SetText(String.valueOf(this.model.level));
            }
        }
    }

    @Override
    public void render(float delta) {

        UpdateModel();

        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawer.DrawBackground();
        drawer.DrawBoxes();
        drawer.DrawGameGrid(model.grid, model.getColorMap());
        drawer.DrawPiece(model.currentPiece);
        if (model.NextPiece() != null)
            drawer.DrawNextPiece(model.NextPiece());

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
            if (Math.abs(velocityX) > 100)
            {
                if (velocityX > 1500) {
                    this.model.MovePieceRight();
                    this.model.MovePieceRight();
                } else if (velocityX > 100)
                {
                    this.model.MovePieceRight();
                } else if (velocityX < 1500) {
                    this.model.MovePieceLeft();
                    this.model.MovePieceLeft();
                } else if (velocityX < 100)
                    this.model.MovePieceLeft();
            }

        } else {
            if (velocityY > 3000) {
                this.model.MovePieceToBottom();
            } else if (velocityY > 2000) {
                this.model.MovePieceDown();
                this.model.MovePieceDown();
                this.model.MovePieceDown();
            } else if (velocityY > 1000)
            {
                this.model.MovePieceDown();
                this.model.MovePieceDown();
            } else if (velocityY > 100)
            {
                this.model.MovePieceDown();
            }
        }
        Gdx.app.log("velocity", String.valueOf(velocityX) + " " + String.valueOf(velocityY));
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
