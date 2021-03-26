import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class StringManipulator {

    private static final String STOPWORDSLOCATION = "stopwords.txt";
    private static final String WORDARRAYLOCATION = "wordArray.txt";


    public static void main(String[] args) {
        for(String input: args){
            printTopWordsInFile(new File(input));
        }
    }

    public static void printTopWordsInFile(File fileToBeProcessed) {

            ArrayList<String> wordArray = new ArrayList<>();
            try {
                Scanner scanner = new Scanner(fileToBeProcessed);
                while (scanner.hasNext()) {
                    wordArray.add(scanner.next());
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            wordArray = stopWordRemover(wordArray);
            wordArray = removeNonAlphabeticalChars(wordArray);
            wordArray = stemWords(wordArray);
            wordArray = countWords(wordArray);

            for(int i = 0; i < 20; i++){
                      System.out.println(wordArray.get(i));
            }
    }


    private static ArrayList<String> stopWordRemover(ArrayList<String> wordArray) {
        ArrayList<String> stopWordList = new ArrayList<>();
        ArrayList<String> wordListWithoutStopWords = new ArrayList<>();
        //this was done to keep stopwords.txt in jar as a resource without calling from non-static context
        Scanner scanner = new Scanner(StringManipulator.class.getResource(STOPWORDSLOCATION).getFile());
        while (scanner.hasNext()) {
            stopWordList.add(scanner.next());
        }
        scanner.close();
        for (String word : wordArray) {
            if (!stopWordList.contains(word)) {
                wordListWithoutStopWords.add(word);
            }
        }
        return wordListWithoutStopWords;
    }

    private static ArrayList<String> stemWords(ArrayList<String> wordArray){
        List<String> stemmedWordArray = new ArrayList<>();
        try {
            FileOutputStream writeData = new FileOutputStream(WORDARRAYLOCATION);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
            for (String currentString : wordArray) {
                writeStream.writeBytes(currentString+ " ");
            }
            writeStream.flush();
            writeStream.close();

            PrintStream consoleStream = System.out;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);

            Stemmer.main(new String[]{WORDARRAYLOCATION});

            System.out.flush();
            System.setOut(consoleStream);

            outputStream.close();
            stemmedWordArray = Arrays.asList(outputStream.toString().split(" "));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(stemmedWordArray);

    }

    private static ArrayList<String> removeNonAlphabeticalChars(ArrayList<String> wordArray) {
        ArrayList<String> alphabeticalCharsOnlyArray = new ArrayList<>();
        for (String word : wordArray) {
            alphabeticalCharsOnlyArray.add(word.replaceAll( "[^a-zA-Z]", ""));
        }
        return alphabeticalCharsOnlyArray;
    }

    private static ArrayList<String> countWords(ArrayList<String> wordArray){
        HashMap<String,Integer> wordCount = new HashMap<>();
        ArrayList<String> descendingList = new ArrayList<>();
        for(String word: wordArray){
            if(wordCount.containsKey(word)){
                wordCount.put(word, wordCount.get(word)+1);
            }else{
                wordCount.put(word, 1);
            }
        }
        while(wordCount.size() > 0) {
            int maxValue = 0;
            String maxKey = null;
            for (String currentKey : wordCount.keySet()) {
                int currentKeyValue = wordCount.get(currentKey);
                if (currentKeyValue > maxValue) {
                    maxValue = currentKeyValue;
                    maxKey = currentKey;
                }
            }
            if(!maxKey.isBlank()){
            descendingList.add(maxKey);
            }
            wordCount.remove(maxKey);
        }
        return descendingList;
    }
}
