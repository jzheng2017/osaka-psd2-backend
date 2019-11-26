package API.DTO.RABO;

import API.DTO.TransactionAmount;

import java.util.ArrayList;

public class RaboBooking {
    private String bookingDate;
    private String iban;
    private String currency;
    private RaboAccount creditorAccount;
    private RaboAccount debtorAccount;
    private String creditorName;
    private String raboTransactionTypeName;
    private String raboBookingDateTime;
    private String bankTransactionCode;
    private String checkId;
    private String creditorAgent;
    private String creditorId;
    private String debtorAgent;
    private String debtorName;
    private String endToEndId;
    private int entryReference;
    private ArrayList<ExchangeRate> exchangeRate;
    private String initiatingPartyName;
    private InstructedAmount instructedAmount;
    private String mandateId;
    private int numberOfTransactions;
    private String paymentInformationIdentification;
    private String proprietaryBankTransactionCode;
    private String purposeCode;
    private String purposeProprietary;
    private String raboDetailedTransactionType;
    private String raboTransactionTypename;
    private String reasonCode;
    private String remittanceInformationStructured;
    private String getRemittanceInformationUnstructured;
    private TransactionAmount transactionAmount;
    private String ultimateCreditor;
    private String ultimateDebtor;
    private String valueDate;

    public RaboBooking(String bookingDate, String iban, String currency, RaboAccount creditorAccount, RaboAccount debtorAccount, String creditorName, String raboTransactionTypeName, String raboBookingDateTime, String bankTransactionCode, String checkId, String creditorAgent, String creditorId, String debtorAgent, String debtorName, String endToEndId, int entryReference, ArrayList<ExchangeRate> exchangeRate, String initiatingPartyName, InstructedAmount instructedAmount, String mandateId, int numberOfTransactions, String paymentInformationIdentification, String proprietaryBankTransactionCode, String purposeCode, String purposeProprietary, String raboDetailedTransactionType, String raboTransactionTypename, String reasonCode, String remittanceInformationStructured, String getRemittanceInformationUnstructured, TransactionAmount transactionAmount, String ultimateCreditor, String ultimateDebtor, String valueDate) {
        this.bookingDate = bookingDate;
        this.iban = iban;
        this.currency = currency;
        this.creditorAccount = creditorAccount;
        this.debtorAccount = debtorAccount;
        this.creditorName = creditorName;
        this.raboTransactionTypeName = raboTransactionTypeName;
        this.raboBookingDateTime = raboBookingDateTime;
        this.bankTransactionCode = bankTransactionCode;
        this.checkId = checkId;
        this.creditorAgent = creditorAgent;
        this.creditorId = creditorId;
        this.debtorAgent = debtorAgent;
        this.debtorName = debtorName;
        this.endToEndId = endToEndId;
        this.entryReference = entryReference;
        this.exchangeRate = exchangeRate;
        this.initiatingPartyName = initiatingPartyName;
        this.instructedAmount = instructedAmount;
        this.mandateId = mandateId;
        this.numberOfTransactions = numberOfTransactions;
        this.paymentInformationIdentification = paymentInformationIdentification;
        this.proprietaryBankTransactionCode = proprietaryBankTransactionCode;
        this.purposeCode = purposeCode;
        this.purposeProprietary = purposeProprietary;
        this.raboDetailedTransactionType = raboDetailedTransactionType;
        this.raboTransactionTypename = raboTransactionTypename;
        this.reasonCode = reasonCode;
        this.remittanceInformationStructured = remittanceInformationStructured;
        this.getRemittanceInformationUnstructured = getRemittanceInformationUnstructured;
        this.transactionAmount = transactionAmount;
        this.ultimateCreditor = ultimateCreditor;
        this.ultimateDebtor = ultimateDebtor;
        this.valueDate = valueDate;
    }

    public RaboBooking() {
    }

