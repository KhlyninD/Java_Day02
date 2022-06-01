import java.util.*;
import java.io.*;

public class Program {

    private static final String SIGNATURES = "./signatures.txt";
    private static final String RESULT = "./result.txt";

    public static void main(String[] args) {
        Map<String, String> signaturesMap;

        signaturesMap = getSignaturesMap();

        Scanner scanner = new Scanner(System.in);

        String fileName = scanner.nextLine();

        while (!fileName.equals("42")) {
            reedFileIn(signaturesMap, fileName);
            fileName = scanner.nextLine();
        }
    }

    public static void reedFileIn(Map<String, String> signaturesMap, String fileName) {

        try(FileInputStream fin = new FileInputStream(fileName))
        {
            int readByte;
            int maxLenSig = maxLen(signaturesMap);
            int i = 0;
            StringBuilder data = new StringBuilder();
            while((readByte = fin.read()) != -1 && i < maxLenSig){
                if (Integer.toHexString(readByte).length() < 2) {
                    data.append("0");
                }
                data.append(Integer.toHexString(readByte) + " ");
                i++;
            }
            searchKey(signaturesMap, data.toString());
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void searchKey(Map<String, String> signaturesMap, String data) {
        for (String key : signaturesMap.keySet()) {
            if (data.startsWith(key)) {
                System.out.println("PROCESSED");
                writeResult(signaturesMap.get(key));
                return;
            }
        }

        System.out.println("UNDEFINED");
    }

    public static void writeResult(String value) {

        try(FileOutputStream fout = new FileOutputStream(RESULT, true)) {
            byte[] buffer = (value + "\n").getBytes();
            fout.write(buffer,0,buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int maxLen(Map<String, String> signaturesMap) {

        int maxLen = 0;

        for (String str : signaturesMap.keySet()) {
            maxLen = str.length() > maxLen ? str.length() : maxLen;
        }
        return  maxLen;
    }

    public static Map<String, String> getSignaturesMap() {

        Map<String, String> signaturesMap = new HashMap<>();

        StringBuilder fileSig = new StringBuilder();

        try(FileInputStream fin = new FileInputStream(SIGNATURES))
        {
            int readByte;
            while((readByte = fin.read()) != -1){
                fileSig.append((char) readByte);
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }

        String[] massFileSig = fileSig.toString().split("\n");

        for (String str: massFileSig) {
            String[] tmp = str.split(",");
            signaturesMap.put(tmp[1].toLowerCase().trim(), tmp[0].trim());
        }
        return signaturesMap;
    }

}
