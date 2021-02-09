import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

//Adam Cunningham
//this class is an outdated version of the game
//it is much harder to interface with, but this program is backwards compatible
public class CryptogramTextView implements Observer{
	public CryptogramController ctrl;
	public Integer width = 80;
	
	public CryptogramTextView() {
		ctrl = new MVC(this).getCtrl();
        initMsg();					//print startup msg
        
    	Scanner scanner = new Scanner(System.in);
        while (!ctrl.isOver()){			//while current guess is not the quote
            System.out.print("Enter a command (help to see commands): ");
            String input = scanner.nextLine();
        	getInput(input);		        	//get input and update game
        	printOutput(width);
        }
        System.out.println("\nYou got it!\n```");	//win
        scanner.close();
	}
	
    public void initMsg() {
    	initMsg(ctrl.getEncrypted(), width);
    }

    /**
     * a startup message, intended for a fencepost whileLoop
     * @param init a string which prints info for the user
	 *  before asking for a guess
     * @param lineLen an int which determines wraparound
     */
    public void initMsg(String init, int lineLen) {
    	System.out.print("```\n");
    	while (init.length() > lineLen-1) {
    		init = streamChars(init, lineLen);
    	}
    	System.out.println(init);
    }
    
	
    public void printOutput(int lineLen){
    	//takes an integer as the maximum line length
    	//gets the guessString and the encrypted String from game
    	String guess = ctrl.getGuess();
    	String encr  = ctrl.getEncrypted();
    	
    	//while the strings are > lineLen chars
    	//looks backwards for the most recent instance of " "
    	int end;
    	while(guess.length() > lineLen-1) {
    		end = guess.lastIndexOf(" ", lineLen);
    		guess = streamChars(guess, end);
    		encr  = streamChars(encr,  end);
    		System.out.println();
    	}
    	System.out.println(guess);
    	System.out.println(encr);
    }
    
    /**
	 * prints n characters and returns the remaining string
	 * if the string was greater than n length
     * @param str the string to be printed
     * @param n the length of the printed string
     * @return the remaining portion of the string
     */
    public String streamChars(String str, int n) {
    	//prints n characters from a string
    	//returns the remaining string
		System.out.println(str.substring(0,n));
		
    	return str.substring(n);
    }
    
    
    
    public Integer getWidth() {
    	return width;
    }
    
    public Integer setWidth(int w) {
    	width = w;
    	return width;
    }
    /**
	 * calls the overloaded replace(x,y)
	 * this function is called when the
	 * user inputs a guess type command
	 * and cleans the data
     * @param str the string to be parsed for
	 * the key, value pair
     */
    //TODO use update
    public void replace(String str) {
    	String[] strArray = str.split(" ");
    	ArrayList<Character> charArray = new ArrayList<>();
    	for (String s : strArray) {
    		s = s.toUpperCase();
    		charArray.add(s.charAt(0));
    	}
    	
    	char x = ' ';
    	char y = ' ';
    	if (strArray.length == 4
    	&&	strArray[0].equals("replace")
    	&&	strArray[2].equals("by")){
    		x = charArray.get(1);
    		y = charArray.get(3);
    	}
    	else if (strArray.length == 3
    	&&       strArray[1].equals("=")) {
    		x = charArray.get(0);
    		y = charArray.get(2);
    	}
        if (isAlpha(x) && isAlpha(y)){//A-Z only
        	ctrl.replace(x,y);
        }
    }
    
    /**
     * first line of defense against user input
     * checks for equivalency for most cmds
     * @param command is a string, which is validated and determines output
     */
    public void getInput(String command){
        //gets input from the user to decide what needs to be replaced
        //cleans the input
        //passes the first characters of the input to the game class
        //the game class updates the map from alphabetical chars to guesses
        if (command.contains("replace") 
        ||  command.contains("=")){
        	replace(command);
        }else if(command.equals("freq")) {
        	showFrequency(ctrl.getCountMap());
        }else if(command.equals("hint")) {
        	printHint(ctrl.getHintString());
        }else if(command.equals("help")) {
        	help();
        }else if(command.equals("exit")) {
        	ctrl.exit();
    	}else {
        	System.out.println("\t bad command, type \"help\"");
        }
        
        System.out.println();
    }
    
    
    /**
     * calls getFrequencyString
	 * and prints the string
     */
    public void showFrequency(HashMap<Character, Integer> count) {
    	//when printing, use the index of the 
    	//next space starting at the minimum length
    	String freq = getFrequencyString(count);
    	System.out.println(freq);
    }
    
    /**
	 * provides a character count string
	 * depending on the game class
     * @return a string which maps an alphabet
	 * to the count of each char, 7 characters
	 * per line
     */
    public String getFrequencyString(HashMap<Character, Integer> count) {
    	//TODO support wraparound for superlarge strings
    	//gets a mapping of chars to count of each char
    	//gets the alphabet from game
    	//if a letter is in the alphabet, it is added with its
    	//count to a string
    	ArrayList<Character> alphabet = ctrl.getAlphabet();
    	String currString  = "";
    	String countString = "";
    	for (int i = 0; i < alphabet.size(); i++) {
    		if (countString.length() == 0) {
    			currString = "";
    		}
    		else {currString = " ";}
    		
    		//countString += ("%c: %d", letter, count)
    		Character c = alphabet.get(i);
    		currString += c;
    		currString += ": ";
    		if(count.containsKey(c)) {
    			currString += count.get(c);
    		}else {
    			currString += 0;
    		}
    		
    		//every seven letters print a newline
    		if (i!=0 && i%7==0) {
    			currString += '\n';
    		}
    		
    		countString += currString;
    	}
    	return countString;
    }
    
    /**
     * provides a list of acceptable commands to the user
     */
    public void help() {
    	//display all options
    	//copied exactly from the spec
    	//wraparound is hardcoded as 80 max chars
    	String help = ctrl.getHelpString();
    	while (help.length() > 81) {
    		help = streamChars(help, 81);
    	}System.out.println(help);
    }
    
    public void printHint(String ae) {
    	//ae is an answer char and an encryption char at 0 and 1 idx
    	assert ae.length() == 2;
    	System.out.println(ae.charAt(0) +" is encrypted as " + ae.charAt(1) + "\r\n");
    }
    
    /**
	 * equivalent to Character.isAlpha(c) && Character.isUpper(c)
     * @param c char
     * @return true if c is uppercase care, else false
     */
    public boolean isAlpha(char c){
        //evaluates whether or not a character is uppercase alphabetical
        return ('A' <= c && c <='Z');
    }

	@Override
	public void update(Observable o, Object arg) {
		//The structure in main here seems fine enough
	}
    
}