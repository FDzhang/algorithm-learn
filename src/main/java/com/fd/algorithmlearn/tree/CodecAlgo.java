package com.fd.algorithmlearn.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    /*-------------------------------------------前序遍历------------------------------------------------------------*/


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


    /*-------------------------------------------前序遍历------------------------------------------------------------*/



    /*--------------------------后序遍历---------------------------------*/


    public String serializeAfter(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeAfter(root, sb);
        return sb.toString();
    }

    private void serializeAfter(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }
        serializeAfter(root.left, sb);
        serializeAfter(root.right, sb);

        sb.append(root.val).append(SEP);
    }

    public TreeNode deserializeAfter(String data) {
        LinkedList<String> nodes = new LinkedList<>();
        for (String s : data.split(SEP)) {
            nodes.add(s);
        }
        return deserializeAfter(nodes);
    }

    private TreeNode deserializeAfter(LinkedList<String> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }

        String val = nodes.removeLast();
        if (NULL.equals(val)) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(val));

        root.right = deserializeAfter(nodes);
        root.left = deserializeAfter(nodes);
        return root;
    }

    /*--------------------------后序遍历---------------------------------*/


    /*--------------------------层级遍历---------------------------------*/

    void traverse(TreeNode root) {

        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.val);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

    }

    public String serializeFor(TreeNode root) {
        if (root == null) {
            return "";
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        StringBuilder sb = new StringBuilder();

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node == null) {
                sb.append(NULL).append(SEP);
                continue;
            }
            sb.append(node.val).append(SEP);

            queue.offer(node.left);
            queue.offer(node.right);
        }

        return sb.toString();
    }

    public TreeNode deserializeFor(String data) {
        if (data.isEmpty()) {
            return null;
        }
        String[] nodes = data.split(SEP);
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);


        for (int i = 0; i < nodes.length; ) {
            TreeNode parent = queue.poll();

            String left = nodes[i++];
            if (!left.equals(NULL)) {
                parent.left = new TreeNode(Integer.parseInt(left));
                queue.offer(parent.left);
            } else {
                parent.left = null;
            }

            if (i>=nodes.length){
                break;
            }
            String right = nodes[i++];
            if (!right.equals(NULL)) {
                parent.right = new TreeNode(Integer.parseInt(right));
                queue.offer(parent.right);
            } else {
                parent.right = null;
            }
        }

        return root;
    }

    /*--------------------------层级遍历---------------------------------*/


}
