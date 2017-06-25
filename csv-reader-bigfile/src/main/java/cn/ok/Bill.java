package cn.ok;

/**
 * PROJECT: java-demo
 * PACKAGE: cn.ok
 * Created by Kyou on 2017/6/25.
 */
public class Bill {
    private String id;
    private String billNo;
    private Double price;
    private String hsCode;
    private Double weight;
    private String createDate;

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Double getPrice() {
        return price;
    }

    void setPrice(Double price) {
        this.price = price;
    }

    public String getHsCode() {
        return hsCode;
    }

    void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public Double getWeight() {
        return weight;
    }

    void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCreateDate() {
        return createDate;
    }

    void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", billNo='" + billNo + '\'' +
                ", price=" + price +
                ", hsCode='" + hsCode + '\'' +
                ", weight=" + weight +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}

