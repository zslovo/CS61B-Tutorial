package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: zslovo
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }
    public boolean helpermethod() {
        boolean changed = false;
        for(int col = 0; col <= 3; col += 1) //Loop of every column
        {
            /*
            After two numbers of one column merged
            if the merged value equals to the other value
            it won't merge again
             */
            int merge_value = 1;

            for (int row = 2; row >= 0; row -= 1) //Loop of every row
            {
                //Counting Steps
                int step = 0;
                Tile t = tile(col, row);
                if(t == null) {continue;}//0(null) won't move

                for (int blocks = 1; blocks <=3; blocks++)
                {
                    if((row + blocks) > 3) {break;}//out of bounds ==> ending the upper loop

                    Tile t_upper = tile(col, row + blocks);
                    /*
                    There are three conditions
                    1.t_upper is null ==> t could move 1 step
                    2.t_upper's value equals to t's value ==> t could move 1 step, and stop here ready merge
                    3.t_upper's value not equals to t's value ==> won't move
                     */
                    if(t_upper == null)
                    {
                        step += 1;
                    }
                    else
                    {

                        if(t_upper.value() == t.value())
                        {
                            step += 1;
                        }
                        break;
                    }
                }

                //Merging/Moving Tilts And Calculating Score
                if(step > 0)
                {
                    //Merge/Move Tilts
                    if(merge_value == t.value())
                    {
                        /*
                        The reason why the row value is (row + step - 1)
                        is that step already been added
                        and merge already happened once
                        we need to stop the merging ==> minus 1
                         */
                        boolean merge_or_not = board.move(col, row + step - 1, t);
                        changed = true;
                        break;
                    }
                    boolean merge_or_not = board.move(col, row + step, t);
                    changed = true;


                    //Calculate Score
                    if(merge_or_not == true)//whether tilt changes, if it changes, score changes
                    {
                        merge_value = 2 * t.value();
                        score += merge_value;
                    }
                }
            }
        }
        return changed;
    }
    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed = false;
        if(side == Side.NORTH) {
            changed = helpermethod();
        }
        else
        {
            board.setViewingPerspective(side);
            changed = helpermethod();
            board.setViewingPerspective(Side.NORTH);
        }
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        for(int i = 0; i < 4; i += 1)
        {
            for (int j = 0; j < 4; j += 1)
            {
                if (b.tile(i, j) == null)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for(int i = 0; i < 4; i += 1)
        {
            for (int j = 0; j < 4; j += 1)
            {
                Tile null_or_value = b.tile(i, j);
                if(null_or_value != null)
                {
                    int t = b.tile(i, j).value();
                    if(t == MAX_PIECE)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                Tile null_or_value = b.tile(i, j);
                if (null_or_value == null)
                {
                    return true;
                }
                else
                {
                    int value = b.tile(i, j).value();
                    int row_north = i - 1, row_south = i + 1;
                    int col_east = j -1, col_west = j + 1;
                    //North
                    if(row_north >= 0)
                    {
                        if (b.tile(row_north,j) == null)
                        {
                            return true;
                        }
                        else if(b.tile(row_north, j).value() == value)
                        {
                            return true;
                        }
                    }

                    //South
                    if(row_south <= 3)
                    {
                        if(b.tile(row_south,j) == null)
                        {
                            return true;
                        }
                        else if (value == b.tile(row_south,j).value())
                        {
                            return true;
                        }
                    }

                    //East
                    if(col_east >= 0)
                    {
                        if(b.tile(i,col_east) == null)
                        {
                            return  true;
                        }
                        else if (value == b.tile(i,col_east).value())
                        {
                            return true;
                        }
                    }

                    //West
                    if(col_west <= 3)
                    {
                        if(b.tile(i,col_west) == null)
                        {
                            return true;
                        }
                        else if (value == b.tile(i,col_west).value())
                        {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model???s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
