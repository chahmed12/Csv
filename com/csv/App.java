import java.util.List;

public class App {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();


        Pretraiteur pretraitementSimple = new PretraiteurSimple();
        Pretraiteur pretraitementPhonetique = new PretraiteurPhonetique();
        List<Pretraiteur> pretraiteurs = List.of(pretraitementSimple, pretraitementPhonetique);

        Comparateur<EntreeNom> comparateur = new ComparateurLevenshtein();
        GenerateurCandidat generateur = new GenerateurSimple();
        Selectionneur selectionneur = new SelectionneurSimple(0.8);


        recuperateurCsv recuperateur = new recuperateurCsv("/home/chahmed/Téléchargements/noms_100000.csv");
        List<EntreeNom> baseInitiale = recuperateur.recuperer();

        Deduplication dedup = new Deduplication(pretraiteurs);
        List<EntreeNom> basePretraiteeDedup = dedup.dedupliquer(baseInitiale);


        MoteurDeRecherche moteur = new MoteurDeRecherche(comparateur, generateur, selectionneur, pretraiteurs);


        String nomRecherche = "hassenleroy";
        List<CoupleNomScore> resultats = moteur.rechercher(basePretraiteeDedup, nomRecherche);


        for (CoupleNomScore r : resultats) {
            System.out.println("Nom trouvé : " + r.getNom11() + " | Score : " + r.getScore());
        }


        long endTime = System.currentTimeMillis();
        System.out.println("\nTemps total d'exécution : " + (endTime - startTime) + " ms");
    }
}
