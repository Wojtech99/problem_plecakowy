package functions;

import objects.Chromosome;
import objects.TableOfBackpack;

import java.util.*;

public class Function {
    private final Helper help = new Helper();

    private final  Random rand = new Random();

    /**
     * function take values from user and return hashMap with parameters
     * and create backpack parameters
     */
    public HashMap<String, Double> takeParameters(){
        String[] tableWithParameters = {"CountOfRepeat", "Pk", "Pm", "maxWeight"};

        HashMap<String, Double> parametersMap = new HashMap<>();

        //user must input parameters here
        Scanner sc = new Scanner(System.in);
        for (String p : tableWithParameters) {
            System.out.print("Enter " + p + ": ");
            double valueForMap = sc.nextDouble();

            parametersMap.put(p, valueForMap);
        }

        return parametersMap;
    }



    public ArrayList<TableOfBackpack> takeBackpackParameters(){
        ArrayList<TableOfBackpack> backpackParameters = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        System.out.print("Are you want input backpack parameters ? (y/n):");
        String userInputChoice = sc.next().toLowerCase(Locale.ROOT);


        if(Objects.equals(userInputChoice, "y")) {
            for (int i = 1; i <= 10; i++) {
                System.out.print("Enter " + i + " position of weight in backpack: ");
                int itemWeight = sc.nextInt();


                System.out.print("Enter " + i + " position of value in backpack: ");
                int itemValue = sc.nextInt();

                backpackParameters.add(new TableOfBackpack(i, itemWeight, itemValue));
            }
        }
        else {
            backpackParameters.add(new TableOfBackpack(1, 13, 17));
            backpackParameters.add(new TableOfBackpack(2, 12, 15));
            backpackParameters.add(new TableOfBackpack(3, 9, 14));

            backpackParameters.add(new TableOfBackpack(4, 14, 8));
            backpackParameters.add(new TableOfBackpack(5, 6, 14));
            backpackParameters.add(new TableOfBackpack(6, 5, 12));

            backpackParameters.add(new TableOfBackpack(7, 7, 3));
            backpackParameters.add(new TableOfBackpack(8, 13, 14));
            backpackParameters.add(new TableOfBackpack(9, 10, 7));

            backpackParameters.add(new TableOfBackpack(10, 13, 9));
        }

        return backpackParameters;
    }



    /**
     * Function generate 6 chromosomes with random values between <0, 1023>
     * @return list with 6 chromosomes (name, phenotype)
     */
    public ArrayList<Chromosome> generateFirstChromosomes(){
        ArrayList<Chromosome> chromosomesList = new ArrayList<>(6);

        for (int i = 1; i <= 6; i++){
            chromosomesList.add(new Chromosome("Ch" + i, rand.nextInt(1024)));
        }

        return chromosomesList;
    }


    /**
     * function calculate values and weight of adaptation
     * @param chromosomeArrayList main list
     * @param parameters list with parameters
     */
    public void calculateAdaptationFunction(ArrayList<Chromosome> chromosomeArrayList,
                                            HashMap<String, Double> parameters,
                                            ArrayList<TableOfBackpack> backpackParameters){
        //max weight for every chromosome
        double maxWeight =  parameters.get("maxWeight");

        //create binary strings in list
        help.convertCollectPhenotypeToBinaryString(chromosomeArrayList);

        //add calculated values and weights to list
        for (int i = 0; i < chromosomeArrayList.size(); i++){
            int calculatedPhenotypeValue = help.calculateChromosomeValue(chromosomeArrayList, backpackParameters, i);
            chromosomeArrayList.get(i).setAdaptationFunctionValue(calculatedPhenotypeValue);

            int calculatedPhenotypeWeight = help.calculateChromosomeWeight(chromosomeArrayList, backpackParameters, i);
            chromosomeArrayList.get(i).setAdaptationFunctionWeight(calculatedPhenotypeWeight);
        }

        //create help list to binary operations
        ArrayList<Chromosome> helpList = help.addToList(chromosomeArrayList);


        for(int i = 0; i < chromosomeArrayList.size(); i++){
            //check the every chromosome weight is less then max weight
            while(chromosomeArrayList.get(i).getAdaptationFunctionWeight() > maxWeight){

                //number of bit index to change
                int placeInStringToMutation = rand.nextInt(10);

                char charToMutation = helpList.get(i).getBinaryPhenotype().charAt(placeInStringToMutation);

                //string after mutate
                String mutatedBinaryString = help.mutateChar(helpList.get(i).getBinaryPhenotype(),
                        charToMutation, placeInStringToMutation);

                //add mutated string to main list
                chromosomeArrayList.get(i).setBinaryPhenotype(mutatedBinaryString);

                //calculate value and weight after mutate
                int calculateValueAfterChange = help.calculateChromosomeValue(chromosomeArrayList, backpackParameters, i);
                int calculateWeightAfterChange = help.calculateChromosomeWeight(chromosomeArrayList, backpackParameters, i);

                //add calculated value and weight to main list
                chromosomeArrayList.get(i).setAdaptationFunctionValue(calculateValueAfterChange);
                chromosomeArrayList.get(i).setAdaptationFunctionWeight(calculateWeightAfterChange);
            }
        }

        help.convertBinaryStringToPhenotype(chromosomeArrayList);

    }


