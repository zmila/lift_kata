Lift Kata
=========


input from user:
---
- button "N" on each floor
- button "N" inside the lift
- *no desired direction from user*
- _no cancel button inside lift_

lift state:
---
- count of floors *initial constant*
- current floor number
- current direction (up, down, no)
- visits queue (internal)

output to user:
---
- current floor number
- current direction (up, down, no)
- lift visits floor N (stops, ding!, opens and closes)

communication:
---
- from user: command "go to floor {N}"
- to display: list of actions 
  - "going down from floor N to M", 
  - "going up from floor N to M", 
  - "visit floor N"
    
