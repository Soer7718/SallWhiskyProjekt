package application.model;

public class Whisky {
    private double mængde;
    private String navn;
    private int nummer;
    private double alkoholProcent;
    private String label;
    private WhiskyType type;
    private Tapning tapning;

    public Whisky(double mængde, String navn, int nummer, double alkoholProcent, String label, WhiskyType type, Tapning tapning){
        this.mængde = mængde;
        this.navn = navn;
        this.nummer = nummer;
        this.alkoholProcent = alkoholProcent;
        this.label = label;
        this.type = type;
        this.tapning = tapning;
    }

    public double getMængde() {
        return mængde;
    }

    public void setMængde(double mængde) {
        this.mængde = mængde;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public void setAlkoholProcent(double alkoholProcent) {
        this.alkoholProcent = alkoholProcent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public WhiskyType getType() {
        return type;
    }

    public void setType(WhiskyType type) {
        this.type = type;
    }

    public Tapning getTapning() {
        return tapning;
    }

    public void setTapning(Tapning tapning) {
        this.tapning = tapning;
    }
}
