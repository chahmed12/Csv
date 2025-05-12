import java.util.*;
import java.util.stream.Collectors;

public class Deduplication {
    private List<Pretraiteur> pretraiteurs;

    public Deduplication(List<Pretraiteur> pretraiteurs) {
        this.pretraiteurs = pretraiteurs;
    }

    private List<String> appliquerPretraiteurs(List<String> noms) {
        List<String> resultats = noms;
        for (Pretraiteur p : pretraiteurs) {
            resultats = p.traiter(resultats);
        }
        return resultats;
    }

    public List<EntreeNom> dedupliquer(List<EntreeNom> base) {
        // Extraire les noms bruts
        List<String> noms = base.stream()
                .map(EntreeNom::get_Nom)
                .collect(Collectors.toList());

        // Appliquer les prétraiteurs (si besoin)
        List<String> nomsTraites = appliquerPretraiteurs(noms);

        // Supprimer les doublons
        Set<String> unique = new LinkedHashSet<>(nomsTraites); // garde l'ordre d'insertion

        // Reconstituer des objets EntreeNom uniques
        return unique.stream()
                .map(EntreeNom::new)
                .collect(Collectors.toList());
    }
}
