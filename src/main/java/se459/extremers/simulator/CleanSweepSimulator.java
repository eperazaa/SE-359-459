package se459.extremers.simulator;

import se459.extremers.cleanSweepFloorPlan.FloorPlanExternal;

public class CleanSweepSimulator {
    FloorPlanExternal extFloorPlanCopy;

    public void CleanSweepSimulator(FloorPlanExternal extFloorPlan){
        this.extFloorPlanCopy = extFloorPlan;
    }

    public FloorPlanExternal getExternalFloorPlan() {
        return this.extFloorPlanCopy;
    }
    
}
