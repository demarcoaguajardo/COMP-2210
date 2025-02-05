import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;



// import java.util.Arrays; 
// (imported just to test methods, hence why it's commented out) 

   
/**
 * Defines a library of selection methods on Collections.
 *
 * @author  Demarco Guajardo (dag0047@auburn.edu)
 *
 */
public final class Selector {

/**
* Can't instantiate this class.
*
* D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
*
*/
   private Selector() { }
   
   
 // TEST DRIVER METHOD (I replaced "floor" and corresponding parameters
 // with all methods to test each individual one)
 
   // public static void main(String[] args) {
      // Collection<Integer> numbers = Arrays.asList(8, 2, 8, 7, 3, 3, 4);
      // Comparator<Integer> comparator = Integer::compare;
      // System.out.print(floor(numbers, 10, comparator));
   // }



   /**
    * Returns the minimum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the minimum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T min(Collection<T> coll, Comparator<T> comp) {
      if (comp == null || coll == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      } 
      
      Iterator<T> itr = coll.iterator();
      
      T min = itr.next();
      
      while (itr.hasNext()) {
         T newT = itr.next();
         if (comp.compare(newT, min) < 0) {
            min = newT;
         }
      } 
      return min;
   }

   /**
    * Selects the maximum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the maximum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T max(Collection<T> coll, Comparator<T> comp) {
      if (comp == null || coll == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      } 
      
      Iterator<T> itr = coll.iterator();
      
      T max = itr.next();
      
      while (itr.hasNext()) {
         T newT = itr.next();
         if (comp.compare(newT, max) > 0) {
            max = newT;
         }
      } 
      return max;
   }


   /**
    * Selects the kth minimum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth minimum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth minimum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T kmin(Collection<T> coll, int k, Comparator<T> comp) {
      if (comp == null || coll == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty() || k <= 0 || k > coll.size()) {
         throw new NoSuchElementException();
      } 
      
      // Creating new list to store only unique values.
      
      Collection<T> collCopy = new ArrayList<>();
      for (T num : coll) {
         if (!collCopy.contains(num)) {
            collCopy.add(num);
         }
      }
      
      // Making the ArrayList as a list of type T.
      
      List<T> collFinal = new ArrayList<>(collCopy);
      
      // Sorting the final list.
      
      java.util.Collections.sort(collFinal, comp);
      
      if (k > collFinal.size()) {
         throw new NoSuchElementException();
      }
      
      if (collFinal.size() < 2) {
         return collFinal.get(0);
      }
   
      
      return collFinal.get(k - 1);
   
   
   
      
   }


   /**
    * Selects the kth maximum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth maximum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth maximum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T kmax(Collection<T> coll, int k, Comparator<T> comp) {
      if (comp == null || coll == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty() || k <= 0 || k > coll.size()) {
         throw new NoSuchElementException();
      } 
      
      // Creating new list to store only unique values.
      
      Collection<T> collCopy = new ArrayList<>();
      for (T num : coll) {
         if (!collCopy.contains(num)) {
            collCopy.add(num);
         }
      }
      
      // Making the ArrayList as a list of type T.
      
      List<T> collFinal = new ArrayList<>(collCopy);
      
      // Sorting the final list.
      
      java.util.Collections.sort(collFinal, comp);
      
      if (k > collFinal.size()) {
         throw new NoSuchElementException();
      }
      
      if (collFinal.size() < 2) {
         return collFinal.get(0);
      }
   
      
      return collFinal.get(collFinal.size() - k);
   }


   /**
    * Returns a new Collection containing all the values in the Collection coll
    * that are greater than or equal to low and less than or equal to high, as
    * defined by the Comparator comp. The returned collection must contain only
    * these values and no others. The values low and high themselves do not have
    * to be in coll. Any duplicate values that are in coll must also be in the
    * returned Collection. If no values in coll fall into the specified range or
    * if coll is empty, this method throws a NoSuchElementException. If either
    * coll or comp is null, this method throws an IllegalArgumentException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the range values are selected
    * @param low     the lower bound of the range
    * @param high    the upper bound of the range
    * @param comp    the Comparator that defines the total order on T
    * @return        a Collection of values between low and high
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> Collection<T> range(Collection<T> coll, T low, T high,
                                                     Comparator<T> comp) {
      if (comp == null || coll == null) {
         throw new IllegalArgumentException();
      }
      
      Collection<T> rangeValues = new ArrayList<>();
      for (T val : coll) {
         if (comp.compare(val, low) >= 0 && comp.compare(val, high) <= 0) {
            rangeValues.add(val);
         }
      }
      
      if (rangeValues.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      return rangeValues;
      
   }


   /**
    * Returns the smallest value in the Collection coll that is greater than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the ceiling value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the ceiling value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T ceiling(Collection<T> coll, T key, Comparator<T> comp) {
      if (comp == null || coll == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      boolean isValid = false;
      for (T num : coll) {
         if (comp.compare(num, key) >= 0) {
            isValid = true;
            break;
         }
      }
      if (!isValid) {
         throw new NoSuchElementException();
      }
      
      Collection<T> ceilingValues = new ArrayList<>();
      
      for (T num : coll) {
         if (comp.compare(num, key) >= 0) {
            ceilingValues.add(num);
         }
      }
      
      return Selector.min(ceilingValues, comp);
   }


   /**
    * Returns the largest value in the Collection coll that is less than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the floor value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the floor value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T floor(Collection<T> coll, T key, Comparator<T> comp) {
      if (comp == null || coll == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      boolean isValid = false;
      for (T num : coll) {
         if (comp.compare(num, key) <= 0) {
            isValid = true;
            break;
         }
      }
      if (!isValid) {
         throw new NoSuchElementException();
      }
      
      Collection<T> floorValues = new ArrayList<>();
      
      for (T num : coll) {
         if (comp.compare(num, key) <= 0) {
            floorValues.add(num);
         }
      }
      
      return Selector.max(floorValues, comp);
   
   }

}
