import java.util.ArrayList;
import java.util.List;

public class SelectionneurSimple implements Selectionneur {
    private double seuil;
    public SelectionneurSimple(double seuil){
        this.seuil=seuil;

    }
    @Override
    public List<CoupleNomScore> selectionner(List<CoupleNomScore> candidats) {
        List<CoupleNomScore> meilleurs = new ArrayList<>();
        for (CoupleNomScore c : candidats) {
            if (c.getScore() >= seuil) {
                meilleurs.add(c);
            }
        }
        return meilleurs;
    }
}
