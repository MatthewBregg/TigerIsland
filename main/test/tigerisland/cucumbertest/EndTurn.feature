#Scenario
#Combining settlements when turn ends.
#Given
#Player has two settlements that share a hex edge
#When
#Player successfully ends turn
#Then
#The settlements are merged
#
#
#Scenario
#Settlements aren’t merged until turn ends.
#Given
#Player has two settlements.
#When
#Player performs an action that makes those two settlements share an edge.
#Then
#The settlements remain separate until the turn ends.
#
#
#Scenario
#Player successfully ends turn with no pieces left
#Given
#Player has 0 pieces left
#When
#Player successfully ends turn
#Then
#Game ends
#
#Scenario
#Player unsuccessfully ends turn
#Given
#Player has incorrectly completed a build action
#When
#Player ends turn
#Then
#Player automatically loses
#
