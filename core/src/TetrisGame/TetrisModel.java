package TetrisGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TetrisModel {

    public int width;
    public int height;
    public Queue pieces;
    public Piece currentPiece;
    public Boolean[][] grid;
    public Color[][] colorMap;
    public int score;
    public ArrayList<Integer> linesDeleting = new ArrayList<Integer>();
    public int cleaningStep;
    public boolean isGameOver;
    public Piece nextPiece;
    public int level;
    public Map<Integer, Integer> levelScoreMap;

    public TetrisModel() {

        levelScoreMap = new HashMap<Integer, Integer>();
        levelScoreMap.put(1, 20);
        levelScoreMap.put(2, 40);
        levelScoreMap.put(3, 60);
        levelScoreMap.put(4, 80);
        levelScoreMap.put(5, 100);
        levelScoreMap.put(6, 120);
        levelScoreMap.put(7, 140);
        levelScoreMap.put(8, 160);
        levelScoreMap.put(9, 180);

        this.width = Constants.gridWidth;
        this.height = Constants.gridHeight;
        reset();
        isGameOver = false;
    }

    public Piece NextPiece()
    {
        return nextPiece;
    }

    private void DeleteLineBlocks()
    {
        if (linesDeleting.size() == 0)
            return;

        if (linesDeleting.size() == 4)
            score = score + 8;
        else
            score = score + linesDeleting.size();

        Boolean[][] newGrid = new Boolean[width][height];
        Color[][] newColor = new Color[width][height];

        int copyRow = 0;

        for (int h = 0; h < height; h++) {
            while (linesDeleting.contains(copyRow)) {
                if (copyRow >= height-1)
                    break;
                copyRow++;
            }

            for (int w = 0; w < width; w++) {
                if (copyRow >= height-1) {
                    newGrid[w][h] = false;
                    newColor[w][h] = Color.CLEAR;
                } else {
                    newGrid[w][h] = grid[w][copyRow];
                    newColor[w][h] = colorMap[w][copyRow];
                }
            }

            copyRow++;
        }

        colorMap = newColor;
        grid = newGrid;

        linesDeleting.clear();
    }

    public Color[][] getColorMap() {
        //Gdx.app.log("clean", Integer.toString(linesDeleting.size()));

        if (linesDeleting.size() == 0)
            return colorMap;
        else {

            Color[][] colorMapDisplay = colorMap;

            for (int l : linesDeleting)
                for (int p = 0; p < width; p++)
                    colorMap[p][l] = Color.CLEAR;

            return colorMapDisplay;
        }
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

    private void Clean()
    {
        DeleteLineBlocks();
    }

    public void Tick() {

        if (this.isGameOver)
            return;

        if (linesDeleting.size() > 0)
            Clean();
        else
        {
            MovePieceDown();
        }

        if (levelScoreMap.containsKey(this.level))
            if (this.score > levelScoreMap.get(this.level))
                this.level++;
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
        if (isCleaning())
            return;

        if (IsPieceAtValidLocation(currentPiece.positionX - 1, currentPiece.positionY, currentPiece.piece, currentPiece.size))
            currentPiece.positionX--;
    }

    public void MovePieceRight() {
        if (isCleaning())
            return;

        if (IsPieceAtValidLocation(currentPiece.positionX + 1, currentPiece.positionY, currentPiece.piece, currentPiece.size))
            currentPiece.positionX++;
    }

    public void MovePieceToBottom() {
        if (isCleaning())
            return;

        while (MovePieceDown()) {
            //nothing
        }
    }

    public boolean MovePieceDown() {
        boolean isPieceAtValidLoc = IsPieceAtValidLocation(currentPiece.positionX, currentPiece.positionY - 1, currentPiece.piece, currentPiece.size);

        if (isPieceAtValidLoc) {
            currentPiece.positionY--;
        } else {
            PutPieceInPlace();
        }

        return isPieceAtValidLoc;
    }

    public void PutPieceInPlace() {
        Gdx.app.debug("PutPieceInPlace: Color:", "");
        for (int x = 0; x < currentPiece.size; x++) {
            for (int y = 0; y < currentPiece.size; y++) {
                int gridX = currentPiece.positionX + x;
                int gridY = currentPiece.positionY + y;

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

    private void DeleteFilledRows() {
        ArrayList<Integer> tempRowsDelete = new ArrayList<Integer>();
        for (int h = 0; h < this.height - 1; h++) {
            int sum = 0;
            for (int x = 0; x < this.width; x++) {
                if (grid[x][h] != null && grid[x][h].booleanValue())
                    sum++;
            }
            if (sum == this.width) {
                tempRowsDelete.add(h);
            }
        }

        if (tempRowsDelete.size() > 0)
            linesDeleting.addAll(tempRowsDelete);
    }

    private void CycleNewPiece() {
        if ((Piece) pieces.peek() == null || pieces.size() < 10)
            FillQueue();

        this.currentPiece = (Piece) pieces.poll();
        this.currentPiece.positionX = (width - this.currentPiece.size) / 2;
        this.currentPiece.positionY = 16;
        this.nextPiece = (Piece) pieces.peek();

        if (!IsPieceAtValidLocation(currentPiece.positionX, currentPiece.positionY, currentPiece.piece, currentPiece.size)) {
            //reset();
            isGameOver = true;
        }
        //Game Over
    }

    public void RotatePiece() {
        if (isCleaning() || isGameOver)
            return;

        if (IsPieceAtValidLocation(currentPiece.positionX, currentPiece.positionY, this.currentPiece.GetAfterRotateRight(), this.currentPiece.size))
            this.currentPiece.Rotate();
    }

    public void reset() {
        InitializeGrid();
        DeleteLineBlocks();
        pieces = new LinkedList<Piece>();
        CycleNewPiece();
        this.score = 0;
        this.level = 1;
        isGameOver = false;
    }

    private boolean isCleaning()
    {
        return linesDeleting.size() > 0;
    }

}
