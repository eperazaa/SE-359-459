package se459.extremers.cleanSweepFloorPlan;

public class CleanSweepNode  {
	int id;
	surfaceType surface;
	boolean dirt;
	boolean obstacle;
	boolean visited;
	
	edgeType northEdge, eastEdge, westEdge, southEdge;
	CleanSweepNode northNode, eastNode, southNode, westNode;

	public CleanSweepNode() {
		this.northNode =  null;
		this.eastNode = null;
		this.southNode = null;
		this.westNode = null;
		this.visited = false;
	}


	public CleanSweepNode(int id, surfaceType surface, boolean obstacle, boolean dirt, 
						  edgeType northEdge, edgeType southEdge, edgeType eastEdge, edgeType westEdge, 
						  CleanSweepNode northNode, CleanSweepNode eastNode, CleanSweepNode southNode, CleanSweepNode westNode) {
		this.id= id;
		this.surface = surface;
		this.obstacle = obstacle;
		this.dirt = dirt;
		
		this.northEdge = northEdge;
		this.eastEdge = eastEdge;
		this.southEdge = southEdge;
		this.westEdge = westEdge;

		this.northNode = northNode;
		this.eastNode = eastNode;
		this.southNode = southNode;
		this.westEdge = westEdge;
	}

	
}


	