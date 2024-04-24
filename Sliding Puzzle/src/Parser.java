import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Parser {
    protected Scanner lineScanner = null;
    private final ArrayList<String> lines = new ArrayList<>();
    private boolean done;
    protected int[] start;
    protected int[] finish;
    protected int[][] map;
    private boolean loaded;
    private File inputFile;

    public Boolean isFileRead() {
        return this.done;
    }


    public int[] getStartPoint() {
        if (hasLoaded()) {
            return this.start;
        }
        return null;
    }

    public int[] getEndPoint() {
        if (hasLoaded()) {
            return this.finish;
        }
        return null;
    }

    public int[][] getMap() {
        if (hasLoaded()) {
            return this.map;
        }
        return null;
    }

    public void loadLines() throws IOException {
        if (this.done) {
            lines.addAll(Files.readAllLines(inputFile.toPath(), Charset.defaultCharset()));
            this.loaded = true;
        }
    }

    public void readFile(String path) throws FileNotFoundException {
        File inputFile;
        inputFile = new File(path);

        if (inputFile.length() == 0) {
            throw new FileNotFoundException("The Map File " + path + " does not exist, Please Try Again!");
        }

        this.inputFile = inputFile;
        this.done = true;
    }

    public ArrayList<String> getLines() {
        if (this.done) {
            return this.lines;
        }
        return null;
    }

    public Boolean hasLoaded() {
        if (this.isFileRead()) {
            return this.loaded;
        }
        return null;
    }

    public boolean loadValues() {
        if (!this.done) {
            return false;
        }

        int floorSize = lines.get(0).trim().length();
        this.map = new int[lines.size()][floorSize];
        int lineCount = 0;

        for (String line : lines) {
            line = line.replace("0", "1").replace(".", "0");

            if (line.contains("S")) {
                this.start = new int[]{lineCount, line.indexOf("S")};
                line = line.replace("S", "0");
            }
            if (line.contains("F")) {
                this.finish = new int[]{lineCount, line.indexOf("F")};
                line = line.replace("F", "0");
            }

            String[] string = line.split("");
            int[] floor = new int[floorSize];
            for (int j = 0; j < string.length; j++) {
                floor[j] = Integer.parseInt(string[j]);
            }
            map[lineCount] = floor;
            lineCount++;
        }
        return true;
    }
}