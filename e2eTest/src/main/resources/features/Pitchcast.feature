Feature: Pitchcast

  Background:
    Given Egde-Services are up
    And Pitchcast-App is up

  Scenario: Open Pitchcast
    Given 3 Pitches stored
    When Pitchcast is opened
    Then 3 Pitches are displayed