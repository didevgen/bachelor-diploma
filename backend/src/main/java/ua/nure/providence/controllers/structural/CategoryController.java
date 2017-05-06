package ua.nure.providence.controllers.structural;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.daos.CategoryDAO;
import ua.nure.providence.dtos.structure.CategoryDTO;
import ua.nure.providence.dtos.structure.DetailCategoryDTO;
import ua.nure.providence.dtos.structure.HolderCategoryDTO;
import ua.nure.providence.dtos.structure.SimpleCategoryDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.hierarchy.StructuralCategory;
import ua.nure.providence.utils.auth.LoginToken;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 06.05.2017.
 */
@Transactional
@RestController
@RequestMapping("category")
public class CategoryController {


    @Autowired
    private CategoryDAO dao;

    @RequestMapping(value = "/detail/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<DetailCategoryDTO> getDetailedCategory(@PathVariable(name = "uuid") String uuid) {
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();

        StructuralCategory category = dao.getDetail(uuid, user);
        if (category == null) {
            throw new RestException(HttpStatus.NOT_FOUND, 404011, "Specified category not found");
        }
        return ResponseEntity.ok(new DetailCategoryDTO().convert(category));
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable(name = "uuid") String uuid) {
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();

        StructuralCategory category = dao.get(uuid, user);
        if (category == null) {
            throw new RestException(HttpStatus.NOT_FOUND, 404012, "Specified category not found");
        }
        return ResponseEntity.ok(new CategoryDTO().convert(category));
    }

    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<SimpleCategoryDTO>> getCategories(@RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                 @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();

        List<StructuralCategory> categories = dao.getAll(user, limit, offset);
        return ResponseEntity.ok(categories.stream()
                .map(item -> new SimpleCategoryDTO().convert(item))
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/holders/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<HolderCategoryDTO>> getCategoriesWithHolders(@RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                            @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();

        List<StructuralCategory> categories = dao.getCategoriesWithHolders(user, limit, offset);
        return ResponseEntity.ok(categories.stream()
                .map(item -> new HolderCategoryDTO().convert(item))
                .collect(Collectors.toList()));
    }
}
