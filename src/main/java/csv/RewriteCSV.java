package csv;

import entity.Field;
import entity.Record;
import environment.Setting;
import io.FileReader;
import io.FileWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 23:20 2018/3/30
 */
public class RewriteCSV {

    public static void main(String[] arg) {
        RewriteCSV rewriteCSV = new RewriteCSV();
        rewriteCSV.readFile();

    }

    public void readFile() {
        Setting setting = new Setting();
        FileWriter fileWriter = new FileWriter("data.csv");
        FileReader fileReader = new FileReader("BUSINESS_NAMES_201803.csv");
        String line = "";
        while (line != null) {
            try {
                line = fileReader.getNextLine();
                if (line == null)
                    continue;
                List<String> list = initializeRecord(line); // Split line
                if (list != null) {
                    line = "";
                    for (String content : list) {
                        if (content.contains("\"") || content.contains(","))
                            content = fix(content);
                        line += content + ",";
                    }
                    line = line.substring(0, line.length() - 1) + "\r\n";
                    fileWriter.writeToFile(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String fix(String content) {
        boolean comma = false;
        boolean quote = false;
        boolean firstQuote = false;
        if (content.contains(","))
            comma = true;
        String list[] = content.split("\"");
        if (list[0].length() == 0)
            firstQuote = true;
        if (list.length > 1)
            quote = true;

        String newContent = "";
        for (String l : list) {
            if (l.length() > 0)
                newContent += l + "\"\"";
        }
        newContent = newContent.substring(0, newContent.length() - 2);

        if (firstQuote) {
            newContent = "\"\"\"" + newContent + "\"\"\"";
            return newContent;
        }
        if (quote || comma)
            newContent = "\"" + newContent + "\"";
        return newContent;

    }

    private List<String> initializeRecord(String line) throws Exception {
        List<String> list;
        ArrayList<Field> fieldList = new ArrayList<Field>();
        String separator = Setting.INPUT_FILE_SEPARATOR; //separator of input file
        String[] filedString = line.split(separator);

        if (filedString.length != 9) {
            if (filedString.length < 9) { //using null string to fill the missing field
                String[] fieldStringFixed = new String[9];
                System.arraycopy(filedString, 0, fieldStringFixed, 0, filedString.length);
                for (int i = filedString.length; i < 9; i++)
                    fieldStringFixed[i] = "";
                filedString = fieldStringFixed;
            } else
                return null; // abandon if field is much than 9
        }

        if (filedString[8] == "" || filedString[8] == null)
            filedString[8] = "0";

        list = Arrays.asList(filedString);
        return list;
    }

}
