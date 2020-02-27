package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ControllerSnake implements Initializable {
    @FXML
    AnchorPane anchorPane;
    private Group rectangles = new Group();
    private Group group = new Group();
    private Group circles = new Group();
    private Stage primaryStage;
    ControllerSnake(Stage stage) {
        primaryStage = stage;
    }

    private Label lab = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Rectangle rec = new Rectangle(70, 70, 10, 10);
            lab.relocate(250.0, 480.0);
            lab.setVisible(true);
            lab.setTextFill(Color.BLACK);
            rectangles.getChildren().add(rec);
            group.getChildren().add(rectangles);
            group.getChildren().add(lab);
            group.getChildren().add(circles);
            new AnimationTimer() {
                int choose = 0;
                Random random = new Random();
                double lastX = rec.getX();
                double lastY = rec.getY();
                int speed = 140, size = 3;

                @Override
                public void handle(long l) {
                    Rectangle tempRectangle = new Rectangle();
                    tempRectangle.setWidth(10);
                    tempRectangle.setHeight(10);
                    tempRectangle.setX(lastX);
                    tempRectangle.setY(lastY);
                    primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                        if (keyEvent.getCode() == KeyCode.S) {


                            if(choose != 3) {
                                choose = 1;
                            }

                        } else if (keyEvent.getCode() == KeyCode.W) {
                            if(choose != 1) {
                                choose = 3;
                            }

                        } else if (keyEvent.getCode() == KeyCode.D) {

                            if(choose != 2) {
                                choose = 4;
                            }


                        } else if (keyEvent.getCode() == KeyCode.A) {
                            if(choose != 4) {
                                choose = 2;
                            }

                        }
                    });

                    if(isInsideItself(rectangles, tempRectangle) && choose != 0
                            && rectangles.getChildren().size() > 3) {
                        lab.setText("GAME OVER");
                        try {
                            TimeUnit.MILLISECONDS.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Stage s = (Stage) primaryStage.getScene().getWindow();
                        s.close();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
                        loader.setController(new ControllerStart(primaryStage));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        primaryStage.setScene(new Scene(root, 500, 500));
                        primaryStage.setTitle("Start");
                        primaryStage.show();
                    }

                    if(choose != 0) {
                        rectangles.getChildren().add(tempRectangle);
                    }

                    lab.setText("X: " + tempRectangle.getX() + " Y: " + tempRectangle.getY());

                    if(circles.getChildren().size() == 0) {
                        Rectangle tempCircle = new Rectangle();
                        tempCircle.setHeight(10);
                        tempCircle.setWidth(10);
                        tempCircle.setX(10 * Math.round((l % random.nextInt((int) primaryStage.getWidth() + 1))/10.));
                        tempCircle.setY(10 * Math.round((l % random.nextInt((int) primaryStage.getWidth() + 1))/10.));
                        tempCircle.setFill(Color.RED);
                        circles.getChildren().add(tempCircle);
                    }

                    try {
                        TimeUnit.MILLISECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(isEaten(rectangles, circles)) {
                        size += 1;
                        //speed -= 20;
                        System.out.println(speed);
                        circles.getChildren().remove(0);
                    }
                    if (choose == 3) {
                        lastY -= 10;
                    } else if (choose == 1) {
                        lastY += 10;
                    } else if (choose == 4) {
                        lastX += 10;
                    } else if (choose == 2) {
                        lastX -= 10;
                    }


                    if (rectangles.getChildren().size() == size) {
                        rectangles.getChildren().remove(0);
                    }

                    if (lastX > primaryStage.getWidth() - 26) {
                        lastX = primaryStage.getMinWidth();
                    }

                    if (lastX < primaryStage.getMinWidth()) {
                        lastX = primaryStage.getWidth() - 26;
                    }

                    if (lastY > primaryStage.getHeight() - 49) {
                        lastY = primaryStage.getMinHeight();
                    }

                    if (lastY < primaryStage.getMinHeight()) {
                        lastY = primaryStage.getHeight() - 49;
                    }
                }
            }.start();
            anchorPane.getChildren().add(group);
    }

    private boolean isEaten(Group rectangles, Group circles) {
        Rectangle r = (Rectangle) rectangles.getChildren().get(rectangles.getChildren().size() - 1);
        Rectangle c = (Rectangle) circles.getChildren().get(0);

        if(Math.abs(r.getX() - c.getX()) <= 0 && Math.abs(r.getY() - c.getY()) <= 0) {
            return true;
        }
        return false;
    }

    private  boolean isInsideItself(Group rectangles, Rectangle r) {
        for(int i = 0; i < rectangles.getChildren().size(); i++) {
            Rectangle temp = (Rectangle) rectangles.getChildren().get(i);
            if(r.getX() == temp.getX() && r.getY() == temp.getY()) {
                return true;
            }
        }
        return false;
    }
}
