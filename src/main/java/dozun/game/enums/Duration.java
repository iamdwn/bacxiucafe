package dozun.game.enums;


public enum Duration {
    GAME_DURATION(5),
    BET_LOCKED_DURATION(2);
    private int dur;

    private Duration(int dur) {
        this.dur = dur;
    }

    public int getDur() {
        return dur;
    }
}
