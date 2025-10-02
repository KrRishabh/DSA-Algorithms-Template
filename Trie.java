import java.util.*;

/**
 * Map-based Trie implementation.
 *
 * Advantages:
 *  - Supports arbitrary characters (not restricted to 'a'..'z').
 *  - Saves memory when the trie is sparse (nodes have few children).
 *
 * Each node:
 *   - children: Map<Character, TrieNode>
 *   - pass: how many words pass through this node
 *   - ends: how many words end exactly at this node
 */
public class Trie {

    private final TrieNode root = new TrieNode();

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        int pass = 0;
        int ends = 0;
    }

    /** Insert a word into the trie. O(L) time */
    public void insert(String word) {
        if (word == null) throw new IllegalArgumentException("word must be non-null");
        TrieNode node = root;
        node.pass++;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
            node.pass++;
        }
        node.ends++;
    }

    /** Return true if the trie contains the exact word. */
    public boolean contains(String word) {
        TrieNode node = getNode(word);
        return node != null && node.ends > 0;
    }

    /** Return number of times `word` was inserted. */
    public int countWordsEqualTo(String word) {
        TrieNode node = getNode(word);
        return node == null ? 0 : node.ends;
    }

    /** Return number of words that start with the given prefix. */
    public int countWordsStartingWith(String prefix) {
        TrieNode node = getNode(prefix);
        return node == null ? 0 : node.pass;
    }

    /** Return true if any word starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return getNode(prefix) != null;
    }

    /** Delete one occurrence of a word. Returns true if deleted, false if not present. */
    public boolean delete(String word) {
        if (countWordsEqualTo(word) == 0) return false;

        TrieNode node = root;
        node.pass--;
        for (char ch : word.toCharArray()) {
            TrieNode child = node.children.get(ch);
            child.pass--;
            if (child.pass == 0) {
                // If no word passes through this child anymore, remove it completely
                node.children.remove(ch);
                return true;
            }
            node = child;
        }
        node.ends--;
        return true;
    }

    // ---------------------------
    // Autocomplete (collect up to k words with given prefix)
    // ---------------------------

    public List<String> autocomplete(String prefix, int k) {
        TrieNode node = getNode(prefix);
        if (node == null) return Collections.emptyList();
        if (k <= 0) k = Integer.MAX_VALUE;
        List<String> results = new ArrayList<>();
        StringBuilder sb = new StringBuilder(prefix);
        collect(node, sb, results, k);
        return results;
    }

    private void collect(TrieNode node, StringBuilder sb, List<String> results, int k) {
        if (results.size() >= k) return;
        if (node.ends > 0) {
            for (int i = 0; i < node.ends && results.size() < k; i++) {
                results.add(sb.toString());
            }
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            sb.append(entry.getKey());
            collect(entry.getValue(), sb, results, k);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    // ---------------------------
    // Wildcard search ('.' matches any single character)
    // ---------------------------

    public boolean searchWithWildcard(String pattern) {
        if (pattern == null) return false;
        return wildcardSearch(root, pattern, 0);
    }

    private boolean wildcardSearch(TrieNode node, String pat, int index) {
        if (node == null) return false;
        if (index == pat.length()) return node.ends > 0;

        char ch = pat.charAt(index);
        if (ch == '.') {
            for (TrieNode child : node.children.values()) {
                if (wildcardSearch(child, pat, index + 1)) return true;
            }
            return false;
        } else {
            TrieNode child = node.children.get(ch);
            return wildcardSearch(child, pat, index + 1);
        }
    }

    // ---------------------------
    // Internal helper
    // ---------------------------

    private TrieNode getNode(String s) {
        if (s == null) return null;
        TrieNode node = root;
        for (char ch : s.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) return null;
        }
        return node;
    }

    // ---------------------------
    // Demo main
    // ---------------------------
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("apple");
        trie.insert("app");
        trie.insert("application");
        trie.insert("apt");
        trie.insert("banana");
        trie.insert("apple"); // duplicate

        System.out.println("contains(\"app\") = " + trie.contains("app"));           // true
        System.out.println("startsWith(\"ap\") = " + trie.startsWith("ap"));         // true
        System.out.println("countWordsEqualTo(\"apple\") = " + trie.countWordsEqualTo("apple")); // 2
        System.out.println("autocomplete(\"app\", 5) = " + trie.autocomplete("app", 5));
        System.out.println("searchWithWildcard(\"a..le\") = " + trie.searchWithWildcard("a..le")); // true

        trie.delete("app");
        System.out.println("after delete(\"app\") contains(\"app\") = " + trie.contains("app")); // false
    }
}
