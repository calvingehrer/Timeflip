package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing Department
 */
@Entity
public class Department implements Persistable<String>, Serializable {

    private static final long serialVersionDID = 1L;

    @Id
    @Column(length = 100)
    private String departmentName;

    private Date createDate;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public static long getSerialVersionDID() {
        return serialVersionDID;
    }



    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return this.getDepartmentName();
    }

    @Override
    public String getId() {
        return getDepartmentName();
    }


    @Override
    public boolean isNew() {
        return null == createDate;
    }

}
