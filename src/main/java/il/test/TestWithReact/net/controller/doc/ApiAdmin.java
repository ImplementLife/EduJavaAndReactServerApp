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
import org.springframework.web.bind.annotation.RequestParam;

public interface ApiAdmin {
    @Operation(summary = "Get users with pagination and search.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the users."),
        @ApiResponse(responseCode = "400", description = "Invalid input parameters.",
            content = @Content(schema = @Schema(implementation = ExceptDetails.class))
        )
    })
    ResponseEntity<ILPage<User>> getUsers(
        @Parameter(description = "Page number (default: 0)") int page,
        @Parameter(description = "Number of items per page (default: 10)") int size,
        @Parameter(description = "Field to sort by") String sortBy,
        @Parameter(description = "Is asc sorting direction [true | false], (default: true)") boolean ascDir
    );

    @Operation(summary = "Get user info")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found."),
        @ApiResponse(responseCode = "404", description = "User not found.",
            content = @Content(schema = @Schema(implementation = ExceptDetails.class))
        )
    })
    ResponseEntity<User> getUser(
        @Parameter(description = "User id") Long id
    );
}
