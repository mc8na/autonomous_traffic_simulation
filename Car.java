// Author: FLIES (Miles, Nick, Rishabh, Sinan)

import java.util.*;
import java.io.*;

// *****************************************************************************
// *****************************************************************************
// **** Car
// *****************************************************************************
// *****************************************************************************

public class Car {

  private int direction, blocksToTravel, turnAction,
              gridEntryTime, ID, segmentEntryTime;


  public Car(int ID, int blocks, int action, int entryTime) {
    this.ID = ID;
    this.blocksToTravel = blocks;
    this.turnAction = action;
    this.gridEntryTime = entryTime;
    this.segmentEntryTime = entryTime;
  } // end Car constructor


  public int getID() { 
    return ID; 
  } // end of getID

  
  public int getTurnAction() { 
    if(blocksToTravel == 0)
      return turnAction;
    return 0;
  } // end of getTurnAction

  
  public int getGridEntryTime() { 
    return gridEntryTime; 
  } // end of getGridEntryTime

  
  public int getSegmentEntryTime() { 
    return segmentEntryTime; 
  } // end of getSegmentEntryTime

  
  public void setBlocksToTravel() { 
    blocksToTravel--; 
  } // end of setBlocksToTravel

  
  public void setSegmentEntryTime(int newTime) { 
    segmentEntryTime = newTime; 
  } // end of setSegmentEntryTime
  
  
} // end of Car class
