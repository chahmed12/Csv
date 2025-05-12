import java.util.List;
import java.util.stream.Collectors;

public class MoteurDeRecherche {
    private Comparateur<EntreeNom> comparateurNom;
    private GenerateurCandidat generateurCandidat;
    private Selectionneur selectionneur;
    private List<Pretraiteur> pretraiteurs;
    private List<EntreeNom> basePretraitee; // base prétraitée une seule fois

    public MoteurDeRecherche(Comparateur<EntreeNom> comparateurNom,
                             GenerateurCandidat generateurCandidat,
                             Selectionneur selectionneur,
                             List<Pretraiteur> pretraiteurs) {
        this.comparateurNom = comparateurNom;
        this.generateurCandidat = generateurCandidat;
        this.selectionneur = selectionneur;
        this.pretraiteurs = pretraiteurs;
    }

    // Initialisation unique de la base
    public void initialiserBase(List<EntreeNom> base) {
        List<String> nomsBase = base.stream()
                .map(EntreeNom::get_Nom)
                .collect(Collectors.toList());

        List<String> nomsBasePretraite = appliquerPretraiteurs(nomsBase);

        this.basePretraitee = nomsBasePretraite.stream()
                .map(EntreeNom::new)
                .collect(Collectors.toList());
    }

    // Applique en chaîne les prétraiteurs
    private List<String> appliquerPretraiteurs(List<String> noms) {
        List<String> resultats = noms;
        for (Pretraiteur p : pretraiteurs) {
            resultats = p.traiter(resultats);
        }
        return resultats;
    }

    public List<CoupleNomScore> rechercher(List<EntreeNom> base, String nomRecherche) {
        // Prétraitement de la base à chaque appel
        List<String> nomsBase = base.stream()
                .map(EntreeNom::get_Nom)
                .collect(Collectors.toList());
        List<String> nomsBasePretraite = appliquerPretraiteurs(nomsBase);
        List<EntreeNom> basePretraiteeLocale = nomsBasePretraite.stream()
                .map(EntreeNom::new)
                .collect(Collectors.toList());

        // Prétraitement du nom recherché
        List<String> nomRechercheTraite = appliquerPretraiteurs(List.of(nomRecherche));
        EntreeNom recherche = new EntreeNom(nomRechercheTraite.get(0));
        List<EntreeNom> rechercheList = List.of(recherche);

        // Génération des couples (recherche vs base)
        List<CoupleNom> couples = generateurCandidat.generer(basePretraiteeLocale, rechercheList);

        // Comparaison parallèle
        List<CoupleNomScore> scores = couples.parallelStream()
                .map(c -> new CoupleNomScore(
                        c.getNom1().get_Nom(),
                        c.getNom2().get_Nom(),
                        comparateurNom.comparer(c.getNom1(), c.getNom2())))
                .collect(Collectors.toList());

        return selectionneur.selectionner(scores);
    }

}
