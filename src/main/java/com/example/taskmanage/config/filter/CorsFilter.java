package com.example.taskmanage.config.filter;

//@Configuration
public class CorsFilter
//        implements Filter
{
//    no need because have cors config in AppConfig

//    @Override
//    public void doFilter(ServletRequest servletRequest,
//                         ServletResponse servletResponse,
//                         FilterChain filterChain) throws IOException, ServletException {
//
//        final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
//        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
//
//        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod())) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//    }
}
