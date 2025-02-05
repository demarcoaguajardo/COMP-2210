import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.SortedSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

/**
 * Game engine that builds a functioning game of word search.
 * @author Demarco Guajardo (dag0047@auburn.edu)
 */
public class WordSearchEngine implements WordSearchGame {

   //Fields

   // Creating the lexicon structure to store words.
   private SortedSet<String> lexicon = new TreeSet<>();
   private String[][] board;
   private Map<String, Boolean> memoizedWords;


   //Methods 

   /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
   
      //Creating a scanner.
      try (Scanner scan = new Scanner(new File(fileName))) {
         while (scan.hasNextLine()) {
            String line = scan.nextLine().toUpperCase().trim();
            String[] words = line.split("\\s+"); // split line into words
            for (String word : words) {
               lexicon.add(word);
            }
         }
      } catch (FileNotFoundException exception) {
         throw new IllegalArgumentException();
      }
   }


   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    * game board in row-major order. Thus, index 0 stores the contents of board
    * position (0,0) and index length-1 stores the contents of board position
    * (N-1,N-1). Note that the board must be square and that the strings inside
    * may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is not square.
    */
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
   
      //checking to see if board is square.
      int size = (int) Math.sqrt(letterArray.length);
      if (size * size != letterArray.length) {
         throw new IllegalArgumentException(); //board is not square.
      }
   
      //Creating board. 
      board = new String[size][size];
   
      //Adding values from letterArray into board.
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            //adds each value into each column before moving on to next row.
            board[i][j] = letterArray[i * size + j];
         }
      }
   }

    /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
   public String getBoard() {
      String output = "";
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            output += board[i][j] + " ";
         }
         output += "\n"; //new line per row.
      }
      return output;
   }

   /**
    * Retrieves all scorable words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllScorableWords(int minimumWordLength) {
      if (lexicon.isEmpty()) {
         throw new IllegalStateException();
      }
      
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
   
      //creating TreeSet of scorable words.
      SortedSet<String> scorableWords = new TreeSet<>();
   
      //returning empty set if min word length is greater than length of largest word in lexicon.
      int maxWordLength = lexicon.last().length();
      if (minimumWordLength > maxWordLength) {
         return scorableWords;
      }
      
      memoizedWords = new HashMap<>();
   
      //looping through each cell of the board.
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            //making a visited variable starting with top left cell and performing dfs.
            boolean[][] visited = new boolean[board.length][board[0].length];
            dfs(i, j, "", visited, scorableWords, minimumWordLength);
         }
      }
    
   
      return scorableWords;
   
   }

   /**
    * Depth-First-Search method that starts at the given cell, which recursively
    * searches for words and then adds them into the scorableWords TreeSet if they
    * meet the length requirement.
    * @param row is the row of the current cell.
    * @param col is the column of the current cell.
    * @param visited to determine what has been visited or not.
    * @param scorableWords is the TreeSet where we put scorable words.
    */
   private void dfs(int row, int col, String word, boolean[][] visited, 
       SortedSet<String> scorableWords, int minWordLength) {
     
     //Checking to make sure the rows and columns are in-bounds.
      if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || visited[row][col]) {
         return;
      }  
       
      //Marking the cell as visited (true = visited).
      visited[row][col] = true;
   
      //Adding the cell's letter to the current word.
      String fullWord = word + board[row][col];
   
      //Check if the full word meets min length requirement.
      if (fullWord.length() >= minWordLength) {
        //Check if the full word is in the lexicon.
         if (lexicon.contains(fullWord)) {
            //Add the full word to the set of scorable words.
            scorableWords.add(fullWord);
         }
        //Memoize the word.
         memoizedWords.put(fullWord, lexicon.contains(fullWord));
      }
      
      //Checking if prefix is in lexicon; if not, skipping.
      if (isPrefix(fullWord)) {
         //Recursively call dfs on all neighboring cells.
         int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
         int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};
         for (int i = 0; i < 8; i++) {
            dfs(row + rowOffsets[i], col + colOffsets[i], fullWord, visited, scorableWords, minWordLength);
         }  
      } 
      
    //Resetting the cell as unvisited.
      visited[row][col] = false;
   }

   private boolean isPrefix(String prefix) {
    //Check if prefix is in lexicon or if there is a word in the lexicon that starts with the prefix
      return lexicon.contains(prefix) || !lexicon.subSet(prefix, prefix + Character.MAX_VALUE).isEmpty();
   }
   
   /**
   * Computes the cummulative score for the scorable words in the given set.
   * To be scorable, a word must (1) have at least the minimum number of characters,
   * (2) be in the lexicon, and (3) be on the board. Each scorable word is
   * awarded one point for the minimum number of characters, and one point for 
   * each character beyond the minimum number.
   *
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */  
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
   
      if (lexicon == null) {
         throw new IllegalStateException();
      }
   
      SortedSet<String> scorableWords = getAllScorableWords(minimumWordLength);
   
      int total = 0;
   
      for (String word : words) {
         if (scorableWords.contains(word)) {
            int score = 1 + Math.max(0, word.length() - minimumWordLength); 
            total += score;
         }
      }
      return total;
   }

   /**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
   
      if (lexicon == null) {
         throw new IllegalStateException();
      }
   
      //Returns the boolean of whether the TreeSet contains the word.
      return lexicon.contains(wordToCheck.toUpperCase());
   }

   /**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
   
      if (lexicon == null) {
         throw new IllegalArgumentException();
      }
   
      //Creates tailSet using prefix which has all words that equal or come after
      //the prefix in alphabetical order.
      SortedSet<String> tailSet = lexicon.tailSet(prefixToCheck.toUpperCase());
   
      if (tailSet.isEmpty()) {
         return false; //There are no words in the lexicon with the prefix. 
      }
   
      //Creating an instance of the first string in the tailSet to see if starts with
      //the prefix. 
      String firstString = tailSet.first();
   
      //Returns boolean to see if the first string starts with the prefix. If it does,
      //that means that there is a word with the given prefix.
      return firstString.startsWith(prefixToCheck.toUpperCase());
      
   }

   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the path
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
   
      boolean[][] visited = new boolean[board.length][board.length];
   
      List<Integer> path = new ArrayList<>();
   
      //Looping through each cell to find the wordToCheck starting at the first cell
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board.length; j++) {
            //checking if the cell matches the first letter to keep searching for
            //remaining letters.
            if (board[i][j].equals(Character.toString(wordToCheck.charAt(0)))) {
               //searching for the word at that position.
               if (search(wordToCheck, visited, i, j, path)) {
                  return path;
               }
            }
         }
      }
      return new ArrayList<Integer>();
   }

   /**
    * Search method that recursively searches for the wordToCheck starting at the specified
    * cell.
    * @param wordToCheck is the word to search for.
    * @param visited is the boolean value determining whether or not the cell has been visited.
    * @param row is the row of the cell.
    * @param col is the column of the cell.
    * @param path A list of integers that show the path to find the word.
    * @return true if word is found; false if not.
    */
   private boolean search(String wordToCheck, boolean[][] visited, int row, int col, 
       List<Integer> path) {
      //if word to check has length of 0, return true
      if (wordToCheck.length() == 0) {
         return true;
      }
   
      //returning false if cell has been visited already or if cell isn't on board
      if (row < 0 || col < 0 || row >= board.length || col >= board.length || visited[row][col]) {
         return false;
      }
   
      //returning false if cell's letter does not match first letter of word.
      if (!board[row][col].equals(Character.toString(wordToCheck.charAt(0)))) {
         return false;
      }
   
      //adding cell to the path and marking as visited.
      int position = row * board.length + col; //position is 0 at top left and N^2 - 1 at bottom right.
      path.add(position);
      visited[row][col] = true;
   
      //Searching for neighbor cells using recursive approach.
      boolean found = search(wordToCheck.substring(1), visited, row + 1, col, path) ||
                     search(wordToCheck.substring(1), visited, row + 1, col + 1, path) ||
                     search(wordToCheck.substring(1), visited, row + 1, col - 1, path) ||
                     search(wordToCheck.substring(1), visited, row - 1, col, path) ||
                     search(wordToCheck.substring(1), visited, row - 1, col - 1, path) ||
                     search(wordToCheck.substring(1), visited, row - 1, col + 1, path) ||
                     search(wordToCheck.substring(1), visited, row, col + 1, path) ||
                     search(wordToCheck.substring(1), visited, row, col - 1, path);
   
      //If word can't be found in neighboring cells, go back.
      if (!found) {
         visited[row][col] = false;
         path.remove(path.size() - 1);
      }
   
      return found;
   }
   
   public static void main (String[] args) {
      WordSearchGame game = WordSearchGameFactory.createGame();
      game.loadLexicon("words_medium.txt");
      game.setBoard(new String[]{"O","Y","D","D","T","P","N","R","A","H","E","L","C","S","B","P","S","U","B","G","U","P","Y","H","R","R","X","R","E","F","H","D","H","T","K","X","K","O","Z","F","W","Y","H","Y","T","C","H","M","V","P","R","T","A","K","N","E","S","I","B","T","M","V","Y","Q","E","U","O","E","F","A","K","J","C","W","I","K","I","U","K","T","P","O","F","E","G","Z","T","X","O","Z","T","H","K","B","M","G","D","P","P","P","G","U","E","S","C","J","C","B","Q","F","T","R","I","P","N","I","E","W","P","K","H","K","G","B","B","L","Y","J","P","J","E","O","N","Q","V","N","B","S","H","R","N","Z","R","G","A","E","W","P","L","L","Z","R","G","I","E","T","U","N","R","L","I","K","T","J","K","J","F","C","I","T","M","R","D","T","R","E","G","L","J","G","I","K","H","L","C","V","P","P","D","S","Q","E","W","O","C","R","L","V","L","P","T","A","T","N","O","R","M","W","K","O","D","O","U","O","V","F","M","H","V","V","S","I","X","Z","L","O","T","Z","L","B","R","G","F","Q","P","A","Y","P","D","L","B","K","S","N","C","H","O","P","Y","K","H","C","R","R","I","C","S","B","J","X","R","F","I","Y","R","H","B","Z","I","P","C","K","I","N","O","E","C","C","U","C","P","I","J","R","E","Y","E","Z","U","R","R","M","F","S","M","R","N","J","I","B","T","Q","O","C","V","R","O","T","X","H","C","R","W","S","A","V","T","N","U","I","O","W","X","C","O","R","X","Q","A","S","A","S","S","E","M","B","L","Y","O","Z","F","P","L","S","C","I","T","L","U","M","O","N","I","T","O","R","J","W","I","N","L","L","L","E","L","J","R","R","E","M","M","O","B","D","X","I","J","D","S","R","L","C","H","S","H","Y","U","L","P","M","O","U","S","E","C","B","I","I","U","I"});
      System.out.print("LENT is on the board at the following positions: ");
      System.out.println(game.isOnBoard("LENT"));
      System.out.print("POPE is not on the board: ");
      System.out.println(game.isOnBoard("POPE"));
      System.out.println("All words of length 10 or more: ");
      System.out.println(game.getAllScorableWords(10));
   }
   
}