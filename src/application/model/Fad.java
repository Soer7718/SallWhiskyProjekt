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
    private Påfyldning påfyldning;
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
    }

    public Fad(int id, int antalGangeBrugt, String type, String leverandør, String historik, double liter,
               double fyldt, List<Tapning> tapninger, Påfyldning påfyldning) {
        this.id = id;
        this.antalGangeBrugt = antalGangeBrugt;
        this.type = type;
        this.leverandør = leverandør;
        this.historik = historik;
        this.størrelseLiter = liter;
        this.fyldt = fyldt;
        this.tapninger = tapninger != null ? tapninger : new ArrayList<>();
        this.påfyldning = påfyldning;
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
    public Påfyldning getPåfyldning() { return påfyldning; }
    public void setPåfyldning(Påfyldning påfyldning) { this.påfyldning = påfyldning; }
    public String getPlacering() {return placering;}

    public void setPlacering(String placering) {this.placering = placering;}

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
        if (påfyldning == null || påfyldning.getStartDato() == null) {
            return 0;
        }
        LocalDate slut = (påfyldning.getSlutDato() != null) ? påfyldning.getSlutDato() : LocalDate.now();
        return Period.between(påfyldning.getStartDato(), slut).getYears();
    }

    public boolean erTomt() {
        return fyldt == 0.0;
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

    public void setFadPåLager() {

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
