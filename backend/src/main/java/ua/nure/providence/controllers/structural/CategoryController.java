package ua.nure.providence.controllers.structural;

import com.mysema.commons.lang.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.daos.CardHolderDAO;
import ua.nure.providence.daos.CategoryDAO;
import ua.nure.providence.dtos.BaseListDTO;
import ua.nure.providence.dtos.structure.*;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.hierarchy.StructuralCategory;
import ua.nure.providence.utils.auth.LoginToken;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 06.05.2017.
 */
@Transactional
@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryDAO dao;

    @Autowired
    private CardHolderDAO holderDAO;

    @RequestMapping(value = "/detail/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<DetailCategoryDTO> getDetailedCategory(@PathVariable(name = "uuid") String uuid) {
        if (!dao.exists(uuid)) {
            throw new RestException(HttpStatus.NOT_FOUND, 404011, "Specified category not found");
        }

        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        StructuralCategory category = dao.getDetail(uuid, user);

        return ResponseEntity.ok(new DetailCategoryDTO().convert(category));
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable(name = "uuid") String uuid) {
        if (!dao.exists(uuid)) {
            throw new RestException(HttpStatus.NOT_FOUND, 404012, "Specified category not found");
        }

        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();

        StructuralCategory category = dao.get(uuid, user);
        return ResponseEntity.ok(new CategoryDTO().convert(category));
    }

    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseListDTO<SimpleCategoryDTO>> getCategories(@RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                        @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }

        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        Pair<List<StructuralCategory>, Long> pair = dao.getAll(user, limit, offset);
        return ResponseEntity.ok(new BaseListDTO<>(pair.getFirst().stream()
                .map(item -> new SimpleCategoryDTO().convert(item))
                .collect(Collectors.toList()), limit, offset, pair.getSecond()));
    }

    @RequestMapping(value = "/holders/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseListDTO<HolderCategoryDTO>> getCategoriesWithHolders(@RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                                   @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }

        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        Pair<List<StructuralCategory>, Long> pair = dao.getCategoriesWithHolders(user, limit, offset);
        return ResponseEntity.ok(new BaseListDTO<>(pair.getFirst().stream()
                .map(item -> new HolderCategoryDTO().convert(item))
                .collect(Collectors.toList()), limit, offset, pair.getSecond()));
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<DetailCategoryDTO> updateCategory(@PathVariable(name = "uuid") String uuid,
                                                            @RequestBody() CategoryUpdateDTO dto) {
        if (!dao.exists(uuid)) {
            throw new RestException(HttpStatus.NOT_FOUND, 404011, "Specified category not found");
        }
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        StructuralCategory category = dao.getDetail(uuid, user);

        if (category == null) {
            throw new RestException(HttpStatus.NOT_FOUND, 404011, "Specified category not found");
        }
        this.handleCategory(category, dto, user);
        dao.update(category);
        return ResponseEntity.ok(new DetailCategoryDTO().convert(category));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DetailCategoryDTO> createCategory(@RequestBody() CategoryUpdateDTO dto) {
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        StructuralCategory category = new StructuralCategory();
        this.handleCategory(category, dto, user);
        category.setAccount(user.getAccount());
        dao.insert(category);
        return ResponseEntity.ok(new DetailCategoryDTO().convert(category));
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteCategory(@PathVariable(name = "uuid") String uuid) {
        if (!dao.exists(uuid)) {
            throw new RestException(HttpStatus.NOT_FOUND, 404012, "Specified category not found");
        }

        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();

        StructuralCategory category = dao.getDetail(uuid, user);
        dao.delete(category);
        return new ResponseEntity(HttpStatus.OK);
    }

    private void handleCategory(StructuralCategory category, CategoryUpdateDTO dto, User user) {
        if (dto.getName() != null) {
            category.setName(dto.getName());
        }

        if (dto.getParent() == null) {
            category.setParent(null);
        } else if (!category.getParent().getUuid().equals(dto.getParent())) {
            StructuralCategory parent = dao.simpleGet(dto.getParent(), user);
            if (parent == null) {
                throw new RestException(HttpStatus.BAD_REQUEST, 400007, "Invalid parent is provided");
            }
        }

        if (dto.getChildren() != null) {
            category.getChildren().forEach(child -> child.setParent(null));
            dto.getChildren().forEach(child -> {
                if (dao.exists(child)) {
                    StructuralCategory childItem = dao.simpleGet(child, user);
                    childItem.setParent(category);
                } else {
                    throw new RestException(HttpStatus.BAD_REQUEST, 400008, "Invalid child is provided");
                }
            });
        }

        if (dto.getCardHolders() != null) {
            category.setCardHolders(null);
            List<CardHolder> cardHolders = new ArrayList<>();
            dto.getCardHolders().forEach(item -> {
                if (holderDAO.exists(item)) {
                    cardHolders.add(holderDAO.get(item));
                } else {
                    throw new RestException(HttpStatus.BAD_REQUEST, 400009, "Invalid holder is provided");
                }
            });
            category.setCardHolders(cardHolders);
        }
    }
}