    /**
     * function to draw lots new chromosomes. Draw is based on listOfChromosomes
     * @param listOfChromosomes list with chromosomes
     */
    public void rouletteWheel(ArrayList<Chromosome> listOfChromosomes){
        //list with the range of hitting a point
        ArrayList<Double> listOfFractions = new ArrayList<>();

        //number from the compartment <0,1>
        double random01;

        //a representative variable high level of chromosome
        double randomCheckBox = 0;

        help.addFraction(listOfChromosomes);

        //complementing the ranges of chromosomes
        for (int i = 0; i <= listOfChromosomes.size(); i++) {
            if(i == 0){
                listOfFractions.add(i, 0.0);
                continue;
            }

            randomCheckBox += listOfChromosomes.get(i-1).getChanceChoose();
            listOfFractions.add(i, randomCheckBox);
        }


        //help list
        ArrayList<Chromosome> helpList = help.addToList(listOfChromosomes);

        for(int j = 0; j < 6; j++){
            random01 = rand.nextDouble(101);

            for (int i = 0; i < listOfChromosomes.size(); i++) {
                if (random01 >= listOfFractions.get(i) && random01 < listOfFractions.get(i+1)) {

                    int value = helpList.get(i).getPhenotype();

                    listOfChromosomes.get(j).setPhenotype(value);
                }

            }
        }

    }



    /**
     * function cross chromosomes together
     * @param listOfChromosome main chromosomes list
     * @param mapOfParameters list with parameters
     */
    public void geneticCross(ArrayList<Chromosome> listOfChromosome, Map<String, Double> mapOfParameters){
        //factor Pk
        double Pk = mapOfParameters.get("Pk");

        help.convertCollectPhenotypeToBinaryString(listOfChromosome);

        //help list
        ArrayList<Chromosome> helpList = help.addToList(listOfChromosome);



        for (int i = 0; i < 3; i++){
            //number to compare with factor Pk
            double randPk = rand.nextDouble();

            //a number that specifies cross the scope
            int range = rand.nextInt(10);


            int ch1 = rand.nextInt(6);
            int ch2 = rand.nextInt(6);

            for (int j = range; j < 10; j++){

                if(randPk > Pk){
                    break;
                }

                String chromosomeOne = helpList.get(ch1).getBinaryPhenotype();
                String chromosomeTwo = helpList.get(ch2).getBinaryPhenotype();


                char tempCharChromosomeOne = chromosomeOne.charAt(j);
                char tempCharChromosomeTwo = chromosomeTwo.charAt(j);

                chromosomeOne = help.replaceChar(chromosomeOne, tempCharChromosomeTwo, j);
                chromosomeTwo = help.replaceChar(chromosomeTwo, tempCharChromosomeOne, j);


                listOfChromosome.get(ch1).setBinaryPhenotype(chromosomeOne);
                listOfChromosome.get(ch2).setBinaryPhenotype(chromosomeTwo);
            }

        }

        help.convertBinaryStringToPhenotype(listOfChromosome);

    }


    /**
     * function take every phenotype and mutate single char
     * @param listOfChromosome list of chromosomes
     * @param parameters list wit parameters
     */
    public void geneticMutation(ArrayList<Chromosome> listOfChromosome, Map<String, Double> parameters){

        //help list
        ArrayList<Chromosome> helpList = help.addToList(listOfChromosome);


        //factor Pm
        double Pm = parameters.get("Pm");

        for (int i = 0; i < helpList.size(); i++){
            //number to compare with factor Pm
            double randPm = rand.nextDouble();

            if(randPm > Pm){
                continue;
            }

            //a number representing the location of the bit mutation
            int placeInStringToMutation = rand.nextInt(10);

            char charToMutation = helpList.get(i).getBinaryPhenotype().charAt(placeInStringToMutation);



            String mutatedBinaryString = help.mutateChar(helpList.get(i).getBinaryPhenotype(), charToMutation, placeInStringToMutation);

            listOfChromosome.get(i).setBinaryPhenotype(mutatedBinaryString);
        }

        help.convertBinaryStringToPhenotype(listOfChromosome);

    }

}
