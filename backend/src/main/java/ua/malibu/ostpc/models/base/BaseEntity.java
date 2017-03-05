package ua.malibu.ostpc.models.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import ua.malibu.ostpc.models.annotations.CloneIgnoreField;

import javax.persistence.*;
import java.io.Serializable;

/**
    Designates a class whose mapping information is applied to the
    entities that inherit from it. A mapped superclass has no separate table defined for it.
*/
@MappedSuperclass
public abstract class BaseEntity extends UUIDEntity implements IIndexed, Serializable, ITransferable {

    @CloneIgnoreField
    private static final long serialVersionUID = 2855346562216652344L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final BaseEntity other = (BaseEntity) obj;
        return Objects.equal(this.getUuid(), other.getUuid())
                && Objects.equal(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId(), this.getUuid());
    }
}
