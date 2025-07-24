package nl.jessenagel.optimization.cemtrouting;

public class CEMTParser {

    public static CEMTKlasse parse(String string) {
        return switch (string) {
            case "_0" -> CEMTKlasse.zero;
            case "I" -> CEMTKlasse.I;
            case "II" -> CEMTKlasse.II;
            case "III" -> CEMTKlasse.III;
            case "IV" -> CEMTKlasse.IVa;
            case "V_A" -> CEMTKlasse.Va;
            case "V_B" -> CEMTKlasse.Vb;
            case "VI_A" -> CEMTKlasse.VIa;
            case "VI_B" -> CEMTKlasse.VIb;
            case "VI_C" -> CEMTKlasse.VIc;
            case "VII" -> CEMTKlasse.VIIa;
            default -> null;
        };
    }


}
