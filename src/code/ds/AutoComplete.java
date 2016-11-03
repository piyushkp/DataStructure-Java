package code.ds;

import java.util.ArrayList;

/**
 * Created by ppatel2 on 11/2/2016.
 */
public class AutoComplete {
    class Node {
        char m_char;
        Node left, center, right;
        boolean wordEnd;

        public Node(char ch, boolean wordEnd) {
            m_char = ch;
            wordEnd = wordEnd;
        }
    }
    class TernaryTree {
        private Node root = null;

        private Node Add(String s, int pos, Node node) {
            if (node == null) {
                node = new Node(s.charAt(pos), false);
            }

            if (s.charAt(pos) < node.m_char) {
                node.left  = Add(s, pos, node.left);
            } else if (s.charAt(pos) > node.m_char) {
                node.right = Add(s, pos, node.right);
            } else {
                if (pos + 1 == s.length()) {
                    node.wordEnd = true;
                } else {
                    node.center = Add(s, pos + 1, node.center);
                }
            }
            return node;
        }

        public void Add(String s) {
            if (s == null || s == "") throw new IllegalArgumentException();
            root = Add(s, 0, root);
        }

        public ArrayList<String> AutoComplete(String s) {
            if (s == null || s == "") throw new IllegalArgumentException();
            ArrayList<String> suggestions = new ArrayList<>();
            int pos = 0;
            Node node = root;
            while (node != null) {
                if (s.charAt(pos) > node.m_char) {
                    node = node.right;
                }
                else if (s.charAt(pos) < node.m_char) {
                    node = node.left;
                }
                else {
                    if (++pos == s.length()) {
                        if (node.wordEnd == true)
                            suggestions.add(s);
                        FindSuggestions(s, suggestions, node.center);
                        return suggestions;
                    }
                    node = node.center;
                }
            }
            return suggestions;
        }

        private void FindSuggestions(String s, ArrayList<String> suggestions, Node node) {
            if (node == null)
                return;
            if (node.wordEnd == true)
                suggestions.add(s + node.m_char);
            FindSuggestions(s, suggestions, node.left);
            FindSuggestions(s + node.m_char, suggestions, node.center);
            FindSuggestions(s, suggestions, node.right);
        }

        public boolean Contains(String s) {
            if (s == null || s == "") throw new IllegalArgumentException();
            int pos = 0;
            Node node = root;
            while (node != null) {
                if (s.charAt(pos) < node.m_char) {
                    node = node.left;
                } else if (s.charAt(pos) > node.m_char) {
                    node = node.right;
                } else {
                    if (++pos == s.length()) return node.wordEnd;
                    node = node.center;
                }
            }
            return false;
        }
    }
}
