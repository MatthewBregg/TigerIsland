Feature: Tiles
  Scenario: Tile Structure Non Volcano
    Given We can examine a tile
    When We examine the left and right portions of said tile
    Then We find two non volcano terrain types

  Scenario: Tile Structure Volcano
    Given We can examine a tile
    When We examine the left and right portions of said tile
    Then We find one volcano terrain types


