import java.util.Arrays;
import java.util.Comparator;

/**
 * Binary search.
 */
public class BinarySearch {

   /**
    * Returns the index of the first key in a[] that equals the search key, 
    * or -1 if no such key exists. This method throws a NullPointerException
    * if any parameter is null.
    */
   public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
      if (a == null || key == null || comparator == null) {
         throw new NullPointerException();
      }
      
      int left = 0; // left most index of bound
      int right = a.length - 1; // right most index of bound
      
      while (left <= right) {
      
         int middle = left + (right - left) / 2; // declaring middle value of bound
         
         // if middle value is greater than or equal to key, right side is now left of middle.
         if (comparator.compare(a[middle], key) >= 0) { 
            right = middle - 1;
         }
         
         // if middle value is less than or equal to key, left side is now right of middle.
         else {
            left = middle + 1;
         }
      }
      
      if (left < a.length && comparator.compare(a[left], key) == 0) {
         return left;
      }
      return -1;
   }

   /**
    * Returns the index of the last key in a[] that equals the search key, 
    * or -1 if no such key exists. This method throws a NullPointerException
    * if any parameter is null.
    */
   public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
      if (a == null || key == null || comparator == null) {
         throw new NullPointerException();
      }
      
      int left = 0; // left most index of bound
      int right = a.length - 1; // right most index of bound
      
      while (left <= right) {
      
         int middle = left + (right - left) / 2; // declaring middle value of bound
         
         // if key is greater than or equal to middle value, left side is now right of middle.
         if (comparator.compare(key, a[middle]) >= 0) { 
            left = middle + 1;
         }
         
         // if key is greater than or equal to middle value, right side is now left of middle.
         else {
            right = middle - 1;
         }
      }
      
      if (right < a.length && comparator.compare(key, a[right]) == 0) {
         return right;
      }
         
      return -1;
   }

}
