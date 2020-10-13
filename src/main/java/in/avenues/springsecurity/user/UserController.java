package in.avenues.springsecurity.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String getTest() {
        return "Sanity";
    }

    @PostMapping
    public ResponseEntity<Boolean> createUser(@RequestBody UserDTO payload) {
        Boolean value = userService.save(payload);
        return ResponseEntity.ok(value);
    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        UserDTO userDTO = userService.getById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("by-username/{username}")
    public ResponseEntity<UserDTO> getById(@PathVariable String username) {
        UserDTO userDTO = userService.getByUserName(username);
        return ResponseEntity.ok(userDTO);
    }


}
