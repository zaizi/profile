/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.security.authentication.impl;

import org.craftercms.security.api.RequestContext;
import org.craftercms.security.api.SecurityConstants;
import org.craftercms.security.api.UserProfile;
import org.craftercms.security.authentication.AuthenticationToken;
import org.craftercms.security.authentication.AuthenticationTokenCache;
import org.craftercms.security.authentication.LoginSuccessHandler;
import org.craftercms.security.exception.CrafterSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Default implementation of {@link LoginSuccessHandler}:
 *
 * <ol>
 *     <li>Deletes any authentication exception saved in the session.</li>
 *     <li>Saves the user profile and ticket in the request context.</li>
 *     <li>Caches the profile.</li>
 *     <li>Uses the Spring {@link RequestCache} to obtain the previous request before login and redirect to it.</li>
 * </ol>
 *
 * @author Alfonso Vásquez
 */
public class LoginSuccessHandlerImpl implements LoginSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandlerImpl.class);

    protected AuthenticationTokenCache authenticationTokenCache;
    protected RequestCache requestCache;
    protected String defaultTargetUrl;

    public LoginSuccessHandlerImpl() {
        requestCache = new HttpSessionRequestCache();
    }

    @Required
    public void setAuthenticationTokenCache(AuthenticationTokenCache authenticationTokenCache) {
        this.authenticationTokenCache = authenticationTokenCache;
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    @Required
    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }

    public void onLoginSuccess(String ticket, UserProfile profile, RequestContext context) throws CrafterSecurityException, IOException {
        AuthenticationToken token = new AuthenticationToken();
        token.setTicket(ticket);
        token.setProfile(profile);

        context.setAuthenticationToken(token);

        clearException(context);
        cacheAuthenticationToken(token, context);
        redirectToSavedUrl(context);
    }

    protected void clearException(RequestContext context) {
        if (logger.isDebugEnabled()) {
            logger.debug("Removing any authentication exceptions from session, not needed anymore");
        }

        HttpSession session = context.getRequest().getSession();
        session.removeAttribute(SecurityConstants.AUTHENTICATION_SYSTEM_EXCEPTION_ATTRIBUTE);
        session.removeAttribute(SecurityConstants.USER_AUTHENTICATION_EXCEPTION_ATTRIBUTE);
    }

    protected void cacheAuthenticationToken(AuthenticationToken token, RequestContext context) {
        if (logger.isDebugEnabled()) {
            logger.debug("Caching authentication token " + token);
        }

        authenticationTokenCache.saveToken(context, token);
    }

    protected void redirectToSavedUrl(RequestContext context) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(context.getRequest(), context.getResponse());

        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();

            if (logger.isDebugEnabled()) {
                logger.debug("Redirecting to saved URL before login: " + redirectUrl);
            }

            context.getResponse().sendRedirect(redirectUrl);
        } else {
            redirectToDefaultTargetUrl(context);
        }
    }

    protected void redirectToDefaultTargetUrl(RequestContext context) throws IOException {
        String redirectUrl = context.getRequest().getContextPath() + defaultTargetUrl;

        if (logger.isDebugEnabled()) {
            logger.debug("Redirecting to URL: " + redirectUrl);
        }

        context.getResponse().sendRedirect(redirectUrl);
    }

}