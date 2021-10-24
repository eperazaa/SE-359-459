package se459.extremers.cleanSweepFloorPlan;

import java.io.FileNotFoundException;

public class mainTest {
    public static void main(String[] args) throws FileNotFoundException{
    
        int maxCol = 4;
        int maxRow = 5;
        HomeMatrix homeMatrix = new HomeMatrix(maxCol, maxRow);
        CleanSweepRobot robot = new CleanSweepRobot();

        homeMatrix.SetupFloorPlan();
        PrintFloorPlan(homeMatrix);

        robot.MapInitalLayout(homeMatrix);
    }


    private static void PrintFloorPlan(HomeMatrix homeMatrix) {

        for (int I = 0; I < homeMatrix.GetHeight(); ++I) {
            for (int J = 0; J < homeMatrix.GetWidth(); ++J) {
                CleanSweepNode currNode = homeMatrix.GetNodeFromXY(J, I);

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
