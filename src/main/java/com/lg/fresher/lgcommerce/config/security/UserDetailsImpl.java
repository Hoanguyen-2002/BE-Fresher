package com.lg.fresher.lgcommerce.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lg.fresher.lgcommerce.constant.AccountStatus;
import com.lg.fresher.lgcommerce.entity.account.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : UserDetailsImpl
 * @ Description : lg_ecommerce_be UserDetailsImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/5/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/5/2024       63200502      first creation */
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private String userId;

    private String username;

    @Getter
    private String email;

    @JsonIgnore
    private String password;
    private String accountStatus;

    private Collection<? extends GrantedAuthority> userRole;

    /**
     *
     * @ Description : lg_ecommerce_be UserDetailsImpl constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be UserDetailsImpl Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024          63200502    first creation
     *<pre>
     */
    public UserDetailsImpl(String userId, String username, String email, String accountStatus, Collection<? extends GrantedAuthority> userRole) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.userRole = userRole;
        this.accountStatus = accountStatus;
    }

    /**
     *
     * @ Description : lg_ecommerce_be UserDetailsImpl constructor
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024         63200502    first creation
     *<pre>

     field:

     * @ Description : lg_ecommerce_be UserDetailsImpl Member Field
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024          63200502    first creation
     *<pre>
     */
    public UserDetailsImpl(String userId, String username, String email, String password, String accountStatus,
                           Collection<? extends GrantedAuthority> userRole) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.accountStatus = accountStatus;
    }

    /**
     *
     * @ Description : lg_ecommerce_be UserDetailsImpl Member Field build
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     *<pre>
     * @param user
     * @return  UserDetailsImpl
     */
    public static UserDetailsImpl build(Account user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new UserDetailsImpl(
                user.getAccountId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus().toString(),
                authorities
        );
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole;
    }

    /**
     *
     * @return
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return !accountStatus.equals(AccountStatus.BANNED.toString());
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return accountStatus.equals(AccountStatus.ACTIVE.toString());
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(userId, user.userId);
    }
}
