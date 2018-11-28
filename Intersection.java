// Author: FLIES (Miles, Nick, Rishabh, Sinan)

import java.util.*;
import java.io.*;
import java.lang.*;

// *****************************************************************************
// *****************************************************************************
// **** Intersection
// *****************************************************************************
// *****************************************************************************
 
public class Intersection {

  private Segment[] incoming, outgoing;
  private int[][] slot, carMovedFrom;
  private int timeToGoStraight, timeToTurnRight, timeToTurnLeft;

  private static final int SOUTHWARD = Simulation.SOUTHWARD;
  private static final int EASTWARD = Simulation.EASTWARD;
  private static final int NORTHWARD = Simulation.NORTHWARD;
  private static final int WESTWARD = Simulation.WESTWARD;
  private static final int NEVER_TURN = Simulation.NEVER_TURN;
  private static final int TURN_RIGHTWARD = Simulation.TURN_RIGHTWARD;
  private static final int TURN_LEFTWARD = Simulation.TURN_LEFTWARD;


  public Intersection(Segment incomingSouthward, Segment incomingEastward,
                      Segment incomingNorthward, Segment incomingWestward,
                      Segment outgoingSouthward, Segment outgoingEastward,
                      Segment outgoingNorthward, Segment outgoingWestward) {
    incoming = new Segment[4];
    outgoing = new Segment[4];
    incoming[SOUTHWARD] = incomingSouthward;
    incoming[EASTWARD] = incomingEastward;
    incoming[NORTHWARD] = incomingNorthward;
    incoming[WESTWARD] = incomingWestward;
    outgoing[SOUTHWARD] = outgoingSouthward;
    outgoing[EASTWARD] = outgoingEastward;
    outgoing[NORTHWARD] = outgoingNorthward;
    outgoing[WESTWARD] = outgoingWestward;
    slot = new int[2][2];
    carMovedFrom = new int[4][2];
    timeToGoStraight = timeToTurnRight = timeToTurnLeft = 0;
  } // end of Intersection constructor

  public void advanceCars(int timestamp) {
    for(int i = 0; i < 2; i++) {
      for(int j = 0; j < 2; j++) {
        if(slot[i][j] > 0) {
          slot[i][j] = slot[i][j] - 1;
        } // end of if
      } // end of for
    } // end of for
    for(int i = 0; i < 4; i++) {
      carMovedFrom[i][0] = 4;
    } // end of for

    if(!handleStraight(timestamp)) {
      // no cars going straight through the intersection
      // entered the intersection
      if(!handleRightTurn(timestamp)) {
      	// no cars turning right in the intersection entered the intersection
        handleLeftTurn(timestamp);
      }
    }

    for(int dir = 0; dir < 4; dir++) {
      System.out.print("  incoming segment having direction " +
                 Simulation.convertToSegmentDirection(dir) + " is ");

      if(carMovedFrom[dir][0] < 4) { // a car moved from that segment
          System.out.println("nonempty");
          System.out.println("    car#" + carMovedFrom[dir][1] +
            " is removed and placed into outgoing segment having direction " +
            Simulation.convertToSegmentDirection(carMovedFrom[dir][0]));
      } else if(!incoming[dir].isEmpty()) {
        System.out.println("nonempty");
      } else {
        System.out.println("empty");
      } // end of else

    } // end of for

    for(int dir = 0; dir < 4; dir++) {
      System.out.print("  outgoing segment having direction " +
                 Simulation.convertToSegmentDirection(dir) + " is ");
      if(outgoing[dir].isEmpty()) {
        System.out.println("empty");
      } else {
        System.out.println("nonempty");
      } // end of else
    } // end of for
    
    System.out.println();
  } // end of advanceCars

