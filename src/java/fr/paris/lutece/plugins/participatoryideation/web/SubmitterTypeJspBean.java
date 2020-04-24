/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.participatoryideation.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.participatoryideation.business.submitter.SubmitterType;
import fr.paris.lutece.plugins.participatoryideation.business.submitter.SubmitterTypeHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

/**
 * This class provides the user interface to manage SubmitterType features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageSubmitterTypes.jsp", controllerPath = "jsp/admin/plugins/participatoryideation/", right = "PARTICIPATORYIDEATION_MANAGEMENT" )
public class SubmitterTypeJspBean extends ManageIdeationJspBean
{

    // //////////////////////////////////////////////////////////////////////////
    // Constants

    // templates
    private static final String TEMPLATE_MANAGE_SUBMITTERS = "/admin/plugins/participatoryideation/manage_submittertypes.html";
    private static final String TEMPLATE_CREATE_SUBMITTER = "/admin/plugins/participatoryideation/create_submittertype.html";
    private static final String TEMPLATE_MODIFY_SUBMITTER = "/admin/plugins/participatoryideation/modify_submittertype.html";

    // Parameters
    private static final String PARAMETER_ID_SUBMITTER = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_SUBMITTERS = "participatoryideation.manage_submittertypes.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_SUBMITTER = "participatoryideation.modify_submittertype.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_SUBMITTER = "participatoryideation.create_submittertype.pageTitle";

    // Markers
    private static final String MARK_SUBMITTER_LIST = "submittertype_list";
    private static final String MARK_SUBMITTER = "submittertype";

    private static final String JSP_MANAGE_SUBMITTERS = "jsp/admin/plugins/participatoryideation/ManageSubmitterTypes.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_SUBMITTER = "participatoryideation.message.confirmRemoveSubmitterType";
    private static final String PROPERTY_DEFAULT_LIST_SUBMITTER_PER_PAGE = "participatoryideation.listSubmitterTypes.itemsPerPage";

    private static final String VALIDATION_ATTRIBUTES_PREFIX = "participatoryideation.model.entity.submittertype.attribute.";

    // Views
    private static final String VIEW_MANAGE_SUBMITTERS = "manageSubmitterTypes";
    private static final String VIEW_CREATE_SUBMITTER = "createSubmitterType";
    private static final String VIEW_MODIFY_SUBMITTER = "modifySubmitterType";

    // Actions
    private static final String ACTION_CREATE_SUBMITTER = "createSubmitterType";
    private static final String ACTION_MODIFY_SUBMITTER = "modifySubmitterType";
    private static final String ACTION_REMOVE_SUBMITTER = "removeSubmitterType";
    private static final String ACTION_CONFIRM_REMOVE_SUBMITTER = "confirmRemoveSubmitterType";

    // Infos
    private static final String INFO_SUBMITTER_CREATED = "participatoryideation.info.submittertype.created";
    private static final String INFO_SUBMITTER_UPDATED = "participatoryideation.info.submittertype.updated";
    private static final String INFO_SUBMITTER_REMOVED = "participatoryideation.info.submittertype.removed";

    // Session variable to store working values
    private SubmitterType _submittertype;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_SUBMITTERS, defaultView = true )
    public String getManageSubmitterTypes( HttpServletRequest request )
    {
        _submittertype = null;
        List<SubmitterType> listSubmitterTypes = (List<SubmitterType>) SubmitterTypeHome.getSubmitterTypesList( );
        Map<String, Object> model = getPaginatedListModel( request, MARK_SUBMITTER_LIST, listSubmitterTypes, JSP_MANAGE_SUBMITTERS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_SUBMITTERS, TEMPLATE_MANAGE_SUBMITTERS, model );
    }

    /**
     * Returns the form to create a submittertype
     *
     * @param request
     *            The Http request
     * @return the html code of the submittertype form
     */
    @View( VIEW_CREATE_SUBMITTER )
    public String getCreateSubmitterType( HttpServletRequest request )
    {
        _submittertype = ( _submittertype != null ) ? _submittertype : new SubmitterType( );

        Map<String, Object> model = getModel( );
        model.put( MARK_SUBMITTER, _submittertype );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_SUBMITTER, TEMPLATE_CREATE_SUBMITTER, model );
    }

    /**
     * Process the data capture form of a new submittertype
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_SUBMITTER )
    public String doCreateSubmitterType( HttpServletRequest request )
    {
        populate( _submittertype, request );

        // Check constraints
        if ( !validateBean( _submittertype, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_SUBMITTER );
        }

        SubmitterTypeHome.create( _submittertype );
        addInfo( INFO_SUBMITTER_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_SUBMITTERS );
    }

    /**
     * Manages the removal form of a submittertype whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_SUBMITTER )
    public String getConfirmRemoveSubmitterType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_SUBMITTER ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_SUBMITTER ) );
        url.addParameter( PARAMETER_ID_SUBMITTER, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_SUBMITTER, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a submittertype
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage submittertypes
     */
    @Action( ACTION_REMOVE_SUBMITTER )
    public String doRemoveSubmitterType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_SUBMITTER ) );
        SubmitterTypeHome.remove( nId );
        addInfo( INFO_SUBMITTER_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_SUBMITTERS );
    }

    /**
     * Returns the form to update info about a submittertype
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_SUBMITTER )
    public String getModifySubmitterType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_SUBMITTER ) );

        if ( _submittertype == null || ( _submittertype.getId( ) != nId ) )
        {
            _submittertype = SubmitterTypeHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_SUBMITTER, _submittertype );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_SUBMITTER, TEMPLATE_MODIFY_SUBMITTER, model );
    }

    /**
     * Process the change form of a submittertype
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_SUBMITTER )
    public String doModifySubmitterType( HttpServletRequest request )
    {
        populate( _submittertype, request );

        // Check constraints
        if ( !validateBean( _submittertype, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_SUBMITTER, PARAMETER_ID_SUBMITTER, _submittertype.getId( ) );
        }

        SubmitterTypeHome.update( _submittertype );
        addInfo( INFO_SUBMITTER_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_SUBMITTERS );
    }
}
