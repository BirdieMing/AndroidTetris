/*
package com.mygdx.game;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BotPlayer {

    TetrisModel model;
    MyGdxGame game;

    public BotPlayer(MyGdxGame game, TetrisModel model)
    {
        this.game = game;
        this.model = model;
    }

    public void MakeNextMove()
    {
        Boolean[][] blockMap = model.grid;
        Piece p = model.currentPiece;

        ArrayList<Piece> pieces = GetListValidLocations(p, blockMap);
        HashMap<Piece, Boolean[][]> possibleMaps = GetBlockMaps(pieces, blockMap);
        Piece bestPiece = FindBestMove(possibleMaps);

        RunMoveSet(bestPiece);
    }

    public ArrayList<Piece> GetListValidLocations(Piece currentPiece, Boolean[][] grid)
    {
        ArrayList<Piece> possiblePieces = new ArrayList<Piece>();

        for (int o = 0; o < 4; o++) {

            int minY = 0;

            for (int x = -1; x < model.width+1; x++) {
                if (!model.IsPieceAtValidLocation(x, 0, currentPiece.piece, currentPiece.size))
                    continue;

                for (int y = 0; y < model.height; y++)
                {
                    if (!model.IsPieceAtValidLocation(x, y, currentPiece.piece, currentPiece.size))
                    {
                        Piece newPiece = new Piece();
                        newPiece.color = currentPiece.color;
                        newPiece.size = currentPiece.size;
                        newPiece.piece = currentPiece.piece;
                        newPiece.positionX = x;
                        newPiece.positionY = y - 1;
                        newPiece.orientation = o;
                        possiblePieces.add(newPiece);
                        break;
                    }
                }
            }

            currentPiece.Rotate();
        }

        return possiblePieces;
    }

    public HashMap<Piece, Boolean[][]> GetBlockMaps(ArrayList<Piece> pieces, Boolean[][] blockMap)
    {
        HashMap<Piece, Boolean[][]> possibleGrid = new HashMap<Piece, Boolean[][]>();

        for(int i = 0; i < pieces.size(); i++)
        {
            Piece p = pieces.get(i);
            Boolean[][] newBlockMap = new Boolean[this.model.width][this.model.height];

            for (int x = 0; x < this.model.width; x++) {
                for (int y = 0; y < this.model.height; y++) {
                    newBlockMap[x][y] = blockMap[x][y];
                }
            }

            for (int x = 0; x < p.size; x++) {
                for (int y = 0; y < p.size; y++) {
                    int gridX = p.positionX + x;
                    int gridY = p.positionY + y;

                    if (model.IsClearLoc(gridX, gridY) && p.piece[x][y]) {
                        newBlockMap[gridX][gridY] = true;
                    }
                }
            }

            possibleGrid.put(p, newBlockMap);
        }

        return possibleGrid;
    }

    public Piece FindBestMove(HashMap<Piece, Boolean[][]> possibilities)
    {
        int numBubbles;
        Piece bestPiece;

        ArrayList<StateInfo> infos = new ArrayList<StateInfo>();

        Iterator it = possibilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            Boolean[][] blockMap = (Boolean[][]) pair.getValue();
            Piece p = (Piece) pair.getKey();

            StateInfo si = new StateInfo(p, blockMap, model.width, model.height);
            infos.add(si);

            it.remove(); // avoids a ConcurrentModificationException
        }

        ArrayList<StateInfo> filtered = filterRightNotEmpty(infos);
        filtered = filterMinBubble(infos);
        filtered = filterMinHeight(filtered);
        //filtered = filterRightNotEmpty(filtered);

        if (filtered.size() > 0)
        {
            return filtered.get(0).piece;
        }

        return null;
    }

    public ArrayList<StateInfo> filterRightNotEmpty(ArrayList<StateInfo> list)
    {
        ArrayList<StateInfo> filtered = new ArrayList<StateInfo>();
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).isRightMostColumnEmpty)
                filtered.add(list.get(i));
        }

        return filtered;
    }

    public ArrayList<StateInfo> filterMinBubble(ArrayList<StateInfo> list)
    {
        ArrayList<StateInfo> filtered = new ArrayList<StateInfo>();
        int minBubble = Integer.MAX_VALUE;

        for (int i = 0; i < list.size(); i++)
        {
            int numBubbles = list.get(i).numBubbles;
            if (numBubbles < minBubble)
                minBubble = numBubbles;
        }

        for (int i = 0; i < list.size(); i++)
        {
            int numBubbles = list.get(i).numBubbles;

            if (minBubble == numBubbles)
                filtered.add(list.get(i));
        }

        return filtered;
    }

    public ArrayList<StateInfo> filterMinHeight(ArrayList<StateInfo> list)
    {
        ArrayList<StateInfo> filtered = new ArrayList<StateInfo>();
        int minHeight = Integer.MAX_VALUE;

        for (int i = 0; i < list.size(); i++)
        {
            int height = list.get(i).maxHeight;
            if (height < minHeight)
                minHeight = height;
        }

        for (int i = 0; i < list.size(); i++)
        {
            int height = list.get(i).maxHeight;

            if (minHeight == height)
                filtered.add(list.get(i));
        }

        return filtered;
    }

    public void RunMoveSet(Piece bestPiece)
    {
        if (bestPiece == null) {
            model.MovePieceToBottom();
            return;
        }

        for (int o = 0; o < bestPiece.orientation; o++)
        {
            model.RotatePiece();
        }

        int diffX = (bestPiece.positionX - model.currentPiecePosX);

        for (int x = 0; x < Math.abs(diffX); x++)
        {
            if (diffX > 0)
                model.MovePieceRight();
            else
                model.MovePieceLeft();
        }

        model.MovePieceToBottom();
    }
}
*/