  private boolean isPathClear(int dir, int turnAction) {
    if(dir == SOUTHWARD) {
      if(turnAction == NEVER_TURN) {
        return (slot[0][0] == 0 && slot[0][1] == 0);
      } else if(turnAction == TURN_RIGHTWARD) {
        return (slot[0][1] == 0);
      } else {
        return (slot[0][1] == 0 && slot[0][0] == 0 && slot[1][0] == 0);
      } // end of else
    } else if(dir == EASTWARD) {
      if(turnAction == NEVER_TURN) {
        return (slot[0][0] == 0 && slot[1][0] == 0);
      } else if(turnAction == TURN_RIGHTWARD) {
        return (slot[0][0] == 0);
      } else {
        return (slot[0][0] == 0 && slot[1][0] == 0 && slot[1][1] == 0);
      } // end of else
    } else if(dir == NORTHWARD) {
      if(turnAction == NEVER_TURN) {
        return (slot[1][0] == 0 && slot[1][1] == 0);
      } else if(turnAction == TURN_RIGHTWARD) {
        return (slot[1][0] == 0);
      } else {
        return (slot[1][0] == 0 && slot[1][1] == 0 && slot[0][1] == 0);
      } // end of else
    } else {
      if(turnAction == NEVER_TURN) {
        return (slot[0][1] == 0 && slot[1][1] == 0);
      } else if(turnAction == TURN_RIGHTWARD) {
        return (slot[1][1] == 0);
      } else {
        return (slot[1][1] == 0 && slot[0][1] == 0 && slot[0][0] == 0);
      } // end of else
    } // end of else

  } // end of isPathClear

  private void setPath(int dir, int turnAction) {
    if(dir == SOUTHWARD){
      if(turnAction == NEVER_TURN) {
        slot[0][0] = slot[0][1] = Math.max(timeToGoStraight,1);
      } else if(turnAction == TURN_RIGHTWARD) {
        slot[0][1] = Math.max(timeToTurnRight,1);
      } else {
        slot[0][1] = slot[0][0] = slot[1][0] = Math.max(timeToTurnLeft,1);
      } // end of else
    } else if(dir == EASTWARD) {
      if(turnAction == NEVER_TURN) {
        slot[0][0] = slot[1][0] = Math.max(timeToGoStraight,1);
      } else if(turnAction == TURN_RIGHTWARD) {
        slot[0][0] = Math.max(timeToTurnRight,1);
      } else {
        slot[0][0] = slot[1][0] = slot[1][1] = Math.max(timeToTurnLeft,1);
      } // end of else
    } else if(dir == NORTHWARD) {
      if(turnAction == NEVER_TURN) {
        slot[1][0] = slot[1][1] = Math.max(timeToGoStraight,1);
      } else if(turnAction == TURN_RIGHTWARD) {
        slot[1][0] = Math.max(timeToTurnRight,1);
      } else {
        slot[1][0] = slot[1][1] = slot[0][1] = Math.max(timeToTurnLeft,1);
      } // end of else
    } else {
      if(turnAction == NEVER_TURN) {
        slot[0][1] = slot[1][1] = Math.max(timeToGoStraight,1);
      } else if(turnAction == TURN_RIGHTWARD) {
        slot[1][1] = Math.max(timeToTurnRight,1);
      } else {
        slot[1][1] = slot[0][1] = slot[0][0] = Math.max(timeToTurnLeft,1);
      } // end of else
    } // end of else

  } // end of setPath

  private boolean handleStraight(int timestamp){
    for(int dir = 0; dir < 4; dir++) {
      Car c = incoming[dir].getCar(timestamp);
      if(c != null && c.getTurnAction() == NEVER_TURN) {
        if(outgoing[dir].isAvailable(timestamp) &&
             isPathClear(dir, NEVER_TURN) ) {
          
          setPath(dir,NEVER_TURN);
          carMovedFrom[dir][0] = dir;
          carMovedFrom[dir][1] = c.getID();
          c.setBlocksToTravel();
          c.setSegmentEntryTime(timestamp + timeToGoStraight);
          incoming[dir].removeCar();
          outgoing[dir].addCar(c);
          return true;
        } // end of if
      } // end of if

    } // end of for
    return false;
  } // end of handleStraight

