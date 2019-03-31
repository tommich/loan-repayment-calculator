import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by tomek on 03/02/2018.
 */
public class TestOffer {

    private final Offer offer1 = new Offer(new BigDecimal("0.069"), new BigDecimal("480"));
    private final Offer offer2 = new Offer(new BigDecimal("0.071"), new BigDecimal("520"));
    private final Offer combinedOffer = offer1.combineOffers(offer2);

    @Test
    public void testAcceptOffer(){
        BigDecimal requestedAmount = new BigDecimal("500");
        assertEquals(offer1.acceptOffer(requestedAmount), offer1);

        Offer resultOffer = new Offer(new BigDecimal("0.071"), requestedAmount);
        assertEquals(offer2.acceptOffer(requestedAmount).toString(), resultOffer.toString());
    }

    @Test
    public void testCombineOffers() {
        assertEquals(0, combinedOffer.getRate().setScale(2, BigDecimal.ROUND_HALF_UP)
                .compareTo(new BigDecimal("0.07")));
        assertEquals(0, combinedOffer.getAmount().compareTo(new BigDecimal("1000")));
    }

    //results obtained from:
    //http://financeformulas.net/Loan_Payment_Formula.html#calcHeader
    @Test
    public void testCalculateMonthlyRepayment() {
        assertEquals(0, combinedOffer.calculateMonthlyRepayment().setScale(2, BigDecimal.ROUND_HALF_UP)
                .compareTo(new BigDecimal("30.88")));
    }

    @Test
    public void testCalculateTotalRepayment() {
        assertEquals(0, combinedOffer.calculateTotalRepayment(new BigDecimal("30.88"))
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .compareTo(new BigDecimal("1111.68")));
    }
}


