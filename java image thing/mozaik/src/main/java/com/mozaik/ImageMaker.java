package com.mozaik;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.image.BufferedImage;
import javafx.scene.image.Image;

public class ImageMaker {

    private int height, width;
    private Integer[][] r, g, b;

    public ImageMaker(Image image, JSONArray points) {
        height = (int) image.getHeight();
        width = (int) image.getWidth();
        r = new Integer[width][height];
        g = new Integer[width][height];
        b = new Integer[width][height];
        List<Integer[]> median = new ArrayList<Integer[]>();
        for (Object o : points) {
            JSONArray color = (JSONArray) ((JSONObject) o).get("coordinates");
            median.add(new Integer[] {
                    Integer.parseInt(String.valueOf(color.get(0))),
                    Integer.parseInt(String.valueOf(color.get(1))),
                    Integer.parseInt(String.valueOf(color.get(2))) });
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final int hex = image.getPixelReader().getArgb(i, j);
                final int red = (hex & 0xFF0000) >> 16;
                final int green = (hex & 0xFF00) >> 8;
                final int blue = (hex & 0xFF);
                Integer[] ans = closest(median, red, green, blue);
                r[i][j] = ans[0];
                g[i][j] = ans[1];
                b[i][j] = ans[2];
            }
        }
    }

    private Integer[] closest(List<Integer[]> median, int red, int green, int blue) {
        int min = -1;
        Integer[] ans = null;
        for (Integer[] rgb : median) {
            int distance = (rgb[0] - red) * (rgb[0] - red) + (rgb[1] - green) * (rgb[1] - green)
                    + (rgb[2] - blue) * (rgb[2] - blue);
            if (min == -1 || distance < min) {
                min = distance;
                ans = rgb;
            }
        }
        return ans;
    }

    public void createImage(String name) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = r[x][y];
                rgb = (rgb << 8) + g[x][y];
                rgb = (rgb << 8) + b[x][y];
                image.setRGB(x, y, rgb);
            }
        }
        File outputFile = new File("../" + name + ".bmp");
        try {
            ImageIO.write(image, "bmp", outputFile);
            System.out.println("wrote");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