  private boolean handleRightTurn(int timestamp) {
    for(int dir = 0; dir < 4; dir++) {
      Car c = incoming[dir].getCar(timestamp);
      if(c != null && c.getTurnAction() == TURN_RIGHTWARD &&
                                   carMovedFrom[dir][0] == 4) {
        int newdir = newDirection(dir,TURN_RIGHTWARD);
        if(outgoing[newdir].isAvailable(timestamp) &&
                isPathClear(dir,TURN_RIGHTWARD) ) {
          
          setPath(dir,TURN_RIGHTWARD);
          carMovedFrom[dir][0] = newdir;
          carMovedFrom[dir][1] = c.getID();
          c.setBlocksToTravel();
          c.setSegmentEntryTime(timestamp + timeToTurnRight);
          incoming[dir].removeCar();
          outgoing[newdir].addCar(c);
          return true;
        } // end of if
      } // end of if

    } // end of for
    return false;
  } // end of handleRightTurn

  private boolean handleLeftTurn(int timestamp) {
    for(int dir = 0; dir < 4; dir++) {
      Car c = incoming[dir].getCar(timestamp);
      if(c != null && c.getTurnAction() == TURN_LEFTWARD &&
                                  carMovedFrom[dir][0] == 4) {
        int newdir = newDirection(dir,TURN_LEFTWARD);
        if(outgoing[newdir].isAvailable(timestamp) &&
                isPathClear(dir,TURN_LEFTWARD) ) {
          
          setPath(dir,TURN_LEFTWARD);
          carMovedFrom[dir][0] = newdir;
          carMovedFrom[dir][1] = c.getID();
          c.setBlocksToTravel();
          c.setSegmentEntryTime(timestamp + timeToTurnLeft);
          incoming[dir].removeCar();
          outgoing[newdir].addCar(c);
          return true;
        } // end of if
      } // end of if

    } // end of for
    return false;
  } // end of handleLeftTurn

  /*
  This function takes an integer dir representing the direction a car is
  traveling, and an integer representing the way that car will turn, and
  returns which direction that car will be traveling when it completes the
  action of turning. For example, a car traveling Westward (3) that will turn
  left (-1) will end up traveling Southward (0) after it turns. Indeed,
  (3 - (-1) + 4) % 4 = 8 % 4 = 0.
  To explain, the integers representing directions are arranged in increasing
  order counterclockwise, from Southward = 0 to Westward = 3. A car going
  straight will not changed direction, and indeed since NEVER_TURN = 0,
  newdir = (dir - 0 + 4) % 4 = dir. A car that will TURN_RIGHTWARD = 1 will
  end up traveling in the direction one step clockwise, and a car that will
  TURN_LEFTWARD will end up traveling in the direction one step
  counterclockwise, which means we must subtract the turnAction of the car
  from direction to obtain the new direction. We add 4 to this value and
  take the sum mod 4 to obtain a number 0,1,2,3.
  */
  private int newDirection(int dir, int turnAction) {
    return (dir - turnAction + 4) % 4;

    // In case direction and turn action values change
    /*
    if(dir == SOUTHWARD) {
      if(turnAction == TURN_RIGHTWARD) {
        return WESTWARD;
      } else if(turnAction == TURN_LEFTWARD) {
        return EASTWARD;
      } else {
        return dir;
      } // end of else
    } else if(dir == EASTWARD) {
      if(turnAction == TURN_RIGHTWARD) {
        return SOUTHWARD;
      } else if(turnAction == TURN_LEFTWARD) {
        return NORTHWARD;
      } else {
        return dir;
      } // end of else
    } else if(dir == NORTHWARD) {
      if(turnAction == TURN_RIGHTWARD) {
        return EASTWARD;
      } else if(turnAction == TURN_LEFTWARD) {
        return WESTWARD;
      } else {
        return dir;
      } // end of else
    } else { // dir == WESTWARD
      if(turnAction == TURN_RIGHTWARD) {
        return NORTHWARD;
      } else if(turnAction == TURN_LEFTWARD) {
        return SOUTHWARD;
      } else {
        return dir;
      } // end of else
    } // end of else
    */

  } // end of newDirection

    
} // end of Intersection class
