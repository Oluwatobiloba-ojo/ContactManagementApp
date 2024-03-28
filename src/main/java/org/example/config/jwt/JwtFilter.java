package org.example.config.jwt;

//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.example.config.securityconfig.UserDetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
//import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Service
//@RequiredArgsConstructor
public class JwtFilter  {

//    private JwtService jwtService;
//    private UserDetailService userDetailService;
//
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain) throws ServletException, IOException {
//        String auth = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//        if (auth != null && auth.startsWith("Bearer ")){
//            token = auth.substring(7);
//            username = jwtService.extractUsername(token);
//        }
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            UserDetails userDetails = userDetailService.loadUserByUsername(username);
//            if (jwtService.isValidToken(token, userDetails)){
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities()
//                        );
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
}
