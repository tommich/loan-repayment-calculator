/**
 * Created by Tomasz Michalski on 03/02/2018.
 */
class StringOperations {
    static boolean isPositiveDecimal(String value) {
        return value.matches("^\\d+(.\\d+)?$") && value.matches(".*[1-9].*");
    }

    static boolean loanAmountConstraints(String value) {
        if (value.matches("\\d+")) {
            int loanAmount = Integer.parseInt(value);
            return loanAmount >= 100 && loanAmount <= 15000 && loanAmount % 100 == 0;
        } else {
            return false;
        }
    }
}
