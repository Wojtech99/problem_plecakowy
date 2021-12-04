package functions;

import objects.Chromosome;
import objects.TableOfBackpack;

import java.util.ArrayList;

public class Helper {

    /**
     * Function convert phenotype to binary number
     * @param listOfChromosomes list of chromosomes
     * @return list of chromosomes with changed binary Phenotype
     */
    protected ArrayList<Chromosome> convertCollectPhenotypeToBinaryString(ArrayList<Chromosome> listOfChromosomes){
        //lista Stringów

        for (int i = 0; i < listOfChromosomes.size(); i++){
            int temp = listOfChromosomes.get(i).getPhenotype();

            String stringTemp = Integer.toBinaryString(temp);

            int stringTempLength = stringTemp.chars().toArray().length;

            if(stringTempLength == 9 ) {
                stringTemp = "0" + stringTemp;
            }
            else if(stringTempLength == 8) {
                stringTemp = "00" + stringTemp;
            }
            else if(stringTempLength == 7) {
                stringTemp = "000" + stringTemp;
            }
            else if(stringTempLength == 6) {
                stringTemp = "0000" + stringTemp;
            }
            else if(stringTempLength == 5) {
                stringTemp = "00000" + stringTemp;
            }
            else if(stringTempLength == 4) {
                stringTemp = "000000" + stringTemp;
            }
            else if(stringTempLength == 3) {
                stringTemp = "0000000" + stringTemp;
            }
            else if(stringTempLength == 2) {
                stringTemp = "00000000" + stringTemp;
            }
            else if(stringTempLength == 1) {
                stringTemp = "000000000" + stringTemp;
            }

            listOfChromosomes.get(i).setBinaryPhenotype(stringTemp);
        }
        return listOfChromosomes;
    }


    /**
     * function convert binary phenotype to int
     * @param listOfChromosomes list of chromosomes
     * @return chromosome list witch changed phenotype
     */
    public ArrayList<Chromosome> convertBinaryStringToPhenotype(ArrayList<Chromosome> listOfChromosomes){

        for (int i = 0; i < listOfChromosomes.size(); i++){
            int temp = Integer.parseInt(listOfChromosomes.get(i).getBinaryPhenotype(), 2);

            listOfChromosomes.get(i).setPhenotype(temp);
        }

        return listOfChromosomes;
    }


    /**
     * function take parameters and then calculate Values function
     * @param chromosomeArrayList list of chromosomes
     * @param backpackParameters list of parameter to function
     * @param indexOfList index in list of chromosomes
     * @return result after calculate function
     */
    protected int calculateChromosomeValue(
            ArrayList<Chromosome> chromosomeArrayList,
            ArrayList<TableOfBackpack> backpackParameters,
            int indexOfList){
        //list with bits of binary phenotype
        ArrayList<Integer> listOfBits = new ArrayList<>();

        //list with values from backpack parameters to calculate every bit
        ArrayList<Integer> listOfValues = new ArrayList<>();

        int result = 0;

        //adding to list operation
        for(int i = 0; i < backpackParameters.size(); i++) {
            int bitOfBinaryPhenotype = Integer.parseInt(String.valueOf(chromosomeArrayList.get(indexOfList).getBinaryPhenotype().charAt(i)));
            listOfBits.add(bitOfBinaryPhenotype);

            int parameterToCalculate = backpackParameters.get(i).getItemValue();
            listOfValues.add(parameterToCalculate);
        }

        //calculate value operation
        for(int i = 0; i < backpackParameters.size(); i++){
            result += listOfBits.get(i) * listOfValues.get(i);
        }

        return result;
    }


    /**
     * function take parameters and then calculate weight function
     * @param chromosomeArrayList list of chromosomes
     * @param backpackParameters list of parameter to function
     * @param indexOfList index in list of chromosomes
     * @return result after calculate function
     */
    protected int calculateChromosomeWeight(
            ArrayList<Chromosome> chromosomeArrayList,
            ArrayList<TableOfBackpack> backpackParameters, int indexOfList){
        //list with bits of binary phenotype
        ArrayList<Integer> listOfBits = new ArrayList<>();

        //list with weights from backpack parameters to calculate every bit
        ArrayList<Integer> listOfValues = new ArrayList<>();

        int result = 0;

        //adding to list operation
        for(int i = 0; i < backpackParameters.size(); i++) {
            int bitOfBinaryPhenotype = Integer.parseInt(String.valueOf(chromosomeArrayList.get(indexOfList).getBinaryPhenotype().charAt(i)));
            listOfBits.add(bitOfBinaryPhenotype);

            int parameterToCalculate = backpackParameters.get(i).getItemWeight();
            listOfValues.add(parameterToCalculate);
        }

        //calculate weight operation
        for(int i = 0; i < backpackParameters.size(); i++){
            result += listOfBits.get(i) * listOfValues.get(i);
        }

        return result;
    }


    /**
     * function change single char in binary string
     * @param myString provided string
     * @param myChar char what will replace to another
     * @param index index of string to replace
     * @return mutated string
     */
    protected String mutateChar(String myString, char myChar, int index){
        switch (myChar){
            case '0' -> myChar = '1';
            case '1' -> myChar = '0';
        }

        StringBuilder myNewString = new StringBuilder(myString);
        myNewString.setCharAt(index, myChar);
        return myNewString.toString();
    }


    /**
     *function replace char in index destination
     * @param myString replacing string
     * @param myChar char which one do you want change
     * @param index place in string to replace
     * @return string with replaced char
     */
    protected String replaceChar(String myString, char myChar, int index){
        StringBuilder myNewString = new StringBuilder(myString);
        myNewString.setCharAt(index, myChar);
        return myNewString.toString();
    }



    /**
     * function duplicate list as new list
     * @param list list to duplicate
     * @param <T> type of lists
     * @return new list
     */
    protected <T> ArrayList<T> addToList(ArrayList<T> list){
        ArrayList<T> result = new ArrayList<>();

        result.addAll(list);
        return result;
    }

    /**
     * function add fractions to list
     * @param listOfChromosomes main list
     * @return the same list with added fractions
     */
    public ArrayList<Chromosome> addFraction(ArrayList<Chromosome> listOfChromosomes){
        //sum of all adaptation function values
        int sumOfCalculatedValues = listOfChromosomes.stream().mapToInt(Chromosome::getAdaptationFunctionValue).sum();

        for (Chromosome chromosome : listOfChromosomes) {
            double number = chromosome.getAdaptationFunctionValue();
            double fraction = (number / (double) sumOfCalculatedValues) * 100;

            chromosome.setChanceChoose(fraction);
        }


        return listOfChromosomes;
    }
}
