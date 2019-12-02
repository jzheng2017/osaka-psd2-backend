package API.DTO.ING;

public class ClearingSystemMemberIdentification {
    private String clearingSystemMemberIdentificationCode;
    private String memberIdentification;

    public ClearingSystemMemberIdentification(String clearingSystemMemberIdentificationCode, String memberIdentification) {
        this.clearingSystemMemberIdentificationCode = clearingSystemMemberIdentificationCode;
        this.memberIdentification = memberIdentification;
    }

    public ClearingSystemMemberIdentification() {
    }

    public String getClearingSystemMemberIdentificationCode() {
        return clearingSystemMemberIdentificationCode;
    }

    public void setClearingSystemMemberIdentificationCode(String clearingSystemMemberIdentificationCode) {
        this.clearingSystemMemberIdentificationCode = clearingSystemMemberIdentificationCode;
    }

    public String getMemberIdentification() {
        return memberIdentification;
    }

    public void setMemberIdentification(String memberIdentification) {
        this.memberIdentification = memberIdentification;
    }
}
