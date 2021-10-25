package se459.extremers.cleanSweepFloorPlan;

import java.util.*;

import org.springframework.data.geo.Point;

public class FloorPlanInternal {
    
    HashMap<Point,CleanSweepNode> map;
    HashMap<Point,CleanSweepNode> doors;
    CleanSweepNode lastDiscovered;
    CleanSweepNode Reference;

    public FloorPlanInternal() {
        this.Reference = null;
        this.map = new HashMap<Point,CleanSweepNode>();
        this.doors = new HashMap<Point,CleanSweepNode>();
    }

    public void Add(CleanSweepNode node, Point pos) {

        // Creates new copy of passed external node
        node = new CleanSweepNode(node);

        // for first node, make it the reference node and add to map 
        if (this.Reference == null) {
            this.Reference = node;

            this.map.put(pos, node);
            this.lastDiscovered = node;
            //CheckForDoor(pos, node);
        }
        // for sucessive nodes, add to map, check around node for connections
        else {

            this.map.put(pos, node);
            //CheckForDoor(pos, node);

            // Check around node and create connections
            // Check north
            CleanSweepNode tmp = map.get(GetNorthPos(pos));
            if(tmp != null) {
                tmp.southNode = node;
                node.northNode = tmp;
            }
            // Check east
            tmp = map.get(GetEastPos(pos));
            if(tmp != null) {
                tmp.westNode = node;
                node.eastNode = tmp;
            }
            // Check south
            tmp = map.get(GetSouthPos(pos));
            if(tmp != null) {
                tmp.northNode = node;
                node.southNode = tmp;
            }
            // Check west
            tmp = map.get(GetWestPos(pos));
            if(tmp != null) {
                tmp.eastNode = node;
                node.westNode = tmp;
            }
        }
    }

    /*
    private void CheckForDoor(Point pos, CleanSweepNode node) {
        // Each time we visit a node, we want to check if it has a door so we can visit it later
        NavigationOptionsEnum tmp = NavigationOptionsEnum.EAST;

        for (int i = 0; i < 4; i++) {
            boolean foundDoor = false;
            switch (tmp) {
                case EAST:
                    if (node.eastEdge == edgeType.DOOR) {
                        foundDoor = true;
                    }
                    break;
                case SOUTH:
                    if (node.southEdge == edgeType.DOOR) {
                        foundDoor = true;
                    }
                    break; 
                case WEST:
                    if (node.westEdge == edgeType.DOOR) {
                        foundDoor = true;
                    }
                    break; 
                case NORTH:
                    if (node.northEdge == edgeType.DOOR) {
                        foundDoor = true;
                    }
                    break;
                default:
                    break;

            }
            if (foundDoor) {
                doors.put(pos,node);
            }

            tmp = NavigationOptionsEnum.RotateDirection(tmp);
        }
    } */

    public CleanSweepNode GetNorthNode(Point pos) {
        try {
            return this.map.get(new Point(pos.getX(), pos.getY()-1));
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public CleanSweepNode GetEastNode(Point pos) {

        try {
            return this.map.get(new Point(pos.getX()+1, pos.getY()));
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public CleanSweepNode GetSouthNode(Point pos) {
        return this.map.get(new Point(pos.getX(), pos.getY()+1));
    }

    public CleanSweepNode GetWestNode(Point pos) {
        return this.map.get(new Point(pos.getX()-1, pos.getY()));
    }

    public Point GetNorthPos(Point pos) {
        return new Point(pos.getX(), pos.getY()-1);
    }

    public Point GetEastPos(Point pos) {
        return new Point(pos.getX()+1, pos.getY());
    }

    public Point GetSouthPos(Point pos) {
        return new Point(pos.getX(), pos.getY()+1);
    }

    public Point GetWestPos(Point pos) {
        return new Point(pos.getX()-1, pos.getY());
    }


    
   
}
