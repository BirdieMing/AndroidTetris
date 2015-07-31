package com.mygdx.game;

public class StateInfo
{
    public int numBubbles;
    public Piece piece;
    public boolean isRightMostColumnEmpty;
    public Boolean[][] blockMap;
    public int mapWidth;
    public int mapHeight;
    public int maxHeight;

    public StateInfo(Piece piece, Boolean[][] blockMap, int mapWidth, int mapHeight)
    {
        this.piece = piece;
        this.blockMap = blockMap;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        maxHeight = this.calcMaxHeight();
        numBubbles = this.calcNumBubble();
        isRightMostColumnEmpty = isRightMostColumnEmpty();
    }

    public Boolean isRightMostColumnEmpty()
    {
            for (int y = 0; y < mapHeight; y++) {
                if (blockMap[mapWidth - 1][y] == true)
                    return false;
            }
        return true;
    }

    public int calcNumBubble()
    {
        int bubble = 0;

        for (int x = 0; x < this.mapWidth; x++) {

            Boolean foundBlock = false;
            int columnBubble = 0;

            for (int y = 0; y < this.mapHeight; y++) {
                if (blockMap[x][y] == true) {
                    foundBlock = true;
                }
                else if (foundBlock == true)
                {
                    columnBubble++;
                }

                if (blockMap[x][y] == true || y == this.mapHeight - 1) {
                    if (columnBubble > 0) {
                        bubble = bubble + columnBubble;
                        columnBubble = 0;
                    }
                }

            }
        }

        return bubble;
    }

    public int calcMaxHeight()
    {
        int maxHeight = 0;

        for (int y = 0; y < mapHeight ;y++)
        {
            for (int x = 0; x < mapWidth; x++) {
                if (blockMap[x][y] == true)
                {
                    return mapHeight - y;
                }
            }
        }

        return maxHeight;
    }
}
