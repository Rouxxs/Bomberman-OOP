package uet.oop.bomberman.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SmallInforLabel extends Label {

    private final static String FONT_PATH = "kenvector_future.ttf";

    public SmallInforLabel(String text, Pos pos) {
        setPrefWidth(130);
        setPrefHeight(48);

        BackgroundImage backgroundImage = new BackgroundImage(new Image("blue_info_label.png", 130, 48, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(pos);
        setPadding(new Insets(10, 10, 10, 10));
        setLabelFont();
        setText(text);
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 15));
            System.out.println("Font loaded.");
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 15));
        }
    }
}
