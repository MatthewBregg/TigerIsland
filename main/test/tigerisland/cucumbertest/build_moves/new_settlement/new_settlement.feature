
  Feature: Build New Settlement
  As a game
  I want to allow players to build new settlement
  So that they can score point


    Scenario Founding a settlement
      Given Player has at least 1 villager
      When Player attempts to place the villager on a non-volcano hex
      And The hex has a level of 1
      And The hex is unoccupied
      Then A new settlement of size 1 is formed on that tile,
      And One villager is subtracted from players villager count

