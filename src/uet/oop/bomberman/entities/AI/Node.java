package uet.oop.bomberman.entities.AI;

import uet.oop.bomberman.entities.Character.Oneal;

import java.util.Objects;

public class Node {
    private int g;
    private int h;
    private int f;
    private int row;
    private int col;
    private boolean isBlock;
    private Node parent;

    public void setH(int h) {
        this.h = h;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParent() {
        return parent;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return f;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Node(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    public void setHeuristic(Node endNode) {
        this.h = Math.abs(endNode.getRow() - this.getRow()) + Math.abs(endNode.getCol() - this.getCol());
    }

    public void setDataNode(Node curr) {
        setParent(curr);
        setG(curr.getG());
        setCost();
    }

    public void setCost() {
        setF(getG() + getH());
    }

    public boolean equals(Object obj) {
        Node other = (Node)obj;
        return other.getRow() == this.getRow() && other.getCol() == this.getCol();
    }

    public boolean isBetterPath(Node curr) {
        int gcost = curr.getG();
        if (gcost < getG()) {
            setDataNode(curr);
            return true;
        }
        return false;
    }

}

