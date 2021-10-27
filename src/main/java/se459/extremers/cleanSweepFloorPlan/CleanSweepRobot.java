package se459.extremers.cleanSweepFloorPlan;

import java.util.*;

import org.springframework.data.geo.Point;

public class CleanSweepRobot {
    
    float batteryCharge;
    int dirtCapacity;
    NavigationOptionsEnum direction;

    FloorPlanInternal internalFloorPlan;
    CleanSweepNode currentNode;
    Point position;


    public CleanSweepRobot(FloorPlanExternal externalFloorPlan, CleanSweepNode startingPoint) {
        this.batteryCharge = 250f;
        this.dirtCapacity = 50;
        this.direction = NavigationOptionsEnum.EAST;
        this.internalFloorPlan = new FloorPlanInternal();
        this.position = new Point(0,0);

        CleanHouse(startingPoint, externalFloorPlan);
    }

    public void CleanHouse(CleanSweepNode node, FloorPlanExternal externalFloorPlan) {

        while (node != null) {

            /*
             1) Visit current node and add to internal map
             2) Check if have enough power/capacity to clean and return to charging station
                - If not: we should have enough power to just return, so do so
                - If so, clean tile and check if title is clean
                    - If not: continue loop of check for power/capacity and clean
             3) Once tile is clean, we prepare for travesal by checking power again to see
                if we have enough to traverse the highest cost traversal (3 units) and then 
                return home. (We must assume highest cost since we do not know the floor 
                type of next tile)
                - If not: we should have enough power to just return, so do so
                - If so, traverse to next tile, decrement power based on the acutual cost,
                  and loop process.
            */

            // IDEA FOR MAKING SURE WE VIST THE ENTIRE ROOM
            // Create a HashMap that stores position (x,y) of OPEN edges that we have not visited yet, each new node we visit, check if 
            // that pos in in the map. If so, remove it. When we "finish" the room. Check the list to make sure any we have passed
            // we have visited.

            // TRAVERSAL
            // Implement A* pathfinding
            // This would be used for many things: calculate return to home, calculate path to door, calcualte path to unvisted tiles in room
            
            this.VisitNode(node);

            // When this is true, there is an unvisited node in one of the 4 directions around current node
            if (this.DecideNextDirection()) {
                node = externalFloorPlan.GetNodeFromNodeAndDirection(node, this.direction);
            }
            // TODO: When this is false, we should traverse to first door on hashmap
            else {
                node = Pathfind();
            }
        } 
        System.out.println("Finished. Shutting down...");
    }


    private CleanSweepNode Pathfind() {

        for (Point pos: this.internalFloorPlan.stations.keySet()) {
            CleanSweepNode chargingStation = this.internalFloorPlan.stations.get(pos);
            System.out.println("Started pathfinding to station at: (" + chargingStation.pos.getX() + "," + chargingStation.pos.getY() + ") from node " + currentNode.id);
            List<CleanSweepNode> path = this.internalFloorPlan.aStar(currentNode, this.internalFloorPlan.stations.get(pos));
            Collections.reverse(path);

            
            for(CleanSweepNode step : path) {
                System.out.println("Visiting node: " + step.id);
            }
        }
        return null;
    }

    public void VisitNode(CleanSweepNode node) {

        node.visited = true;

        // returns new node we added to map so we can set currentNode
        this.currentNode = internalFloorPlan.Add(node, position);

        System.out.println("Visited Node with ID: " + node.id);

    }

    public boolean DecideNextDirection() {
        
        /* 
         This loop checks if the direction we last moved in is still open. If it is, continute in that direction.
         If it's not open or we have already visited and cleaned, change direction to the next in the order (E, S, W, N)
         and check there. Continue until open and unclean direction is found
        */
        boolean foundDirection = false;
        for (int i = 0; i < 4; i++) {
            CleanSweepNode tmp;

            // check if direction from previous traversal is open again AND if node in that direction is
            // visited.  (are the coordinates in our internal map?)
            switch (this.direction) {
                case EAST:
                    tmp = this.internalFloorPlan.GetEastNode(this.position);
                    if (this.currentNode.eastEdge.equals(edgeType.OPEN) && tmp == null) {
                        foundDirection = true;
                    }
                    break;
                case SOUTH:
                    tmp = this.internalFloorPlan.GetSouthNode(this.position);
                    if (this.currentNode.southEdge.equals(edgeType.OPEN) && tmp == null) {
                        foundDirection = true;
                    }
                    break;
                case WEST:
                    tmp = this.internalFloorPlan.GetWestNode(this.position);
                    if (this.currentNode.westEdge.equals(edgeType.OPEN) && tmp == null) {
                        foundDirection = true;
                    }
                    break;
                case NORTH:
                    tmp = this.internalFloorPlan.GetNorthNode(this.position);
                    if (this.currentNode.northEdge.equals(edgeType.OPEN) && tmp == null) {
                        foundDirection = true;
                    }
                    break;
                default:
                    break;
            }
            if (foundDirection) {
                break;
            }

            // Rotate enum through order (E, S, W, N) if we have not found direction
            this.direction = NavigationOptionsEnum.RotateDirection(this.direction);

        }


        if (foundDirection) {

            // After direction is found, change position to relfect new node
            switch (this.direction) {
                case EAST:
                    this.position = this.internalFloorPlan.GetEastPos(this.position);
                    break;
                case SOUTH:
                    this.position = this.internalFloorPlan.GetSouthPos(this.position);
                    break;
                case WEST:
                    this.position = this.internalFloorPlan.GetWestPos(this.position);
                    break;
                case NORTH:
                    this.position = this.internalFloorPlan.GetNorthPos(this.position);
                    break;

                default:
                    break;
            }
            return true;
        }
        return false;

        
    }

}
