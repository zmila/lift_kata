package zl.lift_kata;

import java.util.List;
import java.util.Objects;

enum MoveDirection {
    Not_Moving,
    Moving_Up,
    Moving_Down;
}

interface UserInput {
}

interface LiftAction {
}

interface Lift {
    /**
     * @return true if processed.
     */
    boolean process(UserInput userInput);

    List<LiftAction> getActionsQueue();
}

class GoToFloor implements UserInput {
    private final int floorNumber;

    public GoToFloor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}

class MoveFromToFloor implements LiftAction {
    private final int fromFloorNumber;
    private final int toFloorNumber;

    public MoveFromToFloor(int fromFloorNumber, int toFloorNumber) {
        this.fromFloorNumber = fromFloorNumber;
        this.toFloorNumber = toFloorNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveFromToFloor)) return false;
        MoveFromToFloor that = (MoveFromToFloor) o;
        return fromFloorNumber == that.fromFloorNumber && toFloorNumber == that.toFloorNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromFloorNumber, toFloorNumber);
    }

    @Override
    public String toString() {
        return "MoveFromToFloor{ from=" + fromFloorNumber + ", to=" + toFloorNumber + " }";
    }

    public int getFromFloorNumber() {
        return fromFloorNumber;
    }

    public int getToFloorNumber() {
        return toFloorNumber;
    }
}

class VisitFloor implements LiftAction {
    private final int floorNumber;

    public VisitFloor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitFloor)) return false;
        VisitFloor that = (VisitFloor) o;
        return floorNumber == that.floorNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floorNumber);
    }

    @Override
    public String toString() {
        return "VisitFloor{ " + floorNumber + " }";
    }
}
