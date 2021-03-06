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
package fr.paris.lutece.plugins.participatoryideation.business;

import java.util.Collection;

import fr.paris.lutece.plugins.participatoryideation.business.link.Link;
import fr.paris.lutece.plugins.participatoryideation.business.link.LinkHome;
import fr.paris.lutece.plugins.participatoryideation.business.proposal.Proposal;
import fr.paris.lutece.plugins.participatoryideation.business.proposal.ProposalHome;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.util.ReferenceList;

/**
 * SubmitterTypeTest
 */
public class LinkHomeTest extends LuteceTestCase
{

    private final static String PARENT_CAMPAIGN = "abc";
    private final static int PARENT_CODE_ID = 1;
    private final static String PARENT_TITLE = "parent_title";

    private final static String CHILD_CAMPAIGN = "xyz";
    private final static int CHILD_CODE_ID = 1;
    private final static String CHILD_TITLE = "child_title";

    private final static String PARENT_CAMPAIGN_2 = "abc_2";
    private final static int PARENT_CODE_ID_2 = 1;
    private final static String PARENT_TITLE_2 = "parent_title_2";

    private final static String CHILD_CAMPAIGN_2 = "xyz_2";
    private final static int CHILD_CODE_ID_2 = 1;
    private final static String CHILD_TITLE_2 = "child_title_2";

    public void testBusiness( )
    {
        // *********************************************************************************************
        // * DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA *
        // * DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA DATA *
        // *********************************************************************************************

        Proposal proposalParent = ProposalHomeTest.getMockProposalInstance( );
        proposalParent.setCodeCampaign( PARENT_CAMPAIGN );
        proposalParent.setCodeProposal( PARENT_CODE_ID );
        proposalParent.setTitre( PARENT_TITLE );
        ProposalHome.create( proposalParent );

        Proposal proposalChild = ProposalHomeTest.getMockProposalInstance( );
        proposalChild.setCodeCampaign( CHILD_CAMPAIGN );
        proposalChild.setCodeProposal( CHILD_CODE_ID );
        proposalChild.setTitre( CHILD_TITLE );
        ProposalHome.create( proposalChild );

        Proposal proposalParent2 = ProposalHomeTest.getMockProposalInstance( );
        proposalParent2.setCodeCampaign( PARENT_CAMPAIGN_2 );
        proposalParent2.setCodeProposal( PARENT_CODE_ID_2 );
        proposalParent2.setTitre( PARENT_TITLE_2 );
        ProposalHome.create( proposalParent2 );

        Proposal proposalChild2 = ProposalHomeTest.getMockProposalInstance( );
        proposalChild2.setCodeCampaign( CHILD_CAMPAIGN_2 );
        proposalChild2.setCodeProposal( CHILD_CODE_ID_2 );
        proposalChild2.setTitre( CHILD_TITLE_2 );
        ProposalHome.create( proposalChild2 );

        Link link = new Link( );

        link.setChildCodeCampaign( CHILD_CAMPAIGN );
        link.setChildId( proposalChild.getId( ) );
        link.setChildCodeProposal( CHILD_CODE_ID );
        link.setChildTitle( CHILD_TITLE );

        link.setParentCodeCampaign( PARENT_CAMPAIGN );
        link.setParentId( proposalParent.getId( ) );
        link.setParentCodeProposal( PARENT_CODE_ID );
        link.setParentTitle( PARENT_TITLE );

        LinkHome.create( link );

        // *********************************************************************************************
        // * TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST *
        // * TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST *
        // *********************************************************************************************

        // Test getIdLinksList method
        Collection<Integer> idLinksList = LinkHome.getIdLinksList( );
        assertEquals( 1, idLinksList.size( ) );
        assertEquals( link.getId( ), (int) idLinksList.iterator( ).next( ) );

        // Test getLinksList method
        Collection<Link> linksList = LinkHome.getLinksList( );
        assertEquals( 1, linksList.size( ) );
        Link storedLink = linksList.iterator( ).next( );
        assertEquals( link, storedLink );

        // Test update method
        link.setChildCodeCampaign( CHILD_CAMPAIGN_2 );
        link.setChildId( proposalChild2.getId( ) );
        link.setChildCodeProposal( CHILD_CODE_ID_2 );
        link.setChildTitle( CHILD_TITLE_2 );

        link.setParentCodeCampaign( PARENT_CAMPAIGN_2 );
        link.setParentId( proposalParent2.getId( ) );
        link.setParentCodeProposal( PARENT_CODE_ID_2 );
        link.setParentTitle( PARENT_TITLE_2 );

        LinkHome.update( link );

        linksList = LinkHome.getLinksList( );
        storedLink = linksList.iterator( ).next( );
        assertEquals( link, storedLink );

        // Test remove method
        LinkHome.remove( link.getId( ) );
        idLinksList = LinkHome.getIdLinksList( );
        assertEquals( 0, idLinksList.size( ) );
    }

    // *********************************************************************************************
    // * UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL *
    // * UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL UTIL *
    // *********************************************************************************************

    private void assertEquals( Link link1, Link link2 )
    {
        assertEquals( link1.getId( ), link2.getId( ) );

        assertEquals( link1.getChildCodeCampaign( ), link2.getChildCodeCampaign( ) );

        assertEquals( link1.getChildId( ), link2.getChildId( ) );

        assertEquals( link1.getChildCodeProposal( ), link2.getChildCodeProposal( ) );
        assertEquals( link1.getChildTitle( ), link2.getChildTitle( ) );

        assertEquals( link1.getParentCodeCampaign( ), link2.getParentCodeCampaign( ) );
        assertEquals( link1.getParentId( ), link2.getParentId( ) );
        assertEquals( link1.getParentCodeProposal( ), link2.getParentCodeProposal( ) );
        assertEquals( link1.getParentTitle( ), link2.getParentTitle( ) );
    }

}
