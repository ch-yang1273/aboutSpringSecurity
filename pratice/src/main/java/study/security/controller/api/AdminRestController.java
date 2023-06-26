package study.security.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.security.dto.AddResourceReq;
import study.security.dto.AddRoleHierarchyReq;
import study.security.dto.AddRoleReq;
import study.security.service.AuthorizationResourceService;
import study.security.service.MemberRoleHierarchyService;
import study.security.service.MemberRoleService;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminRestController {

    private final MemberRoleService memberRoleService;
    private final AuthorizationResourceService authorizationResourceService;
    private final MemberRoleHierarchyService memberRoleHierarchyService;

    @PostMapping("/role/add")
    public void addRole(@RequestBody AddRoleReq request) {
        memberRoleService.addRole(request.getRoleName(), request.getDescription());
    }

    @PostMapping("/role/hierarchy")
    public void addRoleHierarchy(@RequestBody AddRoleHierarchyReq request) {
        memberRoleHierarchyService.createMemberRoleHierarchy(request.getChildName(), request.getParentName());
    }

    @PostMapping("/resource/add")
    public void addResource(@RequestBody AddResourceReq request) {
        authorizationResourceService.addResource(request);
    }
}
