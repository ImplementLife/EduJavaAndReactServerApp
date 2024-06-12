package il.test.TestWithReact.net.controller;

import com.fasterxml.jackson.annotation.JsonView;
import il.test.TestWithReact.data.entity.dto.ILPage;
import il.test.TestWithReact.data.entity.dto.ViewLevel;
import il.test.TestWithReact.data.entity.db.User;
import il.test.TestWithReact.data.repo.UserRepo;
import il.test.TestWithReact.net.controller.doc.ApiAdmin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class ApiAdminController implements ApiAdmin {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    @JsonView({ViewLevel.Admin.class})
    public ResponseEntity<ILPage<User>> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String sortBy,
        @RequestParam(defaultValue = "true") boolean ascDir
    ) {
        log.info("Request: api/admin/users page:{} size:{} sortBy:{} ascDir:{}", page, size, sortBy, ascDir);
        Pageable pageable;
        if (sortBy != null && !sortBy.isEmpty()) {
            Sort.Direction sortDirection = ascDir ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(page, size, sortDirection, sortBy);
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<User> all = userRepo.findAll(pageable);
        ILPage<User> userILPage = new ILPage<>(all);
        return ResponseEntity.ok(userILPage);
    }

    @GetMapping("/user")
    @JsonView({ViewLevel.Admin.class})
    public ResponseEntity<User> getUser(@RequestParam Long id) {
        log.info("Request: api/admin/user");
        Optional<User> byId = userRepo.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
