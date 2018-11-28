// Author: FLIES (Miles, Nick, Rishabh, Sinan)

import java.util.*;
import java.io.*;

// *****************************************************************************
// *****************************************************************************
// **** Segment
// *****************************************************************************
// *****************************************************************************

public class Segment {

  private Deque<Car> queue;
  private static int timeToTraverseSegment, maxSegmentCapacity;


  public Segment() {
    queue = new LinkedList<Car>();
    timeToTraverseSegment = 0;
    maxSegmentCapacity = Integer.MAX_VALUE;
  } // end of Segment constructor


  public void setTimeToTraverseSegment(int time) {
  	timeToTraverseSegment = time;
  }


  public void setMaxSegmentCapacity(int capacity) {
  	maxSegmentCapacity = capacity;
  }


  public void addCar(Car c) {
    queue.offerLast(c);
  } // end of addCar


  public Car getCar(int timestamp) {
    Car c = queue.peekFirst();
    if(c != null &&
        (timestamp - c.getSegmentEntryTime() >=  timeToTraverseSegment)){
      return c;
    }
    return null;
  } // end of getCar


  public Car removeCar() {
    return queue.pollFirst();
  } // end of removeCar


  public boolean isEmpty() {
    return (queue.size() == 0);
  }

  public boolean isAvailable(int timestamp) {
    if(queue.size() < maxSegmentCapacity) {
      if(queue.size() == 0) {
      	return true;
      } //end "if(queue.size() < maxSegmentCapacity)"
      return (timestamp > queue.peekLast().getSegmentEntryTime());
      
    } // end of "if(queue.size() < maxSegmentCapacity)"
    return false;
  } // end of isEmpty
  

} // end Segment class
