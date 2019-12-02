package API.DTO.ING;

import API.DTO.Account;
import API.DTO.BalanceAmount;

public class PaymentRequestING {
    private String endToEndIdentification;
    private Account debtorAccount;
    private BalanceAmount instructedAmount;
    private Account creditorAccount;
    private String creditorAgent;
    private String creditorName;
    private CreditorAddress creditorAddress;
    private String chargeBearer;
    private String remittanceInformationUnstructured;
    private ClearingSystemMemberIdentification clearingSystemMemberIdentification;
    private String debtorName;
    private String debtorAgent;
    private String instructionPriority;
    private String serviceLevelCode;
    private String localInstrumentCode;
    private String categoryPurposeCode;
    private String requestedExecutionDate;

    public PaymentRequestING(String endToEndIdentification, Account debtorAccount, BalanceAmount instructedAmount, Account creditorAccount, String creditorAgent, String creditorName, CreditorAddress creditorAddress, String chargeBearer, String remittanceInformationUnstructured, ClearingSystemMemberIdentification clearingSystemMemberIdentification, String debtorName, String debtorAgent, String instructionPriority, String serviceLevelCode, String localInstrumentCode, String categoryPurposeCode, String requestedExecutionDate) {
        this.endToEndIdentification = endToEndIdentification;
        this.debtorAccount = debtorAccount;
        this.instructedAmount = instructedAmount;
        this.creditorAccount = creditorAccount;
        this.creditorAgent = creditorAgent;
        this.creditorName = creditorName;
        this.creditorAddress = creditorAddress;
        this.chargeBearer = chargeBearer;
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
        this.clearingSystemMemberIdentification = clearingSystemMemberIdentification;
        this.debtorName = debtorName;
        this.debtorAgent = debtorAgent;
        this.instructionPriority = instructionPriority;
        this.serviceLevelCode = serviceLevelCode;
        this.localInstrumentCode = localInstrumentCode;
        this.categoryPurposeCode = categoryPurposeCode;
        this.requestedExecutionDate = requestedExecutionDate;
    }

    public PaymentRequestING() {
    }

    public String getEndToEndIdentification() {
        return endToEndIdentification;
    }

    public void setEndToEndIdentification(String endToEndIdentification) {
        this.endToEndIdentification = endToEndIdentification;
    }

    public Account getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(Account debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public BalanceAmount getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(BalanceAmount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public Account getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(Account creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public String getCreditorAgent() {
        return creditorAgent;
    }

    public void setCreditorAgent(String creditorAgent) {
        this.creditorAgent = creditorAgent;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public CreditorAddress getCreditorAddress() {
        return creditorAddress;
    }

    public void setCreditorAddress(CreditorAddress creditorAddress) {
        this.creditorAddress = creditorAddress;
    }

    public String getChargeBearer() {
        return chargeBearer;
    }

    public void setChargeBearer(String chargeBearer) {
        this.chargeBearer = chargeBearer;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public ClearingSystemMemberIdentification getClearingSystemMemberIdentification() {
        return clearingSystemMemberIdentification;
    }

    public void setClearingSystemMemberIdentification(ClearingSystemMemberIdentification clearingSystemMemberIdentification) {
        this.clearingSystemMemberIdentification = clearingSystemMemberIdentification;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getDebtorAgent() {
        return debtorAgent;
    }

    public void setDebtorAgent(String debtorAgent) {
        this.debtorAgent = debtorAgent;
    }

    public String getInstructionPriority() {
        return instructionPriority;
    }

    public void setInstructionPriority(String instructionPriority) {
        this.instructionPriority = instructionPriority;
    }

    public String getServiceLevelCode() {
        return serviceLevelCode;
    }

    public void setServiceLevelCode(String serviceLevelCode) {
        this.serviceLevelCode = serviceLevelCode;
    }

    public String getLocalInstrumentCode() {
        return localInstrumentCode;
    }

    public void setLocalInstrumentCode(String localInstrumentCode) {
        this.localInstrumentCode = localInstrumentCode;
    }

    public String getCategoryPurposeCode() {
        return categoryPurposeCode;
    }

    public void setCategoryPurposeCode(String categoryPurposeCode) {
        this.categoryPurposeCode = categoryPurposeCode;
    }

    public String getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public void setRequestedExecutionDate(String requestedExecutionDate) {
        this.requestedExecutionDate = requestedExecutionDate;
    }
}
