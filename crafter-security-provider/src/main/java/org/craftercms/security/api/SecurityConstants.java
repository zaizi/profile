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
package org.craftercms.security.api;

/**
 * Contains various security constants used in different parts of the library.
 *
 * @author Alfonso Vásquez
 */
public abstract class SecurityConstants {

    public static final String ANONYMOUS_ID =       "Anonymous";
    public static final String ANONYMOUS_USERNAME = "Anonymous";
    public static final String ANONYMOUS_PASSWORD = "Anonymous";

    public static final String AUTHENTICATION_SYSTEM_EXCEPTION_ATTRIBUTE = "authenticationSystemException";
    public static final String USER_AUTHENTICATION_EXCEPTION_ATTRIBUTE = "userAuthenticationException";
    public static final String ACCESS_DENIED_EXCEPTION_ATTRIBUTE = "accessDeniedException";

}