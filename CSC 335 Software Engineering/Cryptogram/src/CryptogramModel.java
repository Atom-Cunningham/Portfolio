import java.util.Collections;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

/*
* Author Adam Cunningham
* NetID Laser
* CryptogramModel.java
* csc 335 fall 2019
* project 1
* 
* @version java 8
* CryptogramModel is a class used by cryptogram.java
* This will perform the following tasks in the Cryptogram class.
*    1. Store the random encryption key
*    2. Encrypt the quotation
*    3. Store the player's decryption mappings
*    4. Decrypt only those letters you have entered a guess for 
* 
* This uses Hashmaps to store an encription key
* mapping A-Z to (A-Z)shuffled with collections lib
* and to store letters changed by the user for display
* mapping A-Z to " " by default, updated to letters as the
* CryptogramModel is played.
* 
* This takes a quote passed from cryptogram.
* 
* All letters are uppercase.
*/


//!!!!! handles rules of CryptogramModel
public class CryptogramModel extends Observable{
    public HashMap<Character, Character> encryptionMap;          //maps A-Z to randomized A-Z
    public HashMap<Character, Character> guessMap;               //maps from encr to guess
    public ArrayList<Character> alphabet;
    public String quote;					//the decrypted quote
    public String encrypted;				//the encrypted string
    public ArrayList<Character> marked;
    
    
    /**
     * instantiates an alphabet
     * and an irreflexive injective one to one
     * mapping to the set of that alphabet
     * 
     */
    CryptogramModel(String quote){
        //constructor method for CryptogramModel
        //takes a string, and encrypts it based on a randomized alphabet
    	this.quote = quote;
        setAlphabet();      //populate alphabet array
        setEncryptionMap(); //create the encryption key
        setGuessMap();      //initialize guessMap as alphabet to ' ' keys
        String encry = getEncrypted(quote.toCharArray()); //get encrypted string
        encrypted = encry;
        
        marked = new ArrayList<Character>();
        
    }
    
    public void setObserver(Observer o) {
    	addObserver(o);
    }

    /**
     * creates an array of all 26 capitol english letters
     */
    public void setAlphabet(){
        //sets the alphabet Arraylist to contain uppercase english alphabet
        alphabet = new ArrayList<>();
        for (Character c = 'A'; c <= 'Z'; c++){
            alphabet.add(c);
        }
    }

    /**
     * uses the alphabet generated from setAlphabet
     * and returns an arraylist which is an equal set
     * but of different ordering
     * @return a shuffled alphabet made of the characters in
     * this.alphabet
     */
    public ArrayList<Character> getShuffled() {
    	//getShuffled uses the alphabet to generate a list
    	//of equal size, containing all the same members
    	//in a different order
    	//returns a shuffled version of the alphabet for use
    	
        //randomly generate shuffled alphabet
        ArrayList<Character> shuffled = new ArrayList<Character>();
        while (shuffled.size() < alphabet.size()) { 
        	//Collections.copy requires same cardinality
        	shuffled.add(' ');
        	}
        
        Collections.copy(shuffled, alphabet);
        Collections.shuffle(shuffled);
        return shuffled;
    	
    }
    
    /**
     * Takes two arraylists. Ensures that !(arraylistA[x] == arralistB[x])
     * for any given inex x. Arraylists a,b need to be ordered sets
     * @see wikipedia irreflexive sets
     * @param a an ordered set
     * @param b an ordered set with the same members as a
     * @return an ordered set such that the index of any member is not
     * equal to the index of itself in set a
     */
    public ArrayList<Character> makeIrreflexive(ArrayList<Character> a,
    											 ArrayList<Character> b) {
    	//takes two lists of characters
    	//both lists should have the same set of members
    	//if any member of both sets has the same index
    	//the member is swapped to a new index
    	//returns a list (b), which has members that do not have
    	//the same index as their sister members in list a
    	
    	//warning! requires alphabet members to be unique!
    	assert a.size()==b.size();
    	assert a.size() > 1;
    	
    	int newIndex = 0;
    	for (int i = 0; i < a.size(); i++) {
    		if (a.get(i).equals(b.get(i))) {
    			//swap with next char (or first char if end of array)
    			newIndex = i + 1;
    			if (newIndex == a.size()) {//dodge OOBerror
    				newIndex = 0;
    			}
    			Collections.swap(b, i, newIndex);
    		}
    	}
    	return b;
    }
    
