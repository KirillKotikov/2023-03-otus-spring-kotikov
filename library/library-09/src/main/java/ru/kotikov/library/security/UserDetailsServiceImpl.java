package ru.kotikov.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kotikov.library.models.User;
import ru.kotikov.library.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails loadedUser;

        try {
            User user = userRepository.findByUsername(username);
            loadedUser = new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), user.getAuthorities());
        } catch (Exception exception) {
            throw new
                    InternalAuthenticationServiceException("Пользователь с таким именем не найден!",
                    exception);
        }
        return loadedUser;
    }

}
