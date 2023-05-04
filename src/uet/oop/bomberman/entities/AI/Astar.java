package uet.oop.bomberman.entities.AI;

import javafx.util.Pair;

import java.util.*;

public class Astar {
    private Node[][] searchArea;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node initialNode;
    private Node finalNode;

    public Astar(int rows, int cols, Node initialNode, Node finalNode) {
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols];
        this.openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        setNodes();
        this.closedSet = new HashSet<>();
    }

    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(i, j);
                node.calculateHeuristic(getFinalNode());
                this.searchArea[i][j] = node;
            }
        }
    }

    public void setBlocks(int[][] blocksArray, int count) {
        for (int i = 0; i < count; i++) {
            int row = blocksArray[i][0];
            int col = blocksArray[i][1];
            setBlock(row, col);
        }
    }

    public List<Node> findPath() {
        openList.add(initialNode);
        int []d = new int[]{0,1,0,-1,0};
        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            assert currentNode != null;
            if (isFinalNode(currentNode)) {
                List<Node> path = new ArrayList<Node>();
                path.add(currentNode);
                Node parent;
                while ((parent = currentNode.getParent()) != null) {
                    path.add(0, parent);
                    currentNode = parent;
                }
                return path;
            } else {
                int row = currentNode.getRow();
                int col = currentNode.getCol();
                for (int i = 0; i < 4; i++) {
                    int nrow = row + d[i];
                    int ncol = col + d[i+1];
                    if (Math.min(ncol, nrow) >= 0 && nrow < getSearchArea().length && ncol < getSearchArea()[0].length) {
                        checkNode(currentNode, ncol, nrow);
                    }
                }
            }
        }
        return new ArrayList<Node>();
    }

    private void checkNode(Node currentNode, int col, int row) {
        Node adjacentNode = getSearchArea()[row][col];
        if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode);
                if (changed) {
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    private void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }

    public Node[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Node> closedSet) {
        this.closedSet = closedSet;
    }

}
