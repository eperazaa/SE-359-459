package se459.extremers.cleanSweepFloorPlan;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

// code retrived from: https://villavu.com/forum/showthread.php?t=95319
// I made a few changed and fixed a major issue
public class HomeMatrix {
	private int Width, Height;
    private CleanSweepNode Reference, RowIterator, ColumnIterator;
   
    public HomeMatrix(int width, int height) {
        this.Width = width;
        this.Height = height;
        Reference = new CleanSweepNode();
        RowIterator = ColumnIterator = Reference;
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

    public void CreateLinkedList(int Width, int Height) {
        for (int I = 0; I < Height; ++I) {
            for (int J = 0; J < Width; ++J) {
				// if we are on first row
                if (I == 0) {
                    if (J < Width - 1) {
                        RowIterator.eastNode = new CleanSweepNode();
                        RowIterator.eastNode.westNode = RowIterator;
                        RowIterator = RowIterator.eastNode;
                    }
                }
                else {  
                    if (J < Width - 1) {
                        RowIterator.eastNode = new CleanSweepNode();
                        RowIterator.northNode.southNode = RowIterator;
                        RowIterator.eastNode.westNode = RowIterator;
                        RowIterator.eastNode.northNode = RowIterator.northNode.eastNode;
                        RowIterator = RowIterator.eastNode;
                    }
					else {
						RowIterator.northNode.southNode = RowIterator;
					}
                }
            }
           
            if (I < Height - 1) {
                ColumnIterator.southNode = new CleanSweepNode();
				ColumnIterator.southNode.northNode = ColumnIterator;
				ColumnIterator = ColumnIterator.southNode;
				RowIterator = ColumnIterator;
            }
        }
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

    

	public int GetWidth() {
        return Width;
    }
   
    public int GetHeight() {
        return Height;
    }

	public CleanSweepNode GetNodeFromXY(int x, int y) {
			RowIterator = Reference;
       
        for (int I = 0; I < y; ++I) {
            RowIterator = RowIterator.southNode;
        }
       
        for (int J = 0; J < x; ++J) {
            RowIterator = RowIterator.eastNode;
        }
       
        return RowIterator;
	}

	public CleanSweepNode GetNodeFromNodeAndDirection(CleanSweepNode node, NavigationOptionsEnum direction) {
		RowIterator = Reference;

        // loop through linkedlist until we find node we passed in args
		for (int y = 0; y < this.Height; ++y) {
            for (int x = 0; x < this.Width; ++x) {
				CleanSweepNode tmp = GetNodeFromXY(x, y);

                // When node is found, check if direction is visited, if it is, rotate direction, if not, return new node in direction
				if(tmp == node) {

                    CleanSweepNode nodeToReturn = new CleanSweepNode();

                    for (int i = 0; i < 4; i++) {
                        switch (direction){
                            case EAST:
                                nodeToReturn = RowIterator.eastNode;
                                break;
                            case SOUTH:
                                nodeToReturn = RowIterator.southNode;
                                break;
                            case WEST:
                                nodeToReturn = RowIterator.westNode;
                                break;
                            case NORTH:
                                nodeToReturn = RowIterator.northNode;
                                break;
                            default:
                                break;
                        }
                        
                        // TODO: I don't want to revisit node right now but that might change
                        if (nodeToReturn.visited) {
                            direction = NavigationOptionsEnum.RotateDirection(direction);
                            node.direction = direction;
                        }
                        else {
                            break;
                        }
                    }

                    // Done Condition: if nodeToReturn is visted, that means we ran out of options
                    if (nodeToReturn.visited) {
                        return null;
                    }
                    else {
                        return nodeToReturn;
                    }
				}
			}
		}
		return null;
    }
}
