Feature: Valid Build Checking

  Scenario:
  Placing a villager/totoro/tiger on a volcano hex
    Given
    Player has a villager or totoro or tiger
    And is attempting a build action
    When
    Player attempts to place said villager or totoro or tiger during a build action onto a hex with a volcano.
    Then
    Invalid build action

  Scenario:
  Placing a villager/totoro/tiger on a occupied hex
    Given
    Player has a villager or totoro and is attempting a build action
    When
    Player attempts to place said villager or totoro on a hex that  is already occupied by a totoro or tiger  or villager
    Then
    Invalid build action

