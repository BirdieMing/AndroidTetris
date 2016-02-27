package TetrisGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TetrisModel {

    public int width;
    public int height;
    public Queue pieces;
    public Piece currentPiece;
    public Boolean[][] grid;
    public Color[][] colorMap;
    public int score;

    public TetrisModel() {
        this.width = Constants.gridWidth;
        this.height = Constants.gridHeight;
        reset();
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
        if (IsPieceAtValidLocation(currentPiece.positionX - 1, currentPiece.positionY, currentPiece.piece, currentPiece.size))
            currentPiece.positionX--;
    }

    public void MovePieceRight() {
        if (IsPieceAtValidLocation(currentPiece.positionX + 1, currentPiece.positionY, currentPiece.piece, currentPiece.size))
            currentPiece.positionX++;
    }

    public void MovePieceToBottom() {
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
        CycleNewPiece();
    }

    private void DeleteFilledRows() {
        int[] offsetMap = new int[this.height];
        int linesDeleted = 0;

        int offset = 0;
        ArrayList<Integer> tempRowsDelete = new ArrayList<Integer>();
        for (int h = 0; h < this.height - 1; h++) {
            int sum = 0;
            for (int x = 0; x < this.width; x++) {
                if (grid[x][h])
                    sum++;
            }
            if (sum == this.width) {
                tempRowsDelete.add(h);
                linesDeleted++;
                offset++;
            } else {
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

        for (int h = 0; h < this.height - 1; h++) {
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

        if (linesDeleted == 1)
            score = score + 1;
        else if (linesDeleted == 2)
            score = score + 2 * 2;
        else if (linesDeleted == 3)
            score = score + 3 * 3;
        else
            score = score + 4 * 4;
    }

    private void CycleNewPiece() {
        if ((Piece) pieces.poll() == null || pieces.size() < 10)
            FillQueue();

        this.currentPiece = (Piece) pieces.poll();
        this.currentPiece.positionX = (width - this.currentPiece.size) / 2;
        this.currentPiece.positionY = 18;

        if (!IsPieceAtValidLocation(currentPiece.positionX, currentPiece.positionY, currentPiece.piece, currentPiece.size)) {
            reset();
        }
        //Game Over
    }

    public void RotatePiece() {
        if (IsPieceAtValidLocation(currentPiece.positionX, currentPiece.positionY, this.currentPiece.GetAfterRotateRight(), this.currentPiece.size))
            this.currentPiece.Rotate();
    }

    public void reset() {
        if (score > TetrisGame.getHighScore())
            TetrisGame.setHighScore(score);


        pieces = new LinkedList<Piece>();
        InitializeGrid();
        score = 0;
        CycleNewPiece();
    }

}
