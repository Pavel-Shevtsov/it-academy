package org.web.authentication;

import org.example.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.web.forms.UserForm;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyDBAuthenticationService implements UserDetailsService {
    @Autowired
    UserJpaRepository userJpaRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        UserForm account = new UserForm(userJpaRepository.findByUserName(username)) ;
        if(account.getUsername()==null){
            throw new UsernameNotFoundException("User: "+ username + " is null");
        }
        List<GrantedAuthority> grantList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+account.getRole());

        grantList.add(authority);

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        UserDetails userDetails = (UserDetails) new User(account.getUsername(), account.getPassword(), enabled,
                accountNonExpired,credentialsNonExpired,accountNonLocked,grantList);

        return userDetails;
    }
}
