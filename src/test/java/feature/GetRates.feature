Feature:
  Verify latest exchange rates returned by Get Api

  @SC001
  Scenario: Verify USD rates returned against currencies
    Given I perform GET call to latestRates api for USD currency
    Then I verify success http status code and status message
    And I verify response schema
    And I verify rates retrieved for all currencies

  @SC002
  Scenario: Verify error response when Invalid base currency is sent
    Given I perform GET call to latestRates api for ABC currency
    Then I verify success http status code and status message
    And I verify error response

  @SC003
  Scenario: Verify error response when No currency is sent
    Given I perform GET call to latestRates api for Empty currency
    Then I verify failure http status code and status message