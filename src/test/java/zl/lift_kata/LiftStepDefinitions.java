package zl.lift_kata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LiftStepDefinitions {
    private static final int COUNT_OF_FLOORS = 7;

    private LiftImpl lift;

    @Before
    public void initScenario() {
        this.lift = new LiftImpl(COUNT_OF_FLOORS);
    }

    @Given("lift is not moving")
    public void liftIsNotMoving() {
        lift.initLiftState(null);
    }

    @When("there is no user requests")
    public void thereIsNoUserRequests() {
        lift.process(null);
    }

    @Then("lift will do no actions")
    public void liftWillDoNoActions() {
        assertTrue(isListEmpty(lift.getActionsQueue()));
    }


    @Given("lift is on {int} floor")
    public void liftIsOnIntFloor(int currentFloor) {
        lift.setCurrentFloor(currentFloor);
    }

    @When("user requests lift on {int} floor")
    public void userRequestsLiftOnIntFloor(int targetFloor) {
        final UserInput userInput = new GoToFloor(targetFloor);
        lift.process(userInput);
    }

    @Then("lift visits the floor {int}")
    public void liftVisitsTheFloorInt(int floorToVisit) {
        List<LiftAction> liftActions = Collections.singletonList(new VisitFloor(floorToVisit));
        assertEquals(liftActions, lift.getActionsQueue());
    }

    @And("finally stops on the floor {int}")
    public void finallyStopsOnTheFloor(int targetFloor) {
        final List<LiftAction> actionsQueue = lift.getActionsQueue();
        assertEquals(actionsQueue.get(actionsQueue.size() - 1), new VisitFloor(targetFloor));
    }

    @Then("lift goes thru floors")
    public void liftGoesThruFloors(List<Map<String, Integer>> floors) {
        final List<LiftAction> actionsQueue = lift.getActionsQueue();
        final List<LiftAction> expectedActions = collectActionsFromStepParam(floors);
        for (int i = 0; i < expectedActions.size(); i++) {
            assertEquals(actionsQueue.get(i), expectedActions.get(i));
        }
    }

    private List<LiftAction> collectActionsFromStepParam(List<Map<String, Integer>> floors) {
        final List<LiftAction> expectedActions = new ArrayList<>();
        for (Map<String, Integer> floorEntry : floors) {
            final Integer from = floorEntry.get("from");
            final int to = floorEntry.get("to");
            if (from == null) {
                expectedActions.add(new VisitFloor(to));
            } else {
                expectedActions.add(new MoveFromToFloor(from, to));
            }
        }
        return expectedActions;
    }

    private <T> boolean isListEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    @And("lift is moving thru floors")
    public void liftIsMovingThruFloors(List<Integer> floorNumbers) {
        lift.initLiftState(floorNumbers);
    }

}
