package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

/**
 * Created by Ming on 4/15/2015.
 */
public class Piece {

    Boolean[][] piece;
    int positionX;
    int positionY;
    PieceType type;
    int size;
    int randomRank;
    boolean state;
    Color color;
    float alpha;
    public int orientation;

    public Piece(int randomRank, int orientation)
    {
        this.orientation = orientation;
        state = true;
        size = 4;
        alpha = 0.8f;
        switch (randomRank) {
            case 1: { //block
                this.color = new Color(79 / 255.0f, 86 / 255.0f, 255 / 255.0f, alpha);
                piece = new Boolean[2][2];
                piece[0][1] = true; piece[1][1] = true;
                piece[0][0] = true; piece[1][0] = true;
                size = 2;
                break;
            }
            case 2: { //line
                this.color = new Color(103 / 255.0f, 232 / 255.0f, 90 / 255.0f, alpha);
                piece = new Boolean[4][4];
                piece[0][3] = false; piece[1][3] = false; piece[2][3] = false; piece[3][3] = false;
                piece[0][2] = false; piece[1][2] = false; piece[2][2] = false; piece[3][2] = false;
                piece[0][1] = true; piece[1][1] = true; piece[2][1] = true; piece[3][1] = true;
                piece[0][0] = false; piece[1][0] = false; piece[2][0] = false; piece[3][0] = false;
                break;
            }
            case 3: { //L
                this.color = new Color(255 / 255.0f, 179 / 255.0f, 92 / 255.0f, alpha);
                piece = new Boolean[3][3];
                piece[0][2] = false; piece[1][2] = false; piece[2][2] = false;
                piece[0][1] = true; piece[1][1] = true; piece[2][1] = true;
                piece[0][0] = true; piece[1][0] = false; piece[2][0] = false;
                size = 3;
                break;
            }
            case 4: { //R-L
                this.color = new Color(138 / 255.0f, 72 / 255.0f, 232 / 255.0f, alpha);
                piece = new Boolean[3][3];
                piece[0][2] = false; piece[1][2] = false; piece[2][2] = false;
                piece[0][1] = true; piece[1][1] = true; piece[2][1] = true;
                piece[0][0] = false; piece[1][0] = false; piece[2][0] = true;

                size = 3;
                break;
            }
            case 5: { //R-Z
                this.color = new Color(104 / 255.0f, 255 / 255.0f, 164 / 255.0f, alpha);
                piece = new Boolean[3][3];
                piece[0][2] = false; piece[1][2] = false; piece[2][2] = false;
                piece[0][1] = true; piece[1][1] = true; piece[2][1] = false;
                piece[0][0] = false; piece[1][0] = true; piece[2][0] = true;


                size = 3;
                break;
            }
            case 6: {//Z
                this.color = new Color(232 / 255.0f,63 / 255.0f, 174 / 255.0f, alpha);
                piece = new Boolean[3][3];
                piece[0][2] = false; piece[1][2] = false; piece[2][2] = false;
                piece[0][1] = false; piece[1][1] = true; piece[2][1] = true;
                piece[0][0] = true; piece[1][0] = true; piece[2][0] = false;
                size = 3;
                break;
            }
            case 7: {//R-T
                this.color = new Color(255 / 255.0f, 84 / 255.0f, 74 / 255.0f, alpha);
                piece = new Boolean[3][3];
                piece[0][2] = false; piece[1][2] = false; piece[2][2] = false;
                piece[0][1] = true; piece[1][1] = true; piece[2][1] = true;
                piece[0][0] = false; piece[1][0] = true; piece[2][0] = false;
                this.size = 3;
                break;
            }
            default:
                Gdx.app.debug("Random Num:" + randomRank, "" );
                break;
        }


    }

    public Piece()
    {
        this(new Random().nextInt(5) + 1, 0);
    }

    public Boolean[][] GetAfterRotateRight()
    {
        Boolean[][] result = new Boolean[size][size];

        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                result[i][j] = this.piece[size - j - 1][i];
            }
        }
        return result;
    }

    public Boolean[][] GetAfterRotateLeft()
    {
        Boolean[][] result = new Boolean[size][size];

        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                result[i][j] = this.piece[j][size - i - 1];
            }
        }
        return result;
    }

    public Boolean[][] GetNextRotation()
    {
        if (randomRank == 2 || randomRank == 5 || randomRank == 6) {
            state = !state;

            if (state) {
                return GetAfterRotateRight();
            } else {
                return GetAfterRotateLeft();
            }
        } else
            return this.GetAfterRotateRight();
    }

    public void Rotate() {
        this.orientation = (this.orientation + 1 ) % 3;
        this.piece = GetNextRotation();
    }

}




