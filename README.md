Assumptions:
- The status of both Rocket and Mission can be freely changed (e.g. Mission with status ENDED can be updated to PENDING)
- Every Rocket and Mission has a unique name

Relations between Rocket statues and Mission statues are somewhat confusing.
The provided example contradicts some of the status descriptions.


Transit – In progress – Dragons: 3\
    Red Dragon – On ground\
    Dragon XL – In space\
    Falcon Heavy – In space

Luna1 – Pending – Dragons: 2\
    Dragon 1 – On ground\
    Dragon 2 – On ground

Vertical Landing – Ended – Dragons: 0

Mars – Scheduled – Dragons: 0

Luna2 – Scheduled – Dragons: 0

Double Landing – Ended – Dragons: 0

\
Mission Pending status description:\
“Pending” – at least one rocket is assigned and one or more assigned rockets are in repair\
In case of the mission "Luna1" none of the assigned rockets are in repair.\

Furthermore, all rockets assigned to "Luna1" have "On ground" status.\
“On ground” – initial status, where the rocket is not assigned to any mission\
Based on this description these rockets shouldn't be assigned to any mission

Due to the unknown rules for transitioning between statues, I haven't implemented any kind of validator.