public class ComparateurLevenshtein implements Comparateur<EntreeNom> {

    @Override
    public double comparer(EntreeNom nom1, EntreeNom nom2) {
        String nom1Str = nom1.get_Nom(); // attention : méthode = getNom(), pas get_Nom()
        String nom2Str = nom2.get_Nom();

        int distance = distanceLevenshtein(nom1Str, nom2Str);
        int maxLength = Math.max(nom1Str.length(), nom2Str.length());

        if (maxLength == 0) return 1.0; // les deux noms sont vides
        return 1.0 - (double) distance / maxLength;
    }

    private int distanceLevenshtein(String str1, String str2) {
        int lenStr1 = str1.length();
        int lenStr2 = str2.length();
        int[][] dp = new int[lenStr1 + 1][lenStr2 + 1];

        for (int i = 0; i <= lenStr1; i++) {
            for (int j = 0; j <= lenStr2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1)
                    );
                }
            }
        }

        return dp[lenStr1][lenStr2];
    }
}