    /**
     * Maps an ordered alphabet to a shuffled alphabet
     * such that no char is mapped to itself
     */
    public void setEncryptionMap(){
        //uses a Character array containing all upperCase english letters
        //creates a copy of the array, shuffles, and iterates to create a map
    	//the map is such that no character is mapped to itself
        //sets the encryptionMap field

    	ArrayList<Character> shuffled = getShuffled();//generate dealphabatized
    	shuffled = makeIrreflexive(alphabet, shuffled);//ensure irreflexivity
    	
        //populate map with encryption key
        encryptionMap = new HashMap<Character,Character>();
        for (int i = 0; i < alphabet.size(); i++){
            encryptionMap.put(alphabet.get(i), shuffled.get(i));
        }
    }

    /**
     * translates a character array, given a map of chars to chars
     * @param message the string (char[]) to be encrypted
     * @param map an encoding such that the message char[]s members
     * will be replaced with a different letter based on the value
     * @return the encoded string
     */
    public String convert(char[] message,
                         HashMap<Character, Character> map){
        //takes a map and a char[] as parameters
        //iterates for char keys and replaces keys
        //in the messageArray with map values
        //converts back to a string
        //returns the string

        //for characters in message
        //replace with encoded letter
    	//@Throws keyvalue error  
        
        //converts the string to a charArray
    	//char[] messageArray = message.toCharArray(); TODO somewhere else
        for (int i = 0; i < message.length; i++){
        	char c = message[i];
        	if (Character.isLetter(c)) {
        		c = Character.toUpperCase(c);
        		message[i] = map.get(message[i]);
        	}
            
        }
        return new String(message);
    }
    
    /**
     * Takes a string and counts each instance of each character
     * creates a map of char:countOf char
     * @param str the string to have its chars counted
     * @return a map of chars to the count of each char in the string
     */
    public HashMap<Character, Integer> getLetterCount(){
    	//takes an encrypted text
    	//counts each instance of each character in the encryption
    	//returns a map which stores the chars and counts
    	
    	HashMap<Character, Integer> count = new HashMap<Character, Integer>();
    	for (int i = 0; i < encrypted.length(); i++) {
    		//if there isnt a mapping for the char yet, make it 0
    		//otherwise update the count
    		char c = encrypted.charAt(i);		//tally the instances of each letter
    		if (!count.containsKey(c)) {
    			count.put(c, 1);
    		}else {
    			count.put(c, count.get(c)+1);
    		}
    	}
    	for (char letter: alphabet) {//identify the remaining alphabet and assign 0
    		if (!count.containsKey(letter)) {
    			count.put(letter, 0);
    		}
    	}
    	return count;
    }
    
    /**
     * 
     */
    public void setGuessMap(){
        //initialize guessMap
        //populate with alphabet to ' ' Characters
    	
        guessMap = new HashMap<Character,Character>();
        for (int i = 0; i < alphabet.size(); i++){
            guessMap.put(alphabet.get(i), ' ');
        }
    }
    public HashMap<Character, Character> getGuessMap() {
    	return this.guessMap;
    }

    /**
     * depends on a Game object
	 * parses the current guessString
	 * alongside the encrypted message
	 * and returns the first incorrectly guessed
	 * letter with its correct mapping
     */
    public String getHintString() {
    	//gets the unencrypted message
    	//gets the guessed message
    	//loops through them to find the first non-matching letter

    	//displays a useful hint
    	//which always applies to the encryption
    	//(not pulled randomly from the map)
    	String quote = getQuote();
      	String guess = getGuess();
      	String encrypted = getEncrypted();
    	
    	char quoteChar = '\0';
    	char encrChar  = '\0';
    	String str;
    	for (int i = 0; i < guess.length(); i++) {
    		if (guess.charAt(i) != quote.charAt(i)){
    			  quoteChar = quote.charAt(i);
    			  encrChar  = encrypted.charAt(i);
    			  return ("" + quoteChar + encrChar);
    		}
    	}
    	return null; //game is over, should never happen
    	
    }
    /**
     * updates the HashMap guessMap with a k,v pair
     * @param key a character to update
     * @param value a value to set or update given the key
     */
    public void addGuess(Character key, Character value){
        //updates the guessMap
    	//TODO, make sure its 1 to 1, keys can share targets
    	
    	char oldval = guessMap.get(key);//save old values
    	char oldkey = 0;
    	for (char c : guessMap.keySet()) {//iterate through keys
    		if (value == guessMap.get(c)) {//if another key had this value
    			oldkey  = guessMap.get(c);	//remove it
    			guessMap.remove(oldkey);
    	
    		}
    	}
    	guessMap.put(key, value);			//update Map
    	    	
    	setChanged();
        notifyObservers();
       	
         
    }

