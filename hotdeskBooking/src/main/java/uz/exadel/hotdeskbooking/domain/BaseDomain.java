package uz.exadel.hotdeskbooking.domain;


import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseDomain implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    @NotNull
    @Column(nullable = false)
    private boolean deleted;

    @NotNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private Date createdDate;

    @NotNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private Date modifiedDate;

    protected BaseDomain() {
        this.deleted = false;
    }

    @PreUpdate
    public void preUpdate() {
        this.setModifiedDate(new Date(System.currentTimeMillis()));
    }

    @PrePersist
    public void prePersist() {
        if (this.isNew() && this.getCreatedDate() == null) {
            this.setCreatedDate(new Date(System.currentTimeMillis()));
        }
        this.setModifiedDate(new Date(System.currentTimeMillis()));
    }

    public Date getCreatedDate() {
        return createdDate != null ? new Date(createdDate.getTime()) : null;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate != null ? new Date(createdDate.getTime()) : null;
    }

    public Date getModifiedDate() {
        return modifiedDate != null ? new Date(modifiedDate.getTime()) : null;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate != null ? new Date(modifiedDate.getTime()) : null;
    }


    @Override
    public int hashCode() {
        int result = this.getId() != null ? this.getId().hashCode() : 0;
        result = 31 * result + this.getEntityKey().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "[Name: " + this.getEntityKey() + ", id: " + getId() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseDomain unObject)) {
            return false;
        }
        if (getId() == null) {
            return false;
        }

        String table1 = getEntityName(this);
        String table2 = getEntityName(unObject);
        return !(table1 == null || !table1.equals(table2)) && getId().equals(unObject.getId());
    }

    private String getEntityName(BaseDomain o) {
        if (o instanceof HibernateProxy proxy) {
            return proxy.getHibernateLazyInitializer().getEntityName();
        }
        Entity entity = o.getClass().getAnnotation(Entity.class);
        if (entity != null) {
            return !"".equals(entity.name()) ? entity.name() : o.getClass().getName();
        }
        return "";
    }

    public abstract String getEntityKey();

    public boolean isNew() {
        return this.getId() == null;
    }

}
