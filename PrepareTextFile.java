import java.util.*;
import java.io.*;
import java.text.*;

public class PrepareTextFile
{
    public static void main(String args[]) throws IOException
    {
        System.out.println("Starting..");
        File dir = new File("C:/Users/winton/Desktop/Excel Upload/PrepareText/output.txt");
        Scanner scanner = new Scanner(dir);
        File FILENAME = new File("C:/Users/winton/Desktop/Excel Upload/output2.txt");
        FILENAME.createNewFile();
        FileWriter fw = new FileWriter(FILENAME, true);
	BufferedWriter bw = new BufferedWriter(fw);
        while (scanner.hasNextLine()){
            String name = scanner.nextLine();
            System.out.println("name: " + name);
            Read read = new Read("C:/Users/winton/Desktop/Excel Upload/PrepareText/Sales_Reports/"+name);
            Print print = new Print();
            //name = name.replace(" SalesDetails", "");
            name = name.replaceAll(" ", "_");
            name = name.replaceAll("[,]", "");
            try {
                print.setFileName("C:/Users/winton/Desktop/Excel Upload/Prepared_Files/"+name);
                bw.write(name);
                bw.newLine();
                read.setPrint(print);
                read.exec(1,name);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        fw.flush();
        bw.close();
        fw.close();
        scanner.close();
        System.out.println("Finished");
    }
}

class Print {
    protected String     fileName;
    protected FileWriter writer;
    
    public Print() { }
    
    public Print(String fileName) throws IOException {
        this.fileName = fileName;
        this.writer = new FileWriter(this.fileName);
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) throws IOException {
        this.fileName = fileName;
        this.writer = new FileWriter(this.fileName);
    }
    
    public void close() throws IOException {
        this.writer.flush();
        this.writer.close();
    }
    
    public void printLine(String c) throws IOException {
        this.writer.write(c);
        this.writer.write('\n');
    }
}

class Read {
    protected String fileName;
    protected BufferedReader bufferedReader;
    protected Print print;
    String words2;
    String stage;
    
    public Read(String fileName) {
        this.fileName = fileName;
    }
    
    public Read(Print print, String fileName) {
        this.print = print;
        this.fileName = fileName;
    }
    
    public void setPrint(Print print) {
        this.print = print;
    }
    
    public void exec(Integer type,String name) {
        String sCurrentLine = "";
        String delims = "[ ]+";
        name = name.replaceAll(".txt", "");
        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.fileName));
            int co = 0;
            while (co < 5){
                sCurrentLine = this.bufferedReader.readLine();
                //System.out.println(co + ":" + sCurrentLine);
                co++;
            }
            //sCurrentLine = this.bufferedReader.readLine();
            sCurrentLine = this.bufferedReader.readLine().trim();
            sCurrentLine += " Account_Name" + " Stage";
            //System.out.println(co++ + ":" + sCurrentLine);
            this.print.printLine(sCurrentLine);
            words2 = "";
            while ((sCurrentLine = this.bufferedReader.readLine()) != null) {    
                String words = sCurrentLine.replaceAll("^\\s", "");
                //words = words.replaceAll("[,]", "");
                int length = words.length();
                words = parse(words,length,name);
                if (words.equals("") || length < 5)
                    continue;
                else 
                    this.print.printLine(words);
            }
            this.print.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (this.bufferedReader != null) {
                    this.bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public String parse(String words, int length, String name) {
        stage = "Closed_Won";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        /*Date date = new Date();
        //System.out.println("corrected");
        try{ 
            date = sdf.parse(sdf.format(date));
            System.out.println("date = " + date);
        }catch (ParseException pe){
            pe.printStackTrace();
        }*/
        if (length > 2 && length <= 50) {
            words2 = words;
            words2 = words2.replaceAll("[ ]", "_");
            //System.out.println(words2);
            words = "";
        }
        
        if (words.contains("Name") || !words.contains("-") || words.contains(":"))
            words = "";
                    
        if (words.contains(",") || words.contains("-")){ 
            words = words.replaceAll("[,]", "");
            //String w3 = words.replaceAll("^\\d", "");
            //System.out.println("w3 = " + w3);
            //words = words.replaceAll("^\\d", "");
            words += " " + name + " " + stage;// + " " + date;
            words = words2 + words;
            //System.out.println(words);
        }
           
        if(words.contains("PM") || words.contains("AM")) {
            words = "";
        }
        
        return words;
    }  
}