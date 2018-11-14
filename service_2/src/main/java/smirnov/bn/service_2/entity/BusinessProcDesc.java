package smirnov.bn.service_2.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "business_proc_desc")
public class BusinessProcDesc {
    @Id
    @Column(name = "biz_proc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bizProcId;

    @Column(name = "biz_proc_name", length = 255)
    private String bizProcName;

    @Column(name = "biz_proc_desc_str", length = 4000)
    private String bizProcDescStr;

    public Integer getBizProcId() {
        return bizProcId;
    }

    public void setBizProcId(Integer bizProcId) {
        this.bizProcId = bizProcId;
    }

    public String getBizProcName() {
        return bizProcName;
    }

    public void setBizProcName(String bizProcName) {
        this.bizProcName = bizProcName;
    }

    public String getBizProcDescStr() {
        return bizProcDescStr;
    }

    public void setBizProcDescStr(String bizProcDescStr) {
        this.bizProcDescStr = bizProcDescStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessProcDesc that = (BusinessProcDesc) o;
        return Objects.equals(bizProcId, that.bizProcId) &&
                Objects.equals(bizProcName, that.bizProcName) &&
                Objects.equals(bizProcDescStr, that.bizProcDescStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bizProcId, bizProcName, bizProcDescStr);
    }

    @Override
    public String toString() {
        return "BizProcDesc{" +
                "bizProcId=" + bizProcId +
                ", bizProcName='" + bizProcName + '\'' +
                ", bizProcDescStr='" + bizProcDescStr + '\'' +
                '}';
    }
}
