import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.awt.*;
import java.util.Random;

public class Grid extends Application {
    Image i;
    String t;
    int count = 0;
    boolean flag = true;
    Node temp;
    Timeline updateTimer;

    Image tBlank = new Image(getClass().getResourceAsStream("BLANK.png"));
    Image tPet = new Image(getClass().getResourceAsStream("Pets_Thumbnail.png"));
    Image tScenery = new Image(getClass().getResourceAsStream("Scenery_Thumbnail.png"));
    Image tLego = new Image(getClass().getResourceAsStream("Lego_Thumbnail.png"));
    Image tNumbers = new Image(getClass().getResourceAsStream("Numbers_thumbnail.png"));

    boolean[][]blank = new boolean[4][4];
    Button[][] b = new Button[4][4];
    Point2D[][] coord = new Point2D[4][4];
    public void start (Stage primaryStage) {
        BorderPane pane = new BorderPane();

        GridPane grid = new GridPane();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                coord [row][col] = new Point2D(row,col);
                blank[row][col]= true;
                b[row][col] = new Button();
                grid.setConstraints(b[row][col], col, row);
                b[row][col].setPrefSize(187, 187);
                b[row][col].setGraphic(new ImageView("BLANK.png"));
                b[row][col].setPadding(new Insets(1, 1, 1, 1));
                grid.getChildren().add(b[row][col]);
                b[row][col].setOnAction(new EventHandler<ActionEvent>() {
                    public void handle (ActionEvent e){
                        for (int r = 0;r<4;r++){
                            for (int c = 0;c<4;c++){
                                if (e.getSource()==b[r][c]){
                                    swap (r,c);
                                }
                            }
                        }
                    }
                });
            }
        }
        VBox box = new VBox(10);

        Label thumb = new Label();
        thumb.setGraphic(new ImageView(tBlank));
        thumb.setPrefSize(187, 187);

        ListView<String> titles = new ListView<String>();
        String[] title = {"Pets", "Scenery", "Lego", "Numbers"};
        titles.setItems(FXCollections.observableArrayList(title));
        titles.setPrefSize(170, 187);

        titles.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (titles.getSelectionModel().getSelectedIndex() == 0) {
                    thumb.setGraphic(new ImageView(tPet));
                    t = "Pets";
                } else if (titles.getSelectionModel().getSelectedIndex() == 1) {
                    thumb.setGraphic(new ImageView(tScenery));
                    t = "Scenery";
                } else if (titles.getSelectionModel().getSelectedIndex() == 2) {
                    thumb.setGraphic(new ImageView(tLego));
                    t = "Lego";
                } else if (titles.getSelectionModel().getSelectedIndex() == 3) {
                    thumb.setGraphic(new ImageView(tNumbers));
                    t = "Numbers";
                }
            }
        });

        Button start = new Button("Start");
        start.setStyle("-fx-base: DARKGREEN; -fx-text-fill: rgb(255,255,255);");
        start.setPrefSize(187, 30);

        HBox time = new HBox(10);
        Label ltime = new Label("Time:");
        TextField alarm = new TextField("0:00");
        alarm.setAlignment(Pos.BASELINE_RIGHT);
        time.getChildren().addAll(ltime, alarm);

        updateTimer = new Timeline(new KeyFrame(Duration.millis(1000),
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        flag = true;
                        alarm.setText(String.format("%d:%02d", count / 60, count % 60));
                        count++;
                        alarm.setText(String.format("%d:%02d", count / 60, count % 60));
                        for (int i= 0;i<4;i++){
                            for(int j=0;j<4;j++){
                                if (coord[i][j].getX()!=i || coord[i][j].getY()!=j){
                                    flag = false;
                                }
                            }
                        }
                        if (flag){
                            for (int i= 0;i<4;i++) {
                                for (int j = 0; j < 4; j++) {
                                    b[i][j].setDisable(true);
                                    if (!blank[i][j]){
                                        b[i][j].setGraphic(temp);
                                    }
                                    updateTimer.stop();
                                }
                            }
                        }
                    }
                }));
        updateTimer.setCycleCount(Timeline.INDEFINITE);

        start.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (start.getText()=="Start") {
                    alarm.setText(String.format("%d:%02d", 0,0));
                    titles.setDisable(true);
                    updateTimer.play();
                    for (int row = 0; row < 4; row++) {
                        for (int col = 0; col < 4; col++) {
                            i = new Image(getClass().getResourceAsStream(t + "_" + row + col + ".png"));
                            b[row][col].setGraphic(new ImageView(i));
                            blank[row][col] = true;
                            coord [row][col] = new Point2D(row,col);
                        }
                    }
                    Random rand = new Random();
                    int r = rand.nextInt(4);
                    int c = rand.nextInt(4);
                    temp = b[r][c].getGraphic();
                    b[r][c].setGraphic(new ImageView(tBlank));
                    blank[r][c] = false;
                    for (int i = 0; i < 5000; i++) {
                        r = rand.nextInt(4);
                        c = rand.nextInt(4);
                        swap(r, c);
                    }
                    start.setStyle("-fx-base: DARKRED");
                    start.setText("Stop");
                }
                else{
                    alarm.setText(String.format("%d:%02d", 0,0));
                    updateTimer.stop();
                    titles.setDisable(false);
                    for (int row = 0; row < 4; row++) {
                        for (int col = 0; col < 4; col++) {
                            b[row][col].setGraphic(new ImageView("BLANK.png"));
                            b[row][col].setDisable(false);
                        }
                    }
                    start.setStyle("-fx-base: DARKGREEN");
                    start.setText("Start");
                    count = 0;
                }
            }
        });

        box.getChildren().addAll(thumb, titles, start, time);
        pane.setRight(box);
        pane.setCenter(grid);
        pane.setMargin(box, new Insets(0,10,10,10));
        pane.setPadding(new Insets(10));

        primaryStage.setTitle("Slider Puzzle Game");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();


    }
    void swap(int row, int col){
        Point2D temp;
        if (row<3) {
            if (!blank[row + 1][col]) {

                Node t1 = b[row][col].getGraphic();
                b[row + 1][col].setGraphic(t1);
                b[row][col].setGraphic(new ImageView(tBlank));

                blank [row+1][col]=true;
                blank[row][col]=false;

                temp = coord[row][col];
                coord[row][col]= coord[row+1][col];
                coord[row+1][col]=temp;

            }
        }
        if (row>0) {
            if (!blank[row - 1][col]) {
                Node t1 = b[row][col].getGraphic();
                b[row - 1][col].setGraphic(t1);
                b[row][col].setGraphic(new ImageView(tBlank));

                blank [row-1][col]=true;
                blank[row][col]=false;

                temp = coord[row][col];
                coord[row][col]= coord[row-1][col];
                coord[row-1][col]=temp;
            }
        }
        if (col<3) {
            if (!blank[row][col + 1]) {
                Node t1 = b[row][col].getGraphic();
                b[row][col+ 1].setGraphic(t1);
                b[row][col].setGraphic(new ImageView(tBlank));

                blank [row][col+1]=true;
                blank[row][col]=false;

                temp = coord[row][col];
                coord[row][col]= coord[row][col+1];
                coord[row][col+1]=temp;
            }
        }
        if (col>0){
            if (!blank[row][col - 1]) {

                Node t1 = b[row][col].getGraphic();
                b[row][col - 1].setGraphic(t1);
                b[row][col].setGraphic(new ImageView(tBlank));

                blank[row][col - 1] = true;
                blank[row][col] = false;

                temp = coord[row][col];
                coord[row][col]= coord[row][col-1];
                coord[row][col-1]=temp;
            }
        }

    }
    public static void main (String [] args){launch (args);}

}
