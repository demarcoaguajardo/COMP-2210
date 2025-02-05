import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Demarco Guajardo (dag0047@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  01/24/23
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


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int min(int[] a) throws IllegalArgumentException{
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      int min = a[0];
      int i;
      
      for (i = 0; i < a.length; i++) {
         if (a[i] < min) {
            min = a[i];
         }
      }
      return min; 
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int max(int[] a) throws IllegalArgumentException{
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      int max = a[0];
      int i;
      
      for (i = 0; i < a.length; i++) {
         if (a[i] > max) {
            max = a[i];
         }
      }
      return max; 
   }


   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) throws IllegalArgumentException{ 
      
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length < 0) {
         throw new IllegalArgumentException();
      }
      if (k <= 0) {
         throw new IllegalArgumentException();
      }
      if (k > a.length) {
         throw new IllegalArgumentException();
      }
   
      // Creates a new array to put unique values in.
      int[] aNew = new int[a.length];
      int aNewIndex = 0;
      
      int i;
      
      int[] aCopy = Arrays.copyOf(a, a.length);
      Arrays.sort(aCopy);
      int current = aCopy[0];
      aNew[aNewIndex] = current;
      aNewIndex++;
      
      for (i = 1; i < aCopy.length; i++) {
         if (aCopy[i] != current) {
            current = aCopy[i];
            aNew[aNewIndex] = current;
            aNewIndex++;
         }
      }
      
      int[] finalArray = Arrays.copyOf(aNew, aNewIndex);
      Arrays.sort(finalArray);
      
      if (k > finalArray.length) {
         throw new IllegalArgumentException();
      }
      
      if (finalArray.length < 2) {
         return finalArray[0];
      }
      
      return finalArray[k-1]; 
   }
   
   // public static void main(String[] args) {
      // int[] a = {-4, -4, -4, -4, -4, -4, -4};
      // Selector.kmin(a, 2);
   // }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) throws IllegalArgumentException{
      
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      if (k <= 0) {
         throw new IllegalArgumentException();
      }
      if (k > a.length) {
         throw new IllegalArgumentException();
      } 
      
      // Creates a new array to put unique values in.
      int[] aNew = new int[a.length];
      int aNewIndex = 0;
      
      int i;
      
      int[] aCopy = Arrays.copyOf(a, a.length);
      Arrays.sort(aCopy);
      int current = aCopy[0];
      aNew[aNewIndex] = current;
      aNewIndex++;
      
      for (i = 1; i < aCopy.length; i++) {
         if (aCopy[i] != current) {
            current = aCopy[i];
            aNew[aNewIndex] = current;
            aNewIndex++;
         }
      }
      
      int[] finalArray = Arrays.copyOf(aNew, aNewIndex);
      Arrays.sort(finalArray);
      
      if (k > finalArray.length) {
         throw new IllegalArgumentException();
      }
      
      if (finalArray.length < 2) {
         return finalArray[0];
      }
     
      return finalArray[finalArray.length - k];
      
   }
   
   // public static void main(String[] args) {
      // int[] a = {-4, -4, -4, -4, -4, -4, -4};
      // Selector.kmax(a, 2);
   // }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    */
   public static int[] range(int[] a, int low, int high) throws IllegalArgumentException {
     
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      int i;
      int count = 0;
   
      for (int num : a) {
         if (num >= low && num <= high) {
            count++;
         }
      }
      
      int[] aNew = new int[count];
      int aNewIndex = 0;
      
      for (i = 0; i < a.length; i++) {
         if (a[i] >= low && a[i] <= high) {
            aNew[aNewIndex] = a[i];
            aNewIndex++;
         }
      }
      return aNew;
   }
   
   // public static void main(String[] args) {
      // int[] a = {8, 2, 8, 7, 3, 3, 4};
      // Selector.range(a, 3, 7);
   // }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) throws IllegalArgumentException{
     
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      boolean isValid = false;
      for (int num : a) {
         if (num >= key) {
            isValid = true;
            break;
         }
      }
      if (!isValid) {
         throw new IllegalArgumentException();
      }
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
     
      int i;
      int count = 0;
      
      for (int num : a) {
         if (num >= key) {
            count++;
         }
      }
      
      int aNew[] = new int[count];
      int aNewIndex = 0;
      
      for (i = 0; i < a.length; i++) {
         if (a[i] >= key) {
            aNew[aNewIndex] = a[i];
            aNewIndex++;
         }
      }
      return Selector.min(aNew);  
   }
   
   // public static void main(String[] args) {
      // int[] a = {8, 2, 8, 7, 3, 3, 4};
      // Selector.ceiling(a, 5);
   // }



   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) {
      
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      boolean isValid = false;
      for (int num : a) {
         if (num <= key) {
            isValid = true;
            break;
         }
      }
      if (!isValid) {
         throw new IllegalArgumentException();
      }
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
   
      
      int i;
      int count = 0;
      
      for (int num : a) {
         if (num <= key) {
            count++;
         }
      }
      
      int aNew[] = new int[count];
      int aNewIndex = 0;
      
      for (i = 0; i < a.length; i++) {
         if (a[i] <= key) {
            aNew[aNewIndex] = a[i];
            aNewIndex++;
         }
      }
      return Selector.max(aNew);  
   }
   
   // public static void main(String[] args) {
      // int[] a = {8, 2, 8, 7, 3, 3, 4};
      // Selector.floor(a, 5);
   // }

}
