package com.egrand.wfw.auth.console;

import com.egrand.commons.lang.StringUtils;
import com.egrand.commons.lang.model.RestResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @Value("${sso.home.uri:}")
    private String SSOHomeUri;

    @RequestMapping({"/"})
    @ResponseBody
    public RestResponse loginInfo(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException, URISyntaxException {
        if (StringUtils.isNotBlank(this.SSOHomeUri)) {
            if (this.SSOHomeUri.indexOf("?") == -1) ;
            URI uri = new URI(this.SSOHomeUri);
            response.sendRedirect(uri.toString());
        }
        return RestResponse.success(principal);
    }
}
