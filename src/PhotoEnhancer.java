import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class PhotoEnhancer {
    private Picture picture;
    private double[][] energies;

    public PhotoEnhancer(Picture picture) {
        this.picture = new Picture(picture);
        energies = new double[picture.height()][picture.width()];

        int left;
        int right;

        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                if (picture.width() > 1) {
                    if (col == 0) {
                        left = col;
                        right = col + 1;
                    } else if (col == picture.width() - 1) {
                        left = col - 1;
                        right = col;
                    } else {
                        left = col - 1;
                        right = col + 1;
                    }
                } else {
                    left = col;
                    right = col;
                }

                if (row > 0) {
                    int prevEnergyCol = findMin(left, col, right, energies[row - 1]);
                    energies[row][col] = energyHelper(col, row) + energies[row - 1][prevEnergyCol];
                } else {
                    energies[row][col] = energyHelper(col, row);
                }
            }
        }
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        return energies[y][x];
    }

    // energy of pixel at column x and row y
    private double energyHelper(int x, int y) {
        if (x < 0 || x >= width()) {
            throw new IndexOutOfBoundsException();
        } else if (y < 0 || y >= height()) {
            throw new IndexOutOfBoundsException();
        }

        double xGradientSquared = xGradientSquared(x, y);
        double yGradientSquared = yGradientSquared(x, y);

        return xGradientSquared + yGradientSquared;
    }

    private double xGradientSquared(int x, int y) {
        int leftRed;
        int leftGreen;
        int leftBlue;

        int rightRed;
        int rightGreen;
        int rightBlue;

        if (picture.width() == 1) {
            leftRed = picture.get(x, y).getRed();
            leftGreen = picture.get(x, y).getGreen();
            leftBlue = picture.get(x, y).getBlue();

            rightRed = picture.get(x, y).getRed();
            rightGreen = picture.get(x, y).getGreen();
            rightBlue = picture.get(x, y).getBlue();
        } else if (x == 0) {
            leftRed = picture.get(picture.width() - 1, y).getRed();
            leftGreen = picture.get(picture.width() - 1, y).getGreen();
            leftBlue = picture.get(picture.width() - 1, y).getBlue();

            rightRed = picture.get(x + 1, y).getRed();
            rightGreen = picture.get(x + 1, y).getGreen();
            rightBlue = picture.get(x + 1, y).getBlue();
        } else if (x == picture.width() - 1) {
            leftRed = picture.get(x - 1, y).getRed();
            leftGreen = picture.get(x - 1, y).getGreen();
            leftBlue = picture.get(x - 1, y).getBlue();

            rightRed = picture.get(0, y).getRed();
            rightGreen = picture.get(0, y).getGreen();
            rightBlue = picture.get(0, y).getBlue();
        } else {
            leftRed = picture.get(x - 1, y).getRed();
            leftGreen = picture.get(x - 1, y).getGreen();
            leftBlue = picture.get(x - 1, y).getBlue();

            rightRed = picture.get(x + 1, y).getRed();
            rightGreen = picture.get(x + 1, y).getGreen();
            rightBlue = picture.get(x + 1, y).getBlue();
        }

        double redDiffSquared = Math.pow(rightRed - leftRed, 2);
        double greenDiffSquared = Math.pow(rightGreen - leftGreen, 2);
        double blueDiffSquared = Math.pow(rightBlue - leftBlue, 2);

        return redDiffSquared + greenDiffSquared + blueDiffSquared;
    }

    private double yGradientSquared(int x, int y) {
        int upperRed;
        int upperGreen;
        int upperBlue;

        int lowerRed;
        int lowerGreen;
        int lowerBlue;

        if (picture.height() == 1) {
            upperRed = picture.get(x, y).getRed();
            upperGreen = picture.get(x, y).getGreen();
            upperBlue = picture.get(x, y).getBlue();

            lowerRed = picture.get(x, y).getRed();
            lowerGreen = picture.get(x, y).getGreen();
            lowerBlue = picture.get(x, y).getBlue();
        } else if (y == 0) {
            upperRed = picture.get(x, picture.height() - 1).getRed();
            upperGreen = picture.get(x, picture.height() - 1).getGreen();
            upperBlue = picture.get(x, picture.height() - 1).getBlue();

            lowerRed = picture.get(x, y + 1).getRed();
            lowerGreen = picture.get(x, y + 1).getGreen();
            lowerBlue = picture.get(x, y + 1).getBlue();
        } else if (y == picture.height() - 1) {
            upperRed = picture.get(x, y - 1).getRed();
            upperGreen = picture.get(x, y - 1).getGreen();
            upperBlue = picture.get(x, y - 1).getBlue();

            lowerRed = picture.get(x, 0).getRed();
            lowerGreen = picture.get(x, 0).getGreen();
            lowerBlue = picture.get(x, 0).getBlue();
        } else {
            upperRed = picture.get(x, y - 1).getRed();
            upperGreen = picture.get(x, y - 1).getGreen();
            upperBlue = picture.get(x, y - 1).getBlue();

            lowerRed = picture.get(x, y + 1).getRed();
            lowerGreen = picture.get(x, y + 1).getGreen();
            lowerBlue = picture.get(x, y + 1).getBlue();
        }

        double redDiffSquared = Math.pow(lowerRed - upperRed, 2);
        double greenDiffSquared = Math.pow(lowerGreen - upperGreen, 2);
        double blueDiffSquared = Math.pow(lowerBlue - upperBlue, 2);

        return redDiffSquared + greenDiffSquared + blueDiffSquared;
    }

    public int[] findBounds(int x, int y) {
        int height = (int) (picture.height() * 0.01);
        int width = (int) (picture.width() * 0.01);

        int upperBound;
        int lowerBound;
        int leftBound;
        int rightBound;

        if (y < height / 2) {
            upperBound = 0;
            lowerBound = (height / 2) + y;
        } else if (y + height / 2 > picture.height()) {
            upperBound = y - (height / 2);
            lowerBound = picture.height();
        } else {
            upperBound = y - (height / 2);
            lowerBound = (height / 2) + y;
        }
        
        if (x < width / 2) {
            leftBound = 0;
            rightBound = (width / 2) + x;
        } else if (x + width / 2 > picture.width()) {
            leftBound = x - (width / 2);
            rightBound = picture.width();
        } else {
            leftBound = x - (width / 2);
            rightBound = (width / 2) + x;
        }

        return new int[]{upperBound, lowerBound, leftBound, rightBound};
    }

    public Point findMinSurroundings(int x, int y) {
        int[] bounds = findBounds(x, y);

        double minEnergy = Double.MAX_VALUE;
        Point min = new Point(x, y);

        for (int row = bounds[0]; row < bounds[1]; row++) {
            for (int col = bounds[2]; col < bounds[3]; col++) {
                if (energies[row][col] < minEnergy) {
                    minEnergy = energies[row][col];
                    min = new Point(col, row);
                }
            }
        }

        return min;
    }

    public Color findAverageColor(int x, int y) {
        int[] bounds = findBounds(x, y);

        double currentEnergy = energies[y][x];
        Color currentColor = picture.get(x, y);
        int averageRed = currentColor.getRed();
        int averageGreen = currentColor.getGreen();
        int averageBlue = currentColor.getBlue();

        int count = 1;
        for (int row = bounds[0]; row < bounds[1]; row++) {
            for (int col = bounds[2]; col < bounds[3]; col++) {
                if (energies[row][col] < currentEnergy) {
                    Color color = picture.get(col, row);
                    averageRed += color.getRed();
                    averageGreen += color.getGreen();
                    averageBlue += color.getBlue();
                    count++;
                }
            }
        }

        return new Color(averageRed / count, averageGreen / count, averageBlue / count);
    }

    private int findMin(int left, int index, int right, double[] row) {
        int min = left;
        double minEnergy = row[left];

        if (row[index] < minEnergy) {
            min = index;
            minEnergy = row[index];
        }
        if (row[right] < minEnergy) {
            min = right;
        }

        return min;
    }
}