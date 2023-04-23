import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String GENES = "abcçdefgğhiıjklmnoöpqrsştuüvwxyzABCÇDEFGĞHIİJKLMNOÖPQRSŞTUÜVWXYZ1234567890 ";
    private static final String OPTIMUM = "Genetic Algorithm 2022";
    private static final double MUTATION_PROB = 0.05;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int POPULATION_SIZE;

        double generation_mean = 0;

        int count = 1;
        while (count < 6) {

            System.out.println(count + "/5");
            System.out.printf("Please enter population number: "); // need big number

            POPULATION_SIZE = scanner.nextInt();

            long startTime = System.currentTimeMillis();
            int generation = 1;
            ArrayList<Individual> population = new ArrayList<>();

            for (int i = 0; i < POPULATION_SIZE; i++) {
                String chromsome = Individual.intial_population();
                population.add(new Individual(chromsome));
            }

            while (true) {
                ArrayList<Individual> new_generation = new ArrayList<>();
                Collections.sort(population);

                if (population.get(0).fitness == OPTIMUM.length()) {
                    System.out.println("Generation: " + generation + " String: " + population.get(0).chromosome + " Fitness: " + population.get(0).fitness);
                    generation_mean += generation;
                    break;
                }
                for (int i = 0; i < POPULATION_SIZE; i++) {
                    Individual parent1 = population.get(0);
                    Individual parent2 = population.get(1);
                    Individual child = parent1.crossover(parent2);
                    new_generation.add(child);
                }
                population = new_generation;
                System.out.println("Generation: " + generation + " String: " + population.get(0).chromosome + " Fitness: " + population.get(0).fitness);
                generation += 1;
            }
            System.out.println("*******************************************************************");

            System.out.println("Calculation time: " + (System.currentTimeMillis() - startTime) + "ms");
            System.out.println("Genaration average: " + generation_mean / count++);

            System.out.println("*******************************************************************");
        }
    }

    static class Individual implements Comparable{

        String chromosome;
        int fitness;

        static Random rnd = new Random();

        public Individual(String chromosome) {
            this.chromosome = chromosome;
            this.fitness = calculate_fitness();
        }

        static char mutated_genes() {
            char gene = GENES.charAt(rnd.nextInt(GENES.length()));
            return gene;
        }

        static String intial_population() {
            int chromosome_len = OPTIMUM.length();
            String str = "";
            for (int i = 0; i < chromosome_len ; i++) {
                str += mutated_genes();
            }
            return str;
        }

        int calculate_fitness() {
            int fitness = 0;

            for (int i = 0; i < OPTIMUM.length(); i++) {
                if (this.chromosome.charAt(i) == OPTIMUM.charAt(i))
                    fitness++;
            }
            return fitness;
        }

        Individual crossover(Individual par2) {
            String child_chromosome = "";
            for (int i = 0; i < this.chromosome.length(); i++) {
                double prob = rnd.nextDouble();
                if (prob < (1-MUTATION_PROB)/2 )
                    child_chromosome += this.chromosome.charAt(i);
                else if (prob < (1-MUTATION_PROB))
                    child_chromosome += par2.chromosome.charAt(i);
                else
                    child_chromosome += this.mutated_genes();
            }
            return new Individual(child_chromosome);
        }

        @Override
        public int compareTo(Object o) {
            int compareage=((Individual)o).fitness;

            return compareage - this.fitness;
        }
    }
}
