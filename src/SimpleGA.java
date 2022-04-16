import java.util.Random;

public class SimpleGA {
    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;
    String[] itemArr = new String[]{"Saco de Dormir", "Corda", "Canivete", "Tocha", "Garrafa", "Comida"};
    int comparator = 0;
    int index = 0;
    int[] comb = new int[6];
    int initialGenFittest = 0;

    public static void main(String[] args) {

        Random rn = new Random();

        SimpleGA demo = new SimpleGA();

        //Initialize population
        demo.population.initializePopulation(10);

        //Calculate fitness of each individual
        demo.population.calculateFitness();

        System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);
        demo.initialGenFittest = demo.population.fittest;

        //While population gets an individual with maximum fitness
        while (demo.generationCount < 30) {
            ++demo.generationCount;

            //Do selection
            demo.selection();

            //Do crossover
            demo.crossover();

            //Do mutation under a random probability
            if (rn.nextInt()%7 < 5) {
                demo.mutation();
            }

            //Add the fittest offspring to population
            demo.addFittestOffspring();

            //Calculate new fitness value
            demo.population.calculateFitness();

            System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);

            //Gets the fittest offspring
            if (demo.population.fittest > demo.comparator) {
                demo.comparator = demo.population.fittest;
                demo.index = demo.generationCount;
                for (int i = 0; i < 6; i++) {
                    demo.comb[i] = demo.population.getFittest().genes[i];
                }
            }
        }

        //compares initial generation with best offspring
        if (demo.comparator < demo.initialGenFittest) {
            demo.comparator = demo.initialGenFittest;
            demo.index = 0;
        }

        //prints the best solution found
        System.out.println("\nBest solution was found in generation " + demo.index);
        System.out.println("Fitness: " + demo.comparator);
        System.out.println("Genes: ");
        for (int i = 0; i < 6; i++) {
            System.out.println(demo.itemArr[i] + " = " + demo.comb[i]);
        }

        System.out.println("");

    }

    //Selection
    void selection() {

        //Select the most fittest individual
        fittest = population.getFittest();

        //Select the second most fittest individual
        secondFittest = population.getSecondFittest();
    }

    //Crossover
    void crossover() {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(population.individuals[0].geneLength);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;

        }

    }

    //Mutation
    void mutation() {
        Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        //Flip values at the mutation point
        if (fittest.genes[mutationPoint] == 0) {
            fittest.genes[mutationPoint] = 1;
        } else {
            fittest.genes[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        if (secondFittest.genes[mutationPoint] == 0) {
            secondFittest.genes[mutationPoint] = 1;
        } else {
            secondFittest.genes[mutationPoint] = 0;
        }
    }

    //Get fittest offspring
    Individual getFittestOffspring() {
        if (fittest.fitness > secondFittest.fitness) {
            return fittest;
        }
        return secondFittest;
    }


    //Replace least fittest individual from most fittest offspring
    void addFittestOffspring() {

        //Update fitness values of offspring
        fittest.calcFitness();
        secondFittest.calcFitness();

        //Get index of least fit individual
        int leastFittestIndex = population.getLeastFittestIndex();

        //Replace least fittest individual from most fittest offspring
        population.individuals[leastFittestIndex] = getFittestOffspring();
    }
}