    /**
     * uses the CryptogramModel guessMap field to create a string
     * @param message the message to be translated
     * @return the current guessString to be displayed
     */
    public String getGuess(char[] message){
        //takes a string to be decrypted
        //uses the guessMap to replace all letters guessed so far
        //returns the string generated by guesses
        return convert(message, guessMap);
    }
    /**
     * an overloaded method
     * calls getGuess with the encryptedString as a default
     * @return the current guess of the user
     */
    public String getGuess() {
    	//overloaded getGuess
    	//throws null if this.encrypted not set
    	return getGuess(encrypted.toCharArray());
    }

    /**
     * given the CryptogramModels mapping, changes the chars
     * of the passed char[] given their mapped encoding
     * @param message the char[] to be encoded
     * @return the encoded String
     */
    public String getEncrypted(char[] message){
        //takes a string to be encrypted
        //uses the encryption key to convert the string to it's encrypted form
        //returns the string
        return convert(message, encryptionMap);
    }
    
    /**
     * takes a char and returns the char its mapped to
     * given the CryptogramModels encryptionMap
     * @param c the char to be encoded
     * @return the encoded char
     */
    public Character getEncrypted(Character c) {
    	//takes a character to be encrypted
    	//returns the encrypted char
    	return encryptionMap.get(c);
    }
    
    //throws nullpointer
    /**
     * returns the encrypted message, so long as the
     * message to be encoded and the CryptogramModels encoding has been set
     * otherwise, a nullpointer may be returned from encrypted
     * @return String the encrypted version of a CryptogramModel set method
     * @throws nullPointerException if CryptogramModel fields were not properly
     * set
     */

    public String getEncrypted() {
    	return encrypted;
    }
    

    
    /**
     * forcibly sets a string, advised that the
     * encrypted field is set given encryption methods
     * @param str the string to set as the current encryption
     */
    public void setEncrypted(String str) {
    	//sets the encrypted field in CryptogramModel to str
    	encrypted = str;
    }
    
    /**
     * forcibly sets the string which is 42
     * @param str sets passed str as the answer to the question
     */
    public void setQuote(String str) {
    	//sets the quote field to the given string
    	quote = str;
    }
    
    /**
     * getter for the string to be decrypted
     * @return the decoded quote
     */
    public String getQuote() {
    	return quote;
    }
    /**
     * returns the array of the defined alphabet in order as an ArrayList
     * @return an ArrayList of characters
     */
    public ArrayList<Character> getAlphabet(){
    	return alphabet;
    }
    
    public String getHelpString() {
    	String help = "a.       replace X by Y �"
    			+ " replace letter X by letter Y in our"
    			+ " attempted solution\r\n" + 
    			"\r\n" + 
    			"X = Y � a shortcut for this same command\r\n" + 
    			"\r\n" + 
    			"b.       freq � Display the letter frequencies"
    			+ " in the encrypted quotation (i.e., "
    			+ "how many of letter X appear) like:\r\n" + 
    			"\r\n" + 
    			"A: 3 B: 8 C:4  D: 0 E: 12 F: 4 G: 6\r\n" + 
    			"(and so on, 7 per line for 4 lines)\r\n" + 
    			"\r\n" + 
    			" \r\n" + 
    			"\r\n" + 
    			"c.       hint � display one correct mapping"
    			+ " that has not yet been guessed\r\n" + 
    			"\r\n" + 
    			"d.       exit � Ends the game early\r\n" + 
    			"\r\n" + 
    			"e.       help � List these commands";
    	return help;
    }

	public ArrayList<char[]> getLines(String str, Integer lineLen) {
	    //returns a list of character arrays which are broken at max lineLen on whitespace
	    	//takes an integer as the maximum line length
	    	//gets the guessString and the encrypted String from game
	    	ArrayList<char[]> lines = new ArrayList<char[]>();	    	
	    	//while the strings are > lineLen chars
	    	//looks backwards for the most recent instance of " "
	    	int end;
	    	while(str.length() > lineLen-1) {
	    		end = str.lastIndexOf(" ", lineLen);
	    		str  = addChars(str ,  end, lines);
	    	}
	    	lines.add(str.toCharArray());
	    	
	    	return lines;
	}
	public String addChars(String str, int end, ArrayList<char[]> lines) {
		lines.add(str.substring(0,end).toCharArray());
		return str.substring(end+1);
	}


}






