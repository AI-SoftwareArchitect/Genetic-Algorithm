import java.util.Random;

public class Main {
    static final int POPULATION_SIZE = 50;
    static final int GENE_LENGTH = 10;
    static final int MAX_GENERATIONS = 100;
    static final double MUTATION_RATE = 0.05;
    static final double CROSSOVER_RATE = 0.8;
    static final double TARGET = 2.0;

    static Random rand = new Random();

    public static void main(String[] args) {
        double[][] population = initializePopulation();
        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            double[] fitnessScore = evaluateFitness(population);

            double[][] newPopulation = new double[POPULATION_SIZE][GENE_LENGTH];
            for (int i = 0; i < POPULATION_SIZE; i++) {
                double[] parent1 = select(population, fitnessScore);
                double[] parent2 = select(population, fitnessScore);
                double[] child = crossover(parent1, parent2);
                mutate(child);
                newPopulation[i] = child;
            }

            population = newPopulation;

            int bestIndex = getBestIndex(fitnessScore);
        }


    }

    static void mutate(double[] individual) {
        for (int i = 0; i < GENE_LENGTH; i++) {
            if (rand.nextDouble() < MUTATION_RATE) {
                individual[i] = rand.nextDouble() * 200;
            }
        }
    }

    static int getBestIndex(double[] fitness) {
        int best = 0;
        for (int i = 1; i < fitness.length; i++) {
            if (fitness[i] < fitness[best]) {
                best = i;
            }
        }
        return best;
    }

    static double[] crossover(double[] parent1, double[] parent2) {
        double[] child = new double[GENE_LENGTH];
        if (rand.nextDouble() < CROSSOVER_RATE) {
            int point = rand.nextInt(GENE_LENGTH);
            for (int i = 0; i < GENE_LENGTH; i++) {
                child[i] = (i < point) ? parent1[i] : parent2[i];
            }
        } else {
            child = parent1.clone();
        }
        return child;
    }

    static double[] select(double[][] population, double[] fitness) {
        int a = rand.nextInt(POPULATION_SIZE);
        int b = rand.nextInt(POPULATION_SIZE);
        return (fitness[a] < fitness[b]) ? population[a] : population[b];
    }

    static double[] evaluateFitness(double[][] population) {
        double[] scores = new double[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            scores[i] = fitness(population[i]);
        }
        return scores;
    }

    static double fitness(double[] individual) {
        double sum = 0;
        for (double gene : individual) {
            sum += Math.pow(gene - TARGET, 2);
        }
        return sum;
    }


    static double[][] initializePopulation() {
        double[][] pop = new double[POPULATION_SIZE][GENE_LENGTH];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < GENE_LENGTH; j++) {
                pop[i][j] = rand.nextDouble() * 200;
            }
        }
        return pop;
    }


}
