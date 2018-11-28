// Author: FLIES (Miles, Nick, Rishabh, Sinan)

import java.util.*;

// *****************************************************************************
// *****************************************************************************
// **** RandomSimulation
// *****************************************************************************
// *****************************************************************************

public class RandomSimulation {

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
    console.nextLine();
    console.nextLine();
    int maxSegmentCapacity = console.nextInt();
    System.out.print("The requested maximum segment capacity (in cars) is: ");
    System.out.println(maxSegmentCapacity);
    console.nextLine();
    console.nextLine();
    int timeToTraverseSegment = console.nextInt();
    System.out.print("The requested time to traverse segment is: ");
    System.out.println(timeToTraverseSegment);
    console.nextLine();
    console.nextLine();
    int numIntersectionsInOneDirection = console.nextInt();
    System.out.print("The number of intersections in one direction ");
    System.out.println("is: " + numIntersectionsInOneDirection);
    console.nextLine();
    console.nextLine();
    int maxNumberOfCars = console.nextInt();
    System.out.println("The maximum number of cars is: " + maxNumberOfCars);
    console.nextLine();
    console.nextLine();
    double probNewCar = console.nextDouble();
    System.out.print("The probability that a car will");
    System.out.println(" enter the grid is: " + probNewCar);
    console.nextLine();
    console.nextLine();
    double probRightTurn = console.nextDouble();
    System.out.print("The probability that a car will");
    System.out.println(" turn right is: " + probRightTurn);
    console.nextLine();
    console.nextLine();
    double probLeftTurn = console.nextDouble();
    System.out.print("The probability that a car will");
    System.out.println(" turn left is: " + probLeftTurn);

    Random rand = new Random();
    rand.setSeed(1);
    int numCars = 0;

    City city = new City(numIntersectionsInOneDirection,maxSegmentCapacity,
                       timeToTraverseSegment);

    
    for (int time = 0; time <= numTimeStepsInSimulation &&
                       numCars < maxNumberOfCars; time++) {
      for (int row = 1; row <= numIntersectionsInOneDirection &&
                        numCars < maxNumberOfCars; row++) {
        if(rand.nextDouble() <= probNewCar) {
          numCars++;
          int direction = rand.nextInt(4);
          int coordinate = rand.nextInt(numIntersectionsInOneDirection) + 1;
          int turnDirectionCode = NEVER_TURN;
          double turn = rand.nextDouble();
          if(turn <= probRightTurn) {
            turnDirectionCode = TURN_RIGHTWARD;
          } else if(turn >= 1 - probLeftTurn) {
            turnDirectionCode = TURN_LEFTWARD;
          } // end of else if
          int numBlocks = rand.nextInt(numIntersectionsInOneDirection);
          city.addCar(numCars,1,row,EASTWARD,numBlocks,
            turnDirectionCode,time);
        } // end of if
      } // end of for

      for (int col = 1; col <= numIntersectionsInOneDirection &&
                        numCars < maxNumberOfCars; col++) {
        if(rand.nextDouble() <= probNewCar) {
          numCars++;
          int direction = rand.nextInt(4);
          int coordinate = rand.nextInt(numIntersectionsInOneDirection) + 1;
          int turnDirectionCode = NEVER_TURN;
          double turn = rand.nextDouble();
          if(turn <= probRightTurn) {
            turnDirectionCode = TURN_RIGHTWARD;
          } else if(turn >= 1 - probLeftTurn) {
            turnDirectionCode = TURN_LEFTWARD;
          } // end of else if
          int numBlocks = rand.nextInt(numIntersectionsInOneDirection);
          city.addCar(numCars,col,numIntersectionsInOneDirection,SOUTHWARD,
            numBlocks,turnDirectionCode,time);
        } // end of if
      } // end of for

      for (int row = 1; row <= numIntersectionsInOneDirection &&
                        numCars < maxNumberOfCars; row++) {
        if(rand.nextDouble() <= probNewCar) {
          numCars++;
          int direction = rand.nextInt(4);
          int coordinate = rand.nextInt(numIntersectionsInOneDirection) + 1;
          int turnDirectionCode = NEVER_TURN;
          double turn = rand.nextDouble();
          if(turn  <= probRightTurn) {
            turnDirectionCode = TURN_RIGHTWARD;
          } else if(turn >= 1 - probLeftTurn) {
            turnDirectionCode = TURN_LEFTWARD;
          } // end of else if
          int numBlocks = rand.nextInt(numIntersectionsInOneDirection);
          city.addCar(numCars,numIntersectionsInOneDirection,row,WESTWARD,
            numBlocks,turnDirectionCode,time);
        } // end of if
      } // end of for

      for (int col = 1; col <= numIntersectionsInOneDirection &&
                        numCars < maxNumberOfCars; col++) {
        if(rand.nextDouble() <= probNewCar) {
          numCars++;
          int direction = rand.nextInt(4);
          int coordinate = rand.nextInt(numIntersectionsInOneDirection) + 1;
          int turnDirectionCode = NEVER_TURN;
          double turn = rand.nextDouble();
          if(turn <= probRightTurn) {
            turnDirectionCode = TURN_RIGHTWARD;
          } else if(turn >= 1 - probLeftTurn) {
            turnDirectionCode = TURN_LEFTWARD;
          } // end of else if
          int numBlocks = rand.nextInt(numIntersectionsInOneDirection);
          city.addCar(numCars,col,1,NORTHWARD,numBlocks,
            turnDirectionCode,time);
        } // end of if
      } // end of for

      /*
      if(rand.nextDouble() <= probNewCar) { 
        // new car enters grid in this time unit
        numCars++;
        int direction = rand.nextInt(4);
        int coordinate = rand.nextInt(numIntersectionsInOneDirection) + 1;
        int turnDirectionCode = NEVER_TURN;
        double r = rand.nextDouble();
        if(r <= probRightTurn) {
          turnDirectionCode = TURN_RIGHTWARD;
        } else if(r <= probRightTurn + probLeftTurn) {
          turnDirectionCode = TURN_LEFTWARD;
        } // end of else if
        int numBlocksBeforeTurning = rand.nextInt(numIntersectionsInOneDirection);
        if(direction == EASTWARD) {
          city.addCar(numCars,1,coordinate,direction,numBlocksBeforeTurning,
            turnDirectionCode,time);
        } else if(direction == WESTWARD) {
          city.addCar(numCars,numIntersectionsInOneDirection,coordinate,
            direction,numBlocksBeforeTurning,turnDirectionCode,time);
        } else if(direction == NORTHWARD) {
          city.addCar(numCars,coordinate,1,direction,numBlocksBeforeTurning,
            turnDirectionCode,time);
        } else { // direction == SOUTHWARD
          city.addCar(numCars,numIntersectionsInOneDirection,coordinate,
            direction,numBlocksBeforeTurning,turnDirectionCode,time);
        } // end of else
        if(numCars == maxNumberOfCars) {
          break;
        }

      } // end of if
      */
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

  /*
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
  */


} // RandomSimulation
