package TetrisGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class StartScreen implements Screen, InputProcessor, GestureDetector.GestureListener {

    private Drawer drawer;

    TextButton titleBtn;
    TextButton txtInstructions;
    TextButton newGameTxt;
    TextButton txtInstruction1;
    SpriteBatch batch;
    TextButton highScoreTxt;
    TetrisGame game;
    ArrayList<TextButton> texts = new ArrayList<TextButton>();
    int textIndent = 30;

    public StartScreen(TetrisGame game) {
        drawer = new Drawer();
        batch = new SpriteBatch();
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        newGameTxt = new TextButton("newGameTxt", "New Game", 100, 0, 500);
        //highScore = new TextButton("highScore", "Tetris", 400, width / 2, 1500);
        txtInstructions = new TextButton("txtInstructions", "Instructions", 100, 0, 1200);
        texts.add(txtInstructions);
        String newline = System.getProperty("line.separator");
        texts.add(new TextButton("instruct1", "1. Swipe left or right to move piece left or right." + newline
                + "2. Swipe down to move piece to bottom." + newline
                + "3. Tap to rotate piece." + newline
                + "4. Score points by filling rows completely with blocks.", 80, textIndent, 1100));
        //texts.add(new TextButton("instruct1", , 100, textIndent, 800));
        //texts.add(new TextButton("instruct1", "3. Tap to rotate piece.", 100, textIndent, 400));
        //texts.add(new TextButton("instruct1", "4. Score points by filling rows completely with blocks.", 100, textIndent, 1100));
        //texts.add(txtInstruction1);
        //int highScore = game.getHighScore();
        //highScoreTxt = new TextButton("highScoreTxt", "High Score: " + highScore, 400, width / 2, 800);

        this.game = game;
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

        int titleSize = 300;
        int titleHeight = Gdx.graphics.getHeight() - 300;
        int titleIndent = 70;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/ARCADE.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = titleSize;
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixel

        drawer.DrawBackground();
        batch.begin();

        font.setColor(Color.RED);
        font.draw(batch, "T", titleIndent, titleHeight);
        font.setColor(Color.ORANGE);
        font.draw(batch, "E", titleIndent + font.getBounds("T").width, titleHeight);
        font.setColor(Color.YELLOW);
        font.draw(batch, "T", titleIndent + font.getBounds("TE").width, titleHeight);
        font.setColor(Color.GREEN);
        font.draw(batch, "R", titleIndent + font.getBounds("TET").width, titleHeight);
        font.setColor(Color.BLUE);
        font.draw(batch, "I", titleIndent + font.getBounds("TETR").width, titleHeight);
        font.setColor(Color.PURPLE);
        font.draw(batch, "S", titleIndent + font.getBounds("TETRI").width, titleHeight);

        for(TextButton b : texts)
        {
            b.Draw(batch, Gdx.graphics.getWidth()-textIndent);
        }
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
        if (newGameTxt.IsPressed(Math.round(x), Math.round(y))) {
            game.gotoGameScreen();
            return true;
        }

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
