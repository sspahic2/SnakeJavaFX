package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {
    @FXML
    AnchorPane anchorPane;
    private Group rectangles = new Group();
    private Group group = new Group();
    private Group circles = new Group();
    private Stage primaryStage;
    Controller(Stage stage) {
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
                Random random = new Random();
                double lastX = rec.getX();
                double lastY = rec.getY();
                int speed = 140, size = 15;
                boolean pressedW = false, pressedS = false, pressedA = false, pressedD = false;

                @Override
                public void handle(long l) {
                    Rectangle tempRectangle = new Rectangle();
                    tempRectangle.setWidth(10);
                    tempRectangle.setHeight(10);
                    tempRectangle.setX(lastX);
                    tempRectangle.setY(lastY);
                    primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                        if (keyEvent.getCode() == KeyCode.S) {
                            if (!pressedW) {
                                pressedS = true;
                                pressedW = false;
                                pressedA = false;
                                pressedD = false;
                            }

                            if(size == 2 && pressedW) {
                                pressedS = true;
                            }
                        } else if (keyEvent.getCode() == KeyCode.W) {
                            if (!pressedS) {
                                pressedW = true;
                                pressedD = false;
                                pressedA = false;
                                pressedS = false;
                            }

                            if(size == 2 && pressedS) {
                                pressedW = true;
                            }
                        } else if (keyEvent.getCode() == KeyCode.D) {
                            if(!pressedA) {
                                pressedD = true;
                                pressedW = false;
                                pressedS = false;
                                pressedA = false;
                            }

                            if(size == 2 && pressedA) {
                                pressedD = true;
                            }
                        } else if (keyEvent.getCode() == KeyCode.A) {
                            if(!pressedD) {
                                pressedA = true;
                                pressedW = false;
                                pressedS = false;
                                pressedD = false;
                            }

                            if(size == 2 && pressedD) {
                                pressedA = true;
                            }
                        }
                    });

                    if(isInsideItself(rectangles, tempRectangle) && (pressedA || pressedS || pressedD || pressedW)
                            && rectangles.getChildren().size() > 10) {
                        lab.setText("GAME OVER");
                        try {
                            TimeUnit.MILLISECONDS.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Stage s = (Stage) primaryStage.getScene().getWindow();
                        s.close();
                    }

                    if((pressedA || pressedS || pressedD || pressedW)) {
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
                    if (pressedW) {
                        lastY -= 10;
                    } else if (pressedS) {
                        lastY += 10;
                    } else if (pressedD) {
                        lastX += 10;
                    } else if (pressedA) {
                        lastX -= 10;
                    }


                    if (rectangles.getChildren().size() == size) {
                        rectangles.getChildren().remove(0);
                    }

                    if (lastX > primaryStage.getWidth() - 26) {
                        lastX = 10;
                    }

                    if (lastX < primaryStage.getMinWidth()) {
                        lastX = primaryStage.getWidth() - 26;
                    }

                    if (lastY > primaryStage.getHeight()) {
                        lastY = 10;
                    }

                    if (lastY < primaryStage.getMinHeight() - 26) {
                        lastY = primaryStage.getHeight() - 26;
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
