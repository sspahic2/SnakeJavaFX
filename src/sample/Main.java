package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {
    private Group rectangles = new Group();
    private Group group = new Group();
    private Group circles = new Group();

    private Label lab = new Label();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle rec = new Rectangle(75, 75, 15, 15);
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
            double foodX = random.nextInt(500);
            double foodY = random.nextInt(500);
            int speed = 500, size = 2;
            boolean pressedW = false, pressedS = false, pressedA = false, pressedD = false;

            @Override
            public void handle(long l) {
                Rectangle tempRectangle = new Rectangle();
                tempRectangle.setWidth(15);
                tempRectangle.setHeight(15);
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
                rectangles.getChildren().add(tempRectangle);

                if(circles.getChildren().size() == 0) {
                    Rectangle tempCircle = new Rectangle();
                    tempCircle.setHeight(15);
                    tempCircle.setWidth(15);
                    tempCircle.setX(l % random.nextInt(501));
                    tempCircle.setY(l % random.nextInt(501));
                    tempCircle.setFill(Color.RED);
                    lab.setText("X: " + tempCircle.getX() + " Y: " + tempCircle.getY());

                    circles.getChildren().add(tempCircle);
                }

                if(isEaten(rectangles, circles)) {
                    size += 1;
                    speed -= 20;
                    System.out.println(speed);
                    circles.getChildren().remove(0);
                }
                if (pressedW) {
                    lastY -= 15;
                } else if (pressedS) {
                    lastY += 15;
                } else if (pressedD) {
                    lastX += 15;
                } else if (pressedA) {
                    lastX -= 15;
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (rectangles.getChildren().size() == size) {
                    rectangles.getChildren().remove(0);
                }

                if (lastX > 500) {
                    lastX = 0;
                }

                if (lastX < 0) {
                    lastX = 500;
                }

                if (lastY > 500) {
                    lastY = 0;
                }

                if (lastY < 0) {
                    lastY = 500;
                }
            }
        }.start();

        primaryStage.setScene(new Scene(group, 500, 500));
        primaryStage.setTitle("Snake");
        primaryStage.show();
    }

    private boolean isEaten(Group rectangles, Group circles) {
        Rectangle r = (Rectangle) rectangles.getChildren().get(rectangles.getChildren().size() - 1);
        Rectangle c = (Rectangle) circles.getChildren().get(0);

        if(Math.abs(r.getX() - c.getX()) <= 10 && Math.abs(r.getY() - c.getY()) <= 10) {
            return true;
        }

        return false;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
