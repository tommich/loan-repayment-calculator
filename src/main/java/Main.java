import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * example args src\main\resources\market.csv 1000
 * Created by Tomasz Michalski on 02/02/2018.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length != 2)
        {
            System.out.println("Wrong number of arguments. \nProvide arguments: [market_file] [loan_amount]");
            System.exit(0);
        }

        Reader input;
        try {
            input = new FileReader(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
            throw e;
        }

        if(!StringOperations.loanAmountConstraints(args[1])){
            System.out.println("[loan_amount] should be a number between 100 and 15000, divisible by 100");
            return;
        }
        BigDecimal loanAmount = new BigDecimal(args[1]);

        CSVParser parser;
        try {
            parser = CSVParser.parse(input, CSVFormat.RFC4180);
        } catch (IOException e) {
            System.out.println("Wrong input file format.");
            throw e;
        }

        LinkedList<Offer> sortedOffers = parser.getRecords().stream()
                    .filter(record -> record.size() == 3)
                    .filter(record -> StringOperations.isPositiveDecimal(record.get(1)))
                    .filter(record -> StringOperations.isPositiveDecimal(record.get(2)))
                    .map(record -> new Offer(new BigDecimal(record.get(1)), new BigDecimal(record.get(2))))
                    .sorted(Comparator.comparing(Offer::getRate))
                    .collect(Collectors.toCollection(LinkedList::new));

        List<Offer> acceptedOffers = new LinkedList<>();
        while (loanAmount.compareTo(BigDecimal.ZERO) > 0 && !sortedOffers.isEmpty()){
            Offer acceptedOffer = sortedOffers.pop().acceptOffer(loanAmount);
            acceptedOffers.add(acceptedOffer);
            loanAmount = loanAmount.subtract(acceptedOffer.getAmount());
        }

        if (loanAmount.compareTo(BigDecimal.ZERO) > 0 && sortedOffers.isEmpty()) {
            System.out.println("Not enough offers for the requested loan!");
        } else {
            Offer combinedOffer = acceptedOffers.stream().reduce(
                    new Offer(BigDecimal.ZERO, BigDecimal.ZERO),
                    Offer::combineOffers);
            BigDecimal monthlyRepayment = combinedOffer.calculateMonthlyRepayment();
            BigDecimal totalRepayment = combinedOffer.calculateTotalRepayment(monthlyRepayment);
            System.out.println("\nRequested amount: £" + combinedOffer.getAmount() +
                    "\nRate: " + combinedOffer.getRate()
                    .setScale(3, BigDecimal.ROUND_HALF_UP).movePointRight(2) + "%" +
                    "\nMonthly repayment: £" + monthlyRepayment +
                    "\nTotal repayment: £" + totalRepayment);
        }
    }
}
