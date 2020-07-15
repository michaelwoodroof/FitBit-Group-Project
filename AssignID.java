import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// This Class ensures that an ID cannot be Assigned Twice

public class AssignID {

    private int currentID;

    public void AssignID() {
        this.setID(1);
    }

    public int getID() {
        return this.currentID;
    }

    public void setID(int id) {
        this.currentID = id;
    }

    public void iterate() {
        this.currentID++;
    }

    public void exportFile() {
        // Saves Latest ID Reference
        String content = Integer.toString(currentID);
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter("accounts/currentID.txt");
            bw = new BufferedWriter(fw);

            bw.write(content);

            // Close Files
            bw.close();
            fw.close();

        } catch (IOException e) {
            // Handle
            System.out.println("Cannot Export File");
        }


    }

    public void importFile() {
        // Get next Avaliable Reference
        BufferedReader br = null;
        FileReader fr = null;

        // Check File Exists First
        File File = new File("accounts/currentID.txt");

        if (File.exists()) {
            try {
                fr = new FileReader("accounts/currentID.txt");
                br = new BufferedReader(fr);

                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    currentID = Integer.parseInt(currentLine);
                }

                // CLose Files
                br.close();
                fr.close();

            } catch (IOException e) {
                // Handle
                System.out.println("Cannot Import File");
            }
        } else {
            this.exportFile();
        }

    }


}
