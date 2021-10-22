package se459.extremers.cleanSweepFloorPlan;

// code retrived from: https://villavu.com/forum/showthread.php?t=95319
// I made a few changed and fixed a major issue
public class HomeMatrix {
	private int Width, Height;
    private CleanSweepNode Reference, RowIterator, ColumnIterator;
   

    public void Matrix(int Width, int Height) {
        Reference = new CleanSweepNode();
        this.Width = Width; 
		this.Height = Height;
        RowIterator = ColumnIterator = Reference;
       
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

		//RowIterator.roomNumber = copyReference.roomNumber;
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

		for (int y = 0; y < this.Height; ++y) {
            for (int x = 0; x < this.Width; ++x) {
				CleanSweepNode tmp = GetNodeFromXY(x, y);

				if(tmp == node) {

                    CleanSweepNode nodeToReturn = new CleanSweepNode();

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

                    // TODO I don't want to revisit node right now but that might change
                    if (nodeToReturn == null || nodeToReturn.visited) {
                        break;
                    }

                    return nodeToReturn;
				}
			}
		}
		return null;
}
	
}
