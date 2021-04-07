package com.fd.algorithmlearn.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * 297. 二叉树的序列化与反序列化 （困难）
 * https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree/
 * <p>
 * // Your Codec object will be instantiated and called as such:
 * // Codec ser = new Codec();
 * // Codec deser = new Codec();
 * // TreeNode ans = deser.deserialize(ser.serialize(root));
 *
 * @author ：zxq
 * @date ：Created in 2021/4/7 9:59
 */

public class CodecAlgo {

    /*-------------------------- Encodes a tree to a single string.---------------------------------*/

    private final String NULL = "#";
    private final String SEP = ",";

    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }

    private void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }

        sb.append(root.val).append(SEP);

        serialize(root.left, sb);
        serialize(root.right, sb);
    }


    /*-------------------------- Encodes a tree to a single string.---------------------------------*/


    /*--------------------------Decodes your encoded data to tree.---------------------------------*/

    public TreeNode deserialize(String data) {
        LinkedList<String> nodes = new LinkedList<>();
        for (String s : data.split(SEP)) {
            nodes.add(s);
        }
        return deserialize(nodes);
    }

    private TreeNode deserialize(LinkedList<String> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }

        String val = nodes.removeFirst();
        if (NULL.equals(val)) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(val));

        root.left = deserialize(nodes);
        root.right = deserialize(nodes);
        return root;
    }

    /*--------------------------Decodes your encoded data to tree.---------------------------------*/


    /*--------------------------后序遍历---------------------------------*/



    /*--------------------------后序遍历---------------------------------*/

}
