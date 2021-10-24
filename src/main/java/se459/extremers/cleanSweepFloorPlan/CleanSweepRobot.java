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


    public void MapInitalLayout(HomeMatrix homeMatrix) {

        // Map out the floor plan
        CleanSweepNode node = homeMatrix.GetNodeFromXY(0, 4);
        while (node != null) {

            // Plan:
            // Add node to visted list
            // Spiral application
                // Prefer order of E->S->W->N
            // Loop and check node open in order and call get node from floor plan once moved

            this.VisitNode(node);
            //this.DecideNextDirection(node);

            CleanSweepNode newNode = homeMatrix.GetNodeFromNodeAndDirection(node, this.direction);

            node = newNode;
        } 


    }

    public void VisitNode(CleanSweepNode node) {

        if (!this.visitedNodes.contains(node)){
            this.visitedNodes.add(node);
            node.visited = true;
        }

        System.out.println("Visited Node with ID: " + node.id);

    }

    public void DecideNextDirection(CleanSweepNode node) {
        
        /* 
         This loop checks if the direction we last moved in is still open. If it is, continute in that direction.
         If it's not open, change direction to the next in the order (E, S, W, N) and check there. Continue until open 
         direction is found
        */
        for (int i = 0; i < 4; i++) {

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
                    break;
            }
            if (foundDirection) {
                break;
            }

            // Rotate enum through order (E, S, W, N) if we have not found direction
            this.direction = NavigationOptionsEnum.RotateDirection(this.direction);

        }
    }

}
