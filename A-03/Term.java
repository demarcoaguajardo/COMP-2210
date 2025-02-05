import java.util.Comparator;

/**
 * Autocomplete term representing a (query, weight) pair.
 * 
 */
public class Term implements Comparable<Term> {

   private String query;
   private long weight;
   
   /**
    * Initialize a term with the given query and weight.
    * This method throws a NullPointerException if query is null,
    * and an IllegalArgumentException if weight is negative.
    */
   public Term(String queryIn, long weightIn) {
      
      if (queryIn == null) {
         throw new NullPointerException();
      }
      if (weightIn < 0) {
         throw new IllegalArgumentException();
      }
     
      query = queryIn;
      weight = weightIn;
     
   }

   /**
    * Compares the two terms in descending order of weight.
    */
   public static Comparator<Term> byDescendingWeightOrder() {
      return 
         new Comparator<Term>() {
            public int compare(Term t1, Term t2) {
               if (t1.getWeight() < t2.getWeight()) {
                  return 1;
               }
               if (t1.getWeight() > t2.getWeight()) {
                  return -1;
               }
               else {
                  return 0;
               }
            }
         };
   }

   /**
    * Compares the two terms in ascending lexicographic order of query,
    * but using only the first length characters of query. This method
    * throws an IllegalArgumentException if length is less than or equal
    * to zero.
    */
   public static Comparator<Term> byPrefixOrder(int length) {
      if (length <= 0) {
         throw new IllegalArgumentException();
      }
      
      return 
         new Comparator<Term>() {
            public int compare(Term t1, Term t2) {
               String prefixOfT1 = t1.getQuery().substring(0, Math.min(length, t1.getQuery().length())); 
               String prefixOfT2 = t2.getQuery().substring(0, Math.min(length, t2.getQuery().length()));
               return prefixOfT1.compareTo(prefixOfT2);
            }
         };
   }

   /**
    * Compares this term with the other term in ascending lexicographic order
    * of query.
    */
   @Override
   public int compareTo(Term other) {
      if (other == null || this == null) {
         throw new NullPointerException();
      }
      return this.getQuery().compareTo(other.getQuery());
   }

   /**
    * Returns a string representation of this term in the following format:
    * query followed by a tab followed by weight
    */
   @Override
   public String toString(){
      return query + "\t" + weight;
   }
   
   /**
    * Returns a long of the term's weight.
    */
   private long getWeight() {
      return weight; 
   }
   
   /**
    * Returns a string of the term's query.
    */ 
   private String getQuery() {
      return query;
   }

}

