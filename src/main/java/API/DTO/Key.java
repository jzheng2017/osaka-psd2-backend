package API.DTO;

public class Key {
    //Waardes uit Response van OAuth, zie: https://developer.ing.com/api-marketplace/marketplace/2d00fd5f-88cd-4416-bbca-f309ebb03bfe/documentation#step-4-authenticate-your-application-to-retrieve-an-application-access-token
    private String kty;
    private String use;
    private String n;
    private String e;
    private String alg;
    private String x5t;

    public Key() {
    }

    public Key(String kty, String use, String n, String e, String alg, String x5t) {
        this.kty = kty;
        this.use = use;
        this.n = n;
        this.e = e;
        this.alg = alg;
        this.x5t = x5t;
    }

    public String getKty() {
        return kty;
    }

    public void setKty(String kty) {
        this.kty = kty;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getX5t() {
        return x5t;
    }

    public void setX5t(String x5t) {
        this.x5t = x5t;
    }
}


