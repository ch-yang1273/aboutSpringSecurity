package study.security.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.security.dto.AddResourceRequest;
import study.security.dto.AddRoleRequest;
import study.security.service.AuthorizationResourceService;
import study.security.service.MemberRoleService;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminRestController {

    private final MemberRoleService memberRoleService;
    private final AuthorizationResourceService authorizationResourceService;

    @PostMapping("/role/add")
    public void addRole(@RequestBody AddRoleRequest request) {
        memberRoleService.addRole(request.getRoleName(), request.getDescription());
    }

    @PostMapping("/resource/add")
    public void addResource(@RequestBody AddResourceRequest request) {
        authorizationResourceService.addResource(request);
    }
}
