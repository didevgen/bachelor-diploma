package ua.nure.providence.models.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import ua.nure.providence.models.annotations.CloneIgnoreField;

import javax.persistence.*;
import java.io.Serializable;

/**
    Designates a class whose mapping information is applied to the
    entities that inherit from it. A mapped superclass has no separate table defined for it.
*/
@MappedSuperclass
public abstract class BaseEntity extends UUIDEntity implements IIndexed, ITransferable {

    @Id
    @Column(name="id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
