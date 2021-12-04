import functions.Function;
import functions.Helper;
import objects.Chromosome;
import objects.TableOfBackpack;

import java.util.ArrayList;
import java.util.HashMap;


public class Program {
    public static void main(String[] args) {
        final Function function = new Function();


        ArrayList<Chromosome> chromosomes = function.generateFirstChromosomes();

        HashMap<String, Double> parameters = function.takeParameters();

        ArrayList<TableOfBackpack> tableOfBackpackList = function.takeBackpackParameters();


        int sumAdaptationFunction;
        int maxSumAdaptationFunction = 0;
        int repeatCount = 0;
        int loopIteration = 0;

        while (repeatCount != parameters.get("CountOfRepeat")) {
            loopIteration++;

            function.calculateAdaptationFunction(chromosomes, parameters, tableOfBackpackList);

            function.rouletteWheel(chromosomes);

            function.geneticCross(chromosomes, parameters);

            function.geneticMutation(chromosomes, parameters);

            makeResultList(chromosomes, parameters, tableOfBackpackList);


            sumAdaptationFunction = chromosomes.stream().mapToInt(Chromosome::getAdaptationFunctionValue).sum();

            if(maxSumAdaptationFunction == sumAdaptationFunction){
                repeatCount++;
            }
            else if (maxSumAdaptationFunction < sumAdaptationFunction){
                repeatCount = 1;
                maxSumAdaptationFunction = sumAdaptationFunction;
            }

            System.out.println("\nIteration: " + loopIteration +
                    ", repeat count: " + repeatCount +
                    ", max sum function adaptation: " + maxSumAdaptationFunction +
                    ", sum function adaptation: " + sumAdaptationFunction);
            chromosomes.forEach(chromosome -> System.out.println(chromosome.toString()));
        }
    }


    public static ArrayList<Chromosome> makeResultList(ArrayList<Chromosome> chromosomeArrayList,
                                                       HashMap<String, Double> parameters,
                                                       ArrayList<TableOfBackpack> backpackArrayList){
        final Function fun = new Function();

        final Helper helper = new Helper();

        fun.calculateAdaptationFunction(chromosomeArrayList, parameters, backpackArrayList);

        helper.convertBinaryStringToPhenotype(chromosomeArrayList);

        helper.addFraction(chromosomeArrayList);

        return chromosomeArrayList;
    }
}
