package loadExcel;

public class LoadExcelVolvoRow {

    private int productGroup;
    private int functionalGroup;
    private String partNumber;
    private String namePart;
    private int saleGroup;
    private Double retailPrice;

    public LoadExcelVolvoRow(int productGroup, int functionalGroup, String partNumber, String namePart, int saleGroup, Double retailPrice) {
        this.productGroup = productGroup;
        this.functionalGroup = functionalGroup;
        this.partNumber = partNumber;
        this.namePart = namePart;
        this.saleGroup = saleGroup;
        this.retailPrice = retailPrice;
    }


    public int getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(int productGroup) {
        this.productGroup = productGroup;
    }

    public int getFunctionalGroup() {
        return functionalGroup;
    }

    public void setFunctionalGroup(int functionalGroup) {
        this.functionalGroup = functionalGroup;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    public int getSaleGroup() {
        return saleGroup;
    }

    public void setSaleGroup(int saleGroup) {
        this.saleGroup = saleGroup;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }
}
