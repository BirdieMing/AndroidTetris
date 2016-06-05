package TetrisGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class StartScreen implements Screen, InputProcessor, GestureDetector.GestureListener {

    private Drawer drawer;

    TextButton titleBtn;
    TextButton byTxt;
    TextButton newGameTxt;
    SpriteBatch batch;
    TextButton highScoreTxt;
    TetrisGame game;

    public StartScreen(TetrisGame game) {
        drawer = new Drawer();
        batch = new SpriteBatch();
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        //highScore = new TextButton("highScore", "Tetris", 400, width / 2, 1500);
        byTxt = new TextButton("byTxt", "by Ming Luo 2015", width / 2, 0, 1200);
        newGameTxt = new TextButton("newGameTxt", "New Game", 400, width / 2, 500);
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

        drawer.DrawBackground();
        batch.begin();
        highScoreTxt.Draw(batch);
        byTxt.Draw(batch);
        newGameTxt.Draw(batch);
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
