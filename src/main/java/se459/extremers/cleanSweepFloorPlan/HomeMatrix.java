package se459.extremers.cleanSweepFloorPlan;

import java.io.Serializable;

public class HomeMatrix implements Serializable{
	private static final long serialVersionUID = 7526472295622776147L;
	int arraySplit;
	// where a row becomes the next row conceptually
	// should be a denominator of array length
	CleanSweepNode[] matrixCont;
	// holds all of the cleanSweepNodes
	int chargingStation;
	
	public  HomeMatrix(int arraySplit, int arrayLen,int chargingStation) {
		this.arraySplit = arraySplit;
		this.matrixCont = new CleanSweepNode[arrayLen];
		this.chargingStation = chargingStation;
		
		
	}
	
	public void tester() {
		HomeMatrix x = new HomeMatrix(9,100,45);
		x.matrixCont[0]= new CleanSweepNode(1,2,false,3); 
		x.matrixCont[0].eastEdge = edgeType.wall;
		for(int i=0;i<x.matrixCont.length;i++) {
			x.matrixCont[i]= new CleanSweepNode(1,2,false,3); 
			
		}

	}
	
}
