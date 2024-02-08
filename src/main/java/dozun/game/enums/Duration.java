package dozun.game.enums;


public enum Duration {
    GAME_DURATION(15L),
    BET_LOCKED_DURATION(5L);
    private Long dur;

    private Duration(Long dur) {
        this.dur = dur;
    }

    public Long getDur() {
        return dur;
    }
}
