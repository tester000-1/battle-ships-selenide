# Task: Battleship Game Test Automation with Page Object Pattern

## Task Overview:

Develop automated tests for the Battleship game based on the Page Object pattern. This task involves creating Page Object classes to encapsulate the game's interactions, implementing tests according to the provided scenario, and including logging and Allure reporting for comprehensive test results.

## Steps to Complete the Task:

1. <b>Create Page Object Classes for Battleship</b>

<ul>
    <li>Develop Page Object classes to represent different sections of the Battleship game.</li>
    <li>Implement methods to handle interactions with game elements, such as switching language, checking checkboxes, selecting opponents, arranging ships, and playing the game.</li>
    <li>Ensure the methods handle logic related to elements, including waiting, text input, and clicking.</li>
    <li>Incorporate logging to track interactions and add Allure reporting for comprehensive test reports.</li>
</ul>

2. <b>Implement Test Scenario for Battleship</b>

<ul>
    <li>Write automated tests based on the given scenario:</li>
        <ul>
            <li>Visit https://battleship-game.org/zh.</li>
            <li>Switch to the English version and verify that it was selected.</li>
            <li>Check that the "Mark verified empty cells" checkbox is checked.</li>
            <li>Check the "Compact chat" checkbox.</li>
            <li>Select a random opponent.</li>
            <li>Randomly arrange ships by clicking the "Randomise" link a random number of times (between 1 and 15).</li>
            <li>Click "Play" and wait for another player to connect.</li>
            <li>Play a game of Battleship and aim to win.</li>
        </ul>
    <li>Implement checks and assertions at each step to ensure proper functionality and expected behavior.</li>
    <li>If the game ends in a win, the test is considered successful. For any other outcome, the test should fail and report the correct reason (e.g., connection lost, opponent left, or defeat).</li>
</ul>

3. <b>Develop an Algorithm for Winning</b>

<ul>
    <li>Create an algorithm for playing the game that maximizes the probability of winning.</li>
    <li>Implement this algorithm in the test script, utilizing strategic moves to increase the chances of success.</li>
    <li>Explain the logic of the algorithm in the readme.md file for documentation purposes.</li>
</ul>

4. <b>Set Up Logging and Allure Reporting</b>

<ul>
    <li>Configure a logger to track interactions and test execution.</li>
    <li>Implement Allure reporting to generate detailed test reports, including logs, screenshots, and other relevant data.</li>
    <li>Ensure the reports are generated and opened in a browser via an npm script.</li>
</ul>

5. <b>Ensure Cross-Browser Compatibility</b>


<ul>
    <li>Implement the ability to execute the test on either Chrome or Firefox.</li>
    <li>Use an environment variable (BROWSER_NAME) to select the required browser during test execution.</li>
    <li>Test the automation script to ensure it works across both browsers without issues.</li>
</ul>

## Expected Outcome:

<ul>
    <li>The Page Object Model is developed, encapsulating interactions with the Battleship game.</li>
    <li>Automated tests are implemented according to the provided scenario, with checks and assertions for expected outcomes.</li>
    <li>The test successfully ends in a win; otherwise, it reports the correct reason for failure.</li>
    <li>Logging and Allure reporting are set up for detailed test reports.</li>
    <li>The test script can be executed on either Chrome or Firefox, depending on the environment variable.</li>
</ul>

## Software and tools

<p><b>Software:</b> Selenide, TestNG, Java, Lombok, SLF4J</p>

## Solution

<p><b>Solution author:</b> Martin A.</p>