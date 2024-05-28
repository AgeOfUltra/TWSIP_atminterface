<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<h1>ATM Interface Project</h1>

<p>This project was completed during an internship at TopperWorld. It implements a graphical user interface (GUI) ATM system using Java and JavaFX, integrated with a MySQL database.</p>

<h2>Project Overview</h2>
<ul>
    <li><strong>Java Development Environment</strong>: Ensure that you have JDK (Java Development Kit) installed on your system to write and compile Java code.</li>
    <li><strong>User Interface</strong>: Implement a graphical user interface (GUI) to interact with the user. The GUI should display options for different transactions and receive input from the user. GUI is developed using the JavaFX.</li>
    <li><strong>Account Management</strong>: Design a system to manage user accounts, including storing account information, such as account number, PIN (Personal Identification Number), and account balance.</li>
    <li><strong>Transaction Processing</strong>: Implement the logic to process various ATM transactions, such as cash withdrawals, balance inquiries, deposits, and fund transfers.</li>
</ul>

<h2>Setup Instructions</h2>
<h3>Prerequisites</h3>
<ul>
    <li>JDK (Java Development Kit)</li>
    <li>JavaFX SDK</li>
    <li>MySQL Database</li>
    <li>Integrated Development Environment (IDE)(Preffered to use Intellij IDea) or text editor</li>
</ul>

<h3>Database Setup</h3>
<p>Download the required.sql file from the resource folder execute it in the MySql workbench</p>

<h3>Project Setup</h3>
<ol>
    <li>Set up a new Java project using an Integrated Development Environment (IDE) or a text editor and compile your Java code.</li>
    <li>Create User Interface: Develop a GUI for the ATM system that displays options for different transactions, such as withdrawing cash, checking balance, etc.</li>
    <li>User Authentication: Implement a mechanism to prompt the user for their account number and PIN to authenticate them before allowing access to the ATM functionalities.</li>
    <li>Account Management: Design a data structure (e.g., arrays, lists, or database) to store and manage account information, including account numbers, PINs, and account balances.</li>
    <li>Transaction Processing: Write code to process different ATM transactions, such as cash withdrawals, balance inquiries, deposits, and fund transfers. Ensure that the transactions update the account balances accordingly.</li>
    <li>Security Measures: Implement code to validate the user's PIN and deny access to unauthorized users. Implement security measures to prevent unauthorized access to account information.</li>
</ol>

<h2>Features</h2>
<ul>
    <li>Cash Withdrawals</li>
    <li>Balance Inquiries</li>
    <li>Deposits</li>
    <li>Fund Transfers</li>
    <li>Transaction History (Self added Feature)</li>
</ul>

<h2>Usage</h2>
<p>Run the project using your IDE. The ATM GUI will prompt you to enter your account number and PIN. Once authenticated, you can perform various transactions such as withdrawing cash, checking balance, making deposits, and transferring funds.</p>

</body>
</html>
