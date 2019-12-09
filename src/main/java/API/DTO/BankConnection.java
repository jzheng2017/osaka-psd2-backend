package API.DTO;

public class BankConnection {
    private boolean limitReached;
    private int allowedConnections;

    public BankConnection(boolean limitReached, int allowedConnections) {
        this.limitReached = limitReached;
        this.allowedConnections = allowedConnections;
    }

    public BankConnection() {
    }

    public boolean isLimitReached() {
        return limitReached;
    }

    public void setLimitReached(boolean limitReached) {
        this.limitReached = limitReached;
    }

    public int getAllowedConnections() {
        return allowedConnections;
    }

    public void setAllowedConnections(int allowedConnections) {
        this.allowedConnections = allowedConnections;
    }
}
