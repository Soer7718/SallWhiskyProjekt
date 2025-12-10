package application.model;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Fad {
    private final int id;
    private int antalGangeBrugt;
    private String type;
    private String leverandør;
    private String historik;
    private double størrelseLiter;
    private double fyldt;
    private List<Tapning> tapninger;
    private List<Påfyldning> påfyldninger;
    private String placering;

    // --- Konstruktører ---
    public Fad(int id, int antalGangeBrugt, String type, String leverandør, String historik, double liter) {
        this.id = id;
        this.antalGangeBrugt = antalGangeBrugt;
        this.type = type;
        this.leverandør = leverandør;
        this.historik = historik;
        this.størrelseLiter = liter;
        this.fyldt = 0.0;
        this.tapninger = new ArrayList<>();
        this.påfyldninger = new ArrayList<>();
    }

    public Fad(int id, int antalGangeBrugt, String type, String leverandør, String historik, double liter, double fyldt, List<Tapning> tapninger, Påfyldning påfyldning) {
        this.id = id;
        this.antalGangeBrugt = antalGangeBrugt;
        this.type = type;
        this.leverandør = leverandør;
        this.historik = historik;
        this.størrelseLiter = liter;
        this.fyldt = fyldt;
        /*
        * if (tapninger != null) {
        * this.tapninger = tapninger;
        * } else {
        * this.tapninger = new ArrayList<>();
        * } samme som under
        * */
        this.tapninger = tapninger != null ? tapninger : new ArrayList<>();
        this.påfyldninger = new ArrayList<>();
    }

    public Tapning createTapning(double alkoholProcent, double mængdeVand, String vandOprindelse, List<WhiskyType> typer, String navn) {
        Tapning tapning = new Tapning(alkoholProcent, mængdeVand, vandOprindelse, typer, navn);
        tapninger.add(tapning);
        return tapning;
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public int getAntalGangeBrugt() { return antalGangeBrugt; }
    public void setAntalGangeBrugt(int antal) { this.antalGangeBrugt = antal; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLeverandør() { return leverandør; }
    public void setLeverandør(String leverandør) { this.leverandør = leverandør; }
    public String getHistorik() { return historik; }
    public void setHistorik(String historik) { this.historik = historik; }
    public double getStørrelseLiter() { return størrelseLiter; }
    public void setStørrelseLiter(double størrelseLiter) { this.størrelseLiter = størrelseLiter; }
    public double getFyldt() { return fyldt; }
    public void setFyldt(double fyldt) { this.fyldt = fyldt; }
    public List<Påfyldning> getPåfyldninger() { return new ArrayList<>(påfyldninger); }
    public void setPåfyldning(List<Påfyldning> påfyldninger) {
        this.påfyldninger = påfyldninger != null ? new ArrayList<>(påfyldninger) : new ArrayList<>(); }

    public void addPåfyldning (Påfyldning påfyldning) {
        if (!påfyldninger.contains(påfyldning)){
            påfyldninger.add(påfyldning);
        }
    }
    public String getPlacering() {return placering;}
    public void setPlacering(String placering) {this.placering = placering;}

    //Alle Metoder

    // --- Tapninger ---
    public List<Tapning> getTapninger() {
        return new ArrayList<>(tapninger);
    }

    public void addTapning(Tapning tapning) {
        if (tapning != null && !tapninger.contains(tapning)) {
            tapninger.add(tapning);
        }
    }

    // --- Hjælpe-metoder ---
    public int udregnLagringsÅr() {
        if (påfyldninger.isEmpty()) {
            return 0;
        }

        Påfyldning nyeste = påfyldninger.get(0);
        for (Påfyldning p : påfyldninger){
            if (p.getStartDato().isBefore(nyeste.getStartDato())){
                nyeste = p;
            }
        }

        LocalDate slut = (nyeste.getSlutDato() != null) ? nyeste.getSlutDato() : LocalDate.now();
        return Period.between(nyeste.getStartDato(), slut).getYears();
    }

    public boolean erTomt() {
        return fyldt == 0.0;
    }

    public void omhældTilAndetFad(Fad f, double mængdeOverført){
        if (mængdeOverført <= 0){
            return;
        }

        if (f.getFyldt() + mængdeOverført > f.getStørrelseLiter()){
            return;
        }

        this.setFyldt(this.getFyldt()-mængdeOverført);
        f.setFyldt(f.getFyldt()+mængdeOverført);
        for (Påfyldning p : påfyldninger){
            f.addPåfyldning(p);
        }
        if (this.erTomt()){
            this.setPåfyldning(null);
        }
    }

    public double beregnAlkoholProcent() {
        if (tapninger.isEmpty()) return 0.0;
        double total = 0.0;
        for (Tapning t : tapninger) {
            total += t.getAlkoholProcent();
        }
        return total / tapninger.size();
    }

    public double beregnMængde() {
        double total = 0.0;
        for (Tapning t : tapninger) {
            total += t.getFuldMængde();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Fad{" +
                "id=" + id +
                ", antalGangeBrugt=" + antalGangeBrugt +
                ", type='" + type + '\'' +
                ", leverandør='" + leverandør + '\'' +
                ", historik='" + historik + '\'' +
                ", fyldt=" + fyldt +
                '}';
    }
}
