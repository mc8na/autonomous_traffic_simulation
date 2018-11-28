// Author: FLIES (Miles, Nick, Rishabh, Sinan)

import java.util.*;

// *****************************************************************************
// *****************************************************************************
// **** Simulation
// *****************************************************************************
// *****************************************************************************

public class Simulation {

// NOTE: Just to keep the current tester program really simple,
// the following constants are not encapsulated within two classes, Direction
// and Turn, according to appropriate Information Hiding:
  static final int SOUTHWARD = 0;
  static final int EASTWARD = 1;
  static final int NORTHWARD = 2;
  static final int WESTWARD = 3;
  static final int NEVER_TURN = 0;
  static final int TURN_RIGHTWARD = 1;
  static final int TURN_LEFTWARD = -1;

  public static void main(String[] args) {
    Scanner console = new Scanner(System.in);
    console.nextLine();
    int numTimeStepsInSimulation = console.nextInt();
    System.out.print("The number of time units requested is: ");
    System.out.println(numTimeStepsInSimulation);
    if (numTimeStepsInSimulation <= 0) {
      System.out.println("Invalid number of time steps for simulation: ");
      System.out.println("  Input must be an integer greater than zero");
      System.exit(1);
    } // end of if
    console.nextLine();
    console.nextLine();
    int maxSegmentCapacity = console.nextInt();
    System.out.print("The requested maximum segment capacity (in cars) is: ");
    System.out.println(maxSegmentCapacity);
    if (maxSegmentCapacity <= 0) {
      System.out.println("Invalid maximum segment capacity");
      System.out.println("  Input must be an integer greater than zero");
      System.exit(1);
    } // end of if
    console.nextLine();
    console.nextLine();
    int timeToTraverseSegment = console.nextInt();
    System.out.print("The requested time to traverse segment is: ");
    System.out.println(timeToTraverseSegment);
    if (timeToTraverseSegment < 0) {
      System.out.println("Invalid time to traverse segment: ");
      System.out.println("  Input must be a non-negative integer");
      System.exit(1);
    } // end of if
    console.nextLine();
    console.nextLine();
    int numIntersectionsInOneDirection = console.nextInt();
    System.out.print("The number of intersections in one direction ");
    System.out.println("is: " + numIntersectionsInOneDirection);
    if (numIntersectionsInOneDirection <= 0 || numIntersectionsInOneDirection > 20) {
      System.out.println("Invalid number of intersections in one direction.");
      System.exit(1);
    } // end of if
    console.nextLine();
    console.nextLine();
    int numberOfCars = console.nextInt();
    System.out.println("The number of cars is: " + numberOfCars);
    if (numberOfCars <= -1) {
      System.out.println("Invalid number of cars.");
      System.exit(1);
    } // end of if
    int carID;
    int row;
    int col;
    int segmentDirectionCode;
    int numBlocksBeforeTurning;
    int turnDirectionCode;
    City city = new City(numIntersectionsInOneDirection,maxSegmentCapacity,
    	                 timeToTraverseSegment);
    for (int i = 1; i <= numberOfCars; i++) {
      console.nextLine();
      console.nextLine();
      carID = console.nextInt();
      System.out.println("Car #" + carID);
      console.nextLine();
      console.nextLine();
      col = console.nextInt();
      if (col <= 0 || col > numIntersectionsInOneDirection) {
        System.out.println("Invalid value of column to put car in.");
        System.exit(1);
      } // end of if
      console.nextLine();
      console.nextLine();
      row = console.nextInt();
      if (row <= 0 || row > numIntersectionsInOneDirection) {
        System.out.println("Invalid value of row to put car in.");
        System.exit(1);
      } // end of if
      console.nextLine();
      console.nextLine();
      segmentDirectionCode = console.nextInt();
      if (segmentDirectionCode <= -1 || segmentDirectionCode > 3) {
        System.out.println("Invalid value of segment direction code.");
        System.exit(1);
      } // end of if
      console.nextLine();
      console.nextLine();
      numBlocksBeforeTurning = console.nextInt();
      if (numBlocksBeforeTurning <= -2) {
        System.out.println("Invalid value of number of blocks before turning.");
        System.exit(1);
      } // end of if
      console.nextLine();
      console.nextLine();
      turnDirectionCode = console.nextInt();
      if (turnDirectionCode <= -2 || turnDirectionCode > 1) {
        System.out.println("Invalid value of turn direction code.");
        System.exit(1);
      } // end of if
      System.out.println("  is born in the segment located at col " + col +
                         " and row " + row + ", that aims " +
                         convertToSegmentDirection(segmentDirectionCode) + ",");
      System.out.println("  and has " + numBlocksBeforeTurning +
                         " block(s) to go before turning");
      System.out.println("  and plans to " +
                         convertToTurnDirection(turnDirectionCode));
      city.addCar(carID,col,row,segmentDirectionCode,numBlocksBeforeTurning,
                  turnDirectionCode,0);
    } // end of for
    for (int timestamp = 1; timestamp <= numTimeStepsInSimulation; timestamp++) {
      System.out.println();
      System.out.println("TIME UNIT " + timestamp + " BEGINS");
      System.out.println();
      city.updateSimulation(timestamp);
    } // end of for
    if (city.getAverageTime() < 0) {
      System.out.println("No cars left the grid");
    } else {
      System.out.print("The average time in the grid for cars that left ");
      System.out.print("the grid is: ");
      System.out.printf("%.2f\n", city.getAverageTime());
    } // end of else

  } // main


