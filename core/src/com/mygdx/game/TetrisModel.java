package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TetrisModel {

    public int width;
    public int height;
    public int currentPiecePosX;
    public int currentPiecePosY;
    Queue pieces;
    MyGdxGame game;
    public Piece currentPiece;
    Boolean[][] grid;
    Color[][] colorMap;
    int linesDeleted;

    public TetrisModel(int width, int height, MyGdxGame game) {
        this.game = game;
        this.width = width;
        this.height = height;
        pieces = new LinkedList<Piece>();
        InitializeGrid();
        linesDeleted = 0;
        CycleNewPiece();
    }

    private void FillQueue() {
        for (int x = 0; x < 11; x++) {
            pieces.add(new Piece());
        }
    }

    public Boolean[][] GetGrid() {
        return this.grid;
    }

    public Color[][] GetColor() {
        return this.colorMap;
    }

    public void Tick() {
        MovePieceDown();
    }

    private void InitializeGrid() {
        grid = new Boolean[width][height];
        colorMap = new Color[width][height];
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                grid[w][h] = false;
                colorMap[w][h] = null;
            }
        }

        game.currentGrid = this.grid;
        game.colorGrid = this.colorMap;
    }

    public boolean IsClearLoc(int x, int y) {
        if (x < 0 || x > width - 1)
            return false;
        if (y < 0 || y > height - 1)
            return false;
        if (grid[x][y])
            return false;
        return true;
    }

    public boolean IsPieceAtValidLocation(int posX, int posY, Boolean[][] piece, int size) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (piece[x][y]) {
                    int xGrid = posX + x;
                    int yGrid = posY + y;

                    if (!IsClearLoc(xGrid, yGrid))
                        return false;
                }
            }
        }

        return true;
    }

    public void MovePieceLeft() {
        if (IsPieceAtValidLocation(currentPiecePosX - 1, currentPiecePosY, currentPiece.piece, currentPiece.size))
            currentPiecePosX--;
    }

    public void MovePieceRight() {
        if (IsPieceAtValidLocation(currentPiecePosX + 1, currentPiecePosY, currentPiece.piece, currentPiece.size))
            currentPiecePosX++;
    }

    public void MovePieceToBottom() {
        while (MovePieceDown())
        {
            //nothing
        }
    }

    public boolean MovePieceDown() {
        boolean isPieceAtValidLoc = IsPieceAtValidLocation(currentPiecePosX, currentPiecePosY + 1, currentPiece.piece, currentPiece.size);

        if (isPieceAtValidLoc) {
            currentPiecePosY++;
        } else {
            PutPieceInPlace();
        }

        return isPieceAtValidLoc;
    }

    public void PutPieceInPlace() {
        for (int x = 0; x < currentPiece.size; x++) {
            for (int y = 0; y < currentPiece.size; y++) {
                int gridX = currentPiecePosX + x;
                int gridY = currentPiecePosY + y;

                if (IsClearLoc(gridX, gridY) && this.currentPiece.piece[x][y]) {
                    this.grid[gridX][gridY] = true;
                    this.colorMap[gridX][gridY] = currentPiece.color;
                    Gdx.app.debug("PutPieceInPlace: Color:", Float.toString(this.colorMap[gridX][gridY].b));
                }
            }
        }
        DeleteFilledRows();
        CycleNewPiece();
    }

    private void DeleteFilledRows()
    {
        int[] offsetMap = new int[this.height];

        int offset = 0;
        ArrayList<Integer> tempRowsDelete = new ArrayList<Integer>();
        for (int h = this.height -1; h > 0; h--) {
            int sum = 0;
            for (int x = 0; x < this.width; x++) {
                if (grid[x][h])
                    sum++;
            }
            if (sum == this.width) {
                tempRowsDelete.add(h);
                linesDeleted++;
                offset++;
            }
            else
            {
                if (tempRowsDelete.size() > 0) {
                    for (int i = 0; i < tempRowsDelete.size(); i++) {
                        offsetMap[tempRowsDelete.get(i)] = offset;
                    }
                    offset++;
                }

                tempRowsDelete.clear();
                offsetMap[h] = offset;
            }
        }

        for (int h = this.height - 1; h > 0; h--) {
            int o = offsetMap[h];
            int newH = h - o;
            for (int w = 0; w < this.width; w++) {
                {
                    if (newH < 0)
                        this.grid[w][h] = false;
                    else
                        this.grid[w][h] = this.grid[w][newH].booleanValue();
                }
            }
        }
    }

    private void CycleNewPiece() {
        if ((Piece) pieces.poll() == null || pieces.size() < 10 )
            FillQueue();

        this.currentPiece = (Piece) pieces.poll();
        currentPiecePosX = (width - this.currentPiece.size) / 2;;
        currentPiecePosY = 0;

        if (this.grid[currentPiecePosX][currentPiecePosY] == true)
            this.InitializeGrid();

        this.game.UpdateCurrentPiece(this.currentPiece);


    }

    public void RotatePiece()
    {
        if (IsPieceAtValidLocation(currentPiecePosX, currentPiecePosY, this.currentPiece.GetAfterRotateRight(), this.currentPiece.size))
            this.currentPiece.Rotate();
        this.game.UpdateCurrentPiece(this.currentPiece);
    }
}
