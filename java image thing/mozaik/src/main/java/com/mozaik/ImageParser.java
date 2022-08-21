package com.mozaik;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ImageParser {

    private List<Integer[]> colors;

    public ImageParser(Image image) {
        colors = new ArrayList<Integer[]>();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                final int hex = image.getPixelReader().getArgb(i, j);
                final int r = (hex & 0xFF0000) >> 16;
                final int g = (hex & 0xFF00) >> 8;
                final int b = (hex & 0xFF);
                colors.add(new Integer[] { r, g, b });
            }
        }
    }

    public List<Integer[]> getColors() {
        return colors;
    }

    public void writeToTxt(File file) {
        if (colors == null)
            return;
        try (PrintWriter pr = new PrintWriter(new FileWriter(file))) {
            for (Integer[] rgb : colors) {
                String string = "r: " + rgb[0] + ", g: " + rgb[1] + ", b: " + rgb[2];
                pr.println(string);
            }
            pr.close();
        } catch (Exception e) {
        }
    }

    public void writeToCSV(File file) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file));) {
            writer.writeNext(new String[] { "r", "g", "b" });
            writer.writeAll(colors.stream().map(rgb -> {
                return new String[] { String.valueOf(rgb[0]), String.valueOf(rgb[1]), String.valueOf(rgb[2]) };
            }).collect(Collectors.toList()));
        } catch (Exception e) {
        }

    }
}
