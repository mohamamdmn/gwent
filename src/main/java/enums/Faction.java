package enums;

public enum Faction {
    Skelige,
    ScosiaTeal,
    NorthernRealms,
    Nilfgaars,
    Monstres,
    Neutral,
    All;



    @Override
    public String toString() {
        switch (this) {
            case Neutral -> {
                return "neutral";
            }
            case ScosiaTeal -> {
                return "scoitael";
            }
            case Skelige -> {
                return "skellige";
            }
            case NorthernRealms -> {
                return "realms";
            }
            case Nilfgaars -> {
                return "nilfgaars";
            }
            case Monstres -> {
                return "monstres";
            }
            case All ->
            {
                return "all";
            }
        }
        return null;
    }
}
