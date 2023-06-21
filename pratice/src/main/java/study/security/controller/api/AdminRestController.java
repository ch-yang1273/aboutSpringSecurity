package study.security.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.security.dto.AddRoleRequest;
import study.security.service.AdminService;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminRestController {

    private final AdminService adminService;

    @PostMapping("/authorization/add")
    public void addAuthorization(@RequestBody AddRoleRequest request) {
        adminService.addRole(request.getRoleName(), request.getDescription());
    }
}
