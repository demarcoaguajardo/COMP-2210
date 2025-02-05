import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author Demarco Guajardo (dag0047@auburn.edu)
 *
 */
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

   //////////////////////////////////////////////////////////
   // Do not change the following three fields in any way. //
   //////////////////////////////////////////////////////////

   /** References to the first and last node of the list. */
   Node front;
   Node rear;

   /** The number of nodes in the list. */
   int size;

   /////////////////////////////////////////////////////////
   // Do not change the following constructor in any way. //
   /////////////////////////////////////////////////////////

   /**
    * Instantiates an empty LinkedSet.
    */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


   //////////////////////////////////////////////////
   // Public interface and class-specific methods. //
   //////////////////////////////////////////////////

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this LinkedSet.
    *
    * @return a string representation of this LinkedSet
    */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }


   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements, false otherwise.
    */
   public boolean isEmpty() {
      return (size == 0);
   }

   public static void main(String[] args) {
      LinkedSet<String> ls = new LinkedSet<String>();
      ls.add("1");
      ls.add("2");
      ls.add("3");
      
      Set<String> s = new LinkedSet<String>();
      s.add("1");
      s.add("2");
      s.add("3");
      
      ls.complement(s);
      ls.union(s);
      ls.intersection(s);
      
      LinkedSet ls2 = new LinkedSet();
      ls2.add("1");
      ls2.add("2");
      ls2.add("3");
   
      ls.complement(ls2);
      ls.union(ls2);
      ls.equals(ls2);
   }
   

   /**
    * Ensures the collection contains the specified element. Neither duplicate
    * nor null values are allowed. This method ensures that the elements in the
    * linked list are maintained in ascending natural order.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   public boolean add(T element) {
      if (contains(element) || element == null) {
         return false;
      }
      Node n = new Node(element);
    // if the list is empty
      if (isEmpty()) {
         front = n;
         rear = n;
         size++;
         return true;
      }
    //if the element belongs in the front (is smallest value)
      if (n.element.compareTo(front.element) < 0) {
         n.next = front;
         front.prev = n;
         front = n;
         size++;
         return true;
      }
    //if the element belongs in the rear (is largest value)
      if (n.element.compareTo(rear.element) > 0) {
         n.prev = rear;
         rear.next = n;
         rear = n; // update rear pointer
         size++;
         return true;
      }
    //inserting the element right before the bigger element.
      Node p = front;
      while (p != null && p.element.compareTo(n.element) < 0) {
         p = p.next;
      }
      n.next = p;
      n.prev = p.prev;
      p.prev.next = n;
      p.prev = n;
      size++;
      return true;
   }

   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. This method, consistent with add, ensures
    * that the elements in the linked lists are maintained in ascending
    * natural order.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   public boolean remove(T element) {
      if (!contains(element) || isEmpty() || element == null) {
         return false;
      }
      
      //removing first element if element is front
      if (front.element.equals(element)) {
         front = front.next;
         if (front == null) {
            rear = null;
         }
         else {
            front.prev = null;
         }
         size--;
         return true;
      }
      
      //removing last element if element is rear
      if (rear.element.equals(element)) {
         rear = rear.prev;
         rear.next = null;
         size--;
         return true;
      }
      
      //removing target element in middle of list (not first or last)
      Node p = front.next;
      while (p != rear) {
         if (p.element.equals(element)) {
            p.prev.next = p.next;
            p.next.prev = p.prev;
            size--;
            return true;
         }
         p = p.next;
      }
      return false;
   }


   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
   public boolean contains(T element) {
      return locate(element) != null;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(Set<T> s) {
      if (size() != s.size()) {
         return false;
      }  
      
      if (s.isEmpty()) {
         return true;
      }
      
      LinkedIterator itr = new LinkedIterator();
      while (itr.hasNext()) {
         T element = itr.next();
         if (!s.contains(element)) {
            return false;
         }
      }
      
      Iterator<T> itr2 = s.iterator();
      while (itr2.hasNext()) {
         T element = itr2.next();
         if (!this.contains(element)) {
            return false;
         }
      }
      
      return true;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(LinkedSet<T> s) {
      
      if (size() != s.size()) {
         return false;
      }  
      
      if (s.isEmpty()) {
         return true;
      }
      
      for (T obj : s) {
         if (!this.contains(obj)) {
            return false;
         }
      }
      
      // LinkedIterator itr = new LinkedIterator();
      // while (itr.hasNext()) {
         // T element = itr.next();
         // if (!s.contains(element)) {
            // return false;
         // }
      // }
   //    
      // Iterator<T> itr2 = s.iterator();
      // while (itr2.hasNext()) {
         // T element = itr2.next();
         // if (!this.contains(element)) {
            // return false;
         // }
      // }
      
      return true;
   
      
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(Set<T> s) {
   
      //final set creation
      Set<T> union = new LinkedSet<>();
      
      //add all elements from this set
      LinkedIterator itr = new LinkedIterator();
      while (itr.hasNext()) {
         T element = itr.next();
         union.add(element);
      }
      
      //add all elements from s
      Iterator<T> itr2 = s.iterator();
      while (itr2.hasNext()) {
         T element = itr2.next();
         union.add(element);
      }
      
      return union;
      
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(LinkedSet<T> s){
      
      //final set creation
      Set<T> union = new LinkedSet<>();
      
      //add all elements from this set
      LinkedIterator itr = new LinkedIterator();
      while (itr.hasNext()) {
         T element = itr.next();
         union.add(element);
      }
      
      //add all elements from s
      Iterator<T> itr2 = s.iterator();
      while (itr2.hasNext()) {
         T element = itr2.next();
         union.add(element);
      }
   
      return union;
       
   }


   /**
    * Returns a set that is the intersection of this set and the parameter set.
    *
    * @return  a set that contains elements that are in both this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      
      //final set creation
      Set<T> intersection = new LinkedSet<>();
      
      //checking if s contains same element as in this. if so,
      //adding it on.
      LinkedIterator itr = new LinkedIterator();
      while (itr.hasNext()) {
         T element = itr.next();
         if (s.contains(element)) {
            intersection.add(element);
         }
      }
      
      return intersection;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(LinkedSet<T> s) {
   
      //final set creation
      Set<T> intersection = new LinkedSet<>();
      
      //checking if s contains same element as in this. If so,
      //adding it on.
      LinkedIterator itr = new LinkedIterator();
      while (itr.hasNext()) {
         T element = itr.next();
         if (s.contains(element)) {
            intersection.add(element);
         }
      }
      
      return intersection;
   
   }


   /**
    * Returns a set that is the complement of this set and the parameter set.
    *
    * @return  a set that contains elements that are in this set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
   
     //final set creation
      Set<T> complement = new LinkedSet<>();
      
      //checking if s doesn't contain same element as in this. If it doesn't,
      //adding it on.
      LinkedIterator itr = new LinkedIterator();
      while (itr.hasNext()) {
         T element = itr.next();
         if (!s.contains(element)) {
            complement.add(element);
         }
      }
      return complement;
   }


   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(LinkedSet<T> s) {
   
      //final set creation
      Set<T> complement = new LinkedSet<>();
      
      //checking if s doesn't contain same element as in this. If it doesn't,
      //adding it on.
      LinkedIterator itr = new LinkedIterator();
      while (itr.hasNext()) {
         T element = itr.next();
         if (!s.contains(element)) {
            complement.add(element);
         }
      }
      return complement;
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in ascending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> iterator() {
      return new LinkedIterator();
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in descending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> descendingIterator() {
      return new DescendingIterator();
   }


   /**
    * Returns an iterator over the members of the power set
    * of this LinkedSet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      return new PowerSetIterator();
   }


   //////////////////////////////
   // Private utility methods. //
   //////////////////////////////

   private Node locate(T element) {
      Node n = front;
      while (n != null) {
         if (n.element.equals(element)) {
            return n;
         }
         n = n.next;
      }
      return null;
   }
   
   private boolean validIndex(int index) {
      if (index < 0 || index > size()) {
         return false;
      }
      return true;
   }
   
   ////////////////////
   // Nested classes //
   ////////////////////
   
   private class LinkedIterator implements Iterator<T> {
      
      private Node current = front;
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
      
      public boolean hasNext() {
         return current != null;
      }
      
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         T result = current.element;
         current = current.next;
         return result;
      }
   }
   
   private class DescendingIterator implements Iterator<T> {
   
      private Node current = rear;
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
      
      public boolean hasNext() {
         return current != null;
      }
      
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         T result = current.element;
         current = current.prev;
         return result;
      }
   }
   
   private class PowerSetIterator implements Iterator<Set<T>> {
      
      int N;
      int M;
      int current;
      
      public PowerSetIterator() {
         N = size();
         M = (int)Math.pow(2, N);
         current = 0;
      }
      
      public void remove() {
         throw new UnsupportedOperationException();
      }  
      
      public boolean hasNext() {
         return current < M;
      }
      
      public Set<T> next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
      
         LinkedSet<T> s = new LinkedSet<T>();
      
      // check if the LinkedSet is empty
         if (front == null) {
            current++;
            return s;
         }
      
         String bitstring = Integer.toBinaryString(current);
         char[] bits = bitstring.toCharArray();
      
      // iterate from right to left over bitstring and the internal
      // linked list to ensure that the call to add will insert a new
      // first node (constant time)
         Node n = front;
         for (int i = bits.length - 1; i >= 0; i--) {
            if (bits[i] == '1') {
               s.add(n.element);
            }
            n = n.next;
         }
      
         current++;
         return s;
      }
   }

   //////////////////////////////////////////////
   // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
   //////////////////////////////////////////////

   /**
    * Defines a node class for a doubly-linked list.
    */
   class Node {
      /** the value stored in this node. */
      T element;
      /** a reference to the node after this node. */
      Node next;
      /** a reference to the node before this node. */
      Node prev;
   
      /**
       * Instantiate an empty node.
       */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }
   
      /**
       * Instantiate a node that containts element
       * and with no node before or after it.
       */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }

}
