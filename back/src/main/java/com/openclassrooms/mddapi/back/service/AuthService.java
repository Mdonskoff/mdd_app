package com.openclassrooms.mddapi.back.service;

import com.openclassrooms.mddapi.back.dto.LogInDto;
import com.openclassrooms.mddapi.back.dto.RegisterDto;
import com.openclassrooms.mddapi.back.dto.UsersDto;
import com.openclassrooms.mddapi.back.model.Users;
import com.openclassrooms.mddapi.back.repository.UsersRepository;
import com.openclassrooms.mddapi.back.utils.functionsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Slf4j
@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private functionsUtils funUtils;

    /**
     * Creates a new user based on the provided RegisterDto.
     *
     * @param registerDto Data transfer object containing user registration details.
     * @return true if the user is successfully created, false otherwise.
     */
    public Boolean createUser(RegisterDto registerDto) {
        if(registerDto.getEmail() == null || registerDto.getPassword() == null || registerDto.getPseudo() == null) {
            log.error("Check users informations");
            return false;
        }

        Optional<Users> checkUser = usersRepository.findByEmail(registerDto.getEmail());
        if(checkUser.isPresent()) {
            log.error("User already exist");
            return false;
        }
        checkUser = usersRepository.findByPseudo(registerDto.getPseudo());
        if(checkUser.isPresent()) {
            log.error("User already exist");
            return false;
        }
        String password = funUtils.checkPassword(registerDto.getPassword());
        if(password == null) {
            return false;
        }
        Users user = new Users();
        user.setEmail(registerDto.getEmail());
        user.setPseudo(registerDto.getPseudo());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setTopicsList(new ArrayList<>());
        usersRepository.save(user);

        return true;
    }

    /**
     * Logs in a user based on the provided LogInDto.
     *
     * @param logInDto Data transfer object containing login details.
     * @return JWT token if login is successful, otherwise an error message.
     */
    public String logIn(LogInDto logInDto) {
        if(logInDto.getId() == null || logInDto.getPassword() == null) {
            return "login/password incorrect";
        }
        try {
            Optional<Users> user = usersRepository.findByEmail(logInDto.getId());
            if (user.isEmpty()) {
                user = Optional.of(usersRepository.findByPseudo(logInDto.getId()).orElseThrow());
                logInDto.setId(user.get().getEmail());
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            logInDto.getId(),
                            logInDto.getPassword()
                    )
            );
           return jwtService.generateToken(user.get());
        }catch(Exception ex) {
            log.error("erreur de connexion " + ex);
            return "";
        }

    }

    /**
     * Retrieves information about the currently logged-in user.
     *
     * @return UsersDto object containing information about the logged-in user,
     *         or null if no user is found with the authenticated email.
     */
    public UsersDto getMyInfo(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> user = usersRepository.findByEmail(email);
        if(user.isEmpty()) {
            return null;
        }
        UsersDto usersDto = new UsersDto();
        usersDto.setId(user.get().getId());
        usersDto.setPseudo(user.get().getPseudo());
        usersDto.setEmail(user.get().getEmail());
        usersDto.setCreated_at(user.get().getCreated_at());
        usersDto.setUpdated_at(user.get().getUpdated_at());
        usersDto.setTopicsList(user.get().getTopicsList());

        return usersDto;
    }

    /**
     * Clears the SecurityContext to remove authentication and authorization information..
     *
     */
    public void logout() {
        SecurityContextHolder.clearContext();
    }

}
