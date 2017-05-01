package ua.nure.providence.daos;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.hierarchy.StructuralCategory;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Repository("categoryDao")
@Transactional
public class CategoryDAO extends BaseDAO<StructuralCategory> {

    @Override
    public StructuralCategory get(String uuid) {
        return null;
    }
}
