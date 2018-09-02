package code.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class design {

  // design parking lot
  /**
   * areas to talk about : Building or open space? Free or Paid? Multiple levels? Accessibility? One
   * building or multiple building? Concurrency? Multiple entrances? Order of filling? Price?
   * Compact - Normal size - Electric charge - Physically challenged?
   */
  abstract class Vehicle {

    String licencePlate;

    boolean canFitInSmallSlot() {
      return false;
    }

    boolean canFitInCompactSlot() {
      return false;
    }

    boolean canFitInlargeSlot() {
      return false;
    }
  }

  class Car extends Vehicle {
  }

  class Motorcycle extends Vehicle {
  }

  class Bus extends Vehicle {
  }

  class Truck extends Vehicle {
  }

  // think about concurrency and pricing
  class ParkingLot {

    private static final int NUMBER_OF_SMALL_SLOTS = 10;
    private static final int NUMBER_OF_COMPACT_SLOTS = 10;
    private static final int NUMBER_OF_LARGE_SLOTS = 10;
    public Map<Long, Slot> occupiedSlots;
    private Stack<Slot> smallSlots;
    private Stack<Slot> compactSlots;
    private Stack<Slot> largeSlots;

    public ParkingLot() {
      smallSlots = new Stack<>();
      compactSlots = new Stack<>();
      largeSlots = new Stack<>();
      createSlots();
      occupiedSlots = new HashMap<>();
    }

    private void createSlots() {
      for (int i = 1; i <= NUMBER_OF_SMALL_SLOTS; i++) {
        smallSlots.push(new SmallSlot(i));
      }
      for (int i = 1; i <= NUMBER_OF_COMPACT_SLOTS; i++) {
        compactSlots.push(new CompactSlot(i));
      }
      for (int i = 1; i <= NUMBER_OF_LARGE_SLOTS; i++) {
        largeSlots.push(new LargeSlot(i));
      }

    }

    // can use stack for each size to see if spot available
    long park(Vehicle vehicle) {
      Slot slot = null;
      long uniqueToken = -1;

      if (vehicle.canFitInSmallSlot()) {
        slot = smallSlots.pop();
        if (slot != null) {
          uniqueToken = parkHelper(slot, vehicle);
        }
      }
      if (vehicle.canFitInCompactSlot()) {
        slot = compactSlots.pop();
        if (slot != null) {
          uniqueToken = parkHelper(slot, vehicle);
        }
      }

      if (vehicle.canFitInlargeSlot()) {
        slot = largeSlots.pop();
        if (slot != null) {
          uniqueToken = parkHelper(slot, vehicle);
        }
      }
      return uniqueToken;
    }

    //
    void unPark(long uniqueToken) {
      Slot slot = occupiedSlots.get(uniqueToken);
      slot.unPark();
      if (slot instanceof SmallSlot) {
        smallSlots.push(slot);
      }
      occupiedSlots.remove(uniqueToken);
    }

    private long parkHelper(Slot slot, Vehicle vehicle) {
      slot.park();
      long uniqueToken = vehicle.hashCode() * 43;
      occupiedSlots.put(uniqueToken, slot);
      return uniqueToken;
    }

  }

  abstract class Slot {

    private boolean isOccupied;
    private int slotNumber;

    Slot(int slotNumber) {
      isOccupied = false;
      this.slotNumber = slotNumber;
    }

    boolean isOccupied() {
      return isOccupied;
    }

    int getSlotNumber() {
      return slotNumber;
    }

    void park() {
      isOccupied = true;
    }

    void unPark() {
      isOccupied = false;
    }

    @Override
    public boolean equals(Object o) {
      return (((Slot) o).slotNumber == this.slotNumber);
    }

    @Override
    public int hashCode() {
      int hash = 5;
      hash = 53 * hash + this.slotNumber;
      return hash;
    }
  }

  class SmallSlot extends Slot {

    SmallSlot(int slotNumber) {
      super(slotNumber);
    }
  }

  class CompactSlot extends Slot {

    CompactSlot(int slotNumber) {
      super(slotNumber);
    }
  }

  class LargeSlot extends Slot {

    LargeSlot(int slotNumber) {
      super(slotNumber);
    }
  }

  class ParkingFloor {
    List<Slot> availableCarParkingSlots;
    List<Slot> availableBikeParkingSlots;
    List<Slot> occupiedCarParkingSlots;
    List<Slot> occupiedBikeParkingSlots;

    List<Slot> getNumberOfAvailableCarParkingSlots() {
      return new ArrayList<>();
    }

    List<Slot> getNumberOfAvailableBikeParkingSlots() {
      return new ArrayList<>();
    }
  }

  class ParkingArea {
    List<ParkingFloor> parkingFloors;
  }

  //design movie booking system. bookmyshow.com



}
