package application.model;

import java.util.ArrayList;
import java.util.List;

public class Gang {
    private int gangNummer;
    private List<Reol> reolList = new ArrayList<>();
    private Lager lager;

    public Gang(Lager lager, int gangNummer) {
        this.lager = lager;
        this.gangNummer = gangNummer;

    }

    public int getGangNummer() {
        return gangNummer;
    }

    public List<Reol> getReolList() {
        return new ArrayList<>(reolList);
    }

    public Lager getLager() {
        return lager;
    }

    public void createReol(int antalReoler, int antalHylder) {
        for (int i = 0; i < antalReoler; i++) {
            Reol reol = new Reol(this, reolList.size()+1, antalHylder);
            reolList.add(reol);
        }
    }


}
