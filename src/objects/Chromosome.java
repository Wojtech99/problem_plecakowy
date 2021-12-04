package objects;

public class Chromosome {
    private final String name;
    private int phenotype;
    private int adaptationFunctionValue;
    private int adaptationFunctionWeight;
    private double chanceChoose;
    private String binaryPhenotype;

    public Chromosome(String name, int phenotype) {
        this.name = name;
        this.phenotype = phenotype;
    }

    public int getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(int phenotype) {
        this.phenotype = phenotype;
    }

    public int getAdaptationFunctionValue() {
        return adaptationFunctionValue;
    }

    public void setAdaptationFunctionValue(int adaptationFunctionValue) {
        this.adaptationFunctionValue = adaptationFunctionValue;
    }

    public int getAdaptationFunctionWeight() {
        return adaptationFunctionWeight;
    }

    public void setAdaptationFunctionWeight(int adaptationFunctionWeight) {
        this.adaptationFunctionWeight = adaptationFunctionWeight;
    }

    public double getChanceChoose() {
        return chanceChoose;
    }

    public void setChanceChoose(double chanceChoose) {
        this.chanceChoose = chanceChoose;
    }

    public String getBinaryPhenotype() {
        return binaryPhenotype;
    }

    public void setBinaryPhenotype(String binaryPhenotype) {
        this.binaryPhenotype = binaryPhenotype;
    }

    @Override
    public String toString() {
        return name + " = " + binaryPhenotype +
                ", Phenotype: " + phenotype +
                ", Function adaptation value: " + adaptationFunctionValue +
                ", Function adaptation weight: " + adaptationFunctionWeight +
                ", Kolo: " + chanceChoose;
    }
}
