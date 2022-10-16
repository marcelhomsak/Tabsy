package com.marcelhomsak.tabsy;

import java.util.ArrayList;
import java.util.HashMap;

public class Tabs {

    /**
     * HashMap of notes as keys and its corresponding possible positions of playing it on guitar as values. <br>
     * Example: (5)3 means 5th string and 3rd fret <br>
     * Note: some notes can be played on different strings and some cannot.
     */
    private static HashMap<String, String[]> positions = new HashMap<>() {{
        put("e2", new String[]{"(6)0"});
        put("f2", new String[]{"(6)1"});
        put("fis2", new String[]{"(6)2"});
        put("g2", new String[]{"(6)3"});
        put("gis2", new String[]{"(6)4"});
        put("a2", new String[]{"(5)0", "(6)5"});
        put("ais2", new String[]{"(5)1", "(6)6"});
        put("b2", new String[]{"(5)2", "(6)7"});
        put("c3", new String[]{"(5)3", "(6)8"});
        put("cis3", new String[]{"(5)4", "(6)9"});
        put("d3", new String[]{"(4)0", "(5)5", "(6)10"});
        put("dis3", new String[]{"(4)1", "(5)6", "(6)11"});
        put("e3", new String[]{"(4)2", "(5)7", "(6)12"});
        put("f3", new String[]{"(4)3", "(5)8", "(6)13"});
        put("fis3", new String[]{"(4)4", "(5)9", "(6)14"});
        put("g3", new String[]{"(3)0", "(4)5", "(5)10", "(6)15"});
        put("gis3", new String[]{"(3)1", "(4)6", "(5)11", "(6)16"});
        put("a3", new String[]{"(3)2", "(4)7", "(5)12", "(6)17"});
        put("ais3", new String[]{"(3)3", "(4)8", "(5)13", "(6)18"});
        put("b3", new String[]{"(2)0", "(3)4", "(4)9", "(5)14"});
        put("c4", new String[]{"(2)1", "(3)5", "(4)10", "(5)15"});
        put("cis4", new String[]{"(2)2", "(3)6", "(4)11", "(5)16"});
        put("d4", new String[]{"(2)3", "(3)7", "(4)12", "(5)17"});
        put("dis4", new String[]{"(2)4", "(3)8", "(4)13", "(5)18"});
        put("e4", new String[]{"(1)0", "(2)5", "(3)9", "(4)14"});
        put("f4", new String[]{"(1)1", "(2)6", "(3)10", "(4)15"});
        put("fis4", new String[]{"(1)2", "(2)7", "(3)11", "(4)16"});
        put("g4", new String[]{"(1)3", "(2)8", "(3)12", "(4)17"});
        put("gis4", new String[]{"(1)4", "(2)9", "(3)13", "(4)18"});
        put("a4", new String[]{"(1)5", "(2)10", "(3)14"});
        put("ais4", new String[]{"(1)6", "(2)11", "(3)15"});
        put("b4", new String[]{"(1)7", "(2)12", "(3)16"});
        put("c5", new String[]{"(1)8", "(2)13", "(3)17"});
        put("cis5", new String[]{"(1)9", "(2)14", "(3)18"});
        put("d5", new String[]{"(1)10", "(2)15"});
        put("dis5", new String[]{"(1)11", "(2)16"});
        put("e5", new String[]{"(1)12", "(2)17"});
        put("f5", new String[]{"(1)13", "(2)18"});
        put("fis5", new String[]{"(1)14"});
        put("g5", new String[]{"(1)15"});
        put("gis5", new String[]{"(1)16"});
        put("a5", new String[]{"(1)17"});
        put("ais5", new String[]{"(1)18"});
        put("b5", new String[]{"(1)19"});
    }};

    /**
     * Check if all notes are valid (can be played on a classical guitar).
     * @param notes ArrayList of notes which were played on a piano.
     * @return returns true if all notes are valid and false otherwise.
     */
    public static boolean checkGuitarValidation(ArrayList<String> notes) {
        for (String note : notes)
            if (!positions.containsKey(note))
                return false;
        return true;
    }

    /**
     * Extract the guitar string number from a String which is of the same format as the values in the positions
     * HashMap i.e. '(5)3'.
     * @param position guitar position i.e. '(5)3'
     * @return guitar string number which would be 5 in this case.
     */
    public static int string(String position) {
        return Integer.parseInt(String.valueOf(position.split("\\)")[0].charAt(1)));
    }

    /**
     * Extract the guitar fret number from a String which is of the same format as the values in the positions
     * HashMap i.e. '(5)3'
     * @param position guitar position i.e. '(5)3'
     * @return guitar fret number which would be 3 in this case.
     */
    public static int fret(String position) {
        return Integer.parseInt(position.split("\\)")[1]);
    }

    /**
     * Draws a single tab.
     * @param position guitar position i.e. '(5)3'
     * @return String of a single tab.
     * For example '(5)3' would be: <br>
     * --- <br>
     * --- <br>
     * --- <br>
     * --- <br>
     * -3- <br>
     * ---
     */
    public static String draw(String position) {
        String output = "";
        int string = string(position);
        int fret = fret(position);
        for (int i = 1; i <= 6; i++) {
            if (i == string)
                output += "-" + fret + "-\n";
            else
                output += "---\n";
        }
        return output;
    }

    /**
     * This method first splits oldDisplay i.e. '(5)3-(5)2-(5)0' by '-' and {@link #draw(String) draws} every part of it
     * and appends its output to a new array.
     * Parts of that new array are then concatenated, so they are readable and represented from left to right not from top to bottom.
     * @param oldDisplay String oldDisplay i.e. '(5)3-(5)2-(5)0'
     * @return normal readable tabs. For example '(5)3-(5)2-(5)0' would be: <br>
     * --------- <br>
     * --------- <br>
     * --------- <br>
     * --------- <br>
     * -3--2--0- <br>
     * --------- <br>
     */
    public static String newDisplay(String oldDisplay) {
        String output = "";
        String[] draws = new String[oldDisplay.split("-").length];
        int ind = 0;
        for (String t : oldDisplay.split("-")) {
            draws[ind] = draw(t);
            ind++;
        }
        for (int i = 0; i < draws[0].split("\n").length; i++) {
            for (int j = 0; j < draws.length; j++)
                output += draws[j].split("\n")[i];
            output += "\n";
        }
        return output;
    }

    /**
     * This method gets called from {@link FirstWindowController#displayTabs()} <br>
     * It returns a String of tabs or a String of an error message saying that this cannot be played on a classical guitar.
     * @param notes ArrayList of notes
     * @return String of tabs if all notes are valid, invalid message otherwise.
     */
    public static String play(ArrayList<String> notes) {
        String output = null;
        if (checkGuitarValidation(notes)) {
            String[] oldDisplayArray = new String[notes.size()];
            int ind = 0;
            for (String note : notes) {
                // for now only first possible position of a given note is obtained
                // TODO: algorithm which can choose the most optimal position of a note.
                oldDisplayArray[ind] = positions.get(note)[0];
                ind++;
            }
            String oldDisplay = String.join("-", oldDisplayArray);
            output = newDisplay(oldDisplay);
            return output;
        } else {
            return String.format("IMPOSSIBLE TO PLAY ON A GUITAR\n" +
                               "(there is too low or too high note)");
        }
    }

}
