package com.egrand.wfw.oauth.security.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AppErrorController
        implements ErrorController
{
    private static final Logger logger = LoggerFactory.getLogger(AppErrorController.class);
    private static AppErrorController appErrorController;
    @Autowired
    private ErrorAttributes errorAttributes;
    private static final String ERROR_PATH = "/error";

    public AppErrorController(ErrorAttributes errorAttributes) { this.errorAttributes = errorAttributes; }

    public AppErrorController() {
        if (appErrorController == null) {
            appErrorController = new AppErrorController(this.errorAttributes);
        }
    }

    @RequestMapping(value = {"/error"}, produces = {"text/html"})
    public ModelAndView errorHtml(HttpServletRequest request) { return new ModelAndView("html/401"); }

    @RequestMapping({"/error"})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
        HttpStatus status = getStatus(request);
        return new ResponseEntity(body, status);
    }

    public String getErrorPath() { return "/error"; }

    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes((RequestAttributes)servletRequestAttributes, includeStackTrace);
        String URL = request.getRequestURL().toString();
        map.put("URL", URL);
        logger.debug("AppErrorController.method [error info]: status-" + map.get("status") + ", request url-" + URL);
        return map;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode.intValue());
            }
            catch (Exception exception) {}
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
