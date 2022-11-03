package bstmap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K, V> {

    int size = 0;
    private Node root;
    /*
    What should I do:
    First, define the Node class, which contains the key, value, right,left
    Second, define the root in BSTMap class
    Third, write different functions
     */
    private class Node {
//        variable
        K key;
        V val;
        Node left;
        Node right;

//        initialize the node class
        Node(K k, V v){
            key = k;
            val = v;
        }

    }
//implement all the methods given in Map61B except for remove,
// iterator and keySet.
    /** Removes all the mappings from this map. */
    public void clear(){
        size = 0;
        root = null;
    }
    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
        if (root == null) {
            return false;
        }
        return containsKey(root,key);
    }

    private boolean containsKey(Node root,K k) {
        if (k == null) {
            throw new IllegalArgumentException("calls containsKey() with a null key");
        }
        if (root == null) {
            return false;
        }
        int cmp = k.compareTo(root.key);
        if (cmp < 0) {
            return containsKey(root.left, k);
        }
        else if (cmp > 0) {
            return containsKey(root.right, k);
        }
        else {
            return true;
        }
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return get(root,key);
    }

    private V get(Node root,K k) {
        if (k == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (root == null) {
            return null;
        }
        int cmp = k.compareTo(root.key);
        if (cmp < 0) {
            return get(root.left, k);
        }
        else if (cmp > 0) {
            return get(root.right, k);
        }
        else {
            return root.val;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        // to do
        return size;
    }
    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        // to do
        // 0. check if root is null, if so, create a new root
        // 1. check if key is null, if so throw an error
        // 2. check if key is already existing, if so, replacing value
        // 3. not existing, add new node. need compare
        root = put(root, key, value);
    }

    private Node put(Node root, K key, V value) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        if (root == null) {
            size += 1;
            return new Node(key,value);
        }
        int cmp = key.compareTo(root.key);
        if (cmp > 0) {
            root.right = put(root.right, key, value);
        }
        else if (cmp < 0) {
            root.left = put(root.left, key, value);
        }
        else {root.val = value;}
        return root;
    }
    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        // 1. if it's the child, directly delete
        // 2. if it only has left child, left child replace it
        // 3. if it only has right child, right child replace it
        // 4. if it has two children, find the right side smallest child, replace it.
        if (containsKey(key)) {
            V targetValue = get(key);
            root = remove(root, key);
            size -= 1;
            return targetValue;
        }
        return null;
    }
    private Node remove(Node root, K key) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp > 0) {
            root.right = remove(root.right, key);
        }
        else if (cmp < 0) {
            root.left = remove(root.left, key);
        }
        else {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            // have two children
            Node t = root;
            root = findSmallChild(root.right);
            root.right =remove(t.right, root.key);
            root.left = t.left;
        }
        return root;
    }

    private Node findSmallChild(Node root) {
        if (root.left == null) {
            return root;
        }
        return findSmallChild(root.left);
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/

    @Override
    public V remove(K key, V value) { throw new UnsupportedOperationException();}

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        addKey(root, set);
        return set;
    }

    private void addKey(Node root, Set<K> set) {
        if (root == null) {
            return;
        }
        set.add(root.key);
        addKey(root.left, set);
        addKey(root.right, set);
    }
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
