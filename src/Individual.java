import java.util.Random;

public class Individual {
    int fitness = 0;
    int peso = 0;
    int[] genes = new int[6];
    int[] pontosArr = new int[]{15, 7, 10, 5, 8, 17};
    int[] pesoArr = new int[]{15, 3, 2, 5, 9, 20};
    int geneLength = 6;

    public Individual() {
        Random rn = new Random();

        //Set genes randomly for each individual
        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.abs(rn.nextInt() % 2);
        }

        fitness = 0;
    }

    //Calculate fitness
    public void calcFitness() {

        fitness = 0;
        peso = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] != 0) {
                fitness = fitness + pontosArr[i];
                peso = peso + pesoArr[i];
            }
        }
        if (peso > 30) {
            fitness = 0;
        }
    }

}
