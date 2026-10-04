package uz.payflow.account;

public enum Currency {
    UZS("000"),
    USD("840"),
    EUR("978");

    private final String numericCode;

    Currency(String numericCode) {
        this.numericCode = numericCode;
    }

    public String getNumericCode() {
        return numericCode;
    }
}
