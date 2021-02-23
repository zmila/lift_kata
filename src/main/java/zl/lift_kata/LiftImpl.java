package zl.lift_kata;

import java.util.ArrayList;
import java.util.List;

public class LiftImpl implements Lift {

    private static final int BASE_FLOOR = 1;
    private final int countOfFloors;
    private final List<LiftAction> actionsQueue;
    private final List<Integer> floorsQueue;

    private int currentFloor;

    public LiftImpl(int countOfFloors) {
        this.countOfFloors = countOfFloors;
        this.actionsQueue = new ArrayList<>();
        this.floorsQueue = new ArrayList<>();
    }

    @Override
    public boolean process(UserInput userInput) {

        boolean res;
        if (userInput instanceof GoToFloor) {
            res = processGoToFloor((GoToFloor) userInput);
        } else {
            res = false;
        }
        if (res) {
            generateActions();
        }

        return res;
    }

    private void generateActions() {
        actionsQueue.clear();
        int from = currentFloor;
        int to;
        for (int floorNumber : floorsQueue) {
            to = floorNumber;
            if (from != to) {
                actionsQueue.add(new MoveFromToFloor(from, to));
            }
            actionsQueue.add(new VisitFloor(to));
            from = to;
        }
    }

    private boolean processGoToFloor(GoToFloor goToFloor) {
        final int targetFloorNumber = goToFloor.getFloorNumber();
        if (floorsQueue.isEmpty()) {
            floorsQueue.add(targetFloorNumber);
            return true;
        }

        insertFloorIntoQueue(targetFloorNumber);
        return true;
    }

    private void insertFloorIntoQueue(int newFloorNumber) {
        if (newFloorNumber == currentFloor) {
            // if requested the same floor where the lift is now
            // do visit the current floor (open and close the doors)
            if (floorsQueue.get(0) != newFloorNumber) {
                floorsQueue.add(0, newFloorNumber);
            }
            return;
        }

        MoveDirection currentDirection = getCurrentDirection();
        MoveDirection directionToNewFloor = getDirectionBetweenFloors(currentFloor, newFloorNumber);
        if (currentDirection == directionToNewFloor) {
            // Continue travelling in the same direction while there are remaining requests in that same direction.
            insertFloorIntoQueueStartingFrom(0, newFloorNumber);
        } else {
            // find the last stop in current direction,
            // and enqueue the new floor number starting at the point where lift changes direction.
            int indexOfChangeDirection = findIndexWhereLiftChangeDirection();
            insertFloorIntoQueueStartingFrom(indexOfChangeDirection, newFloorNumber);
        }
    }

    private int findIndexWhereLiftChangeDirection() {
        MoveDirection currentDirection = getCurrentDirection();
        int index = 1;
        int floorNumber;

        while (index < floorsQueue.size()) {
            floorNumber = floorsQueue.get(index);
            MoveDirection directionToNextFloor = getDirectionBetweenFloors(currentFloor, floorNumber);
            if (currentDirection != directionToNextFloor) {
                return index;
            }

            index++;
        }

        // all floors are in the same direction, so the desired index is queue size.
        return index;
    }

    private void insertFloorIntoQueueStartingFrom(int indexToStart, int newFloorNumber) {
        if (indexToStart >= floorsQueue.size()) {
            floorsQueue.add(newFloorNumber);
            return;
        }

        final MoveDirection currentDir = getDirectionBetweenFloors(currentFloor, floorsQueue.get(indexToStart));
        final MoveDirection nextDir = getDirectionBetweenFloors(floorsQueue.get(indexToStart), newFloorNumber);
        if (currentDir == nextDir) {
            insertFloorIntoQueueStartingFrom(indexToStart + 1, newFloorNumber);
            return;
        }

        floorsQueue.add(indexToStart, newFloorNumber);
    }

    private MoveDirection getDirectionBetweenFloors(int floorNumber1, int floorNumber2) {
        if (floorNumber1 == floorNumber2) {
            return MoveDirection.Not_Moving;
        }
        return (floorNumber1 < floorNumber2) ? MoveDirection.Moving_Up : MoveDirection.Moving_Down;
    }

    private MoveDirection getCurrentDirection() {
        if (floorsQueue.isEmpty()) {
            return MoveDirection.Not_Moving;
        }
        return getDirectionBetweenFloors(currentFloor, floorsQueue.get(0));
    }

    public int getCountOfFloors() {
        return countOfFloors;
    }

    @Override
    public List<LiftAction> getActionsQueue() {
        return actionsQueue;
    }

    protected void initLiftState(List<Integer> floorNumbers) {
        floorsQueue.clear();
        if (floorNumbers != null) {
            floorsQueue.addAll(floorNumbers);
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
}