  public static String convertToSegmentDirection(int segmentDirectionCode) {
    if (segmentDirectionCode == NORTHWARD)      return "NORTHWARD";
    if (segmentDirectionCode == WESTWARD)       return "WESTWARD";
    if (segmentDirectionCode == SOUTHWARD)      return "SOUTHWARD";
    if (segmentDirectionCode == EASTWARD)       return "EASTWARD";
    return "ILLEGAL segmentDirectionCode!!!";
  } // convertToSegmentDirection

 
  public static String convertToTurnDirection(int turnDirectionCode) {
    if (turnDirectionCode == NEVER_TURN)     return "NEVER_TURN";
    if (turnDirectionCode == TURN_RIGHTWARD) return "TURN_RIGHTWARD";
    if (turnDirectionCode == TURN_LEFTWARD)  return "TURN_LEFTWARD";
    return "ILLEGAL turnDirectionCode!!!";
  } // convertToTurnDirection


} // Simulation

// The following comment block is for an outdated sample use of the program

////////////////// Example of sample use of the above program //////////////////
// except for the fact that all the following lines have been made comments,  //
// and also the actual phrase "additional blocks to travel" in the data       //
// shown below has been shortened to the phrase "additional blocks" to fit    //
// the lines below into the required 80-columns per line.                     //
////////////////////////////////////////////////////////////////////////////////
//numIntersectionsInOneDirection:
//1
//number of cars created for the test:
//2
//car number:
//1
//born in the segment that is positioned at col:
//1
//born in the segment that is positioned at row:
//1
//born in the segment that is oriented in direction:
//0
//additional blocks prior to turning (-1 means the car will never turn):
//0
//direction the car will turn, when the car turns:
//1
//car number:
//2
//born in the segment that is positioned at col:
//1
//born in the segment that is positioned at row:
//1
//born in the segment that is oriented in direction:
//3
//additional blocks prior to turning (-1 means the car will never turn):
//-1
//direction the car will turn, when the car turns:
//0
//hopper2{acharles}2484: java TrafficTesterView < data1_for_TrafficTesterView
//The number of intersections in one direction is: 1
//The number of cars is: 2
//Car #1
//  is born in the segment located at col 1 and row 1, that aims SOUTHWARD,
//  and has 0 block(s) to go before turning
//  and plans to TURN_RIGHTWARD
//Car #2
//  is born in the segment located at col 1 and row 1, that aims WESTWARD,
//  and has -1 block(s) to go before turning
//  and plans to NEVER_TURN
