package dozun.game.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String fullName;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Date createAt = new Date();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<GameDetailEntity> gameDetails;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "game_detail",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<GameEntity> games = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "Users_Id"),
            inverseJoinColumns = @JoinColumn(name = "Roles_Id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;

    @Column
    private  Boolean status;

    @Column
    private  Boolean isVerify;

    public UserEntity(String username, String email, String password, Date createAt, WalletEntity wallet) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
        this.wallet = wallet;
    }

    public UserEntity(String username, String email, String password, Date createAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
    }

    public UserEntity(String fullName, String username, String email, String password, Set<RoleEntity> roles, WalletEntity wallet, Boolean status) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.wallet = wallet;
        this.status = status;
    }

    public UserEntity(String fullName, String username, String email, String password, Boolean status, Boolean isVerify) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = status;
        this.isVerify = isVerify;
    }

    public UserEntity(String fullName, String username, String email, String password, Boolean status, Boolean isVerify, WalletEntity wallet) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = status;
        this.isVerify = isVerify;
        this.wallet = wallet;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.stream().forEach(i ->authorities.add(new SimpleGrantedAuthority(i.getName())));
        return List.of(new SimpleGrantedAuthority(authorities.toString()));
    }

    public UserEntity(UUID id, String fullName, String username, String email, String password, Set<RoleEntity> roles) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserEntity user = (UserEntity) obj;
        return Objects.equals(id, user.getId()) &&
                Objects.equals(username, user.getUsername());
    }
}
