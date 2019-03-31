import java.math.BigDecimal;

/**
 * Created by Tomasz Michalski on 02/02/2018.
 */
public class Offer {

    private static final int PAYMENT_PERIODS = 36;
    private static final String MONTHS_IN_A_YEAR_STR = "12";

    private final BigDecimal rate;
    private final BigDecimal amount;

    Offer(BigDecimal rate, BigDecimal amount) {
        this.rate = rate;
        this.amount = amount;
    }

    BigDecimal getRate() {
        return rate;
    }

    BigDecimal getAmount() {
        return amount;
    }

    Offer acceptOffer(BigDecimal requestedAmount) {
        if (requestedAmount.compareTo(amount) >= 0){
            return this;
        } else {
            return new Offer(rate, requestedAmount);
        }
    }

    Offer combineOffers(Offer otherOffer) {
        BigDecimal amountSum = amount.add(otherOffer.getAmount());
        return new Offer((rate.multiply(amount).add(otherOffer.getRate().multiply(otherOffer.getAmount())))
                            .divide(amountSum, 16, BigDecimal.ROUND_HALF_UP), amountSum);
    }

    //loan payment formula used:
    //http://financeformulas.net/Loan_Payment_Formula.html
    BigDecimal calculateMonthlyRepayment(){
        BigDecimal monthlyInterest = rate.divide(new BigDecimal(MONTHS_IN_A_YEAR_STR), 16, BigDecimal.ROUND_HALF_UP);
        return monthlyInterest.multiply(amount)
                .divide(
                        BigDecimal.ONE.subtract(
                                BigDecimal.ONE.divide(
                                        (BigDecimal.ONE.add(monthlyInterest))
                                                .pow(PAYMENT_PERIODS),
                                        16, BigDecimal.ROUND_HALF_UP)),
                        16, BigDecimal.ROUND_HALF_UP)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    BigDecimal calculateTotalRepayment(BigDecimal monthlyRepayment){
        return monthlyRepayment
                .multiply(new BigDecimal(String.valueOf(PAYMENT_PERIODS)))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "rate=" + rate +
                ", amount=" + amount +
                '}';
    }

}
