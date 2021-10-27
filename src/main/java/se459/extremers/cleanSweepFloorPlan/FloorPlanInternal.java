package se459.extremers.cleanSweepFloorPlan;

import java.util.*;

import org.springframework.data.geo.Point;

public class FloorPlanInternal {

    HashMap<Point, CleanSweepNode> map;
    List<Point> unvisited;
    HashMap<Point, CleanSweepNode> stations;
    CleanSweepNode lastDiscovered;
    CleanSweepNode Reference;

    public FloorPlanInternal() {
        this.Reference = null;
        this.map = new HashMap<Point, CleanSweepNode>();
        this.unvisited = new ArrayList<Point>();
        this.stations = new HashMap<Point, CleanSweepNode>();
    }

    public CleanSweepNode Add(CleanSweepNode node, Point pos) {

        // Creates new copy of passed external node
        node = new CleanSweepNode(node, pos);

        // check if charging station
        if (node.isChargingStation) {
            this.stations.put(pos, node);
        }

        // for first node, make it the reference node and add to map
        if (this.Reference == null) {
            this.Reference = node;

            this.map.put(pos, node);
            this.lastDiscovered = node;
        }
        else {
            // add pos and node to internal hashmap
            this.map.put(pos, node);
            this.lastDiscovered = node;
        }
        // Check around node and create connections & check for unvisted nodes

        // Check north
        // Gets north position of node
        Point checkPos = GetNorthPos(pos);
        // Attemps to get north node if it exists in hash map. If so, create connections.
        CleanSweepNode tmp = map.get(checkPos);
        if (tmp != null) {
            tmp.AssignSouthInternal(node);
            node.AssignNorthInternal(tmp);
        }
        // checks if north is open and if north position is in unvisited hashmap
        if (node.northEdge == edgeType.OPEN && tmp == null && !this.unvisited.contains(checkPos)) {
            this.unvisited.add(checkPos);
        }

        // Check east
        checkPos = GetEastPos(pos);
        tmp = map.get(checkPos);
        if (tmp != null) {
            tmp.AssignWestInternal(node);
            node.AssignEastInternal(tmp);
        }
        if (node.eastEdge == edgeType.OPEN && tmp == null && !this.unvisited.contains(checkPos)) {
            this.unvisited.add(checkPos);
        }

        // Check south
        checkPos = GetSouthPos(pos);
        tmp = map.get(checkPos);
        if (tmp != null) {
            tmp.AssignNorthInternal(node);
            node.AssignSouthInternal(tmp);
        }
        if (node.southEdge == edgeType.OPEN && tmp == null && !this.unvisited.contains(checkPos)) {
            this.unvisited.add(checkPos);
        }

        // Check west
        checkPos = GetWestPos(pos);
        tmp = map.get(checkPos);
        if (tmp != null) {
            tmp.AssignEastInternal(node);
            node.AssignWestInternal(tmp);
        }
        if (node.westEdge == edgeType.OPEN && tmp == null && !this.unvisited.contains(checkPos)) {
            this.unvisited.add(checkPos);
        }

        // Finally, check if current node is in univisted list, if so remove it
        if (this.unvisited.contains(pos)) {
            this.unvisited.remove(pos);
        }
        return node;
    }

    public CleanSweepNode GetNorthNode(Point pos) {
        try {
            return this.map.get(new Point(pos.getX(), pos.getY() - 1));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public CleanSweepNode GetEastNode(Point pos) {

        try {
            return this.map.get(new Point(pos.getX() + 1, pos.getY()));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public CleanSweepNode GetSouthNode(Point pos) {
        return this.map.get(new Point(pos.getX(), pos.getY() + 1));
    }

    public CleanSweepNode GetWestNode(Point pos) {
        return this.map.get(new Point(pos.getX() - 1, pos.getY()));
    }

    public Point GetNorthPos(Point pos) {
        return new Point(pos.getX(), pos.getY() - 1);
    }

    public Point GetEastPos(Point pos) {
        return new Point(pos.getX() + 1, pos.getY());
    }

    public Point GetSouthPos(Point pos) {
        return new Point(pos.getX(), pos.getY() + 1);
    }

    public Point GetWestPos(Point pos) {
        return new Point(pos.getX() - 1, pos.getY());
    }

    // code base from
    // https://stackoverflow.com/questions/5601889/unable-to-implement-a-star-in-java
    public List<CleanSweepNode> aStar(CleanSweepNode start, CleanSweepNode goal) {
        Set<CleanSweepNode> open = new HashSet<CleanSweepNode>();
        Set<CleanSweepNode> closed = new HashSet<CleanSweepNode>();

        start.g = 0;
        start.h = estimateDistance(start, goal);
        start.f = start.h;

        open.add(start);

        while (true) {
            CleanSweepNode current = null;

            if (open.size() == 0) {
                throw new RuntimeException("no route");
            }

            for (CleanSweepNode node : open) {
                if (current == null || node.f < current.f) {
                    current = node;
                }
            }

            if (current == goal) {
                break;
            }

            open.remove(current);
            closed.add(current);

            for (CleanSweepNode neighbor : current.neighbors) {
                if (neighbor == null) {
                    continue;
                }

                int nextG = current.g + neighbor.cost;

                if (nextG < neighbor.g) {
                    open.remove(neighbor);
                    closed.remove(neighbor);
                }

                if (!open.contains(neighbor) && !closed.contains(neighbor)) {
                    neighbor.g = nextG;
                    neighbor.h = estimateDistance(neighbor, goal);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;
                    open.add(neighbor);
                }
            }
        }

        List<CleanSweepNode> nodes = new ArrayList<CleanSweepNode>();
        CleanSweepNode current = goal;
        while (current.parent != null) {
            nodes.add(current);
            current = current.parent;
        }
        nodes.add(start);

        return nodes;
    }

    public int estimateDistance(CleanSweepNode node1, CleanSweepNode node2) {
        double tmp;
        try {
            tmp = Math.abs(node1.pos.getX() - node2.pos.getX()) + Math.abs(node1.pos.getY() - node2.pos.getY());
        } catch (NullPointerException e) {
            tmp = Integer.MAX_VALUE;
        }

        return (int) tmp;
    }

}
