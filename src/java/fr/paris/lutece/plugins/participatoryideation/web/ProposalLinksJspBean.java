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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.participatoryideation.business.link.Link;
import fr.paris.lutece.plugins.participatoryideation.business.link.LinkHome;
import fr.paris.lutece.plugins.participatoryideation.business.link.LinkSearcher;
import fr.paris.lutece.plugins.participatoryideation.business.proposal.Proposal;
import fr.paris.lutece.plugins.participatoryideation.business.proposal.ProposalHome;
import fr.paris.lutece.plugins.participatoryideation.service.IdeationStaticService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

/**
 * This class provides the user interface to manage proposal links features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageProposalLinks.jsp", controllerPath = "jsp/admin/plugins/participatoryideation/", right = "PARTICIPATORYIDEATION_LINKS_MANAGEMENT" )
public class ProposalLinksJspBean extends ManageProposalLinksJspBean
{

    // //////////////////////////////////////////////////////////////////////////
    // Constants

    private static final long serialVersionUID = 1L;

    // templates
    private static final String TEMPLATE_MANAGE_LINKS = "/admin/plugins/participatoryideation/manage_links.html";
    private static final String TEMPLATE_CREATE_LINK = "/admin/plugins/participatoryideation/create_link.html";
    private static final String TEMPLATE_CREATE_SEVERAL_LINKS = "/admin/plugins/participatoryideation/create_several_links.html";
    private static final String TEMPLATE_MODIFY_LINK = "/admin/plugins/participatoryideation/modify_link.html";

    // Parameters
    private static final String PARAMETER_ID_LINK = "id";
    private static final String PARAMETER_FILTER_CODE_CAMPAIGN = "filter_code_campaign";
    private static final String PARAMETER_FILTER_CODE_PROPOSAL = "filter_code_proposal";
    private static final String PARAMETER_FILTER_TITLE = "filter_title";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_LINKS = "participatoryideation.manage_links.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_LINK = "participatoryideation.modify_link.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_LINK = "participatoryideation.create_link.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_SEVERAL_LINKS = "participatoryideation.create_several_links.pageTitle";

    // Markers
    private static final String MARK_LINKS_LIST = "links_list";
    private static final String MARK_LINK = "link";
    private static final String MARK_SEVERAL_LINKS_PARENT_CODE_CAMPAIN = "severalLinksParentCodeCampain";
    private static final String MARK_SEVERAL_LINKS_PARENT_CODE_PROPOSAL = "severalLinksParentCodeProposal";
    private static final String MARK_SEVERAL_LINKS_CHILD_CODE_CAMPAIN = "severalLinksChildCodeCampain";
    private static final String MARK_SEVERAL_LINKS_CHILD_CODES_PROPOSALS = "severalLinksChildCodesProposals";
    // private static final String MARK_LIST_CAMPAIGNS = "listCampaigns";

    private static final String MARK_FILTER_CODE_CAMPAIGN = "filter_code_campaign";
    private static final String MARK_FILTER_CODE_PROPOSAL = "filter_code_proposal";
    private static final String MARK_FILTER_TITLE = "filter_title";

    private static final String JSP_MANAGE_LINKS = "jsp/admin/plugins/participatoryideation/ManageProposalLinks.jsp";

    private static final String VALIDATION_ATTRIBUTES_PREFIX = "participatoryideation.model.entity.link.attribute.";

    // Views
    private static final String VIEW_MANAGE_LINKS = "manageLinks";
    private static final String VIEW_CREATE_LINK = "createLink";
    private static final String VIEW_CREATE_SEVERAL_LINKS = "createSeveralLinks";
    private static final String VIEW_MODIFY_LINK = "modifyLink";

    // Actions
    private static final String ACTION_CREATE_LINK = "createLink";
    private static final String ACTION_CREATE_SEVERAL_LINKS = "createSeveralLinks";
    private static final String ACTION_MODIFY_LINK = "modifyLink";
    private static final String ACTION_REMOVE_LINK = "removeLink";
    private static final String ACTION_CONFIRM_REMOVE_LINK = "confirmRemoveLink";
    private static final String ACTION_SEARCH_LINK = "searchLink";
    private static final String ACTION_CANCEL_SEARCH = "cancelSearch";

    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_LINK = "participatoryideation.message.confirmRemoveLink";
    private static final String MESSAGE_ERROR_CHILD_CODES_PROPOSALS_MANDATORY = "participatoryideation.validation.link.parentId.childCodesProposalsMandatory";
    // private static final String MESSAGE_ERROR_NOT_AN_INTEGER = "participatoryideation.validation.link.parentId.notAnInteger";

    // Infos
    private static final String INFO_LINK_CREATED = "participatoryideation.info.link.created";
    private static final String INFO_SEVERAL_LINKS_CREATED = "participatoryideation.info.several.links.created";
    private static final String INFO_LINK_UPDATED = "participatoryideation.info.link.updated";
    private static final String INFO_LINK_REMOVED = "participatoryideation.info.link.removed";

    // Session variable to store working values
    private Link _link;
    private String _parentCodeCampaign;
    private int _parentCodeProposal;
    private String _childCodeCampaign;
    private String _childCodesProposals;

    private LinkSearcher _linkSearcher;
    private static LinkSearcher defaultSearcher;

    static
    {
        defaultSearcher = new LinkSearcher( );
    }

    /* *********************************************************************************** */
    /* * MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MA * */
    /* * MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MANAGE MA * */
    /* *********************************************************************************** */

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_LINKS, defaultView = true )
    public String getManageLinks( HttpServletRequest request )
    {
        _link = null;
        LinkSearcher currentSearcher = _linkSearcher != null ? _linkSearcher : defaultSearcher;
        List<Link> listLinks = (List<Link>) LinkHome.getLinksListSearch( currentSearcher );
        Map<String, Object> model = getPaginatedListModel( request, MARK_LINKS_LIST, listLinks, JSP_MANAGE_LINKS );

        // Collection<Campaign> listCampaigns = CampaignHome.getCampaignsList( );

        if ( _linkSearcher != null )
        {
            if ( StringUtils.isNotBlank( _linkSearcher.getCodeCampaign( ) ) )
            {
                model.put( MARK_FILTER_CODE_CAMPAIGN, _linkSearcher.getCodeCampaign( ) );
            }

            if ( _linkSearcher.getCodeProposal( ) != null )
            {
                model.put( MARK_FILTER_CODE_PROPOSAL, _linkSearcher.getCodeProposal( ) );
            }

            if ( StringUtils.isNotBlank( _linkSearcher.getTitle( ) ) )
            {
                model.put( MARK_FILTER_TITLE, _linkSearcher.getTitle( ) );
            }
        }

        IdeationStaticService.getInstance( ).fillAllStaticContent( model );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_LINKS, TEMPLATE_MANAGE_LINKS, model );
    }

    /* *********************************************************************************** */
    /* * CREATE_LINK CREATE_LINK CREATE_LINK CREATE_LINK CREATE_LINK CREATE_LINK C * */
    /* * CREATE_LINK CREATE_LINK CREATE_LINK CREATE_LINK CREATE_LINK CREATE_LINK C * */
    /* *********************************************************************************** */

    /**
     * Returns the form to create a link
     *
     * @param request
     *            The Http request
     * @return the html code of the link form
     */
    @View( VIEW_CREATE_LINK )
    public String getCreateLink( HttpServletRequest request )
    {
        _link = ( _link != null ) ? _link : new Link( );

        Map<String, Object> model = getModel( );
        model.put( MARK_LINK, _link );

        IdeationStaticService.getInstance( ).fillAllStaticContent( model );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_LINK, TEMPLATE_CREATE_LINK, model );
    }

    /**
     * Process the data capture form of a new link
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_LINK )
    public String doCreateLink( HttpServletRequest request )
    {
        populate( _link, request );

        // Check constraints
        if ( !validateBean( _link, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_LINK, PARAMETER_ID_LINK, _link.getId( ) );
        }

        if ( determineProposalsIdFromCodes( _link, request ) )
        {
            return redirectView( request, ACTION_CREATE_LINK );
        }

        LinkHome.create( _link );
        addInfo( INFO_LINK_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_LINKS );
    }

    /* *********************************************************************************** */
    /* * CREATE_SEVERAL_LINK CREATE_SEVERAL_LINK CREATE_SEVERAL_LINK CREATE_SEVERAL_L * */
    /* * CREATE_SEVERAL_LINK CREATE_SEVERAL_LINK CREATE_SEVERAL_LINK CREATE_SEVERAL_L * */
    /* *********************************************************************************** */

    /**
     * Returns the form to create several links
     *
     * @param request
     *            The Http request
     * @return the html code of the form
     */
    @View( VIEW_CREATE_SEVERAL_LINKS )
    public String getCreateSeveralLinks( HttpServletRequest request )
    {
        Map<String, Object> model = getModel( );

        model.put( MARK_SEVERAL_LINKS_PARENT_CODE_CAMPAIN, _parentCodeCampaign );
        model.put( MARK_SEVERAL_LINKS_PARENT_CODE_PROPOSAL, _parentCodeProposal ); // Si -1, mettre ""

        model.put( MARK_SEVERAL_LINKS_CHILD_CODE_CAMPAIN, _childCodeCampaign );
        model.put( MARK_SEVERAL_LINKS_CHILD_CODES_PROPOSALS, _childCodesProposals );

        IdeationStaticService.getInstance( ).fillAllStaticContent( model );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_SEVERAL_LINKS, TEMPLATE_CREATE_SEVERAL_LINKS, model );
    }

    /**
     * Process the data capture form of several new links
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_SEVERAL_LINKS )
    public String doCreateSeveralLinks( HttpServletRequest request )
    {
        _parentCodeCampaign = request.getParameter( "parentCodeCampaign" );
        _parentCodeProposal = parseLinkProposalCode( request.getParameter( "parentCodeProposal" ) );
        _childCodeCampaign = request.getParameter( "childCodeCampaign" );
        _childCodesProposals = request.getParameter( "childCodesProposals" );

        List<Link> linksToCreate = new ArrayList<Link>( );
        boolean error = false;

        // Split children codes, then process the add for each of one
        if ( StringUtils.isEmpty( _childCodesProposals ) )
        {
            addError( MESSAGE_ERROR_CHILD_CODES_PROPOSALS_MANDATORY, request.getLocale( ) );
            return redirectView( request, ACTION_CREATE_SEVERAL_LINKS );
        }

        String [ ] severaLlinkCodes = _childCodesProposals.split( ";" );

        // Testing all number before creating in SGBD.
        for ( String childCodeProposalStr : severaLlinkCodes )
        {
            int childCodeProposal = parseLinkProposalCode( childCodeProposalStr );
            if ( childCodeProposal == -1 )
            {
                error = true;
            }
            else
            {
                Link link = new Link( );
                link.setParentCodeCampaign( _parentCodeCampaign );
                link.setParentCodeProposal( _parentCodeProposal );
                link.setChildCodeCampaign( _childCodeCampaign );
                link.setChildCodeProposal( childCodeProposal );

                if ( determineProposalsIdFromCodes( link, request ) )
                {
                    error = true;
                }
                else
                {
                    linksToCreate.add( link );
                }
            }
        }

        // If no error, creating links in SGBD.
        if ( error )
        {
            return redirectView( request, ACTION_CREATE_SEVERAL_LINKS );
        }
        for ( Link linkToCreate : linksToCreate )
        {
            LinkHome.create( linkToCreate );
        }

        addInfo( INFO_SEVERAL_LINKS_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_LINKS );
    }

    /* *********************************************************************************** */
    /* * REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE RE * */
    /* * REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE RE * */
    /* *********************************************************************************** */

    /**
     * Manages the removal form of a link whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_LINK )
    public String getConfirmRemoveLink( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_LINK ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_LINK ) );
        url.addParameter( PARAMETER_ID_LINK, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_LINK, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a link
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage links
     */
    @Action( ACTION_REMOVE_LINK )
    public String doRemoveLink( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_LINK ) );
        LinkHome.remove( nId );
        addInfo( INFO_LINK_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_LINKS );
    }

    /* *********************************************************************************** */
    /* * MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MO * */
    /* * MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MODIFY MO * */
    /* *********************************************************************************** */

    /**
     * Returns the form to update info about a link
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_LINK )
    public String getModifyLink( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_LINK ) );

        if ( _link == null || ( _link.getId( ) != nId ) )
        {
            _link = LinkHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_LINK, _link );

        IdeationStaticService.getInstance( ).fillAllStaticContent( model );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_LINK, TEMPLATE_MODIFY_LINK, model );
    }

    /**
     * Process the change form of a link
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_LINK )
    public String doModifyLink( HttpServletRequest request )
    {
        populate( _link, request );

        // Check constraints
        if ( !validateBean( _link, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_LINK, PARAMETER_ID_LINK, _link.getId( ) );
        }
        if ( determineProposalsIdFromCodes( _link, request ) )
        {
            return redirectView( request, ACTION_MODIFY_LINK );
        }

        LinkHome.update( _link );
        addInfo( INFO_LINK_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_LINKS );
    }

    /* *********************************************************************************** */
    /* * SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SE * */
    /* * SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SEARCH SE * */
    /* *********************************************************************************** */

    /**
     * Process to search a link
     * 
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( value = ACTION_SEARCH_LINK )
    public String doSearchLink( HttpServletRequest request )
    {

        if ( _linkSearcher == null )
        {
            _linkSearcher = new LinkSearcher( );
        }

        String strCodeCampaign = request.getParameter( PARAMETER_FILTER_CODE_CAMPAIGN );
        if ( strCodeCampaign != null )
        {
            if ( StringUtils.isBlank( strCodeCampaign ) )
            {
                _linkSearcher.setCodeCampaign( null );
            }
            else
            {
                _linkSearcher.setCodeCampaign( strCodeCampaign );
            }
        }

        String strCodeProposal = request.getParameter( PARAMETER_FILTER_CODE_PROPOSAL );
        if ( strCodeProposal != null )
        {
            if ( StringUtils.isBlank( strCodeProposal ) )
            {
                _linkSearcher.setCodeProposal( null );
            }
            else
            {
                _linkSearcher.setCodeProposal( Integer.parseInt( strCodeProposal ) );
            }
        }

        String strTitle = request.getParameter( PARAMETER_FILTER_TITLE );
        if ( strTitle != null )
        {
            if ( StringUtils.isBlank( strTitle ) )
            {
                _linkSearcher.setTitle( null );
            }
            else
            {
                _linkSearcher.setTitle( strTitle );
            }
        }

        return redirectView( request, VIEW_MANAGE_LINKS );

    }

    /**
     * Reset the search
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @Action( value = ACTION_CANCEL_SEARCH )
    public String doCancelSearch( HttpServletRequest request )
    {
        _linkSearcher = null;
        return redirectView( request, VIEW_MANAGE_LINKS );
    }

    /* *********************************************************************************** */
    /* * UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS U * */
    /* * UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS U * */
    /* *********************************************************************************** */

    /**
     * Parse the string and return the result as an int. If not parsable, returns -1.
     */
    private int parseLinkProposalCode( String _str )
    {
        try
        {
            return Integer.parseInt( _str );
        }
        catch( NumberFormatException ex )
        {
            addError( "Can not parse this string as an integer : '" + _str + "'." );
            return -1;
        }
    }

    /**
     * Populate parentId and childId of link, from codeCampaigns and codeProposals
     * 
     * @param _link
     *            The link
     * @param request
     *            The HTTP request
     * @return True if an error occured
     */
    private boolean determineProposalsIdFromCodes( Link _link, HttpServletRequest request )
    {
        boolean isError = false;

        // Calculating technical ids from campaign and idea codes
        Proposal parentProposal = ProposalHome.findByCodes( _link.getParentCodeCampaign( ), _link.getParentCodeProposal( ) );
        if ( parentProposal == null )
        {
            // addError( MESSAGE_ERROR_NO_SUCH_PARENT, request.getLocale());
            addError( "Can not find parent proposal : " + _link.getParentCodeCampaign( ) + "-" + _link.getParentCodeProposal( ) + "." );
            isError = true;
        }
        else
        {
            _link.setParentId( parentProposal.getId( ) );
        }

        Proposal childProposal = ProposalHome.findByCodes( _link.getChildCodeCampaign( ), _link.getChildCodeProposal( ) );
        if ( childProposal == null )
        {
            // addError( MESSAGE_ERROR_NO_SUCH_CHILD, request.getLocale());
            addError( "Can not find child proposal : " + _link.getChildCodeCampaign( ) + "-" + _link.getChildCodeProposal( ) + "." );
            isError = true;
        }
        else
        {
            _link.setChildId( childProposal.getId( ) );
        }

        return isError;
    }

}
