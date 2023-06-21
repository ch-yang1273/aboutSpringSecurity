package study.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.security.domain.AuthorizationResourceRepository;
import study.security.dto.ResourceResponse;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorizationResourceService {

    private final AuthorizationResourceRepository authorizationResourceRepository;

    @Transactional(readOnly = true)
    public List<ResourceResponse> getResource() {
        return authorizationResourceRepository.findAll().stream()
                .map(ResourceResponse::of)
                .collect(Collectors.toList());
    }
}
