package API.DTO;

public class BankConnection {
    private boolean canConnect;
    private int allowedConnections;

    public BankConnection(boolean canConnect, int allowedConnections) {
        this.canConnect = canConnect;
        this.allowedConnections = allowedConnections;
    }

    public BankConnection() {
    }

    public boolean isCanConnect() {
        return canConnect;
    }

    public void setCanConnect(boolean canConnect) {
        this.canConnect = canConnect;
    }

    public int getAllowedConnections() {
        return allowedConnections;
    }

    public void setAllowedConnections(int allowedConnections) {
        this.allowedConnections = allowedConnections;
    }
}
