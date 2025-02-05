import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Demarco Guajardo (dag0047@auburn.edu)
 */
public class Doublets implements WordLadderGame {

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   
   /////////////////////////////////////////////////////////////////////////////
   // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
   // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
   // PURPOSE OR YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
   // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
   // table with chaining).
   /////////////////////////////////////////////////////////////////////////////
   
   
   private SortedSet<String> lexicon = new TreeSet<>();


   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    */
   public Doublets(InputStream in) {
      try {
         //////////////////////////////////////
         // INSTANTIATE lexicon OBJECT HERE  //
         //////////////////////////////////////
         
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next().toLowerCase();
            /////////////////////////////////////////////////////////////
            // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
            /////////////////////////////////////////////////////////////
            lexicon.add(str);
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }


   //////////////////////////////////////////////////////////////
   // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
   //////////////////////////////////////////////////////////////
   
   /**
    * Returns the total number of words in the current lexicon.
    *
    * @return number of words in the lexicon
    */
   public int getWordCount() {
      return lexicon.size();
   }


   /**
    * Checks to see if the given string is a word.
    *
    * @param  str the string to check
    * @return     true if str is a word, false otherwise
    */
   public boolean isWord(String str) {
      return lexicon.contains(str.toLowerCase());
   }


   /**
    * Returns the Hamming distance between two strings, str1 and str2. The
    * Hamming distance between two strings of equal length is defined as the
    * number of positions at which the corresponding symbols are different. The
    * Hamming distance is undefined if the strings have different length, and
    * this method returns -1 in that case. See the following link for
    * reference: https://en.wikipedia.org/wiki/Hamming_distance
    *
    * @param  str1 the first string
    * @param  str2 the second string
    * @return      the Hamming distance between str1 and str2 if they are the
    *                  same length, -1 otherwise
    */
   public int getHammingDistance(String str1, String str2) {
   
      String word1 = str1.toLowerCase();
      String word2 = str2.toLowerCase();
      
      if (word1.length() != word2.length()) {
         return -1;
      }
      
      int hammingDistance = 0;
      
      //Increase distance if letter's don't match at specific index.
      for (int i = 0; i < word1.length(); i++) {
         if (word1.charAt(i) != word2.charAt(i)) {
            hammingDistance++;
         }
      }  
      
      return hammingDistance;
      
      
   }


   /**
    * Returns all the words that have a Hamming distance of one relative to the
    * given word.
    *
    * @param  word the given word
    * @return      the neighbors of the given word
    */
   public List<String> getNeighbors(String word) {
   
      List<String> neighbors = new ArrayList<>();
      
      word = word.toLowerCase();
      
      //If word in lexicon has same Hamming Distance as parameter, then it is a neighbor.
      for (String lexWord : lexicon) {
         if (getHammingDistance(lexWord, word) == 1) {
            neighbors.add(lexWord);
         }
      }
      
      return neighbors;
   }


   /**
    * Checks to see if the given sequence of strings is a valid word ladder.
    *
    * @param  sequence the given sequence of strings
    * @return          true if the given sequence is a valid word ladder,
    *                       false otherwise
    */
   public boolean isWordLadder(List<String> sequence) {
      
     //  //There must be at least a start and end word in a word ladder.
      // if (sequence.size() < 2) {
         // return false;
      // }
      
      if (sequence.size() < 1) {
         return false;
      }
      
      String start = sequence.get(0).toLowerCase();
      String end = sequence.get(sequence.size() - 1).toLowerCase();
      
      //Checking if start and end words are in lexicon.
      if (!isWord(start) || !isWord(end)) {
         return false;
      }
      
      //Checking to make sure rest of the words are valid words.
      for (String wordInSequence : sequence) {
         String word = wordInSequence.toLowerCase();
         if (!isWord(word)) {
            return false;
         }
      }
      
      //Making sure that words next to each other have the same Hamming Distance (of 1 for this game).
      for (int i = 0; i < sequence.size() - 1; i++) {
         if (getHammingDistance(sequence.get(i), sequence.get(i+1)) != 1) {
            return false;
         }
      }  
      
      return true;
   }


  /**
   * Returns a minimum-length word ladder from start to end. If multiple
   * minimum-length word ladders exist, no guarantee is made regarding which
   * one is returned. If no word ladder exists, this method returns an empty
   * list.
   *
   * Breadth-first search must be used in all implementing classes.
   *
   * @param  start  the starting word
   * @param  end    the ending word
   * @return        a minimum length word ladder from start to end
   */
   public List<String> getMinLadder(String start, String end) {
   
      start = start.toLowerCase();
      end = end.toLowerCase();
   
      //Making sure start and end are both valid words.
      if (!isWord(start) || !isWord(end)) {
         return new ArrayList<String>();
      }
      
      if (start.equals(end)) {
         return Arrays.asList(start);
      }
      
      //Using BFS to search for shortest ladder.
      Deque<Node> queue = new ArrayDeque<Node>();
      
      HashSet<String> visited = new HashSet<String>();
      
      //Setting up nodes, starting with the start word.
      Node startAsNode = new Node(start, null);
      
      //Same as enqueue, but works for nodes.
      queue.offer(startAsNode);
      
      //Marking the start as visited. 
      visited.add(start);
      
      while (!queue.isEmpty()) {
         //Current starts as the starting Node. 
         Node current = queue.poll();
         
         //Going through every neighbor to find end word.
         for (String neighbor : getNeighbors(current.word)) {
            //Making sure to skip all neighbors that have already been visited.
            if (!visited.contains(neighbor)) {
               //Making neighbor word as a node.
               Node neighborAsNode = new Node(neighbor, current);
               //If neighbor matches end word, we need to build the ladder in between.
               if (neighbor.equals(end)) {
                  List<String> ladder = new LinkedList<String>();
                  
                  Node n = neighborAsNode;
                  
                  while (n != null) {
                     //Adding words in reverse order from end to start.
                     ladder.add(0, n.word);
                     //Making the word to add the word before the one just added.
                     n = n.prev;
                  }  
                  return ladder;
               }
               //Mark neighbor as visited before moving to next neighbor.
               visited.add(neighbor);
               //Adding the neighbor node to the queue.
               queue.offer(neighborAsNode);
            }
         }
         
      }
      
      return new ArrayList<String>();
      
      
   }  
   
   /**
    * Class representing a node-linked structure.
    */
   private static class Node {
      
      String word;
      Node prev;
      
      public Node(String word, Node prev) {
         this.word = word;
         this.prev = prev;
      }
      
   }
}