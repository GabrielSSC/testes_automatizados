package br.edu.ada.catalogo.security;

import br.edu.ada.catalogo.security.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository repo) {
        return username -> repo.findByUsername(username)
                .map(u -> User.withUsername(u.getUsername()).password(u.getSenhaHash())
                        .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(u.getRoles())).build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    // API REST: HTTP Basic sem sessão. CSRF desativado SOMENTE nesta cadeia stateless.
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**", "/products").csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.GET, "/api/produtos", "/products", "/api/info")
                                .permitAll().anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults()).formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }

    // Navegador: login com sessão e CSRF ATIVO para formulários HTML.
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/css/**", "/error", "/favicon.ico")
                .permitAll().requestMatchers(HttpMethod.GET, "/", "/produtos").permitAll()
                .requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
                .requestMatchers("/actuator/health/**", "/actuator/info", "/actuator/prometheus").permitAll()
                .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/produtos", true).permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/produtos?logout").permitAll())
                .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
