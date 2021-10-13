package trvoid.bloomfilter;

public class Reject {
    private String fromPhoneNo;
    private String toPhoneNo;

    public Reject(String fromPhoneNo, String toPhoneNo) {
        this.fromPhoneNo = fromPhoneNo;
        this.toPhoneNo = toPhoneNo;
    }

    public String getFromPhoneNo() {
        return fromPhoneNo;
    }

    public void setFromPhoneNo(String fromPhoneNo) {
        this.fromPhoneNo = fromPhoneNo;
    }

    public String getToPhoneNo() {
        return toPhoneNo;
    }

    public void setToPhoneNo(String toPhoneNo) {
        this.toPhoneNo = toPhoneNo;
    }

    @Override
    public String toString() {
        return String.format("Reject[fromPhoneNo:%s, toPhoneNo:%s]", fromPhoneNo, toPhoneNo);
    }
}
