package application.model;

public class Whisky {
    private double mængde;
    private String navn;
    private int nummer;
    private double alkoholProcent;
    private String label;
    private WhiskyType type;

    public Whisky(double mængde, String navn, int nummer, double alkoholProcent, String label, WhiskyType type){
        this.mængde = mængde;
        this.navn = navn;
        this.nummer = nummer;
        this.alkoholProcent = alkoholProcent;
        this.label = label;
        this.type = type;
    }
}
