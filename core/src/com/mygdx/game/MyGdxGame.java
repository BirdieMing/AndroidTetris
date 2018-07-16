package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

//Game State Pause Screens

public class MyGdxGame extends ApplicationAdapter implements GestureDetector.GestureListener {
    GameState state;
    SpriteBatch batch;
    int screenWidth;
    int screenHeight;
    TetrisModel model;
    int wBlocks;
    int hBlocks;
    long nextTickTime;
    Boolean[][] currentGrid;
    Color[][] colorGrid;
    int intervalInSeconds;
    boolean isUpdating;
    int blockUnit;
    Piece currentPiece;
    String message;
    int gridWidth;
    int gridHeight;
    ShapeRenderer sr;
    BitmapFont font;
    Music music;
    int gridStartH;
    int gridStartW;
    SpriteBatch fontBatch;
    //BotPlayer bot;

    TextureRegion background;
    int speed = 100;
    int rotation;
    Matrix4 mx4Font = new Matrix4();

    @Override
    public void create() {
        state = GameState.STOPPED;
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        message = "";

        screenWidth = Gdx.graphics.getHeight();
        screenHeight = Gdx.graphics.getWidth();

        wBlocks = 10;
        hBlocks = 20;

        model = new TetrisModel(wBlocks, hBlocks, this);
        currentGrid = this.model.GetGrid();
        colorGrid = this.model.GetColor();
        currentPiece = this.model.currentPiece;

        gridStartH = 250;
        gridStartW = 50;
        gridWidth = 7 * screenWidth / 10;
        blockUnit = gridWidth / wBlocks;
        gridHeight = blockUnit * hBlocks;

        background = new TextureRegion(new Texture(Gdx.files.internal("data/background2.jpg")));

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setColor(Color.RED);
        font.setScale(1);

        music = Gdx.audio.newMusic(Gdx.files.internal("data/tune.MID"));
        music.setLooping(true);
        music.play();

        GestureDetector gd = new GestureDetector(this);
        Gdx.input.setInputProcessor(gd);

        intervalInSeconds = 1;
        nextTickTime = com.badlogic.gdx.utils.TimeUtils.millis() + 7000;
        isUpdating = false;
        rotation= 0;

        fontBatch = new SpriteBatch();
        //bot = new BotPlayer(this, model);


    }

    @Override
    public void render() {

        long now = com.badlogic.gdx.utils.TimeUtils.millis();

        if (nextTickTime != 0 && nextTickTime <= now) {
            if (!isUpdating) {
                isUpdating = true;
                //this.model.Tick();
                //this.bot.MakeNextMove();
                nextTickTime = now + (intervalInSeconds * 1000);
                isUpdating = false;
            }
        }
        if (!message.isEmpty()) {
            Gdx.app.debug("Message: ", message);
            message = "";
        }

        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, (this.screenHeight - this.screenWidth) / 2, (this.screenWidth - this.screenHeight) / 2, this.screenWidth / 2, this.screenHeight / 2, this.screenWidth, this.screenHeight, (float) 1, (float) 1, 90);
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        drawGameGrid();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        //rotation ++;

        //font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        //mx4Font.setToRotation(new Vector3(1, 1, 0), rotation % 360).translate(rotation, rotation, 0);
        //fontBatch.setTransformMatrix(mx4Font);

        //fontBatch.begin();
        //font.draw(fontBatch, "Testing 123.", 0, 0);
        //fontBatch.end();
    }
    
    private void drawGameGrid()
    {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(181 / 255f, 173 / 255f, 153 / 255f, 0.5f));
        sr.rect(gridStartH, gridStartW, this.gridHeight, this.gridWidth);
        sr.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);

        if (currentGrid == null)
            return;

        try {
            for (int w = 0; w < wBlocks; w++) {
                for (int h = 0; h < hBlocks; h++) {

                    int blockStartW = w * blockUnit;
                    int blockStartH = h * blockUnit;

                    if (this.currentGrid[w][h]) {
                        if (this.model.colorMap[w][h] != null)
                            sr.setColor(this.model.colorMap[w][h]);

                        sr.rect(blockStartH + gridStartH, blockStartW + gridStartW, blockUnit-2, blockUnit-2);
                        }
                    }
                }

            drawPiece(this.currentPiece, gridStartW + this.model.currentPiecePosX * blockUnit, gridStartH + this.model.currentPiecePosY * blockUnit);
            drawPiece(this.currentPiece, 8 * this.screenWidth / 10, 50);
        }
        catch (Exception e)
        {
            String exceptionMessage = e.getMessage();
        }

        sr.end();
    }

    private void drawPiece(Piece piece, int wOffSet, int hOffSet)
    {
        for (int w = 0; w < this.currentPiece.size; w++) {
            for (int h = 0; h < this.currentPiece.size; h++) {
                if (this.currentPiece.piece[w][h]) {
                    int wStart = (w) * blockUnit + wOffSet;
                    int hStart = (h) * blockUnit + hOffSet;
                    int maxY = hBlocks * blockUnit;
                    sr.setColor( this.currentPiece.color);
                    sr.rect(hStart, wStart, blockUnit - 2, blockUnit - 2);
                }
            }
        }
    }

    public void UpdateCurrentPiece(Piece currentPiece)
    {
        this.currentPiece = currentPiece;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        message = message + "Tap performed, finger" + Integer.toString(button);
        this.model.RotatePiece();
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        message = message + "Fling performed, velocity:" + Float.toString(velocityX) +
                "," + Float.toString(velocityY);

        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 200)
                this.model.MovePieceToBottom();
            else if (velocityX > 10)
                this.model.MovePieceDown();
        } else {
            if (velocityY > 10) {
                this.model.MovePieceLeft();
            } else {
                this.model.MovePieceRight();
            }
        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        //message = message + "Pan performed, delta:" + Float.toString(deltaX) +
               // "," + Float.toString(deltaY);
        return true;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        //message = "Zoom performed, initial Distance:" + Float.toString(initialDistance) +
                //" Distance: " + Float.toString(distance);
        return true;
    }

    @Override
    public boolean pinch(com.badlogic.gdx.math.Vector2 initialPointer1, com.badlogic.gdx.math.Vector2 initialPointer2,
                         com.badlogic.gdx.math.Vector2 pointer1, com.badlogic.gdx.math.Vector2 pointer2) {
        //message = "Pinch performed";
        return true;
    }

    @Override
    public boolean panStop (float x, float y, int pointer, int button)
    {
        return false;
    }
}
