package br.edu.ifsp.spo.tads.edda.states;

public enum StatePossibles {
    SUSCETIVEL("S"),
    INFECTADO("I"),
    RECUPERADO("R");

    private String stateName;

    StatePossibles(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }
}
