@TableEPIByDivisions
Feature: Validate Table of Employees
        This scenario should validate Employees Personal Information by a Division search containing Outsourcing

  Scenario: Verify table of Employees Personal Information by specific Division.
    Given I am login on Mach2 with credentials
      | user | password      |
      | Jackeline.Camacho@fundacion-jala.org | Mishelle3026 |
    And I have a board and widget created
    When I select "Table" icon on Widget options
    And I select "Open ERP" service
    And I select "Employees Personal Information" of Open ERP
    And I select "Outsourcing" on "Divisions" option
    And I click on save button
    Then I should see the table of EPI by "Outsourcing" Division and the number of employees of the table should be the same number of employees that have OPEN ERP in this division.

