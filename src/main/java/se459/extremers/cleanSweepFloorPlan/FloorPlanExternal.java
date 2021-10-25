package se459.extremers.cleanSweepFloorPlan;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class FloorPlanExternal extends FloorPlan {

    public FloorPlanExternal(int width, int height) throws FileNotFoundException {
        this.Width = width;
        this.Height = height;
        Reference = new CleanSweepNode();
        RowIterator = ColumnIterator = Reference;

        this.SetupFloorPlan();
    }

    public void SetupFloorPlan() throws FileNotFoundException {
        
        int currCol = 0;
        int currRow = 0;

        this.CreateLinkedList(this.Width, this.Height);
        
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

            this.SetValues(currCol, currRow, tmp);

            //increment current col and row

            // check if at end of current col
            if (currCol == this.Width-1) {
                // check if also at end of row, if so, we are at end of matrix, break
                if (currRow == this.Height-1) {
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
    
    public CleanSweepNode GetNodeFromNodeAndDirection(CleanSweepNode node, NavigationOptionsEnum direction) {
		RowIterator = Reference;

        // loop through linkedlist until we find node we passed in args
		for (int y = 0; y < this.Height; ++y) {
            for (int x = 0; x < this.Width; ++x) {
				CleanSweepNode tmp = GetNodeFromXY(x, y);

                // When node is found return new node in direction
				if(tmp == node) {
                    for (int i = 0; i < 4; i++) {
                        switch (direction){
                            case EAST:
                                return tmp.eastNode;
                            case SOUTH:
                                return tmp.southNode;
                            case WEST:
                                return tmp.westNode;
                            case NORTH:
                                return tmp.northNode;
                            default:
                                break;
                        }
                    }
				}
			}
		}
		return null;
    }

    public void SetValues(int X, int Y, CleanSweepNode copyReference) {
        RowIterator = Reference;
       
        for (int I = 0; I < Y; ++I) {
            RowIterator = RowIterator.southNode;
        }
       
        for (int J = 0; J < X; ++J) {
            RowIterator = RowIterator.eastNode;
        }
       
		// this is probably a really bad way of doing this
		RowIterator.id = copyReference.id;
        RowIterator.northEdge = copyReference.northEdge;
		RowIterator.eastEdge = copyReference.eastEdge;
		RowIterator.southEdge = copyReference.southEdge;
		RowIterator.westEdge = copyReference.westEdge;

		RowIterator.surface = copyReference.surface;
		RowIterator.dirt = copyReference.dirt;
		RowIterator.obstacle = copyReference.obstacle;

	}
    
}