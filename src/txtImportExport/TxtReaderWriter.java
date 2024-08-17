package txtImportExport;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class takes care of writing and reading txt files
 */
public class TxtReaderWriter {

    /**
     * Retrieves the content of a text file located in the same path.
     * Each line of the file is stored as a separate string in a list.
     *
     * @param nameOfFileWithEnding the name of the file (including the file extension)
     * @return the content of the text file as a list of strings
     * @throws FileNotFoundException if the file specified by the name does not exist
     */
    public static List<String> getTxtFromSamePath(String nameOfFileWithEnding) throws FileNotFoundException {
        List<String> txtContent = new ArrayList<>();
        File myObj = new File(nameOfFileWithEnding);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            //Save content line by line
            txtContent.add(myReader.nextLine());
        }
        myReader.close();
        return txtContent;
    }

    /**
     * Writes the content of a list of strings to a file.
     *
     * @param nameOfFileWithEnding the name of the file (including the file extension) to write to
     * @param contentList the list of strings to write to the file
     * @throws IOException if an I/O error occurs while writing the file
     */
    public static void writeListOfStrings(String nameOfFileWithEnding, List<String> contentList) throws IOException {
        FileWriter fw = new FileWriter("./" + nameOfFileWithEnding, StandardCharsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(fw);
        for (String line : contentList) {
            writer.append(line);
            writer.newLine();
        }
        writer.close();
        System.out.println("The File '" + nameOfFileWithEnding + "' was saved in the same folder");
    }
}