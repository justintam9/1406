import javafx.geometry.Point2D;

public class PartImage {
    private boolean[][]     pixels;
    private boolean[][]     visited;
    private boolean[][]     visit;
    private int             rows;
    private int             cols;
    private int             p;
    public PartImage(int r, int c) {
        rows = r;
        cols = c;
        p = 0;
        visited = new boolean[r][c];
        visit = new boolean[r][c];
        pixels = new boolean[r][c];
    }

    public PartImage(int rw, int cl, byte[][] data) {
        this(rw,cl);
        for (int r=0; r<10; r++) {
            for (int c=0; c<10; c++) {
                if (data[r][c] == 1)
                    pixels[r][c] = true;
                else
                    pixels[r][c]= false;
            }
        }
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                visit [r][c] = false;
            }
        }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean getPixel(int r, int c) { return pixels[r][c]; }

    // You will re-write the 5 methods below
    public void print() {
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if (pixels[r][c])
                    System.out.print ("*");
                else
                    System.out.print ("-");
            }
            System.out.println("");
        }
    }
    public Point2D findStart() {
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if (pixels[r][c])
                    return (new Point2D (r,c));
            }
        }
        return null;
    }
    public int partSize() {
        int count = 0;
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if (pixels[r][c])
                    count++;
            }
        }
        return count;
    }
    private void expandFrom(int r, int c) {
        if (!pixels [r][c]){
            return;
        }
        else{
            pixels[r][c]=false;
            visited[r][c] = true;
            if (r+1 < rows){
                expandFrom(r+1,c);
            }
            if (c+1 < cols){
                expandFrom(r,c+1);
            }
            if (r-1 > -1){
                expandFrom(r-1,c);
            }
            if (c - 1 > -1) {
                expandFrom(r,c-1);
            }
        }
    }
    private int perimeterOf(int r, int c) {
        expandFrom(r,c);
        if (!visited[r][c]){
            return p++;
        }
        else{
            visit[r][c] = true;
            if (r == 0){
                p++;
            }
            if (c == 0){
                p++;
            }
            if (r==rows-1){
                p++;
            }
            if (c==cols-1){
                p++;
            }
            if (r+1 < rows){
                if (!visit[r+1][c])
                perimeterOf(r+1,c);
            }
            if (c+1 < cols){
                if (!visit[r][c+1])
                perimeterOf(r,c+1);
            }
            if (r-1 > -1){
                if (!visit[r-1][c])
                perimeterOf(r-1,c);
            }
            if (c-1 > -1){
                if (!visit[r][c-1])
                perimeterOf(r,c-1);
            }
        }
        return p;
    }

    public boolean isBroken(){
        Point2D p = findStart();
        expandFrom((int)p.getX(), (int)p.getY());
        return (partSize() != 0);
    }

    public int perimeter() {
        Point2D p = findStart();
        return perimeterOf((int)p.getX(), (int)p.getY());
    }

    public static PartImage exampleA() {
        byte[][] pix = {{0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,1,1,1,0},
                {0,0,0,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}};
        return new PartImage(10,10, pix);
    }
    public static PartImage exampleB() {
        byte[][] pix = {{1,0,1,0,1,0,1,0,0,0},
                {1,0,1,0,1,0,1,1,1,1},
                {1,0,1,0,1,0,1,0,0,0},
                {1,0,1,0,1,0,1,1,1,1},
                {1,0,1,0,1,0,1,0,0,0},
                {1,0,1,0,1,0,1,1,1,1},
                {1,1,1,1,1,1,1,0,0,0},
                {0,1,0,1,0,0,1,1,1,1},
                {0,1,0,1,0,0,1,0,0,0},
                {0,1,0,1,0,0,1,0,0,0}};
        return new PartImage(10,10, pix);
    }
    public static PartImage exampleC() {
        byte[][] pix = {{1,1,1,0,0,0,1,0,0,0},
                {1,1,1,1,0,0,1,1,1,0},
                {1,1,1,1,1,1,1,1,1,1},
                {0,1,1,1,0,0,1,0,0,0},
                {0,0,1,0,0,0,0,0,0,0},
                {1,0,0,0,1,1,0,1,1,1},
                {1,1,0,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1},
                {0,0,1,1,0,1,1,1,1,1},
                {0,0,1,0,0,0,1,1,0,0}};
        return new PartImage(10,10, pix);
    }
    public static PartImage exampleD() {
        byte[][] pix = {{1,0,1,0,1,0,1,1,0,0},
                {1,0,1,0,0,0,1,0,0,0},
                {0,0,0,0,0,0,0,0,1,1},
                {1,0,1,1,1,1,1,1,1,0},
                {1,0,0,1,0,0,1,0,0,0},
                {1,1,0,0,0,1,1,0,0,1},
                {0,1,0,0,0,0,0,0,1,1},
                {0,1,0,1,0,0,0,0,0,0},
                {0,0,0,1,1,1,0,0,0,0},
                {0,0,0,0,0,1,1,0,0,0}};
        return new PartImage(10,10, pix);
    }
}