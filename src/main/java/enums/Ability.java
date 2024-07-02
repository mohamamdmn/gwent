package enums;

public enum Ability {
    Mardoeme,
    Berserker,
    MoralBoost,
    TightBound,
    Muster,
    Hero_transformers,
    Medic,
    Scorch,
    Spy,
    CommandersHorn,
    None,
    ;

    @Override
    public String toString() {

        if(this.equals(Ability.MoralBoost))return "morale";
        if(this.equals(Mardoeme))return  "mardroeme";
        if(this.equals(Berserker)) return "berserker";
        if(this.equals(TightBound)) return "bond";
        if(this.equals(Muster))return "muster";
        if(this.equals(Hero_transformers)) return "transformers";
        if(this.equals(Medic))return "medic";
        if(this.equals(Scorch)) return "scorch";
        if(this.equals(Spy)) return "spy";
        if(this.equals(CommandersHorn)) return "horn";
        return "none";

    }
}

