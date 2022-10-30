package com.gameapi.Controllers;

import com.gameapi.Configs.AuthentificationManager;
import com.gameapi.DTOs.RequestDTO.AuthenticationRequest;
import com.gameapi.DTOs.RequestDTO.SubmittedCodeDTO;
import com.gameapi.DTOs.ResponseDTO.SubmittedCodeResponseDTO;
import com.gameapi.Models.SubmittedCode;
import com.gameapi.Models.User;
import com.gameapi.Repositories.UserRepository;
import com.gameapi.Services.CodeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/code")
public class CodeController {

    private final CodeService _codeService;
    private final ModelMapper _mapper;

    @PostMapping("testProblem")
    @PreAuthorize("hasAnyAuthority('PROFESOR')")
    public Mono<SubmittedCodeResponseDTO> testProblem(@RequestBody SubmittedCodeDTO dto) {
        return _codeService.create(new SubmittedCode(dto.getCreatedBy(), dto.getCode(), dto.getTestCode())).map(code -> _mapper.map(code, SubmittedCodeResponseDTO.class));
    }

}
