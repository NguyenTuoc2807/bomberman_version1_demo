package uet.oop.bomberman.entities.AI;

import javafx.util.Pair;

import java.util.*;

public class Astar {
    private Node[][] search;
    private PriorityQueue<Node> lst;
    private Set<Node> closed;
    private Node startnode;
    private Node endnode;

    public void setLst(PriorityQueue<Node> lst) {
        this.lst = lst;
    }

    public Set<Node> getClosed() {
        return closed;
    }

    public PriorityQueue<Node> getLst() {
        return lst;
    }

    public void setClosed(Set<Node> closed) {
        this.closed = closed;
    }

    public Node getEndnode() {
        return endnode;
    }

    public Node getStartnode() {
        return startnode;
    }

    public Node[][] getSearch() {
        return search;
    }

    public void setEndnode(Node endnode) {
        this.endnode = endnode;
    }

    public void setSearch(Node[][] search) {
        this.search = search;
    }

    public void setStartnode(Node startnode) {
        this.startnode = startnode;
    }

    public Astar(int rows, int cols, Node startnode, Node endnode) {
        this.search = new Node[rows][cols];
        this.startnode = startnode;
        this.endnode = endnode;
        this.lst = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.getF(), o2.getF());
            }
        });
        setNodes();
        this.closed = new HashSet<>();

    }

    private void setNodes() {
        for (int i = 0; i < search.length; i++) {
            for (int j = 0; j < search[0].length; j++) {
                Node tmp = new Node(i,j);
                tmp.setHeuristic(getEndnode());
                this.search[i][j] = tmp;
            }
        }
    }

    public void setBlocks(int [][] idBlock, int sz) {
        for (int i = 0; i < sz; i++) {
            this.search[idBlock[i][0]][idBlock[i][1]].setBlock(true);
        }
    }

    public List<Node> findPath() {
        lst.add(startnode);
        int []d = new int[]{0,1,0,-1,0};
        while(!lst.isEmpty()) {
            Node curr = lst.poll();
            closed.add(curr);
            if (curr.equals(endnode)) {
                List<Node> path = new ArrayList<Node>();
                path.add(curr);
                Node parent;
                while((parent = curr.getParent()) != null) {
                    path.add(0, parent);
                    curr = parent;
                }
                return path;
            } else {
                int row = curr.getRow();
                int col = curr.getCol();
                for (int i = 0; i < 4; i++) {
                    int nrow = row + d[i];
                    int ncol = col + d[i+1];
                    if (Math.min(ncol, nrow) >= 0 && nrow < getSearch().length && ncol < getSearch()[0].length) {
                        checkNode(curr, ncol, nrow);
                    }
                }
            }

        }
        return new ArrayList<Node>();
    }


    private void checkNode(Node currentNode, int col, int row) {
        Node adjacentNode = getSearch()[row][col];
        if (!adjacentNode.isBlock() && !getClosed().contains(adjacentNode)) {
            if (!getLst().contains(adjacentNode)) {
                adjacentNode.setDataNode(currentNode);
                getLst().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.isBetterPath(currentNode);
                if (changed) {
                    getLst().remove(adjacentNode);
                    getLst().add(adjacentNode);
                }
            }
        }
    }


}
