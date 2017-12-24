package TetrisGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class StartScreen implements Screen, InputProcessor, GestureDetector.GestureListener {

    private Drawer drawer;

    MyTextButton titleBtn;
    MyTextButton txtInstructions;
    MyTextButton txtInstruction1;
    SpriteBatch batch;
    MyTextButton highScoreTxt;
    final TetrisGame myGame;
    ArrayList<MyTextButton> texts = new ArrayList<MyTextButton>();
    int textIndent = 30;
    Skin skin;
    com.badlogic.gdx.scenes.scene2d.ui.TextButton buttonPlay;
    BitmapFont titleFont;
    int titleSize = 300;
    int titleHeight = Gdx.graphics.getHeight() - 300;
    int titleIndent = 70;
    int playButtonX = Gdx.graphics.getWidth() / 2 - 150;
    int playButtonFont = 150;
    Stage stage;
    Table table;

    public StartScreen(final TetrisGame game) {
        this.myGame = game;
        drawer = new Drawer();
        batch = new SpriteBatch();
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        GestureDetector gd = new GestureDetector(this);
        Gdx.input.setInputProcessor(gd);

        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/ARCADE.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = titleSize;
        titleFont = generator.generateFont(parameter); // font size 12 pixel

        FreeTypeFontGenerator.FreeTypeFontParameter playButtonParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        playButtonParameter.size = playButtonFont;
        BitmapFont playButtonFont = generator.generateFont(playButtonParameter); // font size 12 pixel

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/ui-green.atlas"));
        skin = new Skin(atlas);

        com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle textButtonStyle = new com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_04");
        textButtonStyle.over = skin.getDrawable("button_04");
        textButtonStyle.down = skin.getDrawable("button_04");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = playButtonFont;


        buttonPlay = new com.badlogic.gdx.scenes.scene2d.ui.TextButton("Play", textButtonStyle);
        buttonPlay.setPosition(playButtonX,300);

        //buttonExit.pad(50);
        buttonPlay.pad(30, 20, 0, 10);

        buttonPlay.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("test", "test");
                myGame.gotoGameScreen();
            };
        });
        table.add(buttonPlay);

        //highScore = new TextButton("highScore", "Tetris", 400, width / 2, 1500);
        txtInstructions = new MyTextButton("txtInstructions", "Instructions", 100, 0, 1200);
        texts.add(txtInstructions);

        String newline = System.getProperty("line.separator");
        texts.add(new MyTextButton("instruct1", "1. Swipe left or right to move piece left or right." + newline
                + "2. Swipe down to move piece to bottom." + newline
                + "3. Tap to rotate piece." + newline
                + "4. Score points by filling rows completely with blocks.", 80, textIndent, 1100));

        //int highScore = game.getHighScore();
        //highScoreTxt = new TextButton("highScoreTxt", "High Score: " + highScore, 400, width / 2, 800);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawer.DrawBackground();
        batch.begin();

        //Draw title
        titleFont.setColor(Color.BLUE);
        titleFont.draw(batch, "T", titleIndent, titleHeight);
        titleFont.setColor(Color.ORANGE);
        titleFont.draw(batch, "E", titleIndent + titleFont.getBounds("T").width, titleHeight);
        titleFont.setColor(Color.YELLOW);
        titleFont.draw(batch, "T", titleIndent + titleFont.getBounds("TE").width, titleHeight);
        titleFont.setColor(Color.GREEN);
        titleFont.draw(batch, "R", titleIndent + titleFont.getBounds("TET").width, titleHeight);
        titleFont.setColor(Color.RED);
        titleFont.draw(batch, "I", titleIndent + titleFont.getBounds("TETR").width, titleHeight);
        titleFont.setColor(Color.PURPLE);
        titleFont.draw(batch, "S", titleIndent + titleFont.getBounds("TETRI").width, titleHeight);

        for(MyTextButton b : texts)
        {
            b.Draw(batch, Gdx.graphics.getWidth()-textIndent);
        }
        buttonPlay.draw(batch, 1);
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

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
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
