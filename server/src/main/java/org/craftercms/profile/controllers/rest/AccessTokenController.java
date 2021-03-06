package org.craftercms.profile.controllers.rest;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import java.util.Collections;
import java.util.List;

import org.craftercms.profile.api.AccessToken;
import org.craftercms.profile.api.exceptions.ProfileException;
import org.craftercms.profile.api.services.AccessTokenService;
import org.craftercms.profile.exceptions.NoSuchAccessTokenException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.craftercms.profile.api.ProfileConstants.*;

/**
 * REST controller for the access token service.
 *
 * @author avasquez
 */
@Controller
@RequestMapping(BASE_URL_ACCESS_TOKEN)
@Api(value = "access_token", basePath = BASE_URL_ACCESS_TOKEN, description = "Access token operations")
public class AccessTokenController {

    protected AccessTokenService accessTokenService;

    @Required
    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    @ApiOperation("Creates a new access token")
    @ApiImplicitParam(name = "accessTokenId", required = true, dataType = "string", paramType = "query",
                      value = "The ID of the application access token")
    @RequestMapping(value = URL_ACCESS_TOKEN_CREATE, method = RequestMethod.POST)
    @ResponseBody
    public AccessToken createToken(@ApiParam("The token to create")
                                   @RequestBody AccessToken token) throws ProfileException {
        return accessTokenService.createToken(token);
    }

    @ApiOperation("Returns the token for the given ID")
    @ApiImplicitParam(name = "accessTokenId", required = true, dataType = "string", paramType = "query",
                      value = "The ID of the application access token")
    @RequestMapping(value = URL_ACCESS_TOKEN_GET, method = RequestMethod.GET)
    @ResponseBody
    public AccessToken getToken(
        @ApiParam("The ID of the token") @PathVariable(PATH_VAR_ID) String id) throws ProfileException {
        AccessToken token = accessTokenService.getToken(id);
        if (token != null) {
            return token;
        } else {
            throw new NoSuchAccessTokenException(id);
        }
    }

    @ApiOperation("Returns all the access tokens in the DB")
    @ApiImplicitParam(name = "accessTokenId", required = true, dataType = "string", paramType = "query",
                      value = "The ID of the application access token")
    @RequestMapping(value = URL_ACCESS_TOKEN_GET_ALL, method = RequestMethod.GET)
    @ResponseBody
    public List<AccessToken> getAllTokens() throws ProfileException {
        List<AccessToken> tokens = accessTokenService.getAllTokens();
        if (tokens != null) {
            return tokens;
        } else {
            return Collections.emptyList();
        }
    }

    @ApiOperation("Returns all the access tokens in the DB")
    @ApiImplicitParam(name = "accessTokenId", required = true, dataType = "string", paramType = "query",
                      value = "The ID of the application access token")
    @RequestMapping(value = URL_ACCESS_TOKEN_DELETE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void deleteToken(
        @ApiParam("The ID of the token") @PathVariable(PATH_VAR_ID) String id) throws ProfileException {
        accessTokenService.deleteToken(id);
    }

}
