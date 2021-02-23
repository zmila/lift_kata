Feature: Lift is moving
  Lift (elevator) is moving between floors delivering passengers.

  Elevator algorithm.
  Continue travelling in the same direction while there are remaining requests in that same direction.
  If there are no further requests in that direction, then stop and become idle,
  or change direction if there are requests in the opposite direction.

  User may press button "request lift" on each floor.
  User may press button "floor N" from inside lift.

  Lift processes user input and replays with actions - "moving from a floor to a floor" or "visiting a floor".

  Using these actions UI may display "current floor", "current direction arrow" and opening/closing of a door.

  Scenario: Lift is not moving when no requests
    Given lift is not moving
    When there is no user requests
    Then lift will do no actions

  Scenario: Lift opens doors when requested on the same floor
    Given lift is on 1 floor
    And lift is not moving
    When user requests lift on 1 floor
    Then lift visits the floor 1

  Scenario: requested on different floor, then lift goes to the floor and then opens doors
    Given lift is on 5 floor
    And lift is not moving
    When user requests lift on 7 floor
    Then lift goes thru floors
      | order | from | to |
      | 1     | 5    | 7  |
      | 2     |      | 7  |

  Scenario: requested after current moves
    Given lift is on 3 floor
    And lift is moving thru floors
      | 5 |
    When user requests lift on 7 floor
    Then lift goes thru floors
      | order | from | to |
      | 1     | 3    | 5  |
      | 2     |      | 5  |
      | 3     | 5    | 7  |
      | 4     |      | 7  |

  Scenario: requested between current moves
    Given lift is on 3 floor
    And lift is moving thru floors
      | 8 |
    When user requests lift on 7 floor
    Then lift goes thru floors
      | order | from | to |
      | 1     | 3    | 7  |
      | 2     |      | 7  |
      | 3     | 7    | 8  |
      | 4     |      | 8  |

  Scenario: requested on current floor
    Given lift is on 2 floor
    And lift is moving thru floors
      | 8 |
    When user requests lift on 2 floor
    Then lift goes thru floors
      | order | from | to |
      | 1     |      | 2  |
      | 2     | 2    | 8  |
      | 3     |      | 8  |

  Scenario: requested on a floor in inverse direction
    Given lift is on 4 floor
    And lift is moving thru floors
      | 8 |
    When user requests lift on 2 floor
    Then lift goes thru floors
      | order | from | to |
      | 1     | 4    | 8  |
      | 2     |      | 8  |
      | 3     | 8    | 2  |
      | 4     |      | 2  |


  Scenario: complex case
    Given lift is on 4 floor
    And lift is moving thru floors
      | 6 |
      | 3 |
      | 1 |
    When user requests lift on 2 floor
    Then lift goes thru floors
      | order | from | to |
      | 1     | 4    | 6  |
      | 2     |      | 6  |
      | 3     | 6    | 3  |
      | 4     |      | 3  |
      | 5     | 3    | 2  |
      | 6     |      | 2  |
      | 7     | 2    | 1  |
      | 8     |      | 1  |
