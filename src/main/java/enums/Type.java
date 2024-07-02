package enums;

public enum Type {
    Spell,
    CloseCombatUnit,
    RangedUnit,
    SiegeUnit,
    AglieUnit,
    Leader,
    Weather;

    @Override
    public String toString() {
        if(this.equals(Type.Spell))return "spell";
        if(this.equals(Type.CloseCombatUnit))return "close";
        if(this.equals(Type.RangedUnit))return "ranged";
        if(this.equals(Type.SiegeUnit))return "siege";
        if(this.equals(Type.AglieUnit))return "agile";
        if(this.equals(Type.Leader))return "leader";
        if(this.equals(Type.Weather))return "weather";
        return null;
    }
}
