import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Created by tomek on 03/02/2018.
 */
public class TestFormat {

    @Test
    public void testLoanAmountConstraints() {
        assertFalse(StringOperations.loanAmountConstraints("-1"));
        assertFalse(StringOperations.loanAmountConstraints("1"));
        assertFalse(StringOperations.loanAmountConstraints("1.0"));
        assertFalse(StringOperations.loanAmountConstraints("asdf"));
        assertFalse(StringOperations.loanAmountConstraints("99"));
        assertFalse(StringOperations.loanAmountConstraints("102"));
        assertFalse(StringOperations.loanAmountConstraints("15001"));

        assertTrue(StringOperations.loanAmountConstraints("100"));
        assertTrue(StringOperations.loanAmountConstraints("1000"));
        assertTrue(StringOperations.loanAmountConstraints("15000"));
    }

    @Test
    public void testIsPositiveDecimal() {
        assertFalse(StringOperations.isPositiveDecimal("-1"));
        assertFalse(StringOperations.isPositiveDecimal("asdf"));

        assertTrue(StringOperations.isPositiveDecimal("99"));
        assertTrue(StringOperations.isPositiveDecimal("1"));
        assertTrue(StringOperations.isPositiveDecimal("1.0"));
        assertTrue(StringOperations.isPositiveDecimal("0.00010"));
    }
}


