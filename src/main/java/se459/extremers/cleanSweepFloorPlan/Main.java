package se459.extremers.cleanSweepFloorPlan;

import se459.extremers.simulator.CleanSweepSimulator;

import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws FileNotFoundException{
    
        // This is the external map of the house that the robot is not aware of
        int maxCol = 4;
        int maxRow = 5;
        //FloorPlanExternal externalFloorPlan = new FloorPlanExternal(maxCol, maxRow, "./src/test/file copy.csv");
        String path = "./src/test/externalTree_v1.csv";
        //PrintFloorPlan(externalFloorPlan);
        
        // This is where we connect the two and start
       
        CleanSweepSimulator simulator = new CleanSweepSimulator(maxCol, maxRow, path);
        CleanSweepNode startingPoint = simulator.getExternalFloorPlan().GetNodeFromXY(0, 4);
        //simulator.loadFloorPlan(maxCol, maxRow, path);

        CleanSweepRobot robot = new CleanSweepRobot(simulator);
        robot.cleanHouse(startingPoint);

        /*
        //Testing
        for (Point pos: robot.internalFloorPlan.doors.keySet()) {
            String key = "Key (X: " + pos.getX() + " Y: " + pos.getY() + ")";
            String value = "Value (Node ID: " + robot.internalFloorPlan.doors.get(pos).id + ")";
            System.out.println(key + " " + value);
        }
        */
    }


    private static void PrintFloorPlan(FloorPlanExternal floorPlan) {

        for (int I = 0; I < floorPlan.GetHeight(); ++I) {
            for (int J = 0; J < floorPlan.GetWidth(); ++J) {
                CleanSweepNode currNode = floorPlan.GetNodeFromXY(J, I);

                int northID;
                int eastID;
                int southID;
                int westID;
                int currID;
                try {
                    northID = currNode.northNode.id;
                }
                catch (NullPointerException e) {
                    northID = -1;
                }
                try {
                    eastID = currNode.eastNode.id;
                }
                catch (NullPointerException e) {
                    eastID = -1;
                }
                try {
                    southID = currNode.southNode.id;
                }
                catch (NullPointerException e) {
                    southID = -1;
                }
                try {
                    westID = currNode.westNode.id;
                }
                catch (NullPointerException e) {
                    westID = -1;
                }

                try {
                    currID = currNode.id;
                }
                catch (NullPointerException e) {
                    currID = -1;
                }

                //System.out.print("| NodeID: " + currID + " ");
                System.out.println("Node ID: " + currID);
                System.out.println(" Connections = (N:" + northID + " E:" + eastID + " S:" + southID + " W:" + westID +")");
                System.out.println(" Edges = (N:" + currNode.northEdge + " E:" + currNode.eastEdge + " S:" + currNode.southEdge + " W:" + currNode.westEdge +")");
            }
            System.out.println();
        }
        System.out.println();

    }



}
