package il.test.TestWithReact.net.controller.doc;

import il.test.TestWithReact.data.entity.db.User;
import il.test.TestWithReact.data.entity.dto.ILPage;
import il.test.TestWithReact.net.except.ExceptDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface ApiCommon {

    @Operation(summary = "Check server is available.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Server available")
    })
    ResponseEntity<Void> isAvailable();


    @Operation(summary = "Register new user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "New user created.")
    })
    ResponseEntity<Void> newUser(User user);


    @Operation(summary = "Get users with pagination and search.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the users."),
        @ApiResponse(responseCode = "400", description = "Invalid input parameters.",
            content = @Content(schema = @Schema(implementation = ExceptDetails.class))
        )
    })
    ILPage<User> getUsers(
        @Parameter(description = "Page number (default: 0)") int page,
        @Parameter(description = "Number of items per page (default: 10)") int size,
        @Parameter(description = "Field to sort by") String sortBy,
        @Parameter(description = "Is asc sorting direction [true | false], (default: true)") boolean ascDir
    );


    @Operation(summary = "Get user by ID",
        description = "Returns a user with the specified ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found",
                content = @Content(schema = @Schema(implementation = ExceptDetails.class))
            )
        }
    )
    ResponseEntity<User> getUser(Long id);
}
