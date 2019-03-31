# Loan Repayment Calculator
A system for calculating monthly and total repayment rates based on a set of offers and a requested loan amount. <br>
<br>
Example market offers file: <br>
[market.csv](https://github.com/tommich/loan-repayment-calculator/blob/master/src/main/resources/market.csv) <br>
<br>
Build: <br>
mvn clean package <br>
Program arguments: <br>
[market_file] [loan_amount] <br>
Example: <br>
java -jar target/loan-repayment-calculator.jar target/classes/market.csv 1000
<br>
Loan payment formula used: <br>
http://financeformulas.net/Loan_Payment_Formula.html#calcHeader
