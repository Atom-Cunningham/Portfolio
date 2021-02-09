import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.File;

/*
* cryptogram.java
* Author Adam Cunningham
* netID Laser
* CSC 335 project 1
* fall 2019
* 
* @version java 8
* 
* Cryptogram is a short game in which a line from
* a provided file is encrypted with a random encryption key.
* The file is hardcoded as quotes.txt.
* 
* This program depends on the game class, defined in game.java
* 
* Use: the user can enter a letter to be replaced, and a letter
* to replace. All instances of that letter to replace will be
* displayed above the encrypted text (as the replaced), as well
* as all previous guesses of other letters.
* 
* Note that only letters may be replaced, and they may only
* be replaced with letters. If a non-letter
* or a letter which is not in the encrypted message is chosen,
* the user will not see any change, and will be reprompted
* 
* All letters are converted to uppercase.
* 
* When the text displayed above the encryption is
* identical to the unecrypted text (the original line)
* a victory message is printed, and the program should be closed.
*
* For more information on the program, see README.md
*/

public class Cryptogram{
 static CryptogramModel game;
    
    /**
     * Main exists purely for the purpose of allowing the user which interface to use
     * type -text in the cmd line to run the outdated version
     */
    public static void main(String[] args){
        if (args.length > 0 && args[0].equals("-text")) {
        	CryptogramTextView CTV = new CryptogramTextView();
        }else {
        	CryptogramGUIView view = new CryptogramGUIView();
        	javafx.application.Application.launch(CryptogramGUIView.class, args);
        }
    }


    /**
     * attempts to open a file
     * creates a scanner to read the file
     * or closes the program if file not found
     * @param filename the file to be opened
     * @return a list of the quotes in the file
     * 
     */
    public static ArrayList<String> getQuoteArray(String filename){
        //getQuoteArray takes a string filename, 
        //it tries to read the file, 
        //and add each line to a list
        //exits if file not found or list is empty
        //it returns a list representation of the lines in the file
        ArrayList<String> quoteList = new ArrayList<String>();

        try{
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNext()){
                quoteList.add(scanner.nextLine());
            }
            scanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println(e);
            System.exit(1);
        }
        if (quoteList.size() == 0){     //if list is empty
            System.exit(1);
        }
        return quoteList;
    }


    /**
     * gets a random string from a list of strings
     * @param quoteList a list of quotes read from a file
     * @return one of the quotes from the file at random
     */
    public static String getRandomQuote(ArrayList<String> quoteList){
        //takes an arrayList of strings
        //shuffles the list
        //returns the first element in the list

        Collections.shuffle(quoteList);
        return quoteList.get(0).toUpperCase();
    }



}