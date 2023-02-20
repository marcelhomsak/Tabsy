package com.marcelhomsak.tabsy;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.sound.midi.*;

public class FirstWindowController implements Initializable {

    public static ArrayList<String> notes;

    private MidiChannel channel;

    @FXML
    private AnchorPane ap;
    @FXML
    private List<Button> buttons;
    @FXML
    private ImageView exitButton;
    @FXML
    private ImageView trashButton;
    @FXML
    private ImageView goBackButton;
    @FXML
    private ImageView computeButton;
    @FXML
    private ImageView playCurrentButton;


    /**
     * Changes ArrayList notes, by deleting last note (if there is any).
     */
    public void deleteLastNote() {
        if (notes.size() > 0)
            notes.remove(notes.size()-1);
        System.out.println(notes);
    }

    /**
     * Deletes all elements in ArrayList notes.
     */
    public void deleteAllNotes() {
        notes.clear();
        System.out.println(notes);
    }

    /**
     * This method is called every time a piano tile is pressed.
     * It plays a classic piano sound effect.
     * @param note Plays this note.
     */
    public void play(String note) {
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            synthesizer.loadInstrument(synthesizer.getDefaultSoundbank().getInstruments()[0]);
            channel = synthesizer.getChannels()[0];
            channel.noteOn(Notes.values.get(note), 100);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * This method is called when user presses play button.
     * It plays every note which is current in notes ArrayList
     * (which can change because of {@link #deleteLastNote() deleteLastNote} and {@link #deleteAllNotes() deleteAllNotes})
     * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
     */
    public void playCurrent() throws InterruptedException {
        for (String note : notes) {
            try {
                Synthesizer synthesizer = MidiSystem.getSynthesizer();
                synthesizer.open();
                synthesizer.loadInstrument(synthesizer.getDefaultSoundbank().getInstruments()[0]);
                channel.allNotesOff();
                channel = synthesizer.getChannels()[0];
                channel.noteOn(Notes.values.get(note), 100);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            Thread.sleep(300);
        }
    }

    public void writeTabsToFile() {
        if (notes.size() > 0) {
            String home = System.getProperty("user.home");
            String path = home + File.separator + "tabsy";
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdir();
            }
            String txtPath = path + File.separator + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh;mm;ss")) + ".txt";
            File file = new File(txtPath);
            try (FileWriter fw = new FileWriter(file);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(Tabs.play(notes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Displays tabs to the console.
     */
    public void displayTabs() {
        if (notes.size() > 0)
            System.out.println(Tabs.play(notes));
    }

    /**
     * Initially every single button (piano tile) is assigned its corresponding ActionListener - when pressed its
     * ID (which are the same as its note i.e. c4, gis5 etc.), gets added to notes ArrayList, so when tabs want to be
     * displayed only this ArrayList is required. <br>
     * Below are some more ActionListeners which call methods explained above.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notes = new ArrayList<>();

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            button.setOnAction(e -> {
                notes.add(button.getId());
                play(button.getId());
                System.out.println(notes);
            });
        }

        exitButton.setOnMouseClicked(e -> {
            System.exit(0);
        });

        trashButton.setOnMouseClicked(e -> {
            deleteAllNotes();
        });

        goBackButton.setOnMouseClicked(e -> {
            deleteLastNote();
        });

        computeButton.setOnMouseClicked(e -> {
            displayTabs();
            writeTabsToFile();
        });

        playCurrentButton.setOnMouseClicked(e -> {
            try {
                playCurrent();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

    }

}