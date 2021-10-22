package se459.extremers.cleanSweepFloorPlan;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;


public class mainTest {
    public static void main(String[] args) throws FileNotFoundException{
    
        int maxCol = 4;
        int maxRow = 5;
        HomeMatrix homeMatrix = new HomeMatrix();
        CleanSweepRobot robot = new CleanSweepRobot();

        SetupFloorPlan(4, 5, homeMatrix);
        PrintFloorPlan(homeMatrix);

        // Map out the floor plan
        CleanSweepNode node = homeMatrix.GetNodeFromXY(0, 0);
        while (node != null) {
            robot.VisitNode(node);
            CleanSweepNode newNode = homeMatrix.GetNodeFromNodeAndDirection(node, robot.direction);

            node = newNode;
        } 

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


    public static void SetupFloorPlan(int maxCol, int maxRow, HomeMatrix homeMatrix) throws FileNotFoundException {
        
        int currCol = 0;
        int currRow = 0;
        homeMatrix.Matrix(maxCol, maxRow);
        

        Scanner scanner = new Scanner(new File("./src/test/file copy.csv"));
        while(scanner.hasNext()){
            String[] tokens = scanner.nextLine().split(",");
            String id = tokens[0];
            //SENSOR ARRAY VALUES
            edgeType n_sensor= edgeType.getEnumValue(tokens[1]); //path info top of the cell
            edgeType s_sensor=  edgeType.getEnumValue(tokens[2]); //path info bottom of the cell
            edgeType e_sensor=  edgeType.getEnumValue(tokens[3]); //path info right of the cell
            edgeType w_sensor=  edgeType.getEnumValue(tokens[4]); //path info left of the cell
            boolean bottom_sensor= Boolean.parseBoolean(tokens[5]);  //there is a stair/decline
            boolean dirt_sensor= Boolean.parseBoolean(tokens[6]); //whether there is dirt
            surfaceType surface_sensor=  surfaceType.valueOf(tokens[7]);  //kind of surface
            boolean charging_station=  Boolean.parseBoolean(tokens[8]); //whether there is a charging station

            CleanSweepNode tmp = new CleanSweepNode(Integer.parseInt(id), surface_sensor, bottom_sensor, dirt_sensor, n_sensor, s_sensor, e_sensor, w_sensor, null, null, null, null);

            homeMatrix.SetValues(currCol, currRow, tmp);

            //increment current col and row

            // check if at end of current col
            if (currCol == maxCol-1) {
                // check if also at end of row, if so, we are at end of matrix, break
                if (currRow == maxRow-1) {
                    break;
                }
                currCol = 0;
                currRow++;
            }
            else {
                currCol++;
            }

            
        }
    }


}
