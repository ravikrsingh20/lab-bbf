package org.rwth.bbf4.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class HeaderFilter  implements Filter {
	
	private String mode = "DENY";	
   
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse)response;
        resp.addHeader("X-FRAME-OPTIONS", mode );	        
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
        resp.setHeader("Pragma", "no-cache");
        chain.doFilter(request, response);
    }
    
    public void destroy() {
    }
    
    public void init(FilterConfig filterConfig) {
        String configMode = filterConfig.getInitParameter("mode");
        if ( configMode != null ) {
            mode = configMode;
        }
    }

}
