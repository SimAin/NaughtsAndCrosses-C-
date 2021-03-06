public class naughtsAndCrossesCalculator extends Exception{

    //static int Height, Width, Depth, SizeOfWL; //Input variables
    private static int D1WL, D2WL, D3WL, CDWL, CD1WL, CD2WL, TotalWL, TotalWL2, TotalWL4;// Dimension winning line variables
    static int dig1, dig2;

    public static void main (String[] args) {

        //Takes Winning lines for one player in a 2 player standard game
        TotalWL = calculatePotentialWinningLines(3, 3, 1, 3);
        //Creates "total" winning lines as each player could win therefore there are double the amount of winning lines
        TotalWL2 = (TotalWL * 2);
        //Removing the assumption that a winning line has to be a straight line of 0's or X's, this calculates the number of lines if they can alternate
        TotalWL4 = (TotalWL2 * 2);

        //System.out.println("When noughts and crosses is played on a " + Height + " x " + Width + " x " + Depth + " grid and the size of the winning line is " + SizeOfWL + " :");
        System.out.println("The number of possible wining lines for each player in a standard 2 player game is :  " + TotalWL);
        System.out.println("The number of possible wining lines including both players (Straight X's or O's) :  " + TotalWL2);
        System.out.println("The number of possible wining lines is (If alternating X's and O's counts as a winning line) :  " + TotalWL4);
    }

    public static int calculatePotentialWinningLines(int width, int height, int depth, int sizeOfWinningLine) {

        gameBoard grid = new gameBoard(height,width,depth,sizeOfWinningLine);

        if(!validateSizesFit(grid)) {
            //Calls dimension calculator on each combination of sides
            //height X width X Size of Winning Line
            D1WL = dGrid(grid.getHeight(), grid.getWidth(), grid.getSizeOfWL());
            if (grid.getDepth() != 1) {
                //Dimension 1 winning lines: (result from Height x Width) X Depth
                D1WL = (D1WL * grid.getDepth());

                //Dimension 2 Winning lines: width X depth X Size of Winning Line
                D2WL = dGrid(grid.getWidth(), grid.getDepth(), grid.getSizeOfWL());
                D2WL = (D2WL * grid.getHeight());

                //Dimension 3 Winning lines: depth X height X Size of Winning Line
                D3WL = dGrid(grid.getDepth(), grid.getHeight(), grid.getSizeOfWL());
                D3WL = (D3WL * grid.getWidth());

                //Uses dig function to calculate correct winning lines and Cross dimension function to calculate how many times to use it.

                dig1 = diagonal(grid.getHeight(), grid.getWidth(), grid.getSizeOfWL());
                CD1WL = crossDGrid(grid.getDepth(), dig1, grid.getSizeOfWL());

                dig2 = diagonal(grid.getHeight(), grid.getDepth(), grid.getSizeOfWL());
                CD2WL = crossDGrid(grid.getWidth(), dig2, grid.getSizeOfWL());

                CDWL = CD1WL + CD2WL;
            }
        } else {
            return 0;
        }

        drawNaughtsAndCrosses.drawFront2D(grid);
        drawNaughtsAndCrosses.drawSide2D(grid);
        drawNaughtsAndCrosses.drawTop2D(grid);
        return (D1WL + D2WL + D3WL + CDWL); //D2WL +
    }

    public static boolean validateSizesFit(gameBoard grid) throws NumberFormatException{
        if ((grid.getWidth() > grid.getHeight()) && (grid.getHeight() > grid.getDepth()) && (grid.getDepth() < grid.getSizeOfWL()))
        {
            return true;
        }

        if ((grid.getHeight() > grid.getDepth()) && (grid.getDepth() > grid.getWidth()) && (grid.getWidth() < grid.getSizeOfWL()))
        {
            return true;
        }
        if ((grid.getDepth() > grid.getWidth()) && (grid.getWidth() > grid.getHeight()) && (grid.getHeight() < grid.getSizeOfWL()))
        {
            return true;
        }

        if((grid.getSizeOfWL() > grid.getDepth() ) && (grid.getSizeOfWL() > grid.getWidth() )&& (grid.getSizeOfWL() > grid.getHeight() )){
            return true;
        }
        return false;
    }

    //Inputs change depending on stage of calculation, hence vague input names
    public static int dGrid (int dimension_1, int dimension_2, int sizeOfWinningLine )
    {
        int HWL, VWL,DWL; // Horizontal, vertical, diagonal winning lines variable
        int TWL; // Total winning lines

        if((sizeOfWinningLine > dimension_1) && (sizeOfWinningLine > dimension_2)) {
            return 0;
        } else  {
            HWL = (dimension_1*((dimension_2-sizeOfWinningLine)+1)); //Horizontal winning lines
            if(HWL < 0 ) {  HWL = 0;    }
            VWL = (dimension_2*((dimension_1-sizeOfWinningLine)+1)); //Vertical winning lines
            if(VWL < 0 ) {  VWL = 0;    }
            DWL = (2 * ((HWL/dimension_1) * (VWL)/dimension_2)); //diagonal winning lines
            TWL = (HWL + VWL + DWL); //total winning lines
        }

        //Return total
        return TWL;
    }

    //Inputs change depending on stage of calculation, hence vague input names
    public static int diagonal (int dimension_1, int dimension_2, int dimension_3)
    {
        int HWL, VWL,DWL; // Horizontal, vertical, diagonal winning lines variable

        HWL = (dimension_1*((dimension_2-dimension_3)+1)); // Horizontal winning lines
        VWL = (dimension_2*((dimension_1-dimension_3)+1)); //Vertical winning lines
        DWL = (2 * ((HWL/dimension_1) * (VWL)/dimension_2)); //diagonal winning lines
        //Return diagonal winning lines
        return DWL;
    }

    //Inputs change depending on stage of calculation, hence vague input names
    public static int crossDGrid (int dimension_1, int diagonal, int winningLineLength) {
        int Horizontal, CDLines; //winning lines to calculate diagonals

        Horizontal = ((dimension_1 - winningLineLength) + 1); //((Height - Winning line length) +1) =  number of times to run the loop
        CDLines = (diagonal * Horizontal); // total of the above result run the correct amount of times
        //Return total
        return CDLines;
    }
}