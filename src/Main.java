import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static String filename = "imdb-data";
    public static void main(String[] args) {
        createDLLfile();
        createDMLfile();
    }

    public static void createDMLfile(){

        ArrayList<String> data = getDataInArrays();
        createFile("dml");
        writeToDML(data);

    }

    public static void createDLLfile(){
        String[] headers = readHeader();
        String test = createSQL(headers);
        System.out.println(test);
        createFile("dll");
        writeToDDL(test);
    }

    private static String createSQL(String[] headers){
        String returnString = "CREATE TABEL "+ filename + " (\n";

        for (int i = 0; i < headers.length; i++) {
            returnString += i == headers.length - 1 ? headers[i] + " varchar(255)\n": headers[i] + " varchar(255),\n";
        }
        returnString += ");";

        return returnString;
    }

    public static String[] readHeader(){
        File f = new File("resources/" + filename + ".csv");
        try {
            Scanner sc = new Scanner(f);
            String[] header = sc.nextLine().split(";");

            return header;
        }
        catch (Exception e){

        }
        return null;
    }

    public static void createFile(String file){
        File f = new File("resources/" + file + ".sql");
        try {


            if (f.createNewFile()) {
                System.out.println("File created");
            }
            else {
                System.out.println("File NOT created");
            }
        }
        catch (Exception e){
        }
    }

    public static void writeToDDL(String content){
        try {
            FileWriter myWriter = new FileWriter("resources/dll.sql");
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (Exception e) {
            System.out.println("didnt write to file");

        }
    }

    public static void writeToDML(ArrayList<String> content){
        try {
            FileWriter myWriter = new FileWriter("resources/dml.sql");
            for (int i = 0; i < content.size(); i++) {
                if(i == content.size() - 1){
                    myWriter.write(content.get(i));
                }
                else {
                    myWriter.write(content.get(i) + "\n");
                }
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (Exception e) {
            System.out.println("didnt write to file");

        }
    }


    public static ArrayList<String> getDataInArrays(){
        ArrayList<String> datalist = new ArrayList<String>();
        File f = new File("resources/" + filename + ".csv");
        try {
            Scanner sc = new Scanner(f);
            sc.nextLine();
            while (sc.hasNextLine()){

                String data = "Insert into " + filename;
                data +=" VALUES(" + sc.nextLine().replace(';', ',') + ");";
                datalist.add(data);
            }
        }
        catch (Exception e){

        }
        return datalist;
    }

}
