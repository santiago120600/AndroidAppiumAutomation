Feature: Smoke Test

  Scenario Outline: set wifi settings
    Given I have logged into the app
    When I navigate to the wifi settings
    And enable wifi
    Then I should be able to type the "wifi_name"

    Examples:
      | wifi_name     |
      | my_wifi       |
      # | neighbor wifi |
  
  # Scenario Outline: Get text from text Marquee page button 
  #   Given I have logged into the app
  #   When I navigate to the text page
  #   And click Marquee button
  #   Then I should be able to get the text from the Marquee page button
