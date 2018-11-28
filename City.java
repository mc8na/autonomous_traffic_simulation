// Author: FLIES (Miles, Nick, Rishabh, Sinan)

import java.util.*;
import java.io.*;

// *****************************************************************************
// *****************************************************************************
// **** City
// *****************************************************************************
// *****************************************************************************

public class City {

  private int n, numCarsExited, timeToTraverseSegment;
                // n is the number of streets in a single direction
  private double averageTime;
  private Segment[][][] segment;  // segment[direction][column][row]
  private Intersection[][] intersection; // intersection[column][row]
  private ArrayList<Car> cars;

  private static final int SOUTHWARD = Simulation.SOUTHWARD;
  private static final int EASTWARD = Simulation.EASTWARD;
  private static final int NORTHWARD = Simulation.NORTHWARD;
  private static final int WESTWARD = Simulation.WESTWARD;
  private static final int NEVER_TURN = Simulation.NEVER_TURN;
  private static final int TURN_RIGHTWARD = Simulation.TURN_RIGHTWARD;
  private static final int TURN_LEFTWARD = Simulation.TURN_LEFTWARD;
  
  
  public City(int numStreets, int maxSegmentCapacity,
                              int timeToTraverseSegment) {
    averageTime = 0;
    numCarsExited = 0;
    n = numStreets;
    this.timeToTraverseSegment = timeToTraverseSegment;
    segment = new Segment[4][n + 2][n + 2];
    intersection = new Intersection[n + 1][n + 1];
    cars = new ArrayList<Car>();
    
    // create southward segments
    for(int col = 1; col <= n; col++) {
      for(int row = 0; row <= n; row++) {
        segment[SOUTHWARD][col][row] = new Segment(); 
      } // end of for
    } // end of for
    
    // create eastward segments
    for(int col = 1; col <= n + 1; col++) {
      for(int row = 1; row <= n; row++) {
        segment[EASTWARD][col][row] = new Segment(); 
      } // end of for
    } // end of for
     
    // create northward segments
    for(int col = 1; col <= n; col++) {
      for(int row = 1; row <= n + 1; row++) {
        segment[NORTHWARD][col][row] = new Segment(); 
      } // end of for
    } // end of for
        
    // create westward segments
    for(int col = 0; col <= n; col++) {
      for(int row = 1; row <= n; row++) {
        segment[WESTWARD][col][row] = new Segment(); 
      } // end of for
    } // end of for

    segment[0][1][1].setMaxSegmentCapacity(maxSegmentCapacity);
    segment[0][1][1].setTimeToTraverseSegment(timeToTraverseSegment);

    // create intersections
    for(int col = 1; col <= n; col++) {
      for(int row = 1; row <= n; row++) {
        intersection[col][row] = new Intersection(segment[SOUTHWARD][col][row],
                            segment[EASTWARD][col][row],
                            segment[NORTHWARD][col][row],
                            segment[WESTWARD][col][row],
                            segment[SOUTHWARD][col][row - 1],
                            segment[EASTWARD][col + 1][row],
                            segment[NORTHWARD][col][row + 1],
                            segment[WESTWARD][col - 1][row]); 
      } // end of for
    } // end of for

  } // end of City constructor
  
  
  // Simulation class will call the following method
  public void addCar(int carID, int col, int row, int dir,
                     int blocks, int action, int timestamp) {
    Car newCar = new Car(carID, blocks, action, timestamp);
    if((dir == SOUTHWARD && row == n) || (dir == EASTWARD && col == 1) ||
       (dir == NORTHWARD && row == 1) || (dir == WESTWARD && col == n)){
      // car is entering the grid at the boundary, so it can move at once
      newCar.setSegmentEntryTime(timestamp - timeToTraverseSegment);
    } // end of if
    cars.add(newCar);
    segment[dir][col][row].addCar(newCar);
  } // end of addCar
  
  
  // Simulation class will call the following method
  public void updateSimulation(int timestamp) {
    for(int row = 1; row <= n; row++) {
      for(int col = 1; col <= n; col++) {
        System.out.println("At the intersection located at col " + col +
                           " and row " + row);
        intersection[col][row].advanceCars(timestamp);
      } // end of for
    } // end of for
    

    //the following loops go through edge segments and check for exiting cars
    for(int col = 1; col <= n; col++) {
      Car c = segment[SOUTHWARD][col][0].removeCar();
      if(c != null) {
        averageTime = (averageTime * numCarsExited + 
                      (timestamp - c.getGridEntryTime())) / (numCarsExited + 1);
        numCarsExited++;
        System.out.println("    car#" + c.getID() + " has exited the grid");
      } // end of if
    } // end of for
    
    for(int row = 1; row <= n; row++) {
      Car c = segment[EASTWARD][n + 1][row].removeCar();
      if(c != null) {
        averageTime = (averageTime * numCarsExited + 
                      (timestamp-c.getGridEntryTime())) / (numCarsExited + 1);
        numCarsExited++;
        System.out.println("    car#" + c.getID() + " has exited the grid");
      } // end of if
    } // end of for
    
    for(int col = n; col >= 1; col--) {
      Car c = segment[NORTHWARD][col][n + 1].removeCar();
      if(c != null) {
        averageTime = (averageTime*numCarsExited + 
                      (timestamp-c.getGridEntryTime())) / (numCarsExited + 1);
        numCarsExited++;
        System.out.println("    car#" + c.getID() + " has exited the grid");
      } // end of if
    } // end of for
    
    for(int row = n; row >= 1; row--) {
      Car c = segment[WESTWARD][0][row].removeCar();
      if(c != null) {
        averageTime = (averageTime*numCarsExited + 
                      (timestamp-c.getGridEntryTime())) / (numCarsExited + 1);
        numCarsExited++;
        System.out.println("    car#" + c.getID() + " has exited the grid");
      } // end of if
    } // end of for
    
  } // end of updateSimulation
  
  
  public double getAverageTime() {
    if(numCarsExited == 0){
      return -1;
    }
    return averageTime;
  } // end of getAverageTime
  

} // end of City class
