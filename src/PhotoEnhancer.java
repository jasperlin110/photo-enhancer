import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class PhotoEnhancer {
    private Picture picture;
    private double[][] energies;

    public PhotoEnhancer(Picture picture) {
        this.picture = new Picture(picture);
        energies = new double[picture.height()][picture.width()];

        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                energies[row][col] = energyHelper(col, row);
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

    // For recalculating the energy values of a specified area of pixels in case their energy values have changed
    private void recalculateEnergies(int[] bounds) {
        for (int row = bounds[0]; row < bounds[1]; row++) {
            for (int col = bounds[2]; col < bounds[3]; col++) {
                energies[row][col] = energyHelper(col, row);
            }
        }
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
//            Color leftAverage = findAverageHelper(x - 3, y);
//
//            leftRed = leftAverage.getRed();
//            leftGreen = leftAverage.getGreen();
//            leftBlue = leftAverage.getBlue();
//
//            Color rightAverage = findAverageHelper(x + 3, y);
//
//            rightRed = rightAverage.getRed();
//            rightGreen = rightAverage.getGreen();
//            rightBlue = rightAverage.getBlue();

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

    private Color findAverageHelper(int x, int y) {
        int[] bounds = findBounds(x, y);

        Color currentColor = picture.get(x, y);
        int averageRed = currentColor.getRed();
        int averageGreen = currentColor.getGreen();
        int averageBlue = currentColor.getBlue();

        int count = 1;
        for (int row = bounds[0]; row < bounds[1]; row++) {
            for (int col = bounds[2]; col < bounds[3]; col++) {
                Color color = picture.get(col, row);
                averageRed += color.getRed();
                averageGreen += color.getGreen();
                averageBlue += color.getBlue();
                count++;
            }
        }

        averageRed /= count;
        averageGreen /= count;
        averageBlue /= count;

        return new Color(averageRed, averageGreen, averageBlue);
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
//            Color upperAverage = findAverageHelper(x, y - 3);
//
//            upperRed = upperAverage.getRed();
//            upperGreen = upperAverage.getGreen();
//            upperBlue = upperAverage.getBlue();
//
//            Color lowerAverage = findAverageHelper(x, y + 3);
//
//            lowerRed = lowerAverage.getRed();
//            lowerGreen = lowerAverage.getGreen();
//            lowerBlue = lowerAverage.getBlue();

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

    // Finds area around a specified pixel, effectively creating a "brush"
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

    // Finds the average color of the pixels in the area surrounding a specified pixel
    public Color findAverageColor(int x, int y) {
        int[] bounds = findBounds(x, y);

        Color currentColor = picture.get(x, y);
        int averageRed = currentColor.getRed();
        int averageGreen = currentColor.getGreen();
        int averageBlue = currentColor.getBlue();

        int count = 1;
        for (int row = bounds[0]; row < bounds[1]; row++) {
            for (int col = bounds[2]; col < bounds[3]; col++) {
                double currentEnergy = energy(x, y);
                // Only add pixel if its energy is less than that of the specified pixel
                if (energy(col, row) < currentEnergy) {
                    Color color = picture.get(col, row);
                    averageRed += color.getRed();
                    averageGreen += color.getGreen();
                    averageBlue += color.getBlue();
                    count++;
                }
            }
        }

        recalculateEnergies(bounds);

        return new Color(averageRed / count, averageGreen / count, averageBlue / count);
    }

    // Finds the average color of the pixels in the area surrounding a specified pixel
    public Color findAverageColor(int x, int y, Color originalColor) {
        int[] bounds = findBounds(x, y);

        Color currentColor = picture.get(x, y);
        int averageRed = currentColor.getRed() + originalColor.getRed();
        int averageGreen = currentColor.getGreen() + originalColor.getGreen();
        int averageBlue = currentColor.getBlue() + originalColor.getBlue();

        int count = 2;
        for (int row = bounds[0]; row < bounds[1]; row++) {
            for (int col = bounds[2]; col < bounds[3]; col++) {
                double currentEnergy = energy(x, y);
                // Only add pixel if its energy is less than that of the specified pixel
                if (energy(col, row) < currentEnergy) {
                    Color color = picture.get(col, row);
                    averageRed += color.getRed();
                    averageGreen += color.getGreen();
                    averageBlue += color.getBlue();
                    count++;
                }
            }
        }

        recalculateEnergies(bounds);

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