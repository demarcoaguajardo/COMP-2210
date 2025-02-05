import java.util.Arrays;


/**
 * Autocomplete.
 */
public class Autocomplete {

   private Term[] terms;

	/**
	 * Initializes a data structure from the given array of terms.
	 * This method throws a NullPointerException if terms is null.
	 */
   public Autocomplete(Term[] terms) {
      if (terms == null) {
         throw new NullPointerException();
      }
      
      terms = Arrays.copyOf(terms, terms.length);
      
      Arrays.sort(terms);
   }

	/** 
	 * Returns all terms that start with the given prefix, in descending order of weight. 
	 * This method throws a NullPointerException if prefix is null.
	 */
   public Term[] allMatches(String prefix) {
      if (prefix == null) {
         throw new NullPointerException();
      }
      
      Term termOfPrefix = new Term(prefix, 0); // Create the prefix term as the key
                                               // when searching the given prefix (weight doesn't matter).
      
      
      // Creating the range of spots in arrays.
      int firstIndex = BinarySearch.firstIndexOf(terms, termOfPrefix, Term.byPrefixOrder(prefix.length())); 
      if (firstIndex == -1) {
         Term[] nothing = new Term[0];
         return nothing;
      }
      
      int lastIndex = BinarySearch.lastIndexOf(terms, termOfPrefix, Term.byPrefixOrder(prefix.length())); 
      if (lastIndex == -1) {
         Term[] nothing = new Term[0];
         return nothing;
      }
      
      // Creating the array of matches
      Term[] matchingPs = Arrays.copyOfRange(terms, firstIndex, lastIndex + 1);
      
      // Sorting array in descending order of weight
      Arrays.sort(matchingPs, Term.byDescendingWeightOrder());
      
      
      return matchingPs;
   }

}

