package su.huiliang.kafkapoc;

import java.util.Random;

public class TransactionGenerator {

    private static final double SPENT_MIN = 10.0;
    private static final double SPENT_MAX = 1000.0;

    Random random = new Random();

    public Transaction next() {
        return Transaction.newBuilder()
                .setAccount(getRandomAccountId())
                .setAmount(getRandomAmount())
                .setSource(randomSource())
                .build();
    }

    String getRandomAccountId() {
        return String.valueOf(random.nextInt(1001) + 1000);
    }

    Double getRandomAmount() {
        return SPENT_MIN + (SPENT_MAX - SPENT_MIN) * random.nextDouble();
    }

    Source randomSource() {
        return Source.values()[random.nextInt(Source.values().length)];
    }
}