    public RaboAccount getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(RaboAccount debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public String getBankTransactionCode() {
        return bankTransactionCode;
    }

    public void setBankTransactionCode(String bankTransactionCode) {
        this.bankTransactionCode = bankTransactionCode;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCreditorAgent() {
        return creditorAgent;
    }

    public void setCreditorAgent(String creditorAgent) {
        this.creditorAgent = creditorAgent;
    }

    public String getCreditorId() {
        return creditorId;
    }

    public void setCreditorId(String creditorId) {
        this.creditorId = creditorId;
    }

    public String getDebtorAgent() {
        return debtorAgent;
    }

    public void setDebtorAgent(String debtorAgent) {
        this.debtorAgent = debtorAgent;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }

    public int getEntryReference() {
        return entryReference;
    }

    public void setEntryReference(int entryReference) {
        this.entryReference = entryReference;
    }

    public ArrayList<ExchangeRate> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ArrayList<ExchangeRate> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getInitiatingPartyName() {
        return initiatingPartyName;
    }

    public void setInitiatingPartyName(String initiatingPartyName) {
        this.initiatingPartyName = initiatingPartyName;
    }

    public InstructedAmount getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(InstructedAmount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getMandateId() {
        return mandateId;
    }

    public void setMandateId(String mandateId) {
        this.mandateId = mandateId;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public String getPaymentInformationIdentification() {
        return paymentInformationIdentification;
    }

    public void setPaymentInformationIdentification(String paymentInformationIdentification) {
        this.paymentInformationIdentification = paymentInformationIdentification;
    }

    public String getProprietaryBankTransactionCode() {
        return proprietaryBankTransactionCode;
    }

    public void setProprietaryBankTransactionCode(String proprietaryBankTransactionCode) {
        this.proprietaryBankTransactionCode = proprietaryBankTransactionCode;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getPurposeProprietary() {
        return purposeProprietary;
    }

    public void setPurposeProprietary(String purposeProprietary) {
        this.purposeProprietary = purposeProprietary;
    }

    public String getRaboDetailedTransactionType() {
        return raboDetailedTransactionType;
    }

    public void setRaboDetailedTransactionType(String raboDetailedTransactionType) {
        this.raboDetailedTransactionType = raboDetailedTransactionType;
    }

    public String getRaboTransactionTypename() {
        return raboTransactionTypename;
    }

    public void setRaboTransactionTypename(String raboTransactionTypename) {
        this.raboTransactionTypename = raboTransactionTypename;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getRemittanceInformationStructured() {
        return remittanceInformationStructured;
    }

    public void setRemittanceInformationStructured(String remittanceInformationStructured) {
        this.remittanceInformationStructured = remittanceInformationStructured;
    }

    public String getGetRemittanceInformationUnstructured() {
        return getRemittanceInformationUnstructured;
    }

    public void setGetRemittanceInformationUnstructured(String getRemittanceInformationUnstructured) {
        this.getRemittanceInformationUnstructured = getRemittanceInformationUnstructured;
    }

    public TransactionAmount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(TransactionAmount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getUltimateCreditor() {
        return ultimateCreditor;
    }

    public void setUltimateCreditor(String ultimateCreditor) {
        this.ultimateCreditor = ultimateCreditor;
    }

    public String getUltimateDebtor() {
        return ultimateDebtor;
    }

    public void setUltimateDebtor(String ultimateDebtor) {
        this.ultimateDebtor = ultimateDebtor;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getRaboTransactionTypeName() {
        return raboTransactionTypeName;
    }

    public void setRaboTransactionTypeName(String raboTransactionTypeName) {
        this.raboTransactionTypeName = raboTransactionTypeName;
    }

    public String getRaboBookingDateTime() {
        return raboBookingDateTime;
    }

    public void setRaboBookingDateTime(String raboBookingDateTime) {
        this.raboBookingDateTime = raboBookingDateTime;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public RaboAccount getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(RaboAccount creditorAccount) {
        this.creditorAccount = creditorAccount;
    }
}
