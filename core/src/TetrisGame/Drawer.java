package TetrisGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


/**
 * Created by panda on 8/2/2015.
 */
public class Drawer {

    private Stage stage;
    private Image background;
    private ShapeRenderer sr;
    public int gridStartH;
    public int gridStartW;
    private int wBlocks = 10;
    private int hBlocks = 20;

    private int screenWidth = Gdx.graphics.getWidth();
    private int screenHeight = Gdx.graphics.getHeight();
    private int gridWidth = 7 * screenWidth / 10;
    private int blockUnit = gridWidth / wBlocks;
    private int gridHeight = blockUnit * hBlocks;
    private SpriteBatch batch;
    BitmapFont font;

    public Drawer() {
        stage = new Stage();
        //stage.addActor(background);
        sr = new ShapeRenderer();

        gridStartH = 50;
        gridStartW = (screenWidth - gridWidth) / 2;
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Constants.fontSize;
        font = generator.generateFont(parameter); // font size 12 pixels

        generator.dispose();
    }

    public void DrawBackground() {

        sr.begin(ShapeRenderer.ShapeType.Filled);

        sr.rect(0, 0, screenWidth, screenHeight, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE);
        //stage.draw();
        sr.end();
    }

    public void DrawPauseScreen()
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        //sr.setColor(Color.GRAY);
        sr.setColor(0, 0, 0, 0.8f);
        sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sr.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


    public void DrawGameGrid(Boolean[][] currentGrid, Color[][] colorMap) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);
        sr.rect(gridStartW, gridStartH, this.gridWidth, this.gridHeight);
        sr.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);

        if (currentGrid == null)
            return;

        try {
            for (int w = 0; w < wBlocks; w++) {
                for (int h = 0; h < hBlocks; h++) {

                    int blockStartW = w * blockUnit;
                    int blockStartH = h * blockUnit;

                    if (currentGrid[w][h]) {
                        if (colorMap[w][h] != null)
                            sr.setColor(colorMap[w][h]);

                        sr.rect(blockStartW + gridStartW, blockStartH + gridStartH, blockUnit - 2, blockUnit - 2);
                    }
                }
            }
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
        }

        sr.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void DrawNextPiece(Piece nextPiece)
    {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        int wOffSet = Gdx.graphics.getWidth() / 2 - 150;
        int hOffSet = Gdx.graphics.getHeight() - 300;

        for (int w = 0; w < nextPiece.size; w++) {
            for (int h = 0; h < nextPiece.size; h++) {
                if (nextPiece.piece[w][h]) {
                    int wStart = (w) * blockUnit + wOffSet;
                    int hStart = (h) * blockUnit + hOffSet;
                    sr.setColor(nextPiece.color);
                    sr.rect(wStart, hStart, blockUnit - 2, blockUnit - 2);
                }
            }
        }
        sr.end();
    }

    public void DrawPiece(Piece piece) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        int wOffSet = piece.positionX * blockUnit + gridStartW;
        int hOffSet = piece.positionY * blockUnit + gridStartH;

        for (int w = 0; w < piece.size; w++) {
            for (int h = 0; h < piece.size; h++) {
                if (piece.piece[w][h]) {
                    int wStart = (w) * blockUnit + wOffSet;
                    int hStart = (h) * blockUnit + hOffSet;
                    int maxY = hBlocks * blockUnit;
                    sr.setColor(piece.color);
                    sr.rect(wStart, hStart, blockUnit - 2, blockUnit - 2);
                }
            }
        }
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
