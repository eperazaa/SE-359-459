package se459.extremers.cleanSweepFloorPlan;

import java.util.*;

import org.springframework.data.geo.Point;

public class FloorPlanInternal extends FloorPlan {
    
    HashMap<Point,CleanSweepNode> map;
    CleanSweepNode lastDiscovered;

    public FloorPlanInternal() {
        this.Reference = null;
        this.RowIterator = this.ColumnIterator = this.Reference;
        this.map = new HashMap<Point,CleanSweepNode>();
    }

    public void Add(CleanSweepNode node, Point pos) {

        // for first node, make it the reference node and add to map 
        if (this.Reference == null) {
            this.Reference = node;
            this.RowIterator = this.ColumnIterator = this.Reference;

            this.map.put(pos, node);
            this.lastDiscovered = node;
        }
        // for sucessive nodes, add to map, check around node for connections
        else {

            this.map.put(pos, node);

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
