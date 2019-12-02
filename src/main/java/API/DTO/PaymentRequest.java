package API.DTO;

public class PaymentRequest {
    private String ibanOntvanger;
    private String iban;
    private String naamOntvanger;
    private int bedrag;
    private String omschrijving;
    private String optie;
    private String datum;

    public PaymentRequest(String ibanOntvanger, String iban, String naamOntvanger, int bedrag, String omschrijving, String optie, String datum) {
        this.ibanOntvanger = ibanOntvanger;
        this.iban = iban;
        this.naamOntvanger = naamOntvanger;
        this.bedrag = bedrag;
        this.omschrijving = omschrijving;
        this.optie = optie;
        this.datum = datum;
    }

    public PaymentRequest() {
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getNaamOntvanger() {
        return naamOntvanger;
    }

    public void setNaamOntvanger(String naamOntvanger) {
        this.naamOntvanger = naamOntvanger;
    }

    public String getIbanOntvanger() {
        return ibanOntvanger;
    }

    public void setIbanOntvanger(String ibanOntvanger) {
        this.ibanOntvanger = ibanOntvanger;
    }

    public int getBedrag() {
        return bedrag;
    }

    public void setBedrag(int bedrag) {
        this.bedrag = bedrag;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOptie() {
        return optie;
    }

    public void setOptie(String optie) {
        this.optie = optie;
    }
}

