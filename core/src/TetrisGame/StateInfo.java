package TetrisGame;

/**
 * Created by Ming on 3/14/2016.
 */
public class StateInfo {

    public Piece piece;
    public Boolean[][] blockMap;
    public int width;
    public int height;
    public boolean isRightMostColumnEmpty;
    public int numBubbles;
    public int maxHeight;

    public StateInfo(Piece p, Boolean[][] blockMap, int width, int height)
    {
        this.piece = p;
        this.blockMap = blockMap;
        this.width = width;
        this.height = height;
        this.isRightMostColumnEmpty = true;
        this.maxHeight = height;
        this.numBubbles = 0;

        for (int i = 0; i < height; i++) {
            int blocks = 0;
            int bubbles = 0;

            for (int j = 0; j < width; j++) {
                if (!blockMap[j][i]) {
                    bubbles++;
                }
            }

            this.numBubbles = this.numBubbles + bubbles;

            if (bubbles == width)
            {
                this.maxHeight = i;
                break;
            }
        }


    }


}
