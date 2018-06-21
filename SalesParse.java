import java.util.*;
import java.io.*;
import java.text.*;

public class SalesParse
{
    public static void main(String args[]) throws IOException
    {
        System.out.println("Starting..");
        File dir = new File("C:/Users/winton/Desktop/Excel Upload/output2.txt");
        Scanner scanner = new Scanner(dir);
        while (scanner.hasNextLine()){
            String name = scanner.nextLine();
            Read read = new Read("C:/Users/winton/Desktop/Excel Upload/Prepared_Files/"+name);
            Print print = new Print();      
            try {
                name = name.replaceAll("txt", "csv");
                print.setFileName("C:/Users/winton/Desktop/Excel Upload/Ready_To_Upload/"+name);        
                read.setPrint(print);
                read.exec(1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        scanner.close();
        /*if (dir.delete())
            System.out.println("output2 deleted");
        else
            System.out.println("output2 failed to delete");
        System.out.println("Finished");*/
                
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
    
    public void addRow(String[] c) throws IOException {
        int l = c.length;
        Date date = null;
        String oldFormat = "dd-MMM-yyyy";
        String newFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        for (int i=0;i<l;i++) {
            System.out.println(i+":"+c[i]);
            if (c[i].contains(oldFormat)){
                try {
                    date = sdf.parse(c[i]);
                    sdf.applyPattern(newFormat);
                    c[i] = sdf.format(date);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            this.writer.append(c[i]);
            if(i != (l-1)) {
                this.writer.append(",");
            }
        }
        this.writer.append('\n');
    }
}

class Read {
    protected String fileName;
    protected BufferedReader bufferedReader;
    protected Print print;
    
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
    
    public Print getPrint() {
        return this.print;
    }
    
    public void exec(Integer type) {
        String sCurrentLine = "";
        String delims = "[ ]+";
        int flag = 0;
        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.fileName));
            while ((sCurrentLine = this.bufferedReader.readLine()) != null) {
                String[] columns = sCurrentLine.split(delims);
                //columns = columns.trim();
                int length = columns.length;
                if(type == 1){
                    //columns = addElements(columns,flag);
                    this.print.addRow(columns);
                }
            }
            this.print.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(this.bufferedReader != null) {
                    this.bufferedReader.close();
                }
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    /*public String[] addElements(String[] columns, int flag) {
        if (columns[0].contains("Freight") || columns[0].contains("Pallet") || flag == 1){
            //flag = 1;
            //System.out.println("c[0] = " + columns[0]);
            //if (columns[0].length() == 0){
            
            if(columns.length > 12){
            System.out.println("length:"+columns.length);
                System.out.println("0:" + columns[0]);
                System.out.println("1:" + columns[1]);
                System.out.println("2:" + columns[2]);
                System.out.println("3:" + columns[3]);
                System.out.println("4:" + columns[4]);
                System.out.println("5:" + columns[5]);
                System.out.println("6:" + columns[6]);
                System.out.println("7:" + columns[7]);
                System.out.println("8:" + columns[8]);
                System.out.println("9:" + columns[9]);
                System.out.println("10:" + columns[10]);
                System.out.println("11:" + columns[11]);
                System.out.println("12:" + columns[12]);
                columns[0] = columns[1]+columns[2]+columns[3]+columns[4];
                columns[1] = columns[5];
                columns[2] = columns[6];
                columns[3] = columns[7];
                columns[4] = columns[8];
                columns[5] = columns[9];
                columns[6] = columns[10];
                columns[7] = columns[11];
                columns[8] = "0";
                columns[9] = "0";
                columns[10] = columns[12];
                columns = Arrays.copyOf(columns, columns.length-2);
                
            }
            else if(columns.length > 11){
            System.out.println("length2:"+columns.length);
                columns[0] = columns[1]+columns[2]+columns[3];
                columns[1] = columns[4];
                columns[2] = columns[5];
                columns[3] = columns[6];
                columns[4] = columns[7];
                columns[5] = columns[8];
                columns[6] = columns[9];
                columns[7] = columns[10];
                columns[8] = "0";
                columns[9] = "0";
                columns[10] = columns[11];
                columns = Arrays.copyOf(columns, columns.length-1);
            }
                String[] moreColumns = new String[11];
                System.arraycopy(columns, 0, moreColumns, 0, columns.length);
                columns = moreColumns;
                columns[10] = columns[8];
                columns[8] = "0";
                columns[9] = "0";
            //}
            //else {
            //    flag = 0;                    
            //}
       }
       return columns;
   }*/

}