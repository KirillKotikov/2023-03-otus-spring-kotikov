package ru.kotikov.blog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kotikov.blog.models.User;
import ru.kotikov.blog.repositories.UserRepository;

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
                    user.getLogin(), String.valueOf(user.getPassword()),
                    user.getAuthorities());
        } catch (Exception exception) {
            throw new
                    InternalAuthenticationServiceException("Пользователь с таким логином не найден!",
                    exception);
        }
        return loadedUser;
    }

}
