import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trialsCount;
    private final double[] percolationThresholds;


    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("Both N and T must be greater than 0");
        }
        trialsCount = t;
        percolationThresholds = new double[trialsCount];
        for (int trial = 0; trial < trialsCount; trial++) {
            Percolation perc = new Percolation(n);
            int openSitesCount = 0;
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    openSitesCount++;
                }
            }
            percolationThresholds[trial] = (double) openSitesCount / (n * n);
        }
    }


    public double mean() {
        return StdStats.mean(percolationThresholds);
    }


    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }

    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trialsCount));
    }

    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trialsCount));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);

        String confidenceInterval = stats.confidenceLo() + ", " + stats.confidenceHi();
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + confidenceInterval);
    }
}

