package code.ds;
import com.sun.org.apache.bcel.internal.generic.SWAP;
import javafx.util.Pair;

import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * Created by ppatel2 on 3/11/2017.
 */
public class N_Tree {
    class NTree {
        int data;
        ArrayList<NTree> children = new ArrayList<>();

        public NTree(int c) {
            this.data = c;
        }

        public void addChild(NTree node) {
            children.add(node);
        }
    }

    //serialize non- binary tree (n-ary tree)
    private StringBuffer sb = new StringBuffer();
    public String serialize(NTree root) {
        serializeRecursive(root);
        return sb.toString();
    }

    private void serializeRecursive(NTree root) {
        if (root == null)
            return;
        sb.append(root.data);
        for (NTree node : root.children) {
            serializeRecursive(node);
        }
        sb.append(')');
    }

    public NTree deserialize(String str) {
        NTree root = deserializeRecursive(str);
        return root;
    }

    private int currentIndex = 0;
    private NTree deserializeRecursive(String str) {
        if (currentIndex >= str.length())
            return null;
        else if (str.charAt(currentIndex) == ')')
            return null;
        NTree root = new NTree(str.charAt(currentIndex));
        while (currentIndex < str.length()) {
            currentIndex++;
            NTree child = deserializeRecursive(str);
            if (child == null)
                break;
            root.addChild(child);
        }
        return root;
    }

}
