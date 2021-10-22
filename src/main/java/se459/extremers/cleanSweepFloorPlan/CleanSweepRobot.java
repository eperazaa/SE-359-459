package se459.extremers.cleanSweepFloorPlan;

import java.util.*;

public class CleanSweepRobot {
    
    float batteryCharge;
    int dirtCapacity;
    NavigationOptionsEnum direction;

    List<CleanSweepNode> doors;
    List<CleanSweepNode> visitedNodes;

    public CleanSweepRobot() {
        this.batteryCharge = 250f;
        this.dirtCapacity = 50;
        this.direction = NavigationOptionsEnum.EAST;

        this.doors =  new ArrayList<CleanSweepNode>();
        this.visitedNodes = new ArrayList<CleanSweepNode>();
    }


    public void AddDoorNode(CleanSweepNode node) {
        this.doors.add(node);
    }


    public void VisitNode(CleanSweepNode node) {

        // Plan:
        // Add node to visted list
        // Spiral application
            // Prefer order of E->S->W->N
        // Loop and check node open in order and call get node from floor plan once moved

        if (!this.visitedNodes.contains(node)){
            this.visitedNodes.add(node);
            node.visited = true;
        }

        System.out.println("Visited Node with ID: " + node.id);


        /* 
         This loop checks if the direction we last moved in is still open. If it is, continute in that direction.
         If it's not open, change direction to the next in the order (E, S, W, N) and check there. Continue until open 
         direction is found
        */
        for (int i = 0; i < 2; i++) {

            boolean foundDirection = false;

            // check if direction from previous traversal is open again
            switch (this.direction) {
                case EAST:
                    if (node.eastEdge.equals(edgeType.OPEN)) {
                        foundDirection = true;
                    }
                    break;
                case SOUTH:
                    if (node.southEdge.equals(edgeType.OPEN)) {
                        foundDirection = true;
                    }
                    break;
                case WEST:
                    if (node.westEdge.equals(edgeType.OPEN)) {
                        foundDirection = true;
                    }
                    break;
                case NORTH:
                    if (node.northEdge.equals(edgeType.OPEN)) {
                        foundDirection = true;
                    }
                    break;
            
                default:
                    System.out.println("Found End! :D");
                    break;
            }
            if (foundDirection) {
                break;
            }

            // if direction from previous traversal is blocked, change direction to next in order (E, S, W, N)
            switch (this.direction) {
                case EAST:
                    this.direction = NavigationOptionsEnum.SOUTH;
                    break;
                case SOUTH:
                    this.direction = NavigationOptionsEnum.WEST;
                    break;
                case WEST:
                    this.direction = NavigationOptionsEnum.NORTH;
                    break;
                case NORTH:
                    this.direction = NavigationOptionsEnum.EAST;
                    break;
                default:
                    break;
            }

        }

    }

}
