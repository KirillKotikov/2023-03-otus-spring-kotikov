package ru.kotikov.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kotikov.library.models.User;
import ru.kotikov.library.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UserDetails loadedUser;

        try {
            User user = userRepository.findByLogin(login);
            loadedUser = new org.springframework.security.core.userdetails.User(
                    user.getLogin(), user.getPassword(),
                    List.of());
        } catch (Exception exception) {
            throw new
                    InternalAuthenticationServiceException("Пользователь с таким логином не найден!",
                    exception);
        }
        return loadedUser;
    }

}
