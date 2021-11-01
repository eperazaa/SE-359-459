package se459.extremers.cleanSweepFloorPlan;

import se459.extremers.Constants;
import se459.extremers.simulator.CleanSweepSimulator;

import org.springframework.data.geo.Point;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class CleanSweepRobot {
    
    boolean emptyMeLed;
    float batteryCharge;
    int dirtCapacity;
    NavigationOptionsEnum direction;

    FloorPlanInternal internalFloorPlan;
    CleanSweepNode currentNode;
    Point position;
    ModeOptions mode; //probably it wont be used. IT is just if we need to know when traversing if we are just returning or moving to door, no cleaning is neccesary
    private CleanSweepNode lastVisited;
    private CleanSweepSimulator myCSS;

    // stats for testing
    int tripsToStation;    
    FileWriter myWriter;


    public CleanSweepRobot(CleanSweepSimulator css) {
        this.batteryCharge = Constants.MAX_POWER_CHARGE; 
        this.dirtCapacity = Constants.MAX_DIRT_CAPACITY; 
        this.emptyMeLed = false;
        this.direction = Constants.DEF_DIRECTION; 
        this.internalFloorPlan = new FloorPlanInternal();
        this.position = Constants.DEF_INITIAL_POSITION;
        this.mode = Constants.DEF_MODE;
        this.myCSS = css;
        
        this.tripsToStation = 0;
        try {
            myWriter = new FileWriter("filename.txt");
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    private void writeStuff(String tmp) {
        try {
            myWriter.write(tmp + '\n');
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    public void cleanHouse(CleanSweepNode node) {


        while (node != null) { // TODO Floor External PLan should be on the simulator and not here

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

            // IDEA FOR MAKING SURE WE VISIT THE ENTIRE ROOM
            // Create a HashMap that stores position (x,y) of OPEN edges that we have not visited yet, each new node we visit, check if 
            // that pos in in the map. If so, remove it. When we "finish" the room. Check the list to make sure any we have passed
            // we have visited.

            // TRAVERSAL
            // Implement A* pathfinding
            // This would be used for many things: calculate return to home, calculate path to door, calcualte path to unvisted tiles in room
            
            this.VisitNode(node);


            // When this is true, there is an unvisited node in one of the 4 directions around current node
            if (this.DecideNextDirection()) {
                CleanSweepNode prevNode = node;
                node = this.myCSS.getExternalFloorPlan().GetNodeFromNodeAndDirection(node, this.direction);
                float movePowerComsumption = calculateMovingPowerComsumption(prevNode, node);
                decreasePower(movePowerComsumption);
            }
            else {
                boolean unvistedNodeExsist = TraverseToLastUnvisited();
                if (unvistedNodeExsist) {
                        node = this.currentNode;
                        CleanSweepNode prevNode = node;


                        // this is the first movement after we finish pathfinding
                        this.DecideNextDirection(); // this is done to correctly update position
                        node = this.myCSS.getExternalFloorPlan().GetNodeFromNodeAndDirection(node, this.direction);
                        float movePowerComsumption = calculateMovingPowerComsumption(prevNode, node);
                        decreasePower(movePowerComsumption);
                }
                else {
                    node = null;
                    System.out.println("All tiles visted");
                }

            }
        } 
        System.out.println("Finished cleaning. Shutting down...");
        System.out.println("Ending power ammount = " + this.batteryCharge);
        System.out.println("Ending capacity ammount = " + this.dirtCapacity);
        System.out.println("Total trips back to station = " + this.tripsToStation);

        try{
            myWriter.close();
        }
        catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            
        
        

        //TODO: Change this. Without this line, execution continues past end... investigate
        System.exit(0);
    }


    private boolean TraverseToLastUnvisited() {

        // Use this to pathfind to nearest unvisited node
        Tuple newPos = this.internalFloorPlan.unvisited.poll();

        //reset direction
        this.direction = NavigationOptionsEnum.EAST;

        if (newPos != null) {
            //System.out.println("Started pathfinding to unvisted");
            traverse(currentNode, newPos.node);
            return true;
        }
        else {
            return false;
        }
    }

    private float calculateMovingPowerComsumption(CleanSweepNode prevNode, CleanSweepNode node) {

        int toSurface = node.surface.getUnits();
        int fromSurface = prevNode.surface.getUnits();
        return (float) ((fromSurface + toSurface) / 2.0);
    }

    public void VisitNode(CleanSweepNode node) {

        node = internalFloorPlan.Add(node, position);
        this.currentNode = node;
        
        node.visited = true;
        //System.out.println("Visited Node with ID: " + node.id);
        //System.out.println("V," + node.id + "," + this.batteryCharge + "," + this.dirtCapacity);
        writeStuff("V," + node.id + "," + this.batteryCharge + "," + this.dirtCapacity);

        this.cleanNode(node);
    }

    private void cleanNode(CleanSweepNode node) {
        this.lastVisited = node;
        
        while(!node.isClean) // this should be a call to the simulator
        {
            if (checkCapacity())
            {
                if (checkPower(node.surface)) {
                    node.decreaseDirt(); // this should be a call to the simulator

                    this.decreasePower(node.surface.getUnits());
                    this.decreaseCapcity();

                    
                } else {
                    returnToChargingStation(ReturnReasons.POWER_DEFICIT);
                }
            } else {
                toggleEmptyMeLed();
                returnToChargingStation(ReturnReasons.CAPACITY_DEFICIT);
            }

        } 
        //System.out.println("Cleaned Node with ID: " + node.id);
        //System.out.println("C," + node.id + "," + this.batteryCharge + "," + this.dirtCapacity);

        writeStuff("C," + node.id + "," + this.batteryCharge + "," + this.dirtCapacity);
    }

    private void toggleEmptyMeLed() {
        this.emptyMeLed = !this.emptyMeLed;
    }

    private void returnToChargingStation(ReturnReasons reason) {
        //System.out.println("returning to charging station for reason " + reason.toString());
        tripsToStation++;
        CleanSweepNode lastVistedChargingStation = this.internalFloorPlan.FindClosestStation(this.currentNode);
        traverse(this.currentNode, lastVistedChargingStation);
        recharge();
        if (!(reason.equals(ReturnReasons.DEVICE_LOCKED) || reason.equals(ReturnReasons.HOUSE_CLEANED))){
            if (reason.equals(ReturnReasons.CAPACITY_DEFICIT))
                dispose();

            resume(this.lastVisited);
        }
        //shutdown(reason);
    }

    private void shutdown(ReturnReasons reason) {
        System.out.println(" Shutting Down: " + reason.toString());
    }

    private void resume(CleanSweepNode lastVisitedNode) {
        traverse(this.currentNode, lastVisitedNode);
        cleanHouse(lastVisitedNode);


    }

    private void traverse(CleanSweepNode fromNode, CleanSweepNode toNode) {
        //System.out.println("Traversing from " + fromNode.id + " to " + toNode.id); //no cleaning is performed but power should be managed for traversing
        List<CleanSweepNode> path = this.internalFloorPlan.aStar(fromNode, toNode);

            // Reverse list to get path from fromNode to toNode, then remove first entry since that is current node
            Collections.reverse(path);
            path.remove(0);

            CleanSweepNode prevNode = currentNode;
            for (CleanSweepNode steps : path) {
                //System.out.println("Pathfinding... Visited node: " + steps.id);
                //System.out.println("P," + steps.id + "," + this.batteryCharge + "," + this.dirtCapacity);
                writeStuff("P," + steps.id + "," + this.batteryCharge + "," + this.dirtCapacity);
                this.currentNode = steps;
                this.position = new Point(this.position.getX() + (steps.pos.getX()-this.position.getX()), this.position.getY()+(steps.pos.getY()-this.position.getY()));
                float movePowerComsumption = calculateMovingPowerComsumption(prevNode, currentNode);
                decreasePower(movePowerComsumption);
            }

        
    }

    private void recharge() {
        this.batteryCharge = Constants.MAX_POWER_CHARGE;
    }

    private void dispose(){
        this.dirtCapacity = Constants.MAX_DIRT_CAPACITY;
        toggleEmptyMeLed();
    }

    private void decreasePower(float units) {
        this.batteryCharge = this.batteryCharge - units;
    }

    private void decreaseCapcity() {
        this.dirtCapacity--;
    }

    private boolean checkCapacity() {
        return (this.dirtCapacity > 0);
    }

    private boolean checkPower(surfaceType surface) {
        boolean checkResult = false;
        int powerToReturn = calculatePowerToReturn();
        int powerToBeUsed = surface.getUnits();
        
        if (this.batteryCharge - powerToReturn - powerToBeUsed > 0)
            checkResult = true;
        
        return checkResult;
    }

    private int calculatePowerToReturn() {

        //System.out.println("From curr: " + this.currentNode.id + " to lastVistedStation: " + lastVistedChargingStation.id);

        CleanSweepNode lastVistedChargingStation = this.internalFloorPlan.FindClosestStation(this.currentNode);
        List<CleanSweepNode> path = this.internalFloorPlan.aStar(this.currentNode, lastVistedChargingStation);


        int totalCost = 0;
        CleanSweepNode holder = path.get(0);
        for (int i = 1; i < path.size(); i++) {
            CleanSweepNode curr = path.get(i);
            totalCost += (holder.surface.getUnits()+curr.surface.getUnits())/2;
            holder = curr;
        }

        return totalCost;
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
