import java.io.*;
import java.util.*;

public class Program {

    private static final String DICTIONARY = "./dictionary.txt";

    public static void main(String[] args) {

        if (args.length != 2) {
            return;
        }

        List <String> firstFileWords;
        List <String> secondFileWords;
        TreeSet<String> dictionary = new TreeSet<>();

        firstFileWords = addList(args[0], dictionary);
        secondFileWords = addList(args[1], dictionary);
        readDict(dictionary);

        List <Integer> firstWordsEntries;
        List <Integer> secondWordsEntries;

        firstWordsEntries = countEntries(firstFileWords, dictionary);
        secondWordsEntries = countEntries(secondFileWords, dictionary);

        double res = countFrequency(firstWordsEntries, secondWordsEntries);
        System.out.printf("Similarity = %.2f", res);
    }

    public static double countFrequency (List <Integer> firstWordsEntries,  List <Integer> secondWordsEntries) {

        double result = -1;

        if (firstWordsEntries.size() == secondWordsEntries.size())
        {
            double numerator = 0;
            double denumerator = 1;
            double tmp1 = 0;
            double tmp2 = 0;

            for (int i = 0; i < firstWordsEntries.size() ; i++) {
                numerator += firstWordsEntries.get(i) * secondWordsEntries.get(i);
                tmp1 += Math.pow(firstWordsEntries.get(i), 2) ;
                tmp2 += Math.pow(secondWordsEntries.get(i), 2);
            }

            denumerator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
            result = numerator / denumerator;
        }
        return result;
    }

    public static List <Integer> countEntries(List <String> words, TreeSet<String> dictionary) {

        List <Integer> entries = new ArrayList<>();

        for (String dic : dictionary) {
            int i = 0;
            for (String word : words) {
                if (dic.equals(word)) {
                    i++;
                }
            }
            entries.add(i);
        }
        return entries;
    }

    public static void readDict(TreeSet<String> dictionary) {
        try (BufferedWriter bw = new BufferedWriter (new FileWriter(DICTIONARY))) {
            for (String str : dictionary) {
                bw.write(str);
                if (!dictionary.last().equals(str))
                    bw.write(" ");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static List <String> addList(String file, TreeSet<String> dictionary) {

        List <String> fileWords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader (new FileReader(file))) {
            String tmp;
            while ((tmp = br.readLine()) != null) {
                for (String str :tmp.split(" ")) {
                    fileWords.add(str);
                    dictionary.add(str);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileWords;
    }
}
