package il.test.TestWithReact.net.controller;

import com.fasterxml.jackson.annotation.JsonView;
import il.test.TestWithReact.data.entity.dto.ILPage;
import il.test.TestWithReact.data.entity.dto.ViewLevel;
import il.test.TestWithReact.data.entity.db.User;
import il.test.TestWithReact.data.repo.UserRepo;
import il.test.TestWithReact.net.controller.doc.ApiCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiCommonController implements ApiCommon {
    @Autowired
    private UserRepo userRepo;

    public Long getAuthUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

    @GetMapping("/isAvailable")
    public ResponseEntity<Void> isAvailable() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/self")
    public ResponseEntity<User> selfInfo() {
        Optional<User> byId = userRepo.findById(getAuthUserID());
        return ResponseEntity.ok(byId.orElseThrow(() -> new IllegalStateException("Not Found")));
    }

    @GetMapping("/user")
    @JsonView({ViewLevel.Protected.class})
    public ResponseEntity<User> getUser(@RequestParam Long id) {
        log.info("Request: /user");
        Optional<User> byId = userRepo.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/user")
    public ResponseEntity<Void> newUser(@Validated @RequestBody User user) {
        userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/user")
    public ResponseEntity<Void> updateUser(@Validated @RequestBody User user) {
        if (!getAuthUserID().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userRepo.save(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    @GetMapping("/users")
    @JsonView({ViewLevel.Public.class})
    public ILPage<User> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String sortBy,
        @RequestParam(defaultValue = "true") boolean ascDir
    ) {
        log.info("Request: /users page:{} size:{} sortBy:{} ascDir:{}", page, size, sortBy, ascDir);
        Pageable pageable;
        Sort.Direction sortDirection = ascDir ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (sortBy != null && !sortBy.isEmpty()) {
            pageable = PageRequest.of(page, size, sortDirection, sortBy);
        } else {
            pageable = PageRequest.of(page, size, sortDirection, "id");
        }

        Page<User> resultPage = userRepo.findAll(pageable);
        return new ILPage<>(resultPage);
    }
}
