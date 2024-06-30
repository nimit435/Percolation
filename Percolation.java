import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int VIRTUAL_TOP = 0;
    private final boolean[][] grid;
    private final int dimension;
    private final int virtualBottom;
    private int openSitesCount;
    private final WeightedQuickUnionUF uf;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        dimension = n;
        virtualBottom = dimension * dimension + 1;
        uf = new WeightedQuickUnionUF(dimension * dimension + 2);
        grid = new boolean[dimension][dimension];
        openSitesCount = 0;
    }


    public void open(int row, int col) {
        validateIndices(row, col);
        if (grid[row - 1][col - 1]) return;
        grid[row - 1][col - 1] = true;
        openSitesCount++;

        int currentSiteIndex = getSiteIndex(row, col);


        if (row == 1) {
            uf.union(currentSiteIndex, VIRTUAL_TOP);
        }


        if (row == dimension) {
            uf.union(currentSiteIndex, virtualBottom);
        }


        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(currentSiteIndex, getSiteIndex(row - 1, col));
        }
        if (row < dimension && isOpen(row + 1, col)) {
            uf.union(currentSiteIndex, getSiteIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(currentSiteIndex, getSiteIndex(row, col - 1));
        }
        if (col < dimension && isOpen(row, col + 1)) {
            uf.union(currentSiteIndex, getSiteIndex(row, col + 1));
        }
    }

    private void validateIndices(int row, int col) {
        if (row <= 0 || row > dimension || col <= 0 || col > dimension) {
            throw new IllegalArgumentException("Row and column indices must be within valid range");
        }
    }


    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return grid[row - 1][col - 1];
    }


    public int numberOfOpenSites() {
        return openSitesCount;
    }


    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return uf.find(VIRTUAL_TOP) == uf.find(getSiteIndex(row, col));
    }


    private int getSiteIndex(int row, int col) {
        return (row - 1) * dimension + col;
    }


    public boolean percolates() {
        return uf.find(VIRTUAL_TOP) == uf.find(virtualBottom);
    }
}
