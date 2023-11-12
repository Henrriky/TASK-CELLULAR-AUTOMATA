package WORK;

public enum StatePossibles {
    SUSCETIVEL("S"),
    INFECTADO("I"),
    REMOVIDO("R");

    private String stateName;

    StatePossibles(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }
}
