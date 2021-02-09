import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observer;
import java.util.Scanner;
//Adam Cunningham
//starts up the controller and the model by reading a file
//this allows a view to create an mvc, and pass itself to the model
//without ever having a reference to the model in its class
public class MVC{
	public CryptogramController ctrl;
	
	public MVC(Observer obs) {
	    //get quote from file
	    ArrayList<String> quoteList = getQuoteArray("quotes.txt");
	    String quote = getRandomQuote(quoteList); 
	    
	    //init game with quote
	    CryptogramModel game = new CryptogramModel(quote);
	    
	    ctrl = new CryptogramController(game);
	    ctrl.setObserver(obs);
	}
	
	public CryptogramController getCtrl() {
		return ctrl;
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