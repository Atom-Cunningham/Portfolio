import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;
import java.util.Scanner;

/*
 * @author Adam Cunningham
 * netID: Laser
 * @version java 8
 * @purpose The CryptogramController class is an object which handles CryptogramController
 * it takes user input, and executes functions in the game class and therefore
 * depends on the game class to function
 * takes input from the cmd line and sends relevant
 * data to be stored in the game class
 * 
 * @purpose handles the damage from the user input
 * and uses data from game to provide output
 */
//!!! handles user input
public class CryptogramController{
	Scanner input;
	CryptogramModel game;
	/**
	 * ctrl manages user input and resulting output
	 * @param game is a Game type object which stores the data and manages it
	 * 
	 */
	public CryptogramController(CryptogramModel game){
		this.game = game;
	}
	
	public String getEncrypted() {
		return game.getEncrypted();
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
    
    /**
	 * Depends on the instantiation of a game class
	 * updates the guess Hashmap in the game class
     * @param x the key
     * @param y the value
     */
    public void replace(char xkey, char yval) {
    	//takes two characters
    	//updates the game guessmap
            game.addGuess(xkey, yval);
    }
    public HashMap<Character, Character> getGuessMap() {
    	return game.getGuessMap();
    }
    
    public ArrayList<Character> getAlphabet(){
    	return game.getAlphabet();
    }
    

    
	/**
	 *if the string quote belonging to the CryptogramModelclass
	 *is the same as the decrypted encrypted quote]
	 *then the user has won!
	 * @return a boolean. True if they guessed all the letters
	 * else false
	 */
    public boolean isOver() {
    	return game.getGuess().equals(game.getQuote());
    }    

    public String getHintString() {
    	return(game.getHintString());
    }
    
    public void setNewHint() {
    	//updates the model with a newly generated hint
    	String hint = getHintString();
    	assert hint.length() == 2;
    	
    	char answ = hint.charAt(0);
    	char encry = hint.charAt(1);
    	replace(encry, answ);
    }
    
    public String getHelpString() {
    	return game.getHelpString();
    }
    
    public void exit() {
    	//possible functionality, save games
    	System.exit(0);
    }

	public HashMap<Character, Integer> getCountMap() {
		return game.getLetterCount();
	}

	public String getGuess() {
		return game.getGuess();
	}

	public ArrayList<char[]> getEncryptedArray(Integer lineLen) {
		//returns a list of strings such that they can wrap around without breaking
		ArrayList<char[]> lines = game.getLines(game.getEncrypted(),lineLen);
		return lines;
	}
	public ArrayList<char[]> getGuessArray(Integer lineLen) {
		//guess version of get encryptedArray
		ArrayList<char[]> lines = game.getLines(//calculates current encryption string
				game.getGuess(game.getEncrypted().toCharArray()),lineLen);//converts to char array
																		  //converts to current guess
																		  //as an array
		return lines;
	}
	
	public Integer getLineCount(Integer lineLen) {
		//constructs an array of length equal to the number of lines passed by view
		Integer rows = getEncryptedArray(lineLen).size();
		return rows;
	}

	public void setObserver(Observer observer) {
		game.setObserver(observer);
		
	}
	
	

}