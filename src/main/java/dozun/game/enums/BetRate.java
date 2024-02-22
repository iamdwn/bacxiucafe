package dozun.game.enums;

public enum BetRate {
    RATE(1.97D);
    private Double rate;
    private BetRate(Double rate) {
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }
}
