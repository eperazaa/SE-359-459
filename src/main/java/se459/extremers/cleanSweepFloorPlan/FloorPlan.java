package se459.extremers.cleanSweepFloorPlan;

public class FloorPlan {
    protected int Width, Height;
    protected CleanSweepNode Reference, RowIterator, ColumnIterator;

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
}
