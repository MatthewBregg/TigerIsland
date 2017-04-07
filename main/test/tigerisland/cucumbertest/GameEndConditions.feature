#Scenario
#Placing all totoros and villagers ends game
#Given
#Player has either N villagers, xor 1 totoro left.
#When
#Player places down N villagers in an expansion or founding, xor places down that one totoro, and then ends their turn.
#Then
#Game ends, scores are compared, player with highest score wins.
#
#Scenario
#Lack of build options causes player to lose
#Given
#Player is unable to perform a build action.
#When
#A player turn has come up, but for any reason, cannot perform a build action. (For example, has no villagers, but still has totoro, and cannot place totoro anywhere.)
#Then
#Game ends, that player has lost.
#

