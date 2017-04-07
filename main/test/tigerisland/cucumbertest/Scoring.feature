#Scenario   (cannot FIND!!!!)
#Player credited with score for expansion villager
#Given
#The player placed a villager onto an expansion tile
#When
#The villager is placed at level <hexLevel>
#Then
#The player is credited with <scoreAmount> points
#
#Scenario
#Expanding a settlement gives points
#Given
#The player has placed a tile AND the player has chosen to expand a settlement
#When
#The player places 2 villagers on a level 2 hex
#Then
#The player is credited with 4 points
#
#Scenario
#Founding a settlement gives points
#Given
#Player is founding a settlement, under valid conditions for founding a settlement.
#When
#Player places villager down to found settlement.
#Then
#Player immediately gets one more point.
#
#Scenario
#Placing a totoro gives points
#Given
#Player is placing a totoro, under valid conditions for placing a totoro.
#When
#Player places a totoro.
#Then
#Player immediately gets 200 more points.
#
#Scenario
#Player does not get points for any other actions
#Given
#Player has some score
#When
#Player performs an action that does not involve successfully placing a totoro or villager.
#Then
#Player score is unchanged
